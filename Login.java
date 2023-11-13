/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.LinkedHashMap;
import java.util.Scanner;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: The Login class controls the login in process for the user and
 * admin
 * A log of login information is made.
 * <p>
 * 
 * @since 11/13/2023
 * @author Erik LaNeave
 * @version 1.4
 *          <p>
 * @since 10/28/2023
 * @author Michael Ike
 * @version 1.2
 */

public class Login {

    // Intialization of RecordMake class
    private Log logFile = Log.getInstance();
    private Scanner scnr = new Scanner(System.in);

    /**
     * Empty Constructor
     */
    public Login() {
    }

    /**
     * Used to have user pick if they are an admin or customer.
     * 
     * @param eventMap
     * @param customerMap
     * @return
     */
    public boolean custOrAdmin(LinkedHashMap<Integer, Event> eventMap, LinkedHashMap<Integer, Customer> customerMap) {
        System.out.print("Enter the number corresponding to your status below:\n1. Customer\n2. Administrator\nType \"Exit\" to terminate the program\n--> ");
        String input = scnr.nextLine();
        switch (input.toLowerCase()) {
            case "1": // The user is a Customer and will be asked to login in
                logFile.save(logFile.time() + " User is a customer and will attempt to login\n");
                customerLogin(eventMap, customerMap);
                return true;
            case "2": // The user is a admin is shown the admin menu
                logFile.save(logFile.time() + " User is an administrator\n");
                UIAdmin admin = UIAdmin.getInstance();
                admin.setHashMaps(eventMap, customerMap);
                admin.menu();
                return true;
            case "exit":
                logFile.save(logFile.time() + " User terminated the program\n");
                return false;
            default:
                logFile.save(logFile.time() + " User entered an invalid option\n");
                System.out.println("Please enter either 1 or 2\n");
                break;
        }
        return true;
    }

    /**
     * Ask user to enter information and then sends information to different methods
     * will return a call to the Customer UI
     * 
     * @param customerMap
     * @return customerId
     */
    public void customerLogin(LinkedHashMap<Integer, Event> eventMap, LinkedHashMap<Integer, Customer> customerMap) {
        boolean loginControl = true;
        // Will run forever
        while (loginControl) {
            String[] login = new String[2];
            System.out.print(
                    "Option 1: Login with either your first and last name or username and password\nOption 2: Type \"Exit\" to exit the program\nFirst name or Username\n--> ");
            login[0] = scnr.nextLine();
            if (login[0].equalsIgnoreCase("exit")) {
                return;
            }
            System.out.print("Last name or Password\n--> ");
            login[1] = scnr.nextLine();
            // Checks the user credentials with customer hashmap
            int customerId = loginVerify(login, customerMap);
            // Checks the customerId is in the possible range
            if (customerId > 0 && customerId <= customerMap.size()) {
                System.out.println("\nLogin successful");
                logFile.save(logFile.time() + " User with ID " + customerId
                        + " signed in successfully with credentials " + login[0] + " and " + login[1] + "\n");
                // Creates and calls to the UICustomer class and class to the menu in customerUI
                UICustomer custUI = new UICustomer();
                custUI.menu(eventMap, customerMap.get(customerId));
                return;
            } else {
                System.out.println("\nIncorrect login credentials\nPlease try again\n");
                logFile.save(logFile.time() + " Failed login from user with credentials " + login[0] + " and "
                        + login[1] + "\n");
            }
        }
        return;
    }

    /**
     * Verifies customer login credentials based on provided first name/username and
     * last name/password.
     *
     * @param login       An array containing user-provided login information where
     *                    index 0 represents first name or username and index 1
     *                    represents last name or password.
     * @param customerMap A LinkedHashMap containing customer IDs as keys and
     *                    Customer objects as values.
     * @return The ID of the customer if login credentials are valid, or -1 if the
     *         login attempt is invalid.
     */
    public int loginVerify(String[] login, LinkedHashMap<Integer, Customer> customerMap) {
        String firstNameOrUsername = login[0];
        String lastNameOrPassword = login[1];

        for (Customer customer : customerMap.values()) {
            if (firstNameOrUsername.equalsIgnoreCase(customer.getFirstName())
                    || firstNameOrUsername.equalsIgnoreCase(customer.getUsername())) {
                if (lastNameOrPassword.equalsIgnoreCase(customer.getLastName())
                        || lastNameOrPassword.equals(customer.getPassword())) {
                    return customer.getId();
                }
            }
        }
        return -1; // Invalid login credentials
    }
}
