package innerclasses.ex15;

public class First {
    First() {
        System.out.println("no-arg constructor");
    }
    First(int i) {
        System.out.println("Constructor with one argument");
    }
    public static void main(String[] args) {
        Second s = new Second();
        First k = s.makeReference();
    }
}

class Second {
    First makeReference() {
        return new First(12) {
            {
                System.out.println("Instance initialization");
                System.out.println("I am the child of First class");
            }
        };
    }
}
