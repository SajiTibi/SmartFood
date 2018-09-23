package com.example.saji.smartfood;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import static android.content.Context.MODE_PRIVATE;

public class AboutTab extends Fragment {
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.about_tab, container, false);
        TextView userID = view.findViewById(R.id.user_id);
        TextView userEmail = view.findViewById(R.id.user_email);
        TextView userType = view.findViewById(R.id.user_type);
        userID.setText(userID.getText() + String.valueOf(MainActivity.loggedUser.userID));
        userEmail.setText(userEmail.getText() + MainActivity.loggedUser.emailAddress);
        userType.setText(MainActivity.loggedUser.getUserType().getName());
        final Button logoutButton = view.findViewById(R.id.logout_button);
        logoutButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    logoutButton.setBackground(getContext().getDrawable(R.drawable.button_clicked_drawable));
                } else if(event.getAction() == MotionEvent.ACTION_UP) {
                    logoutButton.setBackground(getContext().getDrawable(R.drawable.button_unclicked_drawable));
                    //Deleting saved user & password
                    final String NONE = "";
                    final String PREF = "User";
                    SharedPreferences prefs = getContext().getSharedPreferences(PREF, MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("Username", NONE);
                    editor.putString("Key", NONE);
                    editor.putString("Token", NONE);
                    editor.commit();

                    Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
                    getActivity().startActivity(loginIntent);
                    getActivity().finish();
                }
                return false;
            }
        });
        return view;
    }
}
