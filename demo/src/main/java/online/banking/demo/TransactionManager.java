package online.banking.demo;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import online.banking.demo.model.Account;
import online.banking.demo.model.Transaction;
import online.banking.demo.model.User;
import online.banking.demo.util.DoesNotExistException;

public class TransactionManager extends PersistanceManager {
    private static final Path TRANSACTION_FILE_PATH = Paths.get(BASE_FILE_PATH.toString(), "Transaction.json");

    public List<Transaction> getTransactions(final Account account) {
        try {
            List<Transaction> transactions = new LinkedList<>();
            for (Object object : readArray(TRANSACTION_FILE_PATH)) {
                JSONObject jsonObject = (JSONObject) object;
                if (account.getTransactions().contains(jsonObject.get("id"))) {
                    transactions.add(new Transaction(jsonObject));
                }
            }
            return transactions;
        } catch (Exception e) {
            return null;
        }
    }

    public boolean transact(final Account from, final Account to, double amount) {
        Transaction transaction = from.sendMoney(to, amount);
        if (transaction == null) {
            return false;
        }
        persist(AccountManager.ACCOUNT_FILE_PATH, from, to);
        persist(TRANSACTION_FILE_PATH, transaction);
        return true;
    }
}
