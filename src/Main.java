import java.util.ArrayList;
import java.util.Scanner;

// MenuItem class
class MenuItem {
    int id;
    String name;
    double price;

    MenuItem(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}

// BillItem class
class BillItem {
    MenuItem item;
    int quantity;

    BillItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }

    double getTotal() {
        return item.price * quantity;
    }
}

// Main class
public class Main {

    static Scanner sc = new Scanner(System.in);

    static ArrayList<MenuItem> menu = new ArrayList<>();
    static ArrayList<BillItem> bill = new ArrayList<>();

    static final double GST = 5.0;

    public static void main(String[] args) {

        // Default menu
        menu.add(new MenuItem(1, "Pizza", 250));
        menu.add(new MenuItem(2, "Burger", 120));
        menu.add(new MenuItem(3, "Pasta", 180));
        menu.add(new MenuItem(4, "French Fries", 100));
        menu.add(new MenuItem(5, "Cold Drink", 60));

        int choice;

        do {
            System.out.println("\n=================================");
            System.out.println("     RESTAURANT BILLING SYSTEM");
            System.out.println("=================================");
            System.out.println("1. Display Menu");
            System.out.println("2. Add Item to Bill");
            System.out.println("3. Remove Item from Bill");
            System.out.println("4. View Current Bill");
            System.out.println("5. Add New Menu Item");
            System.out.println("6. Remove Menu Item");
            System.out.println("7. Generate Final Bill");
            System.out.println("8. Exit");
            System.out.println("=================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    displayMenu();
                    break;

                case 2:
                    addItemToBill();
                    break;

                case 3:
                    removeItemFromBill();
                    break;

                case 4:
                    displayBill();
                    break;

                case 5:
                    addMenuItem();
                    break;

                case 6:
                    removeMenuItem();
                    break;

                case 7:
                    generateBill();
                    break;

                case 8:
                    System.out.println("Thank you! Visit again.");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 8);

        sc.close();
    }

    // Display restaurant menu
    static void displayMenu() {

        System.out.println("\n----------- MENU -----------");

        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        System.out.printf("%-5s %-20s %-10s%n",
                "ID", "ITEM", "PRICE");

        for (MenuItem item : menu) {
            System.out.printf("%-5d %-20s Rs.%.2f%n",
                    item.id, item.name, item.price);
        }
    }

    // Add item to bill
    static void addItemToBill() {

        displayMenu();

        if (menu.isEmpty()) {
            return;
        }

        System.out.print("\nEnter item ID: ");
        int id = sc.nextInt();

        MenuItem selectedItem = findMenuItem(id);

        if (selectedItem == null) {
            System.out.println("Item not found!");
            return;
        }

        System.out.print("Enter quantity: ");
        int quantity = sc.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than 0.");
            return;
        }

        // Check if item already exists in bill
        for (BillItem billItem : bill) {

            if (billItem.item.id == id) {
                billItem.quantity += quantity;

                System.out.println(
                        "Quantity updated successfully!"
                );

                return;
            }
        }

        bill.add(new BillItem(selectedItem, quantity));

        System.out.println("Item added to bill successfully!");
    }

    // Remove item from bill
    static void removeItemFromBill() {

        if (bill.isEmpty()) {
            System.out.println("Bill is empty.");
            return;
        }

        displayBill();

        System.out.print("Enter item ID to remove: ");
        int id = sc.nextInt();

        for (int i = 0; i < bill.size(); i++) {

            if (bill.get(i).item.id == id) {

                bill.remove(i);

                System.out.println(
                        "Item removed from bill successfully!"
                );

                return;
            }
        }

        System.out.println("Item not found in bill.");
    }

    // Display current bill
    static void displayBill() {

        if (bill.isEmpty()) {
            System.out.println("\nBill is empty.");
            return;
        }

        System.out.println("\n------------- CURRENT BILL -------------");

        System.out.printf("%-5s %-18s %-10s %-10s%n",
                "ID", "ITEM", "QTY", "TOTAL");

        double subtotal = 0;

        for (BillItem billItem : bill) {

            double total = billItem.getTotal();

            System.out.printf("%-5d %-18s %-10d Rs.%.2f%n",
                    billItem.item.id,
                    billItem.item.name,
                    billItem.quantity,
                    total);

            subtotal += total;
        }

        System.out.println("----------------------------------------");
        System.out.printf("Subtotal: Rs.%.2f%n", subtotal);
    }

    // Add new menu item
    static void addMenuItem() {

        System.out.println("\n------- ADD NEW MENU ITEM -------");

        System.out.print("Enter item ID: ");
        int id = sc.nextInt();

        if (findMenuItem(id) != null) {
            System.out.println("Item ID already exists!");
            return;
        }

        sc.nextLine();

        System.out.print("Enter item name: ");
        String name = sc.nextLine();

        System.out.print("Enter item price: ");
        double price = sc.nextDouble();

        if (price <= 0) {
            System.out.println("Price must be greater than 0.");
            return;
        }

        menu.add(new MenuItem(id, name, price));

        System.out.println(
                "New menu item added successfully!"
        );
    }

    // Remove menu item
    static void removeMenuItem() {

        if (menu.isEmpty()) {
            System.out.println("Menu is empty.");
            return;
        }

        displayMenu();

        System.out.print("\nEnter item ID to remove: ");
        int id = sc.nextInt();

        for (int i = 0; i < menu.size(); i++) {

            if (menu.get(i).id == id) {

                menu.remove(i);

                System.out.println(
                        "Menu item removed successfully!"
                );

                return;
            }
        }

        System.out.println("Item not found.");
    }

    // Generate final bill
    static void generateBill() {

        if (bill.isEmpty()) {
            System.out.println(
                    "\nCannot generate bill. Bill is empty."
            );
            return;
        }

        System.out.println("\n");
        System.out.println("==============================================");
        System.out.println("             RESTAURANT BILL");
        System.out.println("==============================================");

        System.out.printf("%-5s %-18s %-8s %-10s%n",
                "ID", "ITEM", "QTY", "AMOUNT");

        double subtotal = 0;

        for (BillItem billItem : bill) {

            double total = billItem.getTotal();

            System.out.printf("%-5d %-18s %-8d Rs.%.2f%n",
                    billItem.item.id,
                    billItem.item.name,
                    billItem.quantity,
                    total);

            subtotal += total;
        }

        double gstAmount = subtotal * GST / 100;
        double grandTotal = subtotal + gstAmount;

        System.out.println("----------------------------------------------");

        System.out.printf("Subtotal       : Rs.%.2f%n", subtotal);
        System.out.printf("GST (%.0f%%)       : Rs.%.2f%n",
                GST, gstAmount);
        System.out.printf("Grand Total    : Rs.%.2f%n",
                grandTotal);

        System.out.println("==============================================");
        System.out.println("          Thank You! Visit Again!");
        System.out.println("==============================================");

        // Clear bill after final payment
        bill.clear();

        System.out.println(
                "Bill completed successfully."
        );
    }

    // Find menu item by ID
    static MenuItem findMenuItem(int id) {

        for (MenuItem item : menu) {

            if (item.id == id) {
                return item;
            }
        }

        return null;
    }
}