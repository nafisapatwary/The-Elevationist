public class Combination {
    int value;

    public static String chooseCombination(int value, int length) {
        if (value == 0 || value == 1) {
            String combination = Combination.genNumCombination(value, length);
        }
        else if (value == 1 || value == 2) {
            // change duplicate
        }
    }

    // value represents the level the user is on
    // -> 0 = only numbers. 1 = nums & lowercase letters. 2 = lowercase & uppercase letters.
    // 3 = uppercase letters & mixed. 4 = only mixed.

    // 0 & 1
    public static String genNumCombination(int value, int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            int randomInt = (int) (Math.random() * 10);
            combination += randomInt;
        }
        return combination;
    }

    // 1 & 2
    public static String genLowLetterCombination(int value, int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            char randomLetter = (char) ('a' + (int) (Math.random() * 26));
            combination += randomLetter;
        }
        return combination;
    }

    // 2 & 3
    public static String genUpperLetterCombination(int value, int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            char randomLetter = (char) ('A' + (int) (Math.random() * 26));
            combination += randomLetter;
        }
        return combination;
    }

    // 3 & 4
    public static String genMixedCombination(int value, int length) {
        String combination = "";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
        for (int i = 0; i < length; i++) {
            char randomChar = chars.charAt((int) (Math.random() * chars.length()));
            combination += randomChar;
        }
        return combination;
    }

    public int getValue() {
        return value;
    }




}
