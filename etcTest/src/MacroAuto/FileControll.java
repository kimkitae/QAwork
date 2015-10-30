package MacroAuto;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

public class FileControll implements FilenameFilter {
	static ArrayList<String> al = new ArrayList<String>();

	// 파일을 존재여부를 확인하는 메소드
	public static Boolean fileIsLive(String isLivefile) {
		File f1 = new File(isLivefile);

		if (f1.exists()) {
			return true;
		} else {
			return false;
		}
	}

	// 파일을 생성하는 메소드
	public static void fileMake(String infolder, String outfolder, String dir) {
		try {
			FileWriter fw = new FileWriter(dir + "\\" + "Path.txt");
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(infolder);
			bw.newLine();
			bw.write(outfolder);
			bw.newLine();
			bw.write(dir + "\\" + "tmp_macro.xlsm");
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 파일을 삭제하는 메소드
	public static void fileDelete(String deleteFileName) {
		File I = new File(deleteFileName);
		I.delete();
	}

	// 파일을 복사하는 메소드
	public static void fileCopy(String inFileName, String outFileName) {
		try {
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);

			FileChannel fcin = fis.getChannel();
			FileChannel fout = fos.getChannel();
			
			long size = fcin.size();
			fcin.transferTo(0, size, fout);
			
			fout.close();
			fcin.close();
			
			fos.close();
			fis.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 파일을 이동하는 메소드
	public static void fileMove(String inFileName, String outFileName,String ouffolder) {
		try {
			System.out.println(ouffolder);
			File f = new File(ouffolder);
			if(!f.exists()){
				f.mkdirs();
			}
			
			
			FileInputStream fis = new FileInputStream(inFileName);
			FileOutputStream fos = new FileOutputStream(outFileName);

			FileChannel fcin = fis.getChannel();
			FileChannel fout = fos.getChannel();
			
			long size = fcin.size();
			fcin.transferTo(0, size, fout);
			
			fout.close();
			fcin.close();
			
			fos.close();
			fis.close();

			// 복사한뒤 원본파일을 삭제함
			fileDelete(inFileName);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// 디렉토리의 파일 리스트를 읽는 메소드
	public static List<File> getDirFileList(String dirPath) {
		// 디렉토리 파일 리스트
		List<File> dirFileList = null;

		// 파일 목록을 요청한 디렉토리를 가지고 파일 객체를 생성함
		File dir = new File(dirPath);

		// 디렉토리가 존재한다면
		if (dir.exists()) {
			// 파일 목록을 구함
			File[] files = dir.listFiles();

			// 파일 배열을 파일 리스트로 변화함
			dirFileList = Arrays.asList(files);
		}

		return dirFileList;
	}

	public ArrayList<String> folderSearch(String dir) {
		FileControll fc = new FileControll();

		ArrayList<String> unique = new ArrayList<String>(new LinkedHashSet<String>(fc.subDirList(dir)));

		return unique;

	}

	public ArrayList<String> subDirList(String dir) {

		File folder = new File(dir);
		File[] fileList = folder.listFiles();

		try {

			for (int i = 0; i < fileList.length; i++) {
				File file = fileList[i];
				if (file.isFile()) {
					accept(file, file.getCanonicalPath());
					// System.out.println(file.getParent());
					al.add(file.getParent());

				} else if (file.isDirectory()) {
					// System.out.println("디렉토리 이름 = " + file.getName());

					// 서브디렉토리가 존재하면 재귀적 방법으로 다시 탐색
					subDirList(file.getCanonicalPath().toString());
				}

			}

		} catch (IOException e) {

		}

		return al;

	}
	public String pathChange(String dir) {
		ArrayList<String> aa = new ArrayList<String>();
		String dirs = dir;

		String[] patha = dirs.split("\\\\");

		patha[1] = "success";
		String pathChange = patha[0];

		for (int j = 1; j < patha.length; j++) {
			pathChange += "\\" + patha[j].toString();
		}

		return pathChange;
	}
	@Override
	public boolean accept(File dir, String name) {
		// TODO Auto-generated method stub
		return name.toLowerCase().endsWith(".xlsm");
	}

}