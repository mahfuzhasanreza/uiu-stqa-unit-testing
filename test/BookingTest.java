import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class BookingTest {
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    public void setUp() {
        vehicle = new Vehicle(1, VehicleType.CAR, 1000.0);
        slot = new ParkingSlot("S001", ParkingSlotType.REGULAR);
        startTime = LocalDateTime.of(2024, 6, 1, 10, 0);
        endTime = LocalDateTime.of(2024, 6, 1, 12, 0);
    }

    // ==================== Booking Constructor Tests ====================
    @Test
    public void testBookingConstructor() {
        // TC-01: Verify Booking constructor initializes correctly
        Booking booking = new Booking(101, vehicle, slot, startTime, endTime, 100.0);
        
        assertEquals(101, booking.getBookingId(), "Booking ID should match");
        assertEquals(vehicle, booking.getVehicle(), "Vehicle should match");
        assertEquals(slot, booking.getParkingSlot(), "Parking slot should match");
        assertEquals(startTime, booking.getStartTime(), "Start time should match");
        assertEquals(endTime, booking.getEndTime(), "End time should match");
        assertEquals(100.0, booking.getAmount(), "Amount should match");
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Status should be ACTIVE");
    }

    @Test
    public void testBookingConstructorWithDifferentVehicles() {
        // TC-02: Verify Booking with different vehicle types
        VehicleType[] types = {VehicleType.CAR, VehicleType.MOTORCYCLE, VehicleType.BICYCLE};
        
        for (VehicleType type : types) {
            Vehicle testVehicle = new Vehicle(10 + type.ordinal(), type, 500.0);
            Booking booking = new Booking(200, testVehicle, slot, startTime, endTime, 50.0);
            assertEquals(type, booking.getVehicle().getVehicleType(), "Vehicle type should match in booking");
        }
    }

    // ==================== Getter Tests ====================
    @Test
    public void testGetBookingId() {
        // TC-03: Verify getBookingId returns correct ID
        Booking booking = new Booking(999, vehicle, slot, startTime, endTime, 75.0);
        assertEquals(999, booking.getBookingId(), "getBookingId should return correct ID");
    }

    @Test
    public void testGetVehicle() {
        // TC-04: Verify getVehicle returns correct vehicle
        Booking booking = new Booking(102, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(vehicle, booking.getVehicle(), "getVehicle should return correct vehicle");
    }

    @Test
    public void testGetParkingSlot() {
        // TC-05: Verify getParkingSlot returns correct slot
        Booking booking = new Booking(103, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(slot, booking.getParkingSlot(), "getParkingSlot should return correct slot");
    }

    @Test
    public void testGetStartTime() {
        // TC-06: Verify getStartTime returns correct start time
        Booking booking = new Booking(104, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(startTime, booking.getStartTime(), "getStartTime should return correct time");
    }

    @Test
    public void testGetEndTime() {
        // TC-07: Verify getEndTime returns correct end time
        Booking booking = new Booking(105, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(endTime, booking.getEndTime(), "getEndTime should return correct time");
    }

    @Test
    public void testGetAmount() {
        // TC-08: Verify getAmount returns correct amount
        Booking booking = new Booking(106, vehicle, slot, startTime, endTime, 235.50);
        assertEquals(235.50, booking.getAmount(), "getAmount should return correct amount");
    }

    @Test
    public void testGetBookingStatus() {
        // TC-09: Verify getBookingStatus returns ACTIVE for new booking
        Booking booking = new Booking(107, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "New booking should have ACTIVE status");
    }

    // ==================== Booking Status Transition Tests ====================
    @Test
    public void testCompleteBooking() {
        // TC-10: Verify completeBooking changes status to COMPLETED
        Booking booking = new Booking(108, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Initial status is ACTIVE");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status should change to COMPLETED");
    }

    @Test
    public void testCancelBooking() {
        // TC-11: Verify cancelBooking changes status to CANCELLED
        Booking booking = new Booking(109, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Initial status is ACTIVE");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status should change to CANCELLED");
    }

    @Test
    public void testCompleteBookingMultipleTimes() {
        // TC-12: Verify calling completeBooking multiple times
        Booking booking = new Booking(110, vehicle, slot, startTime, endTime, 100.0);
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "First completion");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Should remain COMPLETED");
    }

    @Test
    public void testCancelBookingMultipleTimes() {
        // TC-13: Verify calling cancelBooking multiple times
        Booking booking = new Booking(111, vehicle, slot, startTime, endTime, 100.0);
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "First cancellation");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Should remain CANCELLED");
    }

    @Test
    public void testCompleteAfterCancel() {
        // TC-14: Verify completing after cancelling (state is overwritten)
        Booking booking = new Booking(112, vehicle, slot, startTime, endTime, 100.0);
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status is CANCELLED");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status changed to COMPLETED");
    }

    @Test
    public void testCancelAfterComplete() {
        // TC-15: Verify cancelling after completion (state is overwritten)
        Booking booking = new Booking(113, vehicle, slot, startTime, endTime, 100.0);
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status is COMPLETED");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status changed to CANCELLED");
    }

    // ==================== Booking with Various Times ====================
    @Test
    public void testBookingWithConsecutiveHours() {
        // TC-16: Verify booking spanning consecutive hours
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 14, 0);
        
        Booking booking = new Booking(114, vehicle, slot, start, end, 400.0);
        assertEquals(start, booking.getStartTime(), "Start time correct");
        assertEquals(end, booking.getEndTime(), "End time correct");
    }

    @Test
    public void testBookingWithSameMinute() {
        // TC-17: Verify booking with different minute values but same hour
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 30);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 11, 45);
        
        Booking booking = new Booking(115, vehicle, slot, start, end, 50.0);
        assertEquals(start, booking.getStartTime(), "Start time with minute preserved");
        assertEquals(end, booking.getEndTime(), "End time with minute preserved");
    }

    @Test
    public void testBookingWithSecondsAndNanos() {
        // TC-18: Verify booking with seconds and nanoseconds
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0, 30, 500000000);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 12, 0, 45, 750000000);
        
        Booking booking = new Booking(116, vehicle, slot, start, end, 100.0);
        assertEquals(start, booking.getStartTime(), "Seconds and nanos preserved in start time");
        assertEquals(end, booking.getEndTime(), "Seconds and nanos preserved in end time");
    }

    @Test
    public void testBookingMultipleDays() {
        // TC-19: Verify booking spanning multiple days
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 3, 10, 0);
        
        Booking booking = new Booking(117, vehicle, slot, start, end, 400.0);
        assertEquals(start, booking.getStartTime(), "Start time across days");
        assertEquals(end, booking.getEndTime(), "End time across days");
    }

    // ==================== Booking with Various Amounts ====================
    @Test
    public void testBookingWithZeroAmount() {
        // TC-20: Verify booking with zero amount (edge case)
        Booking booking = new Booking(118, vehicle, slot, startTime, endTime, 0.0);
        assertEquals(0.0, booking.getAmount(), "Booking with zero amount");
    }

    @Test
    public void testBookingWithNegativeAmount() {
        // TC-21: Verify booking with negative amount (not validated in constructor)
        Booking booking = new Booking(119, vehicle, slot, startTime, endTime, -100.0);
        assertEquals(-100.0, booking.getAmount(), "Booking allows negative amount in constructor");
    }

    @Test
    public void testBookingWithLargeAmount() {
        // TC-22: Verify booking with large amount
        Booking booking = new Booking(120, vehicle, slot, startTime, endTime, 999999.99);
        assertEquals(999999.99, booking.getAmount(), "Booking with large amount");
    }

    @Test
    public void testBookingWithDecimalAmount() {
        // TC-23: Verify booking with decimal amount
        Booking booking = new Booking(121, vehicle, slot, startTime, endTime, 123.456);
        assertEquals(123.456, booking.getAmount(), "Booking with decimal precision");
    }

    // ==================== Booking toString Tests ====================
    @Test
    public void testBookingToString() {
        // TC-24: Verify toString includes booking information
        Booking booking = new Booking(122, vehicle, slot, startTime, endTime, 100.0);
        String toString = booking.toString();
        
        assertNotNull(toString, "toString should not be null");
        assertTrue(toString.contains("122"), "toString should contain booking ID");
        assertTrue(toString.contains("ACTIVE"), "toString should contain booking status");
    }

    // ==================== Edge Cases with Booking Times ====================
    @Test
    public void testBookingWithMinimumDuration() {
        // TC-25: Verify booking with very close start and end times
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 10, 0, 0, 1);
        
        Booking booking = new Booking(123, vehicle, slot, start, end, 0.01);
        assertEquals(start, booking.getStartTime(), "Start time exact");
        assertEquals(end, booking.getEndTime(), "End time exact");
    }

    @Test
    public void testBookingWithDifferentVehicles() {
        // TC-26: Verify booking uses correct vehicle reference
        Vehicle vehicle2 = new Vehicle(2, VehicleType.MOTORCYCLE, 500.0);
        Booking booking = new Booking(124, vehicle2, slot, startTime, endTime, 100.0);
        
        assertEquals(2, booking.getVehicle().getVehicleId(), "Booking should use correct vehicle");
        assertEquals(VehicleType.MOTORCYCLE, booking.getVehicle().getVehicleType(), "Correct vehicle type");
    }

    @Test
    public void testBookingWithDifferentSlots() {
        // TC-27: Verify booking uses correct slot reference
        ParkingSlot slot2 = new ParkingSlot("S999", ParkingSlotType.LARGE);
        Booking booking = new Booking(125, vehicle, slot2, startTime, endTime, 100.0);
        
        assertEquals("S999", booking.getParkingSlot().getSlotId(), "Booking should use correct slot");
        assertEquals(ParkingSlotType.LARGE, booking.getParkingSlot().getSlotType(), "Correct slot type");
    }

    // ==================== Booking ID Ranges ====================
    @Test
    public void testBookingWithVariousIds() {
        // TC-28: Verify bookings handle various ID values
        Booking booking1 = new Booking(1, vehicle, slot, startTime, endTime, 100.0);
        Booking booking2 = new Booking(999999, vehicle, slot, startTime, endTime, 100.0);
        Booking booking3 = new Booking(-1, vehicle, slot, startTime, endTime, 100.0);
        
        assertEquals(1, booking1.getBookingId(), "Small ID");
        assertEquals(999999, booking2.getBookingId(), "Large ID");
        assertEquals(-1, booking3.getBookingId(), "Negative ID");
    }
}
