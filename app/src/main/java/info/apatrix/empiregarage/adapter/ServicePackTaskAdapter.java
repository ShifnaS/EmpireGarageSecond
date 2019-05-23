package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.model.IssuedInventoryPackList;
import info.apatrix.empiregarage.model.ServicePackagesTask;

public class ServicePackTaskAdapter extends RecyclerView.Adapter<ServicePackTaskAdapter.MyViewHolder> {

    List<ServicePackagesTask> carList;
    Context context;

    public ServicePackTaskAdapter(List<ServicePackagesTask> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_service_pack, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        ServicePackagesTask issuedInventerayList = carList.get(i);
       // Log.e("111111","List "+i+": "+issuedInventerayList.toString());

        Log.e("111111","Task name "+issuedInventerayList.getTask_name());
        Log.e("111111","Status "+issuedInventerayList.getTask_name());

        myViewHolder.pack_id.setText("Package Id");
        myViewHolder.pack_name.setText("Package Name");
        myViewHolder.task_id.setText("Task Id");
        myViewHolder.task_name.setText("Task Name");

        myViewHolder.tv_package_id.setText(issuedInventerayList.getPackage_id());
        myViewHolder.tv_task_name.setText(issuedInventerayList.getTask_name());
        myViewHolder.tv_package_name.setText(issuedInventerayList.getPackage_name());
        myViewHolder.tv_task_id.setText(issuedInventerayList.getTask_id());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_task_name;

        TextView tv_package_id;

        TextView tv_package_name;

        TextView tv_task_id;

        TextView pack_id,pack_name,task_id,task_name;


        public MyViewHolder(View param1View) {
            super(param1View);


            tv_package_id = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.name);
            tv_task_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.defect);
            tv_package_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.quantity);
            tv_task_id = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.status);


            pack_id = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.pack_id);
            pack_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.pack_name);
            task_id = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.task_id);
            task_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.task_name);


        }

    }
}
