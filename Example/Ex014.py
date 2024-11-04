from Example.Ex013 import result


def even_odd(number: int) -> None:
    if number % 2 == 1:
        print("홀수 입니다.")
    else:
        print("짝수 입니다.")

even_odd(number = 5)


def besu5(n):
    if n % 5 == 0:
        rel = True
    else:
        rel = False

    return rel

num = int(input("양의 정수를 입력하세요: "))
result = besu5(num)

if result == True:
    print('%d -> 5의 배수이다.' % num)
else:
    print('%d -> 5의 배수가 아니다.' % num)





def calculate_volume(radius,height):
    return (radius ** 2 * 3.14 * height)


radius = float(input("반지름은?  "))
height = float(input("높이는?  "))
volume = calculate_volume(height=height, radius=radius)

print(f"엔진의 실린더의 부피 : {volume}")
