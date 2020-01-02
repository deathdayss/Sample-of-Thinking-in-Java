package InitializationCleanup;

public class Vararg {
    static void vargs(int k, int... mk) {
        System.out.println(k);
        for (int i : mk) {
            System.out.print(i + ", ");
        }
    }
    public static void main(String[] args) {
        vargs(2);
    }
}
