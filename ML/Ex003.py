import matplotlib.pylab as plt
from sklearn import linear_model
import numpy as np
import matplotlib.pyplot as plt

X = np.array([0.0, 1.0, 2.0])
y = np.array([3.0, 3.5, 5.5])
W = 0 # 기울기
b = 0 # 절편
lrate = 0.01 # 학습률
epochs = 1000 # 반복 횟수
n = float(len(X)) # 입력 데이터의 개수
# 경사 하강법
for i in range(epochs):
    y_pred = W*X + b # 예측값
    dW = (2/n) * sum(X * (y_pred-y))
    db = (2/n) * sum(y_pred-y)
    W = W - lrate * dW # 기울기 수정
    b = b - lrate * db # 절편 수정

print(round(W))
print(round(b))

# 선형 회귀 모델을 생성한다.
reg = linear_model.LinearRegression()
# 데이터는 파이썬의 리스트로 만들어도 되고 아니면 넘파이의 배열로 만들어도 됨
X = [[0], [1], [2]] # 2차원으로 만들어야 함
y = [3, 3.5, 5.5] # y = x + 3
# 학습을 시킨다.
reg.fit(X, y)
print(reg.coef_)
print(reg.intercept_)