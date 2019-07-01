package oto;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GirisKayit extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GirisKayit frame = new GirisKayit();
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
	public GirisKayit() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 216, 249);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btn_kayit = new JButton("Kay\u0131t Ol");
		btn_kayit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				KayitOl kayit= new KayitOl();
				setVisible(false);
				kayit.setVisible(true);		
			}
		});
		btn_kayit.setBounds(43, 39, 89, 23);
		contentPane.add(btn_kayit);
		
		JButton btn_giris = new JButton("Giri\u015F Yap");
		btn_giris.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				GirisYap giris= new GirisYap();
				setVisible(false);
				giris.setVisible(true);		
			}
		});
		btn_giris.setBounds(43, 136, 89, 23);
		contentPane.add(btn_giris);
	}

}
