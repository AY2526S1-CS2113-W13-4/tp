package seedu.busybreak.command;

import seedu.busybreak.BusyBreak;
import java.util.Arrays;
import seedu.busybreak.storage.History;

public class Budget {
    public static void handleBudget(String[] userInputArray) {
        if (userInputArray.length < 2) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Please specify a budget command: set / add / list / delete / setcat / sync");
            System.out.println(BusyBreak.LINE);
            return;
        }

        String sub = userInputArray[1].toLowerCase();
        try {
            switch (sub) {

            case "set":
                if (userInputArray.length < 3) {
                    System.out.println(BusyBreak.LINE);
                    System.out.println("Usage: budget set <amount>");
                    System.out.println(BusyBreak.LINE);
                    return;
                }
                History.checkpointWithSave(BusyBreak.getStorage());
                BusyBreak.budgetPlan.setBudget(Double.parseDouble(userInputArray[2]));
                BusyBreak.getStorage().saveBudgets();
                System.out.println(BusyBreak.LINE);
                System.out.printf("Budget set to $%.2f%n", BusyBreak.budgetPlan.getTotalBudget());
                System.out.println(BusyBreak.LINE);
                break;

            case "add":
                String joined = String.join(" ", Arrays.copyOfRange(userInputArray, 2, userInputArray.length));
                String name = joined.contains("n/") ? joined.split("n/", 2)[1].split("c/", 2)[0].trim() : "";
                String cost = joined.contains("c/") ? joined.split("c/", 2)[1].split("cat/", 2)[0].trim() : "";
                String category = joined.contains("cat/") ? joined.split("cat/", 2)[1].trim() : "Uncategorized";

                if (name.isEmpty() || cost.isEmpty()) {
                    System.out.println(BusyBreak.LINE);
                    System.out.println("Usage: budget add n/<name> c/<cost> cat/<category>");
                    System.out.println(BusyBreak.LINE);
                    return;
                }
                if (BusyBreak.budgetPlan.isActivityCategory(category)) {
                    System.out.println(BusyBreak.LINE);
                    System.out.println("Activity expenses must be created via Activity commands");
                    System.out.println("Try: add d/<yyyy-mm-dd> t/<hh:mm> desc/<...> c/<cost>");
                    System.out.println(BusyBreak.LINE);
                    return;
                }

                History.checkpointWithSave(BusyBreak.getStorage());
                BusyBreak.budgetPlan.addExpense(name, cost, category);
                int last = BusyBreak.budgetPlan.names.size() - 1;
                System.out.println(BusyBreak.LINE);
                System.out.printf(java.util.Locale.US,
                        "Added Expense: %s | Cost: $%.2f | Category: %s%n",
                        BusyBreak.budgetPlan.names.get(last),
                        BusyBreak.budgetPlan.amounts.get(last),
                        BusyBreak.budgetPlan.categories.get(last));
                System.out.println(BusyBreak.LINE);
                BusyBreak.getStorage().saveBudgets();
                break;

            case "list":
                BusyBreak.budgetPlan.listExpenses();
                break;

            case "sync": {
                History.checkpointWithSave(BusyBreak.getStorage());
                BusyBreak.budgetPlan.syncFromActivities(BusyBreak.list);
                BusyBreak.getStorage().saveBudgets();
                System.out.println(BusyBreak.LINE);
                System.out.println("Budget synced with Activities.");
                System.out.println(BusyBreak.LINE);
                break;
            }

            case "delete":
                if (userInputArray.length < 3) {
                    System.out.println(BusyBreak.LINE);
                    System.out.println("Usage: budget delete <index>");
                    System.out.println(BusyBreak.LINE);
                    return;
                }
                int idx = Integer.parseInt(userInputArray[2]);
                History.checkpointWithSave(BusyBreak.getStorage());
                BusyBreak.budgetPlan.deleteExpense(idx);
                BusyBreak.getStorage().saveBudgets();
                break;

            case "setcat": {
                if (userInputArray.length < 4) {
                    System.out.println(BusyBreak.LINE);
                    System.out.println("Usage: budget setcat <index> cat/<newCategory>");
                    System.out.println(BusyBreak.LINE);
                    return;
                }
                int editIndex = Integer.parseInt(userInputArray[2]);
                String catJoined = String.join(" ",
                        java.util.Arrays.copyOfRange(userInputArray, 3, userInputArray.length));
                String newCat = catJoined.startsWith("cat/") ? catJoined.substring(4).trim() : catJoined.trim();

                History.checkpointWithSave(BusyBreak.getStorage());
                BusyBreak.budgetPlan.setExpenseCategory(editIndex, newCat);
                BusyBreak.getStorage().saveBudgets();
                break;
            }

            default:
                System.out.println(BusyBreak.LINE);
                System.out.println("Invalid budget command. Try: set / add / list / delete / setcat / sync");
                System.out.println(BusyBreak.LINE);
                break;
            }
        } catch (Exception e) {
            System.out.println(BusyBreak.LINE);
            System.out.println("Error: " + e.getMessage());
            System.out.println(BusyBreak.LINE);
        }
    }
}

