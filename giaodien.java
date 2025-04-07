import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class giaodien {


    public class DanhSachSanPickleball extends JFrame {

        private List<San> danhSachSan;

        public DanhSachSanPickleball() {
            danhSachSan = new ArrayList<>();
            danhSachSan.add(new San("Sân 1", "Trong nhà", "Sẵn sàng", 4.5));
            danhSachSan.add(new San("Sân 2", "Ngoài trời", "Đang bảo trì", 4.2));
            danhSachSan.add(new San("Sân 3", "Trong nhà", "Sẵn sàng", 4.8));
            danhSachSan.add(new San("Sân 4", "Ngoài trời", "Sẵn sàng", 4.0));
            danhSachSan.add(new San("Sân 5", "Trong nhà", "Đang sử dụng", 4.7));

            setTitle("Hệ Thống Đặt Sân Pickleball");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panelChinh = new JPanel(new BorderLayout());
            panelChinh.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblTieuDe = new JLabel("Danh Sách Sân Có Sẵn", SwingConstants.CENTER);
            lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
            panelChinh.add(lblTieuDe, BorderLayout.NORTH);

            JPanel panelDanhSachSan = new JPanel();
            panelDanhSachSan.setLayout(new BoxLayout(panelDanhSachSan, BoxLayout.Y_AXIS));

            for (San san : danhSachSan) {
                JPanel card = new JPanel(new BorderLayout());
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(Color.GRAY),
                        BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

                JLabel lblTen = new JLabel(san.getTen());
                lblTen.setFont(new Font("Arial", Font.BOLD, 18));

                JLabel lblChiTiet = new JLabel(String.format(
                        "Loại: %s | Trạng thái: %s | Đánh giá: %.1f/5",
                        san.getLoai(), san.getTrangThai(), san.getDanhGia()
                ));

                JPanel panelInfo = new JPanel(new GridLayout(2, 1));
                panelInfo.add(lblTen);
                panelInfo.add(lblChiTiet);

                card.add(panelInfo, BorderLayout.CENTER);

                JButton btnDat = new JButton("Đặt");
                btnDat.addActionListener(e -> datSan(san));
                card.add(btnDat, BorderLayout.EAST);

                panelDanhSachSan.add(card);
                panelDanhSachSan.add(Box.createRigidArea(new Dimension(0, 10)));
            }

            JScrollPane scrollPane = new JScrollPane(panelDanhSachSan);
            panelChinh.add(scrollPane, BorderLayout.CENTER);

            add(panelChinh);
        }

        private void datSan(San san) {
            JOptionPane.showMessageDialog(this,
                    "Đã đặt " + san.getTen() + " thành công!",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        public void main(String[] args) {
            SwingUtilities.invokeLater(() -> {
                DanhSachSanPickleball app = new DanhSachSanPickleball();
                app.setVisible(true);
            });
        }
    }

    class San {
        private String ten;
        private String loai;
        private String trangThai;
        private double danhGia;

        public San(String ten, String loai, String trangThai, double danhGia) {
            this.ten = ten;
            this.loai = loai;
            this.trangThai = trangThai;
            this.danhGia = danhGia;
        }

        public String getTen() { return ten; }
        public String getLoai() { return loai; }
        public String getTrangThai() { return trangThai; }
        public double getDanhGia() { return danhGia; }
    }
}

