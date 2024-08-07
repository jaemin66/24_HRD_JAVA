package day02;
import java.util.Scanner;
public class IfExam2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		
		int menew = 0; 
		String A = "";
		
		System.out.println("메뉴 선택");
		System.out.println("[1] 구매자 [2] 판매자");
		
		System.out.println("메뉴를 선택하세요 ->");
		menew = sc.nextInt();
		
		if(menew == 1) {
			A = "구매자";
		}else  {
			A = "판매자";
		}
		
		System.out.println("환영합니다!" + A + "로그인 하였습니다");

	}

}

