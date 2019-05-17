package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class ResponseLogin {
  @SerializedName("message")
  private String message;
  
  @SerializedName("response")
  private Result response;
  
  @SerializedName("status")
  private boolean status;
  
  public String getMessage() { return this.message; }
  
  public Result getResponse() { return this.response; }
  
  public boolean getStatus() { return this.status; }
  
  public void setMessage(String paramString) { this.message = paramString; }
  
  public void setResponse(Result paramResult) { this.response = paramResult; }
  
  public void setStatus(boolean paramBoolean) { this.status = paramBoolean; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ResponseLogin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */