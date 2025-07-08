package seed;

import manager.UserManager;

public class Seeder {

    public static void main(String[] args) {
        UserManager um = new UserManager();

        String username = "admin";
        String password = "admin123";

        boolean success = um.register(username, password);
        if (success) {
            System.out.println("✅ Seeder berhasil: User 'admin' ditambahkan.");
        } else {
            System.out.println("⚠️ User 'admin' sudah ada di database.");
        }
    }
}
