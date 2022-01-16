package battleship;

import java.util.regex.Pattern;

final public class IO {

    static String[] readCoordinatesAsStrings(String input) {
        final int pair = 2;
        final String errorText = "Error! Wrong input (expected format: A1 A3!";
        if (input.isEmpty()) {
            throw new IllegalArgumentException(errorText);
        }
        String[] arr = input.split("\\s+");
        if (arr.length != pair) {
            throw new IllegalArgumentException(errorText);
        }
        for (String str : arr) {
            if (!Pattern.matches("^[A-J]\\d+$", str.toUpperCase())) {
                throw new IllegalArgumentException(errorText);
            }
        }
        return arr;
    }
}
