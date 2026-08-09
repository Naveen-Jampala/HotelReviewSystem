import java.util.*;
import java.util.stream.Collectors;

public class ReviewService {
    private List<Hotel> hotels = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
    private int reviewIdCounter = 1;

    public ReviewService() {
        hotels.add(new Hotel(1, "Grand Palace Hotel", "Mumbai"));
        hotels.add(new Hotel(2, "Seaside Resort", "Goa"));
        hotels.add(new Hotel(3, "Mountain Retreat", "Manali"));
    }

    public List<Hotel> getAllHotels() {
        return hotels;
    }

    public Hotel getHotelById(int id) {
        return hotels.stream()
                .filter(h -> h.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void addReview(int hotelId, String username, double rating, String comment) {
        Review review = new Review(reviewIdCounter++, hotelId, username, rating, comment);
        reviews.add(review);
    }

    public List<Review> getReviewsForHotel(int hotelId) {
        return reviews.stream()
                .filter(r -> r.getHotelId() == hotelId)
                .collect(Collectors.toList());
    }

    public List<Review> filterByMinRating(int hotelId, double minRating) {
        return getReviewsForHotel(hotelId).stream()
                .filter(r -> r.getRating() >= minRating)
                .collect(Collectors.toList());
    }

    public List<Review> sortByRatingDescending(List<Review> list) {
        return list.stream()
                .sorted(Comparator.comparingDouble(Review::getRating).reversed())
                .collect(Collectors.toList());
    }

    public List<Review> sortByRatingAscending(List<Review> list) {
        return list.stream()
                .sorted(Comparator.comparingDouble(Review::getRating))
                .collect(Collectors.toList());
    }

    public List<Review> sortByDateNewestFirst(List<Review> list) {
        return list.stream()
                .sorted(Comparator.comparing(Review::getTimestamp).reversed())
                .collect(Collectors.toList());
    }

    public double calculateAverageRating(int hotelId) {
        List<Review> hotelReviews = getReviewsForHotel(hotelId);
        if (hotelReviews.isEmpty()) return 0.0;
        double sum = 0;
        for (Review r : hotelReviews) {
            sum += r.getRating();
        }
        return sum / hotelReviews.size();
    }
}