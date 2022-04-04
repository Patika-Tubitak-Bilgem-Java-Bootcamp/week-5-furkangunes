package online.banking.demo.model;

import org.json.simple.JSONObject;

public interface Persistable {
    String getId();
    String getIdKeyword();
    JSONObject dump();
}
