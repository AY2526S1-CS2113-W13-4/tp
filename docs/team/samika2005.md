# Samika - Project Portfolio Page

## Overview
*BusyBreak* is a travel assistant application that helps users manage their travel itineraries, budgets, and activities.  

It is a Java-based CLI program designed to simplify trip planning and expense tracking by maintaining synchronization 
between the Activity and Budget modules.

## Summary of Contributions

### **New feature: Budget Management**
* **What it does**: Implemented the `BudgetPlan.java` class to handle all core budgeting logic, including adding, 
listing, deleting, and categorizing expenses.
* **Highlights**: Acts as the foundation of the budgeting subsystem, providing automatic synchronization with the 
Activity module to ensure accurate expense tracking and prevent data inconsistency.

---

### **New feature: `Budget` Command**
* **What it does**: Implemented the `Budget.java` command handler, enabling user interaction through commands such 
as `budget set`, `budget add`, `budget delete`, `budget list`, `budget setcat`, `budget sync` and `breakdown`.
* **Highlights**: Offers a structured CLI interface for managing budgets with detailed user feedback and validation for
every command.

---

### **New feature: Activity–Budget Synchronization**
* **What it does**: Linked the Budget and Activity modules through automatic synchronization, ensuring Activity-related
expenses are reflected accurately in the Budget.
* **Highlights**: Maintains real-time consistency between both modules, preventing duplicate or outdated data.

---

### **Enhancement: Category Protection Logic**
* **What it does**: Added logic to prevent modification or deletion of Activity-linked expenses through Budget commands.
* **Highlights**: Improves data reliability by ensuring Activity-related costs are managed only through Activity commands.

---

### **Enhancement: CLI Feedback Standardization**
* **What it does**: Standardized printed outputs and error handling across all Budget-related features.
* **Highlights**: Provides clearer, more consistent feedback for users and simplifies debugging for developers.


---

### **Challenges and Accuracy Considerations**
The main challenge was ensuring the **accuracy and consistency of financial data** across interconnected modules.  
Because both the *Activity* and *Budget* systems could modify overlapping expense records, even small logical oversights risked data duplication or loss.

To maintain accuracy, I:
* Implemented **strict validation checks** for negative and unrealistic input values.
* Added **category normalization** to eliminate inconsistent user inputs (e.g., “meals” vs. “lunch” → *Food*).
* Introduced **category protection logic** to prevent cross-module data corruption.
* Conducted repeated manual and integration testing to verify synchronization between Activity and Budget.

This focus on precision ensured that user data remained reliable, correctly aggregated, and reflected the intended financial state at all times.

---
### **Code contributed**
[tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=samika&breakdown=true&sort=groupTitle%20dsc&sortWithin=title&since=2025-09-19T00%3A00%3A00&timeframe=commit&mergegroup=&groupSelect=groupByRepos&checkedFileTypes=docs~functional-code~test-code~other&filteredFileName=)

---

### **Contributions to the UG**
* Added documentation for **Budget Management** and **Budget Commands** sections.
* Documented `budget set`, `budget add`, `budget delete`, `budget list`, `budget sync`, `budget setcat`, 
and `breakdown` commands.
* Updated usage examples and output explanations for all budget commands.

---

### **Contributions to the DG**
* Added documentation for **BudgetPlan Class Diagram** and **Budget Command Class Diagram**.
* Described the **Activity–Budget Synchronization Flow** and its data consistency logic.
* Wrote the **Design and Implementation** sections for the Budget subsystem.

---

### **Contributions to team-based tasks**
* Reviewed and merged PRs related to Activity–Budget integration.
* Helped resolve merge conflicts during subsystem synchronization.
* Assisted with debugging, manual testing, and CLI output refinement.  