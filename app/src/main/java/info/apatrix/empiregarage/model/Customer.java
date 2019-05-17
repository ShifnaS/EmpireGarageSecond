package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class Customer {
  @SerializedName("color")
  private String color;
  
  @SerializedName("current_mileage")
  private String currentMileage;
  
  @SerializedName("customer_email")
  private String email;
  
  @SerializedName("car_make")
  private String make;
  
  @SerializedName("customer_mobile")
  private String mobile;
  
  @SerializedName("car_model")
  private String model;
  
  @SerializedName("customer_name")
  private String name;
  
  @SerializedName("plate_number")
  private String plateNumber;
  
  public String getColor() { return this.color; }
  
  public String getCurrentMileage() { return this.currentMileage; }
  
  public String getEmail() { return this.email; }
  
  public String getMake() { return this.make; }
  
  public String getMobile() { return this.mobile; }
  
  public String getModel() { return this.model; }
  
  public String getName() { return this.name; }
  
  public String getPlateNumber() { return this.plateNumber; }
  
  public void setColor(String paramString) { this.color = paramString; }
  
  public void setCurrentMileage(String paramString) { this.currentMileage = paramString; }
  
  public void setEmail(String paramString) { this.email = paramString; }
  
  public void setMake(String paramString) { this.make = paramString; }
  
  public void setMobile(String paramString) { this.mobile = paramString; }
  
  public void setModel(String paramString) { this.model = paramString; }
  
  public void setName(String paramString) { this.name = paramString; }
  
  public void setPlateNumber(String paramString) { this.plateNumber = paramString; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\Customer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */