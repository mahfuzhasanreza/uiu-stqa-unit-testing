import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for Vehicle class
 * 
 * Test Scaffolding:
 * - @BeforeEach (setUp): Initializes test fixtures before each test
 * - @AfterEach (tearDown): Cleans up resources after each test
 * 
 * Each test follows the AAA pattern:
 * - Arrange: Set up test data and preconditions
 * - Act: Execute the method being tested
 * - Assert: Verify the results and object state
 * 
 * Tests are independent - each test can run in any order
 * 
 * Testing focus:
 * - Constructor validation with different parameters
 * - Dependency injection (Wallet integration)
 * - State verification through getters
 * - Boundary conditions and edge cases
 */
public class VehicleTest {
    private Wallet testWallet;

    @BeforeEach
    public void setUp() {
        testWallet = new Wallet(1000.0);
    }

    @AfterEach
    public void tearDown() {
        testWallet = null;
    }

    // ==================== CONSTRUCTOR TESTS ====================
    // Testing: Vehicle initialization with different input parameters

    @Test
    public void testVehicleConstructorWithWallet() {
        // ARRANGE: Wallet is prepared in setUp()
        // ACT: Create vehicle with wallet object
        Vehicle vehicle = new Vehicle(101, VehicleType.CAR, testWallet);
        
        // ASSERT: Verify all fields initialized correctly
        assertEquals(101, vehicle.getVehicleId(), "Vehicle ID should match");
        assertEquals(VehicleType.CAR, vehicle.getVehicleType(), "Vehicle type should match");
        assertEquals(1000.0, vehicle.getBalance(), "Wallet balance should match");
    }

    @Test
    public void testVehicleConstructorWithInitialBalance() {
        // TC-02: Verify Vehicle constructor with initial balance parameter
        Vehicle vehicle = new Vehicle(102, VehicleType.MOTORCYCLE, 500.0);
        
        assertEquals(102, vehicle.getVehicleId(), "Vehicle ID should match");
        assertEquals(VehicleType.MOTORCYCLE, vehicle.getVehicleType(), "Vehicle type should match");
        assertEquals(500.0, vehicle.getBalance(), "Initial balance should be set");
    }

    @Test
    public void testVehicleConstructorWithZeroBalance() {
        // TC-03: Verify Vehicle can be created with zero balance
        Vehicle vehicle = new Vehicle(103, VehicleType.BICYCLE, 0.0);
        
        assertEquals(0.0, vehicle.getBalance(), "Vehicle should have zero balance");
    }

    @Test
    public void testVehicleConstructorAllTypes() {
        // TC-04: Verify Vehicle can be created with all VehicleTypes
        VehicleType[] types = {VehicleType.CAR, VehicleType.MOTORCYCLE, VehicleType.TRUCK, 
                              VehicleType.BICYCLE, VehicleType.MICROCAR, VehicleType.BUS};
        
        for (int i = 0; i < types.length; i++) {
            Vehicle vehicle = new Vehicle(200 + i, types[i], 100.0);
            assertEquals(types[i], vehicle.getVehicleType(), "Vehicle type should be " + types[i]);
        }
    }

    // ==================== Getter Tests ====================
    @Test
    public void testGetVehicleId() {
        // TC-05: Verify getVehicleId returns correct ID
        Vehicle vehicle = new Vehicle(12345, VehicleType.CAR, testWallet);
        assertEquals(12345, vehicle.getVehicleId(), "getVehicleId should return correct ID");
    }

    @Test
    public void testGetVehicleType() {
        // TC-06: Verify getVehicleType returns correct type
        Vehicle vehicle = new Vehicle(105, VehicleType.BUS, testWallet);
        assertEquals(VehicleType.BUS, vehicle.getVehicleType(), "getVehicleType should return correct type");
    }

    @Test
    public void testGetWallet() {
        // TC-07: Verify getWallet returns the wallet object
        Wallet wallet = new Wallet(750.0);
        Vehicle vehicle = new Vehicle(106, VehicleType.CAR, wallet);
        
        assertSame(wallet, vehicle.getWallet(), "getWallet should return the same wallet object");
    }

    @Test
    public void testGetBalance() {
        // TC-08: Verify getBalance returns correct wallet balance
        Vehicle vehicle = new Vehicle(107, VehicleType.MICROCAR, 999.99);
        assertEquals(999.99, vehicle.getBalance(), "getBalance should match wallet balance");
    }

    @Test
    public void testGetBalanceAfterModification() {
        // TC-09: Verify getBalance reflects wallet changes
        Wallet wallet = new Wallet(500.0);
        Vehicle vehicle = new Vehicle(108, VehicleType.TRUCK, wallet);
        
        wallet.addFunds(100.0);
        assertEquals(600.0, vehicle.getBalance(), "getBalance should reflect wallet modifications");
    }

    // ==================== Vehicle with Wallet Manipulation Tests ====================
    @Test
    public void testVehicleWalletAddFunds() {
        // TC-10: Verify vehicle wallet can receive funds
        Vehicle vehicle = new Vehicle(109, VehicleType.CAR, 100.0);
        vehicle.getWallet().addFunds(50.0);
        
        assertEquals(150.0, vehicle.getBalance(), "Vehicle balance should increase after addFunds");
    }

    @Test
    public void testVehicleWalletDeductFunds() {
        // TC-11: Verify vehicle wallet can lose funds
        Vehicle vehicle = new Vehicle(110, VehicleType.MOTORCYCLE, 200.0);
        vehicle.getWallet().deductFunds(75.0);
        
        assertEquals(125.0, vehicle.getBalance(), "Vehicle balance should decrease after deductFunds");
    }

    @Test
    public void testVehicleWalletInsufficientFunds() {
        // TC-12: Verify deducting more than balance fails
        Vehicle vehicle = new Vehicle(111, VehicleType.BICYCLE, 100.0);
        
        assertThrows(InsufficientFundsException.class, () -> vehicle.getWallet().deductFunds(150.0),
                "Deducting more than balance should throw exception");
    }

    @Test
    public void testVehicleWalletTransfer() {
        // TC-13: Verify vehicle can transfer funds to another wallet
        Wallet targetWallet = new Wallet(0.0);
        Vehicle vehicle = new Vehicle(112, VehicleType.CAR, 300.0);
        
        vehicle.getWallet().transferFunds(targetWallet, 100.0);
        
        assertEquals(200.0, vehicle.getBalance(), "Vehicle balance should decrease after transfer");
        assertEquals(100.0, targetWallet.getBalance(), "Target wallet should receive funds");
    }

    // ==================== Vehicle Equality and Identity Tests ====================
    @Test
    public void testVehicleWithSameIdAndDifferentWallet() {
        // TC-14: Verify two vehicles with same ID but different wallets are different objects
        Wallet wallet1 = new Wallet(100.0);
        Wallet wallet2 = new Wallet(100.0);
        Vehicle vehicle1 = new Vehicle(113, VehicleType.CAR, wallet1);
        Vehicle vehicle2 = new Vehicle(113, VehicleType.CAR, wallet2);
        
        assertEquals(vehicle1.getVehicleId(), vehicle2.getVehicleId(), "IDs should be same");
        assertNotSame(vehicle1, vehicle2, "Objects should be different");
    }

    @Test
    public void testMultipleVehiclesIndependence() {
        // TC-15: Verify multiple vehicles have independent wallets
        Vehicle car = new Vehicle(114, VehicleType.CAR, 500.0);
        Vehicle bike = new Vehicle(115, VehicleType.MOTORCYCLE, 300.0);
        
        car.getWallet().deductFunds(100.0);
        
        assertEquals(400.0, car.getBalance(), "Car balance should decrease");
        assertEquals(300.0, bike.getBalance(), "Bike balance should not change");
    }

    // ==================== Vehicle toString Tests ====================
    @Test
    public void testVehicleToString() {
        // TC-16: Verify toString includes vehicle information
        Vehicle vehicle = new Vehicle(116, VehicleType.CAR, 250.5);
        String toString = vehicle.toString();
        
        assertNotNull(toString, "toString should not return null");
        assertTrue(toString.contains("116"), "toString should contain vehicle ID");
        assertTrue(toString.contains("CAR"), "toString should contain vehicle type");
        assertTrue(toString.contains("250.5"), "toString should contain balance");
    }

    // ==================== Edge Case Tests ====================
    @Test
    public void testVehicleWithNegativeBalance() {
        // TC-17: Verify vehicle can be created with negative balance (constructor allows it)
        Vehicle vehicle = new Vehicle(117, VehicleType.CAR, -100.0);
        assertEquals(-100.0, vehicle.getBalance(), "Vehicle should have negative balance if created");
    }

    @Test
    public void testVehicleWithLargeBalance() {
        // TC-18: Verify vehicle handles large balance amounts
        double largeBalance = 1_000_000.99;
        Vehicle vehicle = new Vehicle(118, VehicleType.BUS, largeBalance);
        assertEquals(largeBalance, vehicle.getBalance(), "Vehicle should handle large balance");
    }

    @Test
    public void testVehicleIdRanges() {
        // TC-19: Verify vehicle handles various ID ranges
        Vehicle vehicle1 = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle vehicle2 = new Vehicle(999999, VehicleType.CAR, 100.0);
        Vehicle vehicle3 = new Vehicle(-1, VehicleType.CAR, 100.0);
        
        assertEquals(1, vehicle1.getVehicleId(), "Small positive ID");
        assertEquals(999999, vehicle2.getVehicleId(), "Large positive ID");
        assertEquals(-1, vehicle3.getVehicleId(), "Negative ID");
    }

    @Test
    public void testVehicleWithSharedWallet() {
        // TC-20: Verify multiple vehicles can reference the same wallet (shared resource)
        Wallet sharedWallet = new Wallet(1000.0);
        Vehicle vehicle1 = new Vehicle(119, VehicleType.CAR, sharedWallet);
        Vehicle vehicle2 = new Vehicle(120, VehicleType.MOTORCYCLE, sharedWallet);
        
        vehicle1.getWallet().deductFunds(100.0);
        
        assertEquals(900.0, vehicle1.getBalance(), "Vehicle1 balance decreased");
        assertEquals(900.0, vehicle2.getBalance(), "Vehicle2 balance also decreased (shared wallet)");
    }
}
