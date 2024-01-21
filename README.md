# Open doors

This repository contains a Booking service project for a Spring Boot application, called "Open Doors". The project uses Maven as its build tool.

## Technologies used

Java (version 8 or higher)

Maven

Tomcat

PostgreSQL database

## Instructions

To run the project on your device, simply clone the repository and run OpenDoorsApplication.java in your environment of choice.

The application runs at http://localhost:9090.

## Project Structure

The project structure is designed in the MVC pattern, with a Package-by-layer architecture.

Images used in the application are stored locally in the 'path' attribute of the FileRepository.java class.
You can change the file destination according to your PC's file system.

All the dependencies the application uses are defined in the pom.xml file.

## Testing

Unit and integrated tests are implemented in the h2 in-memory database.

E2E tests are done using the Selenium framework.
