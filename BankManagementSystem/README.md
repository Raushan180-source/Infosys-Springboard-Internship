# Bank Management System (Java)

A menu-driven console application that models basic bank operations for
up to 50 customers.

## Files
- `MultiCustomerDemo.java` — contains the `Bank` class and the
  `MultiCustomerDemo` main class.

## How to Compile
```
javac MultiCustomerDemo.java
```

## How to Run
```
java MultiCustomerDemo
```

## Features
1. **Open Account** — Enter Customer Name and Account Type
   (Saving/Current). A unique account number is auto-generated in the
   format `hdfcNNNN` (e.g. `hdfc1839`), and the opening balance is set
   automatically (Saving = 10000, Current = 20000).
2. **Show Detail (Account Enquiry)** — Search by Account Number
   (case-insensitive) and view Account Number, Customer Name, Account
   Type, and Balance.
3. **Deposit Money** — Search by Account Number and deposit an amount;
   the updated balance is displayed.
4. **Withdraw Money** — Search by Account Number and withdraw an
   amount. If funds are insufficient, "Funds not available" is
   printed; otherwise the balance is deducted and displayed.
5. **Exit** — Ends the program.

After each operation (options 2, 3, 4) the app asks:
```
Do you still want to continue... press 1 to exit:
```
Entering `1` ends the program; any other value continues the loop.

## Design Notes / Optimizations
- Account search is centralized in a single `findAccount()` helper
  used by options 2, 3, and 4 — avoids duplicated linear-search code.
- Input reading is centralized through a single `Scanner` instance and
  a safe `readInt()` helper that re-prompts on invalid numeric input,
  preventing crashes from bad input (e.g. `InputMismatchException`).
- Deposit/withdraw amounts are validated (must be positive) before
  being applied.
- Encapsulation: `Bank` fields (`balance`, `acno`, `cname`, `actype`)
  are private; access is only through the class's own methods, as
  required by the spec (`getAcno()`, `setBalance()`, `openAccount()`,
  `enquiryAccount()`, `depositMoney()`, `widrawMoney()`).
- Account number generation uses `java.util.Random` to produce 4
  random digits appended to the `hdfc` prefix.
