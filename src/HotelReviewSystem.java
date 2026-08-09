//main application entry point providing an interactive console menu

import java.util.List;
import java.util.Scanner;

public class HotelReviewSystem {
    private static ReviewService reviewService = new ReviewService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=========================================");
        System.out.println("   WELCOME TO HOTEL REVIEW SYSTEM        ");
        System.out.println("=========================================");

        boolean running = true;
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. View Hotels");
            System.out.println("2. Add Review & Rating");
            System.out.println("3. View Hotel Reviews (Filter & Sort)");
            System.out.println("4. Exit");
            System.out.print("Enter choice (1-4): ");

            int choice = readIntInput();
            switch (choice) {
                case 1 -> displayHotels();
                case 2 -> addReviewMenu();
                case 3 -> viewReviewsMenu();
                case 4 -> {
                    running = false;
                    System.out.println("Thank you for using Hotel Review System!");
                }
                default -> System.out.println("Invalid choice. Please enter 1-4.");
            }
        }
    }

    private static void displayHotels() {
        System.out.println("\n--- Available Hotels ---");
        for (Hotel hotel : reviewService.getAllHotels()) {
            double avg = reviewService.calculateAverageRating(hotel.getId());
            System.out.printf("%s | Avg Rating: %.1f/5.0\n", hotel, avg);
        }
    }

    private static void addReviewMenu() {
        displayHotels();
        System.out.print("\nEnter Hotel ID to review: ");
        int hotelId = readIntInput();

        Hotel hotel = reviewService.getHotelById(hotelId);
        if (hotel == null) {
            System.out.println("Error: Hotel ID not found.");
            return;
        }

        System.out.print("Enter your name: ");
        String username = scanner.nextLine().trim();

        System.out.print("Enter rating (1.0 to 5.0): ");
        double rating = readDoubleInput();
        if (rating < 1.0 || rating > 5.0) {
            System.out.println("Error: Rating must be between 1.0 and 5.0.");
            return;
        }

        System.out.print("Enter comment: ");
        String comment = scanner.nextLine().trim();

        reviewService.addReview(hotelId, username, rating, comment);
        System.out.println("Success: Review posted for " + hotel.getName());
    }

    private static void viewReviewsMenu() {
        displayHotels();
        System.out.print("\nEnter Hotel ID to view reviews: ");
        int hotelId = readIntInput();

        Hotel hotel = reviewService.getHotelById(hotelId);
        if (hotel == null) {
            System.out.println("Error: Hotel ID not found.");
            return;
        }

        List<Review> reviews = reviewService.getReviewsForHotel(hotelId);
        if (reviews.isEmpty()) {
            System.out.println("No reviews found for " + hotel.getName());
            return;
        }

        System.out.println("\nFilter Options:");
        System.out.println("1. All Reviews");
        System.out.println("2. Filter by Minimum Rating");
        System.out.print("Choice: ");
        int filterChoice = readIntInput();

        if (filterChoice == 2) {
            System.out.print("Enter minimum rating (e.g., 4.0): ");
            double minRating = readDoubleInput();
            reviews = reviewService.filterByMinRating(hotelId, minRating);
        }

        if (reviews.isEmpty()) {
            System.out.println("No reviews match your filter.");
            return;
        }

        System.out.println("\nSort Options:");
        System.out.println("1. Highest Rating First");
        System.out.println("2. Lowest Rating First");
        System.out.println("3. Newest First");
        System.out.print("Choice: ");
        int sortChoice = readIntInput();

        switch (sortChoice) {
            case 1 -> reviews = reviewService.sortByRatingDescending(reviews);
            case 2 -> reviews = reviewService.sortByRatingAscending(reviews);
            case 3 -> reviews = reviewService.sortByDateNewestFirst(reviews);
            default -> System.out.println("Invalid sort choice. Showing default order.");
        }

        System.out.println("\n=== Reviews for " + hotel.getName() + " ===");
        for (Review r : reviews) {
            System.out.println("----------------------------------------");
            System.out.println(r);
        }
        System.out.println("----------------------------------------");
    }

    private static int readIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private static double readDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1.0;
        }
    }
}