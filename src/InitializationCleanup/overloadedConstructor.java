package InitializationCleanup;

public class overloadedConstructor {
    overloadedConstructor() {
        System.out.println("I am the default constructor");
    }
    overloadedConstructor(int k) {
        this();
        System.out.println("I invoke the default constructor");
    }
}
