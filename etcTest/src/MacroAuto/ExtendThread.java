package MacroAuto;

import java.io.File;
import java.io.IOException;
import java.util.List;

class ExtendThread implements Runnable {
	String inPathinfo1;
	String outPathinfo1;
	static String DIR = System.getProperty("user.dir");
	static boolean XLSM = false;
	

	public ExtendThread(String inpath, String outpath) {
		// TODO Auto-generated constructor stub
		this.inPathinfo1 = inpath;
		this.outPathinfo1 = outpath;
	}

	@Override
	public void run() {
		FileControll fc = new FileControll();
		ExcelExecute ee = new ExcelExecute();
		ProcessSearch ps = new ProcessSearch();
		UiMain um = new UiMain();

		String inFolder = inPathinfo1;
		String outFolder = outPathinfo1;

		String folderSelect = "";
		try {

			while (!Thread.currentThread().isInterrupted()) {
			
			
				for (int i = 0; i < fc.folderSearch(inFolder).size(); i++) {
					folderSelect = fc.folderSearch(inFolder).get(i).toString();

					fc.fileMake(folderSelect, outFolder, DIR);

					List<File> fileList = fc.getDirFileList(folderSelect);

					for (int j = 0; j < fileList.size(); j++) {
						String fileName = fileList.get(j).getName();
						fc.fileCopy(folderSelect + "\\" + fileName, outFolder + "\\" + fileName);
						um.currentinfo.setText(
								"총 폴더 : " + (fc.folderSearch(inFolder).size() + 1) + " / " + "진행 중 폴더 : " + (i + 1));
					}
					ee.test1();

					while (ps.result() != false) {

						if (ps.result() != false) {
							// System.out.println("엑셀 정상 실행중입니다.");

						} else {
							// System.out.println("엑셀 종료되어 있는 상태입니다.");

						}
						Thread.sleep(3000);
					}

					for (int h = 0; h < fileList.size(); h++) {
						Thread.sleep(1500);

						fc.fileDelete(outFolder + "\\" + fileList.get(h).getName().toString());
					}

				}

				for (int i = 0; i < fc.folderSearch(inFolder).size(); i++) {
					folderSelect = fc.folderSearch(inFolder).get(i).toString();
					List<File> fileList = fc.getDirFileList(folderSelect);

					for (int j = 0; j < fileList.size(); j++) {

						String fileName = fileList.get(j).getName();
						if (fileName.endsWith(".xlsm") == true) {
							XLSM = true;
						}
					}
					if (XLSM == true) {
						for (int k = 0; k < fileList.size(); k++) {

							String fileName = fileList.get(k).getName();

							fc.fileMove(folderSelect + "\\" + fileName, fc.pathChange(folderSelect) + "\\" + fileName,
									fc.pathChange(folderSelect));

						}

						XLSM = false;
						File f = new File(folderSelect);
						f.delete();
					}

				}

			}

		} catch (

		InterruptedException | IOException e)

		{

		}

	}

}
