## 파일 읽어오기
file = open(file='scores.txt', mode='r', encoding='UTF-8')
line_one = file.readline()
# print(lines)
#
# for line in lines:
#     print(line, end="")
print(line_one)

print(line_one.split(sep=","))
line_one_values = line_one.split(sep=",")

total = (int(line_one_values[1]) +
         int(line_one_values[2]) +
         int(line_one_values[3]) +
         int(line_one_values[4]) +
         int(line_one_values[5]))       # index[0]을 포함하지 않은 이유 ==> 0번은 사람이름이기 떄문에 더할 수가 없음
print(f'Total score : {total}')

file.close()
