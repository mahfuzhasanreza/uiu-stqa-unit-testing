import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;


public class ParkingSystemTest {
    private ParkingSystem parkingSystem;
    private Vehicle car;
    private Vehicle motorcycle;
    private Vehicle bus;
    private ParkingSlot compactSlot;
    private ParkingSlot regularSlot;
    private ParkingSlot largeSlot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    public void setUp() {
        parkingSystem = ParkingSystem.getInstance();
        
        parkingSystem.setVehicles(new java.util.ArrayList<>());
        parkingSystem.setBookings(new java.util.ArrayList<>());
        parkingSystem.setParkingSlots(new java.util.ArrayList<>());
        parkingSystem.setSYSTEM_WALLET(new Wallet());
        
        car = new Vehicle(1, VehicleType.CAR, 1000.0);
        motorcycle = new Vehicle(2, VehicleType.MOTORCYCLE, 500.0);
        bus = new Vehicle(3, VehicleType.BUS, 2000.0);
        
        compactSlot = new ParkingSlot("C001", ParkingSlotType.COMPACT);
        regularSlot = new ParkingSlot("R001", ParkingSlotType.REGULAR);
        largeSlot = new ParkingSlot("L001", ParkingSlotType.LARGE);
        
        parkingSystem.addVehicle(car);
        parkingSystem.addVehicle(motorcycle);
        parkingSystem.addVehicle(bus);
        
        parkingSystem.addParkingSlot(compactSlot);
        parkingSystem.addParkingSlot(regularSlot);
        parkingSystem.addParkingSlot(largeSlot);
        
        startTime = LocalDateTime.of(2024, 6, 1, 10, 0);
        endTime = LocalDateTime.of(2024, 6, 1, 12, 0);
    }

    @AfterEach
    public void tearDown() {
        parkingSystem.setVehicles(new java.util.ArrayList<>());
        parkingSystem.setBookings(new java.util.ArrayList<>());
        parkingSystem.setParkingSlots(new java.util.ArrayList<>());
        parkingSystem.setSYSTEM_WALLET(new Wallet());
    }

    // Singleton tests

    @Test
    @DisplayName("Singleton: getInstance returns same instance")
    public void testParkingSystemSingleton() {
        ParkingSystem system1 = ParkingSystem.getInstance();
        ParkingSystem system2 = ParkingSystem.getInstance();
        
        assertSame(system1, system2, "getInstance should return same instance");
    }

    // Add Vehicle tests
    @Test
    public void testAddVehicle() {
        ParkingSystem newSystem = ParkingSystem.getInstance();
        newSystem.setVehicles(new java.util.ArrayList<>());
        
        Vehicle vehicle = new Vehicle(10, VehicleType.CAR, 500.0);
        newSystem.addVehicle(vehicle);
        
        assertEquals(1, newSystem.getVehicles().size(), "Vehicle should be added");
        assertTrue(newSystem.getVehicles().contains(vehicle), "Vehicle should be in list");
    }

    @Test
    public void testAddMultipleVehicles() {
        ParkingSystem newSystem = ParkingSystem.getInstance();
        newSystem.setVehicles(new java.util.ArrayList<>());
        
        Vehicle v1 = new Vehicle(11, VehicleType.CAR, 500.0);
        Vehicle v2 = new Vehicle(12, VehicleType.MOTORCYCLE, 300.0);
        Vehicle v3 = new Vehicle(13, VehicleType.BICYCLE, 100.0);
        
        newSystem.addVehicle(v1);
        newSystem.addVehicle(v2);
        newSystem.addVehicle(v3);
        
        assertEquals(3, newSystem.getVehicles().size(), "All vehicles should be added");
    }

    // Add Parking Slot tests
    @Test
    public void testAddParkingSlot() {
        ParkingSystem newSystem = ParkingSystem.getInstance();
        newSystem.setParkingSlots(new java.util.ArrayList<>());
        
        ParkingSlot slot = new ParkingSlot("TEST001", ParkingSlotType.REGULAR);
        newSystem.addParkingSlot(slot);
        
        assertEquals(1, newSystem.getParkingSlots().size(), "Slot should be added");
        assertTrue(newSystem.getParkingSlots().contains(slot), "Slot should be in list");
    }

    @Test
    public void testAddMultipleParkingSlots() {
        ParkingSystem newSystem = ParkingSystem.getInstance();
        newSystem.setParkingSlots(new java.util.ArrayList<>());
        
        ParkingSlot s1 = new ParkingSlot("S1", ParkingSlotType.COMPACT);
        ParkingSlot s2 = new ParkingSlot("S2", ParkingSlotType.REGULAR);
        ParkingSlot s3 = new ParkingSlot("S3", ParkingSlotType.LARGE);
        
        newSystem.addParkingSlot(s1);
        newSystem.addParkingSlot(s2);
        newSystem.addParkingSlot(s3);
        
        assertEquals(3, newSystem.getParkingSlots().size(), "All slots should be added");
    }

    // Get available parking slots tests
    @Test
    public void testGetAvailableSlotsForCAR() {
        List<ParkingSlot> available = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        
        assertEquals(2, available.size(), "CAR should have 2 available slots (REGULAR, LARGE)");
        assertTrue(available.contains(regularSlot), "Should contain REGULAR slot");
        assertTrue(available.contains(largeSlot), "Should contain LARGE slot");
        assertFalse(available.contains(compactSlot), "Should not contain COMPACT slot");
    }

    @Test
    public void testGetAvailableSlotsForMOTORCYCLE() {
        List<ParkingSlot> available = parkingSystem.getAvailableParkingSlots(motorcycle, startTime, endTime);
        
        assertEquals(3, available.size(), "MOTORCYCLE should have 3 available slots");
        assertTrue(available.contains(compactSlot), "Should contain COMPACT slot");
        assertTrue(available.contains(regularSlot), "Should contain REGULAR slot");
        assertTrue(available.contains(largeSlot), "Should contain LARGE slot");
    }

    @Test
    public void testGetAvailableSlotsForBUS() {
        List<ParkingSlot> available = parkingSystem.getAvailableParkingSlots(bus, startTime, endTime);
        
        assertEquals(1, available.size(), "BUS should have 1 available slot (LARGE)");
        assertTrue(available.contains(largeSlot), "Should contain only LARGE slot");
    }

    @Test
    public void testGetAvailableSlotsForBICYCLE() {
        Vehicle bicycle = new Vehicle(4, VehicleType.BICYCLE, 200.0);
        parkingSystem.addVehicle(bicycle);
        
        ParkingSlot handicappedSlot = new ParkingSlot("H001", ParkingSlotType.HANDICAPPED);
        parkingSystem.addParkingSlot(handicappedSlot);
        
        List<ParkingSlot> available = parkingSystem.getAvailableParkingSlots(bicycle, startTime, endTime);
        
        assertEquals(4, available.size(), "BICYCLE should have all 4 slot types");
    }

    @Test
    public void testGetAvailableSlotsAfterBooking() {
        List<ParkingSlot> availableBefore = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        assertEquals(2, availableBefore.size(), "2 slots available before booking");
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        List<ParkingSlot> availableAfter = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        assertEquals(1, availableAfter.size(), "1 slot available after booking");
    }

    @Test
    public void testGetAvailableSlotsInactiveSlot() {
        regularSlot.deactivate();
        
        List<ParkingSlot> available = parkingSystem.getAvailableParkingSlots(car, startTime, endTime);
        
        assertEquals(1, available.size(), "Only LARGE slot should be available");
        assertFalse(available.contains(regularSlot), "Inactive slot should not be available");
    }

    // Book parking slot tests
    @Test
    public void testBookValidBooking() {
        double initialBalance = car.getBalance();
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertNotNull(booking, "Booking should be created");
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Booking should be ACTIVE");
        assertTrue(car.getBalance() < initialBalance, "Car balance should decrease");
        assertTrue(parkingSystem.getBalance() > 0, "System wallet should receive funds");
    }

    @Test
    public void testBookInvalidTimeRange() {
        LocalDateTime invalidEnd = startTime.minusHours(1);
        
        assertThrows(IllegalBookingTimeException.class, 
                () -> parkingSystem.book(car, regularSlot, startTime, invalidEnd),
                "Booking with end before start should fail");
    }

    @Test
    public void testBookEqualTimesThrowsException() {
        assertThrows(IllegalBookingTimeException.class, 
                () -> parkingSystem.book(car, regularSlot, startTime, startTime),
                "Booking with equal times should fail");
    }

    @Test
    public void testBookIncompatibleVehicleType() {
        assertThrows(IllegalArgumentException.class, 
                () -> parkingSystem.book(bus, compactSlot, startTime, endTime),
                "BUS cannot book COMPACT slot");
    }

    @Test
    public void testBookInsufficientFunds() {
        Vehicle poorCar = new Vehicle(50, VehicleType.CAR, 1.0);
        parkingSystem.addVehicle(poorCar);
        
        assertThrows(InsufficientFundsException.class, 
                () -> parkingSystem.book(poorCar, regularSlot, startTime, endTime),
                "Booking should fail with insufficient funds");
    }

    @Test
    public void testBookOverlappingBooking() {
        Booking booking1 = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        LocalDateTime newStart = LocalDateTime.of(2024, 6, 1, 11, 0);
        LocalDateTime newEnd = LocalDateTime.of(2024, 6, 1, 13, 0);
        Vehicle motorcycle2 = new Vehicle(5, VehicleType.MOTORCYCLE, 500.0);
        parkingSystem.addVehicle(motorcycle2);
        
        assertThrows(IllegalArgumentException.class, 
                () -> parkingSystem.book(motorcycle2, regularSlot, newStart, newEnd),
                "Overlapping booking should fail");
    }

    @Test
    public void testBookPricingCalculation() {
        double initialBalance = car.getBalance();
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertEquals(20.0, booking.getAmount(), "Booking price should be 20.0");
        assertEquals(initialBalance - 20.0, car.getBalance(), 0.01, "Car should be charged 20.0");
    }

    @Test
    public void testBookPricingWithVehicleType() {
        Vehicle bicycle = new Vehicle(6, VehicleType.BICYCLE, 100.0);
        parkingSystem.addVehicle(bicycle);
        ParkingSlot handicappedSlot = new ParkingSlot("H001", ParkingSlotType.HANDICAPPED);
        parkingSystem.addParkingSlot(handicappedSlot);
        
        Booking booking = parkingSystem.book(bicycle, handicappedSlot, startTime, endTime);
        
        assertEquals(4.8, booking.getAmount(), "BICYCLE + HANDICAPPED rate calculation");
    }

    @Test
    public void testBookTransfersFundsToSystem() {
        double systemBalanceBefore = parkingSystem.getBalance();
        double carBalanceBefore = car.getBalance();
        
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertEquals(carBalanceBefore - 20.0, car.getBalance(), 0.01, "Car balance decreased");
        assertEquals(systemBalanceBefore + 20.0, parkingSystem.getBalance(), 0.01, "System balance increased");
    }

    @Test
    public void testBookMultipleBookings() {
        Booking booking1 = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        LocalDateTime start2 = LocalDateTime.of(2024, 6, 2, 10, 0);
        LocalDateTime end2 = LocalDateTime.of(2024, 6, 2, 12, 0);
        Booking booking2 = parkingSystem.book(motorcycle, regularSlot, start2, end2);
        
        assertEquals(2, parkingSystem.getBookings().size(), "Both bookings should be stored");
        assertEquals(1, booking1.getBookingId(), "First booking ID");
        assertEquals(2, booking2.getBookingId(), "Second booking ID");
    }

    // Complete booking tests
    @Test
    public void testCompleteBooking() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double systemBalanceBefore = parkingSystem.getBalance();
        double slotBalanceBefore = regularSlot.getBalance();
        
        parkingSystem.completeBooking(booking);
        
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Booking should be COMPLETED");
        assertEquals(systemBalanceBefore - 16.0, parkingSystem.getBalance(), 0.01, "80% transferred to slot");
        assertEquals(slotBalanceBefore + 16.0, regularSlot.getBalance(), 0.01, "Slot receives 80%");
    }

    @Test
    public void testCompleteBookingDistribution() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double bookingAmount = booking.getAmount();
        
        parkingSystem.completeBooking(booking);
        
        assertEquals(bookingAmount * 0.8, regularSlot.getBalance(), 0.01, "Slot gets 80%");
    }

    @Test
    public void testCompleteBookingWalletTransfer() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        parkingSystem.completeBooking(booking);
        
        assertTrue(regularSlot.getBalance() > 0, "Slot wallet should have funds");
    }

    // Cancel booking tests
    @Test
    public void testCancelBooking() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double carBalanceBefore = car.getBalance();
        
        parkingSystem.cancelBooking(booking);
        
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Booking should be CANCELLED");
        assertEquals(carBalanceBefore + 18.0, car.getBalance(), 0.01, "Car receives 90% refund");
    }

    @Test
    public void testCancelBookingDistribution() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        double bookingAmount = booking.getAmount();
        double carBalanceAfterBooking = car.getBalance();
        
        parkingSystem.cancelBooking(booking);
        
        assertEquals(carBalanceAfterBooking + (bookingAmount * 0.9), car.getBalance(), 0.01, "Car gets 90%");
    }

    @Test
    public void testCancelBookingRefund() {
        double initialBalance = car.getBalance();
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        parkingSystem.cancelBooking(booking);
        
        assertEquals(initialBalance - 2.0, car.getBalance(), 0.01, "Net loss is 10% (20 * 0.1)");
    }

    // System wallet tests
    @Test
    public void testSystemWalletReceivesFunds() {
        parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertEquals(20.0, parkingSystem.getBalance(), 0.01, "System should have 20.0");
    }

    @Test
    public void testSystemWalletFundsAfterCompletion() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        parkingSystem.completeBooking(booking);
        
        assertEquals(4.0, parkingSystem.getBalance(), 0.01, "System keeps 20%");
    }

    @Test
    public void testSystemWalletFundsAfterCancellation() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        parkingSystem.cancelBooking(booking);
        
        assertEquals(2.0, parkingSystem.getBalance(), 0.01, "System keeps 10%");
    }

    // Pricing calculation tests
    @Test
    public void testPricingBicycleCompact() {
        Vehicle bicycle = new Vehicle(7, VehicleType.BICYCLE, 100.0);
        parkingSystem.addVehicle(bicycle);
        
        Booking booking = parkingSystem.book(bicycle, compactSlot, startTime, endTime);
        assertEquals(3.2, booking.getAmount(), 0.01, "Correct pricing");
    }

    @Test
    public void testPricingMotorcycleRegular() {
        Booking booking = parkingSystem.book(motorcycle, regularSlot, startTime, endTime);
        assertEquals(10.0, booking.getAmount(), 0.01, "Correct pricing");
    }

    @Test
    public void testPricingCarLarge() {
        Booking booking = parkingSystem.book(car, largeSlot, startTime, endTime);
        assertEquals(30.0, booking.getAmount(), 0.01, "Correct pricing");
    }

    @Test
    public void testPricingBusLarge() {
        Booking booking = parkingSystem.book(bus, largeSlot, startTime, endTime);
        assertEquals(60.0, booking.getAmount(), 0.01, "Correct pricing");
    }

    @Test
    public void testPricingWithFractionalHours() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 11, 30); 
        
        Booking booking = parkingSystem.book(car, regularSlot, start, end);
        assertEquals(10.0, booking.getAmount(), 0.01, "Fractional hours truncated to 1");
    }

    // Booking storage tests
    @Test
    public void testBookingStoredInSystem() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertTrue(parkingSystem.getBookings().contains(booking), "Booking should be in system");
    }

    @Test
    public void testBookingStoredInSlot() {
        Booking booking = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        assertTrue(regularSlot.getBookings().contains(booking), "Booking should be in slot");
    }

    @Test
    public void testMultipleBookingsStorage() {
        Booking booking1 = parkingSystem.book(car, regularSlot, startTime, endTime);
        
        LocalDateTime start2 = LocalDateTime.of(2024, 6, 2, 10, 0);
        LocalDateTime end2 = LocalDateTime.of(2024, 6, 2, 12, 0);
        Booking booking2 = parkingSystem.book(motorcycle, regularSlot, start2, end2);
        
        assertEquals(2, regularSlot.getBookings().size(), "Both bookings in slot");
        assertEquals(2, parkingSystem.getBookings().size(), "Both bookings in system");
    }

    // Different scenarios
    @Test
    public void testParkingRateConfiguration() {
        double originalRate = parkingSystem.getPARKING_RATE_PER_HOUR();
        assertEquals(10.0, originalRate, "Default rate should be 10.0");
        
        parkingSystem.setPARKING_RATE_PER_HOUR(15.0);
        assertEquals(15.0, parkingSystem.getPARKING_RATE_PER_HOUR(), "Rate should be updated");
    }
}
