
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

import java.util.*;
import java.util.Map.Entry;
import java.io.*;

public class EventHandler extends CSVHandler<Integer, Event> {

  /**
   * Class attributes
   */
  private EventFactory eventFactory = new EventFactory();
  private VenueFactory venueFactory = new VenueFactory();

  /**
   * Constructor
   */
  public EventHandler() {

  }

  /**
   * Takes in a linked hashmap containing events information and creates a CSV
   * file based on the data.
   * 
   * @param events Linked hashmap containing all events and their
   *               information
   */
  public void createCSVFile(LinkedHashMap<Integer, Event> events) {
    try {
      File UpdatedEventListPA2 = new File("UpdatedEventListPA3.csv");
      FileWriter myWriter = new FileWriter(UpdatedEventListPA2);

      updateHeaders();
      String headers = String.join(",", this.headers) + "\n"; // Create column headers
      myWriter.write(headers);

      for (Entry<Integer, Event> event : events.entrySet()) {

        Event currEvent = event.getValue();
        myWriter.write(exportObjectAsRow(currEvent)); // Write event info as a row
      }

      myWriter.close();
    } catch (IOException e) {
      System.out.println("Failed to creat Event CSV");
    }

  }

  /**
   * Takes in a row of the event csv file and creates a event object with
   * its attributes
   * 
   * @param rowValues CSV row broken into an array
   * @return Map entry containing event object as value and event ID as key
   */
  public Map.Entry<Integer, Event> makeEntryFromRow(String[] rowValues) {

    String eventType = rowValues[this.headers.indexOf("Event Type")];
    String venueType = rowValues[this.headers.indexOf("Venue Type")];

    Event currEvent = eventFactory.createEvent(eventType); // Empty event
    Venue currVenue = venueFactory.createVenue(venueType); // Empty venue

    for (int i = 0; i < rowValues.length; i++) {
      switch (this.headers.get(i)) {
        case "Event ID":
          currEvent.setId(Integer.parseInt(rowValues[i]));
          break;

        case "Event Type":
          currEvent.setEventType(rowValues[i]);
          break;

        case "Name":
          currEvent.setName(rowValues[i]);
          break;

        case "Date":
          currEvent.setDate(rowValues[i]);
          break;

        case "Time":
          currEvent.setTime(rowValues[i]);
          break;

        case "VIP Price":
          currEvent.setVipPrice(Double.parseDouble(rowValues[i]));
          break;

        case "Gold Price":
          currEvent.setGoldPrice(Double.parseDouble(rowValues[i]));
          break;

        case "Silver Price":
          currEvent.setSilverPrice(Double.parseDouble(rowValues[i]));
          break;

        case "Bronze Price":
          currEvent.setBronzePrice(Double.parseDouble(rowValues[i]));
          break;

        case "General Admission Price":
          currEvent.setGenAdmissionPrice(Double.parseDouble(rowValues[i]));
          break;

        case "Venue Name":
          currVenue.setName(rowValues[i]);
          break;

        case "Pct Seats Unavailable":
          currVenue.setUnavailableSeatPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Venue Type":
          currVenue.setVenueType(rowValues[i]);
          break;

        case "Capacity":
          currVenue.setCapacity(Integer.parseInt(rowValues[i]));
          break;

        case "Cost":
          currVenue.setCost(Double.parseDouble(rowValues[i]));
          break;

        case "VIP Pct":
          currVenue.setVipPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Gold Pct":
          currVenue.setGoldPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Silver Pct":
          currVenue.setSilverPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Bronze Pct":
          currVenue.setBronzePercentage(Double.parseDouble(rowValues[i]));
          break;

        case "General Admission Pct":
          currVenue.setGenAdmissionPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Reserved Extra Pct":
          currVenue.setReservedExtraPercentage(Double.parseDouble(rowValues[i]));
          break;

        case "Fireworks Planned":
          currEvent.setFireworks(rowValues[i]);
          break;

        case "Fireworks Cost":
          if (rowValues[i].equals("")) {
            currEvent.setFireworksCost(0);
            break;
          }

          currEvent.setFireworksCost(Double.parseDouble(rowValues[i]));
          break;
      }
    }
    currVenue.calculateNumSeats(); // Have numbers of seats calculated
    currEvent.setVenue(currVenue); // Set venue

    return Map.entry(currEvent.getId(), currEvent);
  }

  /**
   * Adds additonal headers needed to create final csv
   */
  public void updateHeaders() {
    this.headers.add("VIP Seats Sold");
    this.headers.add("Gold Seats Sold");
    this.headers.add("Silver Seats Sold");
    this.headers.add("Bronze Seats Sold");
    this.headers.add("General Admission Seats Sold");
    this.headers.add("Total VIP Revenue");
    this.headers.add("Total Gold Revenue");
    this.headers.add("Total Silver Revenue");
    this.headers.add("Total Bronze Revenue");
    this.headers.add("Total General Admission Revenue");
  }

  /**
   * Takes a event object and exports all its attributes as a comma seperated
   * string
   * 
   * @param eventIn Event object
   * @return comma seperated string containing events attributes
   */
  public String exportObjectAsRow(Event eventIn) {
    String event = "";

    for (int i = 0; i < this.headers.size(); i++) {
      switch (this.headers.get(i)) {
        case "Event ID":
          event += eventIn.getId();
          break;

        case "Event Type":
          event += eventIn.getEventType();
          break;

        case "Name":
          event += eventIn.getName();
          break;

        case "Date":
          event += eventIn.getDate();
          break;

        case "Time":
          event += eventIn.getTime();
          break;

        case "VIP Price":
          event += eventIn.getVipPrice();
          break;

        case "Gold Price":
          event += eventIn.getGoldPrice();
          break;

        case "Silver Price":
          event += eventIn.getSilverPrice();
          break;

        case "Bronze Price":
          event += eventIn.getBronzePrice();
          break;

        case "General Admission Price":
          event += eventIn.getGenAdmissionPrice();
          break;

        case "Venue Name":
          event += eventIn.getVenue().getName();
          break;

        case "Pct Seats Unavailable":
          event += eventIn.getVenue().getUnavailableSeatPercentage();
          break;

        case "Venue Type":
          event += eventIn.getVenue().getVenueType();
          break;

        case "Capacity":
          event += eventIn.getVenue().getCapacity();
          break;

        case "Cost":
          event += eventIn.getVenue().getCost();
          break;

        case "VIP Pct":
          event += eventIn.getVenue().getVipPercentage();
          break;

        case "Gold Pct":
          event += eventIn.getVenue().getGoldPercentage();
          break;

        case "Silver Pct":
          event += eventIn.getVenue().getSilverPercentage();
          break;

        case "Bronze Pct":
          event += eventIn.getVenue().getBronzePercentage();
          break;

        case "General Admission Pct":
          event += eventIn.getVenue().getGenAdmissionPercentage();
          break;

        case "Reserved Extra Pct":
          event += eventIn.getVenue().getReservedExtraPercentage();
          break;

        case "Fireworks Planned":
          event += eventIn.getFireworks();
          break;

        case "Fireworks Cost":
          event += eventIn.getFireworksCost();
          break;

        case "VIP Seats Sold":
          event += eventIn.getVipSold();
          break;

        case "Gold Seats Sold":
          event += eventIn.getGoldSold();
          break;

        case "Silver Seats Sold":
          event += eventIn.getSilverSold();
          break;

        case "Bronze Seats Sold":
          event += eventIn.getBronzeSold();
          break;

        case "General Admission Seats Sold":
          event += eventIn.getGeneralAdmissionSold();
          break;

        case "Total VIP Revenue":
          event += eventIn.getVipRevenue();
          break;

        case "Total Gold Revenue":
          event += eventIn.getGoldRevenue();
          break;

        case "Total Silver Revenue":
          event += eventIn.getSilverRevenue();
          break;

        case "Total Bronze Revenue":
          event += eventIn.getBronzeRevenue();
          break;

        case "Total General Admission Revenue":
          event += eventIn.getGeneralAdmissionRevenue();
          break;
      }

      if (i != (this.headers.size() - 1)) {
        event += ",";
      }
    }

    return event + "\n";
  }
}
