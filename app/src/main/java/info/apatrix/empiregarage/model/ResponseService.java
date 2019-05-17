package info.apatrix.empiregarage.model;

import com.google.gson.annotations.SerializedName;

public class ResponseService {
  @SerializedName("message")
  private String message;
  
  @SerializedName("response")
  private Responses response;
  
  @SerializedName("status")
  private String status;
  
  public String getMessage() { return this.message; }
  
  public Responses getResponse() { return this.response; }
  
  public String getStatus() { return this.status; }
  
  public void setMessage(String paramString) { this.message = paramString; }
  
  public void setResponse(Responses paramResponses) { this.response = paramResponses; }
  
  public void setStatus(String paramString) { this.status = paramString; }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\model\ResponseService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */