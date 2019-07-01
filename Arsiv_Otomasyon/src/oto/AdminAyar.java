package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.mysql.jdbc.Statement;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class AdminAyar extends JFrame {

	private JPanel contentPane;
	public JTextField t_ad;
	public JTextField t_soyad;
	public JTextField t_kad;
	public JTextField t_ksifre;
	static Connection conn;
	static Statement s = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminAyar frame = new AdminAyar();
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
	
	public void kullanici() {
		KullaniciAyar kullanici_ayar = new 	KullaniciAyar();
		setVisible(false);
		kullanici_ayar.setVisible(true);
	}

	public void yonetici() {
		AdminAyar yonetici_ayar = new AdminAyar();
		setVisible(false);
		yonetici_ayar.setVisible(true);
	}
	
	public AdminAyar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_bilgigetir = new JButton("Bilgileri Getir");
		btn_bilgigetir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						getConnection();
						String sql = "SELECT * FROM kayit WHERE kullanici_adi=?";
						PreparedStatement ps  = conn.prepareStatement(sql);
						ps.setString(1,  GirisYap.txt_kadi.getText().toString());
						ResultSet rs = ps.executeQuery();
						while(rs.next()) {
							t_ad.setText(rs.getString("ad"));
							t_soyad.setText(rs.getString("soyad"));
							t_kad.setText(rs.getString("kullanici_adi"));
							t_ksifre.setText(rs.getString("sifre"));
						}
						ps.close();
						rs.close();
			            conn.close();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}				
				} 				
		});
		btn_bilgigetir.setBounds(10, 11, 187, 23);
		contentPane.add(btn_bilgigetir);
		
		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 ad\u0131 :");
		lblKullancAd.setBounds(10, 105, 136, 14);
		contentPane.add(lblKullancAd);
		
		JLabel lblifre = new JLabel("\u015Eifre :");
		lblifre.setBounds(10, 130, 60, 14);
		contentPane.add(lblifre);
		
		JLabel lblAd = new JLabel("Ad :");
		lblAd.setBounds(10, 55, 46, 14);
		contentPane.add(lblAd);
		
		JLabel lblSoyad = new JLabel("Soyad :");
		lblSoyad.setBounds(10, 80, 46, 14);
		contentPane.add(lblSoyad);
		
		t_ad = new JTextField();
		t_ad.setBounds(98, 52, 187, 20);
		contentPane.add(t_ad);
		t_ad.setColumns(10);
		
		t_soyad = new JTextField();
		t_soyad.setColumns(10);
		t_soyad.setBounds(98, 77, 187, 20);
		contentPane.add(t_soyad);
		
		t_kad = new JTextField();
		t_kad.setEditable(false);
		t_kad.setColumns(10);
		t_kad.setBounds(98, 102, 187, 20);
		contentPane.add(t_kad);
		
		t_ksifre = new JTextField();
		t_ksifre.setColumns(10);
		t_ksifre.setBounds(98, 127, 187, 20);
		contentPane.add(t_ksifre);
		
		JButton btn_guncelle = new JButton("G\u00FCncelle");
		btn_guncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String sql = "UPDATE kayit SET ad=? , soyad=? , sifre=? WHERE kullanici_adi=? ";
			    try {
			    	getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, t_ad.getText());
					ps.setString(2, t_soyad.getText());
					ps.setString(3, t_ksifre.getText());
					ps.setString(4, GirisYap.txt_kadi.getText().toString());
					
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Güncelleme tamamlandý! ");
					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_guncelle.setBounds(10, 169, 89, 23);
		contentPane.add(btn_guncelle);
		
		JButton btn_istek = new JButton("Gelen \u0130stekleri G\u00F6r");
		btn_istek.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				YoneticiIstek istek = new YoneticiIstek();
				setVisible(false);
				istek.setVisible(true);
			}
		});
		btn_istek.setBounds(10, 213, 402, 23);
		contentPane.add(btn_istek);
		
		JButton btn_anasayfa = new JButton("Anasayfa");
		btn_anasayfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MuzikFilmArsiv geri = new MuzikFilmArsiv();
				setVisible(false);
				geri.setVisible(true);
			}
		});
		btn_anasayfa.setBounds(335, 11, 89, 23);
		contentPane.add(btn_anasayfa);
	}

}
