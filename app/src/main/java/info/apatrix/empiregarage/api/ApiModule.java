package info.apatrix.empiregarage.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {
  public static APIService getAPIService() { return (APIService)getRetrofitInstance().create(APIService.class); }
  
  private static Retrofit getRetrofitInstance() {
    OkHttpClient okHttpClient = new OkHttpClient();
    Gson gson = (new GsonBuilder()).setLenient().create();
    return (new Retrofit.Builder()).baseUrl("http://dev-empire.akstech.com.sg/index.php/api/").client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
  }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\api\ApiModule.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */