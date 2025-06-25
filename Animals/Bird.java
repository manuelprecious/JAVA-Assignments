package Animals;
public class Bird extends Animal {
    @Override
    public void makeSound() {
        System.out.println("TweetTweet");
    }

    @Override
    public boolean canFly() {
        return true;
    }
}
