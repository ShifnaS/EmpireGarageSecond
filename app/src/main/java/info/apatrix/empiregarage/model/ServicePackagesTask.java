package info.apatrix.empiregarage.model;

import java.io.Serializable;

public class ServicePackagesTask implements Serializable {


    private String package_id;
    private String package_name;
    private String task_id;
    private String task_name;

    String material_id;
    String quantity;

    public String getPackage_id() {
        return package_id;
    }

    public void setPackage_id(String package_id) {
        this.package_id = package_id;
    }

    public String getPackage_name() {
        return package_name;
    }

    public void setPackage_name(String package_name) {
        this.package_name = package_name;
    }

    public String getTask_id() {
        return task_id;
    }

    public void setTask_id(String task_id) {
        this.task_id = task_id;
    }

    public String getTask_name() {
        return task_name;
    }

    public void setTask_name(String task_name) {
        this.task_name = task_name;
    }


    public String getMaterial_id() {
        return material_id;
    }

    public void setMaterial_id(String material_id) {
        this.material_id = material_id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ServicePackagesTask{" +
                ", material_id='" + material_id + '\'' +
                ", quantity='" + quantity + '\'' +
                ", pack_id='" + package_id + '\'' +
                ", task_id='" + task_id + '\'' +
                '}';
    }
}
