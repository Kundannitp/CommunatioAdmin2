package com.example.communatioadmin2;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class Notifications extends Fragment {
    View joblist;
    TextView CollegeName,email,contactno,name;
    DatabaseReference mrefrence,mrefrence1;
    CircleImageView pic;
    FirebaseAuth mAuth;
    RecyclerView mrecy;
    ArrayList<Profilestudent> prof=new ArrayList<>();
    public Notifications() {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        joblist = inflater.inflate(R.layout.fragment_notifications, container, false);
        CollegeName=joblist.findViewById(R.id.CollegeName);
        name=joblist.findViewById(R.id.name);
        email=joblist.findViewById(R.id.email);
        contactno=joblist.findViewById(R.id.contactno);
        pic=joblist.findViewById(R.id.pic);
        mrecy = (RecyclerView) joblist.findViewById(R.id.recycler_view_Events);
        mrecy.setLayoutManager(new LinearLayoutManager(getContext()));
        mAuth=FirebaseAuth.getInstance();
        mrefrence= FirebaseDatabase.getInstance().getReference();
        mrefrence1=FirebaseDatabase.getInstance().getReference();
        mrefrence.child("EventCollegewise").child(mAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    mrefrence1.child("Event Responses").child(ds.getKey()).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds1:dataSnapshot.getChildren()){
                                String s1=ds1.child("name").getValue().toString();
                                String s2=ds1.child("email").getValue().toString();
                                String s3=ds1.child("mob").getValue().toString();
                                String s4=ds1.child("college").getValue().toString();
                                String s5=ds1.child("imageurl").getValue().toString();
                                prof.add(new Profilestudent(s1,s2,s3,s4,s5));
                            }
                            NotificationAdapter n=new NotificationAdapter(getActivity(),prof);
                            mrecy.setAdapter(n);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }

                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return joblist;
    }
}
