package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JTabbedPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JToolBar;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class YoneticiFilm extends JFrame {

	private JPanel contentPane;
	private JTextField txt_fad;
	private JTextField txt_fsenarist;
	private JTextField txt_fyonetmen;
	private JTextField txt_fpuan;
	private JTextField txt_gunc_ad;
	private JTextField txt_gunc_senarist;
	private JTextField txt_gunc_yonetmen;
	private JTextField txt_gunc_puan;
	private JTextField txt_ara;
	private JTextField txt_ara2;
	private JTextField t_fad;
	private JTextField t_fsenarist;
	private JTextField t_fyonetmen;
	private JTextField t_fpuan;
	JComboBox cmb_film, cmb_film2;
	JButton btn_listele;
	JTextArea txt_gunc_ozet, t_fozet;
	String secilendeger, secilendeger2;

	/**
	 * Launch the application.
	 */

	public void FilterJtable(JTable jTable, JTextField jtfFilter) {
		TableRowSorter<TableModel> rowSorter = new TableRowSorter<>(jTable.getModel());
		jTable.setRowSorter(rowSorter);
		jtfFilter.getDocument().addDocumentListener(new DocumentListener() {

			@Override
			public void insertUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				String text = jtfFilter.getText();

				if (text.trim().length() == 0) {
					rowSorter.setRowFilter(null);
				} else {
					rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
				}
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods,
																				// choose Tools | Templates.
			}

		});
	}

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					YoneticiFilm frame = new YoneticiFilm();
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

	static Connection conn;
	static Statement s = null;
	private JTable table;
	private JTable table2;

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

	private void cmb_film() {
		try {
			getConnection();
			String sql = "SELECT Ad FROM film_list";
			s = (Statement) conn.createStatement();
			ResultSet r = s.executeQuery(sql);
			while (r.next()) {
				cmb_film.addItem(r.getString("Ad"));
			}
			r.close();
			s.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void cmb_film2() {
		try {
			getConnection();
			String sql = "SELECT Ad FROM film_fav WHERE kullanici_adi = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, GirisYap.txt_kadi.getText().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cmb_film2.addItem(rs.getString("Ad"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void listele() {
		try {
			getConnection();
			String sql = "SELECT Ad FROM film_list";
			s = (Statement) conn.createStatement();
			try (ResultSet rs = s.executeQuery(sql)) {
				int colcount = rs.getMetaData().getColumnCount(); // Veritabanýndaki tabloda kaç tane sütun var?
				DefaultTableModel tm = new DefaultTableModel(); // Model oluþturuyoruz
				for (int i = 1; i <= colcount; i++)
					tm.addColumn(rs.getMetaData().getColumnName(i)); // Tabloya sütun ekliyoruz
																		// veritabanýmýzdaki sütun ismiyle ayný
																		// olacak þekilde
				while (rs.next()) {
					Object[] row = new Object[colcount];
					for (int i = 1; i <= colcount; i++)
						row[i - 1] = rs.getObject(i);
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

	private void listele2() {
		try {
			getConnection();
			String sql = "SELECT Ad,Senarist,Yonetmen,IMDB_puani,Ozet FROM film_list";
			s = (Statement) conn.createStatement();
			try (ResultSet rs = s.executeQuery(sql)) {
				int colcount = rs.getMetaData().getColumnCount(); // Veritabanýndaki tabloda kaç tane sütun var?
				DefaultTableModel tm = new DefaultTableModel(); // Model oluþturuyoruz
				for (int i = 1; i <= colcount; i++)
					tm.addColumn(rs.getMetaData().getColumnName(i)); // Tabloya sütun ekliyoruz
																		// veritabanýmýzdaki sütun ismiyle ayný
																		// olacak þekilde
				while (rs.next()) {
					Object[] row = new Object[colcount];
					for (int i = 1; i <= colcount; i++)
						row[i - 1] = rs.getObject(i);
					tm.addRow(row);
				}
				table2.setModel(tm);

				rs.close();
				conn.close();
			}

		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public YoneticiFilm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btn_anasayfa = new JButton("Anasayfa");
		btn_anasayfa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				MuzikFilmArsiv geri = new MuzikFilmArsiv();
				setVisible(false);
				geri.setVisible(true);
			}
		});
		btn_anasayfa.setBounds(319, 11, 105, 23);
		contentPane.add(btn_anasayfa);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 45, 414, 339);
		// tabbedPane.add("", component)
		contentPane.add(tabbedPane);

		JPanel panel_ekle = new JPanel();
		tabbedPane.addTab("Ekle", null, panel_ekle, null);
		panel_ekle.setLayout(null);

		JLabel lblFilmAd = new JLabel("Film Ad\u0131 :");
		lblFilmAd.setBounds(10, 11, 62, 14);
		panel_ekle.add(lblFilmAd);

		JLabel lblSenarist = new JLabel("Senarist : ");
		lblSenarist.setBounds(10, 36, 62, 14);
		panel_ekle.add(lblSenarist);

		JLabel lblYnetmen = new JLabel("Y\u00F6netmen :");
		lblYnetmen.setBounds(10, 61, 70, 14);
		panel_ekle.add(lblYnetmen);

		JLabel lblzet = new JLabel("IMDB puan\u0131 :");
		lblzet.setBounds(10, 86, 70, 14);
		panel_ekle.add(lblzet);

		JLabel lblzeti = new JLabel("\u00D6zeti :");
		lblzeti.setBounds(10, 111, 46, 14);
		panel_ekle.add(lblzeti);

		txt_fad = new JTextField();
		txt_fad.setBounds(93, 8, 247, 20);
		panel_ekle.add(txt_fad);
		txt_fad.setColumns(10);

		txt_fsenarist = new JTextField();
		txt_fsenarist.setBounds(93, 33, 247, 20);
		panel_ekle.add(txt_fsenarist);
		txt_fsenarist.setColumns(10);

		txt_fyonetmen = new JTextField();
		txt_fyonetmen.setBounds(93, 58, 247, 20);
		panel_ekle.add(txt_fyonetmen);
		txt_fyonetmen.setColumns(10);

		txt_fpuan = new JTextField();
		txt_fpuan.setBounds(93, 83, 247, 20);
		panel_ekle.add(txt_fpuan);
		txt_fpuan.setColumns(10);

		JTextArea txt_fozet = new JTextArea();
		txt_fozet.setBounds(93, 111, 247, 148);
		panel_ekle.add(txt_fozet);

		JButton btn_ekle = new JButton("Ekle");
		btn_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql = "INSERT INTO film_list(Ad,Senarist,Yonetmen,IMDB_puani,Ozet) VALUES(?,?,?,?,?) ";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, txt_fad.getText());
					ps.setString(2, txt_fsenarist.getText());
					ps.setString(3, txt_fyonetmen.getText());
					ps.setString(4, txt_fpuan.getText());
					ps.setString(5, txt_fozet.getText());

					ps.executeUpdate();

					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				JOptionPane.showMessageDialog(null, "Ekleme iþlemi tamamlandý! ");
				txt_fad.setText(" ");
				txt_fsenarist.setText(" ");
				txt_fyonetmen.setText(" ");
				txt_fpuan.setText(" ");
				txt_fozet.setText(" ");
			}
		});
		btn_ekle.setBounds(251, 266, 89, 23);
		panel_ekle.add(btn_ekle);

		JPanel panel_guncelle = new JPanel();
		tabbedPane.addTab("Güncelle", null, panel_guncelle, null);
		panel_guncelle.setLayout(null);

		cmb_film = new JComboBox();
		cmb_film.setBounds(89, 11, 298, 20);
		panel_guncelle.add(cmb_film);

		JLabel lblFilmSe = new JLabel("Film Se\u00E7");
		lblFilmSe.setBounds(10, 14, 69, 14);
		panel_guncelle.add(lblFilmSe);

		JButton btn_tamam = new JButton("Tamam");
		btn_tamam.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String sql = "SELECT * FROM film_list WHERE Ad= ?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, cmb_film.getSelectedItem().toString());
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						txt_gunc_ad.setText(rs.getString("Ad"));
						txt_gunc_senarist.setText(rs.getString("Senarist"));
						txt_gunc_yonetmen.setText(rs.getString("Yonetmen"));
						txt_gunc_puan.setText(rs.getString("IMDB_puani"));
						txt_gunc_ozet.setText(rs.getString("Ozet"));
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
		btn_tamam.setBounds(298, 37, 89, 23);
		panel_guncelle.add(btn_tamam);

		JLabel lblFilmAd_1 = new JLabel("Film ad\u0131 : ");
		lblFilmAd_1.setBounds(10, 80, 82, 14);
		panel_guncelle.add(lblFilmAd_1);

		JLabel lblSenarist_1 = new JLabel("Senarist : ");
		lblSenarist_1.setBounds(10, 105, 67, 14);
		panel_guncelle.add(lblSenarist_1);

		JLabel lblYnetmen_1 = new JLabel("Y\u00F6netmen : ");
		lblYnetmen_1.setBounds(10, 130, 67, 14);
		panel_guncelle.add(lblYnetmen_1);

		JLabel lblImdbPuan = new JLabel("IMDB puan\u0131 :");
		lblImdbPuan.setBounds(10, 155, 82, 14);
		panel_guncelle.add(lblImdbPuan);

		JLabel lblzet_1 = new JLabel("\u00D6zet :");
		lblzet_1.setBounds(10, 180, 46, 14);
		panel_guncelle.add(lblzet_1);

		txt_gunc_ad = new JTextField();
		txt_gunc_ad.setBounds(89, 77, 298, 20);
		panel_guncelle.add(txt_gunc_ad);
		txt_gunc_ad.setColumns(10);

		txt_gunc_senarist = new JTextField();
		txt_gunc_senarist.setBounds(89, 102, 298, 20);
		panel_guncelle.add(txt_gunc_senarist);
		txt_gunc_senarist.setColumns(10);

		txt_gunc_yonetmen = new JTextField();
		txt_gunc_yonetmen.setBounds(89, 127, 298, 20);
		panel_guncelle.add(txt_gunc_yonetmen);
		txt_gunc_yonetmen.setColumns(10);

		txt_gunc_puan = new JTextField();
		txt_gunc_puan.setBounds(89, 152, 298, 20);
		panel_guncelle.add(txt_gunc_puan);
		txt_gunc_puan.setColumns(10);

		txt_gunc_ozet = new JTextArea();
		txt_gunc_ozet.setBounds(88, 180, 299, 96);
		panel_guncelle.add(txt_gunc_ozet);

		JButton btn_guncelle = new JButton("G\u00FCncelle");
		btn_guncelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String sql = "UPDATE film_list SET Ad=? , Senarist=? , Yonetmen=? , IMDB_puani=? , Ozet=? WHERE Ad=? ";
				try {
					getConnection();
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, txt_gunc_ad.getText());
					ps.setString(2, txt_gunc_senarist.getText());
					ps.setString(3, txt_gunc_yonetmen.getText());
					ps.setString(4, txt_gunc_puan.getText());
					ps.setString(5, txt_gunc_ozet.getText());
					ps.setString(6, cmb_film.getSelectedItem().toString());

					ps.executeUpdate();
					JOptionPane.showMessageDialog(null, "Güncelleme tamamlandý!");
					txt_gunc_ad.setText(" ");
					txt_gunc_senarist.setText(" ");
					txt_gunc_yonetmen.setText(" ");
					txt_gunc_puan.setText(" ");
					txt_gunc_ozet.setText(" ");
					ps.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_guncelle.setBounds(310, 277, 89, 23);
		panel_guncelle.add(btn_guncelle);

		JPanel panel_sil = new JPanel();
		tabbedPane.addTab("Sil", null, panel_sil, null);
		panel_sil.setLayout(null);

		txt_ara = new JTextField();
		txt_ara.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				FilterJtable(table, txt_ara);
			}
		});
		txt_ara.setBounds(23, 8, 280, 20);
		panel_sil.add(txt_ara);
		txt_ara.setColumns(10);

		JLabel lblSeiliOlanSil = new JLabel("Se\u00E7ili olan\u0131 sil");
		lblSeiliOlanSil.setBounds(23, 255, 143, 14);
		panel_sil.add(lblSeiliOlanSil);

		JButton btn_sil = new JButton("Sil");
		btn_sil.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql2 = "DELETE FROM film_list WHERE Ad=? ";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, secilendeger);

					ps2.executeUpdate();
					JOptionPane.showMessageDialog(null, "Kayýt silindi! ");
					listele();
					ps2.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		btn_sil.setBounds(23, 277, 89, 23);
		panel_sil.add(btn_sil);

		btn_listele = new JButton("Listele");
		btn_listele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listele();
			}
		});

		btn_listele.setBounds(310, 277, 89, 23);
		panel_sil.add(btn_listele);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tablo1satir = table.getSelectedRow();// Hangi satýr olduðunu alýyor.
				int tablo1sutun = table.getSelectedColumn();// Hangi sutun olduðunu alýyor

				secilendeger = String.valueOf(table.getValueAt(tablo1satir, tablo1sutun));
			}
		});
		table.setBounds(23, 39, 376, 205);
		panel_sil.add(table);

		JPanel panel_list = new JPanel();
		tabbedPane.addTab("Listele", null, panel_list, null);
		panel_list.setLayout(null);

		txt_ara2 = new JTextField();
		txt_ara2.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				FilterJtable(table2, txt_ara2);
			}
		});
		txt_ara2.setBounds(23, 8, 280, 20);
		panel_list.add(txt_ara2);
		txt_ara2.setColumns(10);

		JLabel lblSeiliOlanFavorilere = new JLabel("Se\u00E7ili olan\u0131 favorilere ekle");
		lblSeiliOlanFavorilere.setForeground(Color.MAGENTA);
		lblSeiliOlanFavorilere.setBounds(23, 264, 164, 14);
		panel_list.add(lblSeiliOlanFavorilere);

		JButton btn_fav_ekle = new JButton("Ekle");
		btn_fav_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql2 = "SELECT * FROM film_list WHERE Ad = ?";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, secilendeger2);
					ResultSet rs = ps2.executeQuery();
					if (rs.next()) {
						String sql = " INSERT INTO film_fav(kullanici_adi,Ad,Senarist,Yonetmen,IMDB_puani,Ozet) VALUES(?,?,?,?,?,?) ";
						PreparedStatement ps = conn.prepareStatement(sql);
						ps.setString(1, GirisYap.txt_kadi.getText().toString());
						ps.setString(2, rs.getString("Ad"));
						ps.setString(3, rs.getString("Senarist"));
						ps.setString(4, rs.getString("Yonetmen"));
						ps.setString(5, rs.getString("IMDB_puani"));
						ps.setString(6, rs.getString("Ozet"));

						ps.executeUpdate();
						JOptionPane.showMessageDialog(null, "Favorilere eklendi! ");
						ps.close();
					} else {
						JOptionPane.showMessageDialog(null, "Ýþlem Yapýlamadý! ");
					}
					ps2.close();
					rs.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				cmb_film2.removeAllItems();
				cmb_film2();
			}
		});
		btn_fav_ekle.setBounds(23, 277, 89, 23);
		panel_list.add(btn_fav_ekle);

		JButton btn_listele2 = new JButton("Listele");
		btn_listele2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listele2();
			}
		});
		btn_listele2.setBounds(310, 277, 89, 23);
		panel_list.add(btn_listele2);

		table2 = new JTable();
		table2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int tablo2satir = table2.getSelectedRow();// Hangi satýr olduðunu alýyor.
				int tablo2sutun = table2.getSelectedColumn();// Hangi sutun olduðunu alýyor

				secilendeger2 = String.valueOf(table2.getValueAt(tablo2satir, tablo2sutun));
			}
		});
		table2.setBounds(23, 39, 376, 214);
		panel_list.add(table2);

		JPanel panel_fav = new JPanel();
		tabbedPane.addTab("Favoriler", null, panel_fav, null);
		panel_fav.setLayout(null);

		cmb_film2 = new JComboBox();
		cmb_film2.setBounds(89, 11, 298, 20);
		panel_fav.add(cmb_film2);

		JLabel label = new JLabel("Film Se\u00E7");
		label.setBounds(10, 14, 69, 14);
		panel_fav.add(label);

		JButton button = new JButton("Tamam");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql = "SELECT * FROM film_fav WHERE Ad=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, cmb_film2.getSelectedItem().toString());
					ResultSet rs = ps.executeQuery();
					while (rs.next()) {
						t_fad.setText(rs.getString("Ad"));
						t_fsenarist.setText(rs.getString("Senarist"));
						t_fyonetmen.setText(rs.getString("Yonetmen"));
						t_fpuan.setText(rs.getString("IMDB_puani"));
						t_fozet.setText(rs.getString("Ozet"));
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
		button.setBounds(298, 37, 89, 23);
		panel_fav.add(button);

		JLabel label_1 = new JLabel("Film ad\u0131 : ");
		label_1.setBounds(10, 80, 82, 14);
		panel_fav.add(label_1);

		JLabel label_2 = new JLabel("Senarist : ");
		label_2.setBounds(10, 105, 67, 14);
		panel_fav.add(label_2);

		JLabel label_3 = new JLabel("Y\u00F6netmen : ");
		label_3.setBounds(10, 130, 67, 14);
		panel_fav.add(label_3);

		JLabel label_4 = new JLabel("IMDB puan\u0131 :");
		label_4.setBounds(10, 155, 82, 14);
		panel_fav.add(label_4);

		JLabel label_5 = new JLabel("\u00D6zet :");
		label_5.setBounds(10, 180, 46, 14);
		panel_fav.add(label_5);

		t_fad = new JTextField();
		t_fad.setEditable(false);
		t_fad.setColumns(10);
		t_fad.setBounds(89, 77, 298, 20);
		panel_fav.add(t_fad);

		t_fsenarist = new JTextField();
		t_fsenarist.setEditable(false);
		t_fsenarist.setColumns(10);
		t_fsenarist.setBounds(89, 102, 298, 20);
		panel_fav.add(t_fsenarist);

		t_fyonetmen = new JTextField();
		t_fyonetmen.setEditable(false);
		t_fyonetmen.setColumns(10);
		t_fyonetmen.setBounds(89, 127, 298, 20);
		panel_fav.add(t_fyonetmen);

		t_fpuan = new JTextField();
		t_fpuan.setEditable(false);
		t_fpuan.setColumns(10);
		t_fpuan.setBounds(89, 152, 298, 20);
		panel_fav.add(t_fpuan);

		t_fozet = new JTextArea();
		t_fozet.setEditable(false);
		t_fozet.setBounds(88, 180, 299, 96);
		panel_fav.add(t_fozet);

		JButton btn_kaldir = new JButton("Kald\u0131r");
		btn_kaldir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					getConnection();
					String sql2 = "DELETE FROM film_fav WHERE Ad=? and kullanici_adi =? ";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, cmb_film2.getSelectedItem().toString());
					ps2.setString(2, GirisYap.txt_kadi.getText().toString());
					ps2.executeUpdate();
					JOptionPane.showMessageDialog(null, "Seçiminiz favorilerden kaldýrýldý! ");
					ps2.close();
					conn.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				t_fad.setText(" ");
				t_fsenarist.setText(" ");
				t_fyonetmen.setText(" ");
				t_fpuan.setText(" ");
				t_fozet.setText(" ");
				cmb_film2.removeItem(cmb_film2.getSelectedItem());
			}
		});
		btn_kaldir.setBounds(89, 42, 89, 23);
		panel_fav.add(btn_kaldir);

		cmb_film();

	}
}
