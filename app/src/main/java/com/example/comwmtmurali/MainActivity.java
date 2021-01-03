package com.example.comwmtmurali;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.comwmtmurali.api.APIClient;
import com.example.comwmtmurali.bean.PostUser;


import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText editName,editEmail,editPass,confirmPass;
    private Button buttonsubmit;
    private String name,email,password,confirmpassword;
    public static String token,type;
    public static  String HEADER_NAME0="Authorization";
    SharedPreferences profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName=findViewById(R.id.editName);
        editEmail=findViewById(R.id.editEmail);
        editPass=findViewById(R.id.editPass);
        confirmPass=findViewById(R.id.confirmPass);
        buttonsubmit=findViewById(R.id.buttonsubmit);



        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postData();
            }
        });
    }

    private void postData() {

        name = editName.getText().toString().trim();
        email = editEmail.getText().toString().trim();
        password = editPass.getText().toString().trim();
        confirmpassword = confirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            editName.setError("UserName cannot be empty");
            return;
        } else if (TextUtils.isEmpty(email)) {
            editEmail.setError("Email cannot be empty");
            return;
        }else if (TextUtils.isEmpty(password)) {
            editPass.setError("Password cannot be empty");
            return;
        }
        else if (TextUtils.isEmpty(confirmpassword)) {
            confirmPass.setError("ConfirmPassword cannot be empty");
            return;
        }

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id","");
            jsonObject.put("username",name);
            jsonObject.put("email",email);
            jsonObject.put("profile_pic","");
            jsonObject.put("created_at","");
            jsonObject.put("updated_at","");
            jsonObject.put("password",password);
            jsonObject.put("confirm_password",confirmpassword);

        }catch (JSONException e){
            e.printStackTrace();
        }


        final ProgressDialog progress = new ProgressDialog(MainActivity.this);
        progress.setMessage("Loading...");
        progress.setCancelable(false);
        progress.show();
        Call<PostUser> PostUserCall = APIClient
                .getapiClient()
                .getapiInterface()
                .postUserInfo(jsonObject.toString());
        PostUserCall.enqueue(new Callback<PostUser>() {
            @Override
            public void onResponse(Call<PostUser> call, Response<PostUser> response) {
                progress.dismiss();

                if (response.isSuccessful()){
                    PostUser upRes =response.body();
                    Toast.makeText(MainActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                     type=upRes.getData().getToken().getType();
                    token= upRes.getData().getToken().getToken();
                    data();
                    startActivity(new Intent(MainActivity.this,UserListActivity.class));
                }else {
                    response.errorBody();
                    Toast.makeText(MainActivity.this, "Failed to Update", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostUser> call, Throwable t) {
                progress.dismiss();
                new AlertDialog.Builder(MainActivity.this).setMessage("No Data in the response").show();
            }
        });
    }

    private void data() {
        SharedPreferences.Editor editor =getSharedPreferences("profile",MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.putString("type",type);
        editor.apply();
    }


}