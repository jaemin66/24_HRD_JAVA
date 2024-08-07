package day02;

public class ForExam2 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// for(초기식; 조건식; 증감식) { 반복 수행문 }
		int i;
		for(i = 2; i <= 9; i++) {
			System.out.println("##" + i + "단##");
		
			for(int j = 1; j <= 9; j++) {
				System.out.println(i + "*" + j + "=" + (i*j));
			}
		}
		
	}

}
