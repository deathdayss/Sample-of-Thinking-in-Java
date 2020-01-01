package InitializationCleanup;

public class Ex15 {
}

class Strings {
    static String k;
    static {
        k = "asd";
        System.out.println("Initialize the string");
    }

    public static void main(String[] args) {
        new Strings();
        new Strings();
    }
}