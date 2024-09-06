flowers = ['할미꽃', '무궁화', '장미', "Iris",]
flowers.append("국화")
print(flowers)
flowers.sort()
flowers.reverse()
print(flowers)
# flowers.clear()
# flower = ['물망초']
# new_flowers = flowers + flower
# print(new_flowers)

print('#', 100)

scores = []

while True:

    score = int(input("성적을 입력하세요(종료할거면 -1을 누르세요) :   "))

    if score == -1:
        break
    else:
        scores.append(score)

print('총 점수는 : ', scores)

summation = 0

for subject in scores:
    summation += subject

print(f"총 점수는 : {summation}")
print(f"평균은 : {summation / len(scores)}")

print('#' * 100)

numbers = [[10, 20, 30], [40, 50, 60, 70, 80]]

print(numbers[0],[0])
print(numbers[0],[1])
print(numbers[0],[2])

print(numbers[1],[0])
print(numbers[1],[1])
print(numbers[1],[2])
print(numbers[1],[3])
print(numbers[1],[4])