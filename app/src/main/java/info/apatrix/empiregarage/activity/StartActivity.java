package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.ReportDefects;
import info.apatrix.empiregarage.model.ResponseLogin;
import info.apatrix.empiregarage.model.ResponseService;
import info.apatrix.empiregarage.model.Result;
import info.apatrix.empiregarage.model.ResultList;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StartActivity extends AppCompatActivity {
  String job_id="",auth_token,other="", battery="",suspension="",engine="",steering="",tyre="";
  int rollId,userId;
  int tyre_quantity = 0;

  int battery_quantity = 0;

  int engine_quantity = 0;


  String material_id_battery = "";

  String material_id_engine = "";

  String material_id_other = "";

  String material_id_steering = "";

  String material_id_suspension = "";

  String material_id_tyre = "";


  ProgressDialog progressDialog;

  int quantity_other = 0;

  ArrayList<Result> resultList = new ArrayList();


  List<String> spinnerArray = new ArrayList();


  int steering_quantity = 0;

  int suspension_quantity = 0;


  @BindView(R.id.other)
  TextView tv_other;
  @BindView(R.id.battery)
  TextView tv_battery;
  @BindView(R.id.steering)
  TextView tv_steering;
  @BindView(R.id.engine)
  TextView tv_engine;
  @BindView(R.id.suspension)
  TextView tv_suspension;
  @BindView(R.id.tyres)
  TextView tv_tyres;


  ///status

  @BindView(R.id.other_status)
  Spinner tv_other_status;
  @BindView(R.id.battery_status)
  Spinner tv_battery_status;
  @BindView(R.id.steering_status)
  Spinner tv_steering_status;
  @BindView(R.id.engine_status)
  Spinner tv_engine_status;
  @BindView(R.id.suspension_status)
  Spinner tv_suspension_status;
  @BindView(R.id.tyres_status)
  Spinner tv_tyres_status;

  ///material

  @BindView(R.id.other_material)
  Spinner tv_other_material;
  @BindView(R.id.battery_material)
  Spinner tv_battery_material;
  @BindView(R.id.steering_material)
  Spinner tv_steering_material;
  @BindView(R.id.engine_material)
  Spinner tv_engine_material;
  @BindView(R.id.suspension_material)
  Spinner tv_suspension_material;
  @BindView(R.id.tyres_material)
  Spinner tv_tyres_material;

  //quantity
  @BindView(R.id.other_quantity)
  TextView tv_other_quantity;
  @BindView(R.id.battery_quantity)
  TextView tv_battery_quantity;
  @BindView(R.id.steering_quantity)
  TextView tv_steering_quantity;
  @BindView(R.id.engine_quantity)
  TextView tv_engine_quantity;
  @BindView(R.id.suspension_quantity)
  TextView tv_suspension_quantity;
  @BindView(R.id.tyres_quantity)
  TextView tv_tyres_quantity;


  @BindView(R.id.id)
  TextView tv_id;
  @BindView(R.id.requsest)
  Button tv_requsest;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_start);
    job_id= SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_JOB_ID);
    auth_token=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
    rollId= SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_ROLE_ID);
    userId=SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_USER_ID);
    ButterKnife.bind(this);
    Toolbar toolbar = findViewById(R.id.toolbar);

    setSupportActionBar(toolbar);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);



    tv_id.setText("Gold Maintanence Package (Job ID #"+job_id+")");
    Intent intent = getIntent();
    this.other = intent.getStringExtra("Others");
    this.battery = intent.getStringExtra("Battery");
    this.suspension = intent.getStringExtra("Suspension");
    this.engine = intent.getStringExtra("Engine");
    this.steering = intent.getStringExtra("Steering");
    this.tyre = intent.getStringExtra("Tyres");
    this.tv_other.setText(this.other);
    this.tv_battery.setText(this.battery);
    this.tv_suspension.setText(this.suspension);
    this.tv_engine.setText(this.engine);
    this.tv_steering.setText(this.steering);
    this.tv_tyres.setText(this.tyre);
    new LongOperation().execute("");

    tv_requsest.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        requestMaterial();
      }
    });
  }

  private class LongOperation extends AsyncTask<String, Void, String> {
    private LongOperation() {}

    protected String doInBackground(String... param1VarArgs)
    { requestType();

      return null; }

    protected void onPostExecute(String param1String)
    {
      tv_other_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
      tv_battery_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
      tv_suspension_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
      tv_engine_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
      tv_steering_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
      tv_tyres_material.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> param2AdapterView, View param2View, int param2Int, long param2Long) { if (param2Int != 0) {
          Result result = (Result)StartActivity.this.resultList.get(param2Int - 1);
          StartActivity.this.material_id_other = result.getMeterials_id();
        }  }

        public void onNothingSelected(AdapterView<?> param2AdapterView) {}
      });
    }

    protected void onPreExecute() {
      StartActivity startActivity = StartActivity.this;
      startActivity.progressDialog = new ProgressDialog(startActivity, 2131755016);
      StartActivity.this.progressDialog.setIndeterminate(true);
      StartActivity.this.progressDialog.setMessage("Fetching...");
      StartActivity.this.progressDialog.setCancelable(false);
      StartActivity.this.progressDialog.setCanceledOnTouchOutside(false);
      StartActivity.this.progressDialog.show();
    }
  }

  private void requestType() {
    APIService service = ApiModule.getAPIService();
    Call<ResultList> call =service.getMaterialTypeList(userId, auth_token);

    call.enqueue(new Callback<ResultList>() {
      public void onFailure(Call<ResultList> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResultList> param1Call, Response<ResultList> param1Response) { try {
        StartActivity.this.progressDialog.cancel();
        boolean bool = ((ResultList)param1Response.body()).getMessage().equals("successfully fetched");
        byte b = 0;
        if (bool) {
          StartActivity.this.resultList = ((ResultList)param1Response.body()).getResponse();
          StartActivity.this.spinnerArray.add("Select");
          while (b < StartActivity.this.resultList.size()) {
            List list = StartActivity.this.spinnerArray;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(((Result)StartActivity.this.resultList.get(b)).getMeterials_name());
            stringBuilder.append(" ");
            stringBuilder.append(((Result)StartActivity.this.resultList.get(b)).getMeterials_type_name());
            list.add(stringBuilder.toString());
            b++;
          }
          ArrayAdapter arrayAdapter = new ArrayAdapter(StartActivity.this, android.R.layout.simple_spinner_item, spinnerArray);
          arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
          StartActivity.this.tv_other_material.setAdapter(arrayAdapter);
          StartActivity.this.tv_battery_material.setAdapter(arrayAdapter);
          StartActivity.this.tv_suspension_material.setAdapter(arrayAdapter);
          StartActivity.this.tv_engine_material.setAdapter(arrayAdapter);
          StartActivity.this.tv_steering_material.setAdapter(arrayAdapter);
          StartActivity.this.tv_tyres_material.setAdapter(arrayAdapter);
          return;
        }
        Toast.makeText(StartActivity.this.getApplicationContext(), "No data in this category", Toast.LENGTH_LONG).show();
        return;
      } catch (Exception e) {
        e.printStackTrace();
        Log.e("Exception ", e.getMessage());
        return;
      }  }
    });
  }



  private void requestMaterial() {

    final ProgressDialog progressDialog = new ProgressDialog(StartActivity.this);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Requesting...");
    progressDialog.show();
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("user_id", Integer.valueOf(this.userId));
    jsonObject.addProperty("job_id", this.job_id);
    String str6 = this.tv_other_status.getSelectedItem().toString();
    String str5 = this.tv_battery_status.getSelectedItem().toString();
    String str4 = this.tv_suspension_status.getSelectedItem().toString();
    String str3 = this.tv_engine_status.getSelectedItem().toString();
    String str2 = this.tv_steering_status.getSelectedItem().toString();
    String str1 = this.tv_tyres_status.getSelectedItem().toString();
    if (str6.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.quantity_other));
      jsonObject.add("others", jsonObject1);
    } else {
      str6 = this.tv_other_quantity.getText().toString();
      if (str6.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.quantity_other = Integer.parseInt(str6);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_other);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.quantity_other));
      jsonObject.add("others", jsonObject1);
    }
    if (str5.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.battery_quantity));
      jsonObject.add("battery", jsonObject1);
    } else {
      str5 = this.tv_battery_quantity.getText().toString();
      if (str5.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.battery_quantity = Integer.parseInt(str5);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_battery);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.battery_quantity));
      jsonObject.add("battery", jsonObject1);
    }
    if (str4.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.suspension_quantity));
      jsonObject.add("suspension", jsonObject1);
    } else {
      str4 = this.tv_suspension_quantity.getText().toString();
      if (str4.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.suspension_quantity = Integer.parseInt(str4);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_suspension);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.suspension_quantity));
      jsonObject.add("suspension", jsonObject1);
    }
    if (str3.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.engine_quantity));
      jsonObject.add("engine", jsonObject1);
    } else {
      str3 = this.tv_engine_quantity.getText().toString();
      if (str3.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.engine_quantity = Integer.parseInt(str3);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_engine);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.engine_quantity));
      jsonObject.add("engine", jsonObject1);
    }
    if (str2.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.steering_quantity));
      jsonObject.add("steering", jsonObject1);
    } else {
      str2 = this.tv_steering_quantity.getText().toString();
      if (str2.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.steering_quantity = Integer.parseInt(str2);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_steering);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.steering_quantity));
      jsonObject.add("steering", jsonObject1);
    }
    if (str1.equals("No Repair Need")) {
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", "");
      jsonObject1.addProperty("quantity", Integer.valueOf(this.tyre_quantity));
      jsonObject.add("tyres", jsonObject1);
    } else {
      str1 = this.tv_tyres_quantity.getText().toString();
      if (str1.equals("")) {
        Toast.makeText(this, "Enter other quantity", Toast.LENGTH_LONG).show();
      } else {
        this.tyre_quantity = Integer.parseInt(str1);
      }
      JsonObject jsonObject1 = new JsonObject();
      jsonObject1.addProperty("material_id", this.material_id_tyre);
      jsonObject1.addProperty("quantity", Integer.valueOf(this.tyre_quantity));
      jsonObject.add("tyres", jsonObject1);
    }
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("json object");
    stringBuilder.append(jsonObject);
    Log.e("777777 ", stringBuilder.toString());
    ApiModule.getAPIService().request_material(jsonObject, this.auth_token).enqueue(new Callback<ResponseLogin>() {
      public void onFailure(Call<ResponseLogin> param1Call, Throwable param1Throwable) { progressDialog.dismiss();
        Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseLogin> param1Call, Response<ResponseLogin> param1Response) { progressDialog.dismiss();
        try {
          StartActivity startActivity = StartActivity.this;
          StringBuilder stringBuilder = new StringBuilder();
          stringBuilder.append("hiii ");
          stringBuilder.append(((ResponseLogin)param1Response.body()).getMessage());
          Toast.makeText(startActivity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
          if (((ResponseLogin)param1Response.body()).getStatus()) {
            Toast.makeText(StartActivity.this, ((ResponseLogin)param1Response.body()).getMessage(), Toast.LENGTH_LONG).show();
            Intent intent = new Intent(StartActivity.this.getApplicationContext(), HomeActivity.class);
            StartActivity.this.startActivity(intent);
            StartActivity.this.finish();
            return;
          }
          Toast.makeText(StartActivity.this.getApplicationContext(), "No data in this category", Toast.LENGTH_LONG).show();
          return;
        } catch (Exception e) {
          e.printStackTrace();
          Log.e("Exception ", e.getMessage());
          return;
        }  }
    });
  }
  class Other implements AdapterView.OnItemSelectedListener {

    public void onItemSelected(AdapterView<?> param1AdapterView, View param1View, int param1Int, long param1Long) { if (param1Int != 0) {
      Result result = (Result)StartActivity.this.resultList.get(param1Int - 1);
      StartActivity.this.material_id_other = result.getMeterials_id();
    }  }

    public void onNothingSelected(AdapterView<?> param1AdapterView) {}
  }
}
