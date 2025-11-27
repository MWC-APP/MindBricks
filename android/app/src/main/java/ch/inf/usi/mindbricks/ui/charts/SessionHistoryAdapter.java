package ch.inf.usi.mindbricks.ui.charts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import ch.inf.usi.mindbricks.R;
import ch.inf.usi.mindbricks.model.StudySession;

/**
 * Adapter for displaying session history in a RecyclerView
 */
public class SessionHistoryAdapter extends RecyclerView.Adapter<SessionHistoryAdapter.SessionViewHolder> {

    private List<StudySession> sessions;
    private final OnSessionClickListener listener;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault());

    public interface OnSessionClickListener {
        void onSessionClick(StudySession session);
    }

    public SessionHistoryAdapter(OnSessionClickListener listener) {
        this.sessions = new ArrayList<>();
        this.listener = listener;
    }

    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sessions_history, parent, false);
        return new SessionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        StudySession session = sessions.get(position);
        holder.bind(session);
    }

    @Override
    public int getItemCount() {
        return sessions.size();
    }

    public void setData(List<StudySession> sessions) {
        this.sessions = sessions != null ? sessions : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void addSession(StudySession session) {
        sessions.add(0, session);
        notifyItemInserted(0);
    }

    public class SessionViewHolder extends RecyclerView.ViewHolder {
        private final TextView dateText;
        private final TextView durationText;
        private final TextView productivityText;
        private final TextView noiseText;
        private final TextView lightText;
        private final TextView pickupText;
        private final TextView locationText;
        private final View completionIndicator;

        SessionViewHolder(@NonNull View itemView) {
            super(itemView);
            dateText = itemView.findViewById(R.id.sessionDate);
            durationText = itemView.findViewById(R.id.sessionDuration);
            productivityText = itemView.findViewById(R.id.sessionProductivity);
            noiseText = itemView.findViewById(R.id.noiseLevel);
            lightText = itemView.findViewById(R.id.lightLevel);
            pickupText = itemView.findViewById(R.id.pickupCount);
            locationText = itemView.findViewById(R.id.sessionLocation);
            completionIndicator = itemView.findViewById(R.id.completionIndicator);

            itemView.setOnClickListener(v -> {
                int pos = getBindingAdapterPosition();
                if (pos != RecyclerView.NO_POSITION && listener != null) {
                    listener.onSessionClick(sessions.get(pos));
                }
            });
        }

        void bind(StudySession session) {
            dateText.setText(dateFormat.format(session.getStartTime()));
            durationText.setText(String.format(Locale.getDefault(), "%d min", session.getDurationMinutes()));

            // Productivity score with color coding
            int productivity = session.getProductivityScore();
            productivityText.setText(String.format(Locale.getDefault(), "%d%%", productivity));
            int productivityColor = getProductivityColor(productivity);
            productivityText.setTextColor(productivityColor);

            // Noise level
            String noiseLevel = categorizeNoise(session.getNoiseAvgDb());
            noiseText.setText(noiseLevel);

            // Light level
            String lightLevel = categorizeLight(session.getLightLuxAvg());
            lightText.setText(lightLevel);

            // Phone pickups
            pickupText.setText(String.valueOf(session.getPhonePickups()));

            // Location
            String location = session.getUserLocation();
            locationText.setText(capitalizeFirst(location));

            // Completion indicator
            if (session.isSessionCompleted()) {
                completionIndicator.setBackgroundColor(itemView.getContext().getColor(R.color.tag_color_green));
            } else {
                completionIndicator.setBackgroundColor(itemView.getContext().getColor(R.color.tag_color_orange));
            }
        }

        private int getProductivityColor(int productivity) {
            if (productivity < 40) {
                return itemView.getContext().getColor(R.color.tag_color_red);
            } else if (productivity < 70) {
                return itemView.getContext().getColor(R.color.tag_color_orange);
            } else {
                return itemView.getContext().getColor(R.color.tag_color_green);
            }
        }

        private String categorizeNoise(float noiseDb) {
            if (noiseDb < 30)
                return "Quiet";
            if (noiseDb < 45)
                return "Moderate";
            if (noiseDb < 60)
                return "Loud";

            return "Very Loud";
        }

        private String categorizeLight(float lightLux) {
            if (lightLux < 50)
                return "Dark";
            if (lightLux < 200)
                return "Dim";
            if (lightLux < 400)
                return "Bright";

            return "Very Bright";
        }

        private String capitalizeFirst(String str) {
            if (str == null || str.isEmpty()) return str;
            return str.substring(0, 1).toUpperCase() + str.substring(1);
        }
    }
}
