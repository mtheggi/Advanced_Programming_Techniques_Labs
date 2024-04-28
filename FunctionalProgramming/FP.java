import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FP {

    public static long countOccurrences(String text, String word) {
        return Arrays.stream(text.split(" "))
                    .filter(w -> w.equals(word))
                    .count();
    }
        
    

    public static void main(String[] args) {
        List<String> sentences = List.of("the best of the The the best", "hello", "the brown fox");
        String targetWord = "the";

        // Function to process each sentence
        Function<String, Long> countWord = sentence -> countOccurrences(sentence, targetWord);

        // Use parallel stream to process sentences concurrently
        List<Long> wordCounts = sentences.stream()
                .parallel()
                .map(countWord)
                .collect(Collectors.toList());

        System.out.println(wordCounts); // Output: [2, 0, 1]
    }
}
