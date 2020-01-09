## Thinking in Java notes 
## 1. Operators
## 2. Controlling Execution
### The infamous “goto”
```java
public class LabeledFor {
    public static void main(String[] args) {
        int i = 0;
        outer: // Can’t have statements here
        for(; true ;) { // infinite loop
            inner: // Can’t have statements here
            for(; i < 10; i++) {
                print("i = " + i);
                if(i == 2) {
                    print("continue");
                    continue;
                }
                if(i == 3) {
                    print("break");
                    i++; // Otherwise i never gets incremented.
                    break;
                }
                if(i == 7) {
                    print("continue outer");
                    i++; // Otherwise i never gets incremented.
                    continue outer;
                }
                if(i == 8) {
                    print("break outer");
                    break outer;
                }
                for(int k = 0; k < 5; k++) {
                    if(k == 3) {
                        print("continue inner");
                        continue inner;
                    }
                }
            }
        }
		// Can’t break or continue to labels here
    }
}
```
Add ```label:```  in front of ```for``` and while ```blocks``` then we can `break` out of loop or `continue` to the specific loop.  
## 3. Initialization & Cleanup
### Distinguishing overloaded methods
1. Different type of arguments
2. Different order of arguments
3. Different number of arguments
### The `this` keyword
```this``` in an object refers to  the object itself.
### Calling constructors from constructors
Use ```this``` to call other constructors from constructors.	
### How a garbage collector works
1. *reference counting*(not used): each object contains a reference counter, and every time a reference is attached to that object, the reference count is increased. When the counter is zero, the storage is released. 
The drawback is that if objects circularly refer to each other they can have nonzero reference counts while still being garbage.
2. *stop-and-copy*: Program is first stopped. Then, each live object is copied from one heap to another, leaving behind all the garbage. it is based on the idea that any non-dead object must ultimately be traceable back to a reference that lives either on the stack or in static storage. 
This way is inefficient because of two issues. The first is that you have two heaps and you slosh all the memory back and forth between these two separate heaps, maintaining twice as much memory as you actually need. The second issue is that once your program becomes stable, it might be generating little or no garbage. Despite that, a copy collector will still copy all the memory from one place to another, which is wasteful. 
3. *mark-and-sweep*: Trace through all the references to find live objects and the program also stops. Each time it finds a live object, that object is marked by setting a flag in it. Only when the marking process is finished does the sweep occur. During the sweep, the dead objects are released.
4. *adaptive garbage-collection scheme*: memory is allocated in big blocks. If you allocate a large object, it gets its own block. Each block has a generation count to keep track of whether it’s alive. In the normal case, only the blocks created since the last garbage collection are compacted; all other blocks get their generation count bumped if they have been referenced from somewhere. This handles the normal case of lots of short-lived temporary objects. Periodically, a full sweep is made—large objects are still not copied (they just get their generation count bumped), and blocks containing small objects are copied and compacted. The JVM monitors the efficiency of garbage collection and if it becomes a waste of time because all objects are long-lived, then it switches to mark-and-sweep. Similarly, the JVM keeps track of how successful mark-and-sweep is, and if the heap starts to become fragmented, it switches back to stop-and-copy.
### Member initialization
Local variables must be initialized before using. The field variables automatically get an initial value. (Number types are 0. char is ' '. object reference is **null**)
## 4. Access Control
### Interface and implementation
```java
//: access/OrganizedByAccess.java
public class OrganizedByAccess {
    public void pub1() { /* ... */ }
    public void pub2() { /* ... */ }
    public void pub3() { /* ... */ }
    private void priv1() { /* ... */ }
    private void priv2() { /* ... */ }
    private void priv3() { /* ... */ }
    private int i;
// ...
} ///:~
```
This will make it only partially easier to read, because the interface and implementation are still mixed together.
### Class access

* Make all constructors private to prevent direct creation of a class.
* Use a `static` method to return a new object in `Soup1`.
* Use a *Singleton* to allow only one object to be created  in `Soup2`.

```java
//: access/Lunch.java
// Demonstrates class access specifiers. Make a class
// effectively private with private constructors:
class Soup1 {
    private Soup1() {}
    // (1) Allow creation via static method:
    public static Soup1 makeSoup() {
        return new Soup1();
    }
}
class Soup2 {
    private Soup2() {}
    // (2) Create a static object and return a reference
// upon request.(The "Singleton" pattern):
    private static Soup2 ps1 = new Soup2();
    public static Soup2 access() {
        return ps1;
    }
    public void f() {}
}
// Only one public class allowed per file:
public class Lunch {
    void testPrivate() {
// Can’t do this! Private constructor:
//! Soup1 soup = new Soup1();
    }
    void testStatic() {
        Soup1 soup = Soup1.makeSoup();
    }
    void testSingleton() {
        Soup2.access().f();
    }
} ///:~
```
## 5. Reusing Classes
### Composition syntax
Simply place object references inside new classes. If you want the references initialized, you can do it:
1. At the point the objects are defined. This means that they’ll always be initialized before the constructor is called.

2. In the constructor for that class.

3. Right before you actually need to use the object. This is often called *lazy initialization*. It can reduce overhead in situations where object creation is expensive and the object doesn’t need to be created every time.

4. Using instance initialization. 

All four approaches are shown here:
```java
class Soap {
    private String s;
    Soap() {
        System.out.println("Soap()");
        s = "Constructed";
    }
    public String toString() { return s; }
}
public class Bath {
    private String // Initializing at point of definition:
            s1 = "Happy",
            s2 = "Happy",
            s3, s4;
    private Soap castille;
    private int i;
    private float toy;
    public Bath() {
        System.out.println("Inside Bath()");
        s3 = "Joy";
        toy = 3.14f;
        castille = new Soap();
    }
    // Instance initialization:
    { i = 47; }
    public String toString() {
        if(s4 == null) // Delayed initialization:
            s4 = "Joy";
        return
                "s1 = " + s1 + "\n" +
                        "s2 = " + s2 + "\n" +
                        "s3 = " + s3 + "\n" +
                        "s4 = " + s4 + "\n" +
                        "i = " + i + "\n" +
                        "toy = " + toy + "\n" +
                        "castille = " + castille;
    }
    public static void main(String[] args) {
        Bath b = new Bath();
        System.out.println(b);
    }
} /* Output:
Inside Bath()
Soap()
s1 = Happy
s2 = Happy
s3 = Joy
s4 = Joy
i = 47
toy = 3.14
castille*/
```
### Initializing the base class
Java automatically inserts calls to the base-class constructor in the derived-class constructor. The following example shows this working with three levels of inheritance:
```java
class Art {
    int k = 0;
    {
        System.out.println("I am the art instance initialization");
    }
    Art() { System.out.println("Art constructor"); }
}
class Drawing extends Art {
    Drawing() { System.out.println("Drawing constructor"); }
}
public class Cartoon extends Drawing {
    public Cartoon() { System.out.println("Cartoon constructor"); }
    public static void main(String[] args) {
        Cartoon x = new Cartoon();
    }
} /* Output:
Art constructor
Drawing constructor
Cartoon constructor
*///:~
```
You can see that the construction happens from the base “outward,” so the base class is initialized before the derived-class constructors can access it.
### Constructors with arguments
You must explicitly write the calls to the base-class constructor using the super keyword when dealing with constructors with arguments.
### Delegation
This is midway between inheritance and composition, because you place a member object in the class you’re building (like composition), but at the same time you expose all the methods from the member object in your new class (like inheritance). For example, a spaceship needs a control module:
```java
class SpaceShipControls {
    void up(int velocity) {}
    void down(int velocity) {}
    void left(int velocity) {}
    void right(int velocity) {}
    void forward(int velocity) {}
    void back(int velocity) {}
    void turboBoost() {}
} ///:~

//: reusing/SpaceShipDelegation.java
public class SpaceShipDelegation {
    private String name;
    private SpaceShipControls controls =
            new SpaceShipControls();
    public SpaceShipDelegation(String name) {
        this.name = name;
    }
    // Delegated methods:
    public void back(int velocity) {
        controls.back(velocity);
    }
    public void down(int velocity) {
        controls.down(velocity);
    }
    public void forward(int velocity) {
        controls.forward(velocity);
    }
    public void left(int velocity) {
        controls.left(velocity);
    }
    public void right(int velocity) {
        controls.right(velocity);
    }
    public void turboBoost() {
        controls.turboBoost();
    }
    public void up(int velocity) {
        controls.up(velocity);
    }
    public static void main(String[] args) {
        SpaceShipDelegation protector =
                new SpaceShipDelegation("NSEA Protector");
        protector.forward(100);
    }
} ///:~
```
### Name hiding
```java
//: reusing/Hide.java
// Overloading a base-class method name in a derived
// class does not hide the base-class versions.

class Homer {
    char doh(char c) {
        System.out.println("doh(char)");
        return 'd';
    }
    float doh(float f) {
        System.out.println("doh(float)");
        return 1.0f;
    }
}
class Milhouse {}
class Bart extends Homer {
    void doh(Milhouse m) {
        System.out.println("doh(Milhouse)");
    }
}
public class Hide {
    public static void main(String[] args) {
        Bart b = new Bart();
        b.doh(1);
        b.doh('x');
        b.doh(1.0f);
        b.doh(new Milhouse());
    }
} /* Output:
doh(float)
doh(char)
doh(float)
doh(Milhouse)
*///:~
```
You can see that all the overloaded methods of `Homer` are available in `Bart`, even though `Bart` introduces a new overloaded method. However, you should use a different type of the argument in the new overloaded method.
### Upcasting
Upcasting is always safe because you’re going from a more specific type to a more general type. That is, the derived class is a superset of the base class. The only thing that can occur to the class interface during the upcast is that it can lose methods, not gain them. This is why the compiler allows upcasting without any explicit casts or other special notation.
```java
//: reusing/Wind.java
// Inheritance & upcasting.
class Instrument {
    public void play() {}
    static void tune(Instrument i) {
// ...
        i.play();
    }
}
// Wind objects are instruments
// because they have the same interface:
public class Wind extends Instrument {
    public static void main(String[] args) {
        Wind flute = new Wind();
        Instrument.tune(flute); // Upcasting
    }
} ///:~
```
### Blank finals
Java allows the creation of blank finals, which are fields that are declared as `final` but are not given an initialization value. In all cases, the blank final must be initialized before it is used, and the compiler ensures this.
```java
//: reusing/BlankFinal.java
// "Blank" final fields.
class Poppet {
    private int i;
    Poppet(int ii) { i = ii; }
}
public class BlankFinal {
    private static final int VALUE_TWO;
    private final int i = 0; // Initialized final
    private final int j; // Blank final
    private final Poppet p; // Blank final reference
    // Blank finals MUST be initialized in the constructor:
    static {
        VALUE_TWO = 23;
    }
    public BlankFinal() {
        j = 1; // Initialize blank final
        p = new Poppet(1); // Initialize blank final reference
    }
    public BlankFinal(int x) {
        j = x; // Initialize blank final
        p = new Poppet(x); // Initialize blank final reference
    }
    public static void main(String[] args) {
        new BlankFinal();
        new BlankFinal(47);
    }
} ///:~
```
1. Initialized in instance initialization.
2. Initialized in constructor.
3. `static final` must be initialized in static initialization. 
### `final` arguments
Java allows you to make arguments final by declaring them as such in the argument list. This means that inside the method you cannot change what the argument reference points to:
```java
//: reusing/FinalArguments.java
// Using "final" with method arguments.
class Gizmo {
    public void spin() {}
}
public class FinalArguments {
    void with(final Gizmo g) {
//! g = new Gizmo(); // Illegal -- g is final
    }
    void without(Gizmo g) {
        g = new Gizmo(); // OK -- g not final
        g.spin();
    }
    // void f(final int i) { i++; } // Can’t change
// You can only read from a final primitive:
    int g(final int i) { return i + 1; }
    public static void main(String[] args) {
        FinalArguments bf = new FinalArguments();
        bf.without(null);
        bf.with(null);
    }
} ///:~
```
### `final` and `private` methods
Final methods cannot be overridden. Private methods are not interface of the base-class so you just create new methods in the derived class if you want to "override" the private methods.
### `final` classes
No class can inherit from final classes. All methods in a final class are implicitly final since there is no way to override them.
```java
//: reusing/Jurassic.java
// Making an entire class final.
class SmallBrain {}
final class Dinosaur {
    int i = 7;
    int j = 1;
    SmallBrain x = new SmallBrain();
    void f() {}
}
//! class Further extends Dinosaur {}
// error: Cannot extend final class ‘Dinosaur’
public class Jurassic {
    public static void main(String[] args) {
        Dinosaur n = new Dinosaur();
        n.f();
        n.i = 40;
        n.j++;
    }
} ///:~
```
### Initialization with inheritance
Remember that the compiled code for each class exists in its own separate file. That file isn’t loaded until the code is needed. In general, you can say that “class code is loaded at the point of first use.” This is usually when the first object of that class is constructed, but loading also occurs when a static field or static method is accessed.
```java
class Insect {
    private int i = 9;
    protected int j;
    Insect() {
        System.out.println("i = " + i + ", j = " + j);
        j = 39;
    }
    private static int x1 =
            printInit("static Insect.x1 initialized");
    static int printInit(String s) {
        System.out.println(s);
        return 47;
    }
}
public class Beetle extends Insect {
    private int k = printInit("Beetle.k initialized");
    public Beetle() {
        System.out.println("k = " + k);
        System.out.println("j = " + j);
    }
    private static int x2 =
            printInit("static Beetle.x2 initialized");
    public static void main(String[] args) {
        System.out.println("Beetle constructor");
        Beetle b = new Beetle();
    }
} /* Output:
static Insect.x1 initialized
static Beetle.x2 initialized
Beetle constructor
i = 9, j = 0
Beetle.k initialized
k = 47
j = 39
*///:~
```
The order: base-class static field -> derived-class static field -> (Object created) -> base-class instance & constructors-> derived-class instance & constructors.
## 6. Polymorphism
### Pitfall: fields and static methods
Field accesses and static methods  are not polymorphism. Different types of references use the field and the static methods of the type.
```java
//: polymorphism/FieldAccess.java
// Direct field access is determined at compile time.
class Super {
    public int field = 0;
    public int getField() { return field; }
}
class Sub extends Super {
    public int field = 1;
    public int getField() { return field; }
    public int getSuperField() { return super.field; }
}
public class FieldAccess {
    public static void main(String[] args) {
        Super sup = new Sub(); // Upcast
        System.out.println("sup.field = " + sup.field +
                ", sup.getField() = " + sup.getField());
        Sub sub = new Sub();
        System.out.println("sub.field = " +
                sub.field + ", sub.getField() = " +
                sub.getField() +
                ", sub.getSuperField() = " +
                sub.getSuperField());
    }
} /* Output:
sup.field = 0, sup.getField() = 1
sub.field = 1, sub.getField() = 1, sub.getSuperField() = 0
*///:~
```
```java
//: polymorphism/StaticPolymorphism.java
// Static methods are not polymorphic.
class StaticSuper {
    public static String staticGet() {
        return "Base staticGet()";
    }
    public String dynamicGet() {
        return "Base dynamicGet()";
    }
}
class StaticSub extends StaticSuper {
    public static String staticGet() {
        return "Derived staticGet()";
    }
    public String dynamicGet() {
        return "Derived dynamicGet()";
    }
}
public class StaticPolymorphism {
    public static void main(String[] args) {
        StaticSuper sup = new StaticSub(); // Upcast
        System.out.println(sup.staticGet());
        System.out.println(sup.dynamicGet());
    }
} /* Output:
Base staticGet()
Derived dynamicGet()
*///:~	
```
### Inheritance and cleanup
When cleaning up in inheritance, each layer of the class should dispose its own field and call its super's dispose. The order of disposal should be the reverse of the order of initialization, in case one subobject is dependent on another. Perform the derived-class cleanup first, then the base-class cleanup.
Use reference counting to tell if a reference of a field should be cleaned.
```java
//: polymorphism/ReferenceCounting.java
// Cleaning up shared member objects.
class Shared {
    private int refcount = 0;
    private static long counter = 0;
    private final long id = counter++;
    public Shared() {
        System.out.println("Creating " + this);
    }
    public void addRef() { refcount++; }
    protected void dispose() {
        if(--refcount == 0)
            System.out.println("Disposing " + this);
    }
    public String toString() { return "Shared " + id; }
}
class Composing {
    private Shared shared;
    private static long counter = 0;
    private final long id = counter++;
    public Composing(Shared shared) {
        System.out.println("Creating " + this);
        this.shared = shared;
        this.shared.addRef();
    }
    protected void dispose() {
        System.out.println("disposing " + this);
        shared.dispose();
    }
    public String toString() { return "Composing " + id; }
}
public class ReferenceCounting {
    public static void main(String[] args) {
        Shared shared = new Shared();
        Composing[] composing = { new Composing(shared),
                new Composing(shared), new Composing(shared),
                new Composing(shared), new Composing(shared) };
        for(Composing c : composing)
            c.dispose();
    }
} /* Output:
Creating Shared 0
Creating Composing 0
Creating Composing 1
Creating Composing 2
Creating Composing 3
Creating Composing 4
disposing Composing 0
disposing Composing 1
disposing Composing 2
disposing Composing 3
disposing Composing 4
Disposing Shared 0
*///:~
```
The type of counter is long rather than int, to prevent overflow (this is just good practice; overflowing such a counter is not likely to happen in any of the examples in this book). The id is final because we do not expect it to change its value during the lifetime of the object.
## 7. Interfaces
## 8. Inner Classes
## 9. Holding Your Objects
## 10. Error Handling with Exception
## 11. Strings
## 12. Type Information
## 13. Generics
## 14. Arrays
## 15. Containers in Depth
## 16. I/O
## 17. Enumerated Types
## 18. Annotations
## 19. Concurrency
## 20. Graphical User Interfaces
