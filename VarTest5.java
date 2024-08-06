package day01;
import java.util.Scanner;
/**
 *  4칙연산과 나머지 연산을 하는 간단한 계산기 예제
 */
public class VarTest5 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		// 1. 4칙 연산을 입력받아 저장하는 변수 선언
		String opp;
		
		// 2. 2개 정수 입력 받아 저장하는 변수 선언
		int num1, num2;
		
		// 3. 더하기면 +, 뺴기면 -, 곱셈은 *, 나누기는 /, 나머지연산은 %
		Scanner sc = new Scanner(System.in);
		System.out.println("연산자를 선택하시오: ");
		opp = sc.next();
		int result = 0;
		
		System.out.print("첫 번쨰 정수를 입력하시오: ");
		num1 = sc.nextInt();
		System.out.print("두 번쨰 정수를 입력하시오: ");
		num2 = sc.nextInt();
		
		if(opp.equals("+")) {
			result = num1 + num2;
		}else if(opp.equals("-")) {
			result = num1 - num2;
		}else if(opp.equals("*")) {
			result = num1 * num2;
		}else if(opp.equals("/")) {
			result = num1 / num2;
		}else if(opp.equals("%")) {
			result = num1 % num2;
		}
		// 결과 출력하기
		System.out.println("연산결과: "+ num1 + opp + num2 +"=" +result);
		

	}

}
