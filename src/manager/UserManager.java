package manager;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import model.User;
import org.bson.Document;
import util.HashUtil;
import util.MongoUtil;

public class UserManager {

    private final MongoCollection<Document> collection;

    public UserManager() {
        MongoDatabase db = MongoUtil.getDatabase();
        collection = db.getCollection("users");
    }

    public boolean register(String username, String plainPassword) {
        if (findUser(username) != null) {
            return false;
        }

        String hash = HashUtil.hash(plainPassword);
        Document doc = new Document("username", username)
                .append("password", hash);
        collection.insertOne(doc);
        return true;
    }

    public Document findUser(String username) {
        return collection.find(new Document("username", username)).first();
    }

    public boolean login(String username, String plainPassword) {
        Document userDoc = collection.find(new Document("username", username)).first();
        if (userDoc == null) {
            return false;
        }

        String savedHash = userDoc.getString("password");
        System.out.println("Input (hashed): " + HashUtil.hash(plainPassword));
        System.out.println("Saved from DB : " + savedHash);

        return savedHash.equals(HashUtil.hash(plainPassword)); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
