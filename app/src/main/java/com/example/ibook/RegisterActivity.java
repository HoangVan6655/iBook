package com.example.ibook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.FileObserver;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.ibook.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    //view binding
    private ActivityRegisterBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();

        //setup progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Vui lòng chờ !");
        progressDialog.setCanceledOnTouchOutside(false);

        //handle click, go back
        binding.backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //handle click, begin register
        binding.registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateData();
            }
        });

    }

    private String name ="", email = "", password = "";

    private void validateData() {
        //get gata
        name = binding.nameEt.getText().toString().trim();
        email = binding.emailEt.getText().toString().trim();
        password = binding.passwordEt.getText().toString().trim();
        String cPassword = binding.cPasswordEt.getText().toString().trim();

        //validate Data
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this,"Vui lòng nhập họ và tên !", Toast.LENGTH_SHORT).show();
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this,"Email không hợp lệ !", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(password)) {
            Toast.makeText(this,"Vui lòng nhập mật khẩu !", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(cPassword)) {
            Toast.makeText(this,"Vui lòng nhập xác nhận mật khẩu !", Toast.LENGTH_SHORT).show();
        }
        else if (!password.equals(cPassword)) {
            Toast.makeText(this,"Xác nhận mật khẩu không trùng với mật khẩu !", Toast.LENGTH_SHORT).show();
        }
        else {
            createUserAccount();
        }
    }

    private void createUserAccount() {
        //show progress
        progressDialog.setMessage("Đang tạo tài khoản...");
        progressDialog.show();

        //create user in firebase auth
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        updateUserInfo();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //account creating failed
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void updateUserInfo() {
        progressDialog.setMessage("Đang lưu thông tin người dùng...");

        //timestamp
        long timestamp = System.currentTimeMillis();

        //get curent user uid, since user is registered so we can get now
        String uid = firebaseAuth.getUid();

        //setup data to add in db
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("uid", uid);
        hashMap.put("email", email);
        hashMap.put("name", name);
        hashMap.put("profileImage", ""); //bỏ trống sau này thêm
        hashMap.put("userType", "user"); // quyền user
        hashMap.put("timestamp", timestamp);

        //set data to db
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(uid)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        //data added to db
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "Tài khoản đã được tạo !", Toast.LENGTH_SHORT).show();
                        //since user account is create so start dashboard of user
                        startActivity(new Intent(RegisterActivity.this, DashboardUserActivity.class));
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //data failed adding to db
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}