package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class Responses {
  @SerializedName("CustomerDetails")
  private Customer Customer;
  
  @SerializedName("ReportDefects")
  private ArrayList<ReportDefects> ReportDefects;
  
  @SerializedName("ServicePackages")
  private ArrayList<ServicePackages> ServicePackages;
  
  private String id;



  @SerializedName("IssuedInventoryLists")
  private ArrayList<IssuedInventerayList> issued_inventory_lists;
  
  @SerializedName("ServicePackagesTask")
  private ArrayList<ServicePackagesTask> servicePackagesTask;

  public ArrayList<ServicePackagesTask> getServicePackagesTask() {
    return servicePackagesTask;
  }

  public void setServicePackagesTask(ArrayList<ServicePackagesTask> servicePackagesTask) {
    this.servicePackagesTask = servicePackagesTask;
  }

  @SerializedName("IssuedInventoryServicePackagesLists")
  private ArrayList<IssuedInventoryPackList> IssuedInventoryServicePackagesLists;

  public ArrayList<IssuedInventoryPackList> getIssuedInventoryServicePackagesLists() {
    return IssuedInventoryServicePackagesLists;
  }

  public void setIssuedInventoryServicePackagesLists(ArrayList<IssuedInventoryPackList> issuedInventoryServicePackagesLists) {
    IssuedInventoryServicePackagesLists = issuedInventoryServicePackagesLists;
  }

  public Customer getCustomer() { return this.Customer; }
  
  public String getId() { return this.id; }
  
  public ArrayList<IssuedInventerayList> getIssued_inventory_lists() { return this.issued_inventory_lists; }
  
  public ArrayList<ReportDefects> getReportDefects() { return this.ReportDefects; }
  
  public ArrayList<ServicePackages> getServicePackages() { return this.ServicePackages; }
  
  public void setCustomer(Customer paramCustomer) { this.Customer = paramCustomer; }
  
  public void setId(String paramString) { this.id = paramString; }
  
  public void setIssued_inventory_lists(ArrayList<IssuedInventerayList> paramArrayList) { this.issued_inventory_lists = paramArrayList; }
  
  public void setReportDefects(ArrayList<ReportDefects> paramArrayList) { this.ReportDefects = paramArrayList; }
  
  public void setServicePackages(ArrayList<ServicePackages> paramArrayList) { this.ServicePackages = paramArrayList; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\Responses.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */