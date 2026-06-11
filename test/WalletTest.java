import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class WalletTest {
    private Wallet wallet;
    private Wallet targetWallet;

    @BeforeEach
    public void setUp() {
        wallet = new Wallet();
        targetWallet = new Wallet();
    }

    @AfterEach
    public void tearDown() {
        wallet = null;
        targetWallet = null;
    }

    // Constructor Tests

    @Test
    public void testWalletDefaultConstructor() {
        Wallet newWallet = new Wallet();
        
        assertEquals(0.0, newWallet.getBalance(), "Default wallet should have 0 balance");
    }

    @Test
    public void testWalletConstructorWithInitialBalance() {
        Wallet newWallet = new Wallet(100.5);
        assertEquals(100.5, newWallet.getBalance(), "Wallet should initialize with given balance");
    }

    @Test
    public void testWalletConstructorWithNegativeBalance() {
        Wallet newWallet = new Wallet(-50.0);
        assertEquals(-50.0, newWallet.getBalance(), "Constructor allows negative balance without validation");
    }

    // getBalance Tests
    @Test
    public void testGetBalanceAfterCreation() {
        wallet = new Wallet(250.75);
        assertEquals(250.75, wallet.getBalance(), "getBalance should return initial balance");
    }

    // AddFunds Tests
    @Test
    public void testAddFundsPositiveAmount() {
        wallet = new Wallet(100.0);
        wallet.addFunds(50.0);
        assertEquals(150.0, wallet.getBalance(), "Balance should increase by added amount");
    }

    @Test
    public void testAddFundsMultipleOperations() {
        wallet = new Wallet(100.0);
        wallet.addFunds(25.0);
        wallet.addFunds(75.0);
        assertEquals(200.0, wallet.getBalance(), "Multiple additions should accumulate");
    }

    @Test
    public void testAddFundsSmallAmount() {
        wallet = new Wallet(100.0);
        wallet.addFunds(0.01);
        assertEquals(100.01, wallet.getBalance(), "Should handle small decimal amounts");
    }

    @Test
    public void testAddFundsZeroAmount() {
        wallet = new Wallet(100.0);
        assertThrows(InvalidAmountException.class, () -> wallet.addFunds(0.0),
                "Adding zero amount should throw InvalidAmountException");
    }

    @Test
    public void testAddFundsNegativeAmount() {
        wallet = new Wallet(100.0);
        assertThrows(InvalidAmountException.class, () -> wallet.addFunds(-50.0),
                "Adding negative amount should throw InvalidAmountException");
    }

    // deductFunds Tests
    @Test
    public void testDeductFundsValidAmount() {
        wallet = new Wallet(100.0);
        wallet.deductFunds(30.0);
        assertEquals(70.0, wallet.getBalance(), "Balance should decrease by deducted amount");
    }

    @Test
    public void testDeductFundsCompleteBalance() {
        wallet = new Wallet(100.0);
        wallet.deductFunds(100.0);
        assertEquals(0.0, wallet.getBalance(), "Deducting exact balance should result in 0");
    }

    @Test
    public void testDeductFundsMultipleOperations() {
        wallet = new Wallet(200.0);
        wallet.deductFunds(50.0);
        wallet.deductFunds(75.0);
        assertEquals(75.0, wallet.getBalance(), "Multiple deductions should work correctly");
    }

    @Test
    public void testDeductFundsMoreThanBalance() {
        wallet = new Wallet(50.0);
        assertThrows(InsufficientFundsException.class, () -> wallet.deductFunds(75.0),
                "Deducting more than balance should throw InsufficientFundsException");
    }

    @Test
    public void testDeductFundsZeroAmount() {
        wallet = new Wallet(100.0);
        assertThrows(InvalidAmountException.class, () -> wallet.deductFunds(0.0),
                "Deducting zero amount should throw InvalidAmountException");
    }

    @Test
    public void testDeductFundsNegativeAmount() {
        wallet = new Wallet(100.0);
        assertThrows(InvalidAmountException.class, () -> wallet.deductFunds(-50.0),
                "Deducting negative amount should throw InvalidAmountException");
    }

    @Test
    public void testDeductFundsFromEmptyWallet() {
        wallet = new Wallet(0.0);
        assertThrows(InsufficientFundsException.class, () -> wallet.deductFunds(1.0),
                "Deducting from empty wallet should throw InsufficientFundsException");
    }

    @Test
    public void testDeductFundsSmallAmount() {
        wallet = new Wallet(100.0);
        wallet.deductFunds(0.01);
        assertEquals(99.99, wallet.getBalance(), "Should handle small decimal deductions");
    }

    // transferFunds Tests
    @Test
    public void testTransferFundsValidAmount() {
        wallet = new Wallet(100.0);
        targetWallet = new Wallet(50.0);
        wallet.transferFunds(targetWallet, 30.0);
        
        assertEquals(70.0, wallet.getBalance(), "Source wallet should decrease");
        assertEquals(80.0, targetWallet.getBalance(), "Target wallet should increase");
    }

    @Test
    public void testTransferFundsCompleteBalance() {
        wallet = new Wallet(100.0);
        targetWallet = new Wallet(0.0);
        wallet.transferFunds(targetWallet, 100.0);
        
        assertEquals(0.0, wallet.getBalance(), "Source wallet should be empty");
        assertEquals(100.0, targetWallet.getBalance(), "Target wallet should have all funds");
    }

    @Test
    public void testTransferFundsMoreThanBalance() {
        wallet = new Wallet(50.0);
        targetWallet = new Wallet(100.0);
        assertThrows(InsufficientFundsException.class, () -> wallet.transferFunds(targetWallet, 75.0),
                "Transferring more than balance should throw InsufficientFundsException");
    }

    @Test
    public void testTransferFundsZeroAmount() {
        wallet = new Wallet(100.0);
        targetWallet = new Wallet(50.0);
        assertThrows(InvalidAmountException.class, () -> wallet.transferFunds(targetWallet, 0.0),
                "Transferring zero amount should throw InvalidAmountException");
    }

    @Test
    public void testTransferFundsNegativeAmount() {
        wallet = new Wallet(100.0);
        targetWallet = new Wallet(50.0);
        assertThrows(InvalidAmountException.class, () -> wallet.transferFunds(targetWallet, -30.0),
                "Transferring negative amount should throw InvalidAmountException");
    }

    @Test
    public void testTransferFundsFromEmptyWallet() {
        wallet = new Wallet(0.0);
        targetWallet = new Wallet(100.0);
        assertThrows(InsufficientFundsException.class, () -> wallet.transferFunds(targetWallet, 1.0),
                "Transferring from empty wallet should throw InsufficientFundsException");
    }

    @Test
    public void testTransferFundsMultipleTransfers() {
        wallet = new Wallet(300.0);
        Wallet wallet2 = new Wallet(100.0);
        Wallet wallet3 = new Wallet(50.0);
        
        wallet.transferFunds(wallet2, 50.0);
        wallet.transferFunds(wallet3, 100.0);
        
        assertEquals(150.0, wallet.getBalance(), "Source wallet after 2 transfers");
        assertEquals(150.0, wallet2.getBalance(), "Target wallet2 after transfer");
        assertEquals(150.0, wallet3.getBalance(), "Target wallet3 after transfer");
    }

    @Test
    public void testTransferFundsToSameWallet() {
        wallet = new Wallet(100.0);
        wallet.transferFunds(wallet, 50.0);
        assertEquals(100.0, wallet.getBalance(), "Transfer to same wallet should maintain balance");
    }

    @Test
    public void testTransferFundsSmallAmount() {
        wallet = new Wallet(100.0);
        targetWallet = new Wallet(0.0);
        wallet.transferFunds(targetWallet, 0.01);
        
        assertEquals(99.99, wallet.getBalance(), "Source wallet precision");
        assertEquals(0.01, targetWallet.getBalance(), "Target wallet precision");
    }
}
