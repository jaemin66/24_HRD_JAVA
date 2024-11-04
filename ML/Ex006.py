from sklearn.datasets import load_iris

iris = load_iris()
print(iris)

data = iris['data']
target = iris['target']
print(data)
print(data.shape)
print(target)
print(target.size)

import pandas as pd
iris_df = pd.DataFrame(iris.data, columns=iris.feature_names)
print(iris_df.head())  # head는 데이터가 너무 많이니 앞에 5개만 보여주라는 의미, head의 반대는 tail 뒤로 5개

from sklearn.model_selection import train_test_split
(X_train, X_test, y_train, y_test) = train_test_split(data, target, train_size=0.8)


from sklearn.neighbors import KNeighborsClassifier
k = 7
knn = KNeighborsClassifier(n_neighbors=k)
knn.fit(X_train, y_train)
y_predict = knn.predict(X_test)
print(y_predict)
print(y_test)

from sklearn import metrics
scores = metrics.accuracy_score(y_test, y_predict)
print(f"정확도 : {scores}")

new_flower = [[3,0, 4.0, 5.0, 2.0]]    # 만약 새로운 꽃을 반견했을 때 데이터를 넣는 방법
new_flower_predict = knn.predict(new_flower)
print(new_flower)