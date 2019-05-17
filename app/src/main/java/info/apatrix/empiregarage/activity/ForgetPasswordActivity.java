package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class ForgetPasswordActivity extends AppCompatActivity {
  @BindView(R.id.email)
  EditText et_email;
  String auth;
  int userid;
  @BindView(R.id.btn_login)
  Button _loginButton;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_forget_password);
    ButterKnife.bind(this);

    auth= SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
    userid=SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_USER_ID);

    _loginButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {


        if(NetworkUtil.isOnline())
        {
          forgetPassword();

        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }

  private void forgetPassword() {
    final String email=et_email.getText().toString().trim();
    if(email.equals(""))
    {
      et_email.setError("Please enter your registered email");

    }
    else
    {
      _loginButton.setEnabled(false);

      final ProgressDialog progressDialog = new ProgressDialog(ForgetPasswordActivity.this,
              R.style.AppTheme_Dark_Dialog);
      progressDialog.setIndeterminate(true);
      progressDialog.setMessage("Sending...");
      progressDialog.show();


      new android.os.Handler().postDelayed(
              new Runnable() {
                public void run() {
                  // On complete call either onLoginSuccess or onLoginFailed


                  APIService service = ApiModule.getAPIService();
                  Call<ResponseLogin> call = service.forgetPassword(email,auth);
                  call.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                      progressDialog.dismiss();

                      try
                      {
                        if(response.body()!=null&&response.isSuccessful())
                        {
                          if(response.body().getStatus())
                          {
                            Toast.makeText(ForgetPasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            onSuccess();
                          }
                          else
                          {
                            onFailed("Failed to Change ");
                          }
                        }
                        else
                        {
                          onFailed("Server Busy");
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




                }
              }, 3000);
    }
  }
  public void onSuccess() {
    _loginButton.setEnabled(true);
    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
    startActivity(intent);
    finish();
  }

  public void onFailed(String msg) {
    Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
    _loginButton.setEnabled(true);
  }
}
