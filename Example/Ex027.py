from random import randint

iteration = 0
one = 0
two = 0
three = 0
four = 0
five = 0
six = 0
count= 0

while iteration < 10000:

    count = randint(1,6)

    if count == 1:
        one += 1
    elif count == 2:
        two += 1
    elif count == 3:
        three += 1
    elif count == 4:
        four += 1
    elif count == 5:
        five += 1
    else:
        six += 1

    iteration += 1

print(f"1번이 나온 횟수 : {one}")
print(f"2번이 나온 횟수 : {two}")
print(f"3번이 나온 횟수 : {three}")
print(f"4번이 나온 횟수 : {four}")
print(f"5번이 나온 횟수 : {five}")
print(f"6번이 나온 횟수 : {six}")

print("\r\n")

print("*" * int(one // 50))
print("*" * int(two // 50))
print("*" * int(three // 50))
print("*" * int(four // 50))
print("*" * int(five // 50))
print("*" * int(six // 50))