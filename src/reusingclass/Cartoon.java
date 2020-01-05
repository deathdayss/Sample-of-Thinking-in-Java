package reusingclass;

//: reusing/Cartoon.java
// Constructor calls during inheritance.

class Art {
    int k = 0;
    {
        System.out.println("I am the art instance initialization");
    }
    Art() { System.out.println("Art constructor"); }
}
class Drawing extends Art {
    Drawing() { System.out.println("Drawing constructor"); }
}
public class Cartoon extends Drawing {
    public Cartoon() { System.out.println("Cartoon constructor"); }
    public static void main(String[] args) {
        Cartoon x = new Cartoon();
    }
} /* Output:
Art constructor
Drawing constructor
Cartoon constructor
*///:~
