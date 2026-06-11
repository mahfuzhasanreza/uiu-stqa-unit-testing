import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ParkingSlotTest {
    private ParkingSlot slot;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @BeforeEach
    public void setUp() {
        slot = new ParkingSlot("S001", ParkingSlotType.REGULAR);
        startTime = LocalDateTime.of(2024, 6, 1, 10, 0);
        endTime = LocalDateTime.of(2024, 6, 1, 12, 0);
    }

    // ==================== ParkingSlot Constructor Tests ====================
    @Test
    public void testParkingSlotConstructor() {
        // TC-01: Verify ParkingSlot constructor initializes correctly
        ParkingSlot newSlot = new ParkingSlot("S100", ParkingSlotType.COMPACT);
        
        assertEquals("S100", newSlot.getSlotId(), "Slot ID should match");
        assertEquals(ParkingSlotType.COMPACT, newSlot.getSlotType(), "Slot type should match");
        assertTrue(newSlot.isActive(), "Slot should be active by default");
        assertEquals(0.0, newSlot.getBalance(), "Slot wallet should start empty");
        assertEquals(0, newSlot.getBookings().size(), "Slot should have no initial bookings");
    }

    // ==================== Getter Tests ====================
    @Test
    public void testGetSlotId() {
        // TC-02: Verify getSlotId returns correct ID
        assertEquals("S001", slot.getSlotId(), "getSlotId should return correct ID");
    }

    @Test
    public void testGetSlotType() {
        // TC-03: Verify getSlotType returns correct type
        assertEquals(ParkingSlotType.REGULAR, slot.getSlotType(), "getSlotType should return correct type");
    }

    @Test
    public void testGetWallet() {
        // TC-04: Verify getWallet returns wallet object
        assertNotNull(slot.getWallet(), "getWallet should return non-null wallet");
    }

    @Test
    public void testGetBookings() {
        // TC-05: Verify getBookings returns empty list initially
        assertEquals(0, slot.getBookings().size(), "Bookings list should be empty initially");
    }

    @Test
    public void testGetBalance() {
        // TC-06: Verify getBalance returns wallet balance
        assertEquals(0.0, slot.getBalance(), "Balance should be 0 initially");
    }

    @Test
    public void testIsActive() {
        // TC-07: Verify isActive returns true by default
        assertTrue(slot.isActive(), "Slot should be active by default");
    }

    // ==================== Activate/Deactivate Tests ====================
    @Test
    public void testActivateSlot() {
        // TC-08: Verify activating a slot
        slot.deactivate();
        assertFalse(slot.isActive(), "Slot should be inactive after deactivation");
        
        slot.activate();
        assertTrue(slot.isActive(), "Slot should be active after activation");
    }

    @Test
    public void testDeactivateSlot() {
        // TC-09: Verify deactivating a slot
        assertTrue(slot.isActive(), "Slot should be active initially");
        slot.deactivate();
        assertFalse(slot.isActive(), "Slot should be inactive after deactivation");
    }

    @Test
    public void testMultipleActivationDeactivations() {
        // TC-10: Verify multiple activation/deactivation cycles
        slot.deactivate();
        slot.activate();
        slot.deactivate();
        slot.deactivate();
        slot.activate();
        
        assertTrue(slot.isActive(), "Slot should be active after final activation");
    }

    // ==================== Compatibility Tests ====================
    @Test
    public void testCompatibilityMotorcycleCompactSlot() {
        // TC-11: Verify MOTORCYCLE is compatible with COMPACT slot (when available)
        ParkingSlot compactSlot = new ParkingSlot("S002", ParkingSlotType.COMPACT);
        assertTrue(compactSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime),
                "MOTORCYCLE should be compatible with COMPACT slot");
    }

    @Test
    public void testCompatibilityMotorcycleRegularSlot() {
        // TC-12: Verify MOTORCYCLE is compatible with REGULAR slot
        assertTrue(slot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime),
                "MOTORCYCLE should be compatible with REGULAR slot");
    }

    @Test
    public void testCompatibilityMotorcycleLargeSlot() {
        // TC-13: Verify MOTORCYCLE is compatible with LARGE slot
        ParkingSlot largeSlot = new ParkingSlot("S003", ParkingSlotType.LARGE);
        assertTrue(largeSlot.isCompatible(VehicleType.MOTORCYCLE, startTime, endTime),
                "MOTORCYCLE should be compatible with LARGE slot");
    }

    @Test
    public void testCompatibilityCarRegularSlot() {
        // TC-14: Verify CAR is compatible with REGULAR slot
        assertTrue(slot.isCompatible(VehicleType.CAR, startTime, endTime),
                "CAR should be compatible with REGULAR slot");
    }

    @Test
    public void testCompatibilityCarLargeSlot() {
        // TC-15: Verify CAR is compatible with LARGE slot
        ParkingSlot largeSlot = new ParkingSlot("S004", ParkingSlotType.LARGE);
        assertTrue(largeSlot.isCompatible(VehicleType.CAR, startTime, endTime),
                "CAR should be compatible with LARGE slot");
    }

    @Test
    public void testCompatibilityCarCompactSlot() {
        // TC-16: Verify CAR is NOT compatible with COMPACT slot
        ParkingSlot compactSlot = new ParkingSlot("S005", ParkingSlotType.COMPACT);
        assertFalse(compactSlot.isCompatible(VehicleType.CAR, startTime, endTime),
                "CAR should NOT be compatible with COMPACT slot");
    }

    @Test
    public void testCompatibilityBusLargeSlot() {
        // TC-17: Verify BUS is compatible with LARGE slot
        ParkingSlot largeSlot = new ParkingSlot("S006", ParkingSlotType.LARGE);
        assertTrue(largeSlot.isCompatible(VehicleType.BUS, startTime, endTime),
                "BUS should be compatible with LARGE slot");
    }

    @Test
    public void testCompatibilityBusCompactSlot() {
        // TC-18: Verify BUS is NOT compatible with COMPACT slot
        ParkingSlot compactSlot = new ParkingSlot("S007", ParkingSlotType.COMPACT);
        assertFalse(compactSlot.isCompatible(VehicleType.BUS, startTime, endTime),
                "BUS should NOT be compatible with COMPACT slot");
    }

    @Test
    public void testCompatibilityBicycleAllSlots() {
        // TC-19: Verify BICYCLE is compatible with all slot types
        for (ParkingSlotType type : ParkingSlotType.values()) {
            ParkingSlot testSlot = new ParkingSlot("S008", type);
            assertTrue(testSlot.isCompatible(VehicleType.BICYCLE, startTime, endTime),
                    "BICYCLE should be compatible with " + type + " slot");
        }
    }

    @Test
    public void testCompatibilityMicrocarCompactSlot() {
        // TC-20: Verify MICROCAR is compatible with COMPACT slot
        ParkingSlot compactSlot = new ParkingSlot("S009", ParkingSlotType.COMPACT);
        assertTrue(compactSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime),
                "MICROCAR should be compatible with COMPACT slot");
    }

    @Test
    public void testCompatibilityMicrocarRegularSlot() {
        // TC-21: Verify MICROCAR is compatible with REGULAR slot
        assertTrue(slot.isCompatible(VehicleType.MICROCAR, startTime, endTime),
                "MICROCAR should be compatible with REGULAR slot");
    }

    @Test
    public void testCompatibilityMicrocarLargeSlot() {
        // TC-22: Verify MICROCAR is NOT compatible with LARGE slot (DEFECT FOUND)
        ParkingSlot largeSlot = new ParkingSlot("S010", ParkingSlotType.LARGE);
        assertFalse(largeSlot.isCompatible(VehicleType.MICROCAR, startTime, endTime),
                "MICROCAR should NOT be compatible with LARGE slot");
    }

    @Test
    public void testCompatibilityTruckNoSlot() {
        // TC-23: Verify TRUCK is not compatible with any slot (not in compatibility matrix)
        for (ParkingSlotType type : ParkingSlotType.values()) {
            ParkingSlot testSlot = new ParkingSlot("S011", type);
            assertFalse(testSlot.isCompatible(VehicleType.TRUCK, startTime, endTime),
                    "TRUCK should NOT be compatible with " + type);
        }
    }

    @Test
    public void testCompatibilityInactiveSlot() {
        // TC-24: Verify inactive slot is not compatible with any vehicle
        slot.deactivate();
        assertFalse(slot.isCompatible(VehicleType.CAR, startTime, endTime),
                "Inactive slot should not be compatible");
    }

    // ==================== Availability Tests ====================
    @Test
    public void testAvailabilityEmptySlot() {
        // TC-25: Verify empty slot is available
        assertTrue(slot.isAvailable(startTime, endTime),
                "Empty slot should be available");
    }

    @Test
    public void testAvailabilityOverlappingBooking() {
        // TC-26: Verify overlapping booking makes slot unavailable
        Booking booking = new Booking(1, new Vehicle(1, VehicleType.CAR, 1000), slot, startTime, endTime, 100);
        slot.getBookings().add(booking);
        
        LocalDateTime overlapStart = LocalDateTime.of(2024, 6, 1, 11, 0);
        LocalDateTime overlapEnd = LocalDateTime.of(2024, 6, 1, 13, 0);
        
        assertFalse(slot.isAvailable(overlapStart, overlapEnd),
                "Slot should be unavailable for overlapping booking");
    }

    @Test
    public void testAvailabilityNonOverlappingBookingBefore() {
        // TC-27: Verify booking ending exactly when new booking starts allows booking
        Booking booking = new Booking(2, new Vehicle(2, VehicleType.CAR, 1000), slot, startTime, endTime, 100);
        slot.getBookings().add(booking);
        
        LocalDateTime nextStart = endTime;
        LocalDateTime nextEnd = LocalDateTime.of(2024, 6, 1, 14, 0);
        
        assertTrue(slot.isAvailable(nextStart, nextEnd),
                "Slot should be available when previous booking ends at requested start");
    }

    @Test
    public void testAvailabilityNonOverlappingBookingAfter() {
        // TC-28: Verify booking starting exactly when new booking ends allows booking
        LocalDateTime laterStart = LocalDateTime.of(2024, 6, 1, 14, 0);
        LocalDateTime laterEnd = LocalDateTime.of(2024, 6, 1, 16, 0);
        
        Booking booking = new Booking(3, new Vehicle(3, VehicleType.CAR, 1000), slot, laterStart, laterEnd, 100);
        slot.getBookings().add(booking);
        
        assertTrue(slot.isAvailable(startTime, endTime),
                "Slot should be available when new booking ends at existing booking start");
    }

    @Test
    public void testAvailabilityMultipleBookings() {
        // TC-29: Verify multiple non-overlapping bookings
        Booking booking1 = new Booking(4, new Vehicle(4, VehicleType.CAR, 1000), slot, 
                LocalDateTime.of(2024, 6, 1, 10, 0), LocalDateTime.of(2024, 6, 1, 12, 0), 100);
        Booking booking2 = new Booking(5, new Vehicle(5, VehicleType.CAR, 1000), slot, 
                LocalDateTime.of(2024, 6, 1, 14, 0), LocalDateTime.of(2024, 6, 1, 16, 0), 100);
        
        slot.getBookings().add(booking1);
        slot.getBookings().add(booking2);
        
        LocalDateTime availStart = LocalDateTime.of(2024, 6, 1, 12, 0);
        LocalDateTime availEnd = LocalDateTime.of(2024, 6, 1, 14, 0);
        
        assertTrue(slot.isAvailable(availStart, availEnd),
                "Gap between bookings should be available");
    }

    @Test
    public void testAvailabilityPartialOverlapStart() {
        // TC-30: Verify request starting before existing booking overlaps
        Booking booking = new Booking(6, new Vehicle(6, VehicleType.CAR, 1000), slot, startTime, endTime, 100);
        slot.getBookings().add(booking);
        
        LocalDateTime overlapStart = LocalDateTime.of(2024, 6, 1, 9, 0);
        LocalDateTime overlapEnd = LocalDateTime.of(2024, 6, 1, 11, 0);
        
        assertFalse(slot.isAvailable(overlapStart, overlapEnd),
                "Overlapping start should make slot unavailable");
    }

    @Test
    public void testAvailabilityPartialOverlapEnd() {
        // TC-31: Verify request ending after existing booking overlaps
        Booking booking = new Booking(7, new Vehicle(7, VehicleType.CAR, 1000), slot, startTime, endTime, 100);
        slot.getBookings().add(booking);
        
        LocalDateTime overlapStart = LocalDateTime.of(2024, 6, 1, 11, 0);
        LocalDateTime overlapEnd = LocalDateTime.of(2024, 6, 1, 13, 0);
        
        assertFalse(slot.isAvailable(overlapStart, overlapEnd),
                "Overlapping end should make slot unavailable");
    }

    // ==================== Wallet Operations Tests ====================
    @Test
    public void testSlotWalletReceiveFunds() {
        // TC-32: Verify slot wallet can receive funds
        slot.getWallet().addFunds(100.0);
        assertEquals(100.0, slot.getBalance(), "Slot wallet should receive funds");
    }

    @Test
    public void testSlotWalletMultipleTransactions() {
        // TC-33: Verify slot wallet handles multiple transactions
        Wallet transferWallet = new Wallet(500.0);
        transferWallet.transferFunds(slot.getWallet(), 200.0);
        
        assertEquals(200.0, slot.getBalance(), "Slot should receive transferred funds");
    }

    // ==================== Edge Cases ====================
    @Test
    public void testParkingSlotWithAllTypes() {
        // TC-34: Verify slots can be created with all types
        for (ParkingSlotType type : ParkingSlotType.values()) {
            ParkingSlot testSlot = new ParkingSlot("S_" + type, type);
            assertEquals(type, testSlot.getSlotType(), "Slot type should be " + type);
        }
    }

    @Test
    public void testParkingSlotIdWithSpecialCharacters() {
        // TC-35: Verify slot ID with various characters
        ParkingSlot testSlot = new ParkingSlot("S-A1_#123", ParkingSlotType.REGULAR);
        assertEquals("S-A1_#123", testSlot.getSlotId(), "Slot should accept special characters in ID");
    }
}
