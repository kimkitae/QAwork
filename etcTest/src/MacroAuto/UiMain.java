package MacroAuto;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class UiMain extends JFrame {

	static JLabel inPathinfo;
	static JLabel outPathinfo;
	JButton inPathbtn;
	JButton outPathbtn;
	JButton startbtn;
	JButton stopbtn;
	JButton exitbtn;
	static JLabel currentinfo;

	static Runnable r;
	static Thread t;

	static String outpath;

	JFileChooser folderselect;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		UiMain um = new UiMain();
		um.UiMain();

	}

	public void UiMain() {
		// TODO Auto-generated constructor stub

		setTitle("��ũ�� ���� �ڵ� ������");
		setSize(600, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		outPathbtn = new JButton("ODBC ����");
		outPathbtn.setBounds(0, 0, 130, 20);
		outPathbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				outPathinfo.setText(fileSelect());
			}
		});
		getLayeredPane().add(outPathbtn);

		inPathbtn = new JButton("�ڷ� ���� ����");
		inPathbtn.setBounds(0, 20, 130, 20);
		inPathbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inPathinfo.setText(fileSelect());
			}
		});
		getLayeredPane().add(inPathbtn);

		outPathinfo = new JLabel("ODBC ������ ������ �ֽʽÿ�.");
		outPathinfo.setBounds(140, 0, 300, 20);
		getLayeredPane().add(outPathinfo);

		inPathinfo = new JLabel("��ũ�θ� ���� �� ������ �ִ� ������ �������ּ���");
		inPathinfo.setBounds(140, 20, 300, 20);
		getLayeredPane().add(inPathinfo);

		startbtn = new JButton("�۾� ����");
		startbtn.setBounds(120, 70, 130, 20);
		getLayeredPane().add(startbtn);
		startbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				currentinfo.setText("��ũ�� ���� �۾��� �����մϴ�");
				reThread();
				t.start();
			}
		});

		stopbtn = new JButton("�۾� ����");
		stopbtn.setBounds(280, 70, 130, 20);
		getLayeredPane().add(stopbtn);
		stopbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t.interrupt();
				currentinfo.setText("�۾��� ���� �Ͽ����ϴ�.");
			}
		});

		exitbtn = new JButton("���α׷� ����");
		exitbtn.setBounds(200, 100, 130, 20);
		getLayeredPane().add(exitbtn);
		exitbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t.interrupt();
				System.exit(0);
			}
		});

		currentinfo = new JLabel("���� ����");
		currentinfo.setBounds(140, 40, 300, 20);
		getLayeredPane().add(currentinfo);

		setVisible(true);
	}

	public String fileSelect() {
		folderselect = new JFileChooser();
		String folderPath = null;
		folderselect.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int result = folderselect.showOpenDialog(null);

		if (result == JFileChooser.APPROVE_OPTION) {
			folderPath = folderselect.getSelectedFile().toString();
			System.out.println(folderPath);
		}
		return folderPath;
	}

	public static void reThread() {
		r = new ExtendThread(inPathinfo.getText(), outPathinfo.getText());
		t = new Thread(r);

	}

}
