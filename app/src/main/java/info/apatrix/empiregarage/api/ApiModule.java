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
    return (new Retrofit.Builder()).baseUrl(APIUrl.BASE_URL).client(okHttpClient).addConverterFactory(GsonConverterFactory.create(gson)).build();
  }
}

