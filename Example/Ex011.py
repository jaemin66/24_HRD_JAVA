scores = [[75, 83, 90], [86, 86, 73], [76, 95, 83], [89, 96, 69], [89, 76, 93]]

for i in range(len(scores)):
    for j in range(len(scores[i])):
        score = scores[i][j]
        print(score, end='\t')
    print()

strings = [['잠자리', '풍뎅이', '여치'], ['짜장면', '파스타', '피자', '국수']]

for i in range(len(strings)):
    for j in range(len(strings[i])):
        print(strings[i][j], end='\t')
    print()


(x, y, z) = (10, 20, 30)  # tuple이다
x1: tuple = (10, 20, 30)  # tuple이다
x2: list = [10, 20, 30]   # list이다

# x2.remove()    x2에는 remove가 있다
# x1.            x1에는 remove가 없다

# menu: tuple = ('짜장면', '우동', '짬뽕', '볶음밥')
#
# print(menu)
# print(menu[0])
# print(menu[2])
# print(menu[0:3])
#
# menu[1] = '사천탕면'

print('#' * 100)

name = '홍지수'
scores = {'kor': 90, 'eng': 89, 'math': 95, 'science': 88}
print(scores)

scores['kor'] = 70
print(scores['kor'])

scores['music'] = 100
print(scores)

del scores['science']
print(scores)

print('이름 : %s' % name)
print('국어 : %s' % scores['kor'])
print('영어 : %s' % scores['eng'])
print('수학 : %s' % scores['math'])