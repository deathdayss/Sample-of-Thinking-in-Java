package innerclasses.ex21;

public interface ContainsNested {
    class Nested {
        static void callInterface(ContainsNested c) {
            c.f();
        }
    }
    void f();
}

class DoIt implements ContainsNested {
    @Override
    public void f() {
        System.out.println("I am DoIt");
    }
    public static void main(String[] args) {
        ContainsNested.Nested.callInterface(new DoIt());
    }
}
