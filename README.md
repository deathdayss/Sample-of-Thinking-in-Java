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
Local variables must be initialized before using. The field variables automatically get an initial value.(Number types are 0. char is ' '. object reference is **null**)
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
### Behavior of polymorphic methods inside constructors
Inside an ordinary method, the dynamically-bound call is resolved at run time, because the object cannot know whether it belongs to the class that the method is in or some class derived from it.
If you call a dynamically-bound method inside a constructor, the overridden definition for that method is used. You call a method that might manipulate members that haven’t been initialized yet—a sure recipe for disaster.

```java
//: polymorphism/PolyConstructors.java
// Constructors and polymorphism
// don’t produce what you might expect.
class Glyph {
    void draw() { System.out.println("Glyph.draw()"); }
    Glyph() {
        System.out.println("Glyph() before draw()");
        draw();
        System.out.println("Glyph() after draw()");
    }
}
class RoundGlyph extends Glyph {
    private int radius = 1;
    RoundGlyph(int r) {
        radius = r;
        System.out.println("RoundGlyph.RoundGlyph(), radius = " + radius);
    }
    void draw() {
        System.out.println("RoundGlyph.draw(), radius = " + radius);
    }
}
public class PolyConstructors {
    public static void main(String[] args) {
        new RoundGlyph(5);
    }
} /* Output:
Glyph() before draw()
RoundGlyph.draw(), radius = 0
Glyph() after draw()
RoundGlyph.RoundGlyph(), radius = 5
*///:~
```
1. The storage allocated for the object is initialized to binary zero before anything else happens.
2. The base-class constructors are called as described previously. At this point, the overridden draw( ) method is called (yes, before the RoundGlyph constructor is called), which discovers a radius value of zero, due to Step 1.
3. Member initializers are called in the order of declaration.
4. The body of the derived-class constructor is called.

As a result, a good guideline for constructors is, “Do as little as possible to set the object into a good state, and if you can possibly avoid it, don’t call any other methods in this class.” The only safe methods to call inside a constructor are those that are final in the base class. (This also applies to private methods, which are automatically final.)
## 7. Interfaces
### Abstract classes and methods
Ex3
```java
abstract class Dad {
    protected abstract void print();
    Dad() { print(); }
}

class Son extends Dad {
    private int i = 1;
    @Override protected void print() { System.out.println("Son.i = " + i); }
}

public class Ex3 {
    public static void main(String[] args) {
        /* Process of initialization:
         * 1. Storage for Son s allocated and initialized to binary zero
         * 2. Dad() called
         * 3. Son.print() called in Dad() (s.i = 0)
         * 4. Member initializers called in order (s.i = 1)
         * 5. Body of Son() called
         */
        Son s = new Son();
        s.print();
    }
}
```
Ex4
```java
abstract class Dad4 {
}

class Son4 extends Dad4 {
    protected void print() { System.out.println("Son"); }
}

abstract class SecondDad {   // Add abstract method to the base class to prevent the downcast
    abstract protected void print();
}

class SecondSon extends SecondDad {
    protected void print() { System.out.println("SecondSon"); }
}

public class Ex4 {
    public static void testPrint(Dad4 d) {
        ((Son4)d).print();
    }
    public static void secondTestPrint(SecondDad sd) {
        sd.print();
    }
    public static void main(String[] args) {
        Son4 s = new Son4();
        Ex4.testPrint(s);
        SecondSon ss = new SecondSon();
        Ex4.secondTestPrint(ss);
    }
}
```
### Interfaces
* All interface fields are implicitly static and final.
* methods in an interface are implicitly  public so the methods from the interface must be defined as public.
### Complete decoupling (a little hard stuff)
```java
class Processor {
    public String name() {
        return getClass().getSimpleName();
    }
    Object process(Object input) { return input; }
}

class Upcase extends Processor {
    String process(Object input) { // Covariant return
        return ((String)input).toUpperCase();
    }
}

class Downcase extends Processor {
    String process(Object input) {
        return ((String)input).toLowerCase();
    }
}

class Splitter extends Processor {
    String process(Object input) {
// The split() argument divides a String into pieces:
        return Arrays.toString(((String)input).split(" "));
    }
}
public class Apply {
    public static void process(Processor p, Object s) {
        System.out.println("Using Processor " + p.name());
        System.out.println(p.process(s));
    }
    public static String s =
            "Disagreement with beliefs is by definition incorrect";
    public static void main(String[] args) {
        process(new Upcase(), s);
        process(new Downcase(), s);
        process(new Splitter(), s);
    }
} /* Output:
Using Processor Upcase
DISAGREEMENT WITH BELIEFS IS BY DEFINITION INCORRECT
Using Processor Downcase
disagreement with beliefs is by definition incorrect
Using Processor Splitter
[Disagreement, with, beliefs, is, by, definition, incorrect]
*///:~
```
The `Apply.process( )` method takes any kind of `Processor` and applies it to an Object, then prints the results. Creating a method that behaves differently depending on the argument object that you pass it is called the Strategy design pattern.
Now suppose you discover a set of electronic filters that seem like they could fit into your `Apply.process( )` method:

```java
package interfaces.classprocessor;

public class Waveform {
    private static long counter;
    private final long id = counter++;
    public String toString() { return "Waveform " + id; }
} ///:~

public class Filter {
    public String name() {
        return getClass().getSimpleName();
    }
    public Waveform process(Waveform input) { return input; }
} ///:~

public class LowPass extends Filter {
    double cutoff;
    public LowPass(double cutoff) { this.cutoff = cutoff; }
    public Waveform process(Waveform input) {
        return input; // Dummy processing
    }
} ///:~

public class HighPass extends Filter {
    double cutoff;
    public HighPass(double cutoff) { this.cutoff = cutoff; }
    public Waveform process(Waveform input) { return input; }
} ///:~

public class BandPass extends Filter {
    double lowCutoff, highCutoff;
    public BandPass(double lowCut, double highCut) {
        lowCutoff = lowCut;
        highCutoff = highCut;
    }
    public Waveform process(Waveform input) { return input; }
} ///:~
```
Filter has the same interface elements as `Processor`, but because it isn’t inherited from `Processor`—because the creator of the `Filter` class had no clue you might want to use it as a `Processor`—you can’t use a `Filter` with the `Apply.process( )` method, even though it would work fine.
If `Processor` is an interface, however, the constraints are loosened enough that you can reuse an `Apply.process( )` that takes that interface. Here are the modified versions of `Processor` and `Apply`:

```java
package interfaces.interfaceprocessor;

public interface Processor {
    String name();
    Object process(Object input);
} ///:~

public class Apply {
    public static void process(Processor p, Object s) {
        System.out.println("Using Processor " + p.name());
        System.out.println(p.process(s));
    }
} ///:~

public abstract class StringProcessor implements Processor{
    public String name() {
        return getClass().getSimpleName();
    }
    public abstract String process(Object input);
    public static String s =
            "If she weighs the same as a duck, she’s made of wood";
    public static void main(String[] args) {
        Apply.process(new Upcase(), s);
        Apply.process(new Downcase(), s);
        Apply.process(new Splitter(), s);
    }
}

class Upcase extends StringProcessor {
    public String process(Object input) { // Covariant return
        return ((String)input).toUpperCase();
    }
}

class Downcase extends StringProcessor {
    public String process(Object input) {
        return ((String)input).toLowerCase();
    }
}

class Splitter extends StringProcessor {
    public String process(Object input) {
        return Arrays.toString(((String)input).split(" "));
    }
} /* Output:
Using Processor Upcase
IF SHE WEIGHS THE SAME AS A DUCK, SHE’S MADE OF WOOD
Using Processor Downcase
if she weighs the same as a duck, she’s made of wood
Using Processor Splitter
[If, she, weighs, the, same, as, a, duck,, she’s, made, of, wood]
*///:~
```
However, you are often in the situation of not being able to modify the classes that you want to use. In these cases, you can use the Adapter design pattern. In Adapter, you write code to take the interface that you have and produce the interface that you need, like this:
```java
import interfaces.filters.*;
class FilterAdapter implements Processor {
    Filter filter;
    public FilterAdapter(Filter filter) {
        this.filter = filter;
    }
    public String name() { return filter.name(); }
    public Waveform process(Object input) {
        return filter.process((Waveform)input);
    }
}
public class FilterProcessor {
    public static void main(String[] args) {
        Waveform w = new Waveform();
        Apply.process(new FilterAdapter(new LowPass(1.0)), w);
        Apply.process(new FilterAdapter(new HighPass(2.0)), w);
        Apply.process(
                new FilterAdapter(new BandPass(3.0, 4.0)), w);
    }
} /* Output:
Using Processor LowPass
Waveform 0
Using Processor HighPass
Waveform 0
Using Processor BandPass
Waveform 0
*///:~
```
In this approach to Adapter, the `FilterAdapter` constructor takes the interface that you have—`Filter`—and produces an object that has the `Processor` interface that you need. You may also notice delegation in the `FilterAdapter` class.

### “Multiple inheritance” in Java

When you combine a concrete class with interfaces this way, the concrete class must come first, then the interfaces. (The compiler gives an error otherwise.)

Core reasons for interfaces:
1. Upcast to more than one base type (and the flexibility that this provides).
2. The same as using an abstract base class: to prevent the client programmer from making an object of this class and to establish that it is only an interface.

This brings up a question: Should you use an interface or an abstract class? If it’s possible to create your base class without any method definitions or member variables, you should always prefer interfaces to abstract classes. In fact, if you know something is going to be a base class, you can consider making it an interface

### Name collisions when combining Interfaces
```java
interface I1 { void f(); }
interface I2 { int f(int i); }
interface I3 { int f(); }
class C { public int f() { return 1; } }
class C2 implements I1, I2 {
    public void f() {}
    public int f(int i) { return 1; } // overloaded
}
class C3 extends C implements I2 {
    public int f(int i) { return 1; } // overloaded
}
public class C4 extends C implements I3 {
    // Identical, no problem:
    public int f() { return 1; }
}
// Methods differ only by return type:
//! class C5 extends C implements I1 {}
//! interface I4 extends I1, I3 {} ///:~
```
The difficulty occurs because overriding, implementation, and overloading get unpleasantly mixed together. Also, overloaded methods cannot differ only by return type.
Using the same method names in different interfaces that are intended to be combined generally causes confusion in the readability of the code, as well. Strive to avoid it.
### Adapting to an interface
Thus, a common use for interfaces is the aforementioned Strategy design pattern. You write
a method that performs certain operations, and that method takes an interface that you also
specify. You’re basically saying, "You can use my method with any object you like, as long as
your object conforms to my interface." This makes your method more flexible, general and
reusable.
```java
//: interfaces/RandomWords.java
// Implementing an interface to conform to a method.
import java.nio.*;
import java.util.*;
public class RandomWords implements Readable {
    private static Random rand = new Random(47);
    private static final char[] capitals =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    private static final char[] lowers =
            "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final char[] vowels =
            "aeiou".toCharArray();
    private int count;
    public RandomWords(int count) { this.count = count; }
    public int read(CharBuffer cb) {
        if(count-- == 0)
            return -1; // Indicates end of input
        cb.append(capitals[rand.nextInt(capitals.length)]);
        for(int i = 0; i < 4; i++) {
            cb.append(vowels[rand.nextInt(vowels.length)]);
            cb.append(lowers[rand.nextInt(lowers.length)]);
        }
        cb.append(" ");
        return 10; // Number of characters appended
    }
    public static void main(String[] args) {
        Scanner s = new Scanner(new RandomWords(10));
        while(s.hasNext())
            System.out.println(s.next());
    }
} /* Output:
Yazeruyac
Fowenucor
Goeazimom
Raeuuacio
Nuoadesiw
Hageaikux
Ruqicibui
Numasetih
Kuuuuozog
Waqizeyoy
*///:~
```
### Nesting interfaces
```java
class A {
    interface B {
        void f();
    }
    public class BImp implements B {
        public void f() {}
    }
    private class BImp2 implements B {
        public void f() {}
    }
    public interface C {
        void f();
    }
    class CImp implements C {
        public void f() {}
    }
    private class CImp2 implements C {
        public void f() {}
    }
    private interface D {
        void f();
    }
    private class DImp implements D {
        public void f() {}
    }
    public class DImp2 implements D {
        public void f() {}
    }
    public D getD() { return new DImp2(); }
    private D dRef;
    public void receiveD(D d) {
        dRef = d;
        dRef.f();
    }
}
interface E {
    interface G {
        void f();
    }
    // Redundant "public":
    public interface H {
        void f();
    }
    void g();
// Cannot be private within an interface:
//! private interface I {}
}
public class NestingInterfaces {
    public class BImp implements A.B {
        public void f() {}
    }
    class CImp implements A.C {
        public void f() {}
    }
    // Cannot implement a private interface except
// within that interface’s defining class:
//! class DImp implements A.D {
//! public void f() {}
//! }
    class EImp implements E {
        public void g() {}
    }
    class EGImp implements E.G {
        public void f() {}
    }
    class EImp2 implements E {
        public void g() {}
        class EG implements E.G {
            public void f() {}
        }
    }
    public static void main(String[] args) {
        A a = new A();
// Can’t access A.D:
//! A.D ad = a.getD();
// Doesn’t return anything but A.D:
//! A.DImp2 di2 = a.getD();
// Cannot access a member of the interface:
//! a.getD().f();
// Only another A can do anything with getD():
        A a2 = new A();
        a2.receiveD(a.getD());
    }
} ///:~
```
As an added twist, interfaces can also be `private`, as seen in `A.D` (the same qualification syntax is used for nested interfaces as for nested classes). What good is a `private` nested interface? You might guess that it can only be implemented as a `private` inner class as in `DImp`, but `A.DImp2` shows that it can also be implemented as a `public` class. However, `A.DImp2` can only be used as itself. You are not allowed to mention the fact that it implements the `private` interface D, so implementing a `private` interface is a way to force the definition of the methods in that interface without adding any type information (that is, without allowing any upcasting).
The method `getD( )` produces a further quandary concerning the `private` interface: It’s a `public` method that returns a reference to a `private` interface. What can you do with the return value of this method? In `main( )`, you can see several attempts to use the return value, all of which fail. The only thing that works is if the return value is handed to an object that has permission to use it—in this case, another `A`, via the `receiveD( )` method.
Interface `E` shows that an interface nested within another interface is automatically `public` and cannot be made `private`.
`Nestinglnterfaces` shows the various ways that nested interfaces can be implemented. In particular, notice that when you implement an interface, you are not required to implement any interfaces nested within. Also, `private` interfaces cannot be implemented outside of their defining classes.
### Interfaces and factories
An interface is intended to be a gateway to multiple implementations, and a typical way to produce objects that fit the interface is the Factory Method design pattern. Instead of calling a constructor directly, you call a creation method on a factory object which produces an implementation of the interface—this way, in theory, your code is completely isolated from the implementation of the interface, thus making it possible to transparently swap one implementation for another. Here’s a demonstration showing the structure of the Factory Method:
```java
interface Service {
    void method1();
    void method2();
}
interface ServiceFactory {
    Service getService();
}
class Implementation1 implements Service {
    Implementation1() {} // Package access
    public void method1() {System.out.println("Implementation1 method1");}
    public void method2() {System.out.println("Implementation1 method2");}
}
class Implementation1Factory implements ServiceFactory {
    public Service getService() {
        return new Implementation1();
    }
}
class Implementation2 implements Service {
    Implementation2() {} // Package access
    public void method1() {System.out.println("Implementation2 method1");}
    public void method2() {System.out.println("Implementation2 method2");}
}
class Implementation2Factory implements ServiceFactory {
    public Service getService() {
        return new Implementation2();
    }
}
public class Factories {
    public static void serviceConsumer(ServiceFactory fact) {
        Service s = fact.getService();
        s.method1();
        s.method2();
    }
    public static void main(String[] args) {
        serviceConsumer(new Implementation1Factory());
// Implementations are completely interchangeable:
        serviceConsumer(new Implementation2Factory());
    }
} /* Output:
Implementation1 method1
Implementation1 method2
Implementation2 method1
Implementation2
```
Without the Factory Method, your code would somewhere have to specify the exact type of Service being created, so that it could call the appropriate constructor.
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
