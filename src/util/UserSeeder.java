package util;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class UserSeeder {
    public static void main(String[] args) {
        MongoDatabase db = MongoUtil.getDatabase(); // pastikan ada MongoUtil
        MongoCollection<Document> users = db.getCollection("users");

        String username = "admin";
        String password = "admin123"; // bisa kamu ganti
        String hashedPassword = HashUtil.hash(password);

        Document doc = new Document("username", username)
                .append("password", hashedPassword);

        users.insertOne(doc);
        System.out.println("✅ User admin berhasil dimasukkan ke MongoDB");
    }
}
