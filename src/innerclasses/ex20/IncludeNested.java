package innerclasses.ex20;

public interface IncludeNested {
    class Nested {
        Nested() {
            System.out.println("I am nested");
        }
        void f() {
            System.out.println("The nested is doing something");
        }
    }
    void name();
}

class Outer implements IncludeNested {

    @Override
    public void name() {
        System.out.println("I contain a nested class");
    }

    public static void main(String[] args) {
        Nested n = new Nested();
        n.f();
    }
}
