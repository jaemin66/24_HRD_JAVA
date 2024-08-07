package day02;

public class WhileExam3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// for(초기식; 조건식; 증감식) { 반복 수행문 }
		// 1에서 100까지 짝수들의 총 합을 계산하여 출력하시오.
		int num = 20;   
	
		while(num > 10) {   
			System.out.println(num--);
			}
		
		boolean flag = true;
		while(flag) {
			num--;
			if(num ==3) {
				System.out.println("num3 종료");
				flag = false;
			}
		}

	}

}
