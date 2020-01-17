package innerclasses.ex6.inheritance;

import innerclasses.ex6.inter.MIC;
import innerclasses.ex6.mainclass.MCS;

public class Inherit extends MCS {
    MIC makeMIC() {
        return this.new Inner();
    }
    public static void main(String[] args) {
        MCS k = new MCS();
        MCS.Inner g = k.new Inner();
    }
}
