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

| Command                    | Format                                                    |
|----------------------------|-----------------------------------------------------------|
| Adding activities          | `add d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST`        |
| Editing activities         | `edit INDEX d/yyyy-MM-dd t/HH:mm desc/DESCRIPTION c/COST` |
| Listing activities         | `list`                                                    |
| Viewing activities by date | `view` or `view yyyy-MM-dd`                               |
| Deleting activities        | `delete INDEX`                                            |
| Sorting activities by time | `schedule`                                                |
| Exiting the program        | `exit`                                                    |

