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
 * Class Purpose: Finds the venu type based on given string
 * <p>
 * Last Change: 10/21/2023
 * @author Erik LaNeave and Ian Gutierrez
 * @version 1.0
*/

public class VenueFactory {

  /**
   * Constructor
   */
  public VenueFactory() {

  }

  /**
   * Creates appropriate venue type
   * 
   * @param venueType Venue type
   * @return appropriate venue object
   */
  public Venue createVenue(String venueType) {
    switch (venueType) {
      case "Stadium":
        return new Stadium();

      case "Arena":
        return new Arena();

      case "Auditorium":
        return new Auditorium();

      case "Open Air":
        return new OpenAir();

      default:
        return null;
    }
  }
}
