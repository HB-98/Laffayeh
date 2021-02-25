package com.example.laffayeh.adapter;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laffayeh.R;
import com.example.laffayeh.model.Comments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class RecycleAdapterComments extends RecyclerView.Adapter<RecycleAdapterComments.viewitem> {



    ArrayList<Comments> comments;
    Context context;
    DatabaseReference dbrf;
    public RecycleAdapterComments(Context c, ArrayList<Comments> comment)
    {
        comments=comment;
        context=c;
    }

    class  viewitem extends RecyclerView.ViewHolder
    {

        //Declare
        TextView tvName , tvComment  ;
        ImageView imgReport;

        //initialize
        public viewitem(View itemView) {
            super(itemView);
            tvName=  itemView.findViewById(R.id.tvName);
            tvComment=  itemView.findViewById(R.id.tvComment);
            imgReport=  itemView.findViewById(R.id.imgReport);
        }
    }



    @Override
    public viewitem onCreateViewHolder(final ViewGroup parent, int viewType) {




        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custome_comment, parent, false);



        return new viewitem(itemView);
    }


    @Override
    public void onBindViewHolder(viewitem holder, final int position) {
        dbrf= FirebaseDatabase.getInstance().getReference().child("comments");

        holder.tvName.setText(comments.get(position).getName());
        holder.tvComment.setText(comments.get(position).getComment());
        holder.imgReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final AlertDialog.Builder[] alt_bld = {new AlertDialog.Builder(context,R.style.MyAlertDialogTheme)};
                alt_bld[0].setTitle("Do you Want To Report This Comment ?");

                alt_bld[0].setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        dbrf.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for(DataSnapshot datas: dataSnapshot.getChildren()){
                                    if(datas.getKey().equals(comments.get(position).getCommentKey())) {
                                        int rep = Integer.parseInt(datas.child("report").getValue().toString());
                                        dbrf.child(comments.get(position).getCommentKey()).child("report").setValue(rep+1);                            }
                                    dialog.dismiss();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                dialog.dismiss();

                            }
                        });
                    }
                });
                alt_bld[0].setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });


                AlertDialog alert = alt_bld[0].create();
                alert.show();

            }
        });
    }




    @Override
    public int getItemCount() {
        return comments.size();
    }


}

