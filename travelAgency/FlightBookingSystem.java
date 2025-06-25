package travelAgency;

class FlightBookingSystem {
    public int bookFlight(int availableSeats, int requestedSeats)
            throws IllegalArgumentException, RuntimeException, BookingException {

        // Check 1: If requested seats are less than or equal to 0
        if (requestedSeats <= 0) {
            throw new IllegalArgumentException("Requested seats must be a positive number.");
        }

        // Check 2: If available seats are zero
        if (availableSeats == 0) {
            throw new IllegalArgumentException("Flight is fully booked. No seats available.");
        }

        // Check 3: If requested seats exceed available seats.
        if (requestedSeats > availableSeats) {
            throw new BookingException("Not enough seats available. Only " + availableSeats + " seats remaining.");
        }

        // If all checks pass, the booking is successful
        int remainingSeats = availableSeats - requestedSeats;
        System.out.println("Booking successful! " + requestedSeats + " seats booked.");
        System.out.println("Remaining seats on flight: " + remainingSeats);
        return remainingSeats;
    }
}