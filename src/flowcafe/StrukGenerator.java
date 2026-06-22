package flowcafe;

import javax.swing.table.DefaultTableModel;

public class StrukGenerator {

    public static String buatStruk(DefaultTableModel model, int baris, String total, String bayar, String kembali) {
        StringBuilder struk = new StringBuilder();

        struk.append("====================================\n");
        struk.append("              FLOWCAFE\n");
        struk.append("           POS CAFE SYSTEM\n");
        struk.append("====================================\n\n");

        struk.append(String.format("%-10s: %s\n", "No Order", model.getValueAt(baris, 0)));
        struk.append(String.format("%-10s: %s\n", "Nama", model.getValueAt(baris, 1)));
        struk.append(String.format("%-10s: %s\n", "Tipe", model.getValueAt(baris, 2)));
        struk.append(String.format("%-10s: %s\n", "Meja", model.getValueAt(baris, 3)));
        struk.append(String.format("%-10s: %s\n", "Menu", model.getValueAt(baris, 4)));
        struk.append(String.format("%-10s: %s\n", "Harga", model.getValueAt(baris, 5)));
        struk.append(String.format("%-10s: %s\n", "Jumlah", model.getValueAt(baris, 6)));
        struk.append(String.format("%-10s: %s\n", "Subtotal", model.getValueAt(baris, 7)));
        struk.append(String.format("%-10s: %s\n", "Status", model.getValueAt(baris, 8)));

        struk.append("------------------------------------\n\n");
        struk.append(String.format("%-10s: %s\n", "Total", total));
        struk.append(String.format("%-10s: %s\n", "Bayar", bayar));
        struk.append(String.format("%-10s: %s\n", "Kembali", kembali));

        struk.append("\n");
        struk.append("Kode order dapat digunakan\n");
        struk.append("untuk tracking pesanan.\n\n");
        struk.append("Terima kasih sudah berkunjung.\n");

        return struk.toString();
    }
}