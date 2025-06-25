package Animals;

import java.util.ArrayList;

public class Zoo {
    private ArrayList<Animal> animals;

    public Zoo() {
        animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }

    public void displaySounds() {
        System.out.println("Animal sounds in the zoo:");
        for (Animal animal : animals) {
            animal.makeSound();
        }
    }

    public void displayFlyers() {
        System.out.println("Animals that can fly:");
        for (Animal animal : animals) {
            if (animal.canFly()) {
                System.out.println(animal.getClass().getSimpleName());
            }
        }
    }
}
