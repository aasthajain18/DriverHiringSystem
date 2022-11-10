package com.example.onemoretry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    EditText name,password,contactTxt;
    Button LoginBtn;
    TextView newUser;

    DatabaseReference dbRef = FirebaseDatabase.getInstance().getReferenceFromUrl("https://onemoretry-9ad9c-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.PersonName);
        password = findViewById(R.id.Password);
        contactTxt = findViewById(R.id.ContactTxt);
        LoginBtn = findViewById(R.id.LoginBtn);
        newUser = findViewById(R.id.RegisterTxt);
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pname = name.getText().toString();
                String con = contactTxt.getText().toString();
                String pass = password.getText().toString();

                //Check whether credentials are filled or not

                if(pname.isEmpty() || pass.isEmpty()||con.isEmpty()){
                    Toast.makeText(MainActivity.this, "Enter Credentials!!!!", Toast.LENGTH_SHORT).show();
                }else {

                    dbRef.child("Info").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            // check contact no exists or not
                            if(snapshot.hasChild(con)){
                                //contact no. exists
                                // now get password from database and match with user entered database

                                final String getPassword = snapshot.child(con).child("Password").getValue(String.class);
                                final String getRole = snapshot.child(con).child("Role").getValue(String.class);

                                if (getPassword.equals(pass)){
                                    Toast.makeText(MainActivity.this, "Successfully LoggedIn", Toast.LENGTH_SHORT).show();
                                    if(getRole.equals("Driver")){
                                        startActivity(new Intent(MainActivity.this,DriverHome.class));
                                    }else{
                                        startActivity(new Intent(MainActivity.this,PassengerHome.class));
                                    }
                                }
                                else{
                                    Toast.makeText(MainActivity.this, "Oops!Wrong Password", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(MainActivity.this, "Not registered", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegistrationPage.class));
            }
        });
    }
}