package interfaces;

abstract class Dad {
    protected abstract void print();
    Dad() { print(); }
}

class Son extends Dad {
    private int i = 1;
    @Override protected void print() { System.out.println("Son.i = " + i); }
}

public class Ex3 {
    public static void main(String[] args) {
        /* Process of initialization:
         * 1. Storage for Son s allocated and initialized to binary zero
         * 2. Dad() called
         * 3. Son.print() called in Dad() (s.i = 0)
         * 4. Member initializers called in order (s.i = 1)
         * 5. Body of Son() called
         */
        Son s = new Son();
        s.print();
    }
}
