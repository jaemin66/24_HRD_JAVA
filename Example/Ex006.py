for x in range(5):    # in 의 의미는 into , 즉 ~~ 속에서 ( range에서 0 이란것을 하나 꺼내서 x로 줌 그리고
                                                    # 계속해서 1을 꺼내서 x로 그리고 쭉 4까지 주고 끝난다 )
    print(f"{x}안녕하세요")

print("#" * 100)
for x in "안녕하세요":
    print(f"{x} : 안녕하세요.")

print("#" * 100)

for x in ['안', '녕', '하', '세', '요']:
    print(f"{x} : 안녕하세요")

print('#' * 100)

sum = 0
for i in range(1, 11):
    sum += i
    print(f"Summation : {sum}")

print('#' * 100)

for i in range(10):
    print(i, end=" ")
    print()


print('#' * 100)

for i in range(1, 10, 2):
    print(i, end="")
    print()

print('#' *  100)

sum = 0
for i in range(1, 201, 5):
    sum += i

    print(f'5의 배수의 합 : {sum}')

print('#' * 100)

for i in range(100, 301):     # 100 부터 300까지
    if i % 3 == 0:
        print(f'3의 배수 : {i}', end=" ")

print('#' * 100)

x: str = input("영어 입력")
for i in x:
    print(i, end="")

