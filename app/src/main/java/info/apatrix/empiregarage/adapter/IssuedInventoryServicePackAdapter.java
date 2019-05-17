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

import info.apatrix.empiregarage.activity.RequestedInventoryAdapter;
import info.apatrix.empiregarage.model.IssuedInventerayList;
import info.apatrix.empiregarage.model.IssuedInventoryPackList;

public class IssuedInventoryServicePackAdapter  extends RecyclerView.Adapter<IssuedInventoryServicePackAdapter.MyViewHolder> {

    List<IssuedInventoryPackList> carList;
    Context context;

    public IssuedInventoryServicePackAdapter(List<IssuedInventoryPackList> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(info.apatrix.empiregarage.R.layout.row_service_pack, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        IssuedInventoryPackList issuedInventerayList = carList.get(i);
        Log.e("111111","List "+i+": "+issuedInventerayList.toString());

        Log.e("111111","Task name "+issuedInventerayList.getTask_name());
        Log.e("111111","Status "+issuedInventerayList.getReqtask_request_approved());

        myViewHolder.tv_name.setText(issuedInventerayList.getReqtask_mat_name());
        myViewHolder.tv_defect_name.setText(issuedInventerayList.getTask_name());
        myViewHolder.tv_quantity.setText(issuedInventerayList.getReqtask_mat_qty());
        myViewHolder.tv_remark.setText(issuedInventerayList.getReqtask_request_approved());
    }

    @Override
    public int getItemCount() {
        return carList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_defect_name;

        TextView tv_name;

        TextView tv_quantity;

        TextView tv_remark;


        public MyViewHolder(View param1View) {
            super(param1View);
            tv_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.name);
            tv_defect_name = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.defect);
            tv_quantity = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.quantity);
            tv_remark = (TextView) param1View.findViewById(info.apatrix.empiregarage.R.id.status);


        }

    }
}
