package info.apatrix.empiregarage.activity;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import info.apatrix.empiregarage.model.IssuedInventerayList;
import java.util.List;
import  info.apatrix.empiregarage.R;

public class RequestedInventoryAdapter extends RecyclerView.Adapter<RequestedInventoryAdapter.MyViewHolder> {
  List<IssuedInventerayList> carList;
  
  Context context;
  
  int m = 0;
  
  public RequestedInventoryAdapter(List<IssuedInventerayList> paramList, Context paramContext, int paramInt) { this.carList = paramList;
    this.context = paramContext;
    this.m = paramInt; }
  
  public int getItemCount() { return this.carList.size(); }
  
  public void onBindViewHolder(@NonNull MyViewHolder paramMyViewHolder, int paramInt) {
    IssuedInventerayList issuedInventerayList = (IssuedInventerayList)this.carList.get(paramInt);
    Context context1 = this.context;
    StringBuilder stringBuilder2 = new StringBuilder();
    stringBuilder2.append("carData ");
    stringBuilder2.append(issuedInventerayList.getDefect_name());
    Toast.makeText(context1, stringBuilder2.toString(), Toast.LENGTH_SHORT).show();
    paramMyViewHolder.tv_name.setText(issuedInventerayList.getReqDefect_mat_name());
    paramMyViewHolder.tv_defect_name.setText(issuedInventerayList.getDefect_name());
    paramMyViewHolder.tv_quantity.setText(issuedInventerayList.getReqDefect_mat_qt());
    paramMyViewHolder.tv_remark.setText(issuedInventerayList.getDefect_remarks());
    StringBuilder stringBuilder1 = new StringBuilder();
    stringBuilder1.append("Quanity ");
    stringBuilder1.append(issuedInventerayList.getReqDefect_mat_qt());
    Log.e("1111111", stringBuilder1.toString());
    if (this.m == 0)
      paramMyViewHolder.tv_status.setText(issuedInventerayList.getReqDefect_status()); 
    if (this.m == 1) {
      paramMyViewHolder.tv_customer_status.setText(issuedInventerayList.getReqDefect_cust_approved());
      paramMyViewHolder.tv_issued_quantity.setText(issuedInventerayList.getReqDefect_issued_qty());
      paramMyViewHolder.tv_issued_price.setText(issuedInventerayList.getReqDefect_issued_price());
    } 
  }
  
  @NonNull
  public MyViewHolder onCreateViewHolder(@NonNull ViewGroup paramViewGroup, int paramInt) {
    View view;
    if (this.m == 0) {
      view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_inventoey, paramViewGroup, false);
    } else {
      view = LayoutInflater.from(paramViewGroup.getContext()).inflate(R.layout.row_inventoey_two, paramViewGroup, false);
    } 
    return new MyViewHolder(view);
  }
  
  public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView tv_customer_status;
    
    TextView tv_defect_name;
    
    TextView tv_issued_price;
    
    TextView tv_issued_quantity;
    
    TextView tv_name;
    
    TextView tv_quantity;
    
    TextView tv_remark;
    
    TextView tv_status;
    
    public MyViewHolder(View param1View) { super(param1View);
      this.tv_name = (TextView)param1View.findViewById(R.id.name);
      this.tv_defect_name = (TextView)param1View.findViewById(R.id.defect);
      this.tv_quantity = (TextView)param1View.findViewById(R.id.quantity);
      this.tv_remark = (TextView)param1View.findViewById(R.id.remark);
      if (m == 0)
        this.tv_status = (TextView)param1View.findViewById(R.id.status);
      if (RequestedInventoryAdapter.this.m == 1) {
        this.tv_issued_price = (TextView)param1View.findViewById(R.id.issued_price);
        this.tv_issued_quantity = (TextView)param1View.findViewById(R.id.issued);
        this.tv_customer_status = (TextView)param1View.findViewById(R.id.customerApproved);
      }  }
  }
}


/* Location:              C:\Users\pc\Downloads\student_project\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\info\apatrix\empiregarage\activity\RequestedInventoryAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.0
 */