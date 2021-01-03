package com.example.comwmtmurali.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.comwmtmurali.R;
import com.example.comwmtmurali.bean.users.Data;
import com.example.comwmtmurali.bean.users.GetUser;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.MyViewHolder> {
    public Context context;
    private Data userList;

    public UserListAdapter(Context context, Data userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserListAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.useradapter_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.MyViewHolder holder, int position) {
        holder.userid.setText(String.valueOf(userList.getUsers().get(position).getId()));
        holder.user_name.setText(String.valueOf(userList.getUsers().get(position).getUsername()));
        holder.user_email.setText(String.valueOf(userList.getUsers().get(position).getEmail()));

        Picasso.with(context).load(userList.getUsers().get(position).getProfilePic());

    }

    @Override
    public int getItemCount() {
        return userList.getUsers().size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userimage;
        TextView userid,user_name,user_email;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            userimage=itemView.findViewById(R.id.userimage);
            userid=itemView.findViewById(R.id.userid);
            user_name=itemView.findViewById(R.id.user_name);
            user_email=itemView.findViewById(R.id.user_email);
        }
    }
}
