import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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

    // Constructor Tests

    @Test
    public void testVehicleConstructorWithWallet() {
        Vehicle vehicle = new Vehicle(101, VehicleType.CAR, testWallet);
        
        assertEquals(101, vehicle.getVehicleId(), "Vehicle ID should match");
        assertEquals(VehicleType.CAR, vehicle.getVehicleType(), "Vehicle type should match");
        assertEquals(1000.0, vehicle.getBalance(), "Wallet balance should match");
    }

    @Test
    public void testVehicleConstructorWithInitialBalance() {
        Vehicle vehicle = new Vehicle(102, VehicleType.MOTORCYCLE, 500.0);
        
        assertEquals(102, vehicle.getVehicleId(), "Vehicle ID should match");
        assertEquals(VehicleType.MOTORCYCLE, vehicle.getVehicleType(), "Vehicle type should match");
        assertEquals(500.0, vehicle.getBalance(), "Initial balance should be set");
    }

    @Test
    public void testVehicleConstructorWithZeroBalance() {
        Vehicle vehicle = new Vehicle(103, VehicleType.BICYCLE, 0.0);
        
        assertEquals(0.0, vehicle.getBalance(), "Vehicle should have zero balance");
    }

    @Test
    public void testVehicleConstructorAllTypes() {
        VehicleType[] types = {VehicleType.CAR, VehicleType.MOTORCYCLE, VehicleType.TRUCK, 
                              VehicleType.BICYCLE, VehicleType.MICROCAR, VehicleType.BUS};
        
        for (int i = 0; i < types.length; i++) {
            Vehicle vehicle = new Vehicle(200 + i, types[i], 100.0);
            assertEquals(types[i], vehicle.getVehicleType(), "Vehicle type should be " + types[i]);
        }
    }

    // Getter Tests
    @Test
    public void testGetVehicleId() {
        Vehicle vehicle = new Vehicle(12345, VehicleType.CAR, testWallet);
        assertEquals(12345, vehicle.getVehicleId(), "getVehicleId should return correct ID");
    }

    @Test
    public void testGetVehicleType() {
        Vehicle vehicle = new Vehicle(105, VehicleType.BUS, testWallet);
        assertEquals(VehicleType.BUS, vehicle.getVehicleType(), "getVehicleType should return correct type");
    }

    @Test
    public void testGetWallet() {
        Wallet wallet = new Wallet(750.0);
        Vehicle vehicle = new Vehicle(106, VehicleType.CAR, wallet);
        
        assertSame(wallet, vehicle.getWallet(), "getWallet should return the same wallet object");
    }

    @Test
    public void testGetBalance() {
        Vehicle vehicle = new Vehicle(107, VehicleType.MICROCAR, 999.99);
        assertEquals(999.99, vehicle.getBalance(), "getBalance should match wallet balance");
    }

    @Test
    public void testGetBalanceAfterModification() {
        Wallet wallet = new Wallet(500.0);
        Vehicle vehicle = new Vehicle(108, VehicleType.TRUCK, wallet);
        
        wallet.addFunds(100.0);
        assertEquals(600.0, vehicle.getBalance(), "getBalance should reflect wallet modifications");
    }

    // Vehicle with wallet operations tests
    @Test
    public void testVehicleWalletAddFunds() {
        Vehicle vehicle = new Vehicle(109, VehicleType.CAR, 100.0);
        vehicle.getWallet().addFunds(50.0);
        
        assertEquals(150.0, vehicle.getBalance(), "Vehicle balance should increase after addFunds");
    }

    @Test
    public void testVehicleWalletDeductFunds() {
        Vehicle vehicle = new Vehicle(110, VehicleType.MOTORCYCLE, 200.0);
        vehicle.getWallet().deductFunds(75.0);
        
        assertEquals(125.0, vehicle.getBalance(), "Vehicle balance should decrease after deductFunds");
    }

    @Test
    public void testVehicleWalletInsufficientFunds() {
        Vehicle vehicle = new Vehicle(111, VehicleType.BICYCLE, 100.0);
        
        assertThrows(InsufficientFundsException.class, () -> vehicle.getWallet().deductFunds(150.0),
                "Deducting more than balance should throw exception");
    }

    @Test
    public void testVehicleWalletTransfer() {
        Wallet targetWallet = new Wallet(0.0);
        Vehicle vehicle = new Vehicle(112, VehicleType.CAR, 300.0);
        
        vehicle.getWallet().transferFunds(targetWallet, 100.0);
        
        assertEquals(200.0, vehicle.getBalance(), "Vehicle balance should decrease after transfer");
        assertEquals(100.0, targetWallet.getBalance(), "Target wallet should receive funds");
    }

    // Vehicle identity and independence tests
    @Test
    public void testVehicleWithSameIdAndDifferentWallet() {
        Wallet wallet1 = new Wallet(100.0);
        Wallet wallet2 = new Wallet(100.0);
        Vehicle vehicle1 = new Vehicle(113, VehicleType.CAR, wallet1);
        Vehicle vehicle2 = new Vehicle(113, VehicleType.CAR, wallet2);
        
        assertEquals(vehicle1.getVehicleId(), vehicle2.getVehicleId(), "IDs should be same");
        assertNotSame(vehicle1, vehicle2, "Objects should be different");
    }

    @Test
    public void testMultipleVehiclesIndependence() {
        Vehicle car = new Vehicle(114, VehicleType.CAR, 500.0);
        Vehicle bike = new Vehicle(115, VehicleType.MOTORCYCLE, 300.0);
        
        car.getWallet().deductFunds(100.0);
        
        assertEquals(400.0, car.getBalance(), "Car balance should decrease");
        assertEquals(300.0, bike.getBalance(), "Bike balance should not change");
    }

    // Vehicle toString tests
    @Test
    public void testVehicleToString() {
        Vehicle vehicle = new Vehicle(116, VehicleType.CAR, 250.5);
        String toString = vehicle.toString();
        
        assertNotNull(toString, "toString should not return null");
        assertTrue(toString.contains("116"), "toString should contain vehicle ID");
        assertTrue(toString.contains("CAR"), "toString should contain vehicle type");
        assertTrue(toString.contains("250.5"), "toString should contain balance");
    }

    // Different scenarios for vehicle creation and balance handling
    @Test
    public void testVehicleWithNegativeBalance() {
        Vehicle vehicle = new Vehicle(117, VehicleType.CAR, -100.0);
        assertEquals(-100.0, vehicle.getBalance(), "Vehicle should have negative balance if created");
    }

    @Test
    public void testVehicleWithLargeBalance() {
        double largeBalance = 1_000_000.99;
        Vehicle vehicle = new Vehicle(118, VehicleType.BUS, largeBalance);
        assertEquals(largeBalance, vehicle.getBalance(), "Vehicle should handle large balance");
    }

    @Test
    public void testVehicleIdRanges() {
        Vehicle vehicle1 = new Vehicle(1, VehicleType.CAR, 100.0);
        Vehicle vehicle2 = new Vehicle(999999, VehicleType.CAR, 100.0);
        Vehicle vehicle3 = new Vehicle(-1, VehicleType.CAR, 100.0);
        
        assertEquals(1, vehicle1.getVehicleId(), "Small positive ID");
        assertEquals(999999, vehicle2.getVehicleId(), "Large positive ID");
        assertEquals(-1, vehicle3.getVehicleId(), "Negative ID");
    }

    @Test
    public void testVehicleWithSharedWallet() {
        Wallet sharedWallet = new Wallet(1000.0);
        Vehicle vehicle1 = new Vehicle(119, VehicleType.CAR, sharedWallet);
        Vehicle vehicle2 = new Vehicle(120, VehicleType.MOTORCYCLE, sharedWallet);
        
        vehicle1.getWallet().deductFunds(100.0);
        
        assertEquals(900.0, vehicle1.getBalance(), "Vehicle1 balance decreased");
        assertEquals(900.0, vehicle2.getBalance(), "Vehicle2 balance also decreased (shared wallet)");
    }
}
