package oto;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import java.sql.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

class GirisYap extends JFrame {

	private JPanel contentPane;
	static JTextField txt_kadi;
	public  JLabel lbl_uyari;
	static Connection conn;
	static Statement s=null;
	private JPasswordField txt_ksifre;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirisYap frame = new GirisYap();
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
	public GirisYap() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblHogeldinizGiriYapmak = new JLabel("Ho\u015Fgeldiniz, giri\u015F yapmak i\u00E7in bo\u015F alanlar\u0131 doldurunuz.");
		lblHogeldinizGiriYapmak.setBounds(10, 11, 329, 14);
		contentPane.add(lblHogeldinizGiriYapmak);
		
		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 ad\u0131: ");
		lblKullancAd.setBounds(29, 54, 115, 14);
		contentPane.add(lblKullancAd);
		
		txt_kadi = new JTextField();
		txt_kadi.setBounds(128, 51, 86, 20);
		contentPane.add(txt_kadi);
		txt_kadi.setColumns(10);
		
		JLabel lblifre = new JLabel("\u015Eifre: ");
		lblifre.setBounds(29, 102, 46, 14);
		contentPane.add(lblifre);
		
		JButton btn_giris = new JButton("Giri\u015F Yap");
		btn_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();

					String sql = "SELECT * FROM kayit WHERE kullanici_adi=? and sifre=?" ;
					PreparedStatement ps  = conn.prepareStatement(sql);
					ps.setString(1,txt_kadi.getText());
					ps.setString(2, txt_ksifre.getText().toString());
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						MuzikFilmArsiv mf_arsiv= new MuzikFilmArsiv();
						setVisible(false);
						mf_arsiv.setVisible(true);
					}
					else {
						lbl_uyari.setVisible(true);
						txt_kadi.setText(" ");
						txt_ksifre.setText("");
					}
					ps.close();
					conn.close();
				} catch (Exception e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}		
			}
		});
		btn_giris.setBounds(128, 162, 89, 23);
		contentPane.add(btn_giris);
		
		JButton btn_geri = new JButton("Geri");
		btn_geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GirisKayit giris= new GirisKayit();
				setVisible(false);
				giris.setVisible(true);		
			}
		});
		btn_geri.setBounds(361, 7, 63, 23);
		contentPane.add(btn_geri);
		
		lbl_uyari = new JLabel("Parola veya kullan\u0131c\u0131 ad\u0131 yanl\u0131\u015F !!");
		lbl_uyari.setForeground(Color.RED);
		lbl_uyari.setBounds(10, 236, 414, 14);
		lbl_uyari.hide();
		contentPane.add(lbl_uyari);
		
		txt_ksifre = new JPasswordField();
		txt_ksifre.setBounds(128, 99, 86, 20);
		contentPane.add(txt_ksifre);
	}
}
