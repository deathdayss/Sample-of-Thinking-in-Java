[Thinking in Java notes](# book)

## Thinking in Java notes 
## Operators
## Controlling Execution
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
Add ```label:```  in front of ```for``` and while ```blocks``` then we can break out of loop or continue to the specific loop.  
## Initialization & Cleanup
### Distinguishing overloaded methods
1. Different type of arguments
2. Different order of arguments
3. Different number of arguments
### The **this** keyword
```this``` in an object refers to  the object itself.
### Calling constructors from constructors
Use ```this``` to call other constructors from constructors.	
### How a garbage collector works <a id = "book">
1. *reference counting*(not used): each object contains a reference counter, and every time a reference is attached to that object, the reference count is increased. When the counter is zero, the storage is released. 
The drawback is that if objects circularly refer to each other they can have nonzero reference counts while still being garbage.
2. *stop-and-copy*: Program is first stopped. Then, each live object is copied from one heap to another, leaving behind all the garbage. it is based on the idea that any non-dead object must ultimately be traceable back to a reference that lives either on the stack or in static storage. 
This way is inefficient because of two issues. The first is that you have two heaps and you slosh all the memory back and forth between these two separate heaps, maintaining twice as much memory as you actually need. The second issue is that once your program becomes stable, it might be generating little or no garbage. Despite that, a copy collector will still copy all the memory from one place to another, which is wasteful. 
3. *mark-and-sweep*: Trace through all the references to find live objects and the program also stops. Each time it finds a live object, that object is marked by setting a flag in it. Only when the marking process is finished does the sweep occur. During the sweep, the dead objects are released.
4. *adaptive garbage-collection scheme*: memory is allocated in big blocks. If you allocate a large object, it gets its own block. Each block has a generation count to keep track of whether it’s alive. In the normal case, only the blocks created since the last garbage collection are compacted; all other blocks get their generation count bumped if they have been referenced from somewhere. This handles the normal case of lots of short-lived temporary objects. Periodically, a full sweep is made—large objects are still not copied (they just get their generation count bumped), and blocks containing small objects are copied and compacted. The JVM monitors the efficiency of garbage collection and if it becomes a waste of time because all objects are long-lived, then it switches to mark-and-sweep. Similarly, the JVM keeps track of how successful mark-and-sweep is, and if the heap starts to become fragmented, it switches back to stop-and-copy.
## Access Control
## Reusing Classes
## Polymorphism
## Interfaces
## Inner Classes
## Holding Your Objects
## Error Handling with Exception
## Strings
## Type Information
## Generics
## Arrays
## Containers in Depth
## I/O
## Enumerated Types
## Annotations
## Concurrency
## Graphical User Interfaces
