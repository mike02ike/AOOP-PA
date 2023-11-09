/*
 * Honesty Statement
 * This work was done individually and completely on my own. I did not share, reproduce, or alter
 * any part of this assignment for any purpose. I did not share code, upload this assignment online
 * in any form, or view/received/modified code written from anyone else. All deliverables were
 * produced entirely on my own. This assignment is part of an academic course at The University
 * of Texas at El Paso and a grade will be assigned for the work I produced.
*/

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Course: Adv. Object-Oriented Programming
 * <p>
 * Instructor: Daniel Mejia
 * <p>
 * Class Purpose: The Log class is used to maintain the record of what
 * has changed in the Sport classes and to write to the log file when the program terminates
 * <p>
 * Last Change: 10/29/2023
 * @author Erik LaNeave
 * @version 1.3
*/

public class Log {
    //Attributes
    private static Log obj;
    private File recordFile;
    private String fileName = "Log.txt";
    private FileWriter recordWrite;
    //String that is updated during the run of the program and stores the changes made
    private String record = "";

    /**
     * Constructor makes the log.txt file when a instance of the object is made
     */
    private Log() {
        logFileMake();
    }

    /**
     * @return obj of type RecordMake
     */
    public static synchronized Log getInstance() {
        if (obj == null) {
            obj = new Log();
        }
        return obj;
    }

    //Methods
    /**
     * Used to create the log.txt file
     */
    public void logFileMake() {
        //Creates the file object for log.txt
        this.recordFile = new File(this.fileName);
        //Try-catch used when making the new log.txt file
        try {
            recordFile.createNewFile();
        } catch (IOException e) {
            System.out.println("File could not be made");
        }
    }

    /**
     * Writes the record string to the log.txt file when the use terminates the program
     */
    public void writeLogFile() {
        //Try-catch used incase of an error when writing
        try {
            //Creates the fileWriter object
            this.recordWrite = new FileWriter(this.fileName);
            //Writes the information
            this.recordWrite.write(this.record);
            //Closes the file
            this.recordWrite.close();
        } catch (IOException e) {
            System.out.println("Error when trying to write to the file");
        }
    }

    /**
     * Takes a change made and adds to the record string
     * @param toBeSaved
     */
    public void save(String toBeSaved) {
        this.record = this.record + toBeSaved;
    }

    /**
     * @return a string that is the time in the MM-dd-yyyy HH:mm:ss format
     */
    public String time(){
        LocalDateTime timeDate = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm:ss");
        return timeDate.format(timeFormat);
    }
}
