package com.myapp.asar_.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.viewmodel.AppViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    TextInputEditText email;

    @BindView(R.id.tv_searchEmail)
    TextView search;

    @BindView(R.id.ll_updatePassword)
    LinearLayout updatePasswordLayout;

    @BindView(R.id.et_password)
    TextInputEditText password;

    @BindView(R.id.et_confirm_password)
    TextInputEditText confirmPass;

    @BindView(R.id.tv_updatePass)
    TextView updatePass;

    AppPreferenceHelper appPreferenceHelper;
    AppViewModel viewModel;
    
    int userID;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);

        initUI();
        search.setOnClickListener(this::searchForAccountUsingEmail);
        updatePass.setOnClickListener(this::updatePasswordClick);
    }

    private void initUI() {
        appPreferenceHelper = AppPreferenceHelper.getInstance(getApplicationContext());
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

    }

    @OnClick(R.id.tv_searchEmail)
    void searchForAccountUsingEmail(View view) {
        String userEmail = email.getText().toString();

        if (userEmail.equals("")) {
            Toast.makeText(getApplicationContext(), "Please Enter email first", Toast.LENGTH_SHORT).show();
            return;
        }else{
            User user = viewModel.getUserUsingEmail(userEmail);
            if (user != null) {
                email.setEnabled(false);
                userID = user.getUserID();
                search.setVisibility(View.GONE);
                updatePasswordLayout.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "No Account Found", Toast.LENGTH_SHORT).show();
            }
        }
    }
    
    
    @OnClick(R.id.ll_updatePassword)
    void updatePasswordClick(View view){
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String userConfirmPassword = confirmPass.getText().toString();
        
        if(userEmail.equals("") || userPassword.equals("") || userConfirmPassword.equals("")){
            Toast.makeText(getApplicationContext(), "Empty Fields not acceptable", Toast.LENGTH_SHORT).show();
            return;
        }

        int result = viewModel.updateUserPassword(userPassword,userID);
        if(result > 0){
            Toast.makeText(getApplicationContext(), "Password Updated Successfuly !", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
            finish();
        }
    }
}