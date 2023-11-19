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
 * Instructor: Daniel Mejia
 * <p>
 * @since 11/08/2023
 * @author Michael Ike
 * @author Erik LaNeave
 * @version 1.2
 */

import java.util.*;

/**
 * The TicketMiner class manages ticket fees and revenue calculation for events.
 */
public class TicketMiner {
    // Attributes
    private static TicketMiner obj; // Singleton instance
    private double convenienceFee = 2.50; // Default convenience fee
    private double serviceFee; // Service fee
    private double charityFee; // Charity fee
    private double totalRevenue = 0.0; // Total revenue generated
    // private double convenienceRevenue = 0.0; // Total revenue generated from convenienceFees
    // private double serviceRevenue = 0.0; // Total revenue generated from serviceFees
    // private double charityRevenue = 0.0; // Total revenue generated from charityFees
    private HashMap<Integer, HashMap<String, Double>> collectedFees = new HashMap<>(); // Collected fees for events
    HashMap<String, Double> eventMap; // Inner map to store fee types for each event

    // Constructors
    private TicketMiner() {
    } // Private constructor for singleton pattern

    // Methods

    /**
     * Returns the singleton instance of TicketMiner.
     * 
     * @return The TicketMiner singleton instance.
     */
    public static synchronized TicketMiner getInstance() {
        if (obj == null) {
            obj = new TicketMiner();
        }
        return obj;
    }

    public HashMap<Integer, HashMap<String, Double>> getCollectedFees() {
        return this.collectedFees;
    }

    /**
     * Calculates and returns the total fees for an event based on ticket amount and
     * price.
     * 
     * @param ticketAmount The number of tickets sold.
     * @param ticketPrice  The price per ticket.
     * @return The total fees (convenience, service, and charity) for the given
     *         ticket amount and price.
     */
    public double getAllFees(int ticketAmount, double ticketPrice) {
        return getConvenienceFee() + getServiceFee(ticketAmount, ticketPrice)
                + getCharityFee(ticketAmount, ticketPrice);
    }

    /**
     * Returns the convenience fee.
     * 
     * @return The convenience fee.
     */
    public double getConvenienceFee() {
        return convenienceFee;
    }

    /**
     * Calculates and returns the service fee based on the number of tickets and
     * ticket price.
     * 
     * @param ticketAmount The number of tickets.
     * @param ticketPrice  The price per ticket.
     * @return The service fee.
     */
    public double getServiceFee(int ticketAmount, double ticketPrice) {
        serviceFee = ticketAmount * ticketPrice * .005;
        return serviceFee;
    }

    /**
     * Calculates and returns the charity fee based on the number of tickets and
     * ticket price.
     * 
     * @param ticketAmount The number of tickets.
     * @param ticketPrice  The price per ticket.
     * @return The charity fee.
     */
    public double getCharityFee(int ticketAmount, double ticketPrice) {
        charityFee = ticketAmount * ticketPrice * .0075;
        return charityFee;
    }

    /**
     * Initializes the collected fees for each event.
     * If the eventId's inner HashMap doesn't exist, it creates a new one and
     * initializes specific fee types for the eventId.
     */
    public void initializeCollectedFees(LinkedHashMap<Integer, Event> eventMap) {
        for (Integer eventId : eventMap.keySet()) {
            HashMap<String, Double> feesMap = new HashMap<>();
            feesMap.put("convenience", 0.0);
            feesMap.put("service", 0.0);
            feesMap.put("charity", 0.0);
            collectedFees.put(eventId, feesMap);
        }
    }

    /**
     * Updates the convenience fees for a specific event based on the convenience
     * fee value.
     * 
     * @param eventId The ID of the event.
     */
    public void updateConvenienceFees(int eventId) {
        eventMap = collectedFees.get(eventId);
        double currentConvenienceFees = eventMap.get("convenience");
        double updatedConvenienceFees = currentConvenienceFees + convenienceFee;
        eventMap.put("convenience", updatedConvenienceFees);
        collectedFees.put(eventId, eventMap);
    }

    /**
     * Updates the service fees for a specific event based on the number of tickets
     * and ticket price.
     * 
     * @param eventId   The ID of the event.
     * @param serviceIn
     */
    public void updateServiceFees(int eventId, double serviceIn) {
        eventMap = collectedFees.get(eventId);
        double currentServiceFees = eventMap.get("service");
        double updatedServiceFees = currentServiceFees + serviceIn;
        eventMap.put("service", updatedServiceFees);
        collectedFees.put(eventId, eventMap);
    }

    /**
     * Updates the charity fees for a specific event based on the number of tickets
     * and ticket price.
     * 
     * @param eventId   The ID of the event.
     * @param charityIn
     */
    public void updateCharityFees(int eventId, double charityIn) {
        eventMap = collectedFees.get(eventId);
        double currentCharityFees = eventMap.get("charity");
        double updatedCharityFees = currentCharityFees + charityIn;

        eventMap.put("charity", updatedCharityFees);
        collectedFees.put(eventId, eventMap);
    }

    /**
     * Computes the total revenue earned from convenience fees across all events.
     * 
     * @return The total revenue generated from convenience fees from all events.
     */
    public double computeConvenienceRevenue() {
        double temp;
        double computedConvenience = 0.0;
        for (HashMap<String, Double> eventMap : collectedFees.values()) {
            temp = eventMap.get("convenience");
            computedConvenience += temp;
        }
        return computedConvenience;
    }

    /**
     * Computes the total revenue earned from service fees across all events.
     * 
     * @return The total revenue generated from service fees from all events.
     */
    public double computeServiceRevenue() {
        double temp;
        double computedService = 0.0;
        for (HashMap<String, Double> eventMap : collectedFees.values()) {
            temp = eventMap.get("service");
            computedService += temp;
        }
        return computedService;
    }

    /**
     * Computes the total revenue earned from chairty fees across all events.
     * 
     * @return The total revenue generated from chairty fees from all events.
     */
    public double computeCharityRevenue() {
        double temp;
        double computedCharity = 0.0;
        for (HashMap<String, Double> eventMap : collectedFees.values()) {
            temp = eventMap.get("charity");
            computedCharity += temp;
        }
        return computedCharity;
    }

    /**
     * Computes the total revenue earned from collected fees across all events.
     * 
     * @return The total revenue generated from all events.
     */
    public double computeRevenue() {
        for (HashMap<String, Double> eventMap : collectedFees.values()) {
            for (double fee : eventMap.values()) {
                totalRevenue += fee;
            }
        }
        return totalRevenue;
    }
}