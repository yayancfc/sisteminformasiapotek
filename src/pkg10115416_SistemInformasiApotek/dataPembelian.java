/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg10115416_SistemInformasiApotek;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import static pkg10115416_SistemInformasiApotek.dataObat.tableModel;

/**
 *
 * @author
     * Yayan Heryanto - 10115416
     * Iqbal Hasan - 10115567
     * Septa Farid Kurnia - 10115407
     * Nicko Novendestra - 10115257
 */
public class dataPembelian extends javax.swing.JInternalFrame {

    /**
     * Creates new form dataPembelian
     */
    Koneksi dbsetting;
    String driver, database, user, pass;    
    String data[] = new String[7];     
    int row, stok;
    String IDPembelian, IDSupplier, kodeObat, tanggal, harga, jumlah;      
    String sql;    
    
    public dataPembelian() {
        initComponents();
        
        dbsetting = new Koneksi();
        driver = dbsetting.settingPanel("DBDriver");
        database = dbsetting.settingPanel("DBDatabase");
        user = dbsetting.settingPanel("DBUsername");
        pass = dbsetting.settingPanel("DBPassword");
              
        hideTitleBar();
        tabelPembelian.setModel(tableModel);     
        desainTabel();
        setTableLoad();
        getData();
    }
    
    private void hideTitleBar() {
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI hide = this.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)hide).setNorthPane(null);
    }
    
    private void desainTabel() {
        tabelPembelian.getTableHeader().setBackground(new Color(255,255,255)); 
        tabelPembelian.setBackground(new Color(255,255,255));
        jScrollPane1.getViewport().setBackground(new Color(204,204,255));  
        
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);           
                                
        tabelPembelian.getColumnModel().getColumn(4).setCellRenderer(right);
        tabelPembelian.getColumnModel().getColumn(5).setCellRenderer(right);        
        tabelPembelian.getColumnModel().getColumn(6).setCellRenderer(right);                                     
        
        tabelPembelian.getColumnModel().getColumn(2).setPreferredWidth(175);
        tabelPembelian.getColumnModel().getColumn(3).setPreferredWidth(175);        
        tabelPembelian.getColumnModel().getColumn(5).setPreferredWidth(40);        
        tabelPembelian.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    
    // Membuat model tabel
    static javax.swing.table.DefaultTableModel getDefaultTableModel() {
        // Membuat header
        return new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {
            "ID Pembelian",
            "Tanggal Pembelian",
            "ID Supplier",            
            "Kode Obat",            
            "Harga",
            "Jumlah",
            "Total Harga"            
        }) {
            boolean[] Ubah = new boolean[] {
                false, false, false, false, false, false, false
            };
            
            public boolean UbahTabel(int Kolom) {
                return Ubah[Kolom];
            }
        };
    }
    
    // Inisialisasi tabel model
    static javax.swing.table.DefaultTableModel tableModel = getDefaultTableModel();
    
    // Menampilkan datatabe pada tabel
    private void setTableLoad() {
        //Identifier          
        try {
            //inisialisasi            
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from 10115416_apotekdb.t_transaksi_pembelian";
            ResultSet res = stt.executeQuery(SQL);  
                        
            tableModel.getDataVector().removeAllElements();
            
            while (res.next()) {
                data[0] = res.getString(1);
                data[1] = res.getString(2);
                data[2] = res.getString(3);
                data[3] = res.getString(4);
                data[4] = res.getString(5);
                data[5] = res.getString(6);
                data[6] = res.getString(7);
                
                tableModel.addRow(data);
            }            
            res.close();
            stt.close();
            kon.close();     
            
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            JOptionPane.showMessageDialog(null, ex.getMessage(),"Error",
                    JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        }
    }        
    
    private void getData() {
        tabelPembelian.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                try {         
                    row = tabelPembelian.getSelectedRow();
                    IDPembelian = tabelPembelian.getModel().getValueAt(row, 0).toString();
                    tanggal = tabelPembelian.getModel().getValueAt(row, 1).toString();
                    IDSupplier = tabelPembelian.getModel().getValueAt(row, 2).toString();
                    kodeObat = tabelPembelian.getModel().getValueAt(row, 3).toString();
                    harga = tabelPembelian.getModel().getValueAt(row, 4).toString();
                    jumlah = tabelPembelian.getModel().getValueAt(row, 5).toString();                                                                                
                    stok = Integer.parseInt(jumlah);
                } catch (Exception e) {

                }
            }
        });
    }                           

    private void getKategori(){
        int kategori = CB_kategoriCari.getSelectedIndex();
        if (kategori==1) {
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Id_Pembelian`='"+txt_cari.getText()+"'";
        } else if(kategori==2){
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Id_Supplier` ='"+txt_cari.getText()+"'";
        } else if(kategori==3){
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Kode_Obat` ='"+txt_cari.getText()+"'";
        } else if(kategori == 4) {
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Total` ='"+txt_cari.getText()+"'";
        } else if(kategori == 5) {
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Tanggal` LIKE '%"+txt_cari.getText() +"%'";        
        } else if(kategori == 6) {
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Harga` ='"+txt_cari.getText()+"'";        
        } else if(kategori == 7) {
            sql = "SELECT * FROM `t_transaksi_pembelian` WHERE `Jumlah` ='"+txt_cari.getText()+"'";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDesktopPane1 = new javax.swing.JDesktopPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabelPembelian = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        Ubah = new javax.swing.JLabel();
        Hapus = new javax.swing.JLabel();
        Tambah = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        CB_kategoriCari = new javax.swing.JComboBox<>();
        txt_cari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnRefresh = new javax.swing.JButton();

        setBorder(null);

        jDesktopPane1.setBackground(new java.awt.Color(204, 204, 255));
        jDesktopPane1.setPreferredSize(new java.awt.Dimension(1330, 571));

        jPanel1.setBackground(new java.awt.Color(204, 204, 255));

        jScrollPane1.setBackground(new java.awt.Color(204, 204, 255));

        tabelPembelian.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabelPembelian);

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        jPanel3.setBackground(new java.awt.Color(204, 204, 255));

        Ubah.setBackground(new java.awt.Color(255, 255, 255));
        Ubah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Ubah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Ubah.png"))); // NOI18N
        Ubah.setText("Ubah");
        Ubah.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                UbahMouseMoved(evt);
            }
        });
        Ubah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UbahMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                UbahMouseExited(evt);
            }
        });

        Hapus.setBackground(new java.awt.Color(255, 255, 255));
        Hapus.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Hapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Hapus.png"))); // NOI18N
        Hapus.setText("Hapus");
        Hapus.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                HapusMouseMoved(evt);
            }
        });
        Hapus.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                HapusMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                HapusMouseExited(evt);
            }
        });

        Tambah.setBackground(new java.awt.Color(255, 255, 255));
        Tambah.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Tambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/gambar/Tambah.png"))); // NOI18N
        Tambah.setText("Tambah");
        Tambah.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                TambahMouseMoved(evt);
            }
        });
        Tambah.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TambahMouseClicked(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TambahMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(Tambah, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Ubah, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(Hapus, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(129, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Hapus, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
            .addComponent(Ubah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Tambah, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jPanel5.setBackground(new java.awt.Color(204, 204, 255));

        jPanel4.setBackground(new java.awt.Color(204, 204, 255));

        CB_kategoriCari.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih Kategori", "ID Pembelian", "ID Supplier", "Kode Obat", "Total Harga", "Tanggal", "Harga Satuan", "Jumlah" }));

        txt_cari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_cariActionPerformed(evt);
            }
        });

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnRefresh.setText("Refresh");
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(CB_kategoriCari, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCari)
                .addGap(6, 6, 6)
                .addComponent(btnRefresh)
                .addGap(10, 10, 10))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CB_kategoriCari, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_cari, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRefresh, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 164, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 209, Short.MAX_VALUE)
                .addGap(0, 0, 0)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jDesktopPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 684, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UbahMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UbahMouseMoved
        // TODO add your handling code here:
        Ubah.setOpaque(true);
        Ubah.setBackground(new Color(204,204,255));
        Ubah.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_UbahMouseMoved

    private void UbahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UbahMouseClicked
        // TODO add your handling code here:
        try {
            int baris = tabelPembelian.getSelectedRow();

            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah terlebih dahulu");
            } else {                
                ubahPembelian ubah = new ubahPembelian(null, true,
                    IDPembelian, IDSupplier, kodeObat, tanggal, 
                    harga, jumlah, row, stok);
                ubah.pack();
                ubah.setLocationRelativeTo(this);
                ubah.setVisible(true);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_UbahMouseClicked

    private void UbahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UbahMouseExited
        // TODO add your handling code here:
        Ubah.setOpaque(true);
        Ubah.setBackground(new Color(204,204,255));
        Ubah.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_UbahMouseExited

    private void HapusMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HapusMouseMoved
        // TODO add your handling code here:
        Hapus.setOpaque(true);
        Hapus.setBackground(new Color(204,204,255));
        Hapus.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_HapusMouseMoved

    private void HapusMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HapusMouseClicked
        // TODO add your handling code here:
        try {
            int baris = tabelPembelian.getSelectedRow();

            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu");
            } else {                
                hapusPembelian hapus = new hapusPembelian(null, true, IDPembelian, 
                        IDSupplier, kodeObat, tanggal, harga, jumlah, row);
                hapus.pack();
                hapus.setLocationRelativeTo(this);
                hapus.setVisible(true);
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_HapusMouseClicked

    private void HapusMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_HapusMouseExited
        // TODO add your handling code here:
        Hapus.setOpaque(true);
        Hapus.setBackground(new Color(204,204,255));
        Hapus.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_HapusMouseExited

    private void TambahMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseMoved
        // TODO add your handling code here:
        Tambah.setOpaque(true);
        Tambah.setBackground(new Color(204,204,255));
        Tambah.setForeground(new Color(255,255,255));
    }//GEN-LAST:event_TambahMouseMoved

    private void TambahMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseClicked
        // TODO add your handling code here:
        tabelPembelian.clearSelection();        
        tambahPembelian tb = new tambahPembelian(null, true);
        tb.pack();
        tb.setLocationRelativeTo(this);
        tb.setVisible(true);
    }//GEN-LAST:event_TambahMouseClicked

    private void TambahMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TambahMouseExited
        // TODO add your handling code here:
        Tambah.setOpaque(true);
        Tambah.setBackground(new Color(204,204,255));
        Tambah.setForeground(new Color(0,0,0));
    }//GEN-LAST:event_TambahMouseExited

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        getKategori();
        if (CB_kategoriCari.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(null, "Kategori Pencarian Belum Dipilih");
        }else if(txt_cari.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Isi Data Obat Yang akan Dicari, Silahkan Ulangi");
        }
        else {
            tableModel.setRowCount(0);
            try {
                Class.forName(driver);
                Connection kon = DriverManager.getConnection(
                    database,
                    user,
                    pass
                );
                Statement stt = kon.createStatement();
                ResultSet res = stt.executeQuery(sql);
                while(res.next()){
                    data[0] = res.getString(1);
                    data[1] = res.getString(2);
                    data[2] = res.getString(3);
                    data[3] = res.getString(4);
                    data[4] = res.getString(5);
                    data[5] = res.getString(6);
                    data[6] = res.getString(7);                        
                    tableModel.addRow(data);
                }
                res.close();
                stt.close();
                kon.close();
            } catch (Exception e) {
                System.err.println(e.getMessage());
                JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        // TODO add your handling code here:
        setTableLoad();
        txt_cari.setText("");
        CB_kategoriCari.setSelectedIndex(0);
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txt_cariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_cariActionPerformed
        // TODO add your handling code here:
        btnCariActionPerformed(evt);
    }//GEN-LAST:event_txt_cariActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CB_kategoriCari;
    private javax.swing.JLabel Hapus;
    private javax.swing.JLabel Tambah;
    private javax.swing.JLabel Ubah;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabelPembelian;
    private javax.swing.JTextField txt_cari;
    // End of variables declaration//GEN-END:variables
}
