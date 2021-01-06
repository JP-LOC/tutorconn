package com.example.tutorscape;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

//mine
public class Home extends Fragment {


    public Home() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


      /*  SharedPreferences prefs = this.getActivity().getSharedPreferences(myPrefs, MODE_PRIVATE);
        Boolean isStudent = prefs.getBoolean("isStudent", false);
        Boolean isTutor = prefs.getBoolean("isTutor", false);

        if (isStudent = true) {
            startActivity(new Intent(this.getActivity(), TutorListActivity.class));

            Intent intent = new Intent(this.getActivity(), TutorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        } else if (isTutor = true) {
            startActivity(new Intent(this.getActivity(), TutorListActivity.class));

            Intent intent = new Intent(this.getActivity(), TutorListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

       */


        // Inflate the layout for this fragment
        //    return inflater.inflate(R.layout.fragment_home, container, false);

            View v = inflater.inflate(R.layout.fragment_home, container, false);

            CardView btnS = (CardView) v.findViewById(R.id.buttonRasS);
            btnS.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginStudent.class);
                    startActivity(intent);
                }
            });


           CardView btnT = (CardView) v.findViewById(R.id.buttonRasT);
            btnT.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), LoginTutor.class);
                    startActivity(intent);
                }
            });
            return v;


        }
    }
