import java.util.Arrays;
import java.util.Objects;

public class StringCalculator {
    public int add(String numbers) {
        if (numbers.length() == 0) {
            return 0;
        }
        String spl = ",";
        String delims = "";
        String[] numbers_arr = numbers.split(spl);
        if (numbers.charAt(0) == '/' && numbers.charAt(1) == '/') {
            delims = numbers.substring(2, numbers.indexOf('\n')).replaceAll("]", "").
                    replaceAll("\\[", "");
            numbers = numbers.substring(3 + delims.length()*3);
            numbers_arr = numbers.split(String.valueOf(delims.charAt(0)));
            for (int del = 1; del <= delims.length()-1; del++) {
                for (int j = 0; j <= numbers_arr.length-1; j++) {
                    if (numbers_arr[j].contains(String.valueOf(delims.charAt(del)))) {
                        String[] splitted = numbers_arr[j].split("[" + delims.charAt(del) + "]");
                        for (int s = 0; s <= splitted.length-1; s++) {
                            numbers_arr[j] = "";
                            numbers_arr = Arrays.copyOf(numbers_arr, numbers_arr.length + 1);
                            numbers_arr[numbers_arr.length-1] = splitted[s];
                        }
                    }
                }
            }
        }
        int[] all_nums = {0};
        boolean is_sec = false;
        for (int i = 0; i <= numbers_arr.length-1; i++) {
            try {
                if (Objects.equals(numbers_arr[i], "")) {
                    continue;
                }
                if (delims.contains(numbers_arr[i]) && !is_sec) {
                    continue;
                }
                if (numbers_arr[i].charAt(0) == '\n' ||
                        numbers_arr[i].charAt(numbers_arr[i].length() - 1) == '\n') {
                    is_sec = true;
                    throw new NumberFormatException();
                }
                String[] m = numbers_arr[i].split("\n");
                for (int j=0; j <= m.length - 1; j++) {
                    all_nums = Arrays.copyOf(all_nums, all_nums.length + 1);
                    all_nums[all_nums.length - 1] = Integer.parseInt(String.valueOf(m[j]));
                }
                if (is_sec) {
                    is_sec = false;
                }
            } catch (NumberFormatException ex) {
                if (is_sec) {
                    System.err.println("Incorrect input");
                    throw new RuntimeException();
                } else {
                    is_sec = true;
                }
            }
        }
        int sum = 0;
        int[] negative_nums = {};
        try {
            for (int i = 0; i <= all_nums.length-1; i++) {
                if (all_nums[i] <= 1000 && all_nums[i] >= 0) {
                    sum += all_nums[i];
                } else if (all_nums[i] < 0) {
                    negative_nums = Arrays.copyOf(negative_nums, negative_nums.length + 1);
                    negative_nums[negative_nums.length - 1] = all_nums[i];
                }
            }
            if (negative_nums.length >= 1 && negative_nums[0] != 0) {
                throw new ExceptionInInitializerError();
            }
            return sum;
        }
        catch (ExceptionInInitializerError ex) {
            System.err.println(("Negative numbers, such as".concat(Arrays.toString(negative_nums)).
                    concat("are not allowed")).replace('[', ' ').replace(']', ' '));
            throw new RuntimeException();
        }
    }
}
