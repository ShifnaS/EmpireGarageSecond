package info.apatrix.empiregarage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import info.apatrix.empiregarage.R;
import info.apatrix.empiregarage.model.Notification;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder>{
    ArrayList<Notification> notificationList;
    Context context;

    public NotificationAdapter(ArrayList<Notification> notificationList, Context context) {
        this.notificationList = notificationList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.row_notification, viewGroup, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Notification notification=notificationList.get(i);
        myViewHolder.tv_title.setText(notification.getNotifi_message());
        myViewHolder.tv_jobId.setText("#"+notification.getJob_id());
        myViewHolder.tv_date.setText(notification.getDatetime());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date,tv_jobId,tv_title;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.title);
            tv_jobId=itemView.findViewById(R.id.job_id);
            tv_date=itemView.findViewById(R.id.date);

        }
    }
}
