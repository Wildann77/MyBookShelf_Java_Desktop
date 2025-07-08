package util;

public class Session {
    private static String activeUser;

    public static void setActiveUser(String username) {
        activeUser = username;
    }

    public static String getActiveUser() {
        return activeUser;
    }
}
