
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
import java.util.LinkedHashMap;

public class CustomerHandler extends CSVHandler<Integer, Customer> {

  /**
   * Constructor
   */
  public CustomerHandler() {

  }

  /**
   * Takes in a linked hashmap containing customer information and creates a CSV
   * file based on the data.
   * 
   * @param customers Linked hashmap containing all customers and their
   *                  information
   */
  public void createCSVFile(LinkedHashMap<Integer, Customer> customers) {
    try {
      File UpdatedCustomerListPA2 = new File("UpdatedCustomerListPA3.csv");
      FileWriter myWriter = new FileWriter(UpdatedCustomerListPA2);

      String headers = String.join(",", this.headers) + "\n"; // Create column headers
      myWriter.write(headers);

      for (Entry<Integer, Customer> customer : customers.entrySet()) {

        Customer currCustomer = customer.getValue();
        myWriter.write(exportObjectAsRow(currCustomer)); // Write customer info as a row
      }

      myWriter.close();
    } catch (IOException e) {
      System.out.println("Failed to create Customer CSV");
    }

  }

  /**
   * Takes in a row of the customer csv file and creates a customer object with
   * its attributes
   * 
   * @param rowValues CSV row broken into an array
   * @return Map entry containing customer object as value and customer ID as key
   */
  public Map.Entry<Integer, Customer> makeEntryFromRow(String[] rowValues) {
    Customer currCustomer = new Customer();

    for (int i = 0; i < rowValues.length; i++) {
      switch (this.headers.get(i)) {
        case "ID":
          currCustomer.setId(Integer.parseInt(rowValues[i]));
          break;

        case "First Name":
          currCustomer.setFirstName(rowValues[i]);
          break;

        case "Last Name":
          currCustomer.setLastName(rowValues[i]);
          break;

        case "Money Available":
          currCustomer.setMoneyAvailable(Double.parseDouble(rowValues[i]));
          break;

        case "Tickets Purchased":
          currCustomer.setConcertsPurchased(Integer.parseInt(rowValues[i]));
          break;

        case "TicketMiner Membership":
          currCustomer.setTicketMinerMember(Boolean.parseBoolean(rowValues[i]));
          break;

        case "Username":
          currCustomer.setUsername(rowValues[i]);
          break;

        case "Password":
          currCustomer.setPassword(rowValues[i]);
          break;
      }
    }
    return Map.entry(currCustomer.getId(), currCustomer);
  }

  /**
   * Takes a customer object and exports all its attributes as a comma seperated
   * string
   * 
   * @param customerIn Customer object
   * @return comma seperated string containing customers attributes
   */
  public String exportObjectAsRow(Customer customerIn) {
    String customer = "";

    for (int i = 0; i < this.headers.size(); i++) {
      switch (this.headers.get(i)) {
        case "ID":
          customer += customerIn.getId();
          break;

        case "First Name":
          customer += customerIn.getFirstName();
          break;

        case "Last Name":
          customer += customerIn.getLastName();
          break;

        case "Money Available":
          customer += customerIn.getMoneyAvailable();
          break;

        case "Tickets Purchased":
          customer += customerIn.getConcertsPurchased();
          break;

        case "TicketMiner Membership":
          customer += customerIn.getTicketMinerMember();
          break;

        case "Username":
          customer += customerIn.getUsername();
          break;

        case "Password":
          customer += customerIn.getPassword();
          break;
      }

      if (i != (this.headers.size() - 1)) {
        customer += ",";
      }
    }

    return customer + "\n";
  }

}