package com.example.communatioadmin2;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Home extends Fragment implements View.OnClickListener {
    Button paidevent,unpaidevent,notices;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        paidevent=v.findViewById(R.id.paidevent);
        unpaidevent=v.findViewById(R.id.unpaidevent);
        notices=v.findViewById(R.id.notices);
        paidevent.setOnClickListener(this);
        unpaidevent.setOnClickListener(this);
        notices.setOnClickListener(this);
        return v;
    }

    @Override
    public void onClick(View v) {
        if(v==paidevent){
            Intent i1=new Intent(getActivity(),PaidEvent.class);
            i1.putExtra("pay","paid");
            startActivity(i1);
        }
        if(v==unpaidevent){
            Intent i2=new Intent(getActivity(),PaidEvent.class);
            i2.putExtra("pay","unpaid");
            startActivity(i2);
        }
        if(v==notices){
            Intent i2=new Intent(getActivity(),Notices.class);
            startActivity(i2);
        }
    }
}