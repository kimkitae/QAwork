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

		setTitle("매크로 파일 자동 생성기");
		setSize(600, 200);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		outPathbtn = new JButton("ODBC 폴더");
		outPathbtn.setBounds(0, 0, 130, 20);
		outPathbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				outPathinfo.setText(fileSelect());
			}
		});
		getLayeredPane().add(outPathbtn);

		inPathbtn = new JButton("자료 폴더 선택");
		inPathbtn.setBounds(0, 20, 130, 20);
		inPathbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				inPathinfo.setText(fileSelect());
			}
		});
		getLayeredPane().add(inPathbtn);

		outPathinfo = new JLabel("ODBC 폴더를 선택해 주십시오.");
		outPathinfo.setBounds(140, 0, 300, 20);
		getLayeredPane().add(outPathinfo);

		inPathinfo = new JLabel("매크로를 생성 할 파일이 있는 폴더를 선택해주세요");
		inPathinfo.setBounds(140, 20, 300, 20);
		getLayeredPane().add(inPathinfo);

		startbtn = new JButton("작업 시작");
		startbtn.setBounds(120, 70, 130, 20);
		getLayeredPane().add(startbtn);
		startbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				currentinfo.setText("매크로 생성 작업을 실행합니다");
				reThread();
				t.start();
			}
		});

		stopbtn = new JButton("작업 종료");
		stopbtn.setBounds(280, 70, 130, 20);
		getLayeredPane().add(stopbtn);
		stopbtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				t.interrupt();
				currentinfo.setText("작업을 종료 하였습니다.");
			}
		});

		exitbtn = new JButton("프로그램 종료");
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

		currentinfo = new JLabel("현재 상태");
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
