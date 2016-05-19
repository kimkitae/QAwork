package DefaultAction;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.invoke.SwitchPoint;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class AppUpgrade extends JFrame implements FilenameFilter {

	JButton FileCreate;
	JButton DeviceList;
	JButton LogReset;
	JButton LogExport;
	JButton ProgramExit;
	JOptionPane alram;
	JLabel savepath;
	JTextField cnt;
	JButton clean;

	static JFileChooser chooser;
	JLabel list;
	JList<String> jlist;
	Vector<String> vt;
	static String ResultCode; // 0 실패, 1 성공
	static String ResultMessage;
	static ArrayList<String> DeviceNum;
	static ArrayList<String> DeviceNum1;
	static int count = 0;
	static int Sumcount = 1;
	static String paths = "";
	static String DIR = System.getProperty("user.dir");

	ActionListener listnner;

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		AppUpgrade le = new AppUpgrade();
		le.LogExtrac();

	}

	public void LogExtrac() {

		setTitle("업그레이드 테스트");
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		DeviceList = new JButton("목록 조회");
		DeviceList.setBounds(0, 100, 150, 45);
		DeviceList.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				DeviceList();
				list.setText(ResultMessage);
				count = 1;

			}
		});
		getLayeredPane().add(DeviceList);

		FileCreate = new JButton("OCB 앱 삭제하기");
		FileCreate.setBounds(150, 100, 150, 45);
		FileCreate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				fileRemove1(DeviceNum1);

			}

		});
		getLayeredPane().add(FileCreate);

		LogReset = new JButton("최신버전 설치하기");
		LogReset.setBounds(300, 100, 150, 45);
		LogReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				newUpdate1(DeviceNum1);

			}
		});

		getLayeredPane().add(LogReset);

		LogExport = new JButton("해당 버전 설치하기");
		LogExport.setBounds(450, 100, 150, 45);
		LogExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				appInstall(DeviceNum1);

			}
		});

		getLayeredPane().add(LogExport);

		list = new JLabel("디바이스 목록 조회를 눌러주세요");
		list.setBounds(50, 50, 300, 50);
		getLayeredPane().add(list);
		//
		clean = new JButton("OCB 데이터 삭제");
		clean.setBounds(0, 150, 150, 45);
		clean.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sumcount = Integer.parseInt(cnt.getText());

				dateClear1(DeviceNum1);

			}
		});
		getLayeredPane().add(clean);

		cnt = new JTextField();
		cnt.setBounds(150, 150, 100, 45);
		// 버전명 쓰기
		cnt.setText("버전명 입력");
		getLayeredPane().add(cnt);

		ProgramExit = new JButton("프로그램 종료");
		ProgramExit.setBounds(200, 400, 150, 45);
		getLayeredPane().add(ProgramExit);
		ProgramExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				System.exit(0);

			}
		});

		// alram.showMessageDialog(null, "첫 작업 시 디바이스 조회 버튼을 클릭 해주세요");

		setVisible(true);

	}

	// 디바이스 목록 조회
	public void DeviceList() {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			OutputStream os = process.getOutputStream();
			os.write("adb devices \n\r".getBytes());

			os.close();

			DeviceNum = new ArrayList<String>();
			DeviceNum1 = new ArrayList<String>();
			vt = new Vector<String>();

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("ddd" + line);
				DeviceNum.add(line);
			}

			for (int g = 5; g < DeviceNum.size() - 3; g++) {
				StringTokenizer st1 = new StringTokenizer(DeviceNum.get(g).toString());
				DeviceNum1.add(st1.nextElement().toString());
				vt.addElement(DeviceNum.get(g).replace("	", " / "));
				System.out.println(DeviceNum.get(g).trim());

			}

			ResultMessage = "디바이스 목록 조회 하였습니다.";
			jlist = new JList<String>(vt);
			jlist.setBounds(100, 200, 400, 100);
			getLayeredPane().add(jlist);

			list.setText(ResultMessage);

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "디바이스 목록 조회 실패 하였습니다.";
			jlist = new JList<String>(vt);
			jlist.setBounds(10, 200, 400, 100);
			getLayeredPane().add(jlist);

			list.setText(ResultMessage);

		}

	}

	/// 배치파일 생성
	public void appInstall(ArrayList<String> DeviceNum1) {
		if (count == 0) {
			alram.showMessageDialog(null, "디바이스 조회 버튼을 클릭 해주세요");
		} else {
			for (int i = 0; i < DeviceNum1.size(); i++) {
				// System.out.println(DeviceNum1.get(i).toString());
				appInstall1(DeviceNum1.get(i).toString());
			}

		}
	}

	public void appInstall1(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String sealNum1 = "echo off \n\r";
			String sealNum2 = "adb -s " + DeviceNum1 + " install -r " + DIR + "\\" + "apk" + "\\"
					+ cnt.getText().toString() + ".apk \n\r";

			String sealNum3 = "pause \n\r";

			OutputStream os = process.getOutputStream();
			os.write(sealNum1.getBytes());
			os.write(sealNum2.getBytes());
			os.write(sealNum3.getBytes());

			os.write("exit \n".getBytes());

			os.close();

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("ddd" + line);
			}

			count = 1;
			ResultMessage = "앱 설치 완료 되었습니다.";
			list.setText(ResultMessage);
			// System.exit(process.exitValue());

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "앱 설치에 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	public void newUpdate1(ArrayList<String> DeviceNum1) {
		if (count == 0) {
			alram.showMessageDialog(null, "디바이스 조회 버튼을 클릭 해주세요");
		} else {
			for (int i = 0; i < DeviceNum1.size(); i++) {
				System.out.println(DeviceNum1.get(i).toString());
				newUpdate2(DeviceNum1.get(i).toString());
			}
		}

	}

	public void newUpdate2(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String sealNum1 = "echo off \n\r";
			String sealNum2 = "adb -s " + DeviceNum1 + " install -r " + DIR + "\\" + "apk" + "\\5.7.0.apk \n\r";
			String sealNum3 = "pause \n\r";

			OutputStream os = process.getOutputStream();
			os.write(sealNum1.getBytes());
			os.write(sealNum2.getBytes());
			os.write(sealNum3.getBytes());

			os.write("exit \n".getBytes());

			os.close();

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("ddd" + line);
			}

			count = 1;
			ResultMessage = "앱 설치 완료 되었습니다.";
			list.setText(ResultMessage);
			// System.exit(process.exitValue());

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "앱 설치에 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	public void fileRemove1(ArrayList<String> DeviceNum1) {
		if (count == 0) {
			alram.showMessageDialog(null, "디바이스 조회 버튼을 클릭 해주세요");
		} else {
			for (int i = 0; i < DeviceNum1.size(); i++) {
				System.out.println(DeviceNum1.get(i).toString());
				fileRemove2(DeviceNum1.get(i).toString());
			}
		}

	}

	public void fileRemove2(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String sealNum1 = "echo off \n\r";
			String sealNum2 = "adb -s " + DeviceNum1 + " uninstall com.skmc.okcashbag.home_google \n\r";
			String sealNum3 = "pause \n\r";

			OutputStream os = process.getOutputStream();
			os.write(sealNum1.getBytes());
			os.write(sealNum2.getBytes());
			os.write(sealNum3.getBytes());

			os.write("exit \n".getBytes());

			os.close();

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("ddd" + line);
			}

			count = 1;
			ResultMessage = "앱 삭제 완료  되었습니다.";
			list.setText(ResultMessage);

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "앱 삭제 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	public void dateClear1(ArrayList<String> DeviceNum1) {

		if (count == 0) {
			alram.showMessageDialog(null, "디바이스 조회 버튼을 클릭 해주세요");
		} else {
			for (int i = 0; i < DeviceNum1.size(); i++) {
				System.out.println(DeviceNum1.get(i).toString());
				dateClear2(DeviceNum1.get(i).toString());
			}
		}

	}

	public void dateClear2(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String sealNum1 = "echo off \n\r";
			String sealNum2 = "adb -s " + DeviceNum1 + " shell pm clear com.skmc.okcashbag.home_google \n\r";
			String sealNum3 = "pause \n\r";

			OutputStream os = process.getOutputStream();
			os.write(sealNum1.getBytes());
			os.write(sealNum2.getBytes());
			os.write(sealNum3.getBytes());

			os.write("exit \n".getBytes());

			os.close();

			String line = null;
			while ((line = br.readLine()) != null) {
				System.out.println("ddd" + line);
			}

			count = 1;
			ResultMessage = "데이터 삭제 완료  되었습니다.";
			list.setText(ResultMessage);
			// System.exit(process.exitValue());

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "데이터 삭제 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.toLowerCase().endsWith(".txt");
	}

}
