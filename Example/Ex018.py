scores = ['안소영, 97, 80, 93, 97, 93',
          '정에린, 86, 100, 93, 86, 90',
          '김세린, 91, 88, 99, 79, 92'
          ]

data = ""

for score in scores:
    data = data + score + '\n'

print(data)

file = open(file = "scores.txt", encoding='UTF-8', mode = 'w')
file.write(data)
file.close()


## 파일 읽어오기
file = open(file = 'scores.txt', mode = 'r', encoding = 'UTF-8')
lines = file.readlines()
# print(lines)

for line in lines:
    print(line, end="")
file.close()


