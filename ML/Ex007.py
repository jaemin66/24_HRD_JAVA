dachshund_length = [77, 78, 85, 83, 73, 77, 73, 80]
dachshund_height = [25, 28, 29, 30, 21, 22, 17, 35]

samoyed_length = [75, 77, 86, 86, 79, 83, 83, 88]
samoyed_height = [56, 57, 50, 53, 60, 53, 49, 61]

# import matplotlib.pyplot as plt
# plt.scatter(dachshund_length, dachshund_height, c='r', marker=".")
# plt.scatter(samoyed_length, samoyed_height, c='b', marker="*")
# plt.show()

import numpy as np
d_data = np.column_stack((dachshund_length, dachshund_height))  # 닥스훈트의 length, height 데이터가 따로 있어 합쳐서 데이터를 만드는 코드
print(d_data)
d_label = np.zeros(len(d_data))     # 닥스훈트의 정답을 0으로 만드는 코드
print(d_label)

s_data = np.column_stack((samoyed_length, samoyed_height))    # 사모예드의 length, height 데이터가 따로 있어 합쳐서 데이터를 만드는 코드
print(s_data)
s_label = np.ones(len(s_data))    # 사모예드의 정답을 1로 만드는 코드
print(s_label)


dogs = np.concatenate((d_data, s_data))    # 닥스훈트와 사모예드의 데이터를 합쳐 dogs 데이터를 만드는 코드
labels = np.concatenate((d_label, s_label))
print(dogs)
print(labels)


from sklearn.neighbors import KNeighborsClassifier
k = 3    # 3, 5, 7, 9
knn = KNeighborsClassifier(n_neighbors=k)
knn.fit(dogs, labels)     # fit,  학습을 시키는 코드

new_dog = [[78, 35]]
new_dog_predict = knn.predict(new_dog)
print(new_dog_predict)