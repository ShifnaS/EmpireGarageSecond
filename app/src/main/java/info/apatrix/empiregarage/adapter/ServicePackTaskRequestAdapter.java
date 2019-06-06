package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.activity.StartActivity;
import info.apatrix.empiregarage.api.APIService;
import info.apatrix.empiregarage.api.ApiModule;
import info.apatrix.empiregarage.db.DBManager;
import info.apatrix.empiregarage.model.RequestMaterial;
import info.apatrix.empiregarage.model.Result;
import info.apatrix.empiregarage.model.ResultList;
import info.apatrix.empiregarage.model.ServicePackagesTask;
import info.apatrix.empiregarage.utils.Constants;
import info.apatrix.empiregarage.utils.OnItemSelectListner;
import info.apatrix.empiregarage.utils.SharedPreferenceUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ServicePackTaskRequestAdapter extends RecyclerView.Adapter<ServicePackTaskRequestAdapter.MyViewHolder> implements  StartActivity.MyListner {
    ArrayList<Result> resultList = new ArrayList();
    ArrayList<String> idList = new ArrayList();

    List<ServicePackagesTask> carList;
    ArrayList<ServicePackagesTask> servicePackagesTasks=new ArrayList<ServicePackagesTask>();
    OnItemSelectListner onItemSelectListner;
    ServicePackagesTask obj;
    Context context;
    String  quantity="";

    private DBManager dbManager;

    public ServicePackTaskRequestAdapter(List<ServicePackagesTask> carList, Context context, OnItemSelectListner onItemSelectListner) {
        this.carList = carList;
        this.context = context;
        this.onItemSelectListner=onItemSelectListner;

        dbManager = new DBManager(context);
        dbManager.open();
    }

    public ServicePackTaskRequestAdapter() {

    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_service_pack_request, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
        final int[] minteger = new int[1];

        final ServicePackagesTask issuedInventerayList = carList.get(i);
       // Log.e("111111","List "+i+": "+issuedInventerayList.toString());

        Log.e("111111","Task name "+issuedInventerayList.getTask_name());
        Log.e("111111","Status "+issuedInventerayList.getTask_name());
        requestType(myViewHolder);

        myViewHolder.tv_task_name.setText(issuedInventerayList.getTask_name());
        myViewHolder.tv_package_name.setText(issuedInventerayList.getPackage_name());

        myViewHolder.sp_status.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        myViewHolder.sp_request.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                obj=new ServicePackagesTask();

                String item = parent.getItemAtPosition(position).toString();
                String pack_id=issuedInventerayList.getTask_id();
                String idd=idList.get(position);
               // onItemSelectListner.getMaterialID(meterials_id);
               // obj.setMaterial_id(meterials_id);
              //  servicePackagesTasks.add(obj);
                long res=dbManager.insertMaterial(idd,pack_id);
              //  Toast.makeText(context, "item "+item, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myViewHolder.bt_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj=new ServicePackagesTask();

                minteger[0] = minteger[0] + 1;
              //  int q=minteger[0];
                myViewHolder.tv_quantity.setText(""+ minteger[0]);
                String pack_id=issuedInventerayList.getTask_id();
                long res=dbManager.insertQuantity(""+minteger[0],pack_id);
               // SharedPreferenceUtils.getInstance(context).setStringValue("quantity",""+minteger);
               // onItemSelectListner.getQuantity(""+minteger[0]);
               // obj.setQuantity(quantity);
              //  servicePackagesTasks.add(obj);



            }



        });
        myViewHolder.bt_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj=new ServicePackagesTask();

                minteger[0] = minteger[0] - 1;
                myViewHolder.tv_quantity.setText(""+ minteger[0]);
                String pack_id=issuedInventerayList.getTask_id();
                long res=dbManager.insertQuantity(""+minteger[0],pack_id);
               // onItemSelectListner.getQuantity(""+minteger[0]);

                // obj.setQuantity(quantity);
              //  servicePackagesTasks.add(obj);


            }

        });



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
                List<String> spinnerArray = new ArrayList();
                idList.add("0");

                boolean bool = ((ResultList)param1Response.body()).getMessage().equals("successfully fetched");
                byte b = 0;
                if (bool) {
                   // resultList.add()
                    resultList = ((ResultList)param1Response.body()).getResponse();
                    spinnerArray.add("Select");
                    while (b < resultList.size()) {
                     //   List list = spinnerArray;
                        idList.add(resultList.get(b).getMeterials_id());
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(resultList.get(b).getMeterials_name());
                        stringBuilder.append(" ");
                        stringBuilder.append(resultList.get(b).getMeterials_type_name());
                        spinnerArray.add(stringBuilder.toString());

                        b++;
                    }

                    ArrayAdapter arrayAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, spinnerArray);
                    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    myViewHolder.sp_request.setAdapter(arrayAdapter);

                }
             //   Toast.makeText(context, "No data in this category", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("Exception ", e.getMessage());
            }  }
        });
    }


    @Override
    public int getItemCount() {
        return carList.size();
    }

    @Override
    public void onClick(JsonObject jsonObject) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_task_name, tv_package_name,tv_integer_number;
        TextView tv_quantity;
        Spinner sp_status,sp_request;
        Button bt_increase,bt_decrease;



        public MyViewHolder(View param1View) {
            super(param1View);


            tv_quantity =  param1View.findViewById(R.id.quantity);
            bt_decrease=param1View.findViewById(R.id.decrease);
            bt_increase=param1View.findViewById(R.id.increase);
            tv_task_name = (TextView) param1View.findViewById(R.id.task_name);
            tv_package_name = (TextView) param1View.findViewById(R.id.pack_name);
            sp_request =  param1View.findViewById(R.id.request);
            sp_status =  param1View.findViewById(R.id.status);
           // tv_integer_number=param1View.findViewById(R.id.integer_number);
        }

    }





}
