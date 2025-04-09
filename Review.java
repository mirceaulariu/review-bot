import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

class Review {
    String name;
    String product;
    String reviewText;
    String attachment;

    public Review(String name, String product, String reviewText, String attachment) {
        this.name = name;
        this.product = product;
        this.reviewText = reviewText;
        this.attachment = attachment;
    }

    public String getname() {
        return name;
    }

    public String getproduct() {
        return product;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getAttachment() {
        return attachment;
    }

    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    @Override
    public String toString() {
        return name + ", " + product + ", " + reviewText + ", " + attachment;
    }
}

interface ReviewProcessor {
    Review process(Review review);
}

class BuyerVerificationFilter implements ReviewProcessor {
    private Map<String, Set<String>> productBuyers;

    public BuyerVerificationFilter(Map<String, Set<String>> productBuyers) {
        this.productBuyers = productBuyers;
    }

    @Override
    public Review process(Review review) {
        String product = review.getproduct();
        String name = review.getname();
        if (productBuyers.containsKey(product) && productBuyers.get(product).contains(name)) {
            return review;
        }
        return null;
    }
}

class ProfanityFilter implements ReviewProcessor {
    @Override
    public Review process(Review review) {
        if (review.getReviewText().contains("@#$%")) {
            return null;
        }
        return review;
    }
}

class PoliticalFilter implements ReviewProcessor {
    @Override
    public Review process(Review review) {
        if (review.getReviewText().contains("+++") || review.getReviewText().contains("---")) {
            return null;
        }
        return review;
    }
}

class ImageResizerFilter implements ReviewProcessor {
    @Override
    public Review process(Review review) {
        if (review.getAttachment() != null) {
            review.setAttachment(review.getAttachment().toLowerCase());
        }
        return review;
    }
}

class LinkRemoverFilter implements ReviewProcessor {
    @Override
    public Review process(Review review) {
        review.setReviewText(review.getReviewText().replace("http", ""));
        return review;
    }
}

class SentimentAnalyzerFilter implements ReviewProcessor {
    @Override
    public Review process(Review review) {
        int upperCount = 0;
        int lowerCount = 0;
        for (char c : review.getReviewText().toCharArray()) {
            if (Character.isUpperCase(c)) {
                upperCount++;
            } else if (Character.isLowerCase(c)) {
                lowerCount++;
            }
        }
        String currentText = review.getReviewText();
        if (upperCount > lowerCount) {
            review.setReviewText(currentText + "+");
        } else if (lowerCount > upperCount) {
            review.setReviewText(currentText + "-");
        } else {
            review.setReviewText(currentText + "=");
        }
        return review;
    }
}