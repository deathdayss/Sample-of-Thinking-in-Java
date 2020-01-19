package innerclasses.ex23;

public class U {
    void u1() {
        System.out.println("u1");
    }
    void u2() {
        System.out.println("u2");
    }
    void u3() {
        System.out.println("u3");
    }
}

class A {
    U makeU() {
        return new U() {
            @Override
            void u1() {
                System.out.println("u1 of A");
            }
        };
    }
}

class B {
    int num;
    U[] stores;
    B(int i) {
        num = i;
        stores = new U[i];
    }
    void accept(U u) {
        for (int i = 0; i < stores.length; i++) {
            if (stores[i] == null) {
                stores[i] = u;
                break;
            }
        }
    }
    void delete(int i) {
        stores[i] = null;
    }

    void callU() {
        for (U us : stores) {
            us.u1();
        }
    }

    public static void main(String[] args) {
        A[] a = new A[5];
        B b = new B(12);
        for (A as : a) {
            for (int i = 0; i < b.num; i++) {
                b.accept(as.makeU());
            }
        }
        b.callU();
        for (int i = 0; i < b.num/2; i++) {
            b.delete(i);
        }
    }
}
