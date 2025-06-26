Java Programming Assignments

This repository contains solutions to several Java programming assignments that demonstrate key object-oriented programming (OOP) concepts such as polymorphism (using abstract classes and interfaces) and robust error handling through exceptions, including logging.How to Compile and RunEach question is set up in its own package. To compile and run any of the assignments, navigate to the root directory where the Animals, paymentsystem, vehicles, multiPaymentProcessingSystem, travelAgency, and fundTransfer folders are located.General CompilationYou can compile all .java files within a specific package using javac from the root directory. For example, to compile Animals package:javac Animals/*.java
For packages with sub-packages (like paymentsystem or vehicles), you'll need to include them:javac paymentsystem/*.java paymentsystem/methods/*.java
Or, you can compile all files at once (if all packages are in the same root directory):javac */*.java */*/*.java
General ExecutionAfter compiling, you can run the Main or Demo class for each assignment. For example, to run the Animals assignment:java Animals.Main
Question 1: Zoo Animal Behavior SystemThis assignment demonstrates polymorphism using an abstract base class. It models a zoo system that tracks different animals, their sounds, and whether they can fly.PurposeTo illustrate how polymorphism allows different animal types to be treated as a general Animal type, while still performing their specific behaviors (makeSound, canFly).FilesAnimals/Animal.java: An abstract class defining common animal behaviors (makeSound, canFly).Animals/Dog.java: Implements Animal for a dog.Animals/Cat.java: Implements Animal for a cat.Animals/Bird.java: Implements Animal for a bird.Animals/Zoo.java: Manages a collection of Animal objects and displays their sounds and flying abilities.Animals/Main.java: The main class to create animals, add them to the zoo, and run the display methods.How to Runjavac Animals/*.java
java Animals.Main
Expected OutputAnimal sounds in the zoo:
Woof...
Meow
TweetTweet

Animals that can fly:
Bird
Question 2: E-commerce Payment Processing System (Abstract Class)This assignment focuses on designing a flexible payment processing system using polymorphism with an abstract class.PurposeTo show how to create a system that can handle multiple payment methods and easily add new ones without changing the core payment processing logic.Filespaymentsystem/PaymentMethod.java: An abstract class defining the processPayment method.paymentsystem/methods/BankTransfer.java: Implements PaymentMethod for bank transfers.paymentsystem/methods/CreditCard.java: Implements PaymentMethod for credit card payments.paymentsystem/methods/PayPal.java: Implements PaymentMethod for PayPal payments.paymentsystem/PaymentProcessor.java: A class that processes payments using any PaymentMethod.paymentsystem/PaymentSystemDemo.java: The main class to demonstrate processing different payment types, including a dynamically added "CryptoPayment" method.How to Runjavac paymentsystem/*.java paymentsystem/methods/*.java
java paymentsystem.PaymentSystemDemo
Expected OutputScenario 1: Processing Credit Card Payment

--- Initiating Payment Processing ---
Processing $150.00 payment through Credit Card.
--- Payment Processing Complete ---

Scenario 2: Processing PayPal Payment

--- Initiating Payment Processing ---
Processing $75.00 payment through PayPal.
--- Payment Processing Complete ---

Scenario 3: Processing Bank Transfer Payment

--- Initiating Payment Processing ---
Process payment $500.20 payment through Bank Transfer.
--- Payment Processing Complete ---

Scenario 4: Processing Crypto Payment (New Method)

--- Initiating Payment Processing ---
Processing $1000.00 payment through Crypto Currency.
--- Payment Processing Complete ---
Question 3: Logistics Vehicle Management SystemThis assignment demonstrates polymorphism and inheritance in the context of a logistics company managing different vehicle types.PurposeTo illustrate how a common Vehicle class can define a calculateRange method, which is then implemented differently by various subclasses (Car, Truck, Motorcycle) based on their specific fuel efficiency.Filesvehicles/Vehicle.java: An abstract class with a fuelCapacity and an abstract calculateRange method.vehicles/vehicleTypes/Car.java: Extends Vehicle for cars, with its own fuel consumption rate.vehicles/vehicleTypes/Truck.java: Extends Vehicle for trucks, with its own fuel consumption rate.vehicles/vehicleTypes/Motorcycle.java: Extends Vehicle for motorcycles, with its own fuel consumption rate.vehicles/Logistics.java: Manages a list of Vehicle objects and displays their calculated ranges.vehicles/Main.java: The main class to create and manage various vehicles within the logistics system.How to Runjavac vehicles/*.java vehicles/vehicleTypes/*.java
java vehicles.Main
Expected OutputAdded a Car with 500.0 fuel capacity.
Added a Truck with 1000.0 fuel capacity.
Added a Motorcycle with 150.0 fuel capacity.
Added a Car with 750.0 fuel capacity.
Added a Truck with 1200.0 fuel capacity.

--- Vehicle Ranges ---
1, Car (Fuel Capacity: 500.0 units) Max Range: 50.00 km
2, Truck (Fuel Capacity: 1000.0 units) Max Range: 200.00 km
3, Motorcycle (Fuel Capacity: 150.0 units) Max Range: 10.00 km
4, Car (Fuel Capacity: 750.0 units) Max Range: 75.00 km
5, Truck (Fuel Capacity: 1200.0 units) Max Range: 240.00 km
---------------------------
Question 4: Multi-Payment Processing System (Interface)This assignment demonstrates polymorphism using interfaces for a payment processing system.PurposeTo show how interfaces can be used to define a contract (Payment) that different payment methods (CreditCardPayment, DebitCardPayment, DigitalWalletPayment) must adhere to, providing a flexible and secure way to process payments.FilesmultiPaymentProcessingSystem/Payment.java: An interface defining the processPayment method.multiPaymentProcessingSystem/CreditCardPayment.java: Implements Payment for credit card payments, including card number masking.multiPaymentProcessingSystem/DebitCardPayment.java: Implements Payment for debit card payments, including card number masking.multiPaymentProcessingSystem/DigitalWalletPayment.java: Implements Payment for digital wallet payments (code not provided, but inferred from Main and output).multiPaymentProcessingSystem/PaymentProcessor.java: A class that can process any Payment type.multiPaymentProcessingSystem/Main.java: The main class to demonstrate processing payments using different methods.How to Runjavac multiPaymentProcessingSystem/*.java
java multiPaymentProcessingSystem.Main
Expected Output--- Processing Payments ---
Processing Credit Card Payment of $100.5
Card Number: ************3456
CVV:
Credit Card payment processed successfully.

Processing Debit Card payment of 50.0
Card Number: ************8533
PIN: ****
Debit Card payment processed successfully

Processing Digital Wallet payment of $25.75
Wallet ID: user@gmail.com
Password: ****************
Digital Wallet payment processed successfully.
Question 5: Travel Agency Flight Booking System (Exception Handling)This assignment focuses on robust error handling using different types of exceptions in a flight booking system.PurposeTo demonstrate how to implement custom exceptions (BookingException) and effectively use built-in exceptions (IllegalArgumentException, RuntimeException) to manage various error scenarios in a real-world application, providing clear feedback to the user.FilestravelAgency/BookingException.java: A custom exception class for booking-specific errors.travelAgency/FlightBookingSystem.java: Contains the bookFlight method, which throws various exceptions based on booking conditions.travelAgency/Main.java: The main class to interact with the booking system, using try-catch blocks to handle different exceptions and allow retries.How to Runjavac travelAgency/*.java
java travelAgency.Main
Expected OutputThe output will vary based on user input:Successful Booking:Enter number of available seats on the flight:
5
Enter number of seats you want to book:
4
Booking successful! 4 seats booked.
Remaining seats on flight: 1

--- Booking Attempt Ended ---

Thank you for using our booking system!
Booking with Insufficient Seats (Custom Exception):Enter number of available seats on the flight:
3
Enter number of seats you want to book:
5
Error: Not enough seats available. Only 3 seats remaining.
Please try again later or choose another flight.

--- Booking Attempt Ended ---

Do you want to retry the booking? (yes/no)
yes
Booking with No Available Seats (IllegalArgumentException):Enter number of available seats on the flight:
0
Enter number of seats you want to book:
1
Error: Flight is fully booked. No seats available.
Please try again with a valid number of requested seats.

--- Booking Attempt Ended ---

Do you want to retry the booking? (yes/no)
no
Thank you for using our booking system!
Question 6: Fund Transfer System (Advanced Exception Handling & Logging)This assignment provides a comprehensive demonstration of exception handling and error logging in a fund transfer application.PurposeTo showcase how to validate user inputs and business rules using specific exception types (IllegalArgumentException, ArithmeticException, SecurityException), and how to log these exceptions to a file for auditing and debugging.FilesfundTransfer/Account.java: Manages an account balance and contains the transferFunds method, which performs validation and throws exceptions.fundTransfer/AuditLogger.java: A utility class to log exception details (timestamp, context, type, message, stack trace) to an audit.log file.fundTransfer/FundTransferSimulator.java: The main class that simulates the fund transfer, takes user input, uses try-catch blocks to handle various exceptions, and utilizes AuditLogger to record errors.How to Runjavac fundTransfer/*.java
java fundTransfer.FundTransferSimulator
Expected OutputThe output will vary based on user input. A successful transfer will look like this:DEBUG: Program started.
--- Fund Transfer Simulation ---
Enter sender's initial account balance: $
100
Enter the amount to transfer: $80
DEBUG: Funds successfully deducted. New balance: 20.00

Transfer successful!
Updated sender's balance: $20.00

Simulation complete. Check 'audit.log' for details on any errors.
An example of output when an error occurs (e.g., insufficient balance):DEBUG: Program started.
--- Fund Transfer Simulation ---
Enter sender's initial account balance: $
100
Enter the amount to transfer: $200

Error: Insufficient balance for transfer. Current balance: $100.00, Transfer requested: $200.00

Simulation complete. Check 'audit.log' for details on any errors.
After running with errors, check the audit.log file in the same directory for detailed exception logs:Example audit.log Entry:-----------------------------------------
Timestamp: 2025-06-24 08:59:10
Context: Fund Transfer Validation (Insufficient Balance)
Exception Type: java.lang.ArithmeticException
Message: Insufficient balance for transfer. Current balance: $100.00, Transfer requested: $200.00
Stack Trace:
java.lang.ArithmeticException: Insufficient balance for transfer. Current balance: $100.00, Transfer requested: $200.00
	at fundTransfer.Account.transferFunds(Account.java:55)
	at fundTransfer.FundTransferSimulator.main(FundTransferSimulator.java:56)
------------------------------------------
