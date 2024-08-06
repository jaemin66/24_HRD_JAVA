package day01;
import java.util.Scanner;

public class VarTest4 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int year = sc.nextInt();
		String age_type = sc.next();
		int answer = 0;
		
		if(age_type.equals("Korea")) {
			answer = 2030 - year + 1;
		}else if(age_type.equals("Year")) {
			answer = 2030 - year;
		}
		System.out.println(answer);

		
	}

}


