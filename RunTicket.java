/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.io.FileNotFoundException;
import java.util.LinkedHashMap;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: This class contains the main method and creates the HashMaps
 * and UI.
 * <p>
 * Last Change: 11/17/2023
 *
 * @author Erik LaNeave
 * @version 2.5
 */

public class RunTicket {

    /**
     * Calls the custOrAdmin method that starts the login process and creates the
     * two hashmaps for customer and events
     * 
     * @param args
     */
    public static void main(String args[]) {

        // Makes empty maps
        LinkedHashMap<Integer, Event> eventMap = new LinkedHashMap<>(); // Hashmap that contains events
        LinkedHashMap<Integer, Customer> customerMap = new LinkedHashMap<>(); // Hashmap that contains customers
        // Creates an instance of all needed classes
        Login userLogin = new Login();
        EventHandler eventHandler = new EventHandler();
        CustomerHandler customerHandler = new CustomerHandler();
        Log logFile = Log.getInstance();
        TicketMiner ourCompany = TicketMiner.getInstance();

        // Handles the file not found exception
        try {

            logFile.save(logFile.time() + " Program start\n");
            // Creates the maps with given file name
            eventMap = eventHandler.readFile("EventListPA5.csv");
            customerMap = customerHandler.readFile("CustomerListPA5.csv");
            logFile.save(logFile.time() + " Event and Customer maps made from csv files\n");
            ourCompany.initializeCollectedFees(eventMap);
            // boolean used to control the driving while loop
            boolean mainMenuControl = true;
            System.out.println("Welcome to TicketMiner!");
            // Runs till user ends program
            while (mainMenuControl) {
                logFile.save(logFile.time() + " Starting the login process\n");
                // all the main menus will return a false to end the program
                mainMenuControl = userLogin.custOrAdmin(eventMap, customerMap);
            }

            // Creates the new csv files
            eventHandler.createCSVFile(eventMap);
            customerHandler.createCSVFile(customerMap);
            logFile.save(logFile.time() + " Updated Event and Customer csv made\n");
            System.out.println("Saved new csv");
            // writes the record string in the logFile object the log.txt file
            logFile.writeLogFile();
            System.out.println("Log file has been saved\n");

        } catch (FileNotFoundException e) {
            logFile.save(logFile.time() + " One or both CSV Files not found\n");
            System.out.println("CSV Files not found");
            logFile.writeLogFile();
        }
    }
}
