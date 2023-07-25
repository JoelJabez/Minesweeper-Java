import java.util.*;

public class Main {
    public static void main(String[] args) {
        ArrayList<Integer> numbers = new ArrayList<>();
        ArrayList<Integer> numbersWithSameDifference = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);

        String string = scanner.nextLine();
        int number = scanner.nextInt();
        int difference = (int) Double.POSITIVE_INFINITY;

        for (int i = 0; i < string.split(" ").length; i++) {
            Integer temp = Integer.parseInt(string.split(" ")[i]);
            numbers.add(temp);
        }

        for (Integer integer : numbers) {
            if (Math.abs(integer - number) < difference) {
                difference = Math.abs(integer - number);
            }
        }

        for (Integer integer : numbers) {
            if (Math.abs(integer - number) == difference) {
                numbersWithSameDifference.add(integer);
            }
        }

        Collections.sort(numbersWithSameDifference);
        for (Integer n : numbersWithSameDifference) {
            System.out.print(n + " ");
        }
        System.out.println();

        scanner.close();
    }
}
