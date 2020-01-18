package innerclasses.ex9;

public interface OuterInter {
    void doSomething();
}

class outer {
    OuterInter makeInterface() {
        class cons implements OuterInter{
            @Override
            public void doSomething() {

            }
        }
        return new cons();
    }
}
