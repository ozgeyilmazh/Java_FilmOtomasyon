package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;

class MuzikFilmArsiv extends JFrame {

	private JPanel contentPane;
	public JLabel lbl_kad;
	static Connection conn;
	static Statement s = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MuzikFilmArsiv frame = new MuzikFilmArsiv();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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

	/**
	 * Create the frame.
	 */
	public void kullanici() {
		KullaniciFilm kullanici_film = new KullaniciFilm();
		setVisible(false);
		kullanici_film.setVisible(true);
	}

	public void yonetici() {
		YoneticiFilm yonetici_film = new YoneticiFilm();
		setVisible(false);
		yonetici_film.setVisible(true);
	}

	public MuzikFilmArsiv() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 330, 246);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblHogeldiniz = new JLabel("Ho\u015Fgeldiniz");
		lblHogeldiniz.setBounds(43, 11, 70, 14);
		contentPane.add(lblHogeldiniz);

		lbl_kad = new JLabel(GirisYap.txt_kadi.getText().toString());
		lbl_kad.setBounds(110, 11, 70, 14);
		contentPane.add(lbl_kad);

		JButton btn_film = new JButton("Film Ar\u015Fivi");
		btn_film.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String uyelik_durumu = "0";
					String sql = "SELECT * FROM kayit WHERE uyelik_durumu=? AND kullanici_adi=?";
					PreparedStatement ps  = conn.prepareStatement(sql);
					ps.setString(1,uyelik_durumu);
					ps.setString(2,  GirisYap.txt_kadi.getText().toString());
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						kullanici();
					}
					else{
						yonetici();
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		});
		btn_film.setBounds(43, 60, 151, 23);
		contentPane.add(btn_film);

		JButton btn_muzik = new JButton("M\u00FCzik Ar\u015Fivi");
		btn_muzik.setBounds(43, 131, 151, 23);
		contentPane.add(btn_muzik);

		JButton btn_cikis = new JButton("\u00C7\u0131k\u0131\u015F");
		btn_cikis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		btn_cikis.setBounds(226, 7, 78, 23);
		contentPane.add(btn_cikis);
		
		JButton btn_ayar = new JButton("Ayarlar");
		btn_ayar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String uyelik_durumu = "0";
					String sql = "SELECT * FROM kayit WHERE uyelik_durumu=? AND kullanici_adi=?";
					PreparedStatement ps  = conn.prepareStatement(sql);
					ps.setString(1,uyelik_durumu);
					ps.setString(2,  GirisYap.txt_kadi.getText().toString());
					ResultSet rs = ps.executeQuery();
					if(rs.next()) {
						KullaniciAyar ayarlar = new KullaniciAyar();
						setVisible(false);
						ayarlar.setVisible(true);
					}
					else {
						AdminAyar ayarlar = new AdminAyar();
						setVisible(false);
						ayarlar.setVisible(true);
					}
				}
				catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_ayar.setBounds(226, 38, 78, 23);
		contentPane.add(btn_ayar);
	}
}
