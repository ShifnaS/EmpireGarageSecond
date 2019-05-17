package info.apatrix.empiregarage.fragment;

import  info.apatrix.empiregarage.R;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import info.apatrix.empiregarage.activity.JobDetailActivity;
import info.apatrix.empiregarage.adapter.CarAdapter;
import info.apatrix.empiregarage.adapter.RecyclerTouchListener;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.Result;
import info.apatrix.empiregarage.model.ResultList;
import info.apatrix.empiregarage.utils.NetworkUtil;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DewOutFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    List<Result> carList = new ArrayList();

    CarAdapter mAdapter;

    SwipeRefreshLayout mSwipeRefreshLayout;

    ProgressDialog progressDialog;

    RecyclerView recyclerView;

    int rollId;

    String service = "";

    String token = "";

    int userId;

    private void prepareCarData(String paramString) {
        mSwipeRefreshLayout.setRefreshing(false);
        progressDialog.show();
        ApiModule.getAPIService().getDewOutCarList(rollId,userId,token).enqueue(new Callback<ResultList>() {
            public void onFailure(Call<ResultList> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
                Log.e("Failure ", param1Throwable.getMessage()); }

            public void onResponse(Call<ResultList> param1Call, Response<ResultList> param1Response) { mSwipeRefreshLayout.setRefreshing(false);
                progressDialog.dismiss();
                try {
                    Log.e("Failure ", "response "+param1Response.body().getMessage());

                    if (param1Response.body().getMessage().equals("successfully fetched")) {
                        carList = param1Response.body().getResponse();
                        mAdapter = new CarAdapter(carList, getActivity());
                        recyclerView.setAdapter(mAdapter);
                    }
                    else
                    {
                        Toast.makeText(getActivity(), "No data in this category", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("Exception ", e.getMessage());
                }  }
        });
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),recyclerView, new RecyclerTouchListener.ClickListener() {
            public void onClick(View param1View, int param1Int) {
                Result result = carList.get(param1Int);
                SharedPreferenceUtils.getInstance(getContext()).setStringValue("jobId", result.getId());
                SharedPreferenceUtils.getInstance(getContext()).setStringValue("actiontype", result.getActionType());
                Intent intent = new Intent(getActivity(), JobDetailActivity.class);
                intent.putExtra("flag", 3);
                intent.putExtra("data", "DrewOut Inventory");
                startActivity(intent);
            }

            public void onLongClick(View param1View, int param1Int) {}
        }));
    }

    public View onCreateView(LayoutInflater paramLayoutInflater, ViewGroup paramViewGroup, Bundle paramBundle) {
        View view = paramLayoutInflater.inflate(R.layout.fragment_services, paramViewGroup, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        mSwipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipe_container);
        rollId = SharedPreferenceUtils.getInstance(getContext()).getIntValue("roleId");
        userId = SharedPreferenceUtils.getInstance(getContext()).getIntValue("userId");
        token = SharedPreferenceUtils.getInstance(getContext()).getStringValue("authToken");
        progressDialog = new ProgressDialog(getContext(), R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Fetching...");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        service = "dewout";
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        mSwipeRefreshLayout.post(new Runnable() {
            public void run() { mSwipeRefreshLayout.setRefreshing(true);
                if (NetworkUtil.isOnline().booleanValue()) {
                    prepareCarData(service);
                }
                else
                {
                    mSwipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
                }
            }

        });
        if (NetworkUtil.isOnline().booleanValue()) {
            prepareCarData(service);
        }
        else
        {
            Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();

        }
        return view;
    }

    public void onRefresh() {
        if (NetworkUtil.isOnline().booleanValue()) {
            prepareCarData(service);
        }
        else
        {
            mSwipeRefreshLayout.setRefreshing(false);
            Toast.makeText(getActivity(), "Please check your network", Toast.LENGTH_SHORT).show();
        }
    }
}
