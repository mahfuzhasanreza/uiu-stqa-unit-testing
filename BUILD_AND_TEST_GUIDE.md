# Unit Testing & Build Automation Guide

## Overview

This guide demonstrates comprehensive unit testing and build automation for the Parking System project, aligned with software testing course concepts.

---

## 1. Unit Testing Concepts Demonstrated

### 1.1 Unit Testing Principles

**Definition**: A unit is the smallest testable component (usually a class or method).

**Implementation in this project:**
- **Wallet**: Tested in isolation with mock objects for dependencies
- **Vehicle**: Tested with Wallet as dependency (stub/mock)
- **ParkingSlot**: Tested with time windows (stubs) and compatibility logic
- **Booking**: Tested with Vehicle and ParkingSlot (stubs)
- **ParkingSystem**: Integrated tests with complete test harness

**Isolation Techniques:**
```java
@BeforeEach
public void setUp() {
    // Create isolated test environment
    wallet = new Wallet();
    targetWallet = new Wallet();
    // Each test gets fresh instances - no shared state
}

@AfterEach
public void tearDown() {
    // Clean up - ensures test independence
    wallet = null;
    targetWallet = null;
}
```

---

## 2. Test Automation Framework

### 2.1 JUnit 5 Annotations

| Annotation | Purpose | Scope |
|-----------|---------|-------|
| `@Test` | Marks method as test case | Method level |
| `@BeforeEach` | Runs before each test | Test scaffold setup |
| `@AfterEach` | Runs after each test | Test cleanup |
| `@BeforeAll` | Runs once before all tests | Class initialization |
| `@AfterAll` | Runs once after all tests | Class cleanup |
| `@DisplayName` | Human-readable test name | Documentation |

### 2.2 Assertions Used

**Equality Testing:**
```java
assertEquals(expected, actual, "Message");
assertEquals(100.0, wallet.getBalance(), 0.01); // With delta for floats
```

**Null/Reference Testing:**
```java
assertNotNull(wallet);
assertNull(nullableObject);
assertSame(obj1, obj2);
assertNotSame(wallet1, wallet2);
```

**Boolean Testing:**
```java
assertTrue(slot.isActive());
assertFalse(slot.isAvailable(overlappingTime));
```

**Exception Testing:**
```java
assertThrows(InvalidAmountException.class, () -> wallet.addFunds(0.0));
```

**Array/Collection Testing:**
```java
assertEquals(2, availableSlots.size());
assertTrue(availableSlots.contains(regularSlot));
```

### 2.3 Test Scaffolding Components

#### Fixtures (Test Data)
```java
@BeforeEach
public void setUp() {
    // Fixtures: reusable test data
    wallet = new Wallet(1000.0);
    vehicle = new Vehicle(1, VehicleType.CAR, 500.0);
}
```

#### Test Harness
```java
// Complete system setup for integration tests
parkingSystem = ParkingSystem.getInstance();
parkingSystem.addVehicle(car);
parkingSystem.addParkingSlot(regularSlot);
// System ready for testing
```

#### Stubs (Simplified Dependencies)
```java
// LocalDateTime acts as stub for time system
LocalDateTime startTime = LocalDateTime.of(2024, 6, 1, 10, 0);
// Methods can use this without real clock
```

#### Mock Objects
```java
// Vehicle's wallet acts as mock for financial system
Vehicle vehicle = new Vehicle(1, VehicleType.CAR, 1000.0);
// Wallet is fully functional, not a true mock, but isolates testing
```

---

## 3. AAA Pattern Implementation

All tests follow the **Arrange-Act-Assert** pattern:

### Example: testAddFundsWithPositiveAmount

```java
@Test
public void testAddFundsWithPositiveAmount() {
    // ARRANGE: Set up initial state
    wallet = new Wallet(100.0);
    double amountToAdd = 50.0;
    
    // ACT: Execute the unit being tested
    wallet.addFunds(amountToAdd);
    
    // ASSERT: Verify results
    assertEquals(150.0, wallet.getBalance(), 
            "Balance should increase by added amount");
}
```

### Example: Exception Testing

```java
@Test
public void testDeductFundsMoreThanBalanceThrowsException() {
    // ARRANGE
    wallet = new Wallet(50.0);
    
    // ACT & ASSERT: In same block for exception testing
    assertThrows(InsufficientFundsException.class, 
            () -> wallet.deductFunds(75.0),
            "Deducting more than balance should throw exception");
}
```

---

## 4. Test Independence & Best Practices

### 4.1 Test Independence Rules

❌ **BAD: Dependent Tests**
```java
@Test
public void testA() { wallet.addFunds(100.0); }
@Test
public void testB() { 
    // FAILS if testA doesn't run first!
    assertEquals(100.0, wallet.getBalance()); 
}
```

✅ **GOOD: Independent Tests**
```java
@Test
public void testAddFunds() {
    wallet = new Wallet(0.0);        // Fresh setup
    wallet.addFunds(100.0);
    assertEquals(100.0, wallet.getBalance());
}

@Test
public void testDeductFunds() {
    wallet = new Wallet(100.0);      // Independent setup
    wallet.deductFunds(50.0);
    assertEquals(50.0, wallet.getBalance());
}
```

### 4.2 Deterministic & Repeatable Tests

✅ **GOOD: Deterministic**
```java
@Test
public void testPricingCalculation() {
    // Always produces same result
    double price = 2 * 10 * 1.0 * 1.0;  // = 20.0
    assertEquals(20.0, booking.getAmount());
}
```

❌ **BAD: Non-deterministic**
```java
@Test
public void testRandomWalletBalance() {
    wallet = new Wallet(Math.random() * 1000);
    assertTrue(wallet.getBalance() > 0);  // Unreliable!
}
```

### 4.3 Best Practices Checklist

✓ **One scenario per test**: testAddFunds tests only additions  
✓ **Descriptive names**: testAddFundsWithPositiveAmount (not testAddFunds1)  
✓ **Assertions only**: No System.out.println()  
✓ **Boundary testing**: Zero, negative, large values  
✓ **Exception testing**: All error paths covered  
✓ **Independent fixtures**: @BeforeEach creates fresh data  
✓ **Resource cleanup**: @AfterEach releases resources  

---

## 5. Build Automation with Apache Ant

### 5.1 Build Lifecycle

The standard build lifecycle automates all development phases:

```
Validate → Compile → Test → Package → Deploy
```

**In `build.xml`:**
- **validate**: Check project structure
- **compile**: Compile source code
- **compile-tests**: Compile test classes
- **test**: Run all JUnit tests
- **package**: Create JAR distribution
- **deploy**: (Ready for deployment)

### 5.2 Ant Build Script Structure

```xml
<!-- Properties: Configuration values -->
<property name="src.dir" value="src"/>
<property name="test.dir" value="test"/>

<!-- Paths: Classpath management -->
<path id="compile.classpath">
    <fileset dir="..." includes="*.jar"/>
</path>

<!-- Targets: Build steps -->
<target name="compile" depends="init">
    <javac srcdir="${src.dir}" destdir="${build.dir}"/>
</target>

<target name="test" depends="compile-tests">
    <java jar="${junit.console.jar}" fork="true">
        <!-- Run JUnit tests -->
    </java>
</target>
```

### 5.3 Running Ant Targets

```bash
# Validate project structure
ant validate

# Compile source code only
ant compile

# Run all tests (DEFAULT)
ant test

# Run specific test class
ant test-single -DtestClass=WalletTest

# Complete build: compile + package
ant build

# Clean rebuild
ant rebuild

# Display all available targets
ant help
```

---

## 6. Mocking Strategies

### 6.1 Mock vs Real Objects

**Real Objects (used in this project):**
```java
// Use real Wallet for testing Vehicle
Vehicle vehicle = new Vehicle(1, VehicleType.CAR, new Wallet(1000.0));
// Wallet is fully functional, not a mock
```

**When Mocking Would Help:**
```java
// If we needed to mock Wallet behavior:
// Wallet mockWallet = mock(Wallet.class);
// when(mockWallet.getBalance()).thenReturn(1000.0);
// 
// This would replace real wallet with controlled mock
// Useful for:
// - Unavailable components
// - Unfinished implementations
// - Hard-to-control behaviors (network, database)
```

### 6.2 Stub Objects

```java
// LocalDateTime stubs simulate time without real clock
LocalDateTime startTime = LocalDateTime.of(2024, 6, 1, 10, 0);
LocalDateTime endTime = LocalDateTime.of(2024, 6, 1, 12, 0);

// Allows testing time-based logic deterministically
@Test
public void testOverlapDetection() {
    booking = new Booking(..., startTime, endTime, 100.0);
    // Test availability with fixed time - repeatable
    assertFalse(slot.isAvailable(startTime, endTime));
}
```

---

## 7. Test Execution & Results

### 7.1 Running Tests from IDE

**Using Ant:**
```bash
ant test
```

**Using JUnit directly:**
```bash
java org.junit.platform.console.ConsoleLauncher \
    --scan-classpath test-classes/
```

### 7.2 Test Reports

Test reports are generated in `build/test-reports/`:
- Contains detailed results for each test
- Lists failures and exceptions
- Provides execution statistics

### 7.3 Interpreting Results

```
✓ PASS: assertEquals verified correctly
✗ FAIL: Assertion failed - expected != actual
✗ ERROR: Unexpected exception thrown
SKIPPED: Test skipped with @Disabled or other conditions
```

---

## 8. Comprehensive Test Coverage

### 8.1 Coverage by Class

| Class | Tests | Focus |
|-------|-------|-------|
| Wallet | 26 | Constructor, add/deduct/transfer, exceptions |
| Vehicle | 20 | Constructor, wallet integration, independence |
| ParkingSlot | 35 | Constructor, state, compatibility matrix, availability |
| Booking | 28 | Constructor, status transitions, time handling |
| ParkingSystem | 118 | Booking workflow, pricing, financial transactions |

### 8.2 Path Coverage

**Normal Paths** (Happy Path)
```java
testAddFundsWithPositiveAmount()     // Success case
testTransferFundsWithValidAmount()   // Success case
```

**Error Paths** (Exception Cases)
```java
testAddFundsWithZeroThrowsException()          // Error handling
testDeductFundsMoreThanBalanceThrowsException() // Boundary condition
```

**Boundary Paths**
```java
testWalletConstructorWithZeroBalance()        // Boundary: 0
testWalletConstructorWithNegativeBalance()    // Boundary: negative
testWalletConstructorWithLargeBalance()       // Boundary: large
```

---

## 9. Test Maintenance

### 9.1 Adding New Tests

1. **Identify scenario** to test
2. **Create test method** with `@Test` annotation
3. **Implement AAA pattern**:
   - Arrange: Setup using @BeforeEach data
   - Act: Call the unit
   - Assert: Verify using JUnit assertions
4. **Use descriptive name**: testMethodName_Scenario_ExpectedResult()
5. **Run test**: `ant test`

### 9.2 Debugging Failed Tests

```java
// Use detailed assertion messages
assertEquals(expected, actual, 
    "Detailed message explaining what went wrong");

// Add intermediate assertions for step-by-step verification
assertTrue(step1Pass, "Step 1 should succeed");
assertTrue(step2Pass, "Step 2 should succeed");
```

---

## 10. Summary

This project demonstrates enterprise-level testing practices:

✓ **227 Unit Tests** covering all classes  
✓ **Test Automation** via JUnit 5 and Apache Ant  
✓ **Test Scaffolding** with fixtures, harnesses, stubs  
✓ **AAA Pattern** in all test methods  
✓ **Best Practices** including test independence, boundary testing  
✓ **Build Lifecycle** automation for compile → test → package  
✓ **Comprehensive Assertions** for state verification  
✓ **Exception Testing** for error conditions  

---

## Quick Reference: Running Tests

```bash
# Default: Run all tests
ant test

# Run specific test class
ant test-single -DtestClass=WalletTest

# Compile without testing
ant compile

# Full build with packaging
ant build

# Clean and rebuild
ant rebuild

# View all targets
ant help
```

---
