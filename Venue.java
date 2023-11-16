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

public abstract class Venue {

  /**
   * Class attributes
   */
  private String name;
  private String venueType;
  private double cost;
  private int capacity;
  private double vipPercentage; // Percentage of vip seats available
  private double goldPercentage; // Percentage of gold seats available
  private double silverPercentage; // Percentage of silver seats available
  private double bronzePercentage; // Percentage of bronze seats available
  private double genAdmissionPercentage; // Percentage of general admission seats available
  private double reservedExtraPercentage; // Percentage of reserved extra seats available
  private double unavailableSeatPercentage; // Percentage of unavailable seats
  private double[] numAvailableSeats = new double[6]; // Array that holds the number of available seats for each type
  private double[] orginalAvailableSeats = new double[6]; // Array that holds original seat number

  public Venue() {

  }

  /**
   * This method calculates the number of seats for each type of ticket using the
   * percentages and capacity given
   */
  public void calculateNumSeats() {

    // [0] VIP , [1] Gold, [2] Silver, [3] Bronze, [4] GenAdmin, [5] RerservedExtra
    double[] calculations = { ((this.vipPercentage / 100) * this.capacity),
        ((this.goldPercentage / 100) * this.capacity), ((this.silverPercentage / 100) * this.capacity),
        ((this.bronzePercentage / 100) * this.capacity),
        ((this.genAdmissionPercentage / 100) * this.capacity),
        ((this.reservedExtraPercentage / 100) * this.capacity) };

    this.numAvailableSeats = calculations;
    this.orginalAvailableSeats = this.numAvailableSeats.clone();
  }

  /**
   * Adjusts the number of seats according to the ticket type
   * 
   * @param ticketType   Type of ticket
   * @param numOfTickets Num of tickets
   */
  public void adjustSeatAvailability(int ticketType, int numOfTickets) {
    this.numAvailableSeats[ticketType - 1] -= numOfTickets;
  }

  public void addSeats(int ticketType, int numOfTickets) {
    this.numAvailableSeats[ticketType - 1] += numOfTickets;
  }

  /**
   * Name getter
   * 
   * @return Venue name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Name setter
   * 
   * @param nameIn Name of venue
   */
  public void setName(String nameIn) {
    this.name = nameIn;
  }

  /**
   * Available seats getter
   * 
   * @return Available seats
   */
  public double[] getNumAvailableSeats() {
    return this.numAvailableSeats;
  }

  /**
   * Original seats getter
   * 
   * @return Orginal number of available seats
   */
  public double[] getOriginalAvailableSeats() {
    return this.orginalAvailableSeats;
  }

  /**
   * Venue type getter
   * 
   * @return Venue type
   */
  public String getVenueType() {
    return this.venueType;
  }

  /**
   * Venue type setter
   * 
   * @param venueTypeIn Type of venue
   */
  public void setVenueType(String venueTypeIn) {
    this.venueType = venueTypeIn;
  }

  /**
   * Cost getter
   * 
   * @return Cost of venue
   */
  public double getCost() {
    return this.cost;
  }

  /**
   * Cost setter
   * 
   * @param costIn Cost of venue
   */
  public void setCost(double costIn) {
    this.cost = costIn;
  }

  /**
   * Capacity getter
   * 
   * @return Capacity of venue
   */
  public int getCapacity() {
    return this.capacity;
  }

  /**
   * Capacity setter
   * 
   * @param capacityIn Capacity of venue
   */
  public void setCapacity(int capacityIn) {
    this.capacity = capacityIn;
  }

  /**
   * Vip percentage getter
   * 
   * @return vip seat percentage
   */
  public double getVipPercentage() {
    return this.vipPercentage;
  }

  /**
   * vip percentage setter
   * 
   * @param vipPercentageIn vip seat percentage
   */
  public void setVipPercentage(double vipPercentageIn) {
    this.vipPercentage = vipPercentageIn;
  }

  /**
   * gold percentage getter
   * 
   * @return gold seat percentage
   */
  public double getGoldPercentage() {
    return this.goldPercentage;
  }

  /**
   * gold percentage setter
   * 
   * @param goldPercentageIn gold seat percentage
   */
  public void setGoldPercentage(double goldPercentageIn) {
    this.goldPercentage = goldPercentageIn;
  }

  /**
   * silver percentage getter
   * 
   * @return silver seat percentage
   */
  public double getSilverPercentage() {
    return this.silverPercentage;
  }

  /**
   * silver percentage setter
   * 
   * @param silverPercentageIn silver seat percentage
   */
  public void setSilverPercentage(double silverPercentageIn) {
    this.silverPercentage = silverPercentageIn;
  }

  /**
   * bronze percentage getter
   * 
   * @return bronze seat percentage
   */
  public double getBronzePercentage() {
    return this.bronzePercentage;
  }

  /**
   * bronze percentgae setter
   * 
   * @param bronzePercentageIn bronze seat percentage
   */
  public void setBronzePercentage(double bronzePercentageIn) {
    this.bronzePercentage = bronzePercentageIn;
  }

  /**
   * general admission percentage getter
   * 
   * @return general admission seat percentage
   */
  public double getGenAdmissionPercentage() {
    return this.genAdmissionPercentage;
  }

  /**
   * general admission percentage setter
   * 
   * @param genAdmissionPercentageIn general admission seat percentage
   */
  public void setGenAdmissionPercentage(double genAdmissionPercentageIn) {
    this.genAdmissionPercentage = genAdmissionPercentageIn;
  }

  /**
   * reserved percentage getter
   * 
   * @return reserved seat percentage
   */
  public double getReservedExtraPercentage() {
    return this.reservedExtraPercentage;
  }

  /**
   * reserved percentage setter
   * 
   * @param reservedExtraPercentageIn reserved seat percentage
   */
  public void setReservedExtraPercentage(double reservedExtraPercentageIn) {
    this.reservedExtraPercentage = reservedExtraPercentageIn;
  }

  /**
   * unavailable percentage getter
   * 
   * @return unavailable seat percentage
   */
  public double getUnavailableSeatPercentage() {
    return this.unavailableSeatPercentage;
  }

  /**
   * unavailable percentage setter
   * 
   * @param unavailableSeatPercentageIn unavailable seat percentage
   */
  public void setUnavailableSeatPercentage(double unavailableSeatPercentageIn) {
    this.unavailableSeatPercentage = unavailableSeatPercentageIn;
  }
}
