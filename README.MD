
# Java Programming Assignments

This repository contains solutions to several Java programming assignments that demonstrate key **object-oriented programming (OOP)** concepts such as:

- **Polymorphism** (using abstract classes and interfaces)
- **Exception handling**
- **Logging for robust error handling**

---

## How to Compile and Run

Each question is organized in its own package. To compile and run any of the assignments, navigate to the root directory where the following folders are located:

```
Animals/
paymentsystem/
vehicles/
multiPaymentProcessingSystem/
travelAgency/
fundTransfer/
```

### General Compilation

You can compile all `.java` files within a specific package using `javac` from the root directory.

#### Example: Compiling the `Animals` package
```bash
javac Animals/*.java
```

#### For packages with sub-packages:
```bash
javac paymentsystem/*.java paymentsystem/methods/*.java
```

#### Or compile all at once:
```bash
javac */*.java */*/*.java
```

### General Execution

After compiling, you can run the `Main` or `Demo` class for each assignment.

#### Example: Running the Animals assignment
```bash
java Animals.Main
```

---

## Question 1: Zoo Animal Behavior System

### Purpose
Demonstrate polymorphism using an abstract base class. Models a zoo system that tracks different animals, their sounds, and flying abilities.

### Files
- `Animals/Animal.java`: Abstract class defining common behaviors (`makeSound`, `canFly`)
- `Animals/Dog.java`: Implements behavior for a dog
- `Animals/Cat.java`: Implements behavior for a cat
- `Animals/Bird.java`: Implements behavior for a bird
- `Animals/Zoo.java`: Manages a collection of `Animal` objects
- `Animals/Main.java`: Main class to create and display animal behaviors

### How to Run
```bash
javac Animals/*.java
java Animals.Main
```

### Expected Output
```
Animal sounds in the zoo:
Woof...
Meow
TweetTweet
Animals that can fly:
Bird
```

---

## Question 2: E-commerce Payment Processing System (Abstract Class)

### Purpose
Design a flexible payment processing system using polymorphism with an abstract class.

### Files
- `paymentsystem/PaymentMethod.java`: Abstract class defining `processPayment`
- `paymentsystem/methods/BankTransfer.java`: Bank transfer implementation
- `paymentsystem/methods/CreditCard.java`: Credit card implementation
- `paymentsystem/methods/PayPal.java`: PayPal implementation
- `paymentsystem/PaymentProcessor.java`: Processes payments
- `paymentsystem/PaymentSystemDemo.java`: Main demo class

### How to Run
```bash
javac paymentsystem/*.java paymentsystem/methods/*.java
java paymentsystem.PaymentSystemDemo
```

### Expected Output
```
Scenario 1: Processing Credit Card Payment
--- Initiating Payment Processing ---
Processing $150.00 payment through Credit Card.
--- Payment Processing Complete ---

Scenario 2: Processing PayPal Payment
... [and so on]
```

---

## Question 3: Logistics Vehicle Management System

### Purpose
Demonstrate polymorphism and inheritance for managing different vehicle types in logistics.

### Files
- `vehicles/Vehicle.java`: Abstract class with abstract method `calculateRange`
- `vehicles/vehicleTypes/Car.java`: Car subclass
- `vehicles/vehicleTypes/Truck.java`: Truck subclass
- `vehicles/vehicleTypes/Motorcycle.java`: Motorcycle subclass
- `vehicles/Logistics.java`: Manages list of vehicles
- `vehicles/Main.java`: Main class

### How to Run
```bash
javac vehicles/*.java vehicles/vehicleTypes/*.java
java vehicles.Main
```

### Expected Output
```
Added a Car with 500.0 fuel capacity.
Added a Truck with 1000.0 fuel capacity.
...
--- Vehicle Ranges ---
1, Car (Fuel Capacity: 500.0 units) Max Range: 50.00 km
2, Truck (Fuel Capacity: 1000.0 units) Max Range: 200.00 km
...
```

---

## Question 4: Multi-Payment Processing System (Interface)

### Purpose
Demonstrate polymorphism using interfaces in a payment processing system.

### Files
- `multiPaymentProcessingSystem/Payment.java`: Interface defining `processPayment`
- `multiPaymentProcessingSystem/CreditCardPayment.java`: Implements for credit cards
- `multiPaymentProcessingSystem/DebitCardPayment.java`: Implements for debit cards
- `multiPaymentProcessingSystem/DigitalWalletPayment.java`: Implements for digital wallets
- `multiPaymentProcessingSystem/PaymentProcessor.java`: Handles payment processing
- `multiPaymentProcessingSystem/Main.java`: Main class

### How to Run
```bash
javac multiPaymentProcessingSystem/*.java
java multiPaymentProcessingSystem.Main
```

### Expected Output
```
--- Processing Payments ---
Processing Credit Card Payment of $100.5
Card Number: ************3456
CVV:
Credit Card payment processed successfully.
...
Digital Wallet payment processed successfully.
```

---

## Question 5: Travel Agency Flight Booking System (Exception Handling)

### Purpose
Implement custom exceptions and use built-in exceptions for error handling in a flight booking system.

### Files
- `travelAgency/BookingException.java`: Custom exception class
- `travelAgency/FlightBookingSystem.java`: Contains booking logic
- `travelAgency/Main.java`: Main class with try-catch blocks

### How to Run
```bash
javac travelAgency/*.java
java travelAgency.Main
```

### Expected Output
#### Successful Booking
```
Enter number of available seats on the flight:
5
Enter number of seats you want to book:
4
Booking successful! 4 seats booked.
Remaining seats on flight: 1
Thank you for using our booking system!
```

#### Error Scenarios
- **Insufficient Seats**: Custom exception message
- **No Available Seats**: Built-in exception message

---

## Question 6: Fund Transfer System (Advanced Exception Handling & Logging)

### Purpose
Showcase input validation, business rule enforcement, and logging of exceptions in a fund transfer system.

### Files
- `fundTransfer/Account.java`: Manages account balance and fund transfers
- `fundTransfer/AuditLogger.java`: Logs exceptions to a file
- `fundTransfer/FundTransferSimulator.java`: Main simulation class

### How to Run
```bash
javac fundTransfer/*.java
java fundTransfer.FundTransferSimulator
```

### Expected Output
#### Successful Transfer
```
DEBUG: Program started.
--- Fund Transfer Simulation ---
Enter sender's initial account balance: $100
Enter the amount to transfer: $80
DEBUG: Funds successfully deducted. New balance: 20.00
Transfer successful!
Updated sender's balance: $20.00
Simulation complete. Check 'audit.log' for details on any errors.
```

#### With Errors
```
Error: Insufficient balance for transfer. Current balance: $100.00, Transfer requested: $200.00
Simulation complete. Check 'audit.log' for details on any errors.
```

### Audit Log Example
```
-----------------------------------------
Timestamp: 2025-06-24 08:59:10
Context: Fund Transfer Validation (Insufficient Balance)
Exception Type: java.lang.ArithmeticException
Message: Insufficient balance for transfer. Current balance: $100.00, Transfer requested: $200.00
Stack Trace:
java.lang.ArithmeticException: ...
	at fundTransfer.Account.transferFunds(Account.java:55)
	at fundTransfer.FundTransferSimulator.main(FundTransferSimulator.java:56)
------------------------------------------
```