/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Course: Adv. Object-Oriented Programming
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: Handles the purchase process for both customer and admin
 * <p>
 * Last Change: 11/17/2023
 * @author Erik LaNeave
 * @version 2.5
 * <p>
 * @since 11/15/2023
 * @author Michael Ike
 * @version 2.4
 */

/**
 * Represents a Purchase made for an Event by a Customer.
 */
public class Purchase {

    // Attributes
    private Event currentEvent;
    private Customer currentCustomer;
    private String currentTicketName;
    private double currentTaxAmount;
    private int autoTicket;
    private Log logFile = Log.getInstance();
    private Scanner scnr = new Scanner(System.in);

    /**
     * Empty Constructor
     */
    public Purchase() {
    }

    /**
     * Constructor that takes an event and customer object.
     *
     * @param currentEvent    The Event to be associated with the purchase.
     * @param currentCustomer The Customer associated with the purchase.
     */
    public Purchase(Event currentEvent, Customer currentCustomer) {
        this.currentEvent = currentEvent;
        this.currentCustomer = currentCustomer;
    }

    /**
     * Initiates an automated purchase process for admin use.
     */
    public void autoPurchaseAdmin() {
        if (currentEvent.getIsCanceled()) {
            logFile.save(logFile.time() + " Attempted to purchase tickets for a canceled event");
            return;
        }
        // setCurrentTicketName(ticketName);
        double ticketPrice = ticketAmount();
        logFile.save(logFile.time() + " Starting auto purchase with customer " + currentCustomer.getFirstName() + "\n");

        // Try catch is used to catch possible string to int exception along with
        // exceptions made by me
        try {
            // Checks that event has tickets for the purchase throws CheckTicketAvaException
            ticketCheck(autoTicket);
            // Gets total cost of purchase
            Invoice newInvoice = getTotalCost(ticketPrice, autoTicket);
            // Checks the the user has funds for the purchase throws CustomerMoneyException
            moneyCheckAndUpdate(newInvoice.getTotalPrice());
            // Updates the amount of tickets in the event
            updateEvent(autoTicket);
            // Increases the purchased tickets in customer
            updateCust(autoTicket);
            // Creates a new invoice for the customer
            addInvoices(newInvoice);
            updateEventAndTicketMiner(newInvoice);
            logFile.save(logFile.time() + " Customer with name " + currentCustomer.getFirstName() + " purchased "
                    + autoTicket
                    + " tickets for event with ID " + currentEvent.getId() + " spending $"
                    + doubleForm(newInvoice.getTotalPrice())
                    + "\n");
            logFile.save(logFile.time() + " Customer with name " + currentCustomer.getFirstName() + " current funds: $"
                    + doubleForm(currentCustomer.getMoneyAvailable()) + "\n");
            logFile.save(logFile.time() + " Available ticket type " + currentTicketName + " for event ID "
                    + currentEvent.getId()
                    + " is now tickets\n");
            return;
        } catch (CheckTicketAvaException e) {
            // Catches when event does not have tickets for purchase
            logFile.save(logFile.time() + " Given ticket amount exceeds ticket type amount for event\n");
            logFile.save(logFile.time() + " Customer with ID " + currentCustomer.getId()
                    + " failed to purchase tickets for event with ID " + currentEvent.getId() + "\n");
        } catch (CustomerMoneyException e) {
            // Catches when customer does not have funds for purchase
            logFile.save(logFile.time() + " Given customer has inufficient funds for this purchase\n");
            logFile.save(logFile.time() + " Customer with ID " + currentCustomer.getId()
                    + " failed to purchase tickets for event with ID " + currentEvent.getId() + "\n");
        }
        return;
    }

    /**
     * Allows a customer to purchase tickets by inputting ticket details.
     */
    public void purchaseTicketsCustomer() {
        if (currentEvent.getIsCanceled()) {
            logFile.save(logFile.time() + " Attempted to purchase tickets for a canceled event");
            System.out.println("Event has been canceled\nTickets can't be purchased for this event\n");
            return;
        }
        System.out.println("\n-- Current funds $" + doubleForm(currentCustomer.getMoneyAvailable())
                + " --\n-- Event Name: " + currentEvent.getName() + " --");

        displayEventTicketAmount();
        double ticketPrice = getTicketCostAmount();
        logFile.save(logFile.time() + " User input was correct with ticket name of " + currentTicketName + "\n");

        // Try catch is used to catch possible string to int exception along with
        // exceptions made by me
        try {
            // Gets ticket amount and checks that input is between 1 and 6 throws
            // TicketInputException
            int inputInt = getTicketNumUser();
            // Checks that event has tickets for the purchase throws CheckTicketAvaException
            ticketCheck(inputInt);
            // Gets total cost of purchase
            Invoice newInvoice = getTotalCost(ticketPrice, inputInt);
            // Checks the the user has funds for the purchase throws CustomerMoneyException
            moneyCheckAndUpdate(newInvoice.getTotalPrice());
            // Updates the amount of tickets in the event
            updateEvent(inputInt);
            // Increases the purchased tickets in customer
            updateCust(inputInt);
            // Creates a new invoice for the customer
            addInvoices(newInvoice);
            updateEventAndTicketMiner(newInvoice);
            printNewInvoice();
            logFile.save(logFile.time() + " User with ID " + currentCustomer.getId() + " purchased " + inputInt
                    + " tickets for event with ID " + currentEvent.getId() + " spending $"
                    + doubleForm(newInvoice.getTotalPrice())
                    + "\n");
            logFile.save(logFile.time() + " User with ID " + currentCustomer.getId() + " current funds: $"
                    + doubleForm(currentCustomer.getMoneyAvailable()) + "\n");
            logFile.save(logFile.time() + " Available ticket type " + currentTicketName + " for event ID "
                    + currentEvent.getId()
                    + " is now tickets\n");
            System.out.println("Purchase was successful\n");
            return;
        } catch (CheckTicketAvaException e) {
            // Catches when event does not have tickets for purchase
            logFile.save(logFile.time() + " User input exceeds ticket type amount for event\n");
            logFile.save(logFile.time() + " User with ID " + currentCustomer.getId()
                    + " failed to purchase tickets for event with ID " + currentEvent.getId() + "\n");
            System.out.println("\n" + e.getMessage());
            if (checkIfUserExit()) {
                System.out.println();
                return;
            }
            System.out.println("Please go back through the purchase process and selcet a different ticket type");
            System.out.println("------------------------------------------------------------------------------");
            // If event doesn't have tickets then go back through menu
            purchaseTicketsCustomer();
        } catch (CustomerMoneyException e) {
            // Catches when customer does not have funds for purchase
            logFile.save(logFile.time() + " User has inufficient funds for this purchase\n");
            logFile.save(logFile.time() + " User with ID " + currentCustomer.getId()
                    + " failed to purchase tickets for event with ID " + currentEvent.getId() + "\n");
            System.out.println(
                    "\n" + e.getMessage() + "Current funds are at $" + doubleForm(currentCustomer.getMoneyAvailable()));
            if (checkIfUserExit()) {
                System.out.println();
                return;
            }
            System.out.println(
                    "Please go back through the purchase process and selcet a different ticket type or lower your ticket amount");
            System.out.println(
                    "----------------------------------------------------------------------------------------------------------");
            // If event doesn't have tickets then go back through menu
            purchaseTicketsCustomer();
        }
        return;
    }

    /**
     * Finds the ticket price
     * 
     * @return returns the ticketPrice
     */
    public double getTicketCostAmount() {
        System.out.print("Enter the name of the tickets you would like to purchase\n--> ");
        currentTicketName = scnr.nextLine();
        double ticketPrice = ticketAmount();
        // if ticketamount returns -1 then ticket price was not found
        if (ticketPrice == -1.0) {
            logFile.save(
                    logFile.time() + " User input was an incorrect with ticket name of " + currentTicketName + "\n");
            System.out.println("Ticket of that name does not exist\nPlease enter a valid ticket name\n");
            // ask the user to enter the ticket amount again
            return getTicketCostAmount();
        }
        return ticketPrice;
    }

    /**
     * Ask the user to enter the amounts of ticket they want to purchase and throws
     * errors
     * 
     * @return number of tickets from user
     */
    public int getTicketNumUser() {
        try {
            System.out.print(
                    "\nEnter the amount of tickets you would like to purchase\nLimit of 6 tickets per-transaction\n--> ");
            String userInput = scnr.nextLine();
            int inputInt = Integer.valueOf(userInput);
            ticketAmountCheck(inputInt);
            return inputInt;
        } catch (TicketInputException e) {
            logFile.save(logFile.time() + " Ticket input amount was not in range from 1 to 6\n");
            System.out.println(e.getMessage());
            // Ask user to enter ticket to be purchased again
            return getTicketNumUser();
        } catch (NumberFormatException e) {
            // Catches the String to int exception
            logFile.save(logFile.time() + " User with ID " + currentCustomer.getId()
                    + " failed to enter an integer for ticket amount\n");
            System.out.println("Please enter an integer for ticket amount");
            // Ask user to enter ticket to be purchased again
            return getTicketNumUser();
        }
    }

    /**
     * Asks the user if they want to exit the purchase method
     * 
     * @return retuns true or false depending on user input
     */
    public boolean checkIfUserExit() {
        System.out.println(
                "Would you like to cancel the purchase or try again?\nEnter exit to cancel the order and anything else to try again.");
        System.out.print("--> ");
        String userInput = scnr.nextLine();
        switch (userInput.toLowerCase()) {
            case "exit":
                logFile.save(logFile.time() + " User decided to cancel the purchase\n");
                return true;
            default:
                logFile.save(logFile.time() + " User decided to not cancel the purhcase\n");
                return false;
        }
    }

    /**
     * Displays event information such as ticket price, purchased, and available
     */
    public void displayEventTicketAmount() {
        Venue tempVenue = currentEvent.getVenue();
        double[] ticketsAva = tempVenue.getNumAvailableSeats();
        double[] ogTickets = tempVenue.getOriginalAvailableSeats();
        String amount = String.format(
                "\n-- Number of Tickets Available --\n|Vip: %.0f|Gold: %.0f|Silver: %.0f|Bronze: %.0f|General Admission: %.0f|\n",
                ticketsAva[0], ticketsAva[1], ticketsAva[2], ticketsAva[3], ticketsAva[4]);
        String purchase = String.format(
                "-- Number of Tickets Purchased --\n|Vip: %.0f|Gold: %.0f|Silver: %.0f|Bronze: %.0f|General Admission: %.0f|\n",
                ogTickets[0] - ticketsAva[0], ogTickets[1] - ticketsAva[1], ogTickets[2] - ticketsAva[2],
                ogTickets[3] - ticketsAva[3], ogTickets[4] - ticketsAva[4]);
        String price = String.format(
                "-- Ticket Prices --\n|Vip: $%.2f|Gold: $%.2f|Silver: $%.2f|Bronze: $%.2f|General Admission: $%.2f|\n",
                currentEvent.getVipPrice(), currentEvent.getGoldPrice(), currentEvent.getSilverPrice(),
                currentEvent.getBronzePrice(), currentEvent.getGenAdmissionPrice());
        System.out.print(amount + purchase + price + "\n");
    }

    /**
     * Takes in the ticket name and finds the price
     * 
     * @return returns the price of the ticket or -1 if incorrect name
     */
    public double ticketAmount() {
        switch (currentTicketName.toLowerCase()) {
            case "vip":
                return currentEvent.getVipPrice();
            case "gold":
                return currentEvent.getGoldPrice();
            case "silver":
                return currentEvent.getSilverPrice();
            case "bronze":
                return currentEvent.getBronzePrice();
            case "general admission":
                return currentEvent.getGenAdmissionPrice();
            default:
                return -1.0;
        }
    }

    /**
     * Used to check user input for ticket amount
     * 
     * @param inputInt
     * @throws TicketInputException
     */
    public void ticketAmountCheck(int inputInt) throws TicketInputException {
        if (inputInt >= 1 && inputInt <= 6) {
            return;
        }
        throw new TicketInputException();
    }

    /**
     * Takes the currentTicketName and find the index for the array
     * 
     * @param currentTicketName
     * @return returns the ticket number for the array in venue
     */
    public int findTicketNum() {
        switch (currentTicketName.toLowerCase()) {
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

    /**
     * Checks that the event has tickets equal to the amount that user wants to
     * purchase
     * 
     * @throws CheckTicketAvaException
     */
    public void ticketCheck(int userTicketAmount) throws CheckTicketAvaException {
        double[] ticketAv = currentEvent.getVenue().getNumAvailableSeats();
        int ticketNumValue = findTicketNum();
        // Checks the ticket amount and returns if the ticket name is not valid
        if (ticketNumValue != -1 && ticketAv[ticketNumValue] >= userTicketAmount && userTicketAmount >= 1) {
            return;
        }
        throw new CheckTicketAvaException();
    }

    /**
     * @param totalCost
     * @throws CustomerMoneyException
     */
    public void moneyCheckAndUpdate(double totalCost) throws CustomerMoneyException {
        if (totalCost <= currentCustomer.getMoneyAvailable()) {
            // Updates the current customer money
            currentCustomer.adjustMoneyAvailable(totalCost);
            return;
        }
        throw new CustomerMoneyException();
    }

    /**
     * Finds the total cost of the purchase and handles discounts and tax
     * 
     * @param ticketPrice
     * @param ticketAmount
     * @return newInvoice with all needed values
     */
    public Invoice getTotalCost(double ticketPrice, int ticketAmount) {
        TicketMiner ourCompany = TicketMiner.getInstance();
        double totalWithOutFees = (ticketPrice * ticketAmount);
        double convenienceFees = ourCompany.getConvenienceFee();
        double serviceFees = ourCompany.getServiceFee(ticketAmount, ticketPrice);
        double charityFees = ourCompany.getCharityFee(ticketAmount, ticketPrice);
        double subTotalFees = totalWithOutFees + convenienceFees + serviceFees + charityFees;

        // TicketMiner Member Discount
        if (currentCustomer.getTicketMinerMember()) {
            double discountTotal = subTotalFees * .10;
            currentCustomer.addMembershipSavings(discountTotal);
            currentEvent.addTotalMemberDiscount(discountTotal);

            subTotalFees -= discountTotal;
        }

        currentTaxAmount = subTotalFees * 0.0825;
        currentEvent.addTotalTax(currentTaxAmount);
        double subTotalWithTaxAndFees = Math.floor(100 * (subTotalFees + currentTaxAmount)) / 100;

        Invoice newInvoice = createInvoice(ticketAmount, subTotalWithTaxAndFees, convenienceFees, serviceFees, charityFees);

        return newInvoice;
    }

    /**
     * Updates the event information based on the number of tickets purchased and
     * ticket names.
     * 
     * @param numOfTickets
     */
    public void updateEvent(int numOfTickets) {
        int ticketNumValue = findTicketNum();
        currentEvent.getVenue().adjustSeatAvailability(ticketNumValue + 1, numOfTickets);
        currentEvent.updateSeatsAndRevenue();
    }

    /**
     * Updates the customers purchased fields with each new purchase.
     * 
     * @param tickets
     */
    public void updateCust(int tickets) {
        currentCustomer.setConcertsPurchased(currentCustomer.getConcertsPurchased() + tickets);
    }

    /**
     * Creates an arraylist of tickets that is stored in the invoice
     * 
     * @param userTicketAmount
     * @return arraylist of ticket ourCompanyects
     */
    public ArrayList<Ticket> makeTicketListInvoice(int userTicketAmount) {
        ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

        for (int i = 0; i < userTicketAmount; i++) {
            Ticket tempTicket = new Ticket(currentCustomer.getFirstName(), currentCustomer.getLastName(),
                    currentTicketName);
            ticketList.add(tempTicket);
        }

        return ticketList;
    }

    /**
     * @param toBeForm
     * @return a format double that has two decimal points.
     */
    public String doubleForm(double toBeForm) {
        return String.format("%.2f", toBeForm);
    }

    /**
     * Creates an invoice for the purchase that the customer made
     * 
     * @param userTicketAmount
     * @param totalCostWithTax
     */
    public Invoice createInvoice(int userTicketAmount, double totalCostWithTax, double convenience, double service, double charity) {
        ArrayList<Ticket> ticketList = makeTicketListInvoice(userTicketAmount);
        Invoice newInvoice = new Invoice(this.currentCustomer.getId(), userTicketAmount, totalCostWithTax,
                currentTaxAmount,
                currentEvent.getId(), currentEvent.getName(), currentTicketName, currentEvent.getEventType(),
                currentEvent.getDate(),
                convenience, service, charity, ticketList);
        logFile.save(logFile.time() + " Invoice with confimation number " + newInvoice.getConfirmationNum()
                + " created for user purchase\n");
        return newInvoice;
    }

    /**
     * Adds new invoice to the current customer
     * 
     * @param newInvoice
     */
    public void addInvoices(Invoice newInvoice) {
        currentCustomer.addInvoice(newInvoice);
        currentEvent.addInvoice(newInvoice);
    }

    /**
     * Updates the ticketminer and event with all fees from purchase
     * 
     * @param currentInvoice
     */
    public void updateEventAndTicketMiner(Invoice currentInvoice) {
        TicketMiner ourCompany = TicketMiner.getInstance();
        currentEvent.updateConvenienceFees(currentInvoice.getConvenience());
        currentEvent.updateServiceFees(currentInvoice.getService());
        currentEvent.updateCharityFees(currentInvoice.getCharity());
        ourCompany.updateConvenienceFees(currentEvent.getId());
        ourCompany.updateServiceFees(currentEvent.getId(), currentInvoice.getService());
        ourCompany.updateCharityFees(currentEvent.getId(), currentInvoice.getCharity());
    }

    /**
     * Prints the most recently added user invoice.
     */
    public void printNewInvoice() {
        ArrayList<Invoice> invoiceList = currentCustomer.getInvoiceList();
        Invoice newInvoice = invoiceList.get(invoiceList.size() - 1);
        // The new invoice is printed to the user
        System.out.println("\n ---------CREATED INVOICE---------");
        newInvoice.printInvoice();
    }

    /**
     * @return currentCustomer
     */
    public Event getCurrentEvent() {
        return this.currentEvent;
    }

    /**
     * @param currentEvent
     */
    public void setCurrentEvent(Event currentEvent) {
        this.currentEvent = currentEvent;
    }

    /**
     * @return currentCustomer
     */
    public Customer getCurrentCustomer() {
        return this.currentCustomer;
    }

    /**
     * @param currentCustomer
     */
    public void setCurrentCustomer(Customer currentCustomer) {
        this.currentCustomer = currentCustomer;
    }

    /**
     * @return currentTicketName
     */
    public String getCurrentTicketName() {
        return this.currentTicketName;
    }

    /**
     * @param currentTicketName
     */
    public void setCurrentTicketName(String currentTicketName) {
        this.currentTicketName = currentTicketName;
    }

    /**
     * @return
     */
    public double getCurrentTaxAmount() {
        return this.currentTaxAmount;
    }

    /**
     * @param currentTaxAmount
     */
    public void setCurrentTaxAmount(double currentTaxAmount) {
        this.currentTaxAmount = currentTaxAmount;
    }

    /**
     * @return
     */
    public int getAutoTicket() {
        return this.autoTicket;
    }

    /**
     * @param autoTicket
     */
    public void setAutoTicket(int autoTicket) {
        this.autoTicket = autoTicket;
    }

}
