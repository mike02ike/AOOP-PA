
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
import java.io.*;
import java.util.LinkedHashMap;

public abstract class CSVHandler<K, V> {

  /**
   * Class attributes
   */
  public static String seperator = ","; // CSV row seperator
  public ArrayList<String> headers = new ArrayList<>(); // CSV column headers

  /**
   * This method reads a CSV file and returns a linked hashmap containing the
   * files
   * data.
   * 
   * @param fileName The name of the csv file you want to read.
   * @return A linked hashmap containing appropriate objects
   * @throws FileNotFoundException
   */
  public LinkedHashMap<K, V> readFile(String fileName) throws FileNotFoundException {
    LinkedHashMap<K, V> data = new LinkedHashMap<>();
    File eventFile = new File(fileName);
    Scanner scn = new Scanner(eventFile);
    headers.addAll(Arrays.asList(scn.nextLine().split(seperator))); // Stores csv header in array list

    while (scn.hasNextLine()) {
      String[] rowValues = scn.nextLine().split(seperator);

      Map.Entry<K, V> currEntry = makeEntryFromRow(rowValues); // Create appropriate object
      data.put(currEntry.getKey(), currEntry.getValue());

    }
    scn.close();
    return data;
  }

  /**
   * This method will take in a string containing the row values and create a map
   * entry containing an appropriate object.
   * 
   * @param rowValues A row of a csv file
   * @return null
   */
  public Map.Entry<K, V> makeEntryFromRow(String[] rowValues) {
    return null;
  }

  /**
   * This method takes in an object and exports its attributes as a commana
   * seperated string
   * 
   * @param object Object you want to export as a single string
   * @return null
   */
  public String exportObjectAsRow(Object object) {
    return null;
  }
}
