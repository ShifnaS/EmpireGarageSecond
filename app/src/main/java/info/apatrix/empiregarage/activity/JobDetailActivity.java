package info.apatrix.empiregarage.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.adapter.ReportDefectAdapter;
import info.apatrix.empiregarage.adapter.ServicePackageAdapter;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.Car;
import info.apatrix.empiregarage.model.Customer;
import info.apatrix.empiregarage.model.IssuedInventerayList;
import info.apatrix.empiregarage.model.ReportDefects;
import info.apatrix.empiregarage.model.ResponseLogin;
import info.apatrix.empiregarage.model.ResponseService;
import info.apatrix.empiregarage.model.Responses;
import info.apatrix.empiregarage.model.ServicePackages;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.NetworkUtil;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobDetailActivity extends AppCompatActivity {

  @BindView(R.id.name)
  TextView tv_name;
  @BindView(R.id.mobile)
  TextView tv_mobile;
  @BindView(R.id.email)
  TextView tv_email;
  @BindView(R.id.make)
  TextView tv_make;
  @BindView(R.id.model)
  TextView tv_model;
  @BindView(R.id.plateNo)
  TextView tv_plateNo;
  @BindView(R.id.color)
  TextView tv_color;
  @BindView(R.id.mileage)
  TextView tv_mileage;

  /*@BindView(R.id.service)
  RecyclerView tv_service;
*/


  @BindView(R.id.start)
  Button bt_start;
  @BindView(R.id.id)
  TextView tv_job_id;
  String job_id="",auth_token,actionType;
  int user_id = 0;

  int flag = 0;
  ArrayList<IssuedInventerayList> inventory = new ArrayList();

  ServicePackageAdapter mAdapter;

  ReportDefectAdapter nAdapter;

  RequestedInventoryAdapter pAdapter;

  ProgressDialog progressDialog;

  RecyclerView recyclerView;

  RecyclerView recyclerView_defect;

  RecyclerView recyclerView_inventory;
  @BindView(R.id.isssued_inventory)
  LinearLayout isssued_inventory;
  ArrayList<ReportDefects> reportDefects = new ArrayList();

  ArrayList<ServicePackages> servicePackages = new ArrayList();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_job_detail);
    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    ButterKnife.bind(this);

    this.user_id = SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue("userId");

    job_id= SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_JOB_ID);
    auth_token=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
    actionType=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_ACTION_TYPE);
    // auth_token="123456789";


    Intent intent = getIntent();
    this.flag = intent.getIntExtra("flag", 0);
    if (this.flag == 1)
      this.isssued_inventory.setVisibility(View.VISIBLE);
    this.progressDialog = new ProgressDialog(this);
    this.progressDialog.setIndeterminate(true);
    this.progressDialog.setMessage("Fetching...");
    this.progressDialog.setCancelable(false);
    this.progressDialog.setCanceledOnTouchOutside(false);
    String str = intent.getStringExtra("data").toString();
    ((TextView)findViewById(R.id.text)).setText(str);


    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    this.recyclerView_defect = (RecyclerView)findViewById(R.id.defects);
    this.recyclerView = (RecyclerView)findViewById(R.id.service);
    this.recyclerView_inventory = (RecyclerView)findViewById(R.id.inventory);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    this.recyclerView.setLayoutManager(linearLayoutManager);
    this.recyclerView.setItemAnimator(new DefaultItemAnimator());
    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    this.recyclerView_defect.setLayoutManager(linearLayoutManager);
    this.recyclerView_defect.setItemAnimator(new DefaultItemAnimator());
    linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
    this.recyclerView_inventory.setLayoutManager(linearLayoutManager);
    this.recyclerView_inventory.setItemAnimator(new DefaultItemAnimator());


    if (this.actionType.equals("1")) {
      this.bt_start.setVisibility(View.VISIBLE);
      this.bt_start.setOnClickListener(new View.OnClickListener() {
        public void onClick(View param1View)
        {
          AlertDialog.Builder builder=new AlertDialog.Builder(JobDetailActivity.this);

          builder.setMessage("Are you sure you want to start this Service?")
                  .setCancelable(false)
                  .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    //  finish();
                     startOpenService();
                    }
                  })
                  .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                      //  Action for 'NO' Button
                      dialog.cancel();

                    }
                  });
          //Creating dialog box
          AlertDialog alert = builder.create();
          //Setting the title manually
          alert.setTitle("Confirmation Dialog");
          alert.show();


        }
      });
    }

    else if (this.actionType.equals("2")) {
      this.bt_start.setVisibility(View.VISIBLE);
      this.bt_start.setText("Opened");
      this.bt_start.setOnClickListener(new View.OnClickListener() {
        public void onClick(View param1View) {
          Intent intent = new Intent(JobDetailActivity.this.getApplicationContext(), StartActivity.class);
          intent.putExtra("Others", ((ReportDefects)JobDetailActivity.this.reportDefects.get(0)).getRemarks());
          intent.putExtra("Battery", ((ReportDefects)JobDetailActivity.this.reportDefects.get(1)).getRemarks());
          intent.putExtra("Suspension", ((ReportDefects)JobDetailActivity.this.reportDefects.get(2)).getRemarks());
          intent.putExtra("Engine", ((ReportDefects)JobDetailActivity.this.reportDefects.get(3)).getRemarks());
          intent.putExtra("Steering", ((ReportDefects)JobDetailActivity.this.reportDefects.get(4)).getRemarks());
          intent.putExtra("Tyres", ((ReportDefects)JobDetailActivity.this.reportDefects.get(5)).getRemarks());
          JobDetailActivity.this.startActivity(intent);
        }
      });
    }
    else if(actionType.equals("4"))
    {
      bt_start.setVisibility(View.VISIBLE);
      bt_start.setText("Complete Service");
      bt_start.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          completeService();
          // Intent i=new Intent(getApplicationContext(),StartActivity.class);
          //  startActivity(i);
        }
      });
    }

    if(NetworkUtil.isOnline())
    {
      getData();


    }
    else
    {
      Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
    }

  }


  private void getData() {
    this.progressDialog.show();
    APIService service = ApiModule.getAPIService();
    Call<ResponseService> call =null;
    int i = this.flag;
    if (i == 1) {
      call = service.getServiceDetail(this.job_id, this.auth_token);
    } else if (i == 2) {
      call = service.getRequestedServiceDetail(this.job_id, this.auth_token);
    } else if (i == 3) {
      call = service.getDrewOutServiceDetail(this.job_id, this.auth_token);
    } else if (i == 4) {
      call = service.getCompletedServiceDetail(this.job_id, this.auth_token);
    } else {
      call = null;
    }
    call.enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { JobDetailActivity.this.progressDialog.dismiss();
        try {
          if (param1Response.body() != null && param1Response.isSuccessful() && ((ResponseService)param1Response.body()).getMessage().equals("successfully fetched")) {
            Responses responses = ((ResponseService)param1Response.body()).getResponse();
            responses.getId();
            Customer customer = responses.getCustomer();
            StringBuilder stringBuilder1 = new StringBuilder();
            stringBuilder1.append("REsponse ");
            stringBuilder1.append(customer.getEmail());
            Log.e("1111111111", stringBuilder1.toString());
            String str2 = customer.getName();
            String str3 = customer.getEmail();
            String str4 = customer.getMobile();
            TextView textView = JobDetailActivity.this.tv_job_id;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("JOB_ID: #");
            stringBuilder2.append(JobDetailActivity.this.job_id);
            textView.setText(stringBuilder2.toString());
            JobDetailActivity.this.tv_name.setText(str2);
            JobDetailActivity.this.tv_email.setText(str3);
            JobDetailActivity.this.tv_mobile.setText(str4);
            str2 = customer.getMake();
            str3 = customer.getModel();
            str4 = customer.getColor();
            String str5 = customer.getCurrentMileage();
            String str1 = customer.getPlateNumber();
            JobDetailActivity.this.tv_make.setText(str2);
            JobDetailActivity.this.tv_model.setText(str3);
            JobDetailActivity.this.tv_color.setText(str4);
            JobDetailActivity.this.tv_mileage.setText(str5);
            JobDetailActivity.this.tv_plateNo.setText(str1);
            JobDetailActivity.this.servicePackages = responses.getServicePackages();

            JobDetailActivity.this.mAdapter = new ServicePackageAdapter(servicePackages, getApplicationContext(), 0);

            JobDetailActivity.this.recyclerView.setAdapter(JobDetailActivity.this.mAdapter);
            JobDetailActivity.this.reportDefects = responses.getReportDefects();

            JobDetailActivity.this.nAdapter = new ReportDefectAdapter(reportDefects, getApplicationContext(), 0);

            JobDetailActivity.this.recyclerView_defect.setAdapter(JobDetailActivity.this.nAdapter);

            if (JobDetailActivity.this.flag != 1) {
              if (JobDetailActivity.this.flag == 2) {
                JobDetailActivity.this.inventory = responses.getIssued_inventory_lists();
                JobDetailActivity.this.pAdapter = new RequestedInventoryAdapter(inventory, getApplicationContext(), 0);
                JobDetailActivity.this.recyclerView_inventory.setAdapter(JobDetailActivity.this.pAdapter);
                return;
              }
              JobDetailActivity.this.inventory = responses.getIssued_inventory_lists();
              JobDetailActivity.this.pAdapter = new RequestedInventoryAdapter(inventory, getApplicationContext(), 1);
              JobDetailActivity.this.recyclerView_inventory.setAdapter(JobDetailActivity.this.pAdapter);
              return;
            }
          }
        } catch (Exception e) {
          e.printStackTrace();
          Log.e("Exception ", e.getMessage());
        }  }
    });
  }


  private void startOpenService() {
    this.progressDialog.show();
    APIService aPIService = ApiModule.getAPIService();
    String str = this.job_id;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(this.user_id);
    aPIService.startOpenService(str, stringBuilder.toString(), this.auth_token).enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { try {
        JobDetailActivity.this.progressDialog.dismiss();
        if (((ResponseService)param1Response.body()).getMessage().equals("successfully start job")) {
          Intent intent = new Intent(JobDetailActivity.this.getApplicationContext(), StartActivity.class);
          intent.putExtra("Others", ((ReportDefects)JobDetailActivity.this.reportDefects.get(0)).getRemarks());
          intent.putExtra("Battery", ((ReportDefects)JobDetailActivity.this.reportDefects.get(1)).getRemarks());
          intent.putExtra("Suspension", ((ReportDefects)JobDetailActivity.this.reportDefects.get(2)).getRemarks());
          intent.putExtra("Engine", ((ReportDefects)JobDetailActivity.this.reportDefects.get(3)).getRemarks());
          intent.putExtra("Steering", ((ReportDefects)JobDetailActivity.this.reportDefects.get(4)).getRemarks());
          intent.putExtra("Tyres", ((ReportDefects)JobDetailActivity.this.reportDefects.get(5)).getRemarks());
          JobDetailActivity.this.startActivity(intent);
          return;
        }
      } catch (Exception e) {
        e.printStackTrace();
        Log.e("Exception ", e.getMessage());
      }  }
    });

  }



  private void completeService() {
    this.progressDialog.show();
    APIService aPIService = ApiModule.getAPIService();
    String str = this.job_id;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(this.user_id);
    aPIService.completeService(str, stringBuilder.toString(), this.auth_token).enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { try {
        JobDetailActivity.this.progressDialog.dismiss();
        JobDetailActivity jobDetailActivity = JobDetailActivity.this;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("");
        stringBuilder.append(((ResponseService)param1Response.body()).getMessage());
        Toast.makeText(jobDetailActivity, stringBuilder.toString(), Toast.LENGTH_LONG).show();
        JobDetailActivity.this.finish();
        return;
      } catch (Exception e) {
        e.printStackTrace();
        Log.e("Exception ", e.getMessage());
        return;
      }  }
    });
  }

  @Override
  public void onBackPressed() {
    Intent intent = new Intent(this, HomeActivity.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(intent);
    finish();
    super.onBackPressed();
  }
/*
  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    return super.onOptionsItemSelected(item);
    //Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
    Intent i=new Intent(getApplicationContext(),HomeActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
    startActivity(i);
    finish();
    return true;
  }*/
}
