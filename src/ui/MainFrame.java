/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package ui;

import javax.swing.JOptionPane;
import manager.BookManager;
import model.Book;
import java.util.List;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.util.Locale;
import java.util.ResourceBundle;
import util.AutoBackupThread;
import util.Session;

/**
 *
 * @author boyblanco
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        setLanguage(new Locale("id"));
        // Coba baca backup dari file dan tampilkan jika database kosong
        List<Book> backupBooks = bookManager.loadBackup("books_backup.ser");

        if (!backupBooks.isEmpty() && bookManager.getAllBooks().isEmpty()) {
            for (Book book : backupBooks) {
                bookManager.addBook(book); // restore ke database MongoDB
            }
            System.out.println("📥 Data backup berhasil dimuat ke database.");
        }

        loadBooksToTable();

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                if (txtSearch.getText().trim().isEmpty()) {
                    loadBooksToTable();
                }
            }
        });

        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String filePath = "books_export.json"; // atau bisa pakai JFileChooser
                bookManager.exportToJson(filePath);
                JOptionPane.showMessageDialog(MainFrame.this, "Data berhasil diexport ke " + filePath, "Export Sukses", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        AutoBackupThread autoBackup = new AutoBackupThread(bookManager, "books_backup.ser");
        autoBackup.setDaemon(true); // supaya ikut mati saat aplikasi ditutup
        autoBackup.start();

        tblBooks.getSelectionModel().addListSelectionListener(e -> {
            int row = tblBooks.getSelectedRow();
            if (row >= 0) {
                txtTitle.setText(tblBooks.getValueAt(row, 0).toString());
                txtAuthor.setText(tblBooks.getValueAt(row, 1).toString());
                txtYear.setText(tblBooks.getValueAt(row, 2).toString());
                chkRead.setSelected("Ya".equals(tblBooks.getValueAt(row, 3).toString()));
                chkPrivate.setSelected("Ya".equals(tblBooks.getValueAt(row, 4).toString()));
            }
        });

        btnLogout = new javax.swing.JButton();
        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

    }

    private final BookManager bookManager = new BookManager();
    private ResourceBundle bundle;

    private void resetForm() {
        txtTitle.setText("");
        txtAuthor.setText("");
        txtYear.setText("");
        chkRead.setSelected(false);
        chkPrivate.setSelected(false);
    }

    private void loadBooksToTable() {
        List<Book> books = bookManager.getAllBooks();

        String[] columnNames = {
            bundle.getString("table.title"),
            bundle.getString("table.author"),
            bundle.getString("table.year"),
            bundle.getString("table.read"),
            bundle.getString("table.private")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Book book : books) {
            Object[] row = {
                book.getTitle(),
                book.getAuthor(),
                book.getYear(),
                book.isRead() ? bundle.getString("value.yes") : bundle.getString("value.no"),
                book.isPrivate() ? bundle.getString("value.yes") : bundle.getString("value.no")
            };
            model.addRow(row);
        }

        tblBooks.setModel(model);
    }

    private void setLanguage(Locale locale) {
        bundle = ResourceBundle.getBundle("resources.lang", locale);
        setTexts();           // update semua teks label, tombol, dll
        loadBooksToTable();   // ⬅️ reload isi tabel dengan bahasa baru
    }

    private void setTexts() {
        setTitle(bundle.getString("app.title"));
        lblTitle.setText(bundle.getString("label.title"));
        lblAuthor.setText(bundle.getString("label.author"));
        lblYear.setText(bundle.getString("label.year"));
        chkRead.setText(bundle.getString("label.read"));
        chkPrivate.setText(bundle.getString("label.private"));
        btnAdd.setText(bundle.getString("button.add"));
        lblSearch.setText(bundle.getString("label.search"));
        txtSearch.setToolTipText(bundle.getString("placeholder.search"));
        btnSearch.setText(bundle.getString("button.search"));
        btnEdit.setText(bundle.getString("button.update"));
        btnDelete.setText(bundle.getString("button.delete"));
        txtSearch.setText(""); // kosongkan isinya setiap ganti bahasa (optional)

        // 🔁 Ubah nama kolom tabel saat bahasa diganti
        String[] columnNames = {
            bundle.getString("table.title"),
            bundle.getString("table.author"),
            bundle.getString("table.year"),
            bundle.getString("table.read"),
            bundle.getString("table.private")
        };

        DefaultTableModel model = (DefaultTableModel) tblBooks.getModel();
        model.setColumnIdentifiers(columnNames);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblTitle = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        lblAuthor = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        lblYear = new javax.swing.JLabel();
        txtYear = new javax.swing.JTextField();
        chkRead = new javax.swing.JCheckBox();
        chkPrivate = new javax.swing.JCheckBox();
        btnAdd = new javax.swing.JButton();
        cmbLang = new javax.swing.JComboBox<>();
        lblSearch = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JToggleButton();
        btnExport = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblBooks = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lblTitle.setText("Judul Buku");

        lblAuthor.setText("Penulis");

        lblYear.setText("Tahun Terbit");

        chkRead.setText("Sudah Dibaca");

        chkPrivate.setText("Pribadi");

        btnAdd.setText("Tambah Buku");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        cmbLang.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Indonesia", "English" }));
        cmbLang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbLangActionPerformed(evt);
            }
        });

        lblSearch.setText("Cari Buku");

        txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSearchActionPerformed(evt);
            }
        });

        btnSearch.setText("Cari");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        btnExport.setText("Export JSON");
        btnExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExportActionPerformed(evt);
            }
        });

        btnEdit.setText("Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
            }
        });

        btnDelete.setText("Hapus");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnLogout.setText("Logout");
        btnLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogoutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTitle)
                            .addComponent(lblAuthor)
                            .addComponent(lblYear))
                        .addGap(8, 8, 8)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtTitle)
                            .addComponent(txtAuthor)
                            .addComponent(txtYear, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(chkRead)
                            .addComponent(chkPrivate)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cmbLang, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE))))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnLogout, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnExport, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE))
                        .addGap(40, 40, 40))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTitle)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkRead)
                    .addComponent(cmbLang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAuthor)
                    .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkPrivate)
                    .addComponent(lblSearch)
                    .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblYear)
                    .addComponent(btnSearch))
                .addGap(14, 14, 14)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnEdit)
                    .addComponent(btnDelete)
                    .addComponent(btnExport))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLogout, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tblBooks.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(tblBooks);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        // TODO add your handling code here:
        try {
            String title = txtTitle.getText().trim();
            String author = txtAuthor.getText().trim();
            int year = Integer.parseInt(txtYear.getText().trim());
            boolean isRead = chkRead.isSelected();
            boolean isPrivate = chkPrivate.isSelected();

            Book newBook = new Book(title, author, year, isRead, isPrivate, Session.getActiveUser());

            // Simpan ke MongoDB
            bookManager.addBook(newBook);

            JOptionPane.showMessageDialog(this, "Buku berhasil ditambahkan ke MongoDB!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            resetForm();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun harus berupa angka!", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        loadBooksToTable();

    }//GEN-LAST:event_btnAddActionPerformed

    private void cmbLangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbLangActionPerformed
        // TODO add your handling code here
        String lang = cmbLang.getSelectedItem().toString();
        if (lang.equals("Indonesia")) {
            setLanguage(new Locale("id"));
        } else {
            setLanguage(new Locale("en"));
        }
    }//GEN-LAST:event_cmbLangActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        String keyword = txtSearch.getText().trim().toLowerCase();

        List<Book> books = bookManager.getAllBooks(); // ambil semua data
        String[] columnNames = {"Judul", "Penulis", "Tahun", "Sudah Dibaca", "Pribadi"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(keyword)
                    || book.getAuthor().toLowerCase().contains(keyword)) {

                Object[] row = {
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear(),
                    book.isRead() ? "Ya" : "Tidak",
                    book.isPrivate() ? "Ya" : "Tidak"
                };
                model.addRow(row);
            }
        }

        tblBooks.setModel(model);

    }//GEN-LAST:event_btnSearchActionPerformed

    private void txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSearchActionPerformed

    private void btnExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExportActionPerformed
        // TODO add your handling code here:


    }//GEN-LAST:event_btnExportActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        // TODO add your handling code here:
        int row = tblBooks.getSelectedRow();
        if (row >= 0) {
            String oldTitle = tblBooks.getValueAt(row, 0).toString();
            String author = txtAuthor.getText().trim();
            int year = Integer.parseInt(txtYear.getText().trim());
            boolean isRead = chkRead.isSelected();
            boolean isPrivate = chkPrivate.isSelected();

            Book updatedBook = new Book(txtTitle.getText(), author, year, isRead, isPrivate, Session.getActiveUser());

            bookManager.updateBook(oldTitle, updatedBook);
            JOptionPane.showMessageDialog(this, "Buku berhasil diperbarui!", "Sukses", JOptionPane.INFORMATION_MESSAGE);

            resetForm();

            loadBooksToTable();
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin diedit.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        // TODO add your handling code here:
        int row = tblBooks.getSelectedRow();
        if (row >= 0) {
            String title = tblBooks.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin menghapus buku \"" + title + "\"?", "Konfirmasi", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bookManager.deleteBook(title);
                JOptionPane.showMessageDialog(this, "Buku berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loadBooksToTable();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus.", "Peringatan", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogoutActionPerformed
        // TODO add your handling code here:
        int confirm = JOptionPane.showConfirmDialog(this, "Yakin ingin logout?", "Konfirmasi Logout", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Session.setActiveUser(null); // clear session
            this.dispose(); // tutup MainFrame
            new LoginFrame().setVisible(true); // buka kembali login
        }

    }//GEN-LAST:event_btnLogoutActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnLogout;
    private javax.swing.JToggleButton btnSearch;
    private javax.swing.JCheckBox chkPrivate;
    private javax.swing.JCheckBox chkRead;
    private javax.swing.JComboBox<String> cmbLang;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAuthor;
    private javax.swing.JLabel lblSearch;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblYear;
    private javax.swing.JTable tblBooks;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtTitle;
    private javax.swing.JTextField txtYear;
    // End of variables declaration//GEN-END:variables
}
