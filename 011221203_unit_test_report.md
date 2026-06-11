# Unit Testing Report: Parking System Management

## 0) Team Members

* **011221203** - Mahfuz Hasan Reza

---

## A) Test Case List

| Test ID | Class.Method Under Test | Why this test? | Verdict | Comments/Observations |
| --- | --- | --- | --- | --- |
| **1** | `Wallet.Wallet()` | Verify default constructor initializes balance to 0. | **PASS** | Default wallet correctly has 0 balance. |
| **2** | `Wallet.Wallet(double)` | Verify constructor with positive initial balance sets correctly. | **PASS** | Constructor correctly sets provided balance. |
| **3** | `Wallet.Wallet(double)` | Verify constructor accepts negative balance without validation. | **PASS** | Constructor allows negative balance (no validation). |
| **4** | `Wallet.getBalance()` | Verify getBalance returns correct initial balance. | **PASS** | Returns correct initial balance value. |
| **5** | `Wallet.addFunds(double)` | Verify adding positive amount increases balance. | **PASS** | Balance correctly increased by added amount. |
| **6** | `Wallet.addFunds(double)` | Verify multiple addFunds operations accumulate correctly. | **PASS** | Multiple additions correctly accumulate. |
| **7** | `Wallet.addFunds(double)` | Verify adding very small positive amount works. | **PASS** | Handles small decimal amounts precisely. |
| **8** | `Wallet.addFunds(double)` | Verify adding zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **9** | `Wallet.addFunds(double)` | Verify adding negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **10** | `Wallet.deductFunds(double)` | Verify deducting valid amount decreases balance. | **PASS** | Balance correctly decreased by deducted amount. |
| **11** | `Wallet.deductFunds(double)` | Verify deducting exact balance leaves 0. | **PASS** | Balance correctly becomes 0. |
| **12** | `Wallet.deductFunds(double)` | Verify multiple deduct operations work correctly. | **PASS** | Multiple deductions correctly applied. |
| **13** | `Wallet.deductFunds(double)` | Verify deducting more than balance throws InsufficientFundsException. | **PASS** | Correctly throws InsufficientFundsException. |
| **14** | `Wallet.deductFunds(double)` | Verify deducting zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **15** | `Wallet.deductFunds(double)` | Verify deducting negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **16** | `Wallet.deductFunds(double)` | Verify deducting from empty wallet throws InsufficientFundsException. | **PASS** | Correctly throws InsufficientFundsException. |
| **17** | `Wallet.deductFunds(double)` | Verify deducting small decimal amount works. | **PASS** | Handles small decimal deductions. |
| **18** | `Wallet.transferFunds(Wallet, double)` | Verify transferring valid amount between wallets. | **PASS** | Both wallets correctly updated. |
| **19** | `Wallet.transferFunds(Wallet, double)` | Verify transferring entire balance works. | **PASS** | Source empties, target receives full amount. |
| **20** | `Wallet.transferFunds(Wallet, double)` | Verify transferring more than balance throws exception. | **PASS** | Correctly throws InsufficientFundsException. |
| **21** | `Wallet.transferFunds(Wallet, double)` | Verify transferring zero throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **22** | `Wallet.transferFunds(Wallet, double)` | Verify transferring negative amount throws InvalidAmountException. | **PASS** | Correctly throws InvalidAmountException. |
| **23** | `Wallet.transferFunds(Wallet, double)` | Verify transferring from empty wallet throws exception. | **PASS** | Correctly throws InsufficientFundsException. |
| **24** | `Wallet.transferFunds(Wallet, double)` | Verify multiple consecutive transfers work correctly. | **PASS** | All transfers applied correctly. |
| **25** | `Wallet.transferFunds(Wallet, double)` | Verify transferring to same wallet (edge case). | **PASS** | Balance maintained after self-transfer. |
| **26** | `Wallet.transferFunds(Wallet, double)` | Verify transferring small decimal amount works. | **PASS** | Precision maintained for small amounts. |
| **27** | `Vehicle.Vehicle(int, VehicleType, Wallet)` | Verify Vehicle constructor with Wallet parameter. | **PASS** | Vehicle correctly initialized with provided wallet. |
| **28** | `Vehicle.Vehicle(int, VehicleType, double)` | Verify Vehicle constructor with initial balance parameter. | **PASS** | New wallet created with correct balance. |
| **29** | `Vehicle.Vehicle(int, VehicleType, double)` | Verify Vehicle can be created with zero balance. | **PASS** | Vehicle correctly created with 0 balance. |
| **30** | `Vehicle.Vehicle(...)` | Verify Vehicle can be created with all VehicleTypes. | **PASS** | All vehicle types correctly supported. |
| **31** | `Vehicle.getVehicleId()` | Verify getVehicleId returns correct ID. | **PASS** | Returns correct vehicle ID. |
| **32** | `Vehicle.getVehicleType()` | Verify getVehicleType returns correct type. | **PASS** | Returns correct vehicle type. |
| **33** | `Vehicle.getWallet()` | Verify getWallet returns the wallet object. | **PASS** | Returns same wallet object reference. |
| **34** | `Vehicle.getBalance()` | Verify getBalance returns correct wallet balance. | **PASS** | Returns wallet balance correctly. |
| **35** | `Vehicle.getBalance()` | Verify getBalance reflects wallet changes. | **PASS** | Reflects changes to underlying wallet. |
| **36** | `Vehicle.getWallet().addFunds()` | Verify vehicle wallet can receive funds. | **PASS** | Funds correctly added to vehicle wallet. |
| **37** | `Vehicle.getWallet().deductFunds()` | Verify vehicle wallet can lose funds. | **PASS** | Funds correctly deducted from vehicle wallet. |
| **38** | `Vehicle.getWallet().deductFunds()` | Verify deducting more than balance fails. | **PASS** | Correctly throws InsufficientFundsException. |
| **39** | `Vehicle.getWallet().transferFunds()` | Verify vehicle can transfer funds to another wallet. | **PASS** | Transfer correctly executed. |
| **40** | `Vehicle` | Verify two vehicles with same ID but different wallets are different. | **PASS** | Different object instances despite same ID. |
| **41** | `Vehicle` | Verify multiple vehicles have independent wallets. | **PASS** | Changes to one don't affect others. |
| **42** | `Vehicle.toString()` | Verify toString includes vehicle information. | **PASS** | Contains ID, type, and balance. |
| **43** | `Vehicle` | Verify vehicle can be created with negative balance. | **PASS** | Constructor allows negative balance. |
| **44** | `Vehicle` | Verify vehicle handles large balance amounts. | **PASS** | Correctly handles large numbers. |
| **45** | `Vehicle` | Verify vehicle handles various ID ranges. | **PASS** | Accepts positive, negative, and large IDs. |
| **46** | `Vehicle` | Verify multiple vehicles can reference same wallet. | **PASS** | Shared wallet affects all vehicles. |
| **47** | `ParkingSlot.ParkingSlot(String, ParkingSlotType)` | Verify ParkingSlot constructor initializes correctly. | **PASS** | All fields initialized with correct values. |
| **48** | `ParkingSlot.getSlotId()` | Verify getSlotId returns correct ID. | **PASS** | Returns correct slot ID. |
| **49** | `ParkingSlot.getSlotType()` | Verify getSlotType returns correct type. | **PASS** | Returns correct slot type. |
| **50** | `ParkingSlot.getWallet()` | Verify getWallet returns wallet object. | **PASS** | Returns non-null wallet. |
| **51** | `ParkingSlot.getBookings()` | Verify getBookings returns empty list initially. | **PASS** | Empty list initially. |
| **52** | `ParkingSlot.getBalance()` | Verify getBalance returns wallet balance. | **PASS** | Returns 0 initially. |
| **53** | `ParkingSlot.isActive()` | Verify isActive returns true by default. | **PASS** | Slot active by default. |
| **54** | `ParkingSlot.activate()` | Verify activating a deactivated slot. | **PASS** | Slot becomes active. |
| **55** | `ParkingSlot.deactivate()` | Verify deactivating a slot. | **PASS** | Slot becomes inactive. |
| **56** | `ParkingSlot.activate/deactivate()` | Verify multiple activation/deactivation cycles. | **PASS** | Status correctly toggled multiple times. |
| **57** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with COMPACT. | **PASS** | Correctly compatible. |
| **58** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with REGULAR. | **PASS** | Correctly compatible. |
| **59** | `ParkingSlot.isCompatible(MOTORCYCLE, ...)` | Verify MOTORCYCLE compatible with LARGE. | **PASS** | Correctly compatible. |
| **60** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR compatible with REGULAR. | **PASS** | Correctly compatible. |
| **61** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR compatible with LARGE. | **PASS** | Correctly compatible. |
| **62** | `ParkingSlot.isCompatible(CAR, ...)` | Verify CAR NOT compatible with COMPACT. | **PASS** | Correctly rejects COMPACT. |
| **63** | `ParkingSlot.isCompatible(BUS, ...)` | Verify BUS compatible with LARGE. | **PASS** | Correctly compatible. |
| **64** | `ParkingSlot.isCompatible(BUS, ...)` | Verify BUS NOT compatible with COMPACT. | **PASS** | Correctly rejects COMPACT. |
| **65** | `ParkingSlot.isCompatible(BICYCLE, ...)` | Verify BICYCLE compatible with all slot types. | **PASS** | Compatible with all four types. |
| **66** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR compatible with COMPACT. | **PASS** | Correctly compatible. |
| **67** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR compatible with REGULAR. | **PASS** | Correctly compatible. |
| **68** | `ParkingSlot.isCompatible(MICROCAR, ...)` | Verify MICROCAR NOT compatible with LARGE. | **PASS** | Correctly rejects LARGE slot for MICROCAR. |
| **69** | `ParkingSlot.isCompatible(TRUCK, ...)` | Verify TRUCK not compatible with any slot. | **PASS** | TRUCK correctly has no compatible slots (as per doc). |
| **70** | `ParkingSlot.isCompatible(...)` | Verify inactive slot not compatible. | **PASS** | Inactive slot correctly returns false. |
| **71** | `ParkingSlot.isAvailable(...)` | Verify empty slot is available. | **PASS** | Empty slot correctly available. |
| **72** | `ParkingSlot.isAvailable(...)` | Verify overlapping booking makes slot unavailable. | **PASS** | Overlapping booking correctly blocks. |
| **73** | `ParkingSlot.isAvailable(...)` | Verify booking ending at request start allows slot. | **PASS** | No overlap when end equals start. |
| **74** | `ParkingSlot.isAvailable(...)` | Verify booking starting at request end allows slot. | **PASS** | No overlap when start equals end. |
| **75** | `ParkingSlot.isAvailable(...)` | Verify multiple non-overlapping bookings. | **PASS** | Gap between bookings correctly available. |
| **76** | `ParkingSlot.isAvailable(...)` | Verify partial overlap at start blocks. | **PASS** | Correctly identifies overlap. |
| **77** | `ParkingSlot.isAvailable(...)` | Verify partial overlap at end blocks. | **PASS** | Correctly identifies overlap. |
| **78** | `ParkingSlot.getWallet().addFunds()` | Verify slot wallet can receive funds. | **PASS** | Funds correctly added. |
| **79** | `ParkingSlot.getWallet()` | Verify slot wallet handles transfers. | **PASS** | Transfers correctly applied. |
| **80** | `ParkingSlot` | Verify slots created with all types. | **PASS** | All slot types supported. |
| **81** | `ParkingSlot` | Verify slot ID with special characters. | **PASS** | Special characters accepted in ID. |
| **82** | `Booking.Booking(...)` | Verify Booking constructor initializes correctly. | **PASS** | All fields initialized correctly with ACTIVE status. |
| **83** | `Booking.Booking(...)` | Verify Booking with different vehicle types. | **PASS** | Different vehicle types supported. |
| **84** | `Booking.getBookingId()` | Verify getBookingId returns correct ID. | **PASS** | Returns correct ID. |
| **85** | `Booking.getVehicle()` | Verify getVehicle returns correct vehicle. | **PASS** | Returns correct vehicle. |
| **86** | `Booking.getParkingSlot()` | Verify getParkingSlot returns correct slot. | **PASS** | Returns correct slot. |
| **87** | `Booking.getStartTime()` | Verify getStartTime returns correct time. | **PASS** | Returns correct start time. |
| **88** | `Booking.getEndTime()` | Verify getEndTime returns correct time. | **PASS** | Returns correct end time. |
| **89** | `Booking.getAmount()` | Verify getAmount returns correct amount. | **PASS** | Returns correct amount. |
| **90** | `Booking.getBookingStatus()` | Verify getBookingStatus returns ACTIVE. | **PASS** | New booking has ACTIVE status. |
| **91** | `Booking.completeBooking()` | Verify completeBooking changes status to COMPLETED. | **PASS** | Status correctly changed. |
| **92** | `Booking.cancelBooking()` | Verify cancelBooking changes status to CANCELLED. | **PASS** | Status correctly changed. |
| **93** | `Booking.completeBooking()` | Verify calling complete multiple times. | **PASS** | Remains COMPLETED. |
| **94** | `Booking.cancelBooking()` | Verify calling cancel multiple times. | **PASS** | Remains CANCELLED. |
| **95** | `Booking.completeBooking/cancelBooking` | Verify completing after cancelling. | **PASS** | Status can be overwritten to COMPLETED. |
| **96** | `Booking.cancelBooking/completeBooking` | Verify cancelling after completing. | **PASS** | Status can be overwritten to CANCELLED. |
| **97** | `Booking` | Verify booking spanning consecutive hours. | **PASS** | Multiple hours correctly stored. |
| **98** | `Booking` | Verify booking with different minute values. | **PASS** | Minutes preserved in times. |
| **99** | `Booking` | Verify booking with seconds and nanoseconds. | **PASS** | Seconds and nanos preserved. |
| **100** | `Booking` | Verify booking spanning multiple days. | **PASS** | Multi-day bookings supported. |
| **101** | `Booking` | Verify booking with zero amount. | **PASS** | Accepts zero amount. |
| **102** | `Booking` | Verify booking with negative amount. | **PASS** | Constructor allows negative amount. |
| **103** | `Booking` | Verify booking with large amount. | **PASS** | Large amounts handled. |
| **104** | `Booking` | Verify booking with decimal amount. | **PASS** | Decimal precision maintained. |
| **105** | `Booking.toString()` | Verify toString includes booking info. | **PASS** | Contains ID and status. |
| **106** | `Booking` | Verify booking with minimum duration. | **PASS** | Very close times supported. |
| **107** | `Booking` | Verify booking uses correct vehicle reference. | **PASS** | Correct vehicle stored. |
| **108** | `Booking` | Verify booking uses correct slot reference. | **PASS** | Correct slot stored. |
| **109** | `Booking` | Verify bookings with various IDs. | **PASS** | Various ID ranges supported. |
| **110** | `ParkingSystem.getInstance()` | Verify ParkingSystem is a singleton. | **PASS** | Returns same instance. |
| **111** | `ParkingSystem.addVehicle(Vehicle)` | Verify adding vehicle to system. | **PASS** | Vehicle added to list. |
| **112** | `ParkingSystem.addVehicle(...)` | Verify adding multiple vehicles. | **PASS** | Multiple vehicles added correctly. |
| **113** | `ParkingSystem.addParkingSlot(...)` | Verify adding parking slot to system. | **PASS** | Slot added to list. |
| **114** | `ParkingSystem.addParkingSlot(...)` | Verify adding multiple parking slots. | **PASS** | Multiple slots added correctly. |
| **115** | `ParkingSystem.getAvailableParkingSlots(CAR, ...)` | Verify getting available slots for CAR. | **PASS** | Returns REGULAR and LARGE only. |
| **116** | `ParkingSystem.getAvailableParkingSlots(MOTORCYCLE, ...)` | Verify getting available slots for MOTORCYCLE. | **PASS** | Returns all three types. |
| **117** | `ParkingSystem.getAvailableParkingSlots(BUS, ...)` | Verify getting available slots for BUS. | **PASS** | Returns only LARGE. |
| **118** | `ParkingSystem.getAvailableParkingSlots(BICYCLE, ...)` | Verify getting available slots for BICYCLE. | **PASS** | Returns all four types. |
| **119** | `ParkingSystem.getAvailableParkingSlots(...)` | Verify available slots decrease after booking. | **PASS** | Booked slot removed from available. |
| **120** | `ParkingSystem.getAvailableParkingSlots(...)` | Verify inactive slots not included. | **PASS** | Inactive slot excluded from available. |
| **121** | `ParkingSystem.book(...)` | Verify creating a valid booking. | **PASS** | Booking created with correct details. |
| **122** | `ParkingSystem.book(...)` | Verify booking with end before start fails. | **PASS** | Correctly throws IllegalBookingTimeException. |
| **123** | `ParkingSystem.book(...)` | Verify booking with equal times fails. | **PASS** | Correctly throws IllegalBookingTimeException. |
| **124** | `ParkingSystem.book(...)` | Verify booking incompatible vehicle fails. | **PASS** | Correctly throws IllegalArgumentException. |
| **125** | `ParkingSystem.book(...)` | Verify booking with insufficient funds fails. | **PASS** | Correctly throws InsufficientFundsException. |
| **126** | `ParkingSystem.book(...)` | Verify booking with overlapping time fails. | **PASS** | Correctly throws IllegalArgumentException. |
| **127** | `ParkingSystem.book(...)` | Verify booking price calculation. | **FAIL** | **PRICING BUG**: Expected 20.0, got 30.0. Price calculation appears to be 3 hours instead of 2, or multiplier applied incorrectly. |
| **128** | `ParkingSystem.book(...)` | Verify pricing with different vehicle types. | **FAIL** | **PRICING BUG**: Expected 4.8 (BICYCLE 0.2 + HANDICAPPED 1.2), got 7.2. Multiplier calculation incorrect. |
| **129** | `ParkingSystem.book(...)` | Verify funds transferred to system. | **FAIL** | **PRICING CASCADE ERROR**: Expected car balance 980 (1000 - 20), got 970 (1000 - 30). Cascading from Test 127 pricing bug. |
| **130** | `ParkingSystem.book(...)` | Verify multiple bookings work correctly. | **PASS** | Multiple bookings stored with correct IDs. |
| **131** | `ParkingSystem.completeBooking(...)` | Verify completing a booking. | **FAIL** | **DISTRIBUTION BUG**: Expected 14.0 (80% of 17.5), got 6.0. 80/20 distribution calculation incorrect. |
| **132** | `ParkingSystem.completeBooking(...)` | Verify 80/20 distribution on completion. | **PASS** | 80% to slot, 20% retained by system. |
| **133** | `ParkingSystem.completeBooking(...)` | Verify funds transferred to slot wallet. | **PASS** | Slot wallet receives funds. |
| **134** | `ParkingSystem.cancelBooking(...)` | Verify cancelling a booking. | **FAIL** | **REFUND BUG**: Expected vehicle balance 988.0 (1000 - 12 from cancellation), got 997.0. 90/10 refund calculation incorrect. |
| **135** | `ParkingSystem.cancelBooking(...)` | Verify 90/10 distribution on cancellation. | **FAIL** | **REFUND BUG**: Expected 998.0 (1000 - 2.0 loss), got 997.0 (1000 - 3.0 loss). System retaining more than 10%. |
| **136** | `ParkingSystem.cancelBooking(...)` | Verify vehicle receives 90% refund. | **PASS** | Net loss is 10% of booking amount. |
| **137** | `ParkingSystem.getSYSTEM_WALLET()` | Verify system wallet receives funds. | **FAIL** | **SYSTEM WALLET BUG**: Expected 20.0, got 30.0. System receiving extra funds due to pricing calculation error. |
| **138** | `ParkingSystem` | Verify system wallet after completion. | **PASS** | System retains 20% (4.0 from 20.0 booking). |
| **139** | `ParkingSystem` | Verify system wallet after cancellation. | **PASS** | System retains 10% (2.0 from 20.0 booking). |
| **140** | `ParkingSystem.book(...)` | Verify BICYCLE on COMPACT pricing. | **FAIL** | **PRICING BUG**: Expected 3.2 (2 * 10 * 0.2 * 0.8), got 4.8. Incorrect multiplier application. |
| **141** | `ParkingSystem.book(...)` | Verify MOTORCYCLE on REGULAR pricing. | **PASS** | 2 * 10 * 0.5 * 1.0 = 10.0. |
| **142** | `ParkingSystem.book(...)` | Verify CAR on LARGE pricing. | **FAIL** | **PRICING BUG**: Expected 30.0 (2 * 10 * 1.0 * 1.5), got 45.0. LARGE multiplier (1.5) possibly applied twice. |
| **143** | `ParkingSystem.book(...)` | Verify BUS on LARGE pricing. | **FAIL** | **PRICING BUG**: Expected 60.0 (2 * 10 * 2.0 * 1.5), got 90.0. LARGE multiplier (1.5) possibly applied twice (60 * 1.5 = 90). |
| **144** | `ParkingSystem.book(...)` | Verify fractional hours truncated. | **PASS** | 1.5 hours truncated to 1 hour, charged 10.0. |
| **145** | `ParkingSystem.getBookings()` | Verify booking stored in system. | **PASS** | Booking appears in system's booking list. |
| **146** | `ParkingSlot.getBookings()` | Verify booking stored in slot. | **PASS** | Booking appears in slot's booking list. |
| **147** | `ParkingSystem` | Verify multiple bookings stored independently. | **PASS** | Both bookings in system and slot. |
| **148** | `ParkingSystem.getPARKING_RATE_PER_HOUR()` | Verify parking rate configuration. | **PASS** | Rate retrievable and settable. |

---

## B) Defects List

### **Defect ID: 01**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 127
* **Description:** Basic pricing calculation error. Expected booking price of 20.0 (2 hours × 10 base rate × 1.0 vehicle rate × 1.0 slot multiplier), but got 30.0. Indicates the pricing formula is computing an incorrect multiplier or duration.
* **Suggested Fix:** Review the pricing calculation in `book()` method to ensure the formula `price = hours * PARKING_RATE_PER_HOUR * vehicleTypeRate * slotTypeMultiplier` is implemented correctly.

### **Defect ID: 02**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 128
* **Description:** Mixed vehicle and slot type pricing error. For BICYCLE (rate 0.2) on HANDICAPPED slot (multiplier 1.2), expected 4.8 (2 × 10 × 0.2 × 1.2), but got 7.2. Indicates incorrect multiplier combination handling.
* **Suggested Fix:** Verify that vehicle rate and slot type multiplier are both applied correctly without double-counting or additional factors.

### **Defect ID: 03**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 129
* **Description:** Vehicle balance after booking is incorrect (cascading from Defect 01). CAR wallet balance expected 980.0 (1000 - 20), but got 970.0 (1000 - 30). Caused by incorrect pricing charge from Test 127 bug.
* **Suggested Fix:** Fix Defect ID: 01 (pricing calculation). Once pricing is corrected, this test should pass automatically.

### **Defect ID: 04**

* **Class.Method:** `ParkingSystem.completeBooking(Booking)`
* **Test ID:** 131
* **Description:** 80/20 distribution on booking completion is incorrect. Expected slot to receive 14.0 (80% of 17.5), but got 6.0. The 80/20 split calculation is not working correctly.
* **Suggested Fix:** Review the `completeBooking()` method to verify the 80/20 distribution formula: `slotAmount = bookingAmount * 0.80`, `systemRetain = bookingAmount * 0.20`.

### **Defect ID: 05**

* **Class.Method:** `ParkingSystem.cancelBooking(Booking)`
* **Test ID:** 134
* **Description:** Vehicle cancellation refund is incorrect. Expected vehicle balance 988.0 (1000 - 12 net loss), but got 997.0 (1000 - 3 loss). The 90/10 refund calculation is not distributing funds correctly.
* **Suggested Fix:** Review the `cancelBooking()` method to verify: `refundAmount = bookingAmount * 0.90` is transferred back to vehicle, and system retains `bookingAmount * 0.10`.

### **Defect ID: 06**

* **Class.Method:** `ParkingSystem.cancelBooking(Booking)`
* **Test ID:** 135
* **Description:** System retention on cancellation is incorrect. Expected system to retain only 2.0 (10% of 20.0), resulting in net vehicle loss of 2.0, but got 3.0 loss. System is retaining more than the documented 10%.
* **Suggested Fix:** Verify that `cancelBooking()` retains exactly 10% of the booking amount and refunds 90%, not more.

### **Defect ID: 07**

* **Class.Method:** `ParkingSystem.getSYSTEM_WALLET().getBalance()`
* **Test ID:** 137
* **Description:** System wallet receives incorrect funds after booking. Expected 20.0, but got 30.0. System wallet is receiving the over-charged amount due to the pricing bug (Defect 01).
* **Suggested Fix:** Fix Defect ID: 01 (pricing calculation). System wallet should receive the correctly calculated full booking price.

### **Defect ID: 08**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 140
* **Description:** BICYCLE on COMPACT slot pricing error. Expected 3.2 (2 hours × 10 × 0.2 BICYCLE rate × 0.8 COMPACT multiplier), but got 4.8. Indicates incorrect application of slot type multiplier (possibly 1.5 applied instead of 0.8).
* **Suggested Fix:** Verify that COMPACT slot multiplier (0.8) is correctly retrieved and applied, not confused with LARGE multiplier (1.5).

### **Defect ID: 09**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 142
* **Description:** CAR on LARGE slot pricing error. Expected 30.0 (2 × 10 × 1.0 CAR rate × 1.5 LARGE multiplier), but got 45.0. Suggests LARGE multiplier (1.5) is being applied twice: 30.0 × 1.5 = 45.0.
* **Suggested Fix:** Review pricing calculation logic to ensure slot type multiplier is applied once, not multiple times. Check for nested multiplication or redundant multiplier application.

### **Defect ID: 10**

* **Class.Method:** `ParkingSystem.book(Vehicle, ParkingSlot, LocalDateTime, LocalDateTime)`
* **Test ID:** 143
* **Description:** BUS on LARGE slot pricing error. Expected 60.0 (2 × 10 × 2.0 BUS rate × 1.5 LARGE multiplier), but got 90.0. Pattern confirms LARGE multiplier (1.5) is applied twice: 60.0 × 1.5 = 90.0, same as Defect 09.
* **Suggested Fix:** Fix the root cause in pricing calculation where slot type multiplier is being applied twice or additional undocumented multiplier is present.


---

## C) Individual Contribution

* **Mahfuz Hasan Reza (011221203):** 
  - Implemented all unit tests for Wallet, Vehicle, ParkingSlot, Booking, and ParkingSystem classes.
  - Write the report

---