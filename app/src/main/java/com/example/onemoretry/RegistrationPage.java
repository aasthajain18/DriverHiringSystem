package com.example.onemoretry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegistrationPage extends AppCompatActivity {

    //Create object of Database reference
    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://onemoretry-9ad9c-default-rtdb.firebaseio.com/");
    EditText name,pass,contact,pin,address,conPass;
    Button registerUser;
    RadioGroup radioGroup;
    RadioButton radioButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        name = findViewById(R.id.PersonName);
        pass = findViewById(R.id.Password);
        conPass = findViewById(R.id.ConPassword);
        contact= findViewById(R.id.Contact);
        pin = findViewById(R.id.Pincode);
        address = findViewById(R.id.Address);
        radioGroup = findViewById(R.id.radioGroupRole);
        registerUser = findViewById(R.id.Registerbutton);


        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get User Data
                String pname = name.getText().toString();
                String password = pass.getText().toString();
                String conpas = conPass.getText().toString();
                String contactNo = contact.getText().toString();
                String pincode = pin.getText().toString();
                String addressTxt = address.getText().toString();
                int selectedID = radioGroup.getCheckedRadioButtonId();
                radioButton=(RadioButton)findViewById(selectedID);
                String role_str = radioButton.getText().toString();

                // check weather user entered all the fields or not

                if(pname.isEmpty() || password.isEmpty()||contactNo.isEmpty()||pincode.isEmpty()||addressTxt.isEmpty()||role_str.isEmpty()){
                    Toast.makeText(RegistrationPage.this, "Enter all the credentials!!!!", Toast.LENGTH_SHORT).show();
                }else if(!password.equals(conpas)){
                    Toast.makeText(RegistrationPage.this, "Password not matching", Toast.LENGTH_SHORT).show();
                }else {

                    dbRef.child("Info").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //check wheather user is not registered before

                            if (snapshot.hasChild(contactNo)) {
                                Toast.makeText(RegistrationPage.this, "You are already registered!!", Toast.LENGTH_SHORT).show();
                            } else {

                                //Sending data to Database
                                // we are using contact no as unique Id
                                dbRef.child("Info").child(contactNo).child("Name").setValue(pname);
                                dbRef.child("Info").child(contactNo).child("Password").setValue(password);
                                dbRef.child("Info").child(contactNo).child("ContactNo").setValue(contactNo);
                                dbRef.child("Info").child(contactNo).child("Address").setValue(addressTxt);
                                dbRef.child("Info").child(contactNo).child("Pincode").setValue(pincode);
                                dbRef.child("Info").child(contactNo).child("Role").setValue(role_str);

                                Toast.makeText(RegistrationPage.this, "You're Successfully Registered.", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            if(role_str.equals("Driver")){
                                //creating another branch for driver's info
                                dbRef.child("DriversInfo").child(contactNo).child("Name").setValue(pname);
                                dbRef.child("DriversInfo").child(contactNo).child("Location").setValue(addressTxt);
                                dbRef.child("DriversInfo").child(contactNo).child("Pincode").setValue(pincode);
                                dbRef.child("DriversInfo").child(contactNo).child("ContactNo").setValue(contactNo);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });


    }
}