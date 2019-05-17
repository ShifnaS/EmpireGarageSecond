package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.model.Result;

import java.util.List;

public class CarAdapter extends RecyclerView.Adapter<CarAdapter.MyViewHolder> {
    List<Result> carList;
    Context context;

    public CarAdapter(List<Result> carList, Context context) {
        this.carList = carList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name,tv_carName,tv_carNo,tv_startTime,tv_endTimel,tv_task,tv_advisor,tv_technician;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_name=itemView.findViewById(R.id.name);
            tv_carName=itemView.findViewById(R.id.carname);
            tv_carNo=itemView.findViewById(R.id.carnum);
            tv_startTime=itemView.findViewById(R.id.starttime);
            tv_endTimel=itemView.findViewById(R.id.endtime);
            tv_task=itemView.findViewById(R.id.task);
            tv_advisor=itemView.findViewById(R.id.advisor);
            tv_technician=itemView.findViewById(R.id.technician);
        }
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_list_row, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            Result carData=carList.get(i);
            myViewHolder.tv_name.setText(carData.getCustomer());
            myViewHolder.tv_carName.setText(carData.getCar());
            myViewHolder.tv_carNo.setText(carData.getPlateNo());
            myViewHolder.tv_startTime.setText(carData.getStartedDate());
            myViewHolder.tv_endTimel.setText(carData.getStartedDate());
            myViewHolder.tv_task.setText(carData.getId());
            myViewHolder.tv_technician.setText(carData.getTechnician());
            myViewHolder.tv_advisor.setText(carData.getAdvisorName());

    }

    @Override
    public int getItemCount() {
        return carList.size();
    }


}
