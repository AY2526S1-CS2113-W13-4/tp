# Brien Lim - Project Portfolio Page

## Overview

BusyBreak is a travel assistant application that manages a user's travel itineraries, 
trips, and budgets.
It is written in Java, and is CLI-based. 

### Summary of Contributions

* **New Feature:** `edit` command
    * **What it does:** Allows the user to edit any number of an added activities' details based on its
  list index.
    * **Justification:** This feature improves the product significantly, because activity details
      can change at any time, and the app should provide a way to edit them without having to key in
      the whole activity again.
    * **Highlights:** This feature affects every existing activity and can scale to future fields/commands.
  It introduces a dedicated parser to validate and bundle changes safely. It is also tied in with the 
  budget tracking system.
* **New Feature:** `delete` command
    * **What it does:** Allows the user to delete an activity based on its list index.
    * **Justification:** This feature improves the product significantly, because users need to have a way
      to clean up outdated or wrong entries. It provides a quick and easy way to remove items. It also keeps 
  the budget updated, preventing over or under budgeting.
    * **Highlights:** This feature affects any existing activity and can scale to future fields/commands. 
  It is also tied in with the budget tracking system.

* **Code contributed**: [tP Code Dashboard](https://nus-cs2113-ay2526s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2025-09-19T00%3A00%3A00&filteredFileName=&tabOpen=true&tabType=authorship&tabAuthor=blimc1&tabRepo=AY2526S1-CS2113-W13-4%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false&authorshipIsIgnoredFilesChecked=false)

* **Project Management:** 
  * Managed releases `v1.0` and `v2.0` on GitHub

* **Documentation:**
  * User Guide:
    * Added documentation for the `edit` feature
  * Developer Guide:
    * Added implementations of the `edit` and `delete` features
    * Added User Stories
    * Added Non-Functional Requirements
    * Added Glossary
    * Added Instructions for Manual Testing

* **Contributions to team-based tasks:**
  * Reviewed and merged PRs
  * Assisted with debugging
  * Maintain issue tracker
  * Reminded teammates of submission deadlines
