package online.banking.demo.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.json.simple.JSONObject;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public class Account implements Persistable {
    @EqualsAndHashCode.Include
    private String IBAN;

    private double balance;
    private List<String> transactions;

    public Account(final JSONObject jsonObject) {
        IBAN = (String) jsonObject.get("IBAN");
        balance = (double) jsonObject.get("IBAN");
        transactions = (List<String>) jsonObject.get("IBAN");
    }

    public Transaction sendMoney(final Account to, double amount) {
        if (balance < amount) {
            return null;
        }

        balance -= amount;
        to.balance += amount;

        Transaction transaction = new Transaction(this.IBAN, to.IBAN, amount);
        transactions.add(transaction.getId());
        to.transactions.add(transaction.getId());

        return transaction;
    }

    @Override
    public String getId() {
        return IBAN;
    }

    @Override
    public String getIdKeyword() {
        return "IBAN";
    }

    @Override
    public JSONObject dump() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("IBAN", IBAN);
        jsonObject.put("balance", balance);
        jsonObject.put("transactions", transactions);

        return jsonObject;
    }
}
