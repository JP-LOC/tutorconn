package com.example.tutorscape;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import static androidx.core.content.ContextCompat.startActivity;

public class ContactViewHolder extends RecyclerView.ViewHolder {

    public TextView name,ph_no,list_title,list_image,list_trial,list_phone,list_weeklyRate;
public ImageView imageView,delete;
    public ContactViewHolder(View itemView){
        super(itemView);
        name=(TextView)itemView.findViewById(R.id.contact_name);
        ph_no=(TextView)itemView.findViewById(R.id.ph_no);
        list_title=(TextView)itemView.findViewById(R.id.my_item_title);
        list_trial=(TextView)itemView.findViewById(R.id.my_item_trial);
        list_phone=(TextView)itemView.findViewById(R.id.my_item_phone);
        list_weeklyRate=(TextView)itemView.findViewById(R.id.my_item_weeklyRate);
        imageView =(ImageView)itemView.findViewById(R.id.my_item_imageView);
        delete = (ImageView)itemView.findViewById(R.id.delete);
        //list_imageText=


        ConstraintLayout myFrame= (ConstraintLayout) itemView.findViewById(R.id.my_frame);

                myFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });

                myFrame.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Vibrator tt = (Vibrator) v.getContext().getSystemService(Context.VIBRATOR_SERVICE);
                        tt.vibrate(100);
                        String smsNumber=list_weeklyRate.getText().toString();

                        String nme =name.getText().toString();
                        //  String smsNumber = "263774233406"; // E164 format without '+' sign
                        Intent sendIntent = new Intent(Intent.ACTION_SEND);
                        sendIntent.setType("text/plain");
                        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hello "+nme+" I have reviewed your profile on TUTORCONN and am interested in working with you");
                        sendIntent.putExtra("jid", smsNumber + "@s.whatsapp.net"); //phone number without "+" prefix
                        sendIntent.setPackage("com.whatsapp");
                        v.getContext().startActivity(sendIntent);
                        return false;
                    }
                });


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Press and hold to connect with this tutor", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
