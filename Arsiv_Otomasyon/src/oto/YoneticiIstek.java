package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class YoneticiIstek extends JFrame {

	private JPanel contentPane;
	private JTextField t_kad;
	private JTable table;
	static Connection conn;
	static Statement s = null;
	int sutunSayisi ;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiIstek frame = new YoneticiIstek();
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
	public YoneticiIstek() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 423, 458);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblGelenIstekler = new JLabel("Gelen \u0130stekler");
		lblGelenIstekler.setBounds(10, 21, 125, 14);
		contentPane.add(lblGelenIstekler);
		
		JLabel lblKullancAd = new JLabel("Kullan\u0131c\u0131 Ad\u0131 :");
		lblKullancAd.setBounds(10, 200, 102, 14);
		contentPane.add(lblKullancAd);
		
		JLabel lblyelikDurumu = new JLabel("\u00DCyelik Durumu : ");
		lblyelikDurumu.setBounds(10, 245, 83, 14);
		contentPane.add(lblyelikDurumu);
		
		JComboBox cmb_durum = new JComboBox();
		cmb_durum.setBounds(92, 242, 125, 20);
		cmb_durum.addItem(0);
		cmb_durum.addItem(1);
		cmb_durum.addItem(" ");
		contentPane.add(cmb_durum);
		
		t_kad = new JTextField();
		t_kad.setEditable(false);
		t_kad.setBounds(92, 197, 125, 20);
		contentPane.add(t_kad);
		t_kad.setColumns(10);
		
		JButton btnOnayla = new JButton("Onayla");
		btnOnayla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String sql = "UPDATE kayit SET uyelik_durumu = ? WHERE kullanici_adi=? ";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, cmb_durum.getSelectedItem().toString());
					ps.setString(2, t_kad.getText());
					
					String sql2 = "DELETE FROM yonetici_istek WHERE kullanici_adi=? "; 
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, t_kad.getText());
					
					ps.executeUpdate();	ps2.executeUpdate();
					JOptionPane.showMessageDialog(null,  t_kad.getText() + " adlý kullanýcýyý yönetici olarak onayladýnýz! ");
					ps.close(); ps2.close();
					t_kad.setText(" ");
					conn.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnOnayla.setBounds(128, 299, 89, 23);
		contentPane.add(btnOnayla);
		
		JLabel lblKullanc = new JLabel("0: Kullan\u0131c\u0131 1: Y\u00F6netici");
		lblKullanc.setBounds(10, 379, 252, 14);
		contentPane.add(lblKullanc);
		
		JButton btn_goster = new JButton("G\u00F6ster");
		btn_goster.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql = "SELECT kullanici_adi, uyelik_durumu FROM yonetici_istek";
					s = (Statement) conn.createStatement();
					try(ResultSet rs = s.executeQuery(sql)) {
						 int colcount = rs.getMetaData().getColumnCount(); //Veritabanýndaki tabloda kaç tane sütun var?
				            DefaultTableModel tm = new DefaultTableModel(); //Model oluþturuyoruz
				            for(int i = 1;i<=colcount;i++)
				                tm.addColumn(rs.getMetaData().getColumnName(i)); //Tabloya sütun ekliyoruz veritabanýmýzdaki sütun ismiyle ayný olacak þekilde
				            while(rs.next())
				                {
				                    Object[] row = new Object[colcount];
				                    for(int i=1;i<=colcount;i++)
				                        row[i-1] = rs.getObject(i);
				                    tm.addRow(row);
				                }
				            table.setModel(tm);
				    		
				            rs.close();
				            conn.close();
					}
				            
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_goster.setBounds(104, 17, 89, 23);
		contentPane.add(btn_goster);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int tablo1satir=table.getSelectedRow();//Hangi satýr olduðunu alýyor.
	    		int tablo1sutun=table.getSelectedColumn();//Hangi sutun olduðunu alýyor

	    		String secilendeger=String.valueOf(table.getValueAt(tablo1satir,tablo1sutun));
	    		t_kad.setText(secilendeger);
			}
		});
		table.setBounds(10, 46, 361, 138);
		contentPane.add(table);
		
		JButton btna_anasayfa = new JButton("Anasayfa");
		btna_anasayfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MuzikFilmArsiv anasayfa = new MuzikFilmArsiv();
				setVisible(false);
				anasayfa.setVisible(true);
			}
		});
		btna_anasayfa.setBounds(282, 17, 89, 23);
		contentPane.add(btna_anasayfa);
		
		JButton btn_geri = new JButton("Geri");
		btn_geri.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				AdminAyar geri = new AdminAyar();
				setVisible(false);
				geri.setVisible(true);
			}
		});
		btn_geri.setBounds(206, 17, 66, 23);
		contentPane.add(btn_geri);
	}

}
