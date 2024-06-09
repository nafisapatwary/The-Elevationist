public class Combination {

    // value will be currentWorld's count
    public static String chooseCombination(int value, int length) {
        String combination = "";

        if (value == 0) {
            combination = genNumCombination(length);
        }

        else if (value == 1) {
            int num = (int) ((Math.random() * 2) + 1); // equal chance to choose between either combo
            if (num == 1) {
                combination = genNumCombination(length);
            }
            else {
                combination = genLowLetterCombination(length);
            }
        }

        else if (value == 2) {
            int num = (int) ((Math.random() * 2) + 1);
            if (num == 1) {
                combination = genLowLetterCombination(length);
            }
            else {
                combination = genUpperLetterCombination(length);
            }
        }

        else if (value == 3) {
            int num = (int) ((Math.random() * 2) + 1);
            if (num == 1) {
                combination = genUpperLetterCombination(length);
            }
            else {
                combination = genMixedCombination(length);
            }
        }

        else if (value == 4) {
            combination = genMixedCombination(length);
        }

        return combination;
    }

    // each number represents the level the user is on. higher value = harder combinations
    // -> 0 = only numbers. 1 = numbers & lowercase letters. 2 = lowercase & uppercase letters.
    // 3 = uppercase letters & mixed. 4 = only mixed.

    // 0 & 1
    public static String genNumCombination(int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            int randomInt = (int) (Math.random() * 10);
            combination += randomInt;
        }
        return combination;
    }

    // 1 & 2
    public static String genLowLetterCombination(int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            char randomLetter = (char) ('a' + (int) (Math.random() * 26));
            combination += randomLetter;
        }
        return combination;
    }

    // 2 & 3
    public static String genUpperLetterCombination(int length) {
        String combination = "";
        for (int i = 0; i < length; i++) {
            char randomLetter = (char) ('A' + (int) (Math.random() * 26));
            combination += randomLetter;
        }
        return combination;
    }

    // 3 & 4
    public static String genMixedCombination(int length) {
        String combination = "";
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        for (int i = 0; i < length; i++) {
            char randomChar = chars.charAt((int) (Math.random() * chars.length()));
            combination += randomChar;
        }
        return combination;
    }
}
