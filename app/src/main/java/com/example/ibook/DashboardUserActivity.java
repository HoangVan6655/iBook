package com.example.ibook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.ibook.databinding.ActivityDashboardAdminBinding;
import com.example.ibook.databinding.ActivityDashboardUserBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashboardUserActivity extends AppCompatActivity {
    //view binding
    private ActivityDashboardUserBinding binding;

    //firebase auth
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardUserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //init firebase auth
        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();

        //handle click logout
        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();
                checkUser();
            }
        });
    }

    private void checkUser() {
        //get current user
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser==null){
            //không đăng nhập
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        else {
            //đã đăng nhập
            String email = firebaseUser.getEmail();
            binding.subTitleTv.setText(email);
        }
    }
}