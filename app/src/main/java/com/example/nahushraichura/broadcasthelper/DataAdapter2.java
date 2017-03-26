package com.example.nahushraichura.broadcasthelper;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class DataAdapter2 extends RecyclerView.Adapter<DataAdapter2.ViewHolder> {
    private ArrayList<AndroidVersions2> android_versions2;
    private Context context;
    // OnItemClickListener mItemClickListener;


    public DataAdapter2(Context context, ArrayList<AndroidVersions2> android_versions2) {
        this.context = context;
        this.android_versions2 = android_versions2;

    }


    @Override
    public DataAdapter2.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_layout2, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.username.setText(android_versions2.get(i).getusername());
        viewHolder.useremail.setText(android_versions2.get(i).getuseremail());
        viewHolder.mobile.setText(android_versions2.get(i).getuseremail());



    }

    @Override
    public int getItemCount() {
        return android_versions2.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username,useremail,mobile;

        // private ItemClickListener clickListener;

        //private RecyclerItemClickListener.OnItemClickListener clickListener;


        public ViewHolder(View view) {
            super(view);

            context = view.getContext();
            username = (TextView) view.findViewById(R.id.username);
            useremail = (TextView) view.findViewById(R.id.email);
            mobile=(TextView)view.findViewById(R.id.mobile);

            mobile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customdialog();
                }
            });

        }
    }

    public void customdialog()
    {

// dialog list entries
        final String[] items = {
                "Phone","Messaging"

        };

// dialog list icons: some examples here
        final int[] icons = {
                R.drawable.ic_menu_camera,
                R.drawable.ic_menu_camera
        };

        ListAdapter adapter = new ArrayAdapter<String>(
                context, R.layout.list_item, items) {

            ViewHolder holder;

            class ViewHolder {
                ImageView icon;
                TextView title;
            }

            public View getView(int position, View convertView, ViewGroup parent) {
                final LayoutInflater inflater = (LayoutInflater) context
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                if (convertView == null) {
                    convertView = inflater.inflate(
                            R.layout.list_item, null);

                    holder = new ViewHolder();
                    holder.icon = (ImageView) convertView.findViewById(R.id.icon);
                    holder.title = (TextView) convertView.findViewById(R.id.title);
                    convertView.setTag(holder);
                } else {
                    // view already defined, retrieve view holder
                    holder = (ViewHolder) convertView.getTag();
                }

                holder.title.setText(items[position]);

                holder.icon.setImageResource(icons[position]);
                return convertView;
            }
        };



        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Select");

        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {

                    //if phone is selected
                    Toast.makeText(context,"phone selected",Toast.LENGTH_LONG).show();

                }
                if (which == 1) {
                    //if message is selected
                    Toast.makeText(context,"message selected",Toast.LENGTH_LONG).show();

                }

            }

        });

        builder.create();
        if (! ((Activity) context).isFinishing()) {
            builder.show();
        }
    }
}
