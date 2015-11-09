package co.fddd.www.moran.model;

import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Administrator on 2015/10/27 0027.
 */
public class SuccessMessage {
    private int status;
    private String message;
    private HashMap<String,Object> data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HashMap<String, Object> getData() {
        return data;
    }

    public void setData(HashMap<String, Object> data) {
        this.data = data;
    }
}
