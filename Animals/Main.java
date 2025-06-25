package Animals;
public class Main {
    public static void main(String[] args) {
        Zoo zoo = new Zoo();

        Animal dog = new Dog();
        Animal cat = new Cat();
        Animal bird = new Bird();

        zoo.addAnimal(dog);
        zoo.addAnimal(cat);
        zoo.addAnimal(bird);

        zoo.displaySounds();
        System.out.println();
        zoo.displayFlyers();

    }
}
