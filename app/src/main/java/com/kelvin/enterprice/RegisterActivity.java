package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.kelvin.enterprice.Model.ModelDonor;
import com.kelvin.enterprice.Model.ModelLogin;
import com.kelvin.enterprice.Model.ModelUser;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText mEmailEt,mpasswordEt, mAddress, mPhone, mAge, mfirstName, mLastName;
    Spinner userType;
    Button regbtn;
    TextView mHaveAccount;
    private FirebaseFirestore db;
    ProgressDialog pd;
    private FirebaseAuth mAuth;
    String uid, loginId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //Actionbar and its title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Create Account");
        //Enable back button
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mEmailEt = findViewById(R.id.emailEt);
        mpasswordEt = findViewById(R.id.passwordEt);
        regbtn = findViewById(R.id.registeBtn);
        mHaveAccount = findViewById(R.id.have_accountTv);
        mAddress = findViewById(R.id.address);
        mfirstName = findViewById(R.id.firstname);
        mLastName = findViewById(R.id.lastname);
        mPhone = findViewById(R.id.phone);
        mAge = findViewById(R.id.age);
        db = FirebaseFirestore.getInstance();

        userType = findViewById(R.id.spinArea);

        final ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.type,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userType.setAdapter(adapter3);
        userType.setOnItemSelectedListener(this);

        mHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });


        //init firebase auth
        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);
        pd.setMessage("Registering User...");



        //handler  register
        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = mEmailEt.getText().toString().trim();
                String password = mpasswordEt.getText().toString().trim();


                String userRank = userType.getSelectedItem().toString();
                //validate
                if(username.isEmpty()){
                    //set error and focuss
                    mEmailEt.setText("Enter username");
                    mEmailEt.setFocusable(true);

                }else if(password.length()<6){

                    //set error and focuss
                    mpasswordEt.setText("Password length at least 6 characters");
                    mpasswordEt.setFocusable(true);
                }else{
                    if(userRank.equals("Admin")) {
                        String email = username+"donar@"+"gmail.com";
                        registerUser(email, password);

                    }else {
                        String email = username+"@"+"gmail.com";
                        registerUser(email, password);

                    }
                }

            }
        });


    }

    private void registerUser(String email, final String password) {



        final String phone = mPhone.getText().toString().trim();
        final String address = mAddress.getText().toString().trim();
        final String age = mAge.getText().toString().trim();
        final String firstname = mfirstName.getText().toString().trim();
        final String lastname = mLastName.getText().toString().trim();
        final String userRank =userType.getSelectedItem().toString();
        final String username = mEmailEt.getText().toString().trim();
        String pass = mpasswordEt.getText().toString().trim();
        pd.show();
        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            pd.dismiss();

                            FirebaseUser user = mAuth.getCurrentUser();

                            //Get user email and uid from auth
                            String email = user.getEmail();
                            String uid = user.getUid();

                            if(userRank.equals("Admin")){
                                addLogin(password,"Admin");
                                addDonnor(loginId,username,address,age,lastname,firstname,phone);
                                Toast.makeText(RegisterActivity.this, "Registered....\n"+username, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, PActivity.class));
                                finish();
                            }else {
                                addLogin(password,"User");
                                addUser(loginId,username,address,age,lastname,firstname,phone);
                                Toast.makeText(RegisterActivity.this, "Registered....\n"+username, Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, Main2Activity.class));
                                finish();
                            }




                        }else{
                            Toast.makeText(RegisterActivity.this, "Authenitaction failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(RegisterActivity.this, "" +e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private  void addUser(String login_id, String useName, String Address, String age, String Lastname, String Firstname, String Phone){
       String Username =  mEmailEt.getText().toString().trim();
        DocumentReference dbCustomer = db.collection("User").document();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        ModelUser modelUser = new ModelUser();
        modelUser.setUser_id(uid);
        modelUser.setUser_address(Address);
        modelUser.setUser_age(age);
        modelUser.setUser_firstname(Firstname);
        modelUser.setUser_lastname(Lastname);
        modelUser.setUser_mobile(Phone);
        modelUser.setUser_login_id(login_id);
        modelUser.setUser_name(Username);

        dbCustomer.set(modelUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterActivity.this, "User Created", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });


    }



    private void addDonnor(String login_id, String useName, String Address, String age, String Lastname, String Firstname, String Phone) {
        String Username = mEmailEt.getText().toString().trim();
        DocumentReference dbCustomer = db.collection("Donor").document();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        ModelDonor modelUser = new ModelDonor();
        modelUser.setDonor_id(uid);
        modelUser.setDonor_address(Address);
        modelUser.setDonor_age(age);
        modelUser.setDonor_firstname(Firstname);
        modelUser.setDonor_lastname(Lastname);
        modelUser.setDonor_mobile(Phone);
        modelUser.setDonor_login_id(login_id);
        modelUser.setDonor_name(Username);

        dbCustomer.set(modelUser).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(RegisterActivity.this, "Donor Created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


    private void addLogin(String pass, String rank){
        String Username = mEmailEt.getText().toString().trim();
        DocumentReference dbLogin = db.collection("Login").document();
        FirebaseUser user = mAuth.getCurrentUser();
        loginId = dbLogin.getId();

        ModelLogin modelLogin = new ModelLogin();

        modelLogin.setLogin_id(dbLogin.getId());
        modelLogin.setLogin_user_name(Username);
        modelLogin.setLogin_password(pass);
        modelLogin.setLogin_type(rank);

        dbLogin.set(modelLogin).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(RegisterActivity.this, "User detail added", Toast.LENGTH_SHORT).show();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }


        @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();//go privous activitry
        return super.onSupportNavigateUp();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
