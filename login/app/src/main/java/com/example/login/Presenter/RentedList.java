package com.example.login.Presenter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.login.Model.ActiveParking;
import com.example.login.R;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class RentedList extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<ActiveParking> parkingModelArrayList;
    RentedParkingAdapter myAdapter;
    FirebaseFirestore firebaseFirestore;
    FirebaseUser firebaseUser;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        parkingModelArrayList = new ArrayList<ActiveParking>();
        myAdapter = new RentedParkingAdapter(RentedList.this, parkingModelArrayList);

        recyclerView.setAdapter(myAdapter);

        EventChangeListener();

        MaterialButton backBtn = (MaterialButton) findViewById(R.id.back);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // code to go back to the previous screen goes here
                startActivity(new Intent(RentedList.this, Profile.class));
            }
        });

    }

    private void EventChangeListener() {
        firebaseFirestore.collection("PostedParking").whereEqualTo("renterId", firebaseUser.getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null) {
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        for (DocumentChange dc : value.getDocumentChanges()) {
                            if (dc.getType() == DocumentChange.Type.ADDED) {
                                ActiveParking activeParking = dc.getDocument().toObject(ActiveParking.class);
                                if (activeParking.status.equals("Rented")) {
                                    parkingModelArrayList.add(activeParking.setPostedId(dc.getDocument().getId()));
                                }
                            }

                            myAdapter.notifyDataSetChanged();

                        }
                    }
                });
    }
}