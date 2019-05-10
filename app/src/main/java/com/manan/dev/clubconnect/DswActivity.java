package com.manan.dev.clubconnect;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.manan.dev.clubconnect.Adapters.DswAdapter;

import java.util.ArrayList;


public class DswActivity extends AppCompatActivity {
    ProgressDialog pd;
    DatabaseReference dataRef;
    RecyclerView recyclerView;
    private ChildEventListener mChildEventListener;
    ArrayList<String> notificationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dsw);

        recyclerView = findViewById(R.id.rv_notifications);
        notificationList=new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        pd = new ProgressDialog(DswActivity.this);
        pd.setMessage("Please Wait...");
        pd.setCanceledOnTouchOutside(false);
        pd.setCancelable(false);
        MovableFloatingActionButton addNewEventFab = findViewById(R.id.add_new_event_fab);
        addNewEventFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(DswActivity.this);
                View promptsView = li.inflate(R.layout.alert_dialog, null);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(DswActivity.this);

                // set prompts.xml to alertdialog builder
                alertDialogBuilder.setView(promptsView);

                final EditText notificationText = promptsView.findViewById(R.id.edt_comment);
                Button submit = promptsView.findViewById(R.id.buttonSubmit);
                Button cancel = promptsView.findViewById(R.id.buttonCancel);
                // create alert dialog
                final AlertDialog alertDialog = alertDialogBuilder.create();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                        pd.show();
                        dataRef = FirebaseDatabase.getInstance().getReference().child("notifications").push();
                        dataRef.setValue(notificationText.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("prerna", "Notifications Sent");

                                if (task.isSuccessful()) {
                                    Toast.makeText(DswActivity.this, "Notification Sent Successfully!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                } else {
                                    Toast.makeText(DswActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                    pd.dismiss();
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                            }
                        });
                    }
                });
                // show it
                alertDialog.show();

            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        mChildEventListener = null;
        notificationList.clear();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mChildEventListener == null) {
            mChildEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    try {
                        String details = dataSnapshot.getValue(String.class);
                        notificationList.add(details);
                        DswAdapter adapter = new DswAdapter(DswActivity.this,notificationList);
                        recyclerView.setAdapter(adapter);
//                        Toast.makeText(FeaturedDoctorActivity.this, String.valueOf(doctorList.size()), Toast.LENGTH_SHORT).show();
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    }
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            FirebaseDatabase.getInstance().getReference().child("notifications").addChildEventListener(mChildEventListener);
        }
    }
}
