package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class ServicePackages {
  @SerializedName("package_id")
  private String package_id;
  
  @SerializedName("package_name")
  private String serpack_name;
  
  @SerializedName("package_price")
  private String serpack_price;
  
  public String getSerpack_name() { return this.serpack_name; }
  
  public String getSerpack_price() { return this.serpack_price; }
  
  public void setSerpack_name(String paramString) { this.serpack_name = paramString; }
  
  public void setSerpack_price(String paramString) { this.serpack_price = paramString; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ServicePackages.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */