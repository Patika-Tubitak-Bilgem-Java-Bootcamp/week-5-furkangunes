package online.banking.demo.model;

import org.json.simple.JSONObject;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
// @RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded=true)
public class Transaction implements Persistable {
    private static int ID_COUNTER = 20000;

    @EqualsAndHashCode.Include
    private String id;
    
    private String from;
    private String to;
    private double amount;

    public Transaction(final String from, final String to, double amount) {
        id = nextId();
        this.from = from;
        this.to = to;
        this.amount = amount;
    }

    public Transaction(final JSONObject jsonObject) {
        id = (String) jsonObject.get("id");
        from = (String) jsonObject.get("from");
        to = (String) jsonObject.get("to");
        amount = (double) jsonObject.get("amount");
    }

    private String nextId() {
        String id = Integer.toString(ID_COUNTER++);
        return id;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getIdKeyword() {
        return "id";
    }

    @Override
    public JSONObject dump() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", id);
        jsonObject.put("from", from);
        jsonObject.put("to", to);
        jsonObject.put("amount", amount);

        return jsonObject;
    }
}
