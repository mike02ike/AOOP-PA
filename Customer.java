import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Ian Gutierrez
 * @author Erik LaNeave
 * @since 11/04/2023
 * @version 1.2
 *         Course: CS 3331 - Advanced Object Oriented Programming
 *         Instructor: Dr. Daniel Mejia
 *         Programming Assignment 3
 *         Lab Description: This program is an extension of PA2, with added tax
 *         and discount functionality. It is also merged with a teammate.
 *         Honesty Statement: I completed this work entirely on my own without
 *         any outside sources including peers, experts, online sources, or the
 *         like. The only assistance I received was from the instructor, TA, or
 *         IA.
 * <p>
 * @since 10/30/2023
 * @author Michael Ike
 * @version 1.1
 */

/*
 * The Customer class represents a customer in the TicketMiner system.
 */
public class Customer {

  /**
   * Class attributes
   */
  private int id;
  private String firstName;
  private String lastName;
  private double moneyAvailable;
  private int concertsPurchased;
  private boolean ticketMinerMember;
  private String username;
  private String password;
  private double membershipSavings;
  private ArrayList<Invoice> invoiceList = new ArrayList<Invoice>();

  /**
   * Constructor
   */
  public Customer() {
  }

  /**
   * Generates an electronic invoice summary for the customer's purchases and
   * saves it to a text file.
   *
   * @param invoiceList List of invoices to generate the summary from.
   */
  public void invoiceSummary(ArrayList<Invoice> invoiceList, String currentDir) {
    // Constructing the file path for the invoice summary
    String newFilePath = currentDir + this.firstName + this.lastName + "InvoiceSummary.txt";
    try {
      FileWriter fileWriter = new FileWriter(newFilePath);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      for (int i = 0; i < invoiceList.size(); i++) {
        Invoice currInvoice = invoiceList.get(i);
        // Write object properties to the text file
        bufferedWriter.write("-----------Invoice " + (i + 1) + "-----------\n");
        bufferedWriter.write("Event Type: " + currInvoice.getEventType() + "\n");
        bufferedWriter.write("Event Name: " + currInvoice.getEventName() + "\n");
        bufferedWriter.write("Event Date: " + currInvoice.getDate() + "\n");
        bufferedWriter.write("Ticket Type: " + currInvoice.getTicketType() + "\n");
        bufferedWriter.write("Number of Tickets: " + currInvoice.getNumTickets() + "\n");
        bufferedWriter.write("Total Price: " + currInvoice.getTotalPrice() + "\n");
        bufferedWriter.write("Confirmation Number: " + currInvoice.getConfirmationNum() + "\n");
        bufferedWriter.write("-------------------------------\n");
      }
      // Close the BufferedWriter to ensure data is written to the file
      bufferedWriter.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Adds an amount to the customers total member savings
   * 
   * @param savings Amount saved in one transaction.
   */
  public void addMembershipSavings(double savings) {
    this.membershipSavings += savings;
  }

  /**
   * Gets the amount the customer has saved
   * 
   * @return Total member savings
   */
  public double getMembershipSavings() {
    return this.membershipSavings;
  }

  /**
   * Adjusts a customers money after a purchase
   * 
   * @param purchaseAmount Total purchase price used to adjust customers money
   */
  public void adjustMoneyAvailable(double purchaseAmount) {
    this.moneyAvailable -= purchaseAmount;
  }

  /**
   * ID setter
   * 
   * @param idIn Customers ID
   */
  public void setId(int idIn) {
    this.id = idIn;
  }

  /**
   * ID getter
   * 
   * @return Customers ID
   */
  public int getId() {
    return this.id;
  }

  /**
   * First name getter
   * 
   * @return Customers first name
   */
  public String getFirstName() {
    return this.firstName;
  }

  /**
   * First name setter
   * 
   * @param firstNameIn Customers first name
   */
  public void setFirstName(String firstNameIn) {
    this.firstName = firstNameIn;
  }

  /**
   * Last name getter
   * 
   * @return Customers last name
   */
  public String getLastName() {
    return this.lastName;
  }

  /**
   * Last name setter
   * 
   * @param lastNameIn Customers last name
   */
  public void setLastName(String lastNameIn) {
    this.lastName = lastNameIn;
  }

  /**
   * Money available getter
   * 
   * @return Customers available money
   */
  public double getMoneyAvailable() {
    return this.moneyAvailable;
  }

  /**
   * Money available setter
   * 
   * @param moneyAvailableIn Customers available money
   */
  public void setMoneyAvailable(double moneyAvailableIn) {
    this.moneyAvailable = moneyAvailableIn;
  }

  /**
   * Concerts purchased getter
   * 
   * @return Number of concerts the customer has purchased
   */
  public int getConcertsPurchased() {
    return this.concertsPurchased;
  }

  /**
   * Concerts purchased setter
   * 
   * @param concertsPurchasedIn Number of concerts the customer has purchased
   */
  public void setConcertsPurchased(int concertsPurchasedIn) {
    this.concertsPurchased = concertsPurchasedIn;
  }

  /**
   * Ticket miner member getter
   * 
   * @return Whether a customer is a member
   */
  public boolean getTicketMinerMember() {
    return this.ticketMinerMember;
  }

  /**
   * Ticket miner member setter
   * 
   * @param ticketMinerMemberIn Whether a customer is a member
   */
  public void setTicketMinerMember(boolean ticketMinerMemberIn) {
    this.ticketMinerMember = ticketMinerMemberIn;
  }

  /**
   * Username getter
   * 
   * @return Customers username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * Username setter
   * 
   * @param usernameIn Customers username
   */
  public void setUsername(String usernameIn) {
    this.username = usernameIn;
  }

  /**
   * Password getter
   * 
   * @return Customers password
   */
  public String getPassword() {
    return this.password;
  }

  /**
   * Password setter
   * 
   * @param passwordIn Customers password
   */
  public void setPassword(String passwordIn) {
    this.password = passwordIn;
  }

  /**
   * @return invoiceList
   */
  public ArrayList<Invoice> getInvoiceList() {
    return this.invoiceList;
  }

  /**
   * @param invoiceList
   */
  public void setInvoiceList(ArrayList<Invoice> invoiceList) {
    this.invoiceList = invoiceList;
  }

  /**
   * Adds a new invoice the the arraylist of invoices
   * 
   * @param newInvoice
   */
  public void addInvoice(Invoice newInvoice) {
    this.invoiceList.add(newInvoice);
  }

}
