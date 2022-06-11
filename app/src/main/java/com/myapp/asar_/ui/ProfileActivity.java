package com.myapp.asar_.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;
import com.myapp.asar_.MainActivity;
import com.myapp.asar_.R;
import com.myapp.asar_.database.entities.User;
import com.myapp.asar_.helper.AppPreferenceHelper;
import com.myapp.asar_.viewmodel.AppViewModel;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    private static final int IMAGE_UPLOAD_REQUEST_CODE = 123;

    @BindView(R.id.img_logout)
    ImageView img_Logout;

    @BindView(R.id.img_profile)
    ImageView img_Profile;

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

    @BindView(R.id.tv_updateAccountBtn)
    TextView updateAccountBtn;

    @BindView(R.id.darkModeSwitch)
    Switch darkModeSwitch;

    @BindView(R.id.showGoToList)
    TextView showGoToList;

    AppViewModel viewModel;
    AppPreferenceHelper appPreferenceHelper;

    int currentUserID;

    Uri imageURI;

    @BindView(R.id.card_gotoList)
    CardView gotoLIst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initUI();
    }

    private void initUI() {
        viewModel = ViewModelProviders.of(this).get(AppViewModel.class);
        appPreferenceHelper = AppPreferenceHelper.getInstance(getApplicationContext());

        currentUserID = appPreferenceHelper.getCurrentUserID();

        User user = viewModel.getUserUsingID(currentUserID);
        if (user != null) {
            fname.setText(user.getFname());
            lname.setText(user.getLname());
            email.setText(user.getEmail());
            password.setText(user.getPassword());
            contactNum.setText(user.getContactNum());
            if (user.getProfileImage() != null) {
                Bitmap bmp = BitmapFactory.decodeByteArray(user.getProfileImage(), 0, user.getProfileImage().length);
                img_Profile.setImageBitmap(bmp);
            }
        }

        img_Profile.setOnClickListener(this::onImageUploadClick);
        updateAccountBtn.setOnClickListener(this::onUpdateAccountBtnClick);
        img_Logout.setOnClickListener(this::onLogoutBtnClick);

        if (appPreferenceHelper.getDarkModeInfo()) {
            darkModeSwitch.setChecked(true);
        }
        darkModeSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    appPreferenceHelper.saveDarkModeInfo(true);
                    finish();
                } else if (!isChecked) {
                    appPreferenceHelper.saveDarkModeInfo(false);
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    finish();
                }
            }
        });
    }


    @OnClick(R.id.tv_updateAccountBtn)
    void onUpdateAccountBtnClick(View view) {
        String userFName = fname.getText().toString();
        String userLName = lname.getText().toString();

        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        String phoneNum = contactNum.getText().toString();

        if (userFName.equals("") || userLName.equals("") || userEmail.equals("") || userPassword.equals("")) {
            print("Empty Fields not acceptable");
            return;
        }

        User user = new User(userFName, userLName, userEmail, userPassword, phoneNum);
        user.setUserID(currentUserID);
        if (imageURI != null) {
            InputStream iStream;
            try {

                iStream = getContentResolver().openInputStream(imageURI);
                byte[] image = getBytes(iStream);

                user.setProfileImage(image);
                int result = viewModel.updateUserAccount(user);

                if (result > 0) {
                    print("Account updated Successfully !");
                    startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                    finish();
                } else {
                    print("Failed to update Account");
                    return;
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            int result = viewModel.updateUserAccount(user);

            if (result > 0) {
                print("Account updated Successfully !");
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
                finish();
            } else {
                print("Failed to update Account");
                return;
            }
        }


    }

    private void print(String message) {
        Toast.makeText(getApplicationContext(), "" + message, Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.img_profile)
    void onImageUploadClick(View view) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_UPLOAD_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_UPLOAD_REQUEST_CODE && resultCode == RESULT_OK) {
            if (data.getData() != null) {
                img_Profile.setImageURI(data.getData());
                imageURI = data.getData();
            }
        }
    }


    @OnClick(R.id.img_logout)
    void onLogoutBtnClick(View view) {
        appPreferenceHelper.setCurrentUserID(0);
        appPreferenceHelper.setLoggedInInfo(false);
        appPreferenceHelper.saveUserSignupInfo(true);
        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    @OnClick(R.id.showGoToList)
    void onShowToGoList(View view){
        startActivity(new Intent(ProfileActivity.this,GoToListActivity.class));
    }
}