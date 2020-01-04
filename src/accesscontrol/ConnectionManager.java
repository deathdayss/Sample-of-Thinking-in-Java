package accesscontrol;

class Connection {

}

public class ConnectionManager {
    private static int left = 0;
    private static Connection[] k = new Connection[10];
    static Connection getConnection() {
        if (left < 10) {
            left++;
            return k[left];
        }
        return null;
    }
}
