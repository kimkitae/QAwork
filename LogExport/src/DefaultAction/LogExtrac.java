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

public class LogExtrac extends JFrame implements FilenameFilter {

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

		LogExtrac le = new LogExtrac();
		le.LogExtrac();

	}

	public void LogExtrac() {

		setTitle("Log추출하기");
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

		FileCreate = new JButton("파일 생성하기");
		FileCreate.setBounds(150, 100, 150, 45);
		FileCreate.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				FileCreate1(DeviceNum1);

			}

		});

		getLayeredPane().add(FileCreate);

		LogReset = new JButton("로그 초기화");
		LogReset.setBounds(300, 100, 150, 45);
		LogReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				LogReset1(DeviceNum1);

			}
		});

		getLayeredPane().add(LogReset);

		LogExport = new JButton("로그 추출하기");
		LogExport.setBounds(450, 100, 150, 45);
		LogExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				LogExport1(DeviceNum1);
			}
		});

		getLayeredPane().add(LogExport);

		list = new JLabel("디바이스 목록 조회를 눌러주세요");
		list.setBounds(50, 50, 300, 50);
		getLayeredPane().add(list);

		clean = new JButton("파일 정리하기");
		clean.setBounds(0, 150, 150, 45);
		clean.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				Sumcount = Integer.parseInt(cnt.getText());

				LogClean(DeviceNum1);

			}
		});
		getLayeredPane().add(clean);

		cnt = new JTextField();
		cnt.setBounds(150, 150, 45, 45);
		cnt.setText(String.valueOf(Sumcount));
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
	public void FileCreate2(String DeviceNum1) {

		try {
			BufferedWriter LogReset = new BufferedWriter(new FileWriter(DIR + "\\" + DeviceNum1 + "LogReset.bat"));
			String Content = "adb -s " + DeviceNum1 + " logcat -c" + "\n" + "adb -s " + DeviceNum1
					+ " shell dumpsys batterystats --reset" + "\n" + "adb -s " + DeviceNum1
					+ " shell dumpsys batterystats --enable full-wake-history";

			LogReset.write(Content);
			LogReset.close();

			BufferedWriter LogExtract = new BufferedWriter(new FileWriter(DIR + "\\" + DeviceNum1 + "LogExtract.bat"));
			String Content2 = "set date2=%date:-=%\r\n" + "set time2=%time: =0%\r\n"
					+ "set time3=%time2:~0,2%%time2:~3,2%%time:~6,2%\r\n" + "set filename=" + DeviceNum1
					+ "logcat_log_%date2%%time3%.txt\r\n" + "set filename1=" + DeviceNum1
					+ "batterystats_%date2%%time3%.txt\r\n" + "adb -s " + DeviceNum1 + " shell dumpsys batterystats > "
					+ DeviceNum1 + "/" + "%filename1%\r\n" + "adb -s " + DeviceNum1 + " logcat -v time > " + DeviceNum1
					+ "/" + "%filename%";

			LogExtract.write(Content2);
			LogExtract.close();

			ResultMessage = "파일 생성이 완료 되었습니다.";
			list.setText(ResultMessage);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			System.exit(1);
		}

	}

	public void FileCreate1(ArrayList<String> DeviceNum1) {

		// System.out.println(DeviceNum1.size());
		for (int i = 0; i < DeviceNum1.size(); i++) {
			for (int h = 1; h <= 10; h++) {
				File f = new File(DIR + "/" + DeviceNum1.get(i).toString());
				File ff = new File(DIR + "/" + DeviceNum1.get(i).toString() + "/" + h);
				if (f.exists()) {
					f.mkdirs();
				}
				if (!ff.exists()) {
					ff.mkdirs();
				}
				// System.out.println(DeviceNum1.get(i).toString());

			}
			FileCreate2(DeviceNum1.get(i).toString());
		}

	}

	public void LogReset1(ArrayList<String> DeviceNum1) {
		if (count == 0) {
			alram.showMessageDialog(null, "디바이스 조회 버튼을 클릭 해주세요");
		} else {
			for (int i = 0; i < DeviceNum1.size(); i++) {
				// System.out.println(DeviceNum1.get(i).toString());
				LogReset2(DeviceNum1.get(i).toString());
			}

		}
	}

	public void LogReset2(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String sealNum1 = "adb -s " + DeviceNum1 + " logcat -c \n\r";
			String sealNum2 = "adb -s " + DeviceNum1 + " shell dumpsys batterystats --reset \n\r";
			String sealNum3 = "adb -s " + DeviceNum1 + " shell dumpsys batterystats --enable full-wake-history \n\r";

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
			ResultMessage = "모두 초기화 되었습니다.";
			list.setText(ResultMessage);
			// System.exit(process.exitValue());

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "초기화에 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	public void LogExport1(ArrayList<String> DeviceNum1) {
		System.out.println(DeviceNum1.size());
		for (int i = 0; i < DeviceNum1.size(); i++) {
			System.out.println(DeviceNum1.get(i).toString());
			LogExport2(DeviceNum1.get(i).toString());

		}

	}

	public void LogExport2(String DeviceNum1) {

		Runtime runtime = Runtime.getRuntime();
		try {
			Process process = runtime.exec("C:\\WINDOWS\\system32\\cmd.exe");
			BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
			System.out.println(DIR);
			System.out.println("start " + DIR + "\\" + DeviceNum1 + "LogExtract.bat \n\r");
			String sealNum1 = "start " + DIR + "\\" + DeviceNum1 + "LogExtract.bat \n\r";

			OutputStream os = process.getOutputStream();
			os.write(sealNum1.getBytes());

			// os.write("exit \n".getBytes());

			os.close();

			String line = null;
			while ((line = br.readLine()) != null) {
				// System.out.println("ddd"+line);
			}

			ResultMessage = "Log 추출 되었습니다.";
			list.setText(ResultMessage);

		} catch (IOException e) {
			e.printStackTrace();
			ResultMessage = "Log 추출에 실패하였습니다.";
			list.setText(ResultMessage);
			System.exit(1);
		}

	}

	public void LogClean(ArrayList<String> DeviceNum1) {

		LogExtrac aa = new LogExtrac();
		int count = Integer.valueOf(cnt.getText());
		ArrayList<String> filearray = new ArrayList<String>();
		for (int i = 0; i < DeviceNum1.size(); i++) {
			File path = new File(DIR + "/" + DeviceNum1.get(i));
			String filelist[] = path.list();
			for (int b = 0; b < filelist.length; b++) {
				if (aa.accept(path, filelist[b].toString())) {
					filearray.add(filelist[b].toString());
				}
			}
			for (String move : filearray) {
				File filemove = new File(DIR + "/" + DeviceNum1.get(i).toString() + "/" + move);
				File filemove2 = new File(DIR + "/" + DeviceNum1.get(i).toString() + "/" + cnt.getText() + "/" + move);

				boolean isMoved = filemove.renameTo(filemove2);

			}

		}

		ResultMessage = "파일을 정리하였습니다.";
		list.setText(ResultMessage);
		Sumcount = Sumcount + 1;
		cnt.setText(String.valueOf(Sumcount));

	}

	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.toLowerCase().endsWith(".txt");
	}

}
