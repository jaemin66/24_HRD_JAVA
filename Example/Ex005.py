# from unittest import removeResult
#
# from Example.Ex002 import number1
#
# score = int(input("점수 입력 "))
# result: str = ""
#
# if score >= 90:
#     result = 'A'
# elif score >= 80:
#     result = 'B'
# elif score >= 70:
#     result = 'C'
# elif score >= 60:
#     result = 'D'
# else:
#     result = 'F'
#
# print(f"Your grade is {result}.")
#
#
#
# print("기능 선택")
# print('1.더하기')
# print('2.빼기')
# print('3.곱하기')
# print('4.나눈기')
# print()

s = input('계산기 기능을 선택하세요 (1/2/3/4): ')

num1 = int(input('첫 번째 정수를 입력하세요 : '))
num2 = int(input('두 번쨰 정수를 입력하세요 : '))

if s == '1':
    print(num1 + num2)
elif s == '2':
    print(num1 - num2)
elif s == '3':
    print(num1 * num2)
elif s == '4':
    print(num1 / num2)
else:
    print("입력 숫자가 잘못되었습니다.")
