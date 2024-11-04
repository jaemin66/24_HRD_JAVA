from sklearn import linear_model
import matplotlib.pyplot as plt

X = [174, 152, 138, 128, 186]
y = [71, 55, 46, 38, 88]
plt.scatter(X, y, color='red')
# plt.show()
regression = linear_model.LinearRegression()    # 생성자 호출
X =[[174], [152], [138], [128], [186]]   # Tensor 2인형태로 바꿔주기
y = [71, 55, 46, 38, 88]    # 정답은 그대로 써도 됨
regression.fit(X, y)
print(f"기울기 : {regression.coef_}")
print(f"바이어스 : {regression.intercept_}")
y_hat = regression.predict(X)
print(y_hat)
my_weight = regression.predict([[177]])
print(f"My weight : {my_weight}")
plt.plot(X, y_hat, color='blue', linewidth=4)
plt.show()