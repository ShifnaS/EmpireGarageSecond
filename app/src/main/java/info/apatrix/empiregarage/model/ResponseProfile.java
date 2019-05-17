package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class ResponseProfile {
  @SerializedName("message")
  private String message;
  
  @SerializedName("response")
  private ResponseP result;
  
  @SerializedName("status")
  private boolean status;
  
  public String getMessage() { return this.message; }
  
  public ResponseP getResult() { return this.result; }
  
  public boolean isStatus() { return this.status; }
  
  public void setMessage(String paramString) { this.message = paramString; }
  
  public void setResult(ResponseP paramResponseP) { this.result = paramResponseP; }
  
  public void setStatus(boolean paramBoolean) { this.status = paramBoolean; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ResponseProfile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */