package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.activity.StartActivity;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.model.Result;
import info.apatrix.empiregarage.model.ResultList;
import info.apatrix.empiregarage.model.ServicePackagesTask;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePackTaskRequestAdapter extends RecyclerView.Adapter<ServicePackTaskRequestAdapter.MyViewHolder> {
    ArrayList<Result> resultList = new ArrayList();
    List<String> spinnerArray = new ArrayList();
    List<ServicePackagesTask> carList;
    Context context;

    public ServicePackTaskRequestAdapter(List<ServicePackagesTask> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_service_pack_request, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ServicePackagesTask issuedInventerayList = carList.get(i);
       // Log.e("111111","List "+i+": "+issuedInventerayList.toString());

        Log.e("111111","Task name "+issuedInventerayList.getTask_name());
        Log.e("111111","Status "+issuedInventerayList.getTask_name());

        requestType(myViewHolder);

        myViewHolder.tv_task_name.setText(issuedInventerayList.getTask_name());
        myViewHolder.tv_package_name.setText(issuedInventerayList.getPackage_name());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_task_name, tv_package_name;
        EditText tv_quantity;
        Spinner sp_status,sp_request;



        public MyViewHolder(View param1View) {
            super(param1View);


            tv_quantity =  param1View.findViewById(R.id.quantity);
            tv_task_name = (TextView) param1View.findViewById(R.id.task_name);
            tv_package_name = (TextView) param1View.findViewById(R.id.pack_name);
            sp_request =  param1View.findViewById(R.id.request);
            sp_status =  param1View.findViewById(R.id.status);
        }

    }


    private void requestType(final MyViewHolder myViewHolder) {


        String auth_token= SharedPreferenceUtils.getInstance(context).getStringValue(Constants.KEY_AUTH_TOKEN);
        final int userId=SharedPreferenceUtils.getInstance(context).getIntValue(Constants.KEY_USER_ID);
        APIService service = ApiModule.getAPIService();
        Call<ResultList> call =service.getMaterialTypeList(userId, auth_token);

        call.enqueue(new Callback<ResultList>() {
            public void onFailure(Call<ResultList> param1Call, Throwable param1Throwable) { Log.e("MyTag", "requestFailed", param1Throwable);
                Log.e("Failure ", param1Throwable.getMessage()); }

            public void onResponse(Call<ResultList> param1Call, Response<ResultList> param1Response) { try {
               // progressDialog.cancel();
                boolean bool = ((ResultList)param1Response.body()).getMessage().equals("successfully fetched");
                byte b = 0;
                if (bool) {
                    resultList = ((ResultList)param1Response.body()).getResponse();
                    spinnerArray.add("Select");
                    while (b < resultList.size()) {
                        List list = spinnerArray;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(resultList.get(b).getMeterials_name());
                        stringBuilder.append(" ");
                        stringBuilder.append(resultList.get(b).getMeterials_type_name());
                        list.add(stringBuilder.toString());
                        b++;
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerArray);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myViewHolder.sp_request.setAdapter(arrayAdapter);

                    return;
                }
                Toast.makeText(context, "No data in this category", Toast.LENGTH_LONG).show();
                return;
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception ", e.getMessage());
                return;
            }  }
        });
    }
}
