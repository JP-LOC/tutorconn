package com.example.tutorscape;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TutorListActivity extends BaseActivity {
    boolean doubleBackToExitPressedOnce = false;

    private SqliteDatabase mDatabase;
    boolean isFragmentLoaded;
    Fragment menuFragment;
    TextView title;
    ImageView menuButton;

    private static final String TAG = "SignedInActivity";

    //Firebase
    private FirebaseAuth.AuthStateListener mAuthListener;

    // widgets and UI References

    private RecyclerView mFirebaseList;
    private FirebaseFirestore firebaseFirestore;

    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onResume() {
        super.onResume();
        checkAuthenticationState();

    }

    private void checkAuthenticationState(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user == null){
            Intent intent = new Intent(TutorListActivity.this, LoginStudent.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }else{
            Log.d(TAG, "checkAuthenticationState: user is authenticated.");
        }
    }


    public static final String myPref="myPref";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences pref = TutorListActivity.this.getSharedPreferences(myPref, MODE_PRIVATE);
        Boolean regtutor = pref.getBoolean("regtutor", false);


        super.onCreate(savedInstanceState);
        initAddlayout(R.layout.activity_tutor_list);
        Spinner spinner =(Spinner)findViewById(R.id.spinner);
        firebaseFirestore = FirebaseFirestore.getInstance();
        mFirebaseList = findViewById(R.id.recyclerView);

        isFragmentLoaded=false;
        mDatabase = new SqliteDatabase(this);

        title = (TextView) findViewById(R.id.title_top);
        menuButton = (ImageView) findViewById(R.id.menu_icon);
        title.setText("Menu Activity");
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFragmentLoaded) {
                    loadFragment();
                    title.setText("Profile");
                } else {
                    if (menuFragment != null) {
                        if (menuFragment.isAdded()) {
                            hideFragment();
                        }
                    }
                }
            }
        });

 /*       Query query = firebaseFirestore.collection("Admin");

        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setQuery(query, Model.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {


                holder.list_name.setText(model.getName());
                holder.list_title.setText(model.getTitle());

                holder.list_imageText.setText(model.getImage());
                holder.weeklyRate.setText(model.getWeeklyRate());
                holder.subject.setText(model.getSubject());
                holder.phone.setText(model.getPhone());
                holder.trial.setText(model.getTrial());

                Picasso.get()
                        .load(model.getImage())
                        //   .centerCrop()
                        // .placeholder(R.drawable.account)
                        // .resize(w, h)
                        .transform(new CropCircleTransformation())
                        .into(holder.imageView);
            }
        };


        mFirebaseList.setHasFixedSize(true);
        mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
        mFirebaseList.setAdapter(adapter);
*/


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                class MyViewHolder extends RecyclerView.ViewHolder {

                    private TextView list_name, subject, weeklyRate, trial, phone;
                    private TextView list_title, list_imageText;
                    private ImageView imageView;

                    public MyViewHolder(@NonNull View itemView) {
                        super(itemView);

                        subject = itemView.findViewById(R.id.textViewSubject);
                        weeklyRate = itemView.findViewById(R.id.textViewWeeklyRate);
                        trial = itemView.findViewById(R.id.textViewTrial);
                        phone = itemView.findViewById(R.id.textViewPhone);

                        list_imageText = itemView.findViewById(R.id.phoneTextView);
                        list_name = itemView.findViewById(R.id.textViewName);
                        list_title = itemView.findViewById(R.id.textViewTitle);
                        imageView = itemView.findViewById(R.id.imageView);
                        
                        itemView.setOnLongClickListener(new View.OnLongClickListener() {
                            @Override
                            public boolean onLongClick(View v) {

                                Vibrator tt = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                               // if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.0){
                                 //   tt.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
                                //}else{
                                    tt.vibrate(100);
                                //}
                                final String name = list_name.getText().toString();
                                final String ph_no = subject.getText().toString();
                                final String title = list_title.getText().toString();
                                final String sweekly = weeklyRate.getText().toString();
                                final String strial = trial.getText().toString();

                                final String image = list_imageText.getText().toString();

                                final String smsNumber = phone.getText().toString();

                                Contacts newContact = new Contacts(name, ph_no, title, image, sweekly, strial, smsNumber);
                                mDatabase.addContacts(newContact);
                                //  String smsNumber = "263774233406"; // E164 format without '+' sign
                                Intent sendIntent = new Intent(Intent.ACTION_SEND);
                                sendIntent.setType("text/plain");
                                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello "+name+" I have reviewed your profile on TUTORCONN and am interested in working with you");
                                sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                                sendIntent.setPackage("com.whatsapp");
                                startActivity(sendIntent);
                                return false;
                            }
                        });
                        itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(TutorListActivity.this, "Press and hold to connect with this tutor", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                if(position==0){
                    Query query = firebaseFirestore.collection("Zero");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);
                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                }else if(position==1){

                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","one");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



adapter.startListening();
                    adapter.notifyDataSetChanged();
                }else if(position==2){

                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","two");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



                    adapter.startListening();
                    adapter.notifyDataSetChanged();

                }else if (position==3){

                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","three");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



                    adapter.startListening();
                    adapter.notifyDataSetChanged();

                }else if(position==4){

                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","four");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                }else if(position==5){
                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","five");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



                    adapter.startListening();
                    adapter.notifyDataSetChanged();

                }else if (position==6){

                    Query query = firebaseFirestore.collection("Zero").whereEqualTo("lev","six");
                    FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                            .setQuery(query, Model.class)
                            .build();
                    adapter = new FirestoreRecyclerAdapter<Model, MyViewHolder>(options) {
                        @NonNull
                        @Override
                        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                            return new MyViewHolder(view);
                        }
                        @Override
                        protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                            holder.list_name.setText(model.getName());
                            holder.list_title.setText(model.getTitle());

                            holder.list_imageText.setText(model.getImage());
                            holder.weeklyRate.setText(model.getWeeklyRate());
                            holder.subject.setText(model.getSubject());
                            holder.phone.setText(model.getPhone());
                            holder.trial.setText(model.getTrial());

                            Picasso.get()
                                    .load(model.getImage())
                                    //   .centerCrop()
                                     .placeholder(R.drawable.iconnn)
                                    // .resize(w, h)
                                    .transform(new CropCircleTransformation())
                                    .into(holder.imageView);
                        }
                    };
                    mFirebaseList.setHasFixedSize(true);
                    mFirebaseList.setLayoutManager(new LinearLayoutManager(TutorListActivity.this));
                    mFirebaseList.setAdapter(adapter);



                    adapter.startListening();
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




    }

    /*private class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView list_name, subject, weeklyRate, trial, phone;
        private TextView list_title, list_imageText;
        private ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            subject = itemView.findViewById(R.id.textViewSubject);
            weeklyRate = itemView.findViewById(R.id.textViewWeeklyRate);
            trial = itemView.findViewById(R.id.textViewTrial);
            phone = itemView.findViewById(R.id.textViewPhone);

            list_imageText = itemView.findViewById(R.id.phoneTextView);
            list_name = itemView.findViewById(R.id.textViewName);
            list_title = itemView.findViewById(R.id.textViewTitle);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final String name = list_name.getText().toString();
                    final String ph_no = subject.getText().toString();
                    final String title = list_title.getText().toString();
                    final String sweekly = weeklyRate.getText().toString();
                    final String strial = trial.getText().toString();

                    final String image = list_imageText.getText().toString();

                    final String smsNumber = phone.getText().toString();

                    Contacts newContact = new Contacts(name, ph_no, title, image, sweekly, strial, smsNumber);
                    mDatabase.addContacts(newContact);


                    //  String smsNumber = "263774233406"; // E164 format without '+' sign
                    Intent sendIntent = new Intent(Intent.ACTION_SEND);
                    sendIntent.setType("text/plain");
                    sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello Mr Russo.");
                    sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                    sendIntent.setPackage("com.whatsapp");


                    startActivity(sendIntent);


                }
            });

        }

    }
    */

    @Override
    protected void onStart() {
        super.onStart();
      //  adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
     //   adapter.stopListening();
    }



    public void hideFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.remove(menuFragment);
        fragmentTransaction.commit();
        menuButton.setImageResource(R.drawable.ic_round_notes);
        isFragmentLoaded = false;
        title.setText("Main Activity");
    }

    public void loadFragment() {
        FragmentManager fm = getSupportFragmentManager();
        menuFragment = fm.findFragmentById(R.id.container);
        menuButton.setImageResource(R.drawable.my_up);
        if (menuFragment == null) {
            menuFragment = new MenuFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
            fragmentTransaction.add(R.id.container, menuFragment);
            fragmentTransaction.commit();
        }

        isFragmentLoaded = true;
    }
    @Override
    public void onBackPressed() {
            if (doubleBackToExitPressedOnce) {
         //       super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);

//    if(isFragmentLoaded=true){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_down, R.anim.slide_up);
        fragmentTransaction.remove(menuFragment);
        fragmentTransaction.commit();
        menuButton.setImageResource(R.drawable.ic_round_notes);
        isFragmentLoaded = false;
        title.setText("Main Activity");
        //  }

    }


    public void signOut() {
        Log.d(TAG, "signOut: signing out");
        FirebaseAuth.getInstance().signOut();

        //     SharedPreferences.Editor editor = getSharedPreferences(myPrefs, MODE_PRIVATE).edit();
        //   editor.putBoolean("isStudent", false);
        // editor.apply();

        Intent intent = new Intent(TutorListActivity.this, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();

    }


    /*
            ----------------------------- Firebase setup ---------------------------------
         */



    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: started.");

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {

                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Intent intent = new Intent(TutorListActivity.this, LoginStudent.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                // ...
            }
        };
    }
}
