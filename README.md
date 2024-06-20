TicketMiner

Project Contributors: Anaiah Quinn, Erik LaNeave, Michael Ike, and Ian Gutierrez

Completion Date: November 26, 2023

Course Information: Dr. Daniel Meija, The University of Texas at El Paso


1. Program Explanation

This assignment aimed to create a system to facilitate selling tickets to events (sports games, festivals, and concerts). We developed a command-line interface program that imports event and customer information from CSV files and allows customers to purchase tickets. The program can also read a CSV file containing purchase information and automate said purchases. It includes features for admins to find administrative information on events and create new events from the console. Additionally, the program is equipped with an electronic invoice summary feature. Information about the customers (money available, tickets purchased, etc.) and events (available seats, revenue, etc.) is calculated and stored while the program runs; upon termination, all information is written to new CSV files.

2. What Did We Learn?

As a result of this assignment, we learned about the importance of space complexity. Space complexity involves the amount of memory our program uses to perform algorithms and store information. Our solution's space complexity can be improved by storing some information differently to decrease heap utilization and the amount of Java heap memory being used. We could also consider moving information to a database. We managed our time well by starting the assignment immediately, breaking it into tasks, assigning them, and updating the use cases. We then added the new functionalities, updated our diagrams, and finished the use case scenarios.

3. Solution Design

We finalized the first deliverable of a ticket-purchasing interface designed for TicketMiner. The system operates as a ticket sales company specializing in various events, including sporting events, concerts, and special occasions. The interface facilitates various functionalities for customers, such as browsing events, accessing and saving their invoices, as well as purchasing and canceling tickets. For administrators, it offers capabilities like adding or checking events, running an automated ticket-purchasing tool, canceling events, computing company profits, and storing invoices for customers. Upon program termination, the system updates the Event and Customer list .csv files with the latest information. Additionally, it generates a summary of the program's operations, logging it into a .txt file. Furthermore, invoices are produced for each user who logged in during the session.

4. Testing

The program was tested using a combination of white-box and black-box testing. Both methods were used to test various functionalities like auto purchase for the admin, ticket purchasing, ticket and event cancellation, new event creation, and general program updates. Tests consisted of various random and incorrect inputs to ensure the program runs under any condition. The JUnit framework was used for testing, ensuring robustness and error handling.

5. Test Results

The JUnit tests confirmed that the program handles given test cases without errors and processes correct and incorrect inputs gracefully. The customer menu, invoice menu, admin menu, and event-related functionalities were all tested successfully.

6. Demo with Classmates

During the demo with other teams in class, we tested each other's code without seeing the source code. This allowed us to focus on the user experience, UI menus, and error handling. The demo helped us discuss and improve the program's functionality.

7. Code Review

We completed our code review by ensuring compliance with the code review checklist. Our code meets all implementation requirements, handles misinputs gracefully, and is well-structured and documented. The code follows Java standards and is dynamically coded for easy maintenance. Most of the program runs in constant time, with occasional O(n^2) complexity due to nested loops, which is mitigated by caching.
