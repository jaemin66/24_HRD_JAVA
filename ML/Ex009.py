from sklearn.linear_model import Perceptron
X = [[0, 0],
     [0, 1],
     [1, 0],
     [1, 1]]

y = [0, 1, 1, 0]

percept = Perceptron(tol=0.001)
percept.fit(X, y)
y_predict = percept.predict(X)
print(y_predict)

y_prediction = percept.predict([[0, 1]])
print(f"결과 값 : {y_prediction}")