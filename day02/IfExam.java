package day02;
import java.util.Scanner;
public class IfExam {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		
		int jumsu = 90;
		System.out.print("점수를 입력하세요:");
		jumsu = sc.nextInt();
		String hakjum = "";
		
		if(jumsu >= 95) {
			hakjum = "A+";
		}else if(jumsu >= 85) {
			hakjum = "A";
		}else if(jumsu >= 70) {
			hakjum = "B+";
		}else if(jumsu >= 60) {
			hakjum = "B";
		}else {
			hakjum = "C";
		}
		
		System.out.println("당신의 학점은: " + hakjum + "입니다");
		

	}

}

