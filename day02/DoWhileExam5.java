package day02;
import java.util.Scanner;
public class DoWhileExam5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner scan = new Scanner(System.in);

		System.out.println("메세지를 입려하세요.");
		System.out.println("프로그램을 종료하려면 q를 입력하세요.");
		
		String msg = "";
		
		do {
			System.out.print(">");
			msg = scan.next();
			System.out.println(msg);
			
			
		}while(!msg.equals("q"));
		System.out.println("프로그램 종료");
		
	}

}
