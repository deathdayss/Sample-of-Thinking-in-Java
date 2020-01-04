package accesscontrol;

class Connection {
    private Connection() {}
    static Connection makeConnection() {
        return new Connection();
    }
}

public class ConnectionManager {
    private static int left = 10;
    private static Connection[] k = new Connection[10];
    static {
        System.out.println("initialization block");
        for (int i = 0; i < k.length; i++) {
            k[i] = Connection.makeConnection();
        }
    }
    static Connection getConnection() {
        if (left > 0) {
            return k[--left];
        }
        return null;
    }

    public static void main(String[] args) {
        Connection a = ConnectionManager.getConnection();
        System.out.println(a);
    }
}
