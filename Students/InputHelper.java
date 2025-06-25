
import java.util.Scanner;

public class InputHelper {
    private Scanner input;

    public InputHelper() {
        input = new Scanner(System.in);
    }

    public String readLine(String prompt) {
        System.out.println(prompt);
        return input.nextLine();
    }

    public int readInt(String prompt) {
        System.out.println(prompt);
        int value = input.nextInt();
        input.nextLine();
        return value;
    }

    public void close() {
        input.close();
    }
}
