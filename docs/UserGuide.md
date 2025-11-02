# User Guide

## Introduction

BusyBreak is a travel itinerary management application
that helps you **organize and track your travel activities, schedules, and costs**.

## Table of Contents
- [Quick Start](#quick-start)
- [Managing Activities](#managing-activities)
  - [Adding an activity: add](#adding-an-activity-add)
  - [Finding an activity: find](#finding-an-activity-find)
  - [Editing an activity: edit](#editing-an-activity-edit)
  - [Listing all activities: list](#listing-all-activities-list)
  - [Viewing Activities by Date: view](#viewing-activities-by-date-view)
  - [Deleting an activity: delete](#deleting-an-activity-delete)
- [Managing Budget](#managing-budget)
  - [Setting the Total Budget: budget set](#setting-the-total-budget-budget-set)
  - [Adding an Expense: budget add](#adding-an-expense-budget-add)
  - [Activity Expense Restriction](#-activity-expense-restriction)
  - [Listing Expenses: budget list](#listing-all-activities-list)
  - [Deleting an Expense: budget delete](#deleting-an-expense-budget-delete)
  - [Restricting Edits to Activity-Linked Expenses](#-restricting-edits-to-activity-linked-expenses)
  - [Changing Expense Category: budget setcat](#changing-expense-category-budget-setcat)
  - [Syncing Expenses with Activities: budget sync](#syncing-expenses-with-activities-budget-sync)
  - [Viewing Spending by Category: breakdown](#viewing-spending-by-category-breakdown)
- [Managing Trips](#managing-trips)
  - [Adding a trip: trip add](#adding-a-trip-trip-add)
  - [Listing all trips: trip list](#listing-all-trips-trip-list)
  - [Deleting a trip: trip delete](#deleting-a-trip-trip-delete)
- [Other Commands](#other-commands)
  - [Sorting Activities by Time: schedule](#sorting-activities-by-time-schedule)
  - [Checking activities and trips between two dates: check](#checking-activities-and-trips-between-two-dates-check)
  - [Clearing Data: clear](#clearing-data-clear)
  - [Undo an action: undo](#undo-an-action-undo)
  - [Exiting the program: exit](#exiting-the-program-exit)
- [Command summary](#command-summary)

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest BusyBreak.jar file from the releases page.
3. Open a command terminal and navigate to the folder containing the jar file,
   run `java -jar BusyBreak.jar` to start the application.

## Features
Notes:
* Uppercase letters in commands indicate parameters provided by the user.
For example, `delete INDEX` means user should input something like `delete 1`
* Please do not use the "|" symbol in your input, 
as we use it as a separator in saved files, and using this symbol will cause confusion.
## Managing Activities
The activity includes date, time, description and cost.

---

### Adding an activity: `add`

Adds a new activity to your travel itinerary
with date, time, description, and cost information.<br/>
Each activity is automatically saved to your itinerary, which can be viewed,edited,sorted or deleted later.

Format: `add d/DATE t/TIME desc/DESCRIPTION c/COST`<br/>

* The `DATE` should use the format `yyyy-MM-dd`
    * Example : `2025-01-01` for `January 1st 2025`


* The `TIME` should use the 24 hour time format `HH:mm`
    * Example : `16:30` for `4.30pm`


* The unit of `COST` is the Singapore dollar.<br/>
    * Should be a non-negative number
    * use 0 for free activities
    * Example : `6.70, 1000, 0`

Examples:<br/>

* `add d/2025-01-01 t/10:00 desc/Visit museum c/10`

Expected output:

```
______________________________________________________________________
Added Activity to Itinerary: Date: 2025-01-01 | Time: 10:00 | Description: Visit museum | Cost: $10
______________________________________________________________________
```

* `add d/2025-10-12 t/19:00 desc/Watch the sunset c/0`

Expected output:

```
______________________________________________________________________
Added Activity to Itinerary: Date: 2025-10-12 | Time: 19:00 | Description: Watch the sunset | Cost: $0
______________________________________________________________________
```

### Finding an activity: `find`

Searches for an activity by keyword.

Format: `find KEYWORD`

* the keyword is not case-sensitive
* not providing a keyword functions the same as `list`

Examples:

* `find sightsee`

Expected output:

```
______________________________________________________________________
Here are the activities matching your keyword
______________________________________________________________________
2. 
Date: 2025-10-11
Time: 23:59
Description: sightsee
Cost: $67
______________________________________________________________________
```

### Editing an activity: `edit`

Edits an existing activity in the list using its index number.<br/>
Format: `edit INDEX d/DATE t/TIME desc/DESCRIPTION c/COST`<br/>

* The `INDEX` is the integer displayed in the itinerary list.
* The `DATE` should use the format `yyyy-MM-dd`
* The `TIME` should use the format `HH:mm`
* The unit of `COST` is the Singapore dollar.<br/>

At least one field (d/DATE, t/TIME, desc/DESCRIPTION, c/COST) must be provided.<br/>
If inputting more than one field, they can be in any order.<br/>

Examples:<br/>

* `edit 1 d/2025-04-20 c/67`
* `edit 2 t/08:46 desc/Visit historical site d/2025-09-11`

### Listing all activities: `list`

Displays all activities in the itinerary.<br/>
Format: `list`<br/>

Example: `list`

Expected output:

```
______________________________________________________________________
1. 
Date: 2025-01-01
Time: 10:00
Description: Visit museum
Cost: $10
______________________________________________________________________
2. 
Date: 2025-10-12
Time: 19:00
Description: Watch the sunset
Cost: $0
______________________________________________________________________
```

### Viewing Activities by Date: `view`

Shows all activities scheduled for a specific date.
If there is no date after `view`, the effect is the same as `list`.<br/>
Format: `view DATE` or `view`<br/>

* The `DATE` should use the format 'yyyy-HH-mm'.<br/>

Example:<br/>

* `view`
* `view 2025-01-01`

### Deleting an activity: `delete`

Removes an activity from your itinerary by its index number.<br/>
Format: `delete INDEX`<br/>

* The `INDEX` is the integer displayed in the itinerary list.<br/>

Example:<br/>

* `delete 1`

Expected outcome:

```
______________________________________________________________________
Deleted activity from Itinerary: 
1. a
______________________________________________________________________
```

---

## Managing Budget

The **Budget feature** allows you to track and categorize travel expenses and view how much budget remains.<br/>

---

### Setting the Total Budget: `budget set`

Sets the total budget amount for your trip.<br/>
This represents the **maximum amount** you plan to spend across all expenses.<br/>

Format: `budget set AMOUNT`<br/>

* The **AMOUNT** should be a positive number.<br/>
* The unit of **AMOUNT** is the Singapore dollar (SGD).<br/>

Examples:<br/>

* `budget set 500`<br/>

Expected output:<br/>

```
----------------------------------------------------------------------
Budget set to $500.00
----------------------------------------------------------------------
```

If a new value is set, the remaining budget will be recalculated automatically.<br/>

---

### Adding an Expense: `budget add`

Adds a new expense to the budget list.<br/>
Format: `budget add n/NAME c/COST cat/CATEGORY`<br/>

* The **NAME** is a short label for the expense.<br/>
* The **COST** should be a positive number (in SGD).<br/>
* The **CATEGORY** is optional — if omitted, it defaults to `Uncategorized`.<br/>

Examples:<br/>

* `budget add n/Snacks c/5 cat/Food`<br/>
* `budget add n/Taxi c/12.50 cat/Transport`<br/>
* `budget add n/Souvenirs c/20`<br/>

---

### ⚠ Activity Expense Restriction

Expenses in the **Activity** category **must be created via Activity commands**, because activities require a date and
time.<br/>

If the user tries to add an Activity expense through `budget add`, the system blocks it and suggests the correct
format.<br/>

---

### Listing Expenses: `budget list`

Displays all recorded expenses with total spent and remaining budget.<br/>
Format: `budget list`<br/>

Example output:<br/>

---

### Deleting an Expense: `budget delete`

Removes an expense by its index number.<br/>
Format: `budget delete INDEX`<br/>

* The **INDEX** is the number shown in `budget list`.<br/>

Example:<br/>

* `budget delete 2`

### 🛑 Restricting Edits to Activity-Linked Expenses

Expenses in the **Activity** category are *linked to activities in your itinerary*.<br/>
Because these expenses depend on **date**, **time**, and **description** stored in the activity list,
they **cannot be edited directly through `budget` commands**.<br/>

If the user attempts to **delete** an Activity-linked expense through `'budget delete'`,
the system blocks it and suggests the correct command:<br/>
---

### Changing Expense Category: `budget setcat`

Updates the category of an existing expense.<br/>
Format: `budget setcat INDEX NEW_CATEGORY`<br/>

Example:<br/>

* `budget setcat 1 Travel`<br/>

**Note:** If the expense was created from an Activity, its category **cannot** be changed here.
Edit the Activity instead using `edit` from the Activity command set.

---

### Syncing Expenses with Activities: `budget sync`

Ensures that new activities are automatically reflected in the budget.<br/>
Format: `budget sync`<br/>

Example:<br/>

* `budget sync`<br/>

---

### Viewing Spending by Category: `breakdown`

Displays your expenses grouped by category, along with total amount spent in each category.  
This helps you understand where most of your spending is going (similar to a summary view).<br/>

Format: `breakdown`<br/>

Example output:<br/>

```
----------------------------------------------------------------------
Spending by category:
- Food: $35.50
- Transport: $22.00
- Activity: $68.50
- Lodging: $120.00

Total Spent: $246.00 | Remaining Budget: $154.00
----------------------------------------------------------------------
```

*Categories are automatically normalized.*  
For example, `snacks`, `meal`, and `dinner` are grouped as **Food**,  
and `taxi`, `bus`, and `train` are grouped as **Transport**.<br/>

---
## Managing Trips
The **Trip feature** allows you to track travel trips 
with start/end time and transport information.

### Adding a trip: `trip add`
Adds a new trip to the trip list.<br/>
Format: `trip add sd/START_DATE st/START_TIME ed/END_DATE et/END_TIME by/TRANSPORT`
* The `START_DATE` and `END_DATE` should use the format `yyyy-MM-dd`
* The `START_TIME` and `END_TIME` should use the format `HH:mm`
* TRANSPORT specifies the transport mode for the trip (e.g., plane, train, car).<br/>

Examples:<br/>
* `trip add sd/2025-01-01 st/15:00 ed/2025-01-01 et/20:00 by/plane`
* `trip add sd/2025-02-10 st/17:30 ed/2025-02-11 et/08:00 by/train`


### Listing all trips: `trip list`
Displays all recorded trips.<br/>
Format: `trip list`


### Deleting a trip: `trip delete`
Removes a trip from the trip list by its index number.<br/>
Format: `trip delete INDEX`
* `INDEX` is the integer displayed in the trip list (from trip list)


---

## Other Commands
### Sorting Activities by Time: `schedule`

Sorts all activities by time and display them.<br/>
Format: `schedule`<br/>
Expected outcome:

Sorts all activities or trips by time and display them.<br/>
For trips, if there are time conflicts between trips
(a trip starts before another ends), sorting is blocked with a warning.<br/>
Formats: 
* Use `schedule` for activities
* Use `schedule trip` for trips

Expected outcome for `schedule`:
```
______________________________________________________________________
Your Activities are sorted by time now!
______________________________________________________________________
1. 
Date: 2024-12-31
Time: 10:00
Description: b
Cost: $2
______________________________________________________________________
2. 
Date: 2025-01-01
Time: 00:00
Description: a
Cost: $1
______________________________________________________________________
3. 
Date: 2025-06-01
Time: 19:00
Description: d
Cost: $3
______________________________________________________________________
```


### Checking activities and trips between two dates: `check`
Displays all activities and trips scheduled between two specified dates (inclusive).
For trips, it counts the trip's start date.
If the two date are the same, it will output items on that day.<br/>
Format: `check from/START_DATE to/END_DATE`
* `START_DATE` and `END_DATE` must use the format `yyyy-MM-dd`
* `START_DATE` cannot be later than `END_DATE`<br/>

Examples:
* `check from/2025-01-01 to/2025-01-01`
* `check from/2024-12-30 to/2025-01-30`

### Clearing Data: `clear`
Removes data based on the specified scope.<br/>
Formats:
* `clear`: Clears all activities in the itinerary
* `clear budget`: Clears all budget entries and set the total budget as 0
* `clear trip`: Clears all trips
* `clear all`: Clears all activities, budget entries, and trips
* `clear before yyyy-MM-dd`: Clears activities and trips scheduled on or before the specified date<br/>

Examples:
* `clear before 2025-01-01`

### Undo an action: `undo`
Undo an action that was done earlier.<br/>
Format:
* `undo`

  Expected outcome for `schedule`:
```
undo
______________________________________________________________________
Undid the last change.
______________________________________________________________________
```

### Exiting the program: `exit`

Exits the application.<br/>
Format: `exit`<br/>

## Command summary

| Command                                                 | Format                                                                |
|---------------------------------------------------------|-----------------------------------------------------------------------|
| Adding activities                                       | `add d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST`                    |
| Editing activities                                      | `edit INDEX d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST`             |
| Listing activities                                      | `list`                                                                |
| Viewing activities by date                              | `view` or `view yyyy-MM-dd`                                           |
| Deleting activities                                     | `delete INDEX`                                                        |
| Sorting activities by time                              | `schedule`                                                            |
| Sorting trips by time                                   | `schedule trip`                                                       |
| Adding a trip                                           | `trip add sd/yyyy-MM-dd st/HH:mm ed/yyyy-MM-dd et/HH:mm by/TRANSPORT` |
| Listing all trips                                       | `trip list`                                                           |
| Deleting a trip                                         | `trip delete INDEX`                                                   |
| Checking activities and trips between two dates         | `check from/yyyy-MM-dd to/yyyy-MM-dd`                                 |
| Clearing all activities                                 | `clear`                                                               |
| Clearing all budget                                     | `clear budget`                                                        | 
| Clearing all trips                                      | `clear trip`                                                          | 
| Clearing all activities, budget and trips               | `clear all`                                                           |
| Clearing all activities and trips on or before the date | `clear before yyyy-MM-dd`                                             |
| Display spending by category                            | `breakdown`                                                           |
| Finding an item via keyword                             | `find KEYWORD`                                                        |
| Exiting the program                                     | `exit`                                                                |
| Set budget                                              | `budget set AMOUNT`                                                   |
| Add an expense                                          | `budget add n/NAME c/COST cat/CATEGORY`                               |
| List expenses                                           | `budget list`                                                         |
| Delete an expense                                       | `budget delete INDEX`                                                 |
| Change expense category                                 | `budget setcat INDEX NEW_CATEGORY`                                    |
| Sync budget with activities                             | `budget sync`                                                         |
| Undo an action                                          | `undo`                                                                |

