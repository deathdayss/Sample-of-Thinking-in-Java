package interfaces;

abstract class Dad4 {
}

class Son4 extends Dad4 {
    protected void print() { System.out.println("Son"); }
}

abstract class SecondDad {   // Add abstract method to the base class to prevent the downcast
    abstract protected void print();
}

class SecondSon extends SecondDad {
    protected void print() { System.out.println("SecondSon"); }
}

public class Ex4 {
    public static void testPrint(Dad4 d) {
        ((Son4)d).print();
    }
    public static void secondTestPrint(SecondDad sd) {
        sd.print();
    }
    public static void main(String[] args) {
        Son4 s = new Son4();
        Ex4.testPrint(s);
        SecondSon ss = new SecondSon();
        Ex4.secondTestPrint(ss);
    }
}
