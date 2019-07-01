package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class KayitOl extends JFrame {

	private JPanel contentPane;
	private JTextField ad;
	private JTextField soyad;
	private JTextField k_ad;
	private JTextField k_sifre;
	static JTextField txt_ad;
	static JTextField txt_soyad;
	static JTextField txt_kadi;
	static JTextField txt_ksifre;
	static JComboBox cmb_durum;
	/**
	 * Launch the application.
	 */

	static Connection conn;
	static Statement s = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KayitOl frame = new KayitOl();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public static void getConnection() throws Exception {
		try {
			String url = "jdbc:mysql://localhost:3306/javayazilim?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			String username = "root";
			String password = "canimkendi";
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(url, username, password);
			System.out.println("Connected");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public KayitOl() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 411, 349);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHogeldinizKaytOlmak = new JLabel(
				"Ho\u015Fgeldiniz, kay\u0131t olmak i\u00E7in l\u00FCtfen formu doldurunuz.");
		lblHogeldinizKaytOlmak.setBounds(10, 11, 309, 27);
		contentPane.add(lblHogeldinizKaytOlmak);

		JLabel lblAd = new JLabel("Ad :");
		lblAd.setBounds(10, 49, 46, 14);
		contentPane.add(lblAd);

		JLabel lblSoyad = new JLabel("Soyad :");
		lblSoyad.setBounds(10, 74, 46, 14);
		contentPane.add(lblSoyad);

		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 ad\u0131 :");
		lblKullancAd.setBounds(10, 99, 131, 14);
		contentPane.add(lblKullancAd);

		JLabel lblifre = new JLabel("\u015Eifre :");
		lblifre.setBounds(10, 124, 46, 14);
		contentPane.add(lblifre);

		txt_ad = new JTextField();
		txt_ad.setBounds(151, 43, 165, 20);
		contentPane.add(txt_ad);
		txt_ad.setColumns(10);

		txt_soyad = new JTextField();
		txt_soyad.setBounds(151, 68, 165, 20);
		contentPane.add(txt_soyad);
		txt_soyad.setColumns(10);

		txt_kadi = new JTextField();
		txt_kadi.setBounds(151, 93, 165, 20);
		contentPane.add(txt_kadi);
		txt_kadi.setColumns(10);

		txt_ksifre = new JTextField();
		txt_ksifre.setBounds(151, 118, 165, 20);
		contentPane.add(txt_ksifre);
		txt_ksifre.setColumns(10);

		cmb_durum = new JComboBox();
		cmb_durum.setBounds(151, 143, 165, 20);
		cmb_durum.addItem(0);
		contentPane.add(cmb_durum);

		JButton btn_kayit = new JButton("Kay\u0131t Ol");
		btn_kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql = "INSERT INTO kayit(ad,soyad,kullanici_adi,sifre,uyelik_durumu) VALUES(?,?,?,?,?) ";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, txt_ad.getText());
					ps.setString(2, txt_soyad.getText());
					ps.setString(3, txt_kadi.getText());
					ps.setString(4, txt_ksifre.getText());
					ps.setString(5, cmb_durum.getSelectedItem().toString());

					ps.executeUpdate();

					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(e1);
				}
				JOptionPane.showMessageDialog(null, "Kayýt iþlemi tamamlandý! ");
				txt_ad.setText(" ");
				txt_soyad.setText(" ");
				txt_kadi.setText(" ");
				txt_ksifre.setText(" ");
			}
		});
		btn_kayit.setBounds(103, 192, 89, 23);
		contentPane.add(btn_kayit);

		JLabel lblyelikDurumu = new JLabel("\u00DCyelik Durumu:");
		lblyelikDurumu.setBounds(10, 149, 131, 14);
		contentPane.add(lblyelikDurumu);

		JLabel lblyelikDurumu_1 = new JLabel(
				"\u00DCyelik durumu 0 veya 1 den olu\u015Fur. 0 normal \u00FCye 1 ise y\u00F6neticidr.");
		lblyelikDurumu_1.setBounds(10, 228, 375, 14);
		contentPane.add(lblyelikDurumu_1);

		JLabel lblEerKullancYnetici = new JLabel(
				"E\u011Fer kullan\u0131c\u0131 y\u00F6netici olmak istiyorsa bu \u00FCyelik kayd\u0131 tamamland\u0131ktan sonra");
		lblEerKullancYnetici.setBounds(10, 241, 375, 14);
		contentPane.add(lblEerKullancYnetici);

		JLabel lblKullancYkseltilmeyiTalep = new JLabel("kullan\u0131c\u0131 y\u00FCkseltilmeyi talep edebilir.");
		lblKullancYkseltilmeyiTalep.setBounds(10, 253, 246, 14);
		contentPane.add(lblKullancYkseltilmeyiTalep);

		JButton btn_geri = new JButton("Geri");
		btn_geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GirisKayit giris = new GirisKayit();
				setVisible(false);
				giris.setVisible(true);
			}
		});
		btn_geri.setBounds(335, 13, 60, 23);
		contentPane.add(btn_geri);

	}
}
