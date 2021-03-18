package Models;

import java.time.LocalDateTime;

public class ImpressionEntry {
    private final long userId; // ~19 digit unique user id
    private final LocalDateTime date; // date and time
    private final String context; // blog, new, shopping, social media
    private final double impressionCost; // 6 d.p. value (>0)

    public ImpressionEntry(Long userId, LocalDateTime date, String context, double impressionCost) {
        this.userId = userId;
        this.date = date;
        this.context = context;
        this.impressionCost = impressionCost;
    }

    public long getUserId() {
        return userId;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getContext() {
        return context;
    }

    public double getImpressionCost() {
        return impressionCost;
    }
}
