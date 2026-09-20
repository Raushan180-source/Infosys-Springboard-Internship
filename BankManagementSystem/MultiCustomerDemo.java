import java.util.Random;
import java.util.Scanner;

/**
 * Bank class
 * Models a single customer's bank account and the operations
 * that can be performed on it.
 */
class Bank {

    private int balance;
    private String acno;   // Account Number
    private String cname;  // Customer Name
    private String actype; // Account Type (Saving / Current)

    // ---------- Getters used by the search routine in MultiCustomerDemo ----------
    public String getAcno() {
        return acno;
    }

    public String getCname() {
        return cname;
    }

    public int getBalance() {
        return balance;
    }

    /**
     * Generates a unique account number in the format:
     * hdfc followed by 4 random single digits, e.g. hdfc1839
     */
    private String generateAcno() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder("hdfc");
        for (int i = 0; i < 4; i++) {
            sb.append(rand.nextInt(10)); // single digit 0-9
        }
        return sb.toString();
    }

    /**
     * Sets the initial opening balance based on the account type.
     * Saving  -> 10000
     * Current -> 20000
     */
    public void setBalance(String actype) {
        if (actype.equalsIgnoreCase("Saving")) {
            balance = 10000;
        } else if (actype.equalsIgnoreCase("Current")) {
            balance = 20000;
        } else {
            // Fallback default in case of an unrecognised type
            balance = 0;
        }
    }

    /**
     * Prompts the user for Customer Name and Account Type,
     * generates a unique account number and initializes the balance.
     */
    public void openAccount(Scanner sc) {
        acno = generateAcno();

        System.out.print("Enter Customer Name: ");
        cname = sc.nextLine();

        System.out.print("Enter Account Type (Saving/Current): ");
        actype = sc.nextLine();

        setBalance(actype);

        System.out.println("\nAccount created successfully!");
        System.out.println("Your Account Number is: " + acno);
    }

    /**
     * Prints the full account details.
     */
    public void enquiryAccount() {
        System.out.println("\n----- Account Details -----");
        System.out.println("Account Number : " + acno);
        System.out.println("Customer Name  : " + cname);
        System.out.println("Account Type   : " + actype);
        System.out.println("Balance        : " + balance);
    }

    /**
     * Prompts for a deposit amount, adds it to the balance
     * and displays the updated balance.
     */
    public void depositMoney(Scanner sc) {
        System.out.print("Enter amount to deposit: ");
        int amount = Integer.parseInt(sc.nextLine().trim());

        if (amount <= 0) {
            System.out.println("Invalid deposit amount.");
            return;
        }

        balance += amount;
        System.out.println("Amount deposited successfully!");
        System.out.println("Updated Balance: " + balance);
    }

    /**
     * Prompts for a withdrawal amount.
     * If balance < amount -> "Funds not available"
     * Otherwise deducts the amount and displays the updated balance.
     */
    public void widrawMoney(Scanner sc) {
        System.out.print("Enter amount to withdraw: ");
        int amount = Integer.parseInt(sc.nextLine().trim());

        if (amount <= 0) {
            System.out.println("Invalid withdrawal amount.");
            return;
        }

        if (balance < amount) {
            System.out.println("Funds not available");
        } else {
            balance -= amount;
            System.out.println("Amount withdrawn successfully!");
            System.out.println("Updated Balance: " + balance);
        }
    }
}

/**
 * MultiCustomerDemo - Main Application class
 * Manages up to 50 customer accounts using an array of Bank objects.
 */
public class MultiCustomerDemo {

    private static final int MAX_CUSTOMERS = 50;
    private static Bank[] accounts = new Bank[MAX_CUSTOMERS];
    private static int accountCount = 0;
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;
        char cont = 'y';

        do {
            printMenu();
            choice = readInt("Enter your choice: ");

            switch (choice) {
                case 1:
                    openAccount();
                    break;
                case 2:
                    showDetail();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    System.out.println("Thank you for using the Bank Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

            if (choice != 5) {
                System.out.print("\nDo you still want to continue... press 1 to exit: ");
                String input = sc.nextLine().trim();
                try {
                    int val = Integer.parseInt(input);
                    if (val == 1) {
                        cont = 'n';
                    }
                } catch (NumberFormatException e) {
                    // any non "1" (including invalid input) means continue
                    cont = 'y';
                }
            }

        } while (choice != 5 && cont != 'n');

        System.out.println("Program terminated. Goodbye!");
        sc.close();
    }

    private static void printMenu() {
        System.out.println("\n===== Bank Management System =====");
        System.out.println("1. Open Account");
        System.out.println("2. Show Detail (Account Enquiry)");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Exit");
    }

    /**
     * Reads an integer safely from the console, re-prompting on bad input.
     */
    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = sc.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void openAccount() {
        if (accountCount >= MAX_CUSTOMERS) {
            System.out.println("Maximum number of customers (50) reached. Cannot open a new account.");
            return;
        }
        Bank b = new Bank();
        b.openAccount(sc);
        accounts[accountCount++] = b;
    }

    /**
     * Searches for an account by Account Number (case-insensitive).
     * Returns the matching Bank object, or null if not found.
     */
    private static Bank findAccount(String acno) {
        for (int i = 0; i < accountCount; i++) {
            if (accounts[i].getAcno().equalsIgnoreCase(acno)) {
                return accounts[i];
            }
        }
        return null;
    }

    private static String promptAccountNumber() {
        System.out.print("Enter Account Number: ");
        return sc.nextLine().trim();
    }

    private static void showDetail() {
        if (accountCount == 0) {
            System.out.println("No accounts found. Please open an account first.");
            return;
        }
        String acno = promptAccountNumber();
        Bank b = findAccount(acno);
        if (b != null) {
            b.enquiryAccount();
        } else {
            System.out.println("Account no. not found");
        }
    }

    private static void depositMoney() {
        if (accountCount == 0) {
            System.out.println("No accounts found. Please open an account first.");
            return;
        }
        String acno = promptAccountNumber();
        Bank b = findAccount(acno);
        if (b != null) {
            b.depositMoney(sc);
        } else {
            System.out.println("Account no. not found");
        }
    }

    private static void withdrawMoney() {
        if (accountCount == 0) {
            System.out.println("No accounts found. Please open an account first.");
            return;
        }
        String acno = promptAccountNumber();
        Bank b = findAccount(acno);
        if (b != null) {
            b.widrawMoney(sc);
        } else {
            System.out.println("Account no. not found");
        }
    }
}
