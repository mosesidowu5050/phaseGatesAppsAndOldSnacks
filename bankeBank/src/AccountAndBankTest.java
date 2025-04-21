import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class AccountAndBankTest {


    @Test
    public void testThatAllInputsAreValid() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        assertEquals("Ayo Adenuga", newAccount.getName());
        assertEquals(0.0, newAccount.getBalance(), 0.0);
    }

    @Test
    public void testThrowExceptionIfFirstNameLastNamePinIsEmptyOrInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new Account("", "Adenuga", "1234", "1234567890"));
        assertThrows(IllegalArgumentException.class, () -> new Account("Ayo", "", "1234", "1234567890"));
        assertThrows(IllegalArgumentException.class, () -> new Account("Ayo", "Adenuga", "", "1234567890"));
        assertThrows(IllegalArgumentException.class, () -> new Account("Ayo", "Adenuga", "12", "1234567890"));
        assertThrows(IllegalArgumentException.class, () -> new Account("Ayo", "Adenuga", null, null));
    }

    @Test
    public void testDepositWorkPerfectlyWithValidAmount() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        newAccount.deposit("1234", 3500.00);
        assertEquals(3500.00, newAccount.getBalance(), 0.01);
    }

    @Test
    public void testDepositThrowExceptionIfAmountIsNegativeAndTheDetailsInvalid() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        assertThrows(IllegalArgumentException.class, () -> newAccount.deposit("1234", -1000.00));
        assertThrows(IllegalArgumentException.class, () -> newAccount.deposit("1234", 0.0));
        assertThrows(IllegalArgumentException.class, () -> newAccount.deposit("124", 1000.00));
    }
    @Test
    public void testWithdrawValidAmount() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        newAccount.deposit("1234", 3500.00);
        newAccount.withdraw("1234", 500.00);
        assertEquals(3000.00, newAccount.getBalance(), 0.01);
    }
    @Test
    public void testWithdrawThrowExceptionIfAmountIsNegative() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        newAccount.deposit("1234", 1000.00);
        assertThrows(IllegalArgumentException.class, () -> newAccount.withdraw("1234", -1000.00));
        assertThrows(IllegalArgumentException.class, () -> newAccount.withdraw("0234", 3000.00));
    }
    @Test
    public void testWithdrawThrowExceptionIfWithdrawAmountIsGreaterThanBalance() {
        Account newAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        newAccount.deposit("1234", 1000.00);
        assertThrows(IllegalArgumentException.class, () -> newAccount.withdraw("1234", 1500.00));
    }
    @Test
    public void testTransferWorkPerfectly() {
        Account sender = new Account("Ayo", "Adenuga", "1234", "1234567890");
        Account receiver = new Account("Dada", "Williams", "4554", "1234567890");
        sender.deposit("1234", 3000.00);
        sender.transfer("1234", receiver, 500.00);
        assertEquals(2500.00, sender.getBalance(), 0.01);
        assertEquals(500.00, receiver.getBalance(), 0.01);
    }
    @Test
    public void testTransferThrowExceptionWithInvalidDetails() {
        Account sender = new Account("Ayo", "Adenuga", "1234", "1234567890");
        Account receiver = new Account("Dada", "Williams", "4554", "1234567890");
        sender.deposit("1234", 3000.00);

        assertThrows(IllegalArgumentException.class, () -> sender.transfer("1234", receiver, 5000.00));
        assertThrows(IllegalArgumentException.class, () -> receiver.transfer("0225", receiver, 1000.00));
        assertThrows(IllegalArgumentException.class, () -> sender.transfer("1234", receiver, -1500.00));
    }
    @Test
    public void testThatChangeOfPinWorksProperly() {
        Account bankAccount = new Account("Ayo", "Adenuga", "1234", "1234567890");
        bankAccount.changePin("1234", "0288");
        bankAccount.deposit("0288", 3000.00);
        assertEquals(3000.00, bankAccount.getBalance(), 0.01);
    }







    @Test
    public void testThrowExceptionIfDetailsForCreateAccountIsInvalidAndTheAccountSize(){
        List<Account> userAccounts = new ArrayList<>();
        Bank atm = new Bank();
        assertThrows(IllegalArgumentException.class, ()-> {
        atm.createAccount(null, null, "1234");
        });

        String accountNumber = atm.getAccountNumber();
        assertThrows(IllegalArgumentException.class, ()-> {
        Account accounts = new Account(null, null, "1234", accountNumber);
        userAccounts.add(accounts);
        });
        assertEquals(0, atm.getAccountSize());
    }


    @Test
    public void testThatAccountCreationWorksProperly() {
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        atm.createAccount("Dada", "Williams", "4554");
        assertEquals(2, atm.getAccount().size());
    }
     @Test
     public void testThatCheckIfAccountCreatedExists() {
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        assertEquals("Ayo Adenuga", atm.getAccount().get(0).getName());
    }

    @Test
    public void testThatCheckTheLengthOfTheAccountNumberIfItIsTenDigits() {
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        String accountNumber = atm.getAccountNumber();
        int length = 0;
        String checkAccountNumberLength = " ";
        for (int count = 0; count < accountNumber.length(); count++) {
            checkAccountNumberLength += accountNumber.charAt(count);
            length++;
        }
        assertEquals(10, length);
    }

    @Test
    public void testThatAccountCreatedSuccessfullyWithTheInformationProvided() {
        List<Account> userAccounts = new ArrayList<>();
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        String accountNumber = atm.getAccountNumber();
        Account account = new Account("Ayo", "Adenuga", "1234", accountNumber);
        userAccounts.add(account);
        assertEquals(account, userAccounts.getFirst());
    }

    @Test
    public void testAllAccountWereSuccessfullyAddedToTheCollection(){
        List<Account> userAccounts = new ArrayList<>();
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        String accountNumberOne = atm.getAccountNumber();
        atm.createAccount("Dada", "Williams", "4554");
        String accountNumberTwo = atm.getAccountNumber();
        Account accountOne = new Account("Ayo", "Adenuga", "1234", accountNumberOne);
        userAccounts.add(accountOne);
        Account accountTwo = new Account("Ayo", "Adenuga", "1234", accountNumberTwo);
        userAccounts.add(accountTwo);

        assertEquals(2, userAccounts.size());
    }

    @Test
    public void testTransferWorksPerfectly(){
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        atm.createAccount("Dada", "Wiliams", "0202");
        Account ayo = atm.getAccount().get(0);
        Account dada = atm.getAccount().get(1);
        ayo.deposit("1234", 1500.00);
        ayo.transfer("1234", dada, 500.00);

        assertEquals(1000, ayo.getBalance(), 0.01);
        assertEquals(500, dada.getBalance(), 0.01);
    }
    @Test
    public void testThrowExceptionIfDetailsAreNotValid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Bank atm = new Bank();
            atm.createAccount("Ayo", "Adenuga", "1234");
            atm.createAccount("Dada", "Williams", "4554");
            Account ayo = atm.getAccount().get(0);
            Account dada = atm.getAccount().get(1);
            ayo.deposit("1234", 1500.00);

            ayo.transfer("1234",  dada, 500.00);
            ayo.transfer("1234",  null, 500.00);
            ayo.transfer("1234", dada, 1700.00);
        });
    }

    @Test
    public void testThatGetBalanceWorksProperly() {
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        atm.createAccount("Dada", "Williams", "4554");
        Account ayo = atm.getAccount().get(0);
        Account dada = atm.getAccount().get(1);
        ayo.deposit("1234", 1500.00);
        dada.deposit("4554", 500.00);

        assertEquals(1500.00, atm.getBalance("1234", ayo), 0.01);
        assertEquals(500.00, atm.getBalance("4554", dada), 0.01);
        assertThrows(IllegalArgumentException.class, () -> atm.getBalance("1234", null));
        assertThrows(IllegalArgumentException.class, () -> atm.getBalance(null, null));

    }

    @Test
    public void testThatClosingAccountWorks(){
        Bank atm = new Bank();

        atm.createAccount("Ayo", "Adenuga", "1234");
        atm.createAccount("Dada", "Williams", "4554");
        Account ayo = atm.getAccount().get(0);
        Account dada = atm.getAccount().get(1);

        ayo.deposit("1234", 1500.00);
        ayo.withdraw("1234", 500.00);
        atm.closeAccount("1234", ayo);
        assertEquals(1, atm.getAccount().size());
    }
    @Test
    public void testThatConfirmIfAccountClosedSuccessfully(){
        Bank atm = new Bank();
        atm.createAccount("Ayo", "Adenuga", "1234");
        Account ayo = atm.getAccount().get(0);
        ayo.deposit("1234", 1500.00);

        assertThrows(IllegalArgumentException.class, () -> {
            atm.closeAccount("1234", null);
        });
    }
}

