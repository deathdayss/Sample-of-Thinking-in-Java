package innerclasses.ex11;

public interface OuterInter {
    void outerDoing();
}

class Arbitrary {
    private class Inner implements OuterInter {
        @Override
        public void outerDoing() {
            System.out.println("Inner class outerDoing()");
        }
    }
    OuterInter hideDowncast() {
        return new Inner();
    }

    public static void main(String[] args) {
        Arbitrary a = new Arbitrary();
        // !Cannot downcast to the private inner class
        // ((Inner)a).hideDowncast().outerDoing();
    }
}