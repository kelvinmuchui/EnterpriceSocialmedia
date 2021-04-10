package com.kelvin.enterprice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.kelvin.enterprice.Model.ModelPayment;
import com.kelvin.enterprice.Model.ModelPledge;
import com.kelvin.enterprice.Model.ModelUser;

public class PaymentActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    EditText amount;
    TextView uName, uAmount;
    Spinner spinner;
    FirebaseFirestore db;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    String pId;

    Button payment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        amount = findViewById(R.id.amountEt);
        uAmount = findViewById(R.id.tvAmount);
        uName = findViewById(R.id.tvName);
        spinner = findViewById(R.id.spinArea);
        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        Intent intent = getIntent();
        payment = findViewById(R.id.plPaybtn);

        pId = intent.getStringExtra("pledgeId");

        final ArrayAdapter<CharSequence> adapter3=ArrayAdapter.createFromResource(this,R.array.pay,android.R.layout.simple_spinner_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter3);
        spinner.setOnItemSelectedListener(PaymentActivity.this);

        loadPData(pId);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPayment();
            }
        });




    }


    private void addPayment(){
        DocumentReference dbCustomer = db.collection("Fund_payment").document();


        String uid = user.getUid();
        final String timestamp = String.valueOf(System.currentTimeMillis());

        ModelPayment payment = new ModelPayment();


        payment.setFund_payment_amount(amount.getText().toString().trim());
        payment.setFund_payment_User_id(uid);
        payment.setFund_payment_id(dbCustomer.getId());
        payment.setFund_payment_Fundpledge_id(pId);
        payment.setFund_payment_mode(spinner.getSelectedItem().toString());
        payment.setFund_payment_ref(dbCustomer.getId());
        payment.setFund_payment_ref(timestamp);
        payment.setFund_payment_date(timestamp);

        dbCustomer.set(payment).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(PaymentActivity.this, "Payment made", Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void loadPData(final String pLid){

        final CollectionReference reRef = db.collection("User");



        CollectionReference reference = db.collection("Fundplegde");
        Query query =reference.whereEqualTo("fundpledge_id",pLid);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()){
                        ModelPledge pledge = documentSnapshot.toObject(ModelPledge.class);
                        uAmount.setText(pledge.getFundpledge_amount());
                        Query query2 =reRef.whereEqualTo("fundpledge_User_id", pledge.getFundpledge_User_id());
                        query2.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){
                                    for (QueryDocumentSnapshot documentSnapshot: task.getResult()) {
                                        ModelUser user = documentSnapshot.toObject(ModelUser.class);
                                        uName.setText(user.getUser_name());
                                    }
                                    }
                            }
                        });


                    }
                }
            }
        });

    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
