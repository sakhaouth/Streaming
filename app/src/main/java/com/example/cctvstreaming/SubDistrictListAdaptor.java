package com.example.cctvstreaming;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SubDistrictListAdaptor extends RecyclerView.Adapter<SubDistrictListAdaptor.ViewHolder>{
    private ArrayList<SubDistrict> subDistricts;
    private String subDistrictName;
    private String districtName;
    public SubDistrictListAdaptor(String districtName)
    {

        this.subDistricts = new ArrayList<>();
        this.districtName = districtName;
    }

    @NonNull
    @Override
    public SubDistrictListAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.sub_district_list_view,parent,false);
        return new SubDistrictListAdaptor.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDistrictListAdaptor.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.subDistrictName.setText(subDistricts.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //intent.putExtra("uri",uri);

                Intent intent = new Intent(holder.itemView.getContext(),SchoolList.class);
                intent.putExtra("dis",districtName);
                intent.putExtra("sub",subDistricts.get(holder.getAdapterPosition()).getName());
                holder.itemView.getContext().startActivity(intent);

            }
        });
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return subDistricts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView subDistrictName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            subDistrictName = (TextView) itemView.findViewById(R.id.sub_district_name);

        }
    }

    public void setSubDistricts(ArrayList<SubDistrict> subDistricts) {
        this.subDistricts = subDistricts;
        notifyDataSetChanged();
    }
}
