package util;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoUtil {
    private static final String URI = "mongodb+srv://boyblaco77:boyblaco77@mybookshelf.xpdslc9.mongodb.net/?retryWrites=true&w=majority&appName=MyBookShelf";
    private static final String DB_NAME = "bookshelf";

    public static MongoDatabase getDatabase() {
        MongoClient client = MongoClients.create(URI);
        return client.getDatabase(DB_NAME);
    }
}

//boyblaco77