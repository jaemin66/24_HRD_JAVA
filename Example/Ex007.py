#
# i = 1
# sum = 0
#
# while i <= 100:
#     sum += i
#     print(f"{i} : {sum}")
#     i += 1
#
# print("총합은 : ", sum)


s = 'Python is widely used by a number of big companies'

index = 0
count = 0
word = s.upper()
size = len(word)

# print("Python is widely used by a number of big companies".upper())
while index <= size - 1:
    if word[index] == 'A' or word[index] == 'I' or word[index] == 'O' \
            or word[index] == 'U' or word[index] == 'E':
        # print('모음 : ', word[index], end="\t")
        count += 1
    index += 1

    print(f"모음의 개수는 : {count}")