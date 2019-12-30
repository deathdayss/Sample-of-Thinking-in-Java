package ControllingExecution;

import java.util.Random;

public class iterationEx {
    public static void main(String[] args) {
        Random pr = new Random();
        int last = 0;
        while (true) {
            for (int i = 0; i < 25; i++) {
                int mine = pr.nextInt();
                if (i != 0) {
                    System.out.println("The value is " + last);
                    System.out.println("A second value is " + mine);
                    if (mine < last) {
                        System.out.println("The value is greater than a second value");
                    } else if (mine > last) {
                        System.out.println("The value is smaller than a second value");
                    } else {
                        System.out.println("The value is equal to a second value");
                    }
                }
                last = mine;
            }
        }
    }
}
