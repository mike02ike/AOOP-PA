/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.util.HashMap;
/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Class Purpose: Runs all test classes
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * 
 * @since 11/08/2023
 * @author Michael Ike
 * @version 1.0
 */

public class TicketMiner{
    //attributes
    private static TicketMiner obj; //singleton
    private double convenienceFee = 2.50;
    private double serviceFee;
    private double charityFee;
    private HashMap<Integer, HashMap<String, Double>> collectedFees = new HashMap<>();

    //constructors
    private TicketMiner(){}

    //methods
    public static synchronized TicketMiner getInstance(){
        if(obj == null){
            obj = new TicketMiner();
        }
        return obj;
    }

    public double getAllFees(int ticketAmount, double ticketPrice){
        return getConvenienceFee() + getServiceFee(ticketAmount, ticketPrice) + getCharityFee(ticketAmount, ticketPrice);
    }

    public double getConvenienceFee() {
        return convenienceFee;
    }

    public double getServiceFee(int ticketAmount, double ticketPrice) {
        serviceFee = ticketAmount * ticketPrice * .005;
        return serviceFee;
    }

    public double getCharityFee(int ticketAmount, double ticketPrice) {
        charityFee = ticketAmount * ticketPrice * .0075;
        return charityFee;
    }

    public void initializeCollectedFees() {
        // Initialize the inner HashMaps for each eventId
        for (Integer eventId : collectedFees.keySet()) {
            // Create a new inner HashMap for each eventId if it doesn't exist
            if (!collectedFees.containsKey(eventId)) {
                collectedFees.put(eventId, new HashMap<>());
            }
            // Initialize specific fee types for the eventId
            collectedFees.get(eventId).put("convenience", 0.0);
            collectedFees.get(eventId).put("service", 0.0);
            collectedFees.get(eventId).put("charity", 0.0);
        }
    }

}