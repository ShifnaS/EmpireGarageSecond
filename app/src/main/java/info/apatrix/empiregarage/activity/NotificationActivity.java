package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.adapter.NotificationAdapter;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.Notification;
import info.apatrix.empiregarage.model.ResponseNotification;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    String token = "";
    int userId;
    NotificationAdapter mAdapter;
    ProgressDialog progressDialog;
    ArrayList<Notification> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        userId = SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue("userId");
        token = SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue("authToken");
        Log.e("userId ", "userId "+userId);
        Log.e("token ", "token "+token);

         progressDialog = new ProgressDialog(NotificationActivity.this,  R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        prepareNotificationData();
    }

    private void prepareNotificationData() {
        progressDialog.show();
        ApiModule.getAPIService().getNotification(userId, token).enqueue(new Callback<ResponseNotification>() {
            public void onFailure(Call<ResponseNotification> param1Call, Throwable param1Throwable) {
                Log.e("MyTag", "requestFailed", param1Throwable);
                Log.e("Failure ", param1Throwable.getMessage()); }

            public void onResponse(Call<ResponseNotification> param1Call, Response<ResponseNotification> param1Response) {
                progressDialog.dismiss();
                ResponseNotification responseNotification=param1Response.body();
                Log.e("Response", ""+responseNotification.toString() );

              ///  Toast.makeText(NotificationActivity.this, "hiiii "+param1Response.body().getMessage(), Toast.LENGTH_SHORT).show();
                try {

                    if (param1Response.body().getMessage().equals("successfully fetched")) {
                        notificationList = param1Response.body().getResponse();
                        mAdapter = new NotificationAdapter(notificationList, getApplicationContext());
                        recyclerView.setAdapter(mAdapter);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "No data in this category", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception ", e.getMessage());
                }  }
        });
    }
}
