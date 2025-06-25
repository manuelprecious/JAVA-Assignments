package Animals;
public class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Woof...");
    }

    @Override
    public boolean canFly() {
        return false;
    }
}

