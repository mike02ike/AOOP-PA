import java.util.*;
import java.io.*;

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

public class AutoPurchaseReader {

  /**
   * Class attributes
   */
  private String seperator = ","; // CSV file seperator
  private ArrayList<String> headers = new ArrayList<>(); // Headers of CSV file

  /**
   * Default constructor
   */
  public AutoPurchaseReader() {

  }

  /**
   * Reads an autopurchase csv file and returns an array list that contains all
   * auto purchase instructions
   * 
   * @param fileName
   * @return An array list containing all auto purchase instructions
   * @throws FileNotFoundException
   */
  public ArrayList<AutoPurchaseInstruction> readFile(String fileName) throws FileNotFoundException {
    ArrayList<AutoPurchaseInstruction> data = new ArrayList<>();
    File autoPurchase = new File(fileName);
    Scanner scn = new Scanner(autoPurchase);
    headers.addAll(Arrays.asList(scn.nextLine().split(seperator))); // Get headers

    while (scn.hasNextLine()) {
      String[] rowValues = scn.nextLine().split(seperator);
      data.add(makeObjectFromRow(rowValues)); // Add auto purchase instruction
    }
    scn.close();
    return data;
  }

  /**
   * Takes in a line of the csv file seperated by a comma and returns an
   * AutoPurchaseInstruction object using the data in the rowValues array
   * 
   * @param rowValues One line from csv file split by a comma
   * @return AutpPurchaseInstruction
   */
  public AutoPurchaseInstruction makeObjectFromRow(String[] rowValues) {
    AutoPurchaseInstruction currAutoPurchase = new AutoPurchaseInstruction();

    for (int i = 0; i < rowValues.length; i++) {
      switch (this.headers.get(i)) {
        case "First":
          currAutoPurchase.setCustomerFName(rowValues[i]);
          break;

        case "Last":
          currAutoPurchase.setCustomerLName(rowValues[i]);
          break;

        case "Action":
          currAutoPurchase.setAction(rowValues[i]);
          break;

        case "Event ID":
          currAutoPurchase.setEventID(Integer.parseInt(rowValues[i]));
          break;

        case "Event Name":
          currAutoPurchase.setEventName(rowValues[i]);
          break;

        case "Ticket Quantity":
          currAutoPurchase.setTicketQuantity(Integer.parseInt(rowValues[i]));
          break;

        case "Ticket Type":
          currAutoPurchase.setTicketType(rowValues[i]);
          break;
      }
    }

    return currAutoPurchase;
  }
}
