package com.myapp.asar_.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.viewmodel.AppViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.et_email)
    TextInputEditText email;

    @BindView(R.id.et_password)
    TextInputEditText password;

    @BindView(R.id.tv_signIn)
    TextView signIn;

    @BindView(R.id.tv_donotHaveAccount)
    TextView donotHaveAccount;

    @BindView(R.id.forgetPassword)
            TextView forgetPassword;


    AppPreferenceHelper appPreferenceHelper;
    AppViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initUI();
    }

    private void initUI() {
        appPreferenceHelper = AppPreferenceHelper.getInstance(this);
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);

        signIn.setOnClickListener(this::onSignInBtnClick);
        donotHaveAccount.setOnClickListener(this::onDonotHaveAccountClick);
        forgetPassword.setOnClickListener(this::onForgetPasswordClick);

    }

    @OnClick(R.id.tv_signIn)
    void onSignInBtnClick(View view){
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();

        if(userEmail.equals("") || userPassword.equals("")){
            print("Empty Fields not acceptable");
            return;
        }

        User user = viewModel.loginUser(userEmail, userPassword);
        if(user != null){
            appPreferenceHelper.setCurrentUserID(user.getUserID());
            appPreferenceHelper.setLoggedInInfo(true);
            appPreferenceHelper.saveUserSignupInfo(true);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }else{
            print("Invalid Credentials");
        }
    }

    @OnClick(R.id.tv_donotHaveAccount)
    void onDonotHaveAccountClick(View view){
        startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        finish();
    }
    private void print(String message) {
        Toast.makeText(getApplicationContext(), ""+message, Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.forgetPassword)
    void onForgetPasswordClick(View view){
        startActivity(new Intent(LoginActivity.this, ForgetPasswordActivity.class));
    }
}