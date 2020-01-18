package innerclasses;

// initialization. A briefer version of Parcel5.java.
public class Parcel9 {
    // Argument must be final to use inside
    // anonymous inner class:
    // don't need the final after java 8 (it is implicit final)
    public Destination destination(final String dest) {
        return new Destination() {
            private String label = dest;
            public String readLabel() { return label; }
        };
    }
    public static void main(String[] args) {
        Parcel9 p = new Parcel9();
        Destination d = p.destination("Tasmania");
    }
} ///:~
