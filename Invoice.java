/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.ArrayList;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: The Invoice class is used to create invoices which are stored
 * in UI
 * using a ArrayList of Invoices.
 * <p>
 * 
 * @since 11/16/2023
 * @author Erik LaNeave
 * @version 2.1
 *          <p>
 * @since 10/28/2023
 * @author Michael Ike
 * @version 1.8
 */

public class Invoice {

    // Attributes
    private Integer customerID;
    private Integer eventID;
    private Integer numTickets;
    private int confirmationNum;
    private Double totalPrice;
    private Double tax;
    private String eventName;
    private String ticketType;
    private String eventType;
    private String date;
    private Double convenience;
    private Double service;
    private Double charity;
    private ArrayList<Ticket> ticketList = new ArrayList<Ticket>();

    // Constructors
    /**
     * Empty constructor
     */
    public Invoice() {
    }

    /**
     * @param numTickets
     * @param totalPrice
     * @param tax
     * @param eventID
     * @param eventName
     * @param ticketType
     * @param eventType
     * @param date
     * @param newTicketList
     */
    public Invoice(int customerID, int numTickets, double totalPrice, double tax, int eventID, String eventName,
            String ticketType,
            String eventType, String date, double convenience, double service, double charity,
            ArrayList<Ticket> newTicketList) {
        this.customerID = customerID;
        this.numTickets = numTickets;
        this.totalPrice = totalPrice;
        this.tax = tax;
        this.eventID = eventID;
        this.eventName = eventName;
        this.ticketType = ticketType;
        this.eventType = eventType;
        this.date = date;
        this.convenience = convenience;
        this.service = service;
        this.charity = charity;
        this.ticketList = newTicketList;
        this.confirmationNum = hashCode();
    }

    // Methods

    /**
     * Prints the invoice object for the user
     */
    public void printInvoice() {
        System.out.printf("Event Name: %s\n", getEventName());
        System.out.printf("Event ID: %d\n", getEventID());
        System.out.printf("Ticket Type: %s\n", getTicketType());
        System.out.printf("Number of tickets bought: %d\n", getNumTickets());
        System.out.printf("Total cost of order: $%.2f\n", getTotalPrice());
        System.out.printf("Total fees of order: $%.2f\n", (getCharity() + getConvenience() + getService()));
        System.out.printf("Total tax of order was: $%.2f\n", getTax());
        System.out.printf("Confirmation Number: %d\n", getConfirmationNum());
        System.out.printf("----------------------------------\n");
    }

    /**
     * Creates a hashCode from the attributes and is used as the confirmation code
     * for the invoices
     */
    public int hashCode() {
        return (int) numTickets.hashCode() + totalPrice.hashCode() + tax.hashCode() + eventID.hashCode()
                + eventName.hashCode() + ticketType.hashCode() + eventType.hashCode() + date.hashCode()
                + ticketList.hashCode() +
                convenience.hashCode() + service.hashCode() + charity.hashCode();
    }

    /**
     * @return numTickets
     */
    public int getNumTickets() {
        return this.numTickets;
    }

    /**
     * @param numTickets
     */
    public void setNumTickets(int numTickets) {
        this.numTickets = numTickets;
    }

    /**
     * @return totalPrice
     */
    public double getTotalPrice() {
        return this.totalPrice;
    }

    /**
     * @param totalPrice
     */
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * @return tax
     */
    public Double getTax() {
        return this.tax;
    }

    /**
     * @param tax
     */
    public void setTax(Double tax) {
        this.tax = tax;
    }

    /**
     * @return ConfirmationNum
     */
    public int getConfirmationNum() {
        return this.confirmationNum;
    }

    /**
     * @param confirmationNum
     */
    public void setConfirmationNum(int confirmationNum) {
        this.confirmationNum = confirmationNum;
    }

    /**
     * @return eventID
     */
    public int getEventID() {
        return this.eventID;
    }

    /**
     * @param eventID
     */
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }

    /**
     * @return eventName
     */
    public String getEventName() {
        return this.eventName;
    }

    /**
     * @param eventName
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * @return ticketType
     */
    public String getTicketType() {
        return this.ticketType;
    }

    /**
     * @param ticketType
     */
    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    /**
     * @return the ticketlist
     */
    public ArrayList<Ticket> getTicketList() {
        return this.ticketList;
    }

    /**
     * @param ticketList
     */
    public void setTicketList(ArrayList<Ticket> ticketList) {
        this.ticketList = ticketList;
    }

    /**
     * @return eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    /**
     * @return Date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return convenience fee
     */
    public Double getConvenience() {
        return this.convenience;
    }

    /**
     * @param convenience
     */
    public void setConvenience(Double convenience) {
        this.convenience = convenience;
    }

    /**
     * @return service fee
     */
    public Double getService() {
        return this.service;
    }

    /**
     * @param service
     */
    public void setService(Double service) {
        this.service = service;
    }

    /**
     * @return charity fee
     */
    public Double getCharity() {
        return this.charity;
    }

    /**
     * @param charity
     */
    public void setCharity(Double charity) {
        this.charity = charity;
    }

    /**
     * @param id
     */
    public void setCustomerID(int id) {
        this.customerID = id;
    }

    /**
     * @return customer id
     */
    public int getCustomerID() {
        return this.customerID;
    }
}