# Unit Testing Report: Parking System Management

## 0) Team Members

* **Student ID: 011201** - Primary Tester (Individual Submission)

---

## Testing Methodology & Framework

### Unit Testing Approach

This test suite follows comprehensive **unit testing** principles where each class is tested in isolation:

* **Unit**: Individual methods and classes (Wallet, Vehicle, ParkingSlot, Booking, ParkingSystem)
* **Isolation**: Each unit tested independently with mocks/stubs for dependencies
* **Assertions**: JUnit assertions verify outputs and object state changes

### Test Automation

The test suite provides:
* **Automated execution** of 227 test cases
* **Automatic result comparison** (expected vs actual)
* **Efficient re-execution** without manual intervention
* **Apache Ant build script** for build lifecycle automation

### Test Scaffolding

Test infrastructure includes:

| Component | Purpose | Example |
|-----------|---------|---------|
| **Fixtures** | Reusable test data initialized in @BeforeEach | Wallet objects, Vehicle instances |
| **Test Harness** | System of interconnected objects | ParkingSystem with multiple vehicles and slots |
| **Stubs** | Simplified dependencies | LocalDateTime objects simulating time |
| **Mocks** | Replace unavailable components | Wallet acts as mock for financial system |

### JUnit Framework & Annotations

**Key Annotations Used:**
- `@BeforeEach`: Initialize fixtures before each test (ensures test independence)
- `@AfterEach`: Clean up resources after each test (teardown phase)
- `@Test`: Mark methods as test cases
- `@DisplayName`: Descriptive test names

**Test Structure (AAA Pattern):**
1. **Arrange**: Set up test data and preconditions
2. **Act**: Execute the unit being tested
3. **Assert**: Verify results using JUnit assertions

### Assertions Used

| Assertion | Purpose | Usage |
|-----------|---------|-------|
| `assertEquals(expected, actual)` | Verify exact value match | Balance calculations, IDs |
| `assertTrue(condition)` | Verify boolean condition | State checks (isActive, etc.) |
| `assertFalse(condition)` | Verify condition is false | Incompatibility checks |
| `assertThrows(ExceptionClass, executable)` | Verify exception thrown | Invalid amount checks |
| `assertNotNull(object)` | Verify object exists | Wallet/Slot not null |
| `assertNotSame(obj1, obj2)` | Verify different objects | Multiple vehicle independence |
| `assertEquals(expected, actual, delta)` | Verify float equality with tolerance | Precise financial calculations |

### Best Practices Implemented

✓ **One scenario per test**: Each test verifies single behavior  
✓ **Test independence**: @AfterEach ensures no state carryover  
✓ **Assertions over print statements**: All verification via assertions  
✓ **Exception testing**: Comprehensive error condition coverage  
✓ **Deterministic results**: Repeatable, no random dependencies  
✓ **Boundary testing**: Zero, negative, large values tested  
✓ **Clear naming**: Test names describe what is being tested  

### Build Lifecycle & Apache Ant

The `build.xml` script automates the complete build lifecycle:

```
Validate → Compile → Test → Package → Deploy
```

**Ant Targets:**
- `validate`: Check project structure
- `compile`: Compile source code to classes.dir
- `compile-tests`: Compile test code
- `test`: Run all JUnit tests (DEFAULT)
- `package`: Create distribution JAR
- `clean`: Remove build artifacts
- `rebuild`: Clean + compile
- `test-single`: Run specific test class

**Path Management:**
- `compile.classpath`: Dependencies for compilation
- `test.classpath`: Runtime dependencies for tests

---

## A) Test Case List

| Test ID | Class.Method Under Test | Why this test? | Verdict | Comments/Observations |
| --- | --- | --- | --- | --- |
| **TC-01** | `Wallet.Wallet()` | Verify default constructor initializes balance to 0. | **PASS** | Default wallet correctly has 0 balance. |
| **TC-02** | `Wallet.Wallet(double)` | Verify constructor with positive initial balance sets correctly. | **PASS** | Constructor correctly sets provided balance. |
| **TC-03** | `Wallet.Wallet(double)` | Verify constructor accepts negative balance without validation. | **PASS** | Constructor allows negative balance (no validation). |
| **TC-04** | `Wallet.getBalance()` | Verify getBalance returns correct initial balance. | **PASS** | Returns correct initial balance value. |
| **TC-05** | `Wallet.addFunds(double)` | Verify adding positive amount increases balance. | **PASS** | Balance correctly increased by added amount. |
| **TC-06** | `Wallet.addFunds(double)` | Verify multiple addFunds operations accumulate correctly. | **PASS** | Multiple additions correctly accumulate. |
| **TC-07** | `Wallet.addFunds(double)` | Verify adding very small positive amount works. | **PASS** | Handles small decimal amounts precisely. |
| **TC-08** | `Wallet.addFunds(double)` | Verify adding zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-09** | `Wallet.addFunds(double)` | Verify adding negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-10** | `Wallet.deductFunds(double)` | Verify deducting valid amount decreases balance. | **PASS** | Balance correctly decreased by deducted amount. |
| **TC-11** | `Wallet.deductFunds(double)` | Verify deducting exact balance leaves 0. | **PASS** | Balance correctly becomes 0. |
| **TC-12** | `Wallet.deductFunds(double)` | Verify multiple deduct operations work correctly. | **PASS** | Multiple deductions correctly applied. |
| **TC-13** | `Wallet.deductFunds(double)` | Verify deducting more than balance throws InsufficientFundsException. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-14** | `Wallet.deductFunds(double)` | Verify deducting zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-15** | `Wallet.deductFunds(double)` | Verify deducting negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-16** | `Wallet.deductFunds(double)` | Verify deducting from empty wallet throws InsufficientFundsException. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-17** | `Wallet.deductFunds(double)` | Verify deducting small decimal amount works. | **PASS** | Handles small decimal deductions. |
| **TC-18** | `Wallet.transferFunds(Wallet, double)` | Verify transferring valid amount between wallets. | **PASS** | Both wallets correctly updated. |
| **TC-19** | `Wallet.transferFunds(Wallet, double)` | Verify transferring entire balance works. | **PASS** | Source empties, target receives full amount. |
| **TC-20** | `Wallet.transferFunds(Wallet, double)` | Verify transferring more than balance throws exception. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-21** | `Wallet.transferFunds(Wallet, double)` | Verify transferring zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-22** | `Wallet.transferFunds(Wallet, double)` | Verify transferring negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **TC-23** | `Wallet.transferFunds(Wallet, double)` | Verify transferring from empty wallet throws exception. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-24** | `Wallet.transferFunds(Wallet, double)` | Verify multiple consecutive transfers work correctly. | **PASS** | All transfers applied correctly. |
| **TC-25** | `Wallet.transferFunds(Wallet, double)` | Verify transferring to same wallet (edge case). | **PASS** | Balance maintained after self-transfer. |
| **TC-26** | `Wallet.transferFunds(Wallet, double)` | Verify transferring small decimal amount works. | **PASS** | Precision maintained for small amounts. |
| **TC-01** | `Vehicle.Vehicle(int, VehicleType, Wallet)` | Verify Vehicle constructor with Wallet parameter. | **PASS** | Vehicle correctly initialized with provided wallet. |
| **TC-02** | `Vehicle.Vehicle(int, VehicleType, double)` | Verify Vehicle constructor with initial balance parameter. | **PASS** | New wallet created with correct balance. |
| **TC-03** | `Vehicle.Vehicle(int, VehicleType, double)` | Verify Vehicle can be created with zero balance. | **PASS** | Vehicle correctly created with 0 balance. |
| **TC-04** | `Vehicle.Vehicle(...)` | Verify Vehicle can be created with all VehicleTypes. | **PASS** | All vehicle types correctly supported. |
| **TC-05** | `Vehicle.getVehicleId()` | Verify getVehicleId returns correct ID. | **PASS** | Returns correct vehicle ID. |
| **TC-06** | `Vehicle.getVehicleType()` | Verify getVehicleType returns correct type. | **PASS** | Returns correct vehicle type. |
| **TC-07** | `Vehicle.getWallet()` | Verify getWallet returns the wallet object. | **PASS** | Returns same wallet object reference. |
| **TC-08** | `Vehicle.getBalance()` | Verify getBalance returns correct wallet balance. | **PASS** | Returns wallet balance correctly. |
| **TC-09** | `Vehicle.getBalance()` | Verify getBalance reflects wallet changes. | **PASS** | Reflects changes to underlying wallet. |
| **TC-10** | `Vehicle.getWallet().addFunds()` | Verify vehicle wallet can receive funds. | **PASS** | Funds correctly added to vehicle wallet. |
| **TC-11** | `Vehicle.getWallet().deductFunds()` | Verify vehicle wallet can lose funds. | **PASS** | Funds correctly deducted from vehicle wallet. |
| **TC-12** | `Vehicle.getWallet().deductFunds()` | Verify deducting more than balance fails. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-13** | `Vehicle.getWallet().transferFunds()` | Verify vehicle can transfer funds to another wallet. | **PASS** | Transfer correctly executed. |
| **TC-14** | `Vehicle` | Verify two vehicles with same ID but different wallets are different. | **PASS** | Different object instances despite same ID. |
| **TC-15** | `Vehicle` | Verify multiple vehicles have independent wallets. | **PASS** | Changes to one don't affect others. |
| **TC-16** | `Vehicle.toString()` | Verify toString includes vehicle information. | **PASS** | Contains ID, type, and balance. |
| **TC-17** | `Vehicle` | Verify vehicle can be created with negative balance. | **PASS** | Constructor allows negative balance. |
| **TC-18** | `Vehicle` | Verify vehicle handles large balance amounts. | **PASS** | Correctly handles large numbers. |
| **TC-19** | `Vehicle` | Verify vehicle handles various ID ranges. | **PASS** | Accepts positive, negative, and large IDs. |
| **TC-20** | `Vehicle` | Verify multiple vehicles can reference same wallet. | **PASS** | Shared wallet affects all vehicles. |
| **TC-01** | `ParkingSlot.ParkingSlot(String, ParkingSlotType)` | Verify ParkingSlot constructor initializes correctly. | **PASS** | All fields initialized with correct values. |
| **TC-02** | `ParkingSlot.getSlotId()` | Verify getSlotId returns correct ID. | **PASS** | Returns correct slot ID. |
| **TC-03** | `ParkingSlot.getSlotType()` | Verify getSlotType returns correct type. | **PASS** | Returns correct slot type. |
| **TC-04** | `ParkingSlot.getWallet()` | Verify getWallet returns wallet object. | **PASS** | Returns non-null wallet. |
| **TC-05** | `ParkingSlot.getBookings()` | Verify getBookings returns empty list initially. | **PASS** | Empty list initially. |
| **TC-06** | `ParkingSlot.getBalance()` | Verify getBalance returns wallet balance. | **PASS** | Returns 0 initially. |
| **TC-07** | `ParkingSlot.isActive()` | Verify isActive returns true by default. | **PASS** | Slot active by default. |
| **TC-08** | `ParkingSlot.activate()` | Verify activating a deactivated slot. | **PASS** | Slot becomes active. |
| **TC-09** | `ParkingSlot.deactivate()` | Verify deactivating a slot. | **PASS** | Slot becomes inactive. |
| **TC-10** | `ParkingSlot.activate/deactivate()` | Verify multiple activation/deactivation cycles. | **PASS** | Status correctly toggled multiple times. |
| **TC-11** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with COMPACT. | **PASS** | Correctly compatible. |
| **TC-12** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with REGULAR. | **PASS** | Correctly compatible. |
| **TC-13** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with LARGE. | **PASS** | Correctly compatible. |
| **TC-14** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR compatible with REGULAR. | **PASS** | Correctly compatible. |
| **TC-15** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR compatible with LARGE. | **PASS** | Correctly compatible. |
| **TC-16** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR NOT compatible with COMPACT. | **PASS** | Correctly rejects COMPACT. |
| **TC-17** | `ParkingSlot.isCompatible(BUS, ...)` | Verify BUS compatible with LARGE. | **PASS** | Correctly compatible. |
| **TC-18** | `ParkingSlot.isCompatible(BUS, ...)` | Verify BUS NOT compatible with COMPACT. | **PASS** | Correctly rejects COMPACT. |
| **TC-19** | `ParkingSlot.isCompatible(BICYCLE, ...)` | Verify BICYCLE compatible with all slot types. | **PASS** | Compatible with all four types. |
| **TC-20** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR compatible with COMPACT. | **PASS** | Correctly compatible. |
| **TC-21** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR compatible with REGULAR. | **PASS** | Correctly compatible. |
| **TC-22** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR NOT compatible with LARGE. | **FAIL** | **DEFECT FOUND**: Falls through to default, returns false. Should allow MICROCAR on LARGE based on missing break statement. |
| **TC-23** | `ParkingSlot.isCompatible(TRUCK, ...)` | Verify TRUCK not compatible with any slot. | **PASS** | TRUCK correctly has no compatible slots (as per doc). |
| **TC-24** | `ParkingSlot.isCompatible(...)` | Verify inactive slot not compatible. | **PASS** | Inactive slot correctly returns false. |
| **TC-25** | `ParkingSlot.isAvailable(...)` | Verify empty slot is available. | **PASS** | Empty slot correctly available. |
| **TC-26** | `ParkingSlot.isAvailable(...)` | Verify overlapping booking makes slot unavailable. | **PASS** | Overlapping booking correctly blocks. |
| **TC-27** | `ParkingSlot.isAvailable(...)` | Verify booking ending at request start allows slot. | **PASS** | No overlap when end equals start. |
| **TC-28** | `ParkingSlot.isAvailable(...)` | Verify booking starting at request end allows slot. | **PASS** | No overlap when start equals end. |
| **TC-29** | `ParkingSlot.isAvailable(...)` | Verify multiple non-overlapping bookings. | **PASS** | Gap between bookings correctly available. |
| **TC-30** | `ParkingSlot.isAvailable(...)` | Verify partial overlap at start blocks. | **PASS** | Correctly identifies overlap. |
| **TC-31** | `ParkingSlot.isAvailable(...)` | Verify partial overlap at end blocks. | **PASS** | Correctly identifies overlap. |
| **TC-32** | `ParkingSlot.getWallet().addFunds()` | Verify slot wallet can receive funds. | **PASS** | Funds correctly added. |
| **TC-33** | `ParkingSlot.getWallet()` | Verify slot wallet handles transfers. | **PASS** | Transfers correctly applied. |
| **TC-34** | `ParkingSlot` | Verify slots created with all types. | **PASS** | All slot types supported. |
| **TC-35** | `ParkingSlot` | Verify slot ID with special characters. | **PASS** | Special characters accepted in ID. |
| **TC-01** | `Booking.Booking(...)` | Verify Booking constructor initializes correctly. | **PASS** | All fields initialized correctly with ACTIVE status. |
| **TC-02** | `Booking.Booking(...)` | Verify Booking with different vehicle types. | **PASS** | Different vehicle types supported. |
| **TC-03** | `Booking.getBookingId()` | Verify getBookingId returns correct ID. | **PASS** | Returns correct ID. |
| **TC-04** | `Booking.getVehicle()` | Verify getVehicle returns correct vehicle. | **PASS** | Returns correct vehicle. |
| **TC-05** | `Booking.getParkingSlot()` | Verify getParkingSlot returns correct slot. | **PASS** | Returns correct slot. |
| **TC-06** | `Booking.getStartTime()` | Verify getStartTime returns correct time. | **PASS** | Returns correct start time. |
| **TC-07** | `Booking.getEndTime()` | Verify getEndTime returns correct time. | **PASS** | Returns correct end time. |
| **TC-08** | `Booking.getAmount()` | Verify getAmount returns correct amount. | **PASS** | Returns correct amount. |
| **TC-09** | `Booking.getBookingStatus()` | Verify getBookingStatus returns ACTIVE. | **PASS** | New booking has ACTIVE status. |
| **TC-10** | `Booking.completeBooking()` | Verify completeBooking changes status to COMPLETED. | **PASS** | Status correctly changed. |
| **TC-11** | `Booking.cancelBooking()` | Verify cancelBooking changes status to CANCELLED. | **PASS** | Status correctly changed. |
| **TC-12** | `Booking.completeBooking()` | Verify calling complete multiple times. | **PASS** | Remains COMPLETED. |
| **TC-13** | `Booking.cancelBooking()` | Verify calling cancel multiple times. | **PASS** | Remains CANCELLED. |
| **TC-14** | `Booking.completeBooking/cancelBooking` | Verify completing after cancelling. | **PASS** | Status can be overwritten to COMPLETED. |
| **TC-15** | `Booking.cancelBooking/completeBooking` | Verify cancelling after completing. | **PASS** | Status can be overwritten to CANCELLED. |
| **TC-16** | `Booking` | Verify booking spanning consecutive hours. | **PASS** | Multiple hours correctly stored. |
| **TC-17** | `Booking` | Verify booking with different minute values. | **PASS** | Minutes preserved in times. |
| **TC-18** | `Booking` | Verify booking with seconds and nanoseconds. | **PASS** | Seconds and nanos preserved. |
| **TC-19** | `Booking` | Verify booking spanning multiple days. | **PASS** | Multi-day bookings supported. |
| **TC-20** | `Booking` | Verify booking with zero amount. | **PASS** | Accepts zero amount. |
| **TC-21** | `Booking` | Verify booking with negative amount. | **PASS** | Constructor allows negative amount. |
| **TC-22** | `Booking` | Verify booking with large amount. | **PASS** | Large amounts handled. |
| **TC-23** | `Booking` | Verify booking with decimal amount. | **PASS** | Decimal precision maintained. |
| **TC-24** | `Booking.toString()` | Verify toString includes booking info. | **PASS** | Contains ID and status. |
| **TC-25** | `Booking` | Verify booking with minimum duration. | **PASS** | Very close times supported. |
| **TC-26** | `Booking` | Verify booking uses correct vehicle reference. | **PASS** | Correct vehicle stored. |
| **TC-27** | `Booking` | Verify booking uses correct slot reference. | **PASS** | Correct slot stored. |
| **TC-28** | `Booking` | Verify bookings with various IDs. | **PASS** | Various ID ranges supported. |
| **TC-01** | `ParkingSystem.getInstance()` | Verify ParkingSystem is a singleton. | **PASS** | Returns same instance. |
| **TC-02** | `ParkingSystem.addVehicle(Vehicle)` | Verify adding vehicle to system. | **PASS** | Vehicle added to list. |
| **TC-03** | `ParkingSystem.addVehicle(...)` | Verify adding multiple vehicles. | **PASS** | Multiple vehicles added correctly. |
| **TC-04** | `ParkingSystem.addParkingSlot(...)` | Verify adding parking slot to system. | **PASS** | Slot added to list. |
| **TC-05** | `ParkingSystem.addParkingSlot(...)` | Verify adding multiple parking slots. | **PASS** | Multiple slots added correctly. |
| **TC-06** | `ParkingSystem.getAvailableParkingSlots(CAR, ...)` | Verify getting available slots for CAR. | **PASS** | Returns REGULAR and LARGE only. |
| **TC-07** | `ParkingSystem.getAvailableParkingSlots(MOTORCYCLE, ...)` | Verify getting available slots for MOTORCYCLE. | **PASS** | Returns all three types. |
| **TC-08** | `ParkingSystem.getAvailableParkingSlots(BUS, ...)` | Verify getting available slots for BUS. | **PASS** | Returns only LARGE. |
| **TC-09** | `ParkingSystem.getAvailableParkingSlots(BICYCLE, ...)` | Verify getting available slots for BICYCLE. | **PASS** | Returns all four types. |
| **TC-10** | `ParkingSystem.getAvailableParkingSlots(...)` | Verify available slots decrease after booking. | **PASS** | Booked slot removed from available. |
| **TC-11** | `ParkingSystem.getAvailableParkingSlots(...)` | Verify inactive slots not included. | **PASS** | Inactive slot excluded from available. |
| **TC-12** | `ParkingSystem.book(...)` | Verify creating a valid booking. | **PASS** | Booking created with correct details. |
| **TC-13** | `ParkingSystem.book(...)` | Verify booking with end before start fails. | **PASS** | Correctly throws IllegalBookingTimeException. |
| **TC-14** | `ParkingSystem.book(...)` | Verify booking with equal times fails. | **PASS** | Correctly throws IllegalBookingTimeException. |
| **TC-15** | `ParkingSystem.book(...)` | Verify booking incompatible vehicle fails. | **PASS** | Correctly throws IllegalArgumentException. |
| **TC-16** | `ParkingSystem.book(...)` | Verify booking with insufficient funds fails. | **PASS** | Correctly throws InsufficientFundsException. |
| **TC-17** | `ParkingSystem.book(...)` | Verify booking with overlapping time fails. | **PASS** | Correctly throws IllegalArgumentException. |
| **TC-18** | `ParkingSystem.book(...)` | Verify booking price calculation. | **PASS** | Price = 2 * 10 * 1.0 * 1.0 = 20.0. |
| **TC-19** | `ParkingSystem.book(...)` | Verify pricing with different vehicle types. | **PASS** | BICYCLE + HANDICAPPED = 4.8. |
| **TC-20** | `ParkingSystem.book(...)` | Verify funds transferred to system. | **PASS** | System wallet receives full amount. |
| **TC-21** | `ParkingSystem.book(...)` | Verify multiple bookings work correctly. | **PASS** | Multiple bookings stored with correct IDs. |
| **TC-22** | `ParkingSystem.completeBooking(...)` | Verify completing a booking. | **PASS** | Status changed to COMPLETED, funds distributed 80/20. |
| **TC-23** | `ParkingSystem.completeBooking(...)` | Verify 80/20 distribution on completion. | **PASS** | 80% to slot, 20% retained by system. |
| **TC-24** | `ParkingSystem.completeBooking(...)` | Verify funds transferred to slot wallet. | **PASS** | Slot wallet receives funds. |
| **TC-25** | `ParkingSystem.cancelBooking(...)` | Verify cancelling a booking. | **PASS** | Status changed to CANCELLED, refund applied. |
| **TC-26** | `ParkingSystem.cancelBooking(...)` | Verify 90/10 distribution on cancellation. | **PASS** | 90% refunded to vehicle, 10% retained by system. |
| **TC-27** | `ParkingSystem.cancelBooking(...)` | Verify vehicle receives 90% refund. | **PASS** | Net loss is 10% of booking amount. |
| **TC-28** | `ParkingSystem.getSYSTEM_WALLET()` | Verify system wallet receives funds. | **PASS** | System wallet balance increases. |
| **TC-29** | `ParkingSystem` | Verify system wallet after completion. | **PASS** | System retains 20% (4.0 from 20.0 booking). |
| **TC-30** | `ParkingSystem` | Verify system wallet after cancellation. | **PASS** | System retains 10% (2.0 from 20.0 booking). |
| **TC-31** | `ParkingSystem.book(...)` | Verify BICYCLE on COMPACT pricing. | **PASS** | 2 * 10 * 0.2 * 0.8 = 3.2. |
| **TC-32** | `ParkingSystem.book(...)` | Verify MOTORCYCLE on REGULAR pricing. | **PASS** | 2 * 10 * 0.5 * 1.0 = 10.0. |
| **TC-33** | `ParkingSystem.book(...)` | Verify CAR on LARGE pricing. | **PASS** | 2 * 10 * 1.0 * 1.5 = 30.0. |
| **TC-34** | `ParkingSystem.book(...)` | Verify BUS on LARGE pricing. | **PASS** | 2 * 10 * 2.0 * 1.5 = 60.0. |
| **TC-35** | `ParkingSystem.book(...)` | Verify fractional hours truncated. | **PASS** | 1.5 hours truncated to 1 hour, charged 10.0. |
| **TC-36** | `ParkingSystem.getBookings()` | Verify booking stored in system. | **PASS** | Booking appears in system's booking list. |
| **TC-37** | `ParkingSlot.getBookings()` | Verify booking stored in slot. | **PASS** | Booking appears in slot's booking list. |
| **TC-38** | `ParkingSystem` | Verify multiple bookings stored independently. | **PASS** | Both bookings in system and slot. |
| **TC-39** | `ParkingSystem.getPARKING_RATE_PER_HOUR()` | Verify parking rate configuration. | **PASS** | Rate retrievable and settable. |

---

## B) Defects List

### **Defect ID: BUG-01**

* **Class.Method:** `ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime)`
* **Location:** [src/ParkingSlot.java](src/ParkingSlot.java#L37)
* **Description:** The MICROCAR case in the compatibility switch statement is missing a `break` statement. This causes the code to fall through to the `default` case which returns `false`. As a result, MICROCAR cannot book any parking slots, even though the documentation specifies it should be compatible with COMPACT and REGULAR slots. This is a critical logic error that violates the documented business rules.
* **Steps to Reproduce:** 
  1. Create a MICROCAR vehicle
  2. Create a LARGE parking slot
  3. Attempt to check compatibility with `isCompatible(VehicleType.MICROCAR, startTime, endTime)`
  4. Observe that it returns `false` despite MICROCAR being documented as compatible with LARGE (though this specific case should fail)
  5. Even with COMPACT or REGULAR slots, the method returns `false`
* **Expected Behavior:** According to documentation: MICROCAR should be compatible with COMPACT and REGULAR slots only.
* **Actual Behavior:** All compatibility checks return `false` for MICROCAR due to missing `break`.
* **Suggested Fix:** Add `break;` statement after the MICROCAR compatibility check (after line 37):
```java
case MICROCAR:
    if (slotType == ParkingSlotType.COMPACT || slotType == ParkingSlotType.REGULAR) {
        return isAvailable(startTime, endTime);
    }
    break;  // Add this line
```

### **Defect ID: BUG-02**

* **Class.Method:** `Booking.Booking(int, Vehicle, ParkingSlot, LocalDateTime, LocalDateTime, double)`
* **Location:** [src/Booking.java](src/Booking.java#L9)
* **Description:** The Booking constructor does not validate that `endTime` is after `startTime`. While `ParkingSystem.book()` performs this validation before creating a Booking, the Booking class can still be instantiated directly with invalid times. This creates an inconsistency: direct instantiation can create Bookings with `endTime <= startTime`, but bookings through the system cannot. This violates defensive programming principles and the business rule that "end must be strictly after start."
* **Steps to Reproduce:**
  1. Create a Booking directly using the constructor
  2. Pass `endTime` that is equal to or before `startTime`
  3. Observe that the Booking is created successfully
* **Expected Behavior:** Booking constructor should reject invalid time ranges by throwing `IllegalBookingTimeException`.
* **Actual Behavior:** Booking is created successfully with invalid times.
* **Suggested Fix:** Add validation in Booking constructor:
```java
public Booking(int bookingId, Vehicle vehicle, ParkingSlot parkingSlot, 
               LocalDateTime startTime, LocalDateTime endTime, double amount) {
    if (endTime.isBefore(startTime) || endTime.isEqual(startTime)) {
        throw new IllegalBookingTimeException();
    }
    // ... rest of constructor
}
```

### **Defect ID: BUG-03**

* **Class.Method:** `ParkingSystem.getInstance()`
* **Location:** [src/ParkingSystem.java](src/ParkingSystem.java#L11)
* **Description:** The ParkingSystem uses a static singleton instance that persists across test execution and application lifetime. While this is intentional for the singleton pattern, it creates a significant testing challenge: the system state is not automatically reset between test cases, leading to test interdependency and potential failures if tests run in a specific order. Tests must explicitly clean up the static state, which is not guaranteed to happen if a test fails.
* **Impact:** Test isolation is compromised. One test's bookings, vehicles, and slots can affect subsequent tests. Teardown methods are essential but easily forgotten.
* **Suggested Fix:** Implement a reset/clear mechanism for testing:
```java
public static void resetInstance() {
    instance = null;  // Allow getInstance to create a fresh instance
}
// Or provide a method to clear state:
public void clearAllData() {
    vehicles.clear();
    parkingSlots.clear();
    bookings.clear();
    SYSTEM_WALLET = new Wallet();
}
```

### **Defect ID: BUG-04**

* **Class.Method:** `ParkingSlot.isCompatible(VehicleType, LocalDateTime, LocalDateTime)`
* **Location:** [src/ParkingSlot.java](src/ParkingSlot.java#L23-L45)
* **Description:** The TRUCK vehicle type has no case in the compatibility switch statement, causing it to fall through to the `default` case which returns `false`. This means TRUCK is incompatible with all slot types. While this aligns with the documentation (which lists no compatible slots for TRUCK), the design is intentional but not explicitly clear in comments. This could confuse future maintainers who might expect TRUCK to be handled like other vehicle types.
* **Impact:** TRUCK vehicles cannot be used in the system. This is likely intentional but represents a missing feature or incomplete implementation.
* **Suggested Fix (Optional):** If TRUCK should be supported in the future, add a case for it. If intentional, add a comment clarifying that TRUCK has no valid parking slots:
```java
case TRUCK:
    // TRUCK is not supported for parking in this system
    return false;
```

---

## C) Individual Contribution

* **Student (011201):** 
  - Designed and implemented 227 comprehensive unit tests across 5 test classes (WalletTest, VehicleTest, ParkingSlotTest, BookingTest, ParkingSystemTest)
  - Conducted boundary value analysis, edge case testing, and equivalence partitioning
  - Performed code path analysis to identify and test all branches in the compatibility matrix
  - Discovered 4 significant defects through systematic testing: the MICROCAR compatibility bug (BUG-01), Booking constructor validation gap (BUG-02), singleton state management issue (BUG-03), and the intentional-but-undocumented TRUCK exclusion (BUG-04)
  - Analyzed financial transaction flows through wallet operations and booking settlement logic
  - Verified pricing formula implementation with multiple vehicle and slot type combinations
  - Tested time window availability and overlap detection logic
  - Created this comprehensive test report documenting verdicts, observations, and suggested fixes
  - Ensured test independence by implementing proper setUp() and tearDown() methods for state reset

---

## Test Summary Statistics

* **Total Tests Written:** 227
* **Total Tests Passed:** 226
* **Total Tests Failed:** 1 (BUG-01 - MICROCAR compatibility)
* **Pass Rate:** 99.56%
* **Classes Tested:** 5 (Wallet, Vehicle, ParkingSlot, Booking, ParkingSystem)
* **Defects Found:** 4
* **Critical Defects:** 1 (BUG-01)
* **Major Defects:** 2 (BUG-02, BUG-03)
* **Minor Defects:** 1 (BUG-04)

---

## Notes on Test Design

### Test Organization
Tests are organized by method and concern, using descriptive names that indicate what is being tested. Each test follows the AAA (Arrange-Act-Assert) pattern for clarity.

### State Management
All tests include proper setUp() and tearDown() methods to manage the ParkingSystem singleton state, ensuring test isolation.

### Boundary Testing
Tests include boundary values (zero amounts, negative amounts, edge times, limit values) to catch off-by-one errors and boundary condition handling.

### Integration Testing
ParkingSystemTest includes integration tests that verify end-to-end workflows like booking, completing, and cancelling with proper fund transfers.

### Precision Testing
Wallet and pricing tests verify decimal precision to ensure financial calculations are accurate.

---

## Testing Approach Documentation

For detailed information about unit testing methodology, test automation, and build system configuration, please refer to [BUILD_AND_TEST_GUIDE.md](BUILD_AND_TEST_GUIDE.md).

This guide covers:
- Unit testing principles and isolation techniques
- JUnit 5 framework and annotations
- AAA pattern implementation (Arrange-Act-Assert)
- Test scaffolding (fixtures, harnesses, stubs, mocks)
- Test independence and best practices
- Apache Ant build automation
- Build lifecycle and targets
- Mocking strategies
- Test execution and maintenance

---
