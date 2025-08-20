package org.example;


import java.time.LocalDate;
import java.util.Scanner;
import java.util.InputMismatchException;


public class LostnFoundMain {
    private static LostnFoundList list = new LostnFoundList();
    private static int lostCounter = 1;
    private static int foundCounter = 1;


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = -1; // Initialize with a value that won't exit the loop


        do {
            System.out.println("-------------------------");
            System.out.println("===== LOST & FOUND MENU =====");
            System.out.println("1. Add Lost Item");
            System.out.println("2. Add Found Item");
            System.out.println("3. View Unclaimed Items");
            System.out.println("4. Search Items");
            System.out.println("5. Claim Item (by keyword, with verification)");
            System.out.println("6. Claim Item by ID");
            System.out.println("7. Sort Items by Name (A->Z)");
            System.out.println("8. Sort Items by Date");
            System.out.println("9. Exit");
            System.out.print("Enter Choice:");



            try {
                choice = scanner.nextInt();
                scanner.nextLine(); // clear newline


                switch (choice) {
                    case 1:
                        addLostItem(scanner);
                        break;
                    case 2:
                        addFoundItem(scanner);
                        break;
                    case 3:
                        list.viewUnclaimedItems();
                        break;
                    case 4:
                        System.out.print("Enter keyword:");
                        String keyword = scanner.nextLine();
                        list.searchItems(keyword);
                        break;
                    case 5:
                        System.out.print("Enter keyword to claim:");
                        String claimKeyword = scanner.nextLine();
                        list.claimItemWithVerification(claimKeyword);
                        break;
                    case 6:
                        System.out.print("Enter Item ID to claim:");
                        String id = scanner.nextLine();
                        list.claimItemByID(id);
                        break;
                    case 7:
                        list.sortByNameAsc();
                        break;
                    case 8:
                        list.sortByDateAsc();
                        break;
                    case 9:
                        System.out.println("Exiting system...");
                        break;
                    default:
                        if (choice != 9) {
                            System.out.println("Invalid choice. Please enter a number between 1 and 9.");
                        }
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number from the menu.");
                scanner.nextLine(); // Clear the invalid input from the scanner
                choice = -1; // Reset choice to keep the loop running
            }
        } while (choice != 9);


        scanner.close();
    }


    private static void addLostItem(Scanner scanner) {
        String id = "L" + String.format("%03d", lostCounter++);

        String desc;
        do {
            System.out.print("Description (required):");
            desc = scanner.nextLine().trim(); // Use .trim() to handle whitespace
        } while (desc.isBlank());

        System.out.print("Brand (optional):");
        String brand = emptyToNull(scanner.nextLine());
        System.out.print("Color (optional):");
        String color = emptyToNull(scanner.nextLine());
        System.out.print("Condition (optional):");
        String condition = emptyToNull(scanner.nextLine());

        // The askDate method already handles validation
        LocalDate date = askDate(scanner);

        System.out.print("Lost Location (optional):");
        String location = emptyToNull(scanner.nextLine());

        String owner;
        do {
            System.out.print("Owner Name (required):");
            owner = scanner.nextLine().trim();
        } while (owner.isBlank());

        LostItem item = new LostItem(id, desc, brand, color, condition, date, owner, location);
        list.addItem(item);
        System.out.println("Lost item added with ID:" + id);
        System.out.println("-------------------------");
    }

    private static void addFoundItem(Scanner scanner) {
        String id = "F" + String.format("%03d", foundCounter++);

        String desc;
        do {
            System.out.print("Description (required):");
            desc = scanner.nextLine().trim(); // Use .trim() to handle whitespace
        } while (desc.isBlank());

        System.out.print("Brand (optional):");
        String brand = emptyToNull(scanner.nextLine());
        System.out.print("Color (optional):");
        String color = emptyToNull(scanner.nextLine());
        System.out.print("Condition (optional):");
        String condition = emptyToNull(scanner.nextLine());

        // The askDate method already handles validation
        LocalDate date = askDate(scanner);

        System.out.print("Found Location (optional):");
        String location = emptyToNull(scanner.nextLine());

        String finder;
        do {
            System.out.print("Finder Name (required):");
            finder = scanner.nextLine().trim();
        } while (finder.isBlank());

        FoundItem item = new FoundItem(id, desc, brand, color, condition, date, finder, location);
        list.addItem(item);
        System.out.println("Found item added with ID:" + id);
        System.out.println("-------------------------");
    }



    private static String emptyToNull(String input) {
        return input.isEmpty() ? null : input;
    }


    private static LocalDate askDate(Scanner scanner) {
        LocalDate date = null;
        while (date == null) {
            System.out.print("Enter date (yyyy/MM/dd):");
            String dateStr = scanner.nextLine();
            date = list.validateDate(dateStr);
        }
        return date;
    }
}
