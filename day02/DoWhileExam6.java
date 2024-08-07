package day02;
import java.util.Scanner;
public class DoWhileExam6 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		
		int i = 1, sum = 0;
		while(i<=10) {
			i++;
			
			if(i % 2 != 0) {
				continue;   //무시
			}
			System.out.print(i+" ");
		}

		
	}

}
