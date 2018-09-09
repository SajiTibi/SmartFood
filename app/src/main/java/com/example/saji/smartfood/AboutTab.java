package com.example.saji.smartfood;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

public class AboutTab extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.about_tab, container, false);
        TextView userID = view.findViewById(R.id.user_id);
        TextView userEmail = view.findViewById(R.id.user_email);
        TextView userType = view.findViewById(R.id.user_type);
        userID.setText(userID.getText() +String.valueOf(MainActivity.loggedUser.userID));
        userEmail.setText(userEmail.getText() +MainActivity.loggedUser.emailAddress);
        userType.setText(MainActivity.loggedUser.getUserType().getName());
        return view;
    }
}
