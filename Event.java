
/**
 * @author Ian Gutierrez & Erik LaNeave
 *         Date: 10/21/2023
 *         Course: CS 3331 - Advanced Object Oriented Programming
 *         Instructor: Dr. Daniel Mejia
 *         Programming Assignment 3
 *         Lab Description: This program is an extension of PA2, with added tax and discount functionality. It is also merged with a teammate.
 *         Honesty Statement: I completed this work entirely on my own without
 *         any outside sources including peers, experts, online sources, or the
 *         like. The only assistance I received was from the instructor, TA, or
 *         IA.
 */

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Event {

  /**
   * Class attributes
   */
  private boolean isCanceled;
  private int id;
  private String eventType;
  private String name;
  private String date;
  private String time;
  private Venue venue; // Venue where event will take place
  private boolean fireworks; // Will the event have fireworks
  private double fireworksCost;
  private HashMap<String, Double> seatPrices = new HashMap<>();
  private HashMap<String, ArrayList<Ticket>> purchasedTickets = new HashMap<>(); // Tickets sold by type
  private HashMap<String, Integer> numSeatsSold = new HashMap<>(); // Number of seats sold by type
  private HashMap<String, Double> seatRevenue = new HashMap<>(); // Revenue collected by type
  private HashMap<Integer, ArrayList<Invoice>> invoices = new HashMap<>();
  private double expectedProfit;
  private double actualProfit;
  private double totalMemberDiscount; // Total amount customers have saved due to membership
  private double totalTax; // Total tax collected
  private HashMap<String, Double> collectedFees = new HashMap<>();

  /**
   * Constructor
   */
  public Event() {
    initializeSeatPrices();
    initializePurchasedTickets();
    initializeNumSeatsSold();
    initializeSeatRevenue();
    initializeCollectedFees();
  }

  /**
   * Initialized hashmap
   */
  public void initializeSeatPrices() {
    this.seatPrices.put("vip", 0.0);
    this.seatPrices.put("gold", 0.0);
    this.seatPrices.put("silver", 0.0);
    this.seatPrices.put("bronze", 0.0);
    this.seatPrices.put("genadmission", 0.0);
  }

  /**
   * Initialized hashmap
   */
  public void initializePurchasedTickets() {
    ArrayList<Ticket> vipPurchasedTickets = new ArrayList<>(); // Vip tickets purchased
    ArrayList<Ticket> goldPurchasedTickets = new ArrayList<>(); // Gold tickets purchased
    ArrayList<Ticket> silverPurchasedTickets = new ArrayList<>(); // Silver tickets purchased
    ArrayList<Ticket> bronzePurchasedTickets = new ArrayList<>(); // Bronze tickets purchased
    ArrayList<Ticket> generalAdmissionPurchasedTickets = new ArrayList<>(); // General admission tickets purchased

    this.purchasedTickets.put("vip", vipPurchasedTickets);
    this.purchasedTickets.put("gold", goldPurchasedTickets);
    this.purchasedTickets.put("silver", silverPurchasedTickets);
    this.purchasedTickets.put("bronze", bronzePurchasedTickets);
    this.purchasedTickets.put("genadmission", generalAdmissionPurchasedTickets);
  }

  /**
   * Initialized hashmap
   */
  public void initializeNumSeatsSold() {
    this.numSeatsSold.put("vip", 0);
    this.numSeatsSold.put("gold", 0);
    this.numSeatsSold.put("silver", 0);
    this.numSeatsSold.put("bronze", 0);
    this.numSeatsSold.put("genadmission", 0);
    this.numSeatsSold.put("total", 0);
  }

  /**
   * Initialized hashmap
   */
  public void initializeSeatRevenue() {
    this.seatRevenue.put("vip", 0.0);
    this.seatRevenue.put("gold", 0.0);
    this.seatRevenue.put("silver", 0.0);
    this.seatRevenue.put("bronze", 0.0);
    this.seatRevenue.put("genadmission", 0.0);
    this.seatRevenue.put("total", 0.0);
  }

  public void initializeCollectedFees() {
    this.collectedFees.put("convenience", 0.0);
    this.collectedFees.put("service", 0.0);
    this.collectedFees.put("charity", 0.0);
  }

  public HashMap<Integer, ArrayList<Invoice>> getInvoices() {
    return this.invoices;
  }

  public void addInvoice(Invoice invoice) {
    if (this.invoices.get(invoice.getCustomerID()) == null) {
      ArrayList<Invoice> customerInvoices = new ArrayList<>();
      this.invoices.put(invoice.getCustomerID(), customerInvoices);
    }

    ArrayList<Invoice> temp = this.invoices.get(invoice.getCustomerID());
    temp.add(invoice);

    this.invoices.put(invoice.getCustomerID(), temp);

  }

  /**
   * Updates all necessary seat and revenue information
   */
  public void updateSeatsAndRevenue() {
    // Seat availability information
    double[] originalSeatsAvailable = this.getVenue().getOriginalAvailableSeats();
    double[] updatedSeatsAvailable = this.getVenue().getNumAvailableSeats();

    // Number of seats sold by type
    this.numSeatsSold.put("vip", (int) (originalSeatsAvailable[0] - updatedSeatsAvailable[0]));
    this.numSeatsSold.put("gold", (int) (originalSeatsAvailable[1] - updatedSeatsAvailable[1]));
    this.numSeatsSold.put("silver", (int) (originalSeatsAvailable[2] - updatedSeatsAvailable[2]));
    this.numSeatsSold.put("bronze", (int) (originalSeatsAvailable[3] - updatedSeatsAvailable[3]));
    this.numSeatsSold.put("genadmission", (int) (originalSeatsAvailable[4] - updatedSeatsAvailable[4]));

    this.numSeatsSold.put("total", (this.numSeatsSold.get("vip") + this.numSeatsSold.get("gold")
        + this.numSeatsSold.get("silver") + this.numSeatsSold.get("bronze") + this.numSeatsSold.get("genadmission")));

    // Revenue generated by type
    this.seatRevenue.put("vip", (this.numSeatsSold.get("vip") * this.seatPrices.get("vip")));

    this.seatRevenue.put("gold", (this.numSeatsSold.get("gold") * this.seatPrices.get("gold")));

    this.seatRevenue.put("silver", (this.numSeatsSold.get("silver") * this.seatPrices.get("silver")));

    this.seatRevenue.put("bronze", (this.numSeatsSold.get("bronze") * this.seatPrices.get("bronze")));

    this.seatRevenue.put("genadmission", (this.numSeatsSold.get("genadmission") * this.seatPrices.get("genadmission")));

    this.seatRevenue.put("total", (this.seatRevenue.get("vip") + this.seatRevenue.get("gold")
        + this.seatRevenue.get("silver") + this.seatRevenue.get("bronze") + this.seatRevenue.get("genadmission")));

    // Profit infomation
    this.expectedProfit = ((originalSeatsAvailable[0] * this.seatPrices.get("vip"))
        + (originalSeatsAvailable[1] * this.seatPrices.get("gold"))
        + (originalSeatsAvailable[2] * this.seatPrices.get("silver"))
        + (originalSeatsAvailable[3] * this.seatPrices.get("bronze"))
        + (originalSeatsAvailable[4] * this.seatPrices.get("genadmission"))) - this.venue.getCost();

    this.actualProfit = (this.seatRevenue.get("total") - (this.venue.getCost() + this.fireworksCost));

  }

  /**
   * Prints all of an events information for an admin
   */
  public void adminPrintAllInfo() {
    System.out.println("-------------------------");

    System.out.println("Event ID: " + this.getId());
    System.out.println("Name: " + this.getName());
    System.out.println("Date: " + this.getDate());
    System.out.println("Time: " + this.getTime());
    System.out.println("Event Type: " + this.getEventType());
    System.out.println("Event Capacity " + this.venue.getCapacity());
    System.out.println("Total Seats Sold: " + this.getTotalSeatsSold());
    System.out.println("Total VIP Seats Sold: " + this.getVipSold());
    System.out.println("Total Gold Seats Sold: " + this.getGoldSold());
    System.out.println("Total Silver Seats Sold: " + this.getSilverSold());
    System.out.println("Total Bronze Seats Sold: " + this.getBronzeSold());
    System.out.println("Total General Admission Seats Sold: " + this.getGeneralAdmissionSold());
    System.out.println("Total Revenue For VIP Tickets: $" + this.getVipRevenue());
    System.out.println("Total Revenue For Gold Tickets: $" + this.getGoldRevenue());
    System.out.println("Total Revenue For Silver Tickets: $" + this.getSilverRevenue());
    System.out.println("Total Revenue For Bronze Tickets: $" + this.getBronzeRevenue());
    System.out.println("Total Revenue For General Admission Tickets: $" + this.getGeneralAdmissionRevenue());
    System.out.println("Total Revenue For All Tickets: $" + this.getTotalRevenue());
    System.out.println("Expected Profit (Sell Out): $" + this.expectedProfit);
    System.out.println("Actual Profit: $" + this.actualProfit);

    System.out.println("-------------------------");
  }

  /**
   * Prints event basic information
   */
  public void eventBasicInfo() {
    System.out.println("Event ID: " + id + "\nEvent Name: " + name + "\nEvent Date: " + date + "\nEvent Time: " + time +
        "\nEvent Type: " + eventType + "\nEvent Location: " + venue.getName() + "\nEvent Capacity: "
        + venue.getCapacity());
  }

  /**
   * Prints all of the event seat information for an admin
   */
  public void adminPrintAvailableSeats() {
    double[] updatedSeatsAvailable = this.getVenue().getNumAvailableSeats();

    int totalSeatsRemainingExclude = (int) updatedSeatsAvailable[0] + (int) updatedSeatsAvailable[1]
        + (int) updatedSeatsAvailable[2] + (int) updatedSeatsAvailable[3] + (int) updatedSeatsAvailable[4];
    int totalSeatsRemaining = totalSeatsRemainingExclude + (int) updatedSeatsAvailable[5];

    System.out.println("-------------------------");

    System.out.println("VIP Seats Remaining: " + (int) updatedSeatsAvailable[0]);
    System.out.println("Gold Seats Remaining: " + (int) updatedSeatsAvailable[1]);
    System.out.println("Silver Seats Remaining: " + (int) updatedSeatsAvailable[2]);
    System.out.println("Bronze Seats Remaining: " + (int) updatedSeatsAvailable[3]);
    System.out.println("General Admission Seats Remaining: " + (int) updatedSeatsAvailable[4]);
    System.out.println("Total Seats Remaining (Excluding Reserved): " + totalSeatsRemainingExclude);
    System.out.println("Total Seats Remaining: " + totalSeatsRemaining);

    System.out.println("-------------------------");
  }

  /**
   * Prints all of the event revenue information for an admin
   */
  public void adminPrintRevenueInfo() {
    System.out.println("-------------------------");

    System.out.println("Total Revenue for VIP Tickets: $" + getVipRevenue());
    System.out.println("Total Revenue for Gold Tickets: $" + getGoldRevenue());
    System.out.println("Total Revenue for Silver Tickets: $" + getSilverRevenue());
    System.out.println("Total Revenue for Bronze Tickets: $" + getBronzeRevenue());
    System.out.println("Total Revenue for General Admission Tickets: $" + getGeneralAdmissionRevenue());
    System.out.println("Total Revenue for All Tickets: $" + getTotalRevenue());
    System.out.println("Total Amount of Discount for Event: $" + getTotalMemberDiscount());
    System.out.println("Total Tax for Event: $" + getTotalTax());
    System.out.println("Expected Profit (Sell Out): $" + this.expectedProfit);
    System.out.println("Actual Profit: $" + this.actualProfit);

    System.out.println("-------------------------");
  }

  /**
   * Adds to the total member discount savings
   * 
   * @param savings Amount saved
   */
  public void addTotalMemberDiscount(double savings) {
    this.totalMemberDiscount += savings;
  }

  /**
   * Adds to the total tax collected
   * 
   * @param tax Taxed charged
   */
  public void addTotalTax(double tax) {
    this.totalTax += tax;
  }

  /**
   * Total membership discount getter
   * 
   * @return Amount a member has saved
   */
  public double getTotalMemberDiscount() {
    return this.totalMemberDiscount;
  }

  /**
   * Total tax getter
   * 
   * @return total tax
   */
  public double getTotalTax() {
    return this.totalTax;
  }

  /**
   * Total tax setter
   * 
   * @param totalTax tax collected
   */
  public void setTotalTax(double totalTax) {
    this.totalTax = totalTax;
  }

  /**
   * ID getter
   * 
   * @return Event ID
   */
  public int getId() {
    return this.id;
  }

  /**
   * ID setter
   * 
   * @param idIn Event ID
   */
  public void setId(int idIn) {
    this.id = idIn;
  }

  /**
   * Event type getter
   * 
   * @return Event type
   */
  public String getEventType() {
    return this.eventType;
  }

  /**
   * Event type setter
   * 
   * @param eventTypeIn Event type
   */
  public void setEventType(String eventTypeIn) {
    this.eventType = eventTypeIn;
  }

  /**
   * Name getter
   * 
   * @return Event name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Name setter
   * 
   * @param nameIn Event name
   */
  public void setName(String nameIn) {
    this.name = nameIn;
  }

  /**
   * Date getter
   * 
   * @return Event date
   */
  public String getDate() {
    return this.date;
  }

  /**
   * Date setter
   * 
   * @param dateIn Event date
   */
  public void setDate(String dateIn) {
    this.date = dateIn;
  }

  /**
   * Time getter
   * 
   * @return Event time
   */
  public String getTime() {
    return this.time;
  }

  /**
   * Time setter
   * 
   * @param timeIn Event time
   */
  public void setTime(String timeIn) {
    this.time = timeIn;
  }

  /**
   * Vip price getter
   * 
   * @return Vip ticket price
   */
  public double getVipPrice() {
    return this.seatPrices.get("vip");
  }

  /**
   * Vip price setter
   * 
   * @param vipPriceIn Vip ticket price
   */
  public void setVipPrice(double vipPriceIn) {
    this.seatPrices.put("vip", vipPriceIn);
  }

  /**
   * Gold price getter
   * 
   * @return Gold ticket price
   */
  public double getGoldPrice() {
    return this.seatPrices.get("gold");
  }

  /**
   * Gold price setter
   * 
   * @return Gold ticket price
   */
  public void setGoldPrice(double goldPriceIn) {
    this.seatPrices.put("gold", goldPriceIn);
  }

  /**
   * Silver price getter
   * 
   * @return Silver ticket price
   */
  public double getSilverPrice() {
    return this.seatPrices.get("silver");
  }

  /**
   * Silver price setter
   * 
   * @return Silver ticket price
   */
  public void setSilverPrice(double silverPriceIn) {
    this.seatPrices.put("silver", silverPriceIn);
  }

  /**
   * Bronze price getter
   * 
   * @return Bronze ticket price
   */
  public double getBronzePrice() {
    return this.seatPrices.get("bronze");
  }

  /**
   * Bronze price setter
   * 
   * @return Bronze ticket price
   */
  public void setBronzePrice(double bronzePriceIn) {
    this.seatPrices.put("bronze", bronzePriceIn);
  }

  /**
   * General Admission price getter
   * 
   * @return General Admission ticket price
   */
  public double getGenAdmissionPrice() {
    return this.seatPrices.get("genadmission");
  }

  /**
   * General Admission price setter
   * 
   * @return General Admission ticket price
   */
  public void setGenAdmissionPrice(double genAdmissionPriceIn) {
    this.seatPrices.put("genadmission", genAdmissionPriceIn);
  }

  /**
   * Venue getter
   * 
   * @return Event venue
   */
  public Venue getVenue() {
    return this.venue;
  }

  /**
   * Venue setter
   * 
   * @param venueIn Event venue
   */
  public void setVenue(Venue venueIn) {
    this.venue = venueIn;
  }

  /**
   * Fireworks getter
   * 
   * @return Whether the event has fireworks
   */
  public boolean getFireworks() {
    return this.fireworks;
  }

  /**
   * Fireworks setter
   * 
   * @param fireworksIn Whether the event has fireworks
   */
  public void setFireworks(String fireworksIn) {
    if (fireworksIn.equals("Yes")) {
      this.fireworks = true;
    } else {
      this.fireworks = false;
    }
  }

  /**
   * Fireworks cost getter
   * 
   * @return Fireworks cost
   */
  public double getFireworksCost() {
    return this.fireworksCost;
  }

  /**
   * Fireworks setter
   * 
   * @param fireworksCostIn Fireworks cost
   */
  public void setFireworksCost(double fireworksCostIn) {
    this.fireworksCost = fireworksCostIn;
  }

  /**
   * Vip seats sold getter
   * 
   * @return Number of vip seats sold
   */
  public int getVipSold() {
    return this.numSeatsSold.get("vip");
  }

  /**
   * Gold seats sold getter
   * 
   * @return Number of gold seats sold
   */
  public int getGoldSold() {
    return this.numSeatsSold.get("gold");
  }

  /**
   * Silver seats sold getter
   * 
   * @return Number of silver seats sold
   */
  public int getSilverSold() {
    return this.numSeatsSold.get("silver");
  }

  /**
   * Bronze seats sold getter
   * 
   * @return Number of bronze seats sold
   */
  public int getBronzeSold() {
    return this.numSeatsSold.get("bronze");
  }

  /**
   * General admission seats sold getter
   * 
   * @return Number of general admission seats sold
   */
  public int getGeneralAdmissionSold() {
    return this.numSeatsSold.get("genadmission");
  }

  public int getTotalSeatsSold() {
    return this.numSeatsSold.get("total");
  }

  /**
   * Vip ticket revenue
   * 
   * @return revenue from vip tickets
   */
  public double getVipRevenue() {
    return this.seatRevenue.get("vip");
  }

  /**
   * Gold ticket revenue
   * 
   * @return revenue from gold tickets
   */
  public double getGoldRevenue() {
    return this.seatRevenue.get("gold");
  }

  /**
   * Silver ticket revenue
   * 
   * @return revenue from silver tickets
   */
  public double getSilverRevenue() {
    return this.seatRevenue.get("silver");
  }

  /**
   * Bronze ticket revenue
   * 
   * @return revenue from bronze tickets
   */
  public double getBronzeRevenue() {
    return this.seatRevenue.get("bronze");
  }

  /**
   * General admission ticket revenue
   * 
   * @return revenue from general admission tickets
   */
  public double getGeneralAdmissionRevenue() {
    return this.seatRevenue.get("genadmission");
  }

  /**
   * Total revenue getter
   * 
   * @return Total revenue
   */
  public double getTotalRevenue() {
    return this.seatRevenue.get("total");
  }

  public double getConvenienceFee() {
    return this.collectedFees.get("convenience");
  }

  public void setConvenienceFee(double feeIn) {
    this.collectedFees.put("convenience", feeIn);
  }

  public void addToConvenienceFee(double feeIn) {
    this.collectedFees.put("convenience", this.getConvenienceFee() + feeIn);
  }

  public double getServiceFee() {
    return this.collectedFees.get("service");
  }

  public void setServiceFee(double feeIn) {
    this.collectedFees.put("service", feeIn);
  }

  public void addToServiceFee(double feeIn) {
    this.collectedFees.put("service", this.getServiceFee() + feeIn);
  }

  public double getCharityFee() {
    return this.collectedFees.get("charity");
  }

  public void setCharityFee(double feeIn) {
    this.collectedFees.put("charity", feeIn);
  }

  public void addToCharityFee(double feeIn) {
    this.collectedFees.put("charity", this.getCharityFee() + feeIn);
  }

  public void setIsCanceled(boolean isCanceled) {
    this.isCanceled = isCanceled;
  }

  public boolean getIsCanceled() {
    return this.isCanceled;
  }

}
