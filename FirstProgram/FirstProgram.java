package FirstProgram;
import java.util.Scanner;

public class FirstProgram {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        System.out.println("What is your name? ");
        String name = input.nextLine();

        System.out.println("How old are you? ");
        int age = input.nextInt();
        input.nextLine();

        System.out.println("How are you feeling? e.g., sad, tired, excited, happy");
        String mood = input.nextLine().toLowerCase();

        System.out.println("Nice to meet you, " + name);

        // Age-based response
        if (age < 13) {
            System.out.println("You're a cool kid, enjoy those cartoons!");
        } else if (age < 20) {
            System.out.println("Teen life, huh? Stay curious!");
        } else if (age < 30) {
            System.out.println("Welcome to the Adulting zone!");
        } else if (age < 50) {
            System.out.println("You're in your prime, keep thriving");
        } else {
            System.out.println("Wisdom vibes! The world needs your experience.");
        }

        switch (mood) {
            case "happy":
                System.out.println("Yesss! Keep spreading that sunshine");
                break;
            case "tired":
                System.out.println("Go grab a snack or take a power nap. You got this.");
                break;
            case "excited":
                System.out.println("Let's gooo! Ride that energy wave.");
                break;
            case "sad":
                System.out.println("Hey, it's okay to feel that way. Better days ahead");
            default:
                System.out.println("Hmmm... that's a vibe. Keep doing  you.");
        }

        input.close();
    }
}