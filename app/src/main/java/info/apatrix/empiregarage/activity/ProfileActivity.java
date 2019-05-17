package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
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
import info.apatrix.empiregarage.model.ResponseP;
import info.apatrix.empiregarage.model.ResponseProfile;
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

  String email = "";
  String fullname = "";

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
      Toolbar toolbar = findViewById(R.id.toolbar);
      setSupportActionBar(toolbar);
      getSupportActionBar().setDisplayShowTitleEnabled(false);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      getSupportActionBar().setDisplayShowHomeEnabled(true);

      if(NetworkUtil.isOnline())
    {
        getData();
      _loginButton.setOnClickListener(new View.OnClickListener() {
        public void onClick(View param1View) {
          email = et_email.getText().toString();
          fullname = et_name.getText().toString();
          submitData();
        }
      });

    }
    else
    {
      Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
    }
  }



  private void submitData() {
    final ProgressDialog progressDialog = new ProgressDialog(ProfileActivity.this, R.style.AppTheme_Dark_Dialog);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Authenticating...");
    progressDialog.show();
    ApiModule.getAPIService().updateProfile(userid, email, fullname, auth).enqueue(new Callback<ResponseProfile>() {
      public void onFailure(Call<ResponseProfile> param1Call, Throwable param1Throwable) 
      {
        progressDialog.dismiss();
        Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseProfile> param1Call, Response<ResponseProfile> param1Response) 
      { 
        progressDialog.dismiss();
        try {
          if (param1Response.body().isStatus()) {
          
            Toast.makeText(getApplicationContext(), ""+param1Response.body().getMessage(), Toast.LENGTH_SHORT).show();
           
          }
          else
          {
            Toast.makeText(getApplicationContext(), ""+param1Response.body().getMessage(), Toast.LENGTH_SHORT).show();

          }
          
        } catch (Exception e) {
          e.printStackTrace();
          Log.e("Exception ", e.getMessage());
        }  }
    });
  }
  
  

   private void getData() {

        APIService service = ApiModule.getAPIService();
        Call<ResponseProfile> call = service.getProfile(userid, auth);
        call.enqueue(new Callback<ResponseProfile>() {
            @Override
            public void onResponse(Call<ResponseProfile> call, Response<ResponseProfile> response) {

                try
                {
                  
                  ResponseP responseP = response.body().getResult();
                  et_email.setText(responseP.getEmail());
                  et_name.setText(responseP.getFullname());
                  if (Integer.parseInt(responseP.getRole_id()) == 3)
                    et_role.setText("Technician");
                  if (Integer.parseInt(responseP.getStatus()) == 1) {
                    et_status.setText("Active");
                  }


                }
                catch (Exception e)
                {
                    e.printStackTrace();
                    Log.e("Exception ",e.getMessage());


                }

            }

            @Override
            public void onFailure(Call<ResponseProfile> call, Throwable t) {
                //       progressBar.setVisibility(View.GONE);

                Log.e("MyTag", "requestFailed", t);
                Log.e("Failure ",t.getMessage());

            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        Intent i=new Intent(getApplicationContext(),HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
        return super.onOptionsItemSelected(item);

    }
}
