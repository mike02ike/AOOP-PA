/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: The UICustomer class handles the menu options for the
 * customer.
 * The log and invoices for the user are kept and updated inside of the UI.
 * <p>
 * 
 * @since 11/17/2023
 * @author Erik LaNeave
 * @author Ian Gutierrez
 * @version 2.7
 * 
 *          <p>
 * @since 10/27/2023
 * @author Michael Ike
 * @version 1.9
 */
public class UICustomer {

    private Scanner scnr = new Scanner(System.in);
    // Intialization of RecordMake class
    private Log logFile = Log.getInstance();
    // Keeps track of currCustomer
    private Customer currCustomer;
    // The map that contains all the events
    private LinkedHashMap<Integer, Event> eventMap;

    /**
     * Main menu for the customer UI
     * 
     * @param eventMap
     * @param currCustomerIn
     * @return Boolean back to RunTicket to stop main while loop
     */
    public void menu(LinkedHashMap<Integer, Event> eventMapIn, Customer currCustomerIn) {
        // Set the class attributes to the given eventMapIn and the currCustomerIn
        currCustomer = currCustomerIn;
        eventMap = eventMapIn;

        System.out.println("Hello " + currCustomerIn.getFirstName() + "\n");
        while (true) {
            // Prints the menu options and ask for user input as number
            System.out.print(
                    "Miner Menu\n1 - View Single Event Information\n2 - View All Events\n3 - Purchase Event Tickets\n4 - View Invoices\n5 - Save Invoices\n6 - Cancel Purchase\nor Type \"Exit\" to return to login\n--> ");
            // Only takes in strings to help with exceptions
            String input = scnr.nextLine().toLowerCase();
            // switch case used to cut down on possible exceptions and clean look
            switch (input) {
                case "1": // 1 - View Event Information
                    logFile.save(logFile.time() + " User picked menu option 1 to view event information\n");
                    singleEventPrintInfo();
                    break;
                case "2": // 2 - View all events
                    logFile.save(logFile.time() + " User picked menu option 2 to view all events\n");
                    viewAllEvents();
                    break;
                case "3": // 3 - Purchase Event Tickets
                    logFile.save(logFile.time() + " User picked menu option 3 to purchase event tickets\n");
                    purchaseTickets();
                    break;
                case "4": // 4 - View Invoices
                    logFile.save(logFile.time() + " User picked menu option 4 to view customer invoice\n");
                    printInvoices();
                    break;
                case "5": // 5 - Save Invoices
                    logFile.save(logFile.time() + " User picked menu option 5 to save invoices\n");
                    saveInvoice();
                    break;
                case "6": // 6 - Cancel purchase
                    logFile.save(logFile.time() + " User picked menu option 6 to cancel purchase\n");
                    cancelPurchase();
                    break;
                case "exit":
                    System.out.println("\nThank you " + currCustomer.getFirstName()
                            + " for using TicketMiner!\nReturning to login...\n");
                    logFile.save(logFile.time() + " User " + currCustomer.getFirstName() + " terminated the program\n");
                    // Stops while loop from running in RunTicket
                    return;
                default: // Used for incorrect input
                    // If user inputs an incorrect case
                    logFile.save(logFile.time() + " User input is an incorrect menu option\n");
                    System.out.println("Please enter a number between 1 and 6 or type \"Exit\".\n");
                    break;
            }
        }
    }

    /**
     * @return The event ID of a single event or -1 if event not found
     */
    public int getIdFromUser() {
        try {
            System.out.print("Enter the event ID\n--> ");
            String userInput = scnr.nextLine();
            int eventID = Integer.parseInt(userInput);
            return eventID;
        } catch (NumberFormatException e) {
            System.out.println("Not a vaild event ID\n");
            logFile.save(logFile.time() + " User input for event ID was not a number\n");
            return -1;
        }
    }

    /**
     * Prints the information for a single event
     */
    public void singleEventPrintInfo() {
        int eventID = getIdFromUser();
        if (eventID == -1) {
            System.out.println("Not a vaild event ID\n");
            logFile.save(logFile.time() + " User input for event ID was an incorrect Number\n");
            singleEventPrintInfo();
            return;
        }
        Event tempEvent = eventMap.get(eventID);
        // Checks that the key is in the map
        if (tempEvent == null) {
            System.out.println("Not a vaild event ID\n");
            logFile.save(logFile.time() + " User input was an incorrect event ID of " + eventID + "\n");
            singleEventPrintInfo();
            return;
        }
        viewEventInfo(tempEvent);
        return;
    }

    /**
     * Prints the event information for the user to see.
     * 
     * @param tempEvent
     */
    public void viewEventInfo(Event tempEvent) {
        logFile.save(logFile.time() + " Event with ID " + tempEvent.getId() + " information was viewed\n");
        System.out.println("\n-------EVENT INFORMATION-------\nEvent Name is: " + tempEvent.getName());
        tempEvent.eventBasicInfo();
        System.out.println("-------------------------\n");
        System.out.print("-------EVENT TICKET INFORMATION-------");
        displayEventTicketAmount(tempEvent);
        System.out.println("--------------------------------------\n");
    }

    /**
     * Prints all the event IDs and names
     */
    public void viewAllEvents() {
        for (Entry<Integer, Event> event : eventMap.entrySet()) {
            Event currEvent = event.getValue();
            System.out.println("\n-------------------------");
            System.out.println(currEvent.getId() + " - " + currEvent.getName());
            System.out.println("-------------------------\n");
        }
    }

    /**
     * Calls purchase class and lets customer purchase tickets for a single event
     */
    public void purchaseTickets() {
        int eventID = getIdFromUser();
        if (eventID == -1) {
            System.out.println("Not a vaild event ID\n");
            logFile.save(logFile.time() + " User input for event ID was an incorrect Number\n");
            purchaseTickets();
            return;
        }
        Event tempEvent = eventMap.get(eventID);
        // Checks that the key is in the map
        if (tempEvent == null) {
            System.out.println("Not a vaild event ID\n");
            logFile.save(logFile.time() + " User input was an incorrect event ID of " + eventID + "\n");
            purchaseTickets();
            return;
        }
        logFile.save(
                logFile.time() + " User is trying to purchase tickets for event with ID " + tempEvent.getId() + "\n");
        Purchase completePurchase = new Purchase(tempEvent, currCustomer);
        completePurchase.purchaseTicketsCustomer();
        return;
    }

    /**
     * Displays event information such as ticket price, purchased, and available
     */
    public void displayEventTicketAmount(Event currentEvent) {
        Venue tempVenue = currentEvent.getVenue();
        double[] ticketsAva = tempVenue.getNumAvailableSeats();
        double[] ogTickets = tempVenue.getOriginalAvailableSeats();
        String amount = String.format(
                "\n-- Number of Tickets Available --\n|Vip: %.0f|Gold: %.0f|Silver %.0f|Bronze: %.0f|General Admission: %.0f|\n",
                ticketsAva[0], ticketsAva[1], ticketsAva[2], ticketsAva[3], ticketsAva[4]);
        String purchase = String.format(
                "-- Number of Tickets Purchased --\n|Vip: %.0f|Gold: %.0f|Silver %.0f|Bronze: %.0f|General Admission: %.0f|\n",
                ogTickets[0] - ticketsAva[0], ogTickets[1] - ticketsAva[1], ogTickets[2] - ticketsAva[2],
                ogTickets[3] - ticketsAva[3], ogTickets[4] - ticketsAva[4]);
        String price = String.format(
                "-- Ticket Prices --\n|Vip: $%.2f|Gold: $%.2f|Silver $%.2f|Bronze: $%.2f|General Admission: $%.2f|\n",
                currentEvent.getVipPrice(), currentEvent.getGoldPrice(), currentEvent.getSilverPrice(),
                currentEvent.getBronzePrice(), currentEvent.getGenAdmissionPrice());
        System.out.print(amount + purchase + price);
    }

    /**
     * Checks to see if the ArrayList is empty and if it is not then it will
     * run through the list and print the invoice information.
     */
    public void printInvoices() {
        ArrayList<Invoice> invoiceList = currCustomer.getInvoiceList();
        // checks if empty
        if (invoiceList.isEmpty()) {
            logFile.save(logFile.time() + " User tried to print invoice list but list is empty\n");
            System.out.println("No tickets have been purchased\n");
            return;
        }
        logFile.save(logFile.time() + " Invoice list printed to user to view\n");
        System.out.println("\n-------------Invoices-------------");
        // Runs through invoices and prints them
        for (int i = 0; i < invoiceList.size(); i++) {
            invoiceList.get(i).printInvoice();
        }
        System.out.print("\n");
    }

    /**
     * Saves the current invoices at a user might have to a text file.
     */
    public void saveInvoice() {
        if (currCustomer.getInvoiceList().isEmpty()) {
            System.out.println("No invoices have been made\n");
            logFile.save(logFile.time() + " User tried to save empty invoice list\n");
            return;
        }
        currCustomer.invoiceSummary(currCustomer.getInvoiceList(), "");
        System.out.println("Invoice list has been saved\n");
        logFile.save(logFile.time() + " Invoice list has been saved\n");
    }

    /**
     * Runs through the invoice list and finds the invoice with same confirmation
     * number
     * 
     * @param confirmationNumInput
     * @return found invoice or null
     */
    public Invoice findInvoice(int confirmationNumInput) {
        for (Invoice currInv : currCustomer.getInvoiceList()) {
            if (currInv.getConfirmationNum() == confirmationNumInput) {
                return currInv;
            }
        }
        return null;
    }

    /**
     * Ask user for use confirmation and will then cancel the ticket and give them
     * the money back
     */
    public void cancelPurchase() {
        if (currCustomer.getInvoiceList().isEmpty()) {
            logFile.save(logFile.time() + " User tried to cancel a purchase but has not invoices\n");
            System.out.println("No tickets have been purchased\n");
            return;
        }
        printInvoices();
        System.out.print("Enter the confirmation number of the purchase you would like to cancel or enter anything to go back to the menu\n--> ");
        String confirString = scnr.nextLine();
        try {
            int confirInt = Integer.parseInt(confirString);
            // Not done but will zero out total cost and change name
            Invoice currInv = findInvoice(confirInt);
            if (currInv == null) {
                System.out.println("Not a valid confirmation number\n");
                logFile.save(logFile.time() + " Given confirmation number was not correct\n");
                return;
            }
            if (currInv.getIsCanceled()) {
                System.out.println("Purchase has already been canceled\n");
                logFile.save(logFile.time() + " Purchase has already been canceled\n");
                return;
            }
            // updates customer money
            currCustomer.addMoney(
                    (currInv.getTotalPrice() - currInv.getConvenience() - currInv.getService() - currInv.getCharity()));
            // Updates the event
            int numTickets = currInv.getNumTickets();
            int ticketType = getTicketType(currInv.getTicketType());
            Event currentEvent = eventMap.get(currInv.getEventID());
            Venue currentVenue = currentEvent.getVenue();
            currentVenue.addSeats(ticketType, numTickets);
            currentEvent.updateSeatsAndRevenue();
            currentEvent.subTotalTax(currInv.getTax());
            // Zero outs parts of the invoice
            currInv.setTotalPrice(0.0);
            currInv.setTax(0.0);
            currInv.setEventName("(Purchase Canceled) " + currInv.getEventName());
            currInv.setIsCanceled(true);
            System.out.println(
                    "Purchase with confirmation number " + currInv.getConfirmationNum() + " Canceled\n");
            logFile.save(logFile.time() + " User canceled invoice with confirmation number " + confirInt + "\n");
            return;
        } catch (NumberFormatException e) {
            logFile.save(logFile.time() + " User input was not an integer\n");
            System.out.println("Not a vaild confimation number\n");
        }
    }

    /**
     * @param toBeForm
     * @return a format double that has two decimal points.
     */
    public String doubleForm(double toBeForm) {
        return String.format("%.2f", toBeForm);
    }

    /**
     * Returns the index of the ticket for use in ticket array
     * 
     * @param ticketType
     * @return
     */
    public int getTicketType(String ticketType) {
        switch (ticketType.toLowerCase()) {
            case "vip":
                return 0;
            case "gold":
                return 1;
            case "silver":
                return 2;
            case "bronze":
                return 3;
            case "general admission":
                return 4;
            default:
                return -1;
        }
    }
}
