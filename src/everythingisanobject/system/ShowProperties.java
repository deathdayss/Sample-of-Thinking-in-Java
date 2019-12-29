package everythingisanobject.system;

public class ShowProperties {
    /**
     * @param args
     */
    public static void main(String[] args) {
        System.getProperties().list(System.out);  // Display all of the "properties" from the system where you are running the program, so it gives you environment information
        System.out.println(System.getProperty("user.name"));
        System.out.println(System.getProperty("java.library.path"));
    }
}