package info.apatrix.empiregarage.model;

import android.os.Parcel;
import android.os.Parcelable;

public class ReportDefects implements Parcelable {
  public static final Creator<ReportDefects> CREATOR = new Creator<ReportDefects>() {
      public ReportDefects createFromParcel(Parcel param1Parcel) { return new ReportDefects(param1Parcel); }
      
      public ReportDefects[] newArray(int param1Int) { return new ReportDefects[param1Int]; }
    };
  
  private String defect_id;
  
  private String defect_name;
  
  private String remarks;
  
  protected ReportDefects(Parcel paramParcel) { this.defect_id = paramParcel.readString();
    this.defect_name = paramParcel.readString();
    this.remarks = paramParcel.readString(); }
  
  public int describeContents() { return 0; }
  
  public String getDefect_id() { return this.defect_id; }
  
  public String getDefect_name() { return this.defect_name; }
  
  public String getRemarks() { return this.remarks; }
  
  public void setDefect_id(String paramString) { this.defect_id = paramString; }
  
  public void setDefect_name(String paramString) { this.defect_name = paramString; }
  
  public void setRemarks(String paramString) { this.remarks = paramString; }
  
  public void writeToParcel(Parcel paramParcel, int paramInt) { paramParcel.writeString(this.defect_id);
    paramParcel.writeString(this.defect_name);
    paramParcel.writeString(this.remarks); }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ReportDefects.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */