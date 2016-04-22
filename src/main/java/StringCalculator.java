import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by ruben.arana on 21/4/16.
 */
public class StringCalculator {

    public static final String DELIMITER_INIT_MARK = "//";
    public static final String DELIMITER_END_MARK = "\n";
    public static final String NEGATIVES_NOT_ALLOWED = "Negatives not allowed: ";
    private static String DEFAULT_DELIMITER = ",|\n";

    private String delimiter = DEFAULT_DELIMITER;
    private String numbersString = "";


    public int add(String input) {
        parseInput(input);
        checkNegativeNumbers(numbersString);
        return sum(numbersString);
    }

    private void parseInput(String input) {
        if (hasCustomDelimiter(input)) {
            String[] parts = splitIntoDelimiterPartAndNumbersPart(input);
            calculateDelimiter(getDelimiterPart(parts[0]));
            numbersString = parts[1];
        } else {
            numbersString = input;
        }
    }

    private String getDelimiterPart(String part) {
        return part.substring(DELIMITER_INIT_MARK.length());
    }

    private String[] splitIntoDelimiterPartAndNumbersPart(String input) {
        return input.substring(input.indexOf(DELIMITER_INIT_MARK))
                .split(DELIMITER_END_MARK);
    }

    private boolean hasCustomDelimiter(String input) {
        return input.startsWith(DELIMITER_INIT_MARK);
    }

    private void calculateDelimiter(String delimiterPart) {
        if (delimiterPart.startsWith("[")) {
            delimiter = Stream.of(delimiterPart.substring(1, delimiterPart.length() - 1).split("]\\["))
                    .map(s -> "\\" + s)
                    .collect(Collectors.joining("|"));
        } else{
            delimiter = delimiterPart;
        }
    }

    private void checkNegativeNumbers(String numbersString) {
        IntStream intStream = getIntStreamFromString(numbersString);
        String negativeNumbersString = intStream.filter(n -> n < 0)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(","));

        if (!negativeNumbersString.isEmpty()) {
            throw new IllegalArgumentException(NEGATIVES_NOT_ALLOWED + negativeNumbersString);
        }
    }

    private int sum(String numbersString) {
        return getIntStreamFromString(numbersString)
                .filter(n -> n <= 1000)
                .sum();
    }

    private IntStream getIntStreamFromString(String input) {
        if (input.isEmpty()) {
            return IntStream.empty();
        }
        return Stream
                .of(input.split(delimiter))
                .mapToInt(Integer::parseInt);
    }
}
