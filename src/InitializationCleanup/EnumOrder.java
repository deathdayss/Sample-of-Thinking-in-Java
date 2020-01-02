package InitializationCleanup;

enum Spiciness {
    NOT, MILD, MEDIUM, HOT, FLAMING
}

public class EnumOrder {
    public static void main(String[] args) {
        for(Spiciness s : Spiciness.values())
            System.out.println(s + ", ordinal " + s.ordinal());
    }
}
