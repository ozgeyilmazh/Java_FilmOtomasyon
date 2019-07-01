package oto;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import com.mysql.jdbc.Statement;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class KullaniciFilm extends JFrame {

	private JPanel contentPane;
	private JTextField txt_ara;
	private JTextField t_fad;
	private JTextField t_fsenarist;
	private JTextField t_fyonetmen;
	private JTextField t_fpuan;
	private JTextArea t_fozet;
	private JTable table;
	JComboBox cmb_film;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					KullaniciFilm frame = new KullaniciFilm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

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

	static Connection conn;
	static Statement s = null;
	String secilendeger;
	
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
			String sql = "SELECT Ad FROM film_fav WHERE kullanici_adi = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, GirisYap.txt_kadi.getText().toString());
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				cmb_film.addItem(rs.getString("Ad"));
			}
			rs.close();
			ps.close();
			conn.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * Create the frame.
	 */
	public KullaniciFilm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 458, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

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

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 45, 414, 365);
		contentPane.add(tabbedPane);

		JPanel panel_list = new JPanel();
		tabbedPane.addTab("Listele", null, panel_list, null);
		panel_list.setLayout(null);

		txt_ara = new JTextField();
		txt_ara.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				FilterJtable(table, txt_ara);
			}
		});
		txt_ara.setBounds(10, 11, 298, 20);
		panel_list.add(txt_ara);
		txt_ara.setColumns(10);

		JLabel lblSeiliOlanListeye = new JLabel("Se\u00E7ili olan\u0131 listeye ekle");
		lblSeiliOlanListeye.setBounds(159, 307, 149, 14);
		panel_list.add(lblSeiliOlanListeye);

		JButton btn_ekle = new JButton("Ekle");
		btn_ekle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String sql2 = "SELECT * FROM film_list WHERE Ad = ?";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, secilendeger);
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
				cmb_film.removeAllItems();
				cmb_film();
			}
		});
		btn_ekle.setBounds(318, 303, 89, 23);
		panel_list.add(btn_ekle);

		JButton btn_listele = new JButton("Listele");
		btn_listele.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		btn_listele.setBounds(10, 303, 89, 23);
		panel_list.add(btn_listele);

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				int tablo1satir = table.getSelectedRow();// Hangi satýr olduðunu alýyor.
				int tablo1sutun = table.getSelectedColumn();// Hangi sutun olduðunu alýyor

				secilendeger = String.valueOf(table.getValueAt(tablo1satir, tablo1sutun));
			}
		});
		table.setBounds(10, 42, 389, 253);
		panel_list.add(table);

		JPanel panel_fav = new JPanel();
		panel_fav.setLayout(null);
		tabbedPane.addTab("Favoriler", null, panel_fav, null);

		cmb_film = new JComboBox();
		cmb_film.setBounds(89, 11, 298, 20);
		panel_fav.add(cmb_film);

		JLabel label = new JLabel("Film Se\u00E7");
		label.setBounds(10, 14, 69, 14);
		panel_fav.add(label);

		JButton button = new JButton("Tamam");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String sql = "SELECT * FROM film_fav WHERE Ad=?";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setString(1, cmb_film.getSelectedItem().toString());
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

		JTextArea t_fozet = new JTextArea();
		t_fozet.setEditable(false);
		t_fozet.setBounds(88, 180, 299, 146);
		panel_fav.add(t_fozet);
		
		JButton btn_kaldir = new JButton("Kald\u0131r");
		btn_kaldir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					getConnection();
					String sql2 = "DELETE FROM film_fav WHERE Ad=? and kullanici_adi=?";
					PreparedStatement ps2 = conn.prepareStatement(sql2);
					ps2.setString(1, cmb_film.getSelectedItem().toString());
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
				cmb_film.removeItem(cmb_film.getSelectedItem());
			}
		});
		btn_kaldir.setBounds(89, 37, 89, 23);
		panel_fav.add(btn_kaldir);
	}

}
