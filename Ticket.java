/**
 * @author Ian Gutierrez & Erik LaNeave
 *         Date: 10/21/2023
 *         Course: CS 3331 - Advanced Object Oriented Programming
 *         Instructor: Dr. Daniel Mejia
 *         Programming Assignment 3
 *         Lab Description: This program is an extension of PA2, with added tax
 *         and discount functionality. It is also merged with a teammate.
 *         Honesty Statement: I completed this work entirely on my own without
 *         any outside sources including peers, experts, online sources, or the
 *         like. The only assistance I received was from the instructor, TA, or
 *         IA.
 */

public class Ticket {

  /**
   * Class attributes
   */
  private String customerFName;
  private String customerLName;
  private String ticketType;

  /**
   * Constructor
   * 
   * @param fName      First name
   * @param lName      Last name
   * @param ticketType Ticket type
   */
  public Ticket(String fName, String lName, String ticketType) {
    this.customerFName = fName;
    this.customerLName = lName;
    this.ticketType = ticketType;

  }

  /**
   * First name getter
   * 
   * @return Customer first name
   */
  public String getCustomerFName() {
    return this.customerFName;
  }

  /**
   * First name setter
   * 
   * @param customerFName Customer first name
   */
  public void setCustomerFName(String customerFName) {
    this.customerFName = customerFName;
  }

  /**
   * Last name getter
   * 
   * @return Customer last name
   */
  public String getCustomerLName() {
    return this.customerLName;
  }

  /**
   * Last name setter
   * 
   * @param customerLName Customer last name
   */
  public void setCustomerLName(String customerLName) {
    this.customerLName = customerLName;
  }

  /**
   * Ticket type getter
   * 
   * @return Ticket type
   */
  public String getTicketType() {
    return this.ticketType;
  }

  /**
   * Ticket type setter
   * 
   * @param ticketType Ticket type
   */
  public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
  }
}
