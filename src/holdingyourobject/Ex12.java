package holdingyourobject;

import java.util.*;

public class Ex12 {
    public static void main(String[] args) {
        List<Integer> a = new LinkedList<>(Arrays.asList(1,2,3,4,5));
        List<Integer> b = new LinkedList<>(Arrays.asList(-1,-2,-3,-4,-5));
        ListIterator a1 = a.listIterator();
        ListIterator b1 = b.listIterator();
        while (a1.hasNext())
            a1.next();
        while (b1.hasNext()) {
            b1.next();
            b1.set(a1.previous());
        }
        System.out.println(a);
        System.out.println(b);
    }
}