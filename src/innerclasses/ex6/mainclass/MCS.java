package innerclasses.ex6.mainclass;

import innerclasses.ex6.inter.MIC;

public class MCS {
    protected class Inner implements MIC {
        public Inner() {} // need public constructor to create one in Ex6Base child:
        @Override
        public void doSomething() {
            System.out.println("do something in the inner class");
        }
    }
}