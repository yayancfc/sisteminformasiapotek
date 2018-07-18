/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg10115416_SistemInformasiApotek;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
/**
 *
 * @author
     * Yayan Heryanto - 10115416
     * Iqbal Hasan - 10115567
     * Septa Farid Kurnia - 10115407
     * Nicko Novendestra - 10115257
 */
public class transaksiPenjualan extends javax.swing.JInternalFrame {

    /**
     * Creates new form Penjualan
     */  
    Koneksi dbsetting;
    String driver, database, user, pass;
    Object tabel;
    Connection kon;
    Statement stt;
    ResultSet res;
    PreparedStatement ps;
    String sql;         
    String kodeObat, jumlah;
    int row;
    
    public transaksiPenjualan() {        
        initComponents();
        
        dbsetting = new Koneksi();
        driver = dbsetting.settingPanel("DBDriver");
        database = dbsetting.settingPanel("DBDatabase");
        user = dbsetting.settingPanel("DBUsername");
        pass = dbsetting.settingPanel("DBPassword");
        
        tabelPenjualan.setModel(tableModel);
        hideTitleBar();
        desainTabel();
        setDesanTxt();
        nonAktifButton();
        getData();   
        getIDKaryawan();
        getTanggal();
        getKey();
        setHargaKembali();
    }    
    
    private void getKey(){
        Action tambah = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTambahActionPerformed(e);
            }
        };
        
        KeyStroke keyTambah = KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK); 
        btnTambah.getActionMap().put("tambah", tambah);
        btnTambah.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyTambah, "tambah");
        
        Action simpan = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnSimpanActionPerformed(e);
            }
        };
        
        KeyStroke keySimpan = KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK); 
        btnSimpan.getActionMap().put("simpan", simpan);
        btnSimpan.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keySimpan, "simpan");
        
        Action hapus = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHapusActionPerformed(e);
            }
        };
        
        KeyStroke keyHapus = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, Event.CTRL_MASK); 
        btnHapus.getActionMap().put("hapus", hapus);
        btnHapus.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyHapus, "hapus");
        
        Action tambahItem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnTambahItemActionPerformed(e);
            }
        };
        
        KeyStroke keyTambahItem = KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0); 
        btnTambahItem.getActionMap().put("tambahItem", tambahItem);
        btnTambahItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyTambahItem, "tambahItem");
        
        Action editItem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnUbahItemActionPerformed(e);
            }
        };
        
        KeyStroke keyEditItem = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0); 
        btnUbahItem.getActionMap().put("editItem", editItem);
        btnUbahItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyEditItem, "editItem");
        
        Action hapusItem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnHapusItemActionPerformed(e);
            }
        };
        
        KeyStroke keyHapusItem = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0); 
        btnHapusItem.getActionMap().put("hapusItem", hapusItem);
        btnHapusItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyHapusItem, "hapusItem");
        
    }
    
    private void hideTitleBar() {
        setRootPaneCheckingEnabled(false);
        javax.swing.plaf.InternalFrameUI hide = this.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI)hide).setNorthPane(null);
    }
    
    private void desainTabel() {
        tabelPenjualan.getTableHeader().setBackground(new Color(255,255,255)); 
        tabelPenjualan.setBackground(new Color(255,255,255));
        jScrollPane2.getViewport().setBackground(new Color(204,204,255));  
        
        DefaultTableCellRenderer right = new DefaultTableCellRenderer();
        right.setHorizontalAlignment(JLabel.RIGHT);           
                                
        tabelPenjualan.getColumnModel().getColumn(1).setCellRenderer(right);
        tabelPenjualan.getColumnModel().getColumn(2).setCellRenderer(right);
        tabelPenjualan.getColumnModel().getColumn(3).setCellRenderer(right);                                                   
                        
        tabelPenjualan.getColumnModel().getColumn(2).setPreferredWidth(40);        
        tabelPenjualan.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    }
    
    // Membuat model tabel
    static javax.swing.table.DefaultTableModel getDefaultTableModel() {
        // Membuat header
        return new javax.swing.table.DefaultTableModel(new Object[][] {}, new String[] {  
            "Kode Obat",            
            "Harga Satuan",                        
            "Jumlah",
            "Total Harga"            
        }) {
            boolean[] Ubah = new boolean[] {
                false, false, false, false
            };
            
            public boolean UbahTabel(int Kolom) {
                return Ubah[Kolom];
            }
        };
    }        
    
    private void setDesanTxt() {
        txtIDPenjualan.setBackground(new Color(255,255,255));        
        txtTanggal.setBackground(new Color(255,255,255));
        txtTotal.setBackground(new Color(255,255,255));
        txtBayar.setBackground(new Color(255,255,255));
        txtKembali.setBackground(new Color(255,255,255));   
        
        txtIDPenjualan.setDisabledTextColor(new Color(0,0,0));        
        txtTanggal.setDisabledTextColor(new Color(0,0,0));
        txtTotal.setDisabledTextColor(new Color(0,0,0));
        txtBayar.setDisabledTextColor(new Color(0,0,0));
        txtKembali.setDisabledTextColor(new Color(0,0,0));
    }
    
    // Inisialisasi tabel model
    static javax.swing.table.DefaultTableModel tableModel = getDefaultTableModel();
    
    private void nonAktifButton() {
        btnSimpan.setEnabled(false);
        btnBayar.setEnabled(false);
        btnHapus.setEnabled(false);
        btnTambahItem.setEnabled(false);
        btnUbahItem.setEnabled(false);
        btnHapusItem.setEnabled(false);
    }
    
    private void aktifButton() {
        btnSimpan.setEnabled(true);
        btnBayar.setEnabled(true);
        btnHapus.setEnabled(true);
        btnTambahItem.setEnabled(true);
        btnUbahItem.setEnabled(true);
        btnHapusItem.setEnabled(true);
    }
    
     private void getData() {
        tabelPenjualan.getSelectionModel().addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent event) {
                // do some actions here, for example
                // print first column value from selected row
                try {         
                    row = tabelPenjualan.getSelectedRow();
                    kodeObat = tabelPenjualan.getModel().getValueAt(row, 0).toString();
                    jumlah = tabelPenjualan.getModel().getValueAt(row, 2).toString();
                } catch (Exception e) {

                }
            }
        });
    } 
     
    private void resetData() {
        try {
            txtIDPenjualan.setText("");            
            txtTanggal.setText("");
            txtTotal.setText("");
            txtBayar.setText("");
            txtKembali.setText("");
            tableModel.setRowCount(0);
        } catch (Exception e) {
        }
    }
    
    private void totalHarga() {        
        int rows = tableModel.getRowCount();
            
        double totalHarga = 0;
                  
        for (int i = 0; i < rows; i++) {            
            String harga = tableModel.getValueAt(i, 3).toString();
            totalHarga += Double.parseDouble(harga);
        }                      
            
        txtTotal.setText(String.valueOf(totalHarga));
    }
    
    private void getIDKaryawan() {
        try {
            //inisialisasi            
            String data;
            
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from 10115416_apotekdb.t_karyawan";
            ResultSet res = stt.executeQuery(SQL);  
            while (res.next()) {
                data = res.getString(1);
                txtIDKaryawan.addItem(data);
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
    
    public void getTanggal() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                Locale id = new Locale("in", "ID");
                SimpleDateFormat tgl = new SimpleDateFormat("yyyy-MM-dd", id);                
                Date dt = new Date();                            
                txtTanggal.setText(tgl.format(dt));
            }
        };
        new javax.swing.Timer(1000, taskPerformer).start();
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
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelPenjualan = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        txtTotal = new javax.swing.JTextField();
        jLabel49 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        jPanel23 = new javax.swing.JPanel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        txtIDPenjualan = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        txtTanggal = new javax.swing.JTextField();
        txtIDKaryawan = new javax.swing.JComboBox<>();
        jPanel24 = new javax.swing.JPanel();
        btnTambahItem = new javax.swing.JButton();
        btnUbahItem = new javax.swing.JButton();
        btnHapusItem = new javax.swing.JButton();
        jPanel17 = new javax.swing.JPanel();
        btnTambah = new javax.swing.JButton();
        btnSimpan = new javax.swing.JButton();
        btnHapus = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();

        setBorder(null);

        jDesktopPane1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(204, 204, 255));

        tabelPenjualan.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(tabelPenjualan);

        jPanel15.setBackground(new java.awt.Color(204, 204, 255));
        jPanel15.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel15.setPreferredSize(new java.awt.Dimension(364, 150));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel47.setText("Total");

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel48.setText("Bayar");

        txtBayar.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtBayar.setHorizontalAlignment(javax.swing.JTextField.RIGHT);

        txtTotal.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setEnabled(false);

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel49.setText("Kembali");

        txtKembali.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        txtKembali.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtKembali.setEnabled(false);

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel49)
                    .addComponent(jLabel48)
                    .addComponent(jLabel47))
                .addGap(20, 20, 20)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtBayar, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtTotal)
                    .addComponent(txtKembali))
                .addGap(20, 20, 20))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47))
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48))
                .addGap(11, 11, 11)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel23.setBackground(new java.awt.Color(204, 204, 255));
        jPanel23.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel50.setBackground(new java.awt.Color(255, 255, 255));
        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel50.setText("ID Penjualan");

        jLabel51.setBackground(new java.awt.Color(255, 255, 255));
        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel51.setText("ID Karyawan");

        txtIDPenjualan.setEnabled(false);

        jLabel66.setBackground(new java.awt.Color(255, 255, 255));
        jLabel66.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel66.setText("Tanggal");

        txtTanggal.setEnabled(false);

        txtIDKaryawan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pilih" }));

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel50)
                    .addComponent(jLabel51)
                    .addComponent(jLabel66))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 79, Short.MAX_VALUE)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(txtTanggal)
                    .addComponent(txtIDPenjualan, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txtIDKaryawan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel23Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIDPenjualan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50))
                .addGap(13, 13, 13)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(txtIDKaryawan, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(13, 13, 13)
                .addGroup(jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel66)
                    .addComponent(txtTanggal, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel24.setBackground(new java.awt.Color(204, 204, 255));

        btnTambahItem.setText("Tambah Item");
        btnTambahItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahItemActionPerformed(evt);
            }
        });

        btnUbahItem.setText("Ubah Item");
        btnUbahItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUbahItemActionPerformed(evt);
            }
        });

        btnHapusItem.setText("Hapus Item");
        btnHapusItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(btnTambahItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnUbahItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnHapusItem, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambahItem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnUbahItem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapusItem, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        jPanel17.setBackground(new java.awt.Color(204, 204, 255));
        jPanel17.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnTambah.setText("Tambah Transaksi");
        btnTambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTambahActionPerformed(evt);
            }
        });

        btnSimpan.setText("Simpan Transaksi");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        btnHapus.setText("Hapus Transaksi");
        btnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHapusActionPerformed(evt);
            }
        });

        btnBayar.setText("Bayar");
        btnBayar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBayarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnHapus, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(11, 11, 11))
            .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(11, 11, 11)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        jDesktopPane1.setLayer(jPanel2, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout jDesktopPane1Layout = new javax.swing.GroupLayout(jDesktopPane1);
        jDesktopPane1.setLayout(jDesktopPane1Layout);
        jDesktopPane1Layout.setHorizontalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jDesktopPane1Layout.setVerticalGroup(
            jDesktopPane1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jDesktopPane1)
                .addGap(0, 0, 0))
        );

        jDesktopPane1.getAccessibleContext().setAccessibleName("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnTambahItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahItemActionPerformed
        // TODO add your handling code here:
        try {
            tambahItemPenjualan tambahItem = new tambahItemPenjualan(null, true);
            tambahItem.pack();
            tambahItem.setLocationRelativeTo(this);
            tambahItem.setVisible(true);      
            totalHarga();
        } catch (Exception e) {
        }        
    }//GEN-LAST:event_btnTambahItemActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
        // TODO add your handling code here: 
        try {
            btnTambah.setEnabled(false);
            int i = 1;
            
            Class.forName(driver);
            Connection kon = DriverManager.getConnection(database, user, pass);
            Statement stt = kon.createStatement();
            String SQL = "select * from 10115416_apotekdb.t_transaksi_penjualan";
            ResultSet res = stt.executeQuery(SQL);            
            while (res.next()) {
                i++;
            }       
            
            txtIDPenjualan.setText(String.valueOf(i));
            aktifButton();
        } catch (ClassNotFoundException classNotFoundException) {
        } catch (SQLException sQLException) {
        }
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnUbahItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUbahItemActionPerformed
        // TODO add your handling code here:
        try {
            int baris = tabelPenjualan.getSelectedRow();

            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan diubah terlebih dahulu");
            } else {                
                ubahItemPenjualan ubahItem = new ubahItemPenjualan(null, true, 
                        kodeObat, jumlah, row);
                ubahItem.pack();
                ubahItem.setLocationRelativeTo(this);
                ubahItem.setVisible(true);
                totalHarga();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnUbahItemActionPerformed

    private void btnHapusItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusItemActionPerformed
        // TODO add your handling code here:
        try {
            int baris = tabelPenjualan.getSelectedRow();

            if (baris == -1) {
                JOptionPane.showMessageDialog(this, "Pilih data yang akan dihapus terlebih dahulu");
            } else {                
                hapusItemPenjualan hapusItem = new hapusItemPenjualan(null, true, 
                        kodeObat, jumlah, row);
                hapusItem.pack();
                hapusItem.setLocationRelativeTo(this);
                hapusItem.setVisible(true);
                totalHarga();
            }            
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnHapusItemActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        try {
            if ((tableModel.getRowCount() == 0)
                    || (txtBayar.getText().equals(""))
                    || (txtIDKaryawan.getSelectedIndex() == 0)) {
                JOptionPane.showMessageDialog(this, "Lengkapi data terlebih dahulu");
            } else {
                try {
                    Class.forName(driver);
                    kon = DriverManager.getConnection(database, user, pass);
                    stt = kon.createStatement();
                    
                    String IDPenjualan = txtIDPenjualan.getText();
                    String IDKaryawan = txtIDKaryawan.getSelectedItem().toString();                    
                    String tanggal = txtTanggal.getText();
                    String totalHarga = txtTotal.getText();
                    
                    sql = "INSERT INTO `10115416_apotekdb`.`t_transaksi_penjualan` "
                            + "(`Id_Penjualan`,"
                            + " `Tanggal`,"
                            + " `Id_Karyawan`,"
                            + " `Total_Harga`) "
                            + "VALUES ('" +IDPenjualan+ "',"
                            + " '" +tanggal+ "',"
                            + " '" +IDKaryawan+ "',"
                            + " '" +totalHarga+ "')";                    
                                        
                    stt.execute(sql);
                    
                    int rows = tableModel.getRowCount();
                    
                    for (int i = 0; i < rows; i++) {
                        String kodeObat = tableModel.getValueAt(i, 0).toString();
                        String harga = tableModel.getValueAt(i, 1).toString();
                        String jumlah = tableModel.getValueAt(i, 2).toString();
                        String total = tableModel.getValueAt(i, 3).toString();
                                                
                        String sql1 = "INSERT INTO `10115416_apotekdb`.`t_detail_penjualan` "
                                + "(`Id_Penjualan`,"
                                + " `Kode_Obat`,"
                                + " `Harga_Satuan`,"
                                + " `Jumlah`,"
                                + " `Total_Harga`)"
                                + "VALUES ('" + IDPenjualan + "',"
                                + " '" + kodeObat + "',"
                                + " '" + harga + "',"
                                + " '" + jumlah + "',"
                                + " '" + total + "');";                                                
                        
                        String sql2 = "UPDATE `10115416_apotekdb`.`t_obat` SET `Stok` = `Stok` - '"+jumlah+"'"
                                + "WHERE `Kode_Obat` = '"+kodeObat+"'";
                        
                        stt.execute(sql1);
                        stt.execute(sql2);                        
                    }
                    
                    JOptionPane.showMessageDialog(this, "Sukses menyimpan data");
                    resetData();
                    
                    stt.close();
                    kon.close();
                } catch (ClassNotFoundException classNotFoundException) {
                }
            }
        } catch (HeadlessException headlessException) {
        } catch (SQLException ex) {
            Logger.getLogger(transaksiPenjualan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    private void btnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHapusActionPerformed
        // TODO add your handling code here:
        try {
            if (JOptionPane.showConfirmDialog(this, "Ingin menghapus data penjualan?") == 0) {                
                resetData();
            }
        } catch (Exception e) {
        }
    }//GEN-LAST:event_btnHapusActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        // TODO add your handling code here:
        try {            
            txtBayar.requestFocus();            
        } catch (Exception e) {
        } 
    }//GEN-LAST:event_btnBayarActionPerformed

    private void setHargaKembali(){
        txtBayar.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                getHargaKembali();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                getHargaKembali();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                getHargaKembali();
            }
        });
    }
    
   public void getHargaKembali(){
        try {
            double Pembayaran = Double.parseDouble(txtBayar.getText());
            double Total = Double.parseDouble(txtTotal.getText());
            double Kembali = Pembayaran - Total;
            txtKembali.setText(String.valueOf(Kembali));
        } catch (NumberFormatException numberFormatException) {
                }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnHapus;
    private javax.swing.JButton btnHapusItem;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JButton btnTambah;
    private javax.swing.JButton btnTambahItem;
    private javax.swing.JButton btnUbahItem;
    private javax.swing.JDesktopPane jDesktopPane1;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tabelPenjualan;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JComboBox<String> txtIDKaryawan;
    private javax.swing.JTextField txtIDPenjualan;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtTanggal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
