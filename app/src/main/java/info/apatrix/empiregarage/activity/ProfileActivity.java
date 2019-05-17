package info.apatrix.empiregarage.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.ResponseLogin;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.NetworkUtil;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {


  @BindView(R.id.name)
  EditText et_name;
  @BindView(R.id.email)
  EditText et_email;
  @BindView(R.id.btn_login)
  Button _loginButton;
  @BindView(R.id.role)
  EditText et_role;
  @BindView(R.id.status)
  EditText et_status;
  String auth;
  int userid;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_profile);
    auth= SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
    userid=SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_USER_ID);
    ButterKnife.bind(this);

    if(NetworkUtil.isOnline())
    {
      //  getData();


    }
    else
    {
      Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
    }
  }

    /*private void getData() {

        APIService service = ApiModule.getAPIService();
        Call<ResponseLogin> call = service.changePassword(userid,auth);
        call.enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {

                try
                {
                    if(response.body()!=null&&response.isSuccessful())
                    {
                        if(response.body().getStatus())
                        {
                            Toast.makeText(ProfileActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                            et_email.setText("");
                            et_name.setText("");
                            et_role.setText("");
                            et_status.setText("");
                        }

                    }
                    else
                    {
                        Toast.makeText(ProfileActivity.this, "Server Busy", Toast.LENGTH_SHORT).show();
                    }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                //       progressBar.setVisibility(View.GONE);

                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }*/
}
