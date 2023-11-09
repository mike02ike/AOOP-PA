
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

public class TestCaseForVenueFactory {

  /**
   * Test case for creating a stadium object
   */
  @Test
  public void testValidStadium() {
    VenueFactory factory = new VenueFactory();
    Stadium stadiumBlank = new Stadium();

    assertEquals(stadiumBlank.getClass(), factory.createVenue("Stadium").getClass());
  }

  /**
   * Test case for creating an arena object
   */
  @Test
  public void testValidArena() {
    VenueFactory factory = new VenueFactory();
    Arena arenaBlank = new Arena();

    assertEquals(arenaBlank.getClass(), factory.createVenue("Arena").getClass());
  }

  /**
   * Test case for creating an auditorium object
   */
  @Test
  public void testValidAuditorium() {
    VenueFactory factory = new VenueFactory();
    Auditorium auditoriumBlank = new Auditorium();

    assertEquals(auditoriumBlank.getClass(), factory.createVenue("Auditorium").getClass());
  }

  /**
   * Test case for creating an open air object
   */
  @Test
  public void testValidOpenAir() {
    VenueFactory factory = new VenueFactory();
    OpenAir openAirBlank = new OpenAir();

    assertEquals(openAirBlank.getClass(), factory.createVenue("Open Air").getClass());
  }

  /**
   * Test case for handling misinput
   */
  @Test
  public void testInvalidInput() {
    VenueFactory factory = new VenueFactory();

    assertEquals(null, factory.createVenue("Arna"));
  }
}