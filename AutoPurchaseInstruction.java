/**
 * @author Ian Gutierrez
 *         Date: 11/5/2023
 *         Course: CS 3331 - Advanced Object Oriented Programming
 *         Instructor: Dr. Daniel Mejia
 *         Programming Assignment 4
 *         Lab Description: This program is an extension of PA3, with an added
 *         autopurchaser and admin functionality.
 *         Honesty Statement: I completed this work entirely on my own without
 *         any outside sources including peers, experts, online sources, or the
 *         like. The only assistance I received was from the instructor, TA, or
 *         IA.
 */

public class AutoPurchaseInstruction {

  /**
   * Class attributes
   */
  private String customerFName;
  private String customerLName;
  private String action;
  private int eventID;
  private String eventName;
  private int ticketQuantity;
  private String ticketType;

  /**
   * Default constructor
   */
  public AutoPurchaseInstruction() {

  }

  /**
   * Customer first name getter
   * 
   * @return Customer first name
   */
  public String getCustomerFName() {
    return this.customerFName;
  }

  /**
   * Customer first name setter
   * 
   * @param customerFName Customer first name
   */
  public void setCustomerFName(String customerFName) {
    this.customerFName = customerFName;
  }

  /**
   * Customer last name getter
   * 
   * @return Customer last name
   */
  public String getCustomerLName() {
    return this.customerLName;
  }

  /**
   * Customer last name setter
   * 
   * @param customerLName Customer last name
   */
  public void setCustomerLName(String customerLName) {
    this.customerLName = customerLName;
  }

  /**
   * Get auto purchase action
   * 
   * @return auto purchase action
   */
  public String getAction() {
    return this.action;
  }

  /**
   * Set auto purchase action
   * 
   * @param action auto purchase action
   */
  public void setAction(String action) {
    this.action = action;
  }

  /**
   * Event id getter
   * 
   * @return Event id
   */
  public int getEventID() {
    return this.eventID;
  }

  /**
   * Event id setter
   * 
   * @param eventID
   */
  public void setEventID(int eventID) {
    this.eventID = eventID;
  }

  /**
   * Event name getter
   * 
   * @return Event name
   */
  public String getEventName() {
    return this.eventName;
  }

  /**
   * Event name setter
   * 
   * @param eventName
   */
  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  /**
   * Ticket quantity getter
   * 
   * @return Ticket quantity
   */
  public int getTicketQuantity() {
    return this.ticketQuantity;
  }

  /**
   * Ticket quantity setter
   * 
   * @param ticketQuantity
   */
  public void setTicketQuantity(int ticketQuantity) {
    this.ticketQuantity = ticketQuantity;
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
   * @param ticketType
   */
  public void setTicketType(String ticketType) {
    this.ticketType = ticketType;
  }

}
