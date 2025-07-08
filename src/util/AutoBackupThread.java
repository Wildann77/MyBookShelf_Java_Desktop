package util;

import manager.BookManager;
import model.Book;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class AutoBackupThread extends Thread {
    private final BookManager bookManager;
    private final String backupPath;

    public AutoBackupThread(BookManager bookManager, String backupPath) {
        this.bookManager = bookManager;
        this.backupPath = backupPath;
    }

    @Override
    public void run() {
        while (true) {
            try {
                List<Book> books = bookManager.getAllBooks();
                FileOutputStream fos = new FileOutputStream(backupPath);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(books);
                oos.close();
                System.out.println("📦 Auto-backup selesai: " + backupPath);

                Thread.sleep(1 * 60 * 1000); // 1 menit
            } catch (Exception e) {
                System.err.println("⚠️ Gagal backup: " + e.getMessage());
            }
        }
    }
}
