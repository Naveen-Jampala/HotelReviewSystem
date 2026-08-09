//represent user reviews,rating, timestamps

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Review {
    private int id;
    private int hotelId;
    private String username;
    private double rating;
    private String comment;
    private LocalDateTime timestamp;

    public Review(int id, int hotelId, String username, double rating, String comment) {
        this.id = id;
        this.hotelId = hotelId;
        this.username = username;
        this.rating = rating;
        this.comment = comment;
        this.timestamp = LocalDateTime.now();
    }

    public int getId() { return id; }
    public int getHotelId() { return hotelId; }
    public String getUsername() { return username; }
    public double getRating() { return rating; }
    public String getComment() { return comment; }
    public LocalDateTime getTimestamp() { return timestamp; }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return String.format("Review #%d | Rating: %.1f/5.0 | By: %s | Date: %s\nComment: \"%s\"",
                id, rating, username, timestamp.format(formatter), comment);
    }
}