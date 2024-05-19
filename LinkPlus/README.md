 
LinkPlus Application - README

Overview
LinkPlus is a Spring Boot application designed to manage a banking system. 
It allows for the creation of user accounts,
 performing transactions (deposits, withdrawals, and transfers), 
and maintaining transaction records. 
This application uses Java 22 and an H2 in-memory database for data storage.

Prerequisites
Java 22
Maven 4.0.0 or higher
Features
Create a bank and accounts: Set up a new bank and create multiple user accounts.
Perform transactions: Execute deposits, withdrawals, and transfers between accounts
 with applicable fees.
View transaction records: Check the transaction history for any account.
Account management: Monitor account balances and list all accounts within the bank.
Fee management: Manage flat and percentage-based transaction fees.



Configuration
The application uses an H2 in-memory database. You can access the H2 console 
at http://localhost:9090/h2-console with the default credentials 
provided in application.properties.

Project Structure
src/main/java/com/BankSystem/LinkPlus: Contains the main application and business logic.
src/main/resources: Contains the application properties and H2 database configuration.
Dependencies
Spring Boot Starter Data JPA: For database operations.
Spring Boot Starter Web: For building web, including RESTful, applications.
H2 Database: In-memory database for development and testing.
Lombok: For reducing boilerplate code.
Spring Boot Starter Test: For testing the application.





Contact
For any queries or issues, please contact Donjete Zogaj at donjetazogaj2@gmail.com.