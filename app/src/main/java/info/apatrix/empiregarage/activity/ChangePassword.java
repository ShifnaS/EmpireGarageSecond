package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.content.Context;
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

public class ChangePassword extends AppCompatActivity {

  @BindView(R.id.input_password_current)
  EditText _input_password_current;
  @BindView(R.id.input_password)
  EditText _input_new_password_current;
  @BindView(R.id.btn_login)
  Button _loginButton;
  @BindView(R.id.input_password_retype)
  EditText _input_password_retype;
  String auth;
  int userid;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_change_password);
    ButterKnife.bind(this);
    _loginButton.setOnClickListener(new View.OnClickListener() {

      @Override
      public void onClick(View v) {

        auth=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
        userid=SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_USER_ID);

        if(NetworkUtil.isOnline())
        {
          changepassword();


        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }
      }
    });
  }


  public void changepassword() {
    //  Log.d(TAG, "Login");

    if (!validate()) {
      onFailed("Login failed");
      return;
    }

    _loginButton.setEnabled(false);

    final ProgressDialog progressDialog = new ProgressDialog(ChangePassword.this,
            R.style.AppTheme_Dark_Dialog);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Changing...");
    progressDialog.show();



    // TODO: Implement your own authentication logic here.

    new android.os.Handler().postDelayed(
            new Runnable() {
              public void run() {
                // On complete call either onLoginSuccess or onLoginFailed
                String current = _input_password_current.getText().toString();
                String password = _input_new_password_current.getText().toString();
                String retype = _input_new_password_current.getText().toString();

                APIService service = ApiModule.getAPIService();
                Call<ResponseLogin> call = service.changePassword(password,userid,auth);
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
                          Toast.makeText(ChangePassword.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
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
  public boolean validate() {
    boolean valid = true;

    String current = _input_password_current.getText().toString().trim();
    String new_password = _input_new_password_current.getText().toString().trim();
    String retype = _input_password_retype.getText().toString().trim();
    String old=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_PASSWORD);

    if(current.isEmpty() || !current.equals(old))
    {
      _input_password_current.setError("Incorrect old password");
      valid = false;
    }
    else
    {
      _input_password_current.setError(null);
    }



    if (new_password.isEmpty() || new_password.length() < 4 || new_password.length() > 10)
    {
      _input_new_password_current.setError("between 4 and 10 alphanumeric characters");
      valid = false;
    }
    else
    {
      _input_new_password_current.setError(null);
    }

    if (retype.isEmpty() || !retype.equals(new_password))
    {
      _input_password_retype.setError("password mismatch");
      valid = false;
    }
    else
    {
      _input_password_retype.setError(null);
    }
    return valid;
  }
}
