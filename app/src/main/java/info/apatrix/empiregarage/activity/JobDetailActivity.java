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

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.adapter.IssuedInventoryServicePackAdapter;
import info.apatrix.empiregarage.adapter.ReportDefectAdapter;
import info.apatrix.empiregarage.adapter.ServicePackageAdapter;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.Customer;
import info.apatrix.empiregarage.model.IssuedInventerayList;
import info.apatrix.empiregarage.model.IssuedInventoryPackList;
import info.apatrix.empiregarage.model.ReportDefects;
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
  ArrayList<IssuedInventoryPackList> issuedInventoryServicePackagesLists = new ArrayList();

  ServicePackageAdapter mAdapter;

  ReportDefectAdapter nAdapter;

  RequestedInventoryAdapter pAdapter;
  IssuedInventoryServicePackAdapter requestAdapter;
  ProgressDialog progressDialog;

  RecyclerView recyclerView;

  RecyclerView recyclerView_defect;

  RecyclerView recyclerView_inventory;

  RecyclerView recyclerView_inventory_service_pack;


  @BindView(R.id.isssued_inventory)
  LinearLayout isssued_inventory;
  @BindView(R.id.isssued_inventory_service_pack)
  LinearLayout isssued_inventory_service_pack;

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

    user_id = SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue("userId");

    job_id= SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_JOB_ID);
    auth_token=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
    actionType=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_ACTION_TYPE);
    // auth_token="123456789";


    Intent intent = getIntent();
    flag = intent.getIntExtra("flag", 0);
    if (flag == 1)
    {
      isssued_inventory.setVisibility(View.GONE);
      isssued_inventory_service_pack.setVisibility(View.GONE);
    }
      else
    {
      isssued_inventory.setVisibility(View.VISIBLE);
      isssued_inventory_service_pack.setVisibility(View.VISIBLE);

    }
    progressDialog = new ProgressDialog(this);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Fetching...");
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    String str = intent.getStringExtra("data").toString();
    ((TextView)findViewById(R.id.text)).setText(str);


    getSupportActionBar().setDisplayShowTitleEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    getSupportActionBar().setDisplayShowHomeEnabled(true);
    recyclerView_defect = (RecyclerView)findViewById(R.id.defects);
    recyclerView = (RecyclerView)findViewById(R.id.service);
    recyclerView_inventory = (RecyclerView)findViewById(R.id.inventory);
    recyclerView_inventory_service_pack = (RecyclerView)findViewById(R.id.inventory_service_pack);


    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView.setLayoutManager(linearLayoutManager);
    recyclerView.setItemAnimator(new DefaultItemAnimator());

    linearLayoutManager = new LinearLayoutManager(getApplicationContext());
    recyclerView_defect.setLayoutManager(linearLayoutManager);
    recyclerView_defect.setItemAnimator(new DefaultItemAnimator());

    linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView_inventory.setLayoutManager(linearLayoutManager);
    recyclerView_inventory.setItemAnimator(new DefaultItemAnimator());

    linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
    recyclerView_inventory_service_pack.setLayoutManager(linearLayoutManager);
    recyclerView_inventory_service_pack.setItemAnimator(new DefaultItemAnimator());

    if (actionType.equals("1")) {
      bt_start.setVisibility(View.VISIBLE);
      bt_start.setOnClickListener(new View.OnClickListener() {
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

    else if (actionType.equals("2")) {
      bt_start.setVisibility(View.VISIBLE);
      bt_start.setText("Opened");
      bt_start.setOnClickListener(new View.OnClickListener() {
        public void onClick(View param1View) {
          Intent intent = new Intent(getApplicationContext(), StartActivity.class);
          intent.putExtra("Others", ((ReportDefects)reportDefects.get(0)).getRemarks());
          intent.putExtra("Battery", ((ReportDefects)reportDefects.get(1)).getRemarks());
          intent.putExtra("Suspension", ((ReportDefects)reportDefects.get(2)).getRemarks());
          intent.putExtra("Engine", ((ReportDefects)reportDefects.get(3)).getRemarks());
          intent.putExtra("Steering", ((ReportDefects)reportDefects.get(4)).getRemarks());
          intent.putExtra("Tyres", ((ReportDefects)reportDefects.get(5)).getRemarks());
          startActivity(intent);
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
    progressDialog.show();
    APIService service = ApiModule.getAPIService();
    Call<ResponseService> call =null;
    int i = flag;
    if (i == 1) {
      call = service.getServiceDetail(job_id, auth_token);
    } else if (i == 2) {
      call = service.getRequestedServiceDetail(job_id, auth_token);
    } else if (i == 3) {
      call = service.getDrewOutServiceDetail(job_id, auth_token);
    } else if (i == 4) {
      call = service.getCompletedServiceDetail(job_id, auth_token);
    }
    call.enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { progressDialog.dismiss();
        try {
          if (param1Response.body() != null && param1Response.isSuccessful() && param1Response.body().getMessage().equals("successfully fetched")) {
            Responses responses = param1Response.body().getResponse();
            responses.getId();
            Customer customer = responses.getCustomer();
            Log.e("Response",new Gson().toJson(param1Response));

           // Log.e("1111111111",""+param1Response.body().getResponse());
            String str2 = customer.getName();
            String str3 = customer.getEmail();
            String str4 = customer.getMobile();
            TextView textView = tv_job_id;
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("JOB_ID: #");
            stringBuilder2.append(job_id);
            textView.setText(stringBuilder2.toString());
            tv_name.setText(str2);
            tv_email.setText(str3);
            tv_mobile.setText(str4);
            str2 = customer.getMake();
            str3 = customer.getModel();
            str4 = customer.getColor();
            String str5 = customer.getCurrentMileage();
            String str1 = customer.getPlateNumber();
            tv_make.setText(str2);
            tv_model.setText(str3);
            tv_color.setText(str4);
            tv_mileage.setText(str5);
            tv_plateNo.setText(str1);

            servicePackages = responses.getServicePackages();
            mAdapter = new ServicePackageAdapter(servicePackages, getApplicationContext(), 0);
            recyclerView.setAdapter(mAdapter);

            reportDefects = responses.getReportDefects();
            nAdapter = new ReportDefectAdapter(reportDefects, getApplicationContext(), 0);
            recyclerView_defect.setAdapter(nAdapter);


            issuedInventoryServicePackagesLists=responses.getIssuedInventoryServicePackagesLists();
            Log.e("1111111 ",""+issuedInventoryServicePackagesLists.toString());
            requestAdapter = new IssuedInventoryServicePackAdapter(issuedInventoryServicePackagesLists, getApplicationContext());
            recyclerView_inventory_service_pack.setAdapter(requestAdapter);

            if (flag != 1) {
              if (flag == 2) {
                inventory = responses.getIssued_inventory_lists();
                pAdapter = new RequestedInventoryAdapter(inventory, getApplicationContext(), 0);
                recyclerView_inventory.setAdapter(pAdapter);
              }
              else
              {
                inventory = responses.getIssued_inventory_lists();
                pAdapter = new RequestedInventoryAdapter(inventory, getApplicationContext(), 1);
                recyclerView_inventory.setAdapter(pAdapter);

              }
              }
          }
        } catch (Exception e) {
          e.printStackTrace();
          Log.e("Exception ", e.getMessage());
        }  }
    });
  }


  private void startOpenService() {
    progressDialog.show();
    APIService aPIService = ApiModule.getAPIService();
    String str = job_id;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(user_id);
    aPIService.startOpenService(str, stringBuilder.toString(), auth_token).enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { try {
        progressDialog.dismiss();
        if (((ResponseService)param1Response.body()).getMessage().equals("successfully start job")) {
          Intent intent = new Intent(getApplicationContext(), StartActivity.class);
          intent.putExtra("Others", ((ReportDefects)reportDefects.get(0)).getRemarks());
          intent.putExtra("Battery", ((ReportDefects)reportDefects.get(1)).getRemarks());
          intent.putExtra("Suspension", ((ReportDefects)reportDefects.get(2)).getRemarks());
          intent.putExtra("Engine", ((ReportDefects)reportDefects.get(3)).getRemarks());
          intent.putExtra("Steering", ((ReportDefects)reportDefects.get(4)).getRemarks());
          intent.putExtra("Tyres", ((ReportDefects)reportDefects.get(5)).getRemarks());
          startActivity(intent);
        }
      } catch (Exception e) {
        e.printStackTrace();
        Log.e("Exception ", e.getMessage());
      }  }
    });

  }



  private void completeService() {
    progressDialog.show();
    APIService aPIService = ApiModule.getAPIService();
    String str = job_id;
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("");
    stringBuilder.append(user_id);
    aPIService.completeService(str, stringBuilder.toString(), auth_token).enqueue(new Callback<ResponseService>() {
      public void onFailure(Call<ResponseService> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
        Log.e("Failure ", param1Throwable.getMessage()); }

      public void onResponse(Call<ResponseService> param1Call, Response<ResponseService> param1Response) { try {
        progressDialog.dismiss();

       // Toast.makeText(getApplicationContext(),""+param1Response.body(), Toast.LENGTH_LONG).show();
        finish();
      } catch (Exception e) {
        e.printStackTrace();
        Log.e("Exception ", e.getMessage());
      }  }
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
