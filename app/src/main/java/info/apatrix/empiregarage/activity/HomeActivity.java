package info.apatrix.empiregarage.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nex3z.notificationbadge.NotificationBadge;

import butterknife.OnClick;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.adapter.CarAdapter;
import info.apatrix.empiregarage.adapter.NotificationAdapter;
import info.apatrix.empiregarage.adapter.RecyclerTouchListener;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.APIUrl;
import info.apatrix.empiregarage.api.ApiModule;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.apatrix.empiregarage.fragment.CompletedFragment;
import info.apatrix.empiregarage.fragment.DewOutFragment;
import info.apatrix.empiregarage.fragment.OpenFragment;
import info.apatrix.empiregarage.fragment.OpenedFragment;
import info.apatrix.empiregarage.fragment.RequestedFragment;
import info.apatrix.empiregarage.model.ResponseNotification;
import info.apatrix.empiregarage.model.Result;
import info.apatrix.empiregarage.model.ResultList;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.NetworkUtil;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

  @BindView(R.id.service)
  TextView tv_service;
  @BindView(R.id.opened)
  ImageView _opened;
  @BindView(R.id.requested)
  ImageView _requested;
  @BindView(R.id.drewout)
  ImageView _drewout;
  @BindView(R.id.completed)
  ImageView _completed;

  @BindView(R.id.filter)
  ImageView filter;
  @BindView(R.id.mymenu)
  ImageView menus;
  @BindView(R.id.bell)
  ImageView notification;
  @BindView(R.id.toolbar)
  Toolbar topToolBar;

  @BindView(R.id.badge)
  NotificationBadge badge;

  String service="";
  int rollId,userId;
  String token="";
  ProgressDialog progressDialog;


  Retrofit retrofit;
  APIService apiService;
  Disposable disposable;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_NO_TITLE);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);
    setContentView(R.layout.activity_home);
    setSupportActionBar(topToolBar);
    ButterKnife.bind(this);
    _opened.setOnClickListener(this);
    _requested.setOnClickListener(this);
    _drewout.setOnClickListener(this);
    _completed.setOnClickListener(this);
    menus.setOnClickListener(this);
    filter.setOnClickListener(this);
    progressDialog = new ProgressDialog(HomeActivity.this,R.style.AppTheme_Dark_Dialog);
    progressDialog.setIndeterminate(true);
    progressDialog.setMessage("Fetching...");
    progressDialog.setCancelable(false);
    progressDialog.setCanceledOnTouchOutside(false);
    rollId= SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_ROLE_ID);
    userId=SharedPreferenceUtils.getInstance(getApplicationContext()).getIntValue(Constants.KEY_USER_ID);
    //token="123456789";
    token=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);

    service="opened";
    _opened.setImageResource(R.drawable.ic_opened_services_active);
    loadHomeFragment();


    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build();

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();


    retrofit = new Retrofit.Builder()
            .baseUrl(APIUrl.BASE_URL)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    apiService = retrofit.create(APIService.class);


    disposable = Observable.interval(1000, 5000,
            TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::callJokesEndpoint, this::onError);


    notification.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Intent intent=new Intent(getApplicationContext(),NotificationActivity.class);
        startActivity(intent);
      }
    });

  }

  private void loadHomeFragment() {
    OpenedFragment openedFragment = new OpenedFragment();
    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    fragmentTransaction.replace(R.id.output, openedFragment);
    fragmentTransaction.commit();
  }
  @Override
  public void onClick(View v) {
    switch (v.getId()) {

      case R.id.opened:
        service="opened";
        topToolBar.setTitle("Opened Services");
        tv_service.setText("Opened Services");
        _opened.setImageResource(R.drawable.ic_opened_services_active);
        _requested.setImageResource(R.drawable.ic_requested_inventory);
        _drewout.setImageResource(R.drawable.ic_drew_out_inventory);
        _completed.setImageResource(R.drawable.ic_completed_services);

        if(NetworkUtil.isOnline())
        {
            //Toast.makeText(this, "hii", Toast.LENGTH_SHORT).show();

          OpenedFragment requestedFragment = new OpenedFragment();
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.output, requestedFragment);
          fragmentTransaction.commit();
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

        break;

      case R.id.requested:
        // do your code
        service="requested";
        topToolBar.setTitle("Requested Inventory");
        tv_service.setText("Requested Inventory");
        _requested.setImageResource(R.drawable.ic_requested_inventory_active);
        _opened.setImageResource(R.drawable.ic_opened_services);
        _drewout.setImageResource(R.drawable.ic_drew_out_inventory);
        _completed.setImageResource(R.drawable.ic_completed_services);
        if(NetworkUtil.isOnline())
        {
          RequestedFragment requestedFragment = new RequestedFragment();
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.output, requestedFragment);
          fragmentTransaction.commit();
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

        break;

      case R.id.drewout:
        // do your code
        service="dewout";
        topToolBar.setTitle("DrewOut Inventory");
        tv_service.setText("DrewOut Inventory");
        _drewout.setImageResource(R.drawable.ic_ic_drew_out_inventory_active);
        _opened.setImageResource(R.drawable.ic_opened_services);
        _requested.setImageResource(R.drawable.ic_requested_inventory);
        _completed.setImageResource(R.drawable.ic_completed_services);
        if(NetworkUtil.isOnline())
        {
          DewOutFragment dewOutFragment = new DewOutFragment();
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.output, dewOutFragment);
          fragmentTransaction.commit();
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

        break;
      case R.id.completed:
        // do your code
        service="closed";
        topToolBar.setTitle("Completed Services");
        tv_service.setText("Completed Services");
        _completed.setImageResource(R.drawable.ic_completed_services_active);
        _opened.setImageResource(R.drawable.ic_opened_services);
        _requested.setImageResource(R.drawable.ic_requested_inventory);
        _drewout.setImageResource(R.drawable.ic_drew_out_inventory);
        if(NetworkUtil.isOnline())
        {
          CompletedFragment completedFragment = new CompletedFragment();
          FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
          fragmentTransaction.replace(R.id.output, completedFragment);
          fragmentTransaction.commit();
        }
        else
        {
          Toast.makeText(getApplicationContext(), "Please check your network", Toast.LENGTH_SHORT).show();
        }

        break;

      case R.id.mymenu:
       // Toast.makeText(this, "fghfghfh", Toast.LENGTH_SHORT).show();
        setOption();
        break;
      default:
        break;
    }

  }

  private void setOption() {
    //Creating the instance of PopupMenu
    Context wrapper = new ContextThemeWrapper(HomeActivity.this, R.style.PopUpTheme);

    PopupMenu popup = new PopupMenu(wrapper, menus);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
      popup.setGravity(Gravity.END);
    }

    //Inflating the Popup using xml file
    popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());

    //registering popup with OnMenuItemClickListener
    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
      public boolean onMenuItemClick(MenuItem item) {
        Intent i;
       // Toast.makeText(HomeActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getTitle().toString())
        {

          case "Profile" :
            i=new Intent(getApplicationContext(),ProfileActivity.class);
            startActivity(i);
            break;
          case "Change Password" :
            i=new Intent(getApplicationContext(),ChangePassword.class);
            startActivity(i);
            break;
          case "Logout" :
            // showBottomSheetDialog();
            String auth=SharedPreferenceUtils.getInstance(getApplicationContext()).getStringValue(Constants.KEY_AUTH_TOKEN);
            SharedPreferenceUtils.getInstance(getApplicationContext()).clear();
            SharedPreferenceUtils.getInstance(getApplicationContext()).setStringValue(Constants.KEY_AUTH_TOKEN,auth);
            i=new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(i);
            finish();
            break;
          default:
            break;
        }
        return true;
      }
    });

    popup.show();//showing popup menu
  }
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);

    return true;

  }

  @Override
  public void onRefresh() {

  }

  @Override
  protected void onResume() {
    super.onResume();

    if (disposable.isDisposed()) {
      disposable = Observable.interval(1000, 5000,
              TimeUnit.MILLISECONDS)
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(this::callJokesEndpoint, this::onError);
    }
  }

  private void callJokesEndpoint(Long aLong) {


    Observable<ResponseNotification> observable = apiService.getRandomNotification(userId,token);
    observable.subscribeOn(Schedulers.newThread()).
            observeOn(AndroidSchedulers.mainThread())
            .map(result -> result.getCount())
            .subscribe(this::handleResults, this::handleError);
  }

  private void onError(Throwable throwable) {
    Toast.makeText(this, "OnError in Observable Timer",
            Toast.LENGTH_LONG).show();
  }


  private void handleResults(String data) {


    if (!TextUtils.isEmpty(data)) {
     // Toast.makeText(this, "hiii "+data, Toast.LENGTH_SHORT).show();
      int count=Integer.parseInt(data);
      if(count>0) {
          badge.setNumber(count);
      }
      else
      {
          badge.setNumber(0);

      }
     // textView.setText(joke);


    } else {
      Toast.makeText(this, "NO RESULTS FOUND",
              Toast.LENGTH_LONG).show();
    }
  }

  private void handleError(Throwable t) {

    //Add your error here.
  }

  @Override
  protected void onPause() {
    super.onPause();

    disposable.dispose();
  }






}
