
package Animals;

public class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }

    @Override
    public boolean canFly() {
        return false;
    }
}
