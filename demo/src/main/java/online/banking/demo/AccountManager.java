package online.banking.demo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.json.simple.parser.ParseException;

import online.banking.demo.model.Account;
import online.banking.demo.model.User;
import online.banking.demo.util.DoesNotExistException;

public class AccountManager extends PersistanceManager {
    public static final Path ACCOUNT_FILE_PATH = Paths.get(BASE_FILE_PATH.toString(), "Account.json");

    public boolean ownsAccount(final User user, String iban) {
        return user.getAccounts().stream().anyMatch(iban::equals);
    }

    public Account getAccount(String iban) throws DoesNotExistException {
        try {
            return new Account(findObject(iban, "IBAN", ACCOUNT_FILE_PATH));
        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
