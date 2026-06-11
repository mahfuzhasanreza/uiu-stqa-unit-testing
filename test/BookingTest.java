import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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

    @AfterEach
    public void tearDown() {
        vehicle = null;
        slot = null;
        startTime = null;
        endTime = null;
    }

    // Constructor and initialization tests

    @Test
    public void testBookingConstructor() {
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
        VehicleType[] types = {VehicleType.CAR, VehicleType.MOTORCYCLE, VehicleType.BICYCLE};
        
        for (VehicleType type : types) {
            Vehicle testVehicle = new Vehicle(10 + type.ordinal(), type, 500.0);
            Booking booking = new Booking(200, testVehicle, slot, startTime, endTime, 50.0);
            assertEquals(type, booking.getVehicle().getVehicleType(), "Vehicle type should match in booking");
        }
    }

    @Test
    public void testGetBookingId() {
        Booking booking = new Booking(999, vehicle, slot, startTime, endTime, 75.0);
        assertEquals(999, booking.getBookingId(), "getBookingId should return correct ID");
    }

    @Test
    public void testGetVehicle() {
        Booking booking = new Booking(102, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(vehicle, booking.getVehicle(), "getVehicle should return correct vehicle");
    }

    @Test
    public void testGetParkingSlot() {
        Booking booking = new Booking(103, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(slot, booking.getParkingSlot(), "getParkingSlot should return correct slot");
    }

    @Test
    public void testGetStartTime() {
        Booking booking = new Booking(104, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(startTime, booking.getStartTime(), "getStartTime should return correct time");
    }

    @Test
    public void testGetEndTime() {
        Booking booking = new Booking(105, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(endTime, booking.getEndTime(), "getEndTime should return correct time");
    }

    @Test
    public void testGetAmount() {
        Booking booking = new Booking(106, vehicle, slot, startTime, endTime, 235.50);
        assertEquals(235.50, booking.getAmount(), "getAmount should return correct amount");
    }

    @Test
    public void testGetBookingStatus() {
        Booking booking = new Booking(107, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "New booking should have ACTIVE status");
    }

    @Test
    public void testCompleteBooking() {
        Booking booking = new Booking(108, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Initial status is ACTIVE");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status should change to COMPLETED");
    }

    @Test
    public void testCancelBooking() {
        Booking booking = new Booking(109, vehicle, slot, startTime, endTime, 100.0);
        assertEquals(BookingStatus.ACTIVE, booking.getBookingStatus(), "Initial status is ACTIVE");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status should change to CANCELLED");
    }

    @Test
    public void testCompleteBookingMultipleTimes() {
        Booking booking = new Booking(110, vehicle, slot, startTime, endTime, 100.0);
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "First completion");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Should remain COMPLETED");
    }

    @Test
    public void testCancelBookingMultipleTimes() {
        Booking booking = new Booking(111, vehicle, slot, startTime, endTime, 100.0);
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "First cancellation");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Should remain CANCELLED");
    }

    @Test
    public void testCompleteAfterCancel() {
        Booking booking = new Booking(112, vehicle, slot, startTime, endTime, 100.0);
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status is CANCELLED");
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status changed to COMPLETED");
    }

    @Test
    public void testCancelAfterComplete() {
        Booking booking = new Booking(113, vehicle, slot, startTime, endTime, 100.0);
        
        booking.completeBooking();
        assertEquals(BookingStatus.COMPLETED, booking.getBookingStatus(), "Status is COMPLETED");
        
        booking.cancelBooking();
        assertEquals(BookingStatus.CANCELLED, booking.getBookingStatus(), "Status changed to CANCELLED");
    }

    @Test
    public void testBookingWithConsecutiveHours() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 14, 0);
        
        Booking booking = new Booking(114, vehicle, slot, start, end, 400.0);
        assertEquals(start, booking.getStartTime(), "Start time correct");
        assertEquals(end, booking.getEndTime(), "End time correct");
    }

    @Test
    public void testBookingWithSameMinute() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 30);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 11, 45);
        
        Booking booking = new Booking(115, vehicle, slot, start, end, 50.0);
        assertEquals(start, booking.getStartTime(), "Start time with minute preserved");
        assertEquals(end, booking.getEndTime(), "End time with minute preserved");
    }

    @Test
    public void testBookingWithSecondsAndNanos() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0, 30, 500000000);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 12, 0, 45, 750000000);
        
        Booking booking = new Booking(116, vehicle, slot, start, end, 100.0);
        assertEquals(start, booking.getStartTime(), "Seconds and nanos preserved in start time");
        assertEquals(end, booking.getEndTime(), "Seconds and nanos preserved in end time");
    }

    @Test
    public void testBookingMultipleDays() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 3, 10, 0);
        
        Booking booking = new Booking(117, vehicle, slot, start, end, 400.0);
        assertEquals(start, booking.getStartTime(), "Start time across days");
        assertEquals(end, booking.getEndTime(), "End time across days");
    }

    // Booking with various amounts
    @Test
    public void testBookingWithZeroAmount() {
        Booking booking = new Booking(118, vehicle, slot, startTime, endTime, 0.0);
        assertEquals(0.0, booking.getAmount(), "Booking with zero amount");
    }

    @Test
    public void testBookingWithNegativeAmount() {
        Booking booking = new Booking(119, vehicle, slot, startTime, endTime, -100.0);
        assertEquals(-100.0, booking.getAmount(), "Booking allows negative amount in constructor");
    }

    @Test
    public void testBookingWithLargeAmount() {
        Booking booking = new Booking(120, vehicle, slot, startTime, endTime, 999999.99);
        assertEquals(999999.99, booking.getAmount(), "Booking with large amount");
    }

    @Test
    public void testBookingWithDecimalAmount() {
        Booking booking = new Booking(121, vehicle, slot, startTime, endTime, 123.456);
        assertEquals(123.456, booking.getAmount(), "Booking with decimal precision");
    }

    // Booking toString tests
    @Test
    public void testBookingToString() {
        Booking booking = new Booking(122, vehicle, slot, startTime, endTime, 100.0);
        String toString = booking.toString();
        
        assertNotNull(toString, "toString should not be null");
        assertTrue(toString.contains("122"), "toString should contain booking ID");
        assertTrue(toString.contains("ACTIVE"), "toString should contain booking status");
    }

    // Test with booking times
    @Test
    public void testBookingWithMinimumDuration() {
        LocalDateTime start = LocalDateTime.of(2024, 6, 1, 10, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 6, 1, 10, 0, 0, 1);
        
        Booking booking = new Booking(123, vehicle, slot, start, end, 0.01);
        assertEquals(start, booking.getStartTime(), "Start time exact");
        assertEquals(end, booking.getEndTime(), "End time exact");
    }

    @Test
    public void testBookingWithDifferentVehicles() {
        Vehicle vehicle2 = new Vehicle(2, VehicleType.MOTORCYCLE, 500.0);
        Booking booking = new Booking(124, vehicle2, slot, startTime, endTime, 100.0);
        
        assertEquals(2, booking.getVehicle().getVehicleId(), "Booking should use correct vehicle");
        assertEquals(VehicleType.MOTORCYCLE, booking.getVehicle().getVehicleType(), "Correct vehicle type");
    }

    @Test
    public void testBookingWithDifferentSlots() {
        ParkingSlot slot2 = new ParkingSlot("S999", ParkingSlotType.LARGE);
        Booking booking = new Booking(125, vehicle, slot2, startTime, endTime, 100.0);
        
        assertEquals("S999", booking.getParkingSlot().getSlotId(), "Booking should use correct slot");
        assertEquals(ParkingSlotType.LARGE, booking.getParkingSlot().getSlotType(), "Correct slot type");
    }

    // Booking ID ranges
    @Test
    public void testBookingWithVariousIds() {
        Booking booking1 = new Booking(1, vehicle, slot, startTime, endTime, 100.0);
        Booking booking2 = new Booking(999999, vehicle, slot, startTime, endTime, 100.0);
        Booking booking3 = new Booking(-1, vehicle, slot, startTime, endTime, 100.0);
        
        assertEquals(1, booking1.getBookingId(), "Small ID");
        assertEquals(999999, booking2.getBookingId(), "Large ID");
        assertEquals(-1, booking3.getBookingId(), "Negative ID");
    }
}
