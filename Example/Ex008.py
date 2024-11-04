
colors: list = ['black', 'white', 'red', 'blue']

print(colors[0])

colors[0] = 'yellow'    # 이 줄은 set의 의미  set은 넣으하는의미, 즉 바꿔라의 의미

print(colors[0])     #  get의 의미  , get은 read의 의미  set은 write 의미

print(colors)

for i in colors:
    print(i)

# [:] --> slice operator
sliced_colors = colors[1:]    # 원본이 수정된게 아닌 그 값을 가져와서 바꾼 것
print(sliced_colors)          # 원본 수정을 slice effect 라고 함

# numbers = [1, 2, 3, 4, 5, 6 ,,,,,]  이 방법으로 많은 수를 list로 만들기보단 아래처럼
numbers = list(range(0, 1001))
print(numbers)  # range를 통해서 list 만들기

v = [1, 2, 3]
V = [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

value1 = V[0][0]   # get 한것
print(value1)

value1 = V[0]   # scalar 타입
print(value1)   # list 타입

for i in range(0, 3):
    for j in range(0, 3):
        scalar = V[i][j]
        print(scalar, end='\t')
    print()

print('#' * 100)

animals: list = ['사자', '토끼', '하이에나', '기린']

index = 0
size = len(animals)

while index < size:
    animal: str = animals[index]
    print(animal)
    index += 1

print('#' * 100)

questions = ['tr_in', 'b_s', '_axi', 'air_lane']
answer = ['a', 'u', 't', 'p']
count = 0

for i in range(len(questions)):
    q = '%s 에서 밑줄(_) 안에 들어갈 알파벳은?  ' % questions[i]
    ans = input(q)

    if ans == answer[i]:
        print('정답입니다!!')
        count += 1
    else:
        print('틀렸습니다.')

# print('최종 맞은 개수는 ' + str(count) + '입니다')
print(f'최종 맞은 개수는 {count} 입니다')