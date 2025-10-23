# Developer Guide

## Acknowledgements

{list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well}

## Design & implementation

{Describe the design and implementation of the product. Use UML diagrams and short code snippets where applicable.}

---
### Feature: Time and Schedule Management
#### Design
Each activity contains date and time information, 
this feature enables the application to store, sort, and manage trip schedules based on time.  

#### Implementation
It uses Java's built-in `java.time.LocalDateTime` library and other related libraries
to store time-related variables, to facilitate the standardization of time format and subsequent operations.
It also introduces a `schedule` command to compare the order of all activities and sort the output in chronological order

#### Code
Time-related libraries used:
```
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
```

The core code of the `schedule` command:
```
list.sort(Comparator.comparing(a -> a.getDateTimeObject().getDateTime()));
```
---
### Feature: Data Storage and Loading
#### Design
This feature is responsible for storing activity and budget data, 
as well as loading previously saved data when the application starts.
It ensures that user data is retained between application sessions
by saving to files and retrieving from them.

#### Implementation
It uses Java's I/O libraries to handle file operations, 
including creating necessary directories, writing data to files, and reading data from files.
The Storage class handles saving activities and budget data into text files in a structured format (using "|" as a delimiter), 
while the Load class handles parsing these text files and reconstructing the application's data structures with validation for data integrity.

---

### Feature: Budget Management

#### Design

The budget feature enables users to set a total budget and track spending during trip planning. 
It helps monitor total expenses and remaining funds as activities are added or removed.


#### Implementation

The feature is implemented in the BudgetPlan class, 
which stores the total budget and records each expense added by the user. 
It validates inputs to prevent negative or invalid budget values and uses assertions to ensure data integrity.

---

### Feature: Adding to list

#### Design

The lets a user add an activity to their travel itinerary with a date, time, cost and description.
It provides a way for users to build up a complete trip schedule activity by activity.

#### Implementation

The feature is implemented in through the addActivityDataToList method in the BusyBreak class.
User input is parsed through getParsedActivityData to extract date,time,cost and description from a User's input.
This then creates an Activity object which is stored in an ArrayList.

---

## Product scope
### Target user profile

{Describe the target user profile}

### Value proposition

{Describe the value proposition: what problem does it solve?}

## User Stories

| Version                        | As a ...  | I want to ...             | So that I can ...                                           |
|--------------------------------|-----------|---------------------------|-------------------------------------------------------------|
| seedu.busybreak.BusyBreak.v1.0 | new user  | see usage instructions    | refer to them when I forget how to use the application      |
| v2.0                           | user      | find a to-do item by name | locate a to-do without having to go through the entire list |

## Non-Functional Requirements

{Give non-functional requirements}

## Glossary

* *glossary item* - Definition

## Instructions for manual testing

{Give instructions on how to do a manual product testing e.g., how to load sample data to be used for testing}
