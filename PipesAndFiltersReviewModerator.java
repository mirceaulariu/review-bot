import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class PipesAndFiltersReviewModerator {

    public static void main(String[] args) {

        // initialising the product-buyers database through a Hash Map
        Map<String, Set<String>> productBuyers = new HashMap<>();
        productBuyers.put("Laptop", new HashSet<>(Arrays.asList("John", "Alice")));
        productBuyers.put("Phone", new HashSet<>(Arrays.asList("Mary", "Peter")));
        productBuyers.put("Book", new HashSet<>(Arrays.asList("Ann")));

        // instantiate the filters and the pipeline
        ReviewProcessor buyerVerification = new BuyerVerificationFilter(productBuyers);
        ReviewProcessor profanity = new ProfanityFilter();
        ReviewProcessor political = new PoliticalFilter();
        ReviewProcessor imageResizer = new ImageResizerFilter();
        ReviewProcessor linkRemover = new LinkRemoverFilter();
        ReviewProcessor sentiment = new SentimentAnalyzerFilter();
        ReviewProcessor[] pipeline = { buyerVerification, profanity, political, imageResizer, linkRemover, sentiment };

        // add reviews database
        String[] reviews = {
                "John, Laptop, ok, PICTURE",
                "Mary, Phone, @#$%, IMAGE",
                "Peter, Phone, httpGREAT, ManyPictures",
                "Ann, Book, So GOOD, Image",
                "John, Book, great, pic"
        };

        // iterate the reviews array through the pipeline
        for (String reviewStr : reviews) {
            String[] parts = reviewStr.split(", ");
            Review review = new Review(parts[0], parts[1], parts[2], parts[3]);

            for (ReviewProcessor filter : pipeline) {
                if (review != null) {
                    review = filter.process(review);
                } else {
                    break; // eliminate reveiew
                }
            }

            if (review != null) {
                System.out.println(review);
            }
        }
    }
}