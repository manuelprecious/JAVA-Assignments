import java.util.Scanner;

public class HelloWorld {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("What is your name?");
        String name = input.nextLine();

        System.out.println("How old are you? ");
        int age = input.nextInt();

        System.out.println("Nice to meet you, " + name + "!");

        if (age < 13) {
            System.out.println("You're a cool kid. Enjoy those cartoons");
        } else if (age < 20) {
            System.out.println("Teens life huh! Stay curious");
        } else if (age < 30){
            System.out.println("You're in the adulting zone.");
        }
        
        else if (age < 50) {
            System.out.println("You're in your prime. Keep Thriving");
        } else {
            System.out.println("Wisdom vibes. The world needs your experience...");
        }

        input.close();
    }
}