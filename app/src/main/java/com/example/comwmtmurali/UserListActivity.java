package com.example.comwmtmurali;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.comwmtmurali.adapter.UserListAdapter;
import com.example.comwmtmurali.api.APIClient;
import com.example.comwmtmurali.bean.users.Data;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends AppCompatActivity {
    private RecyclerView recycler;
    Toolbar toolbar;
    String types,tokens;
    private UserListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        toolbar=findViewById(R.id.toolbar);

        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserListActivity.this,MainActivity.class));
            }
        });
        recycler=findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recycler.setLayoutManager(mLayoutManager);
        recycler.setAdapter(listAdapter);


       SharedPreferences pref = getApplicationContext().getSharedPreferences("profile", MODE_PRIVATE);
        types = pref.getString("type", "");
        tokens = pref.getString("token", "");


        getUserList();
    }

    private void getUserList() {
        final ProgressDialog progress = new ProgressDialog(UserListActivity.this);
        progress.setMessage("Loading...");
        progress.setCancelable(true);
        progress.show();



        Call<Data> resCall = APIClient
                .getapiClient()
                .getapiInterface()
                .getUser(types+tokens.toString());
        resCall.enqueue(new Callback<Data>() {
            @Override
            public void onResponse(Call<Data> call, Response<Data> response) {
                progress.dismiss();
                Data list= response.body();

                if (response.isSuccessful()){
                    listAdapter=new UserListAdapter(UserListActivity.this,list);
                    recycler.setAdapter(listAdapter);
                }else {
                    Toast.makeText(UserListActivity.this, "No Data in the List", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Data> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(getApplicationContext(), "Server Error " + t.toString(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(UserListActivity.this,MainActivity.class));
    }
}