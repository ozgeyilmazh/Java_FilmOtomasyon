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
import java.awt.event.ActionEvent;

public class KullaniciAyar extends JFrame {

	private JPanel contentPane;
	private JTextField t_ad;
	private JTextField t_soyad;
	private JTextField t_kad;
	private JTextField t_ksifre;
	static Connection conn;
	static Statement s = null;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KullaniciAyar frame = new KullaniciAyar();
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
	
	public KullaniciAyar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.add(panel, BorderLayout.CENTER);
		
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
		panel.add(btn_bilgigetir);
		
		JLabel label = new JLabel("Kullan\u0131c\u0131 ad\u0131 :");
		label.setBounds(10, 105, 136, 14);
		panel.add(label);
		
		JLabel label_1 = new JLabel("\u015Eifre :");
		label_1.setBounds(10, 130, 60, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("Ad :");
		label_2.setBounds(10, 55, 46, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("Soyad :");
		label_3.setBounds(10, 80, 46, 14);
		panel.add(label_3);
		
		t_ad = new JTextField();
		t_ad.setEditable(false);
		t_ad.setColumns(10);
		t_ad.setBounds(98, 52, 187, 20);
		panel.add(t_ad);
		
		t_soyad = new JTextField();
		t_soyad.setEditable(false);
		t_soyad.setColumns(10);
		t_soyad.setBounds(98, 77, 187, 20);
		panel.add(t_soyad);
		
		t_kad = new JTextField();
		t_kad.setEditable(false);
		t_kad.setColumns(10);
		t_kad.setBounds(98, 102, 187, 20);
		panel.add(t_kad);
		
		t_ksifre = new JTextField();
		t_ksifre.setColumns(10);
		t_ksifre.setBounds(98, 127, 187, 20);
		panel.add(t_ksifre);
		
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
					JOptionPane.showMessageDialog(null, "Güncelleme tamamlandý!");
					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_guncelle.setBounds(10, 169, 89, 23);
		panel.add(btn_guncelle);
		
		JLabel lblYneticiOlmaIsteinde = new JLabel("Y\u00F6netici olma iste\u011Finde bulun");
		lblYneticiOlmaIsteinde.setBounds(10, 226, 187, 14);
		panel.add(lblYneticiOlmaIsteinde);
		
		JButton btn_yonetici = new JButton("Y\u00F6netici Pozisyonu");
		btn_yonetici.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql = "INSERT INTO yonetici_istek(kullanici_adi,uyelik_durumu) VALUES(?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1,  GirisYap.txt_kadi.getText().toString());
					ps.setString(2, "0");
					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Ýstek gönderildi! ");
					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_yonetici.setBounds(196, 222, 158, 23);
		panel.add(btn_yonetici);
		
		JButton btn_geri = new JButton("Anasayfa");
		btn_geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MuzikFilmArsiv geri = new MuzikFilmArsiv();
				setVisible(false);
				geri.setVisible(true);
			}
		});
		btn_geri.setBounds(325, 11, 89, 23);
		panel.add(btn_geri);
	}

}
