package manager;

import com.mongodb.client.FindIterable;
import model.Book;
import util.MongoUtil;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.List;
import java.util.ArrayList;
import org.bson.Document;
import util.CryptoUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class BookManager {

    private MongoCollection<Document> collection;

    public BookManager() {
        MongoDatabase db = MongoUtil.getDatabase();
        collection = db.getCollection("books");
    }

    public void addBook(Book book) {
        try {
            String title = book.getTitle();
            if (book.isPrivate()) {
                title = CryptoUtil.encrypt(title); // enkripsi kalau private
            }

            Document doc = new Document("title", title) // ✅ pakai variable `title` hasil enkripsi
                    .append("author", book.getAuthor())
                    .append("year", book.getYear())
                    .append("isRead", book.isRead())
                    .append("isPrivate", book.isPrivate())
                    .append("username", book.getUsername());

            collection.insertOne(doc);
        } catch (Exception e) {
            System.out.println("Gagal menyimpan buku: " + e.getMessage());
        }
    }

    public List<Book> getAllBooks() {
        List<Book> list = new ArrayList<>();
        String currentUser = util.Session.getActiveUser(); // ambil user aktif

        // Cari dokumen buku milik user yang sedang login
        FindIterable<Document> docs = collection.find(new Document("username", currentUser));

        for (Document doc : docs) {
            String title = doc.getString("title");
            boolean isPrivate = doc.getBoolean("isPrivate", false);

            if (isPrivate) {
                try {
                    title = CryptoUtil.decrypt(title);
                } catch (Exception e) {
                    title = "[Encrypted]";
                }
            }

            String author = doc.getString("author");
            int year = doc.getInteger("year", 0);
            boolean isRead = doc.getBoolean("isRead", false);

            // Tambahkan username ke objek Book
            list.add(new Book(title, author, year, isRead, isPrivate, currentUser));
        }

        return list;
    }

    public void exportToJson(String filePath) {
        List<Book> books = getAllBooks();
        JSONArray jsonArray = new JSONArray();

        for (Book book : books) {
            JSONObject obj = new JSONObject();
            obj.put("title", book.getTitle());
            obj.put("author", book.getAuthor());
            obj.put("year", book.getYear());
            obj.put("isRead", book.isRead());
            obj.put("isPrivate", book.isPrivate());
            jsonArray.put(obj);
        }

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonArray.toString(4)); // Pretty print with indent
            System.out.println("✅ Berhasil export ke " + filePath);
        } catch (IOException e) {
            System.err.println("❌ Gagal export JSON: " + e.getMessage());
        }
    }

    public void updateBook(String oldTitle, Book updatedBook) {
        String currentUser = updatedBook.getUsername();

        // Cari dokumen lama berdasarkan username & judul (plaintext atau terenkripsi)
        Document found = collection.find(new Document("username", currentUser)).first();
        String encryptedOldTitle = null;

        for (Document doc : collection.find(new Document("username", currentUser))) {
            String title = doc.getString("title");
            boolean isPrivate = doc.getBoolean("isPrivate", false);
            String decryptedTitle = title;

            if (isPrivate) {
                try {
                    decryptedTitle = CryptoUtil.decrypt(title);
                } catch (Exception e) {
                    decryptedTitle = "[Encrypted]";
                }
            }

            if (decryptedTitle.equals(oldTitle)) {
                encryptedOldTitle = title;
                break;
            }
        }

        if (encryptedOldTitle == null) {
            System.err.println("❌ Gagal update: Buku dengan judul \"" + oldTitle + "\" tidak ditemukan.");
            return;
        }

        // Handle new title
        String newTitle = updatedBook.getTitle();
        if (updatedBook.isPrivate()) {
            try {
                newTitle = CryptoUtil.encrypt(newTitle);
            } catch (Exception e) {
                System.err.println("Encrypt Error: " + e.getMessage());
            }
        }

        Document query = new Document("title", encryptedOldTitle).append("username", currentUser);

        Document updated = new Document("$set", new Document("title", newTitle)
                .append("author", updatedBook.getAuthor())
                .append("year", updatedBook.getYear())
                .append("isRead", updatedBook.isRead())
                .append("isPrivate", updatedBook.isPrivate()));

        collection.updateOne(query, updated);
    }

    public void deleteBook(String title) {
        String currentUser = util.Session.getActiveUser();

        // Cari semua buku milik user
        for (Document doc : collection.find(new Document("username", currentUser))) {
            String dbTitle = doc.getString("title");
            boolean isPrivate = doc.getBoolean("isPrivate", false);
            String decryptedTitle = dbTitle;

            if (isPrivate) {
                try {
                    decryptedTitle = CryptoUtil.decrypt(dbTitle);
                } catch (Exception e) {
                    decryptedTitle = "[Encrypted]";
                }
            }

            if (decryptedTitle.equals(title)) {
                // Ditemukan! Hapus berdasarkan title asli di DB
                collection.deleteOne(new Document("title", dbTitle).append("username", currentUser));
                System.out.println("✅ Buku berhasil dihapus: " + decryptedTitle);
                return;
            }
        }

        System.err.println("❌ Gagal hapus: Buku \"" + title + "\" tidak ditemukan.");
    }

    @SuppressWarnings("unchecked")
    public List<Book> loadBackup(String backupPath) {
        List<Book> books = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(backupPath))) {
            books = (List<Book>) ois.readObject();
            System.out.println("✅ Backup berhasil dimuat dari: " + backupPath);
        } catch (Exception e) {
            System.err.println("❌ Gagal memuat backup: " + e.getMessage());
        }
        return books;
    }

}
