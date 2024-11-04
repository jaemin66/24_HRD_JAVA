import numpy as np
from sklearn import datasets

(diabets_X, diabetes_y) = datasets.load_diabetes(return_X_y=True)
print(diabets_X)
print(diabets_X.shape)
print(diabetes_y)
print(diabetes_y.shape)

bmi = diabets_X[:, 2]
print(bmi)
print(bmi.shape)

diabets_X_new = diabets_X[:, np.newaxis, 2]   # 1차원데이터이기 때문에 분류를 위해 2차원으로 만드는 코드
print(diabets_X_new)
print(diabets_X_new.shape)

from sklearn.model_selection import train_test_split
(X_train, X_test, y_train, y_test) = (train_test_split(diabets_X_new,
                                                       diabetes_y,
                                                       train_size=0.2,
                                                       random_state=0))
# print(X_train[10])
from sklearn import linear_model
regression = linear_model.LinearRegression()   # 클래스
regression.fit(X_train, y_train)
y_predict = regression.predict(X_test)
print(y_predict)
print(y_test)

import matplotlib.pyplot as plt
plt.scatter(y_test, y_predict)
plt.show()