package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ResponseNotification {
    @SerializedName("status")
    private boolean status;
    @SerializedName("message")
    private String message;
   @SerializedName("response")
    private ArrayList<Notification> response;
    @SerializedName("count")
    private String count;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ArrayList<Notification> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<Notification> response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "ResponseNotification{" +
                "status=" + status +
                ", message='" + message + '\'' +
                '}';
    }
}
