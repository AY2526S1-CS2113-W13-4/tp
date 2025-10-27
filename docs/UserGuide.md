# User Guide

## Introduction

BusyBreak is a travel itinerary management application 
that helps you **organize and track your travel activities, schedules, and costs**.

## Quick Start

1. Ensure that you have Java 17 or above installed.
2. Download the latest BusyBreak.jar file from the releases page.
3. Open a command terminal and navigate to the folder containing the jar file, 
run `java -jar BusyBreak.jar` to start the application.

## Features
### Adding an activity: `add`
Adds a new activity to your travel itinerary 
with date, time, description, and cost information.<br/>
Format: `add d/DATE t/TIME desc/DESCRIPTION c/COST`<br/>
* The `DATE` should use the format `yyyy-MM-dd`
* The `TIME` should use the format `HH:mm`
* The unit of `COST` is the Singapore dollar.<br/>

Examples:<br/>
* `add d/2025-01-01 t/10:00 desc/Visit museum c/10`
* `add d/2025-10-12 t/19:00 desc/Watch the sunset c/0`


### Editing an activity: `edit`
Edits an existing activity in the list using its index number.<br/>
Format: `edit INDEX d/DATE t/TIME desc/DESCRIPTION c/COST`<br/>
* The `INDEX` is the integer displayed in the itinerary list.
* The `DATE` should use the format `yyyy-MM-dd`
* The `TIME` should use the format `HH:mm`
* The unit of `COST` is the Singapore dollar.<br/>

Examples:<br/>
* `edit 1 d/2025-04-20 t/11:11 desc/Go to theme park c/67`
* `edit 2 d/2025-09-11 t/08:46 desc/Visit historical site c/67`


### Listing all activities: `list`
Displays all activities in the itinerary.<br/>
Format: `list`<br/>


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

Expenses in the **Activity** category **must be created via Activity commands**, because activities require a date and time.<br/>

If the user tries to add an Activity expense through `budget add`, the system blocks it and suggests the correct format.<br/>

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

### Budget Command Summary

| Command                     | Format                                  |
|-----------------------------|-----------------------------------------|
| Set budget                  | `budget set AMOUNT`                     |
| Add an expense              | `budget add n/NAME c/COST cat/CATEGORY` |
| List expenses               | `budget list`                           |
| Delete an expense           | `budget delete INDEX`                   |
| Change expense category     | `budget setcat INDEX NEW_CATEGORY`      |
| Sync budget with activities | `budget sync`                           |

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

### Sorting Activities by Time: `schedule`
Sorts all activities by time and display them.<br/>
Format: `schedule`<br/>
Expected outcome:
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


### Exiting the program: `exit`
Exits the application.<br/>
Format: `exit`<br/>

## Command summary

| Command                      | Format                                                    |
|------------------------------|-----------------------------------------------------------|
| Adding activities            | `add d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST`        |
| Editing activities           | `edit INDEX d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST` |
| Listing activities           | `list`                                                    |
| Viewing activities by date   | `view` or `view yyyy-MM-dd`                               |
| Deleting activities          | `delete INDEX`                                            |
| Sorting activities by time   | `schedule`                                                |
| Display spending by category | `breakdown`                                               |
| Exiting the program          | `exit`                                                    |

