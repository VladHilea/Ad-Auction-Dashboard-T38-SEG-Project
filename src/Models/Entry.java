package Models;

public class Entry {
    private final long userId; // ~19 digit unique user id

    public Entry(long userId) {
        this.userId = userId;
    }

    public long getUserId() {
        return userId;
    }
}
