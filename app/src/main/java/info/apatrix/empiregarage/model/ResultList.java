package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;

public class ResultList {
  @SerializedName("message")
  private String message;
  
  @SerializedName("response")
  private ArrayList<Result> response;
  
  @SerializedName("status")
  private String status;
  
  public String getMessage() { return this.message; }
  
  public ArrayList<Result> getResponse() { return this.response; }
  
  public String getStatus() { return this.status; }
  
  public void setMessage(String paramString) { this.message = paramString; }
  
  public void setResponse(ArrayList<Result> paramArrayList) { this.response = paramArrayList; }
  
  public void setStatus(String paramString) { this.status = paramString; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ResultList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */