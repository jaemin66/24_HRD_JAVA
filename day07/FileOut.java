package day07;

import java.io.*;
public class FileOut {

	/**
	 *  키보드로 입력받고, 파일로 출력하기
	 *  파일이름 : result.txt
	 */
	
	public static void main(String[] args) {
		
		System.err.println("파일로 저장할 내용을 입력하세요=>");
		int n = 0;
		try {
			FileOutputStream fos = new FileOutputStream("test.txt", true);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			FileReader fr = new FileReader("test.txt");
			OutputStreamWriter osw = new OutputStreamWriter(System.out);
			
		while((n = fr.read()) != -1) {
			
			osw.write(n);
			osw.flush();
			
		}
		fos.close();
		bos.close();
			
		}catch(FileNotFoundException fd) {
			
		}catch(IOException i) {
			
		}
		

	}

}
