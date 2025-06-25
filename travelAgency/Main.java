package travelAgency;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        FlightBookingSystem bookingSystem = new FlightBookingSystem();
        boolean bookingCompleted = false;

        // Loop to allow retrying the booking process
        do {
            try {
                System.out.println("Enter number of available seats on the flight: ");
                int availableSeats = scanner.nextInt();

                System.out.println("Enter number of seats you want to book: ");
                int requestedSeats = scanner.nextInt();

                // Attempt to book the flight
                bookingSystem.bookFlight(availableSeats, requestedSeats);
                bookingCompleted = true;

            } catch (IllegalArgumentException e) {
                // Catching specific IllegalArgumentException for invalid requested seats
                System.err.println("Error: " + e.getMessage());
                System.out.println("Please try again with a valid number of requested seats.");

            } catch (BookingException e) {
                // Catching custom BookingException for fully booked flights.
                System.err.println("Error: " + e.getMessage());
                System.out.println("Please try again later or choose another flight.");
            } catch (RuntimeException e) {
                // Catching RuntimeException for insufficient seats
                System.err.println("Error: " + e.getMessage());
                System.out.println("Please try again later or choose another flight");
            } catch (Exception e) {
                // Generic catch-all for any other unexpected exceptions
                System.err.println("An unexpected error occurred: " + e.getMessage());
            } finally {
                System.out.println("\n--- Booking Attempt Ended ---\n");
            }

            // If booking was not completed, prompt to retry
            if (!bookingCompleted) {
                System.out.println("Do you want to retry the booking? (yes/no)");
                String retryChoice = scanner.next();
                if (!retryChoice.equalsIgnoreCase("yes")) {
                    break;
                }
            }
        } while (!bookingCompleted); // Continue looping until booking is successfully completed.
        scanner.close();
        System.out.println("Thank you for using our booking system!");
    }
}
