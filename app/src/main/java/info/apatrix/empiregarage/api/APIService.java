package info.apatrix.empiregarage.api;

import com.google.gson.JsonObject;
import info.apatrix.empiregarage.model.ResponseLogin;
import info.apatrix.empiregarage.model.ResponseNotification;
import info.apatrix.empiregarage.model.ResponseProfile;
import info.apatrix.empiregarage.model.ResponseService;
import info.apatrix.empiregarage.model.ResultList;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
  @FormUrlEncoded
  @POST("Settings/change_password")
  Call<ResponseLogin> changePassword(@Field("new_password") String paramString1, @Field("user_id") int paramInt, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("Technician_services/complete_service")
  Call<ResponseService> completeService(@Field("job_id") String paramString1, @Field("user_id") String paramString2, @Header("Authtoken") String paramString3);
  
  @FormUrlEncoded
  @POST("Settings/forgot_password")
  Call<ResponseLogin> forgetPassword(@Field("email") String paramString1, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("Technician_services/completed_services")
  Call<ResultList> getClosedCarList(@Field("roleId") int paramInt1, @Field("userId") int paramInt2, @Header("Authtoken") String paramString);

  @FormUrlEncoded
  @POST("Settings/notifications")
  Call<ResponseNotification> getNotification(@Field("userId") int paramInt2, @Header("Authtoken") String paramString);


    @FormUrlEncoded
    @POST("Settings/notifications")
    Observable<ResponseNotification> getRandomNotification(@Field("userId") int userId, @Header("Authtoken") String token);

  @FormUrlEncoded
  @POST("Technician_services/complete_service_view")
  Call<ResponseService> getCompletedServiceDetail(@Field("job_id") String paramString1, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("Technician_services/drew_out_inventory_services")
  Call<ResultList> getDewOutCarList(@Field("roleId") int paramInt1, @Field("userId") int paramInt2, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Technician_services/drew_out_inventory_services_view")
  Call<ResponseService> getDrewOutServiceDetail(@Field("job_id") String paramString1, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("Technician_services/material_list")
  Call<ResultList> getMaterialTypeList(@Field("user_id") int paramInt, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Technician_services/opened_services")
  Call<ResultList> getOpenedCarList(@Field("roleId") int paramInt1, @Field("userId") int paramInt2, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Settings/user_profile")
  Call<ResponseProfile> getProfile(@Field("user_id") int paramInt, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Technician_services/requested_inventory_services")
  Call<ResultList> getRequestedCarList(@Field("roleId") int paramInt1, @Field("userId") int paramInt2, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Technician_services/requested_inventory_services_view")
  Call<ResponseService> getRequestedServiceDetail(@Field("job_id") String paramString1, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("Technician_services/services_detail")
  Call<ResponseService> getServiceDetail(@Field("job_id") String paramString1, @Header("Authtoken") String paramString2);
  
  @FormUrlEncoded
  @POST("auth/login")
  Call<ResponseLogin> login(@Field("email") String paramString1, @Field("password") String paramString2);
  
  @Headers({"Content-Type: application/json;"})
  @POST("Technician_services/request_material")
  Call<ResponseLogin> request_material(@Body JsonObject paramJsonObject, @Header("Authtoken") String paramString);
  
  @FormUrlEncoded
  @POST("Technician_services/start_service")
  Call<ResponseService> startOpenService(@Field("job_id") String paramString1, @Field("user_id") String paramString2, @Header("Authtoken") String paramString3);
  
  @FormUrlEncoded
  @POST("Settings/user_editprofile")
  Call<ResponseProfile> updateProfile(@Field("user_id") int paramInt, @Field("email") String paramString1, @Field("full_name") String paramString2, @Header("Authtoken") String paramString3);
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\api\APIService.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */