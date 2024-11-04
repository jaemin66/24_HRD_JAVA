# number = input("정수 입력 ")
# last_character = number[-1]
#
# if last_character in "02468":
#     print("짝수입니다.")
# if last_character in "13579":
#     print("홀수입니다.")
#
#
# pass 키워드  --- 일단 통과하고 나중에 작성하겠다 --> 일단 오류가 나는 걸 방지하기 위해
# number = int(input("정수 입력 "))
# if number > 0:
#     pass
# else:
#     print("음수이다.")

values = [1, 2, 3, 4, 5]
values.insert(0, 0)

list_values = [1, 0, 1, 0, 1, 0, 1, 1, 1, 1]
error = 0

while error in list_values:
    list_values.remove(error)
print(list_values)


scores = [10, 20, 30, 40, 50]
summation = 0

for element in scores:
    summation += element

print("총합은 : {}".format(summation))
print(f"총합은 : {sum(scores)}")


list_a = [1, 2, 3, 4, 5, 6, 7]
reversed_list_a = reversed(list_a)
print(reversed_list_a)    # 여기까지만 적으면 리스트를 뒤집은 값의 주소를 던져줌

for element in reversed_list_a:
    print(element, end=" ")     #  여기까지 적어야 콘솔에 값이 출력됨


