package InitializationCleanup;
class Dog {
    static Dog k = new Dog();
    Dog() {
        System.out.println("I am initialized");
    }
    static void bark() {
        System.out.println("I am barking");
    }
}
public class staticInitial {
    public static void main(String[] args) {
        Dog.bark();
    }
}
