package innerclasses.ex7;

public class Outer {
    private boolean isModified = false;
    Outer() {
        System.out.println("I have been modified: " + false);
    }
    private void outerMethod() {
        System.out.println("The private outer method");
    }
    class Inner {
        private int t = 23;
        void modifyOuter() {
            isModified = true;
            outerMethod();
        }
    }
    void useInner() {
        Inner i = new Inner();
        i.t = 234; // outer class can access the private members of inner class
        i.modifyOuter();
        System.out.println("I have been modified: " + isModified);
    }

    public static void main(String[] args) {
        Outer o = new Outer();
        o.useInner();
    }
}
