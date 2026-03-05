package com.example.attendanceapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.ViewHolder> {

    private List<String> attendeeList;

    public AttendanceAdapter(List<String> attendeeList) {
        this.attendeeList = attendeeList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_attendee, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String email = attendeeList.get(position);
        holder.tvAttendeeName.setText(email);

        // Generate initials from email for the avatar
        String initials = getInitials(email);
        holder.tvAvatar.setText(initials);
    }

    private String getInitials(String email) {
        // e.g. "john.doe@example.com" → "JD"
        try {
            String localPart = email.split("@")[0];
            String[] parts = localPart.split("[._]");
            if (parts.length >= 2) {
                return ("" + parts[0].charAt(0) + parts[1].charAt(0)).toUpperCase();
            } else {
                return localPart.substring(0, Math.min(2, localPart.length())).toUpperCase();
            }
        } catch (Exception e) {
            return "ST";
        }
    }

    @Override
    public int getItemCount() {
        return attendeeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvAttendeeName;
        TextView tvAvatar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAttendeeName = itemView.findViewById(R.id.tvAttendeeName);
            tvAvatar = itemView.findViewById(R.id.tvAvatar);
        }
    }
}