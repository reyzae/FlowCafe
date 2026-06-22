/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package flowcafe;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.NumberFormat;
import java.util.Locale;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.Timer;


/**
 *
 * @author smailing
 */
public class FlowCafeForm extends javax.swing.JFrame {
    
    DefaultTableModel model;
    int nomorOrder = 1;
    int totalBayar = 0;
    boolean sedangFormatBayar = false;
    
    ArrayList<Long> waktuPesanan = new ArrayList<>();
    Timer timerStatus;
    
    String[] daftarMenu = {
        "Americano",
        "Ice Latte",
        "Cappuccino",
        "Matcha Latte",
        "Chocolate",
        "Roti Bakar",
        "Pisang Bakar",
        "French Fries"
    };
    
    int[] daftarHarga = {
        18000,
        25000,
        24000,
        26000,
        22000,
        20000,
        17000,
        18000
    };
    
      
    private int ambilAngkaRupiah(String teks) {
        teks = teks.replace("Rp", "")
                   .replace(".", "")
                   .replace(" ", "")
                   .trim();
        return Integer.parseInt(teks);
    }
    
    private String formatRupiah(int angka) {
        NumberFormat format = NumberFormat.getNumberInstance(new Locale("id", "ID"));
        return format.format(angka);
    
    }
    
    private void formatBayarOtomatis() {
        if (sedangFormatBayar) {
            return;
        }
        
        sedangFormatBayar = true;
        
        String teks = txtBayar.getText()
                .replace("Rp", "")
                .replace(".", "")
                .replace(" ", "")
                .trim();
        
        if (teks.isEmpty()) {
            sedangFormatBayar = false;
            return;
        }
        try {
            int angka = Integer.parseInt(teks);
            txtBayar.setText(formatRupiah(angka));
            txtBayar.setCaretPosition(txtBayar.getText().length());
        } catch (NumberFormatException e) {
            txtBayar.setText("");
        }
        
            sedangFormatBayar = false;
        }
    
    private void setupAwal() {
        setTitle("FLOWCAFE POS SYSTEM");
        setLocationRelativeTo(null);
        
        txtHarga.setEditable(false);
        txtTotal.setEditable(false);
        txtKembali.setEditable(false);
        
        txtStatusPesanan.setEditable(false);
        txtStatusPesanan.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtStatusPesanan.setBackground(java.awt.Color.WHITE);
        
        cmbTipe.removeAllItems();
        cmbTipe.addItem("Dine In");
        cmbTipe.addItem("Take Away");
        
        cmbMenu.removeAllItems();
        for (String menu : daftarMenu) {
            cmbMenu.addItem(menu);
        }
        
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
    };
        
        model.addColumn("No Order");
        model.addColumn("Nama");
        model.addColumn("Tipe");
        model.addColumn("Meja");
        model.addColumn("Menu");
        model.addColumn("Harga");
        model.addColumn("Jumlah");
        model.addColumn("Subtotal");
        model.addColumn("Status");
        model.addColumn("Bayar");
        model.addColumn("Kembali");
        
        tblOrder.setModel(model);
        
        tblOrder.getColumnModel().getColumn(9).setMinWidth(0);
        tblOrder.getColumnModel().getColumn(9).setMaxWidth(0);
        tblOrder.getColumnModel().getColumn(9).setWidth(0);
        
        tblOrder.getColumnModel().getColumn(10).setMinWidth(0);
        tblOrder.getColumnModel().getColumn(10).setMaxWidth(0);
        tblOrder.getColumnModel().getColumn(10).setWidth(0);
              
                
        tampilkanHargaMenu();
        
    }
    
    private void tampilkanDetailStatusPesanan(int baris) {
        if (baris < 0) {
            return;
        }
    
        String status = model.getValueAt(baris, 8).toString();
        String keterangan = "";
        
        if (status.equals("Order Masuk")) {
            keterangan = "Pesanan sudah masuk dan menunggu proses.";
        } else if (status.equals("Sedang Dibuat")) {
            keterangan = "Pesanan sedang dibuat, Mohon Tunggu.";
        } else if (status.equals("Siap Diantar")) {
            keterangan = "Pesanan sudah siap untuk diantar ke pelanggan.";
        } else if (status.equals("Selesai")) {
            keterangan = "Pesanan sudah selesai diproses";
        
        StringBuilder detail = new StringBuilder();

        detail.append("====== DETAIL STATUS PESANAN ======\n\n");
        detail.append(String.format("%-9s: %s\n", "No Order", model.getValueAt(baris, 0)));
        detail.append(String.format("%-9s: %s\n", "Nama", model.getValueAt(baris, 1)));
        detail.append(String.format("%-9s: %s\n", "Tipe", model.getValueAt(baris, 2)));
        detail.append(String.format("%-9s: %s\n", "Meja", model.getValueAt(baris, 3)));
        detail.append(String.format("%-9s: %s\n", "Menu", model.getValueAt(baris, 4)));
        detail.append(String.format("%-9s: %s\n", "Jumlah", model.getValueAt(baris, 6)));
        detail.append(String.format("%-9s: %s\n", "Subtotal", model.getValueAt(baris, 7)));
        detail.append(String.format("%-9s: %s\n", "Status", model.getValueAt(baris, 8)));
        detail.append("\nKeterangan:\n");
        detail.append(keterangan);

        txtStatusPesanan.setText(detail.toString());
        txtStatusPesanan.setCaretPosition(0);
        }
          
    }
    
    
    private void tampilkanHargaMenu() {
        int index = cmbMenu.getSelectedIndex();
        
        if (index >= 0) {
            txtHarga.setText(formatRupiah(daftarHarga[index]));
        }
    }
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FlowCafeForm.class.getName());

    /**
     * Creates new form FlowCafeForm
     */
    public FlowCafeForm() {
        initComponents();
        setupAwal();
        setSize(1200, 650);
        setLocationRelativeTo(null);
        mulaiAutoStatus();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtNama = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cmbTipe = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtMeja = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        cmbMenu = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtHarga = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtJumlah = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnTambah = new javax.swing.JButton();
        btnBayar = new javax.swing.JButton();
        btnStruk = new javax.swing.JButton();
        btnReset = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrder = new javax.swing.JTable();
        txtScroll = new javax.swing.JScrollPane();
        txtCatatan = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtBayar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtKembali = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtStatusPesanan = new javax.swing.JTextArea();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FLOW CAFE - POS SYSTEM");
        setPreferredSize(new java.awt.Dimension(900, 550));

        jLabel1.setText("Nama Pelanggan");

        txtNama.addActionListener(this::txtNamaActionPerformed);

        jLabel2.setText("Tipe Pesanan");

        cmbTipe.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("No. Meja");

        jLabel4.setText("Menu");

        cmbMenu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cmbMenu.addActionListener(this::cmbMenuActionPerformed);

        jLabel5.setText("Harga");

        txtHarga.setEditable(false);

        jLabel6.setText("Jumlah");

        jLabel7.setText("Catatan");

        btnTambah.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        btnTambah.setText("Tambah Pesanan");
        btnTambah.addActionListener(this::btnTambahActionPerformed);

        btnBayar.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        btnBayar.setText("Bayar");
        btnBayar.addActionListener(this::btnBayarActionPerformed);

        btnStruk.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        btnStruk.setText("Cetak Struk");
        btnStruk.addActionListener(this::btnStrukActionPerformed);

        btnReset.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        btnReset.setText("Reset");
        btnReset.addActionListener(this::btnResetActionPerformed);

        jLabel8.setFont(new java.awt.Font("Perpetua Titling MT", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(204, 51, 0));
        jLabel8.setText("FLOWCAFE POS SYSTEM");

        tblOrder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "No Order", "Nama", "Tipe", "Meja", "Menu", "Harga", "Jumlah", "Subtotal", "Status"
            }
        ));
        tblOrder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblOrderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblOrder);

        txtCatatan.setColumns(20);
        txtCatatan.setRows(5);
        txtScroll.setViewportView(txtCatatan);

        jLabel9.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(204, 0, 0));
        jLabel9.setText("TOTAL");

        txtTotal.addActionListener(this::txtTotalActionPerformed);

        jLabel10.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(204, 0, 0));
        jLabel10.setText("UANG BAYAR");

        txtBayar.addActionListener(this::txtBayarActionPerformed);
        txtBayar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBayarKeyReleased(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Microsoft New Tai Lue", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(204, 0, 0));
        jLabel11.setText("KEMBALIAN");

        txtStatusPesanan.setColumns(20);
        txtStatusPesanan.setRows(5);
        jScrollPane2.setViewportView(txtStatusPesanan);

        jLabel12.setFont(new java.awt.Font("Bookman Old Style", 0, 14)); // NOI18N
        jLabel12.setText("INPUT PESANAN");

        jLabel13.setFont(new java.awt.Font("Bookman Old Style", 0, 12)); // NOI18N
        jLabel13.setText("Status Pesanan");

        jLabel14.setFont(new java.awt.Font("Bookman Old Style", 0, 12)); // NOI18N
        jLabel14.setText("TABEL ORDERAN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBayar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnReset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtHarga)
                            .addComponent(cmbTipe, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtMeja)
                            .addComponent(cmbMenu, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtScroll, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                            .addComponent(txtJumlah)
                            .addComponent(txtNama)))
                    .addComponent(btnStruk, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtBayar, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTotal)
                            .addComponent(txtKembali))
                        .addGap(40, 40, 40)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 484, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13)))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel14)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 761, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel14))
                        .addGap(25, 25, 25)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(cmbTipe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtMeja, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(cmbMenu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtHarga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txtJumlah, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(txtScroll)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 371, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtBayar, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtKembali, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnTambah, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnBayar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnStruk, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnReset, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(0, 19, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNamaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNamaActionPerformed

    private void cmbMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbMenuActionPerformed
        tampilkanHargaMenu();        // TODO add your handling code here:
    }//GEN-LAST:event_cmbMenuActionPerformed

    private void btnTambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTambahActionPerformed
    
        String nama = txtNama.getText().trim();
        String tipe = cmbTipe.getSelectedItem().toString();
        String meja = txtMeja.getText().trim();
        String menu = cmbMenu.getSelectedItem().toString();
        String jumlahText = txtJumlah.getText().trim();
        
        if (nama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama Pelanggan harus diisi!");
            return;
        }
        
        if (jumlahText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Jumlah pesanan harus diisi!");
            return;
        }
        
        if (tipe.equals("Dine In") && meja.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nomor meja harus diisi untuk Dine In!");
            return;
        }
        
        try {
            int jumlah = Integer.parseInt(jumlahText);
            
            if (jumlah <=0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
                return;
            }
            
            int indexMenu = cmbMenu.getSelectedIndex();
            int harga = daftarHarga[indexMenu];
            int subtotal = harga * jumlah;
            
            String noOrder = String.format("ORDER-%03d", nomorOrder);
            
            if (tipe.equals("Take Away")) {
                meja = "-";
            }
            
            model.addRow(new Object[]{
                noOrder,
                nama,
                tipe,
                meja,
                menu,
                formatRupiah(harga),
                jumlah,
                formatRupiah(subtotal),
                "Order Masuk",
                "",
                ""
            });
            
            totalBayar = subtotal;
            txtTotal.setText(formatRupiah(totalBayar));
            
            txtBayar.setText("");
            txtKembali.setText("");
            
            int barisTerakhir = model.getRowCount() - 1;
            tblOrder.setRowSelectionInterval(barisTerakhir, barisTerakhir);
            pilihPesanan(barisTerakhir);
            
            waktuPesanan.add(System.currentTimeMillis());
                        
            nomorOrder++;
            
            txtJumlah.setText("");
            txtCatatan.setText("");
            
            JOptionPane.showMessageDialog(this, "Pesanan berhasil ditambahkan!");
            
               
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah harus berupa angka!");
        }
        
    }//GEN-LAST:event_btnTambahActionPerformed

    private void btnBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBayarActionPerformed
        int baris = tblOrder.getSelectedRow();
        
        if (baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan di tabel terlebih dahulu!");
            return;
        }
        
        String subtotalText = model.getValueAt(baris, 7).toString();
        totalBayar = ambilAngkaRupiah(subtotalText);
        txtTotal.setText(formatRupiah(totalBayar));
        
                      
        if (txtBayar.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Masukkan uang bayar!");
        return;
        }

    try {
        int uangBayar = ambilAngkaRupiah(txtBayar.getText());

        if (uangBayar < totalBayar) {
            JOptionPane.showMessageDialog(this, "Uang bayar kurang!");
        return;
        }

        int kembalian = uangBayar - totalBayar;
            
        String bayarFormat = formatRupiah(uangBayar);
        String kembaliFormat = formatRupiah(kembalian);
        
        txtBayar.setText(bayarFormat);
        txtKembali.setText(kembaliFormat);
        
        model.setValueAt(bayarFormat, baris, 9);
        model.setValueAt(kembaliFormat, baris, 10);

        JOptionPane.showMessageDialog(this, "Pembayaran berhasil!");

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Uang bayar harus berupa angka!");
       }
        
    }//GEN-LAST:event_btnBayarActionPerformed

    private void btnStrukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStrukActionPerformed
        int baris = tblOrder.getSelectedRow();
        
        if(baris == -1) {
            JOptionPane.showMessageDialog(this, "Pilih pesanan di tabel terlebih dahulu!");
            return;
        }
        
        String bayar = model.getValueAt(baris, 9).toString();
        String kembali = model.getValueAt(baris, 10).toString();
        
                
        //if (txtBayar.getText().isEmpty() || txtKembali.getText().isEmpty()) {
        //    JOptionPane.showMessageDialog(this, "Lakukan pembayaran terlebih dahulu!");
        //return;
        //}
       
        String totalStruk = model.getValueAt(baris, 7).toString();
        
        String struk = StrukGenerator.buatStruk(
               model,
               baris,
               totalStruk,
               bayar,
               kembali
        );
          
        StrukDialog dialog = new StrukDialog(this, true, struk);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnStrukActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
        txtNama.setText("");
        txtMeja.setText("");
        txtJumlah.setText("");
        txtCatatan.setText("");
        
        cmbTipe.setSelectedIndex(0);
        cmbMenu.setSelectedIndex(0);
        
        tampilkanHargaMenu();
        
        txtTotal.setText("");
        txtBayar.setText("");
        txtKembali.setText("");
        txtStatusPesanan.setText("");

        totalBayar = 0;

        JOptionPane.showMessageDialog(this, "Data berhasil dibersihkan!");
    }//GEN-LAST:event_btnResetActionPerformed
       
    private void txtBayarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBayarActionPerformed
        if (!txtBayar.getText().trim().isEmpty()) {
            try {
                int uang = ambilAngkaRupiah(txtBayar.getText());
                txtBayar.setText(formatRupiah(uang));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Uang bayar harus berupa angka!");
                txtBayar.setText("");
            }
        }
    }//GEN-LAST:event_txtBayarActionPerformed

    private void hitungTotalOtomatis() {
        totalBayar = 0;

    for (int i = 0; i < model.getRowCount(); i++) {
        String subtotalText = model.getValueAt(i, 7).toString();
        int subtotal = ambilAngkaRupiah(subtotalText);

        totalBayar += subtotal;
    }

        txtTotal.setText(formatRupiah(totalBayar));
    }
    
    private void txtBayarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBayarKeyReleased
        formatBayarOtomatis();
    }//GEN-LAST:event_txtBayarKeyReleased

    private void txtTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTotalActionPerformed
        
    }//GEN-LAST:event_txtTotalActionPerformed

    private void tblOrderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblOrderMouseClicked
        int baris = tblOrder.getSelectedRow();
        
        if (baris >= 0) {
            pilihPesanan(baris);
        }
    }//GEN-LAST:event_tblOrderMouseClicked

    private void mulaiAutoStatus() {
         timerStatus = new Timer(1000, e -> {
            long waktuSekarang = System.currentTimeMillis();

        for (int i = 0; i < model.getRowCount(); i++) {
            long selisih = (waktuSekarang - waktuPesanan.get(i)) / 1000;

        String statusBaru;

            if (selisih >= 30) {
                statusBaru = "Selesai";
            } else if (selisih >= 20) {
                statusBaru = "Siap Diantar";
            } else if (selisih >= 10) {
                statusBaru = "Sedang Dibuat";
            } else {
                statusBaru = "Order Masuk";
            }

            model.setValueAt(statusBaru, i, 8);
             }
       });

            timerStatus.start();
    }
    
    private void pilihPesanan(int baris) {
        if (baris < 0) {
            return;
        }
        
        String subtotalText = model.getValueAt(baris, 7).toString();
        
        totalBayar = ambilAngkaRupiah(subtotalText);         
        txtTotal.setText(formatRupiah(totalBayar));
        
        String bayar = model.getValueAt(baris, 9).toString();
        String kembali = model.getValueAt(baris, 10).toString();
        
        txtBayar.setText("");
        txtKembali.setText("");
        
        tampilkanDetailStatusPesanan(baris);
    }
    
    
    
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new FlowCafeForm().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBayar;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnStruk;
    private javax.swing.JButton btnTambah;
    private javax.swing.JComboBox<String> cmbMenu;
    private javax.swing.JComboBox<String> cmbTipe;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblOrder;
    private javax.swing.JTextField txtBayar;
    private javax.swing.JTextArea txtCatatan;
    private javax.swing.JTextField txtHarga;
    private javax.swing.JTextField txtJumlah;
    private javax.swing.JTextField txtKembali;
    private javax.swing.JTextField txtMeja;
    private javax.swing.JTextField txtNama;
    private javax.swing.JScrollPane txtScroll;
    private javax.swing.JTextArea txtStatusPesanan;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
