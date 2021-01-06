package com.example.tutorscape;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;
import static com.example.tutorscape.TutorListActivity.myPref;

/**
 * Created by Admin on 31-05-2017.
 */

public class MenuFragment extends Fragment implements View.OnTouchListener {

    GestureDetector gestureDetector;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.slide_menu, container, false);
        LinearLayout root = (LinearLayout) rootView.findViewById(R.id.rootLayout);
        /*gestureDetector=new GestureDetector(getActivity(),new OnSwipeListener(){

            @Override
            public boolean onSwipe(Direction direction) {
                if (direction==Direction.up){
                    //do your stuff
                    ((MainActivity )  getActivity()).hideFragment();

                }

                if (direction==Direction.down){
                    //do your stuff

                }
                return true;
            }


        });
        root.setOnTouchListener(this);*/
        LinearLayout home=rootView.findViewById(R.id.home_menu);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             Intent intent = new Intent(getActivity(),TutorListActivity.class);
             startActivity(intent);
            }
        });
        LinearLayout myTutors=rootView.findViewById(R.id.my_tutors);
        myTutors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), MyTutorsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        LinearLayout registerTutor=rootView.findViewById(R.id.register_tutor);
        registerTutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                SharedPreferences.Editor editor = getActivity().getSharedPreferences(myPref, MODE_PRIVATE).edit();
                editor.putBoolean("regtutor", true);
                editor.apply();



                Intent intent = new Intent(getActivity(), TutorProfile.class);
                startActivity(intent);
            }
        });
        LinearLayout about=rootView.findViewById(R.id.about);
        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(),About.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        LinearLayout searchby = rootView.findViewById(R.id.search_location);
        searchby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),ByLocation.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });
        TextView Logout = rootView.findViewById(R.id.logout);
        Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });




        LinearLayout dummy = rootView.findViewById(R.id.dummy);
        dummy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return rootView;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;
    }


}
