/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Class Purpose: Runs all test classes
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * 
 * @since 11/05/2023
 * @author Erik LaNeave
 * @author Ian Gutierrez
 * @version 1.0
 */

@RunWith(Suite.class)
@SuiteClasses({ TestCaseForPurchase.class, TestCaseForEventFactory.class, TestCaseForVenueFactory.class })
public class AllTests {

}
