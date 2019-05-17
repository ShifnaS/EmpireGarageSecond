package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import info.apatrix.empiregarage.model.ServicePackages;
import java.util.List;
import info.apatrix.empiregarage.R;
public class ServicePackageAdapter extends RecyclerView.Adapter<ServicePackageAdapter.MyViewHolder> {
  List<ServicePackages> carList;
  
  Context context;
  
  int m = 0;
  
  public ServicePackageAdapter(List<ServicePackages> paramList, Context paramContext, int paramInt)
  { this.carList = paramList;
    this.context = paramContext;
    this.m = paramInt; }
  
  public int getItemCount() { return this.carList.size(); }
  
  public void onBindViewHolder(@NonNull MyViewHolder paramMyViewHolder, int paramInt) {
    ServicePackages servicePackages = (ServicePackages)this.carList.get(paramInt);
    paramMyViewHolder.tv_name.setText(servicePackages.getSerpack_name());
    paramMyViewHolder.tv_price.setText(servicePackages.getSerpack_price());
  }
  
  @NonNull
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup paramViewGroup, int paramInt)
  { return new MyViewHolder(LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_service_packages, paramViewGroup, false)); }
  
  public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_name;
    
    TextView tv_price;
    
    public MyViewHolder(View param1View) { super(param1View);
      this.tv_name = (TextView)param1View.findViewById(info.apatrix.empiregarage.R.id.name);
      this.tv_price = (TextView)param1View.findViewById(info.apatrix.empiregarage.R.id.price);
    }
  }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\adapter\ServicePackageAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */