package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class IssuedInventoryPackList {



    ///////////// IssuedInventoryServicePackagesLists/////////////////


    @SerializedName("reqtask_mat_name")
    private String reqtask_mat_name;
    @SerializedName("reqtask_mat_qty")
    private String reqtask_mat_qty;
    @SerializedName("reqtask_status")
    private String reqtask_request_approved;
    @SerializedName("task_name")
    private  String task_name;

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }

    public String getReqtask_mat_name() {
        return reqtask_mat_name;
    }

    public void setReqtask_mat_name(String reqtask_mat_name) {
        this.reqtask_mat_name = reqtask_mat_name;
    }

    public String getReqtask_mat_qty() {
        return reqtask_mat_qty;
    }

    public void setReqtask_mat_qty(String reqtask_mat_qty) {
        this.reqtask_mat_qty = reqtask_mat_qty;
    }

    public String getReqtask_request_approved() {
        return reqtask_request_approved;
    }

    public void setReqtask_request_approved(String reqtask_request_approved) {
        this.reqtask_request_approved = reqtask_request_approved;
    }


}
