package info.apatrix.empiregarage.model;

public class Notification {


    private String datetime;

    private String user_id;

    private String job_id;

    private String notifi_message;

    private String notifi_id;

    private String description;

    private String status;

    public String getNotifi_id() {
        return notifi_id;
    }

    public void setNotifi_id(String notifi_id) {
        this.notifi_id = notifi_id;
    }

    public String getJob_id() {
        return job_id;
    }

    public void setJob_id(String job_id) {
        this.job_id = job_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNotifi_message() {
        return notifi_message;
    }

    public void setNotifi_message(String notifi_message) {
        this.notifi_message = notifi_message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
