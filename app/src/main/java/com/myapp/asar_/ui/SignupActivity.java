package com.myapp.asar_.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.viewmodel.AppViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.et_fname)
    TextInputEditText fname;

    @BindView(R.id.et_lname)
    TextInputEditText lname;

    @BindView(R.id.et_email)
    TextInputEditText email;

    @BindView(R.id.et_password)
    TextInputEditText password;

    @BindView(R.id.et_contactNum)
    TextInputEditText contactNum;

    @BindView(R.id.tv_createAccountBtn)
    TextView createAccountBtn;

    @BindView(R.id.alreadyHaveAccount)
    TextView alreadyHaveAccount;

    AppViewModel viewModel;
    AppPreferenceHelper appPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initUI();
    }

    private void initUI() {
        appPreferenceHelper = AppPreferenceHelper.getInstance(this);
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        if(appPreferenceHelper.getLoggedInfo()){
            startActivity(new Intent(SignupActivity.this,MainActivity.class));
            return;
        }

        createAccountBtn.setOnClickListener(this::onSignupBtnClick);
        alreadyHaveAccount.setOnClickListener(this::onAlreadyHaveAccountClick);

    }

    @OnClick(R.id.tv_createAccountBtn)
    void onSignupBtnClick(View view) {
        String firstName = fname.getText().toString();
        String lastName = lname.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String phoneNum = contactNum.getText().toString();

        if (firstName.equals("") || lastName.equals("") || userEmail.equals("") || userPassword.equals("")) {
            print("Empty Fields not acceptable.");
            return;
        } else {

            User user = new User(firstName, lastName,userEmail, userPassword, phoneNum);
            long result = viewModel.createAccountForUser(user);
            if (result > 0) {
                User loginUser = viewModel.loginUser(userEmail, userPassword);
                if (loginUser != null) {
                    appPreferenceHelper.setCurrentUserID(loginUser.getUserID());
                    appPreferenceHelper.setLoggedInInfo(true);
                    appPreferenceHelper.saveUserSignupInfo(true);
                    startActivity(new Intent(SignupActivity.this, MainActivity.class));
                }
            } else {
                print("Failed to create user account !");
                return;
            }
        }
    }

    @OnClick(R.id.alreadyHaveAccount)
    void onAlreadyHaveAccountClick(View view){
        startActivity(new Intent(SignupActivity.this,LoginActivity.class));
    }
    private void print(String s) {
        Toast.makeText(getApplicationContext(), "" + s, Toast.LENGTH_SHORT).show();
    }
}