package day02;

public class ForExam1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// for(초기식; 조건식; 증감식) { 반복 수행문 }
		// 1에서 100까지 짝수들의 총 합을 계산하여 출력하시오.
		int i, sum=0;
		
		for(i = 0; i <= 100; i++) {
			// i가 짝수인지 조건 판단하여 맞으면 누적, 그렇지 않으면 무시
			if(i % 2 == 0) {
				sum = sum + i;
			}
		}
		System.out.println("0에서 100까지 짝수들의 합: " + sum);
	}

}
