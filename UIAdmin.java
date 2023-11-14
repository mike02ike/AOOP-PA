import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: The UIAdmin class represents the user interface for
 * administrators in the
 * TicketMiner application.
 * Administrators can inquire about events by ID or name, view basic event
 * information, and access additional calculated information.
 * <p>
 * 
 * @since 11/14/2023
 * @author Anaiah Quinn
 * @version 3.0
 *          <p>
 * @since 11/13/2023
 * @author Erik LaNeave
 * @version 2.9
 *          <p>
 * @since 10/25/2023
 * @author Michael Ike
 * @version 1.8
 * 
 * @author Ian Gutierrez
 */

public class UIAdmin {

    public static UIAdmin uiAdmin;

    private LinkedHashMap<Integer, Event> events;
    private LinkedHashMap<Integer, Customer> customers;
    private Event selectedEvent;
    private Log logFile = Log.getInstance();
    private Scanner myScanner = new Scanner(System.in);

    public static synchronized UIAdmin getInstance() {
        if (uiAdmin == null) {
            uiAdmin = new UIAdmin();
        }
        return uiAdmin;
    }

    public UIAdmin() {

    }

    public void setHashMaps(LinkedHashMap<Integer, Event> eventMap, LinkedHashMap<Integer, Customer> customerMap) {
        this.events = eventMap;
        this.customers = customerMap;
    }

    public void menu() {
        boolean control = true;
        while (control) {
            // Prints the menu options and ask for user input as option
            System.out.println("\nAdministrator Menu");
            System.out.println("1 - Inquire Event by ID");
            System.out.println("2 - Inquire Event by Name");
            System.out.println("3 - Create New Event");
            System.out.println("4 - Run Auto Purchase");
            System.out.println("5 - Save an Invoice for a Customer");
            System.out.println("6 - Cancel event");
            System.out.print("Enter \"Exit\" to exit the Program\n--> ");

            // Only takes in strings to help with exceptions
            String inputUser = myScanner.next();
            // switch case used to cut down on possible exceptions and clean look
            switch (inputUser.toLowerCase()) {
                case "1": // 1 - inquire event by ID
                    logFile.save(logFile.time() + " Admin picked menu option 1 to Inquire Event by ID\n");
                    inquireEventByID();
                    break;
                case "2": // 2 - inquire event by name
                    logFile.save(logFile.time() + " Admin picked menu option 2 to Inquire Event by Name\n");
                    inquireEventByName();
                    break;
                case "3": // 3 - Create a new event
                    logFile.save(logFile.time() + " Admin picked menu option 3 to create an Event\n");
                    createEvent();
                    break;
                case "4": // 4 - Auto purchase for customers
                    logFile.save(logFile.time() + " Admin picked menu option 4 to run the auto purchase\n");
                    autoPurchase();
                    break;
                case "5": // 5 - Save invoice for customer
                    logFile.save(logFile.time() + " Admin picked menu option 5 to save an invoice for a user\n");
                    saveInvoiceForCustomer();
                    break;
                case "6":
                    cancelEvent();
                    break;
                case "exit":
                    System.out.println("\nThank you for using TicketMiner!\nReturning to login...");
                    logFile.save(logFile.time() + " Admin terminated the program\n");
                    // Stops while loop from running in RunTicket
                    control = false;
                    break;
                default: // Used for incorrect input
                    logFile.save(logFile.time() + " Admin inputted an invalid menu option\n");
                    System.out.println("Invalid input. Please enter a number 1 - 6");
                    break;
            }// end switch

        }
        return;
    }

    public void cancelEvent() {
        Event selectedEvent = selectEvent();
        selectedEvent.setIsCanceled(true);

        if (!selectedEvent.getInvoices().isEmpty()) {
            HashMap<Integer, ArrayList<Invoice>> invoices = selectedEvent.getInvoices();

            for (Entry<Integer, ArrayList<Invoice>> invoice : invoices.entrySet()) {
                ArrayList<Invoice> customerInvoices = invoice.getValue();
                double refundAmount = 0;

                for (Invoice currInvoice : customerInvoices) {
                    refundAmount += currInvoice.getTotalPrice();
                }

                customers.get(invoice.getKey()).addMoney(refundAmount);
            }
        }

        System.out.println("\nEvent has been canceled and all collected money has been refunded.");
    }

    public Event selectEvent() {
        while (true) {
            try {
                System.out.println("Enter the event ID of the event you would like to cancel.");
                int input = myScanner.nextInt();

                if (events.get(input) != null) {
                    return events.get(input);
                } else {
                    System.out.println("Event does not exist. Try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a number");
                myScanner.next();
            }
        }
    }

    /**
     * Allows an admin to create a new event
     * 
     * @param eventMap hashmap containing all events
     * @return updated hashmap
     */
    public void createEvent() {
        Scanner scn = new Scanner(System.in);

        Event newEvent = createBlankEvent();
        newEvent.setVenue(createBlankVenue());

        newEvent.setId(calculateEventID());

        System.out.print("\nEnter the name of the event\n--> ");
        newEvent.setName(scn.nextLine());

        System.out.print("\nEnter the date of the event. FORMAT: MM/DD/YYYY\n--> ");
        newEvent.setDate(scn.nextLine());

        System.out.print("\nEnter the time of the event. FORMAT: XX:XX AM or PM\n--> ");
        newEvent.setTime(scn.nextLine());

        newEvent.getVenue().setName(chooseVenue());

        newEvent.getVenue().setVipPercentage(5);
        newEvent.getVenue().setGoldPercentage(10);
        newEvent.getVenue().setSilverPercentage(15);
        newEvent.getVenue().setBronzePercentage(20);
        newEvent.getVenue().setGenAdmissionPercentage(45);
        newEvent.getVenue().setReservedExtraPercentage(5);

        double generalAdmissionPrice = getGeneralAdmissionPrice();
        newEvent.setGenAdmissionPrice(generalAdmissionPrice);
        newEvent.setBronzePrice(generalAdmissionPrice * 1.5);
        newEvent.setSilverPrice(generalAdmissionPrice * 2.5);
        newEvent.setGoldPrice(generalAdmissionPrice * 3);
        newEvent.setVipPrice(generalAdmissionPrice * 5);

        this.events.put(newEvent.getId(), newEvent);
        System.out.println("\nEvent added!");

    }

    /**
     * Retrieves general admission price from admin
     * 
     * @return general admission price
     */
    public double getGeneralAdmissionPrice() {
        Scanner scn = new Scanner(System.in);
        double price;

        while (true) {
            try {
                System.out.print("\nEnter the price of your general admission tickets. MAX = 4000\n--> ");
                price = scn.nextDouble();

                if (price > 0 && price <= 4000) {
                    return price;
                }

                System.out.println("Not in range! Try again");

            } catch (InputMismatchException e) {
                System.out.println("Input must be a number! Try again.");
                scn.next();
            }

        }
    }

    /**
     * Retrieves venue from admin
     * 
     * @return venue
     */
    public String chooseVenue() {
        Scanner scn = new Scanner(System.in);

        while (true) {
            System.out.println("\nSelect the venue you would like to use");
            System.out.println("1. Sun Bowl Stadium");
            System.out.println("2. Don Haskins Center");
            System.out.println("3. Magoffin Auditorium");
            System.out.println("4. San Jacinto Plaza");
            System.out.print("5. Centennial Plaza\n--> ");

            String input = scn.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4") || input.equals("5")) {
                switch (input) {
                    case "1":
                        return "Sun Bowl Stadium";

                    case "2":
                        return "Don Haskins Center";

                    case "3":
                        return "Magoffin Auditorium";

                    case "4":
                        return "San Jacinto Plaza";

                    case "5":
                        return "Centennial Plaza";
                }
            }

            System.out.println("Invalid selection! Try again.");
        }
    }

    /**
     * Creates a blank event depending on admin selection
     * 
     * @return Blank event
     */
    public Event createBlankEvent() {
        Scanner scn1 = new Scanner(System.in);
        EventFactory eventFactory = new EventFactory();
        Event event;

        while (true) {
            System.out.println("\nSelect the type of event you would like to create");
            System.out.println("1. Sport");
            System.out.println("2. Concert");
            System.out.print("3. Festival\n--> ");

            String input = scn1.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3")) {
                switch (input) {
                    case "1":
                        event = eventFactory.createEvent("Sport");
                        break;

                    case "2":
                        event = eventFactory.createEvent("Concert");
                        break;

                    case "3":
                        event = eventFactory.createEvent("Festival");
                        break;

                    default:
                        event = eventFactory.createEvent("idk");
                        break;
                }

                return event;
            }

            System.out.println("Invalid selection! Try again.");
        }
    }

    /**
     * Creates a blank venue depending on admin selection
     * 
     * @return blank venue
     */
    public Venue createBlankVenue() {
        Scanner scn2 = new Scanner(System.in);
        VenueFactory venueFactory = new VenueFactory();
        Venue venue;

        while (true) {
            System.out.println("\nSelect the type venue you would like to use");
            System.out.println("1. Stadium");
            System.out.println("2. Arena");
            System.out.println("3. Auditorium");
            System.out.print("4. Open Air\n--> ");

            String input = scn2.nextLine();
            if (input.equals("1") || input.equals("2") || input.equals("3") || input.equals("4")) {
                switch (input) {
                    case "1":
                        venue = venueFactory.createVenue("Stadium");
                        break;

                    case "2":
                        venue = venueFactory.createVenue("Arena");
                        break;

                    case "3":
                        venue = venueFactory.createVenue("Auditorium");
                        break;

                    case "4":
                        venue = venueFactory.createVenue("Open Air");
                        break;

                    default:
                        venue = venueFactory.createVenue("idk");
                        break;

                }

                return venue;
            }

            System.out.println("Invalid selection! Try again.");

        }
    }

    /**
     * Returns the event id for a new event
     * 
     * @param eventMap Hashmap of all events
     * @return newest event id
     */
    public int calculateEventID() {
        int largestID = Integer.MIN_VALUE;
        for (Entry<Integer, Event> event : this.events.entrySet()) {
            int currID = event.getKey();

            if (currID > largestID) {
                largestID = currID;
            }
        }

        return largestID + 1;

    }

    public void inquireEventByID() {
        while (true) {
            try {
                System.out.print("Enter the Event ID\n--> ");
                String userInput = myScanner.nextLine();
                int eventID = Integer.parseInt(userInput);
                // Uses .get from the map to find the event based of ID
                Event currEvent = this.events.get(eventID);
                // Checks that the event was found and does not give null
                if (currEvent == null) {
                    logFile.save(logFile.time() + " Admin entered an invalid event ID of " + eventID + "\n");
                    System.out.println("Not a valid Event ID");
                    continue;
                }
                logFile.save(logFile.time() + " Admin entered a correct event ID of " + eventID + "\n");
                this.selectedEvent = currEvent;
                subMenu();
                break;
            } catch (NumberFormatException e) {
                logFile.save(logFile.time() + " Admin did not enter a number for the event ID\n");
                System.out.println("Event not found. Enter a valid Event ID");
                continue;
            }
        }
    }

    public void inquireEventByName() {
        while (true) {
            System.out.print("Enter the Event Name\n--> ");
            String input = myScanner.nextLine();
            Event currEvent = null;
            // Runs through all the events in the map and checks the name
            for (Event curr : this.events.values()) {
                if (input.equalsIgnoreCase(curr.getName())) {
                    currEvent = curr;
                }
            }
            // Checks the the event was found
            if (currEvent == null) {
                logFile.save(logFile.time() + " Admin entered an invalid event name of " + input + "\n");
                System.out.println("Not a valid event name");
                continue;
            }

            logFile.save(logFile.time() + " Admin entered a corret event name of " + input + "\n");
            subMenu();
            break;
        }
    }

    public void subMenu() {
        // Displays standard event and then ask if they want to see the calculated
        // information
        System.out.println("\nStandard Event Information");
        System.out.println("================================");
        this.selectedEvent.eventBasicInfo();
        System.out.println("================================\n");
        while (true) {
            System.out.print("Submenu for Additional Information\n");
            System.out.print("1 - View Event Calculated Info\n 2- Print Event Fees\n3 - Exit Submenu\n--> ");
            String input = myScanner.nextLine();
            switch (input) {
                case "1": // Views calculated event info
                    logFile.save(logFile.time()
                            + " Admin picked submenu option 1 to view event calculated information\n");
                    System.out.println("================================\nAvailable Seats");
                    this.selectedEvent.adminPrintAvailableSeats();
                    System.out.println("Total Revenue");
                    this.selectedEvent.adminPrintRevenueInfo();
                    break;
                case "2":// Print total fees from event
                    logFile.save(logFile.time() + "Admin picked submenu option 2 to print event fees");
                    printFees(selectedEvent);
                    break;
                case "3": // Exit subMenu
                    logFile.save(logFile.time() + " Admin picked submenu option 3 to exit submenu\n");
                    System.out.println("Exiting sub menu...");
                    return;
                default: // Handles incorrect input
                    logFile.save(logFile.time() + " Admin input is invalid option for submenu\n");
                    System.out.println("Please enter a number between 1 and 2");
                    break;
            }
        }
    }

    public void printFees(Event event) {
        System.out.println("Fees");
    }

    public void autoPurchase() {
        Purchase completPurchase = new Purchase();
        ArrayList<AutoPurchaseInstruction> autoPurchases = getAutoPurchaseInfo();
        HashMap<String, Customer> cache = new HashMap<String, Customer>();
        if (autoPurchases == null) {
            return;
        }
        logFile.save(logFile.time() + " AutoPurchse CSV read\n");
        System.out.println(logFile.time());
        StringBuilder firstAndLast = new StringBuilder();
        for (AutoPurchaseInstruction currAuto : autoPurchases) {
            Customer currCustomer;
            firstAndLast.append(currAuto.getCustomerFName());
            firstAndLast.append(currAuto.getCustomerLName());
            String custName = firstAndLast.toString();
            if (cache.get(custName) != null) {
                currCustomer = cache.get(custName);
            } else {
                currCustomer = findCustomer(currAuto.getCustomerFName(), currAuto.getCustomerLName(), cache);
            }
            firstAndLast.delete(0, firstAndLast.length());
            completPurchase.setCurrentCustomer(currCustomer);
            completPurchase.setCurrentEvent(this.events.get(currAuto.getEventID()));
            completPurchase.setAutoTicket(currAuto.getTicketQuantity());
            completPurchase.setCurrentTicketName(currAuto.getTicketType());
            completPurchase.autoPurchaseAdmin();
        }
        System.out.println(logFile.time());
        createAutoPurchaseInvoicesDir();
        writeNewAutoPurchaseInvoices();
        logFile.save(logFile.time() + " Auto purchase complete and all invoices saved\n");
        System.out.println("Auto purchase complete and Invoices have been saved");
    }

    public ArrayList<AutoPurchaseInstruction> getAutoPurchaseInfo() {
        try {
            AutoPurchaseReader autoPurchaseReader = new AutoPurchaseReader();
            ArrayList<AutoPurchaseInstruction> autoPurchases = new ArrayList<>();
            autoPurchases = autoPurchaseReader.readFile("AutoPurchase5M.csv");
            return autoPurchases;
        } catch (FileNotFoundException e) {
            System.out.println("\nAuto Purchase file can't be found");
            return null;
        }
    }

    public Customer findCustomer(String firstName, String lastName, HashMap<String, Customer> cache) {
        for (Customer currCust : this.customers.values()) {
            if (currCust.getFirstName().equalsIgnoreCase(firstName)
                    && currCust.getLastName().equalsIgnoreCase(lastName)) {
                cache.put(firstName + lastName, currCust);
                return currCust;
            }
        }
        return null;
    }

    public Customer findCustomer(String firstName, String lastName) {
        for (Customer currCust : this.customers.values()) {
            if (currCust.getFirstName().equalsIgnoreCase(firstName)
                    && currCust.getLastName().equalsIgnoreCase(lastName)) {
                return currCust;
            }
        }
        return null;
    }

    public void createAutoPurchaseInvoicesDir() {
        if (Files.exists(Paths.get("Auto-Purchase-Invoices"))) {
            logFile.save(logFile.time() + " Old Auto-Purchase-Invoices directory found and deleted\n");
            File directory = new File("Auto-Purchase-Invoices");
            File[] files = directory.listFiles();
            for (File currFile : files) {
                currFile.delete();
            }
            directory.delete();
        }
        File newDir = new File("Auto-Purchase-Invoices");
        newDir.mkdir();
        logFile.save(logFile.time() + " Auto-Purchase-Invoices directory created\n");
    }

    public void writeNewAutoPurchaseInvoices() {
        for (Customer currCust : this.customers.values()) {
            if (!currCust.getInvoiceList().isEmpty()) {
                currCust.invoiceSummary(currCust.getInvoiceList(), "Auto-Purchase-Invoices\\");
            }
        }
        logFile.save(logFile.time() + " All auto purchase invoices have been saved\n");
    }

    public void saveInvoiceForCustomer() {
        System.out.println("Enter the First and Last name of the user you want to save an invoice for");
        System.out.print("First Name\n--> ");
        String firstName = myScanner.nextLine();
        System.out.print("Last Name\n--> ");
        String lastName = myScanner.nextLine();
        Customer tempCustomer = findCustomer(firstName, lastName);
        if (tempCustomer == null) {
            logFile
                    .save(logFile.time() + " Admin entered the incorrect customer name of " + firstName + " " + lastName
                            + "\n");
            System.out.println("Incorrect customer name");
            return;
        }
        if (!tempCustomer.getInvoiceList().isEmpty()) {
            tempCustomer.invoiceSummary(tempCustomer.getInvoiceList(), "");
            logFile.save(logFile.time() + " Admin entered the correct customer name of " + firstName + " " + lastName
                    + " and saved an invoice\n");
            System.out.println("Invoice saved for customer " + firstName + " " + lastName);
        }
        logFile.save(logFile.time() + " Admin entered the correct customer name of " + firstName + " " + lastName
                + " but invoice list was empty\n");
        System.out.println("No invoice for customer " + firstName + " " + lastName);
    }

}