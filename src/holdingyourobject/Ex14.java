package holdingyourobject;

import java.util.LinkedList;
import java.util.ListIterator;

public class Ex14 {
    public static void main(String[] args) {
        LinkedList<Integer> a = new LinkedList<>();
        for (int i = 0; i < 10; i++) {
            ListIterator l = a.listIterator(a.size()/2);
            l.add(i);
        }
        System.out.println(a);
    }
}
