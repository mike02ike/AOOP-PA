/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: This class is used for when the user does not have funds for purchase
 * <p>
 * Last Change: 9/24/2023
 * @author Erik LaNeave
 * @version 1.3
*/

public class CustomerMoneyException extends Exception{

    //Attributes
    private String message = "You have inufficient funds for this purchase\n";

    //Constructor
    /**
     * Empty constructor
     */
    public CustomerMoneyException() {
    }

    //Methods
    /**
     * Gives the exception message
     * @return message
     */
    public String getMessage() {
        return this.message;
    }

}
