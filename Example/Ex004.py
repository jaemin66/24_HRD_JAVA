# x:int = int(input("정숫값을 입력: "))
#
# if x > 79:
#     print("합격")
# else:
#     print("불합격")

number = input("당신의 주민들록번호를 입력하세요.")

first = number[7]

if first == '1' or first == '3':
    print("당신의 성별은 남성입니다")
else:
    print("당신의 성별은 여성입니다.")