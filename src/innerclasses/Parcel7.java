package innerclasses;

public class Parcel7 {
    public Contents contents() {
        //! Contents is an interface so cannot be initialized directly
        // return new Contents();
        return new Contents() { // Insert a class definition
            private int i = 11;
            public int value() { return i; }
        }; // Semicolon required in this case
    }
    public static void main(String[] args) {
        Parcel7 p = new Parcel7();
        Contents c = p.contents();
    }
} ///:~
