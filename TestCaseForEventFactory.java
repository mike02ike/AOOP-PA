
/**
 * @author Ian Gutierrez
 *         Date: 11/6/2023
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

import static org.junit.Assert.*;
import org.junit.Test;

public class TestCaseForEventFactory {

  /**
   * Test case for creating a sport object
   */
  @Test
  public void testValidSport() {
    EventFactory factory = new EventFactory();
    Sport sportBlank = new Sport();

    assertEquals(sportBlank.getClass(), factory.createEvent("Sport").getClass());
  }

  /**
   * Test case for creating a concert object
   */
  @Test
  public void testValidConcert() {
    EventFactory factory = new EventFactory();
    Concert concertBlank = new Concert();

    assertEquals(concertBlank.getClass(), factory.createEvent("Concert").getClass());
  }

  /**
   * Test case for creating a festival object
   */
  @Test
  public void testValidFestival() {
    EventFactory factory = new EventFactory();
    Festival festivalBlank = new Festival();

    assertEquals(festivalBlank.getClass(), factory.createEvent("Festival").getClass());
  }

  /**
   * Test case for handling misinput
   */
  @Test
  public void testInvalidInput() {
    EventFactory factory = new EventFactory();

    assertEquals(null, factory.createEvent("Spor"));
  }

}
