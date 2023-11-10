/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.InputMismatchException;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
 * @since 11/09/2023
 * @author Anaiah Quinn
 * @version 2.5
 *        <p>
 * 
 * @since 11/06/2023
 * @author Erik LaNeave
 * @version 2.4
 *          <p>
 * @since 10/25/2023
 * @author Michael Ike
 * @version 1.8
 * 
 * @author Ian Gutierrez
 */

public class UIAdminCopy {

  private Scanner myScanner = new Scanner(System.in);
  // Intialization of RecordMake class
  private Log logFile = Log.getInstance();

  /**
   * Empty Constructor
   */
  public UIAdminCopy() {
  }

  /**
   * Displays the main menu for the admin UI and handles user input.
   *
   * @param eventMap A LinkedHashMap containing events mapped by their unique IDs.
   * @return boolean Returns `false` to indicate completion and return to the
   *         RunTicket class.
   */
  public boolean menu(LinkedHashMap<Integer, Event> eventMap, LinkedHashMap<Integer, Customer> customerMap) {
    boolean control = true;
    while (control) {
      // Prints the menu options and ask for user input as option
      System.out.print(
          "\nAdministrator Menu:\n1 - Inquire Event by ID\n2 - Inquire Event by Name\n3 - Create New Event\n4 - Run Auto Purchase\n5 - Save an Invoice for a Customer\n 6- Print all Event Fees\nEnter \"Exit\" to exit the Program\n--> ");
      // Only takes in strings to help with exceptions
      String inputUser = myScanner.nextLine();
      // switch case used to cut down on possible exceptions and clean look
      switch (inputUser.toLowerCase()) {
        case "1": // 1 - inquire event by ID
          logFile.save(logFile.time() + " Admin picked menu option 1 to Inquire Event by ID\n");
          inquireEventByID(eventMap);
          break;
        case "2": // 2 - inquire event by name
          logFile.save(logFile.time() + " Admin picked menu option 2 to Inquire Event by Name\n");
          inquireEventByName(eventMap);
          break;
        case "3": // 3 - Create a new event
          logFile.save(logFile.time() + " Admin picked menu option 3 to create an Event\n");
          createEvent(eventMap);
          break;
        case "4": // 4 - Auto purchase for customers
          logFile.save(logFile.time() + " Admin picked menu option 4 to run the auto purchase\n");
          autoPurchase(customerMap, eventMap);
          break;
        case "5": // 5 - Save invoice for customer
          logFile.save(logFile.time() + " Admin picked menu option 5 to save an invoice for a user\n");
          saveInvoiceForCustomer(customerMap);
          break;
        case "6"://6 - Print Fees for all Events
          logFile.save(logFile.time() + "Admin picked menu option 6 to print all event fees");
          //call method to implent
          break;
        case "exit":
          System.out.println("\nThank you for using TicketMiner!\nTerminating program...");
          logFile.save(logFile.time() + " Admin terminated the program\n");
          // Stops while loop from running in RunTicket
          control = false;
          break;
        default: // Used for incorrect input
          logFile.save(logFile.time() + " Admin inputted an invalid menu option\n");
          System.out.println("Invalid input. Please enter a number 1 - 4");
          break;
      }// end switch
    }
    return false;
  }

  /**
   * Allows an admin to create a new event
   * 
   * @param eventMap hashmap containing all events
   * @return updated hashmap
   */
  public LinkedHashMap<Integer, Event> createEvent(LinkedHashMap<Integer, Event> eventMap) {
    Scanner scn = new Scanner(System.in);

    Event newEvent = createBlankEvent();
    newEvent.setVenue(createBlankVenue());

    newEvent.setId(calculateEventID(eventMap));

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

    eventMap.put(newEvent.getId(), newEvent);
    System.out.println("\nEvent added!");

    return eventMap;

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
  public int calculateEventID(LinkedHashMap<Integer, Event> eventMap) {
    int largestID = Integer.MIN_VALUE;
    for (Entry<Integer, Event> event : eventMap.entrySet()) {
      int currID = event.getKey();

      if (currID > largestID) {
        largestID = currID;
      }
    }

    return largestID + 1;

  }

  /**
   * Allows the admin to inquire about an event by its ID.
   *
   * @param eventMap A LinkedHashMap containing events mapped by their unique IDs.
   */
  public void inquireEventByID(LinkedHashMap<Integer, Event> eventMap) {
    while (true) {
      try {
        System.out.print("Enter the Event ID\n--> ");
        String userInput = myScanner.nextLine();
        int eventID = Integer.parseInt(userInput);
        // Uses .get from the map to find the event based of ID
        Event currEvent = eventMap.get(eventID);
        // Checks that the event was found and does not give null
        if (currEvent == null) {
          logFile.save(logFile.time() + " Admin entered an invalid event ID of " + eventID + "\n");
          System.out.println("Not a valid Event ID");
          continue;
        }
        logFile.save(logFile.time() + " Admin entered a correct event ID of " + eventID + "\n");
        subMenu(currEvent);
        break;
      } catch (NumberFormatException e) {
        logFile.save(logFile.time() + " Admin did not enter a number for the event ID\n");
        System.out.println("Event not found. Enter a valid Event ID");
        continue;
      }
    }
  }

  /**
   * Allows the admin to inquire about an event by its name.
   *
   * @param eventMap A LinkedHashMap containing events mapped by their unique IDs.
   */
  public void inquireEventByName(LinkedHashMap<Integer, Event> eventMap) {
    while (true) {
      System.out.print("Enter the Event Name\n--> ");
      String input = myScanner.nextLine();
      Event currEvent = null;
      // Runs through all the events in the map and checks the name
      for (Event curr : eventMap.values()) {
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
      subMenu(currEvent);
      break;
    }
  }

  /**
   * Displays a submenu for the admin to access additional information about an
   * event.
   *
   * @param currEvent The event for which additional information is displayed.
   */
  public void subMenu(Event currEvent) {
    // Displays standard event and then ask if they want to see the calculated
    // information
    System.out.println("\nStandard Event Information");
    System.out.println("================================");
    currEvent.eventBasicInfo();
    System.out.println("================================\n");
    while (true) {
      System.out.print("Submenu for Additional Information\n");
      System.out.print("1 - View Event Calculated Info\n2 - Print Event Fees\n3 - Exit Submenu\n--> ");
      String input = myScanner.nextLine();
      switch (input) {
        case "1": // Views calculated event info
          logFile.save(logFile.time()
              + " Admin picked submenu option 1 to view event calculated information\n");
          System.out.println("================================\nAvailable Seats");
          currEvent.adminPrintAvailableSeats();
          System.out.println("Total Revenue");
          currEvent.adminPrintRevenueInfo();
          break;
        case "2"://Prints total fees for event
          logFile.save(logFile.time() + "Admin picked submenu option 2 to print event fees\n");
          //implimentation to be determined
          break;
        case "3": // Exit subMenu
          logFile.save(logFile.time() + " Admin picked submenu option 2 to exit submenu\n");
          System.out.println("Exiting sub menu...");
          return;
        default: // Handles incorrect input
          logFile.save(logFile.time() + " Admin input is invalid option for submenu\n");
          System.out.println("Please enter a number between 1 and 2");
          break;
      }
    }
  }

  /**
   * Does the auto purchase based of the given csv file and completes the purchase
   * from the user.
   * 
   * @param customerMap
   * @param eventMap
   */
  public void autoPurchase(LinkedHashMap<Integer, Customer> customerMap, LinkedHashMap<Integer, Event> eventMap) {
    Purchase completPurchase = new Purchase();
    ArrayList<AutoPurchaseInstruction> autoPurchases = getAutoPurchaseInfo();
    if (autoPurchases == null) {
      return;
    }
    logFile.save(logFile.time() + " AutoPurchse CSV read\n");
    for (AutoPurchaseInstruction currAuto : autoPurchases) {
      Customer currCustomer = findCustomer(currAuto.getCustomerFName(), currAuto.getCustomerLName(), customerMap);
      completPurchase.setCurrentCustomer(currCustomer);
      completPurchase.setCurrentEvent(eventMap.get(currAuto.getEventID()));
      completPurchase.autoPurchaseAdmin(currAuto.getTicketQuantity(), currAuto.getTicketType());
    }
    createAutoPurchaseInvoicesDir();
    writeNewAutoPurchaseInvoices(customerMap);
    logFile.save(logFile.time() + " Auto purchase complete and all invoices saved\n");
    System.out.println("Auto purchase complete and Invoices have been saved");
  }

  /**
   * Reads the given csv file and returns information
   * 
   * @return Returns an arraylist filled with all the auto purchase objects.
   */
  public ArrayList<AutoPurchaseInstruction> getAutoPurchaseInfo() {
    try {
      AutoPurchaseReader autoPurchaseReader = new AutoPurchaseReader();
      ArrayList<AutoPurchaseInstruction> autoPurchases = new ArrayList<>();
      autoPurchases = autoPurchaseReader.readFile("AutoPurchase100.csv");
      return autoPurchases;
    } catch (FileNotFoundException e) {
      System.out.println("\nAuto Purchase file can't be found");
      return null;
    }
  }

  /**
   * Takes given customer first and last name and returns that customer object.
   * 
   * @param firstName
   * @param lastName
   * @param customerMap
   * @return currentCustomer that was found from the auto-purchase
   */
  public Customer findCustomer(String firstName, String lastName, LinkedHashMap<Integer, Customer> customerMap) {
    for (Customer currCust : customerMap.values()) {
      if (currCust.getFirstName().equalsIgnoreCase(firstName) && currCust.getLastName().equalsIgnoreCase(lastName)) {
        return currCust;
      }
    }
    return null;
  }

  /**
   * Creates the Auto-Purchase-Invoices directory. If the dir exists then it is
   * deleted and remade.
   */
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

  /**
   * Runs through the customer list and creates the invoices
   * 
   * @param customerMap
   */
  public void writeNewAutoPurchaseInvoices(LinkedHashMap<Integer, Customer> customerMap) {
    for (Customer currCust : customerMap.values()) {
      if (!currCust.getInvoiceList().isEmpty()) {
        currCust.invoiceSummary(currCust.getInvoiceList(), "Auto-Purchase-Invoices\\");
      }
    }
    logFile.save(logFile.time() + " All auto purchase invoices have been saved\n");
  }

  /**
   * Searchs for customer and then saves that customers invoices
   */
  public void saveInvoiceForCustomer(LinkedHashMap<Integer, Customer> customerMap) {
    System.out.println("Enter the First and Last name of the user you want to save an invoice for");
    System.out.print("First Name\n--> ");
    String firstName = myScanner.nextLine();
    System.out.print("Last Name\n--> ");
    String lastName = myScanner.nextLine();
    Customer tempCustomer = findCustomer(firstName, lastName, customerMap);
    if (tempCustomer == null) {
      logFile
          .save(logFile.time() + " Admin entered the incorrect customer name of " + firstName + " " + lastName + "\n");
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
