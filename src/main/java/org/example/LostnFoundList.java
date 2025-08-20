package org.example;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Comparator;
import java.util.Scanner;


public class LostnFoundList {
    private LostnFoundNode head;


    public void addItem(BaseItem item) {
        LostnFoundNode newNode = new LostnFoundNode(item);
        if (head == null) {
            head = newNode;
        } else {
            LostnFoundNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }


    public void viewUnclaimedItems() {
        if (head == null) {
            System.out.println("No items found in Lost & Found System!");
            return;
        }


        LostnFoundNode current = head;
        boolean foundUnclaimed = false;
        while (current != null) {
            BaseItem item = current.getData();
            if (item instanceof FoundItem) {
                if (!item.isClaimed()) {
                    System.out.println(item.getDetails());
                    System.out.println("-------------------------");
                    foundUnclaimed = true;
                }
            } else if (item instanceof LostItem) {
                if (!((LostItem) item).isResolved()) {
                    System.out.println(item.getDetails());
                    System.out.println("-------------------------");
                    foundUnclaimed = true;
                }
            }
            current = current.getNext();
        }


        if (!foundUnclaimed) {
            System.out.println("No unclaimed items found.");
        }
    }


    public void searchItems(String keyword) {
        LostnFoundNode current = head;
        boolean found = false;
        while (current != null) {
            if (current.getData().matchesKeyword(keyword)) {
                System.out.println(current.getData().getDetails());
                System.out.println("-------------------------");
                found = true;
            }
            current = current.getNext();
        }
        if (!found) {
            System.out.println("No items found for keyword:" + keyword);
        }
    }


    // Inside LostnFoundList.java


    public void claimItemWithVerification(String keyword) {
        Scanner scanner = new Scanner(System.in);
        LostnFoundNode current = head;


        // Store matches in array for verification
        DynamicArray<BaseItem> matches = new DynamicArray<>(5);
        while (current != null) {
            if (current.getData() instanceof FoundItem && !current.getData().isClaimed() &&
                    current.getData().matchesKeyword(keyword)) {
                matches.add(current.getData());
            }
            current = current.getNext();
        }


        if (matches.size() == 0) {
            System.out.println("No unclaimed items match your keyword.");
            return;
        }


        // Always show the details, regardless of how many matches are found
        System.out.println("Matches found. Please verify the item you wish to claim:");
        for (int i = 0; i < matches.size(); i++) {
            System.out.println("-------------------------");
            System.out.println((i + 1) + ". " + matches.get(i).getDetails());
        }


        System.out.print("Enter the number of the item to claim (or 0 to cancel):");
        int choice = scanner.nextInt();
        scanner.nextLine(); // clear newline


        if (choice > 0 && choice <= matches.size()) {
            matches.get(choice - 1).markAsClaimed();
            System.out.println("Item claimed successfully!");
            // Add this new section to prompt the user for the lost item ID


            System.out.print("If this was an item you reported lost, enter your lost item ID (e.g., L001) or press Enter to skip: ");
            String lostItemID = scanner.nextLine();


            if (!lostItemID.isBlank()) {
                resolveLostItem(lostItemID);
            }
        } else if (choice == 0) {
            System.out.println("Claiming cancelled.");
        } else {
            System.out.println("Invalid selection.");
        }
    }


    public void claimItemByID(String itemID) {
        LostnFoundNode current = head;
        while (current != null) {
            BaseItem foundItem = current.getData();
            if (foundItem.getItemID().equalsIgnoreCase(itemID)) {
                if (foundItem instanceof FoundItem) {
                    if (!foundItem.isClaimed()) {
                        foundItem.markAsClaimed();
                        System.out.println("Item claimed successfully!");


                        // Add this new section to prompt the user for the lost item ID
                        Scanner scanner = new Scanner(System.in);
                        System.out.print("If this was an item you reported lost, enter your lost item ID (e.g., L001) or press Enter to skip: ");
                        String lostItemID = scanner.nextLine();


                        if (!lostItemID.isBlank()) {
                            resolveLostItem(lostItemID);
                        }
                    } else {
                        System.out.println("Item with ID " + itemID + " has already been claimed.");
                    }
                } else {
                    System.out.println("Item with ID " + itemID + " is a lost item report and cannot be claimed.");
                }
                return;
            }
            current = current.getNext();
        }
        System.out.println("No item found with ID: " + itemID);
    }


    private void resolveLostItem(String lostItemID) {
        LostnFoundNode current = head;
        while (current != null) {
            BaseItem item = current.getData();
            if (item.getItemID().equalsIgnoreCase(lostItemID)) {
                if (item instanceof LostItem) {
                    ((LostItem) item).markAsResolved();
                    System.out.println("Lost item report with ID " + lostItemID + " has been marked as resolved.");
                } else {
                    System.out.println("ID " + lostItemID + " does not correspond to a lost item report.");
                }
                return;
            }
            current = current.getNext();
        }
        System.out.println("No lost item report found with ID: " + lostItemID);
    }


    public void sortByNameAsc() {
        if (head == null) {
            System.out.println("No items found in Lost & Found System!");
            return;
        }
        DynamicArray<BaseItem> arr = toDynamicArray();
        arr.sortBy(Comparator.comparing(item -> item.description.toLowerCase()));
        printArray(arr);
        System.out.println("-------------------------");
    }


    public void sortByDateAsc() {
        if (head == null) {
            System.out.println("No items found in Lost & Found System!");
            return;
        }
        DynamicArray<BaseItem> arr = toDynamicArray();
        arr.sortBy(Comparator.comparing(BaseItem::getDate));
        printArray(arr);
        System.out.println("-------------------------");
    }




    public LocalDate validateDate(String dateStr) {
        try {
            // Use ResolverStyle.STRICT to enforce strict calendar rules
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("uuuu/MM/dd")
                    .withResolverStyle(ResolverStyle.STRICT);
            return LocalDate.parse(dateStr, formatter);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid date. Please use yyyy/MM/dd and ensure the date is not empty and valid (e.g., February has 28 or 29 days).");
            return null;
        }
    }

    private DynamicArray<BaseItem> toDynamicArray() {
        DynamicArray<BaseItem> arr = new DynamicArray<>(5);
        LostnFoundNode current = head;
        while (current != null) {
            arr.add(current.getData());
            current = current.getNext();
        }
        return arr;
    }


    private void printArray(DynamicArray<BaseItem> arr) {
        for (int i = 0; i < arr.size(); i++) {
            System.out.println(arr.get(i).getDetails());
        }
    }
}
