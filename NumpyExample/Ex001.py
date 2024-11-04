import numpy as np
from numpy.ma.core import shape

# list_a = [1, 2, 3, 4, 5]
# print(type(list_a))
# np_array1 = np.array(list_a, dtype=np.int32)
# print(type(np_array1))
# print(list_a)
# print(np_array1)
# # print(list_a.shape)
# print(np_array1.shape)
# print(np_array1.shape, np_array1.ndim, np_array1.itemsize, np_array1.size)
#
# a = np.array([2, 3, 4])
#
# print(a.shape, a.ndim, a.dtype, a.itemsize, a.size)


# np_array2 = np.array([[1, 2, 3], [4, 5, 6]])
# print(np_array2.shape)
# print(np_array2)
# print(np_array2.ndim, np_array2.itemsize, np_array2.size)



# np_array3 = np.array([10, 20, 30])
# result = np_array3 * 3
# print(result)
# result2 = np_array3 + 10
# print(result2)


# np_array4 = np.array([[10, 20, 30],
#                      [40, 50, 60]])
# np_array5 = np.array([2, 3, 4])
# result = np_array4 + np_array5
# print(result)


# print(np.eye(20) * 100)

# np_array4 = np.arange(0, 10)
# print(np_array4)
# np_array5 = np_array4.reshape((5, -1))   # -1을 쓰면 하나만 적어도 거기에 맞춰서 해줌
# print(np_array5)


# np_array6 = np.linspace(0, 10, 1000)
# print(np_array6)

# np_array7 = np.array([[1, 1], [2, 2], [3, 3]])
# result = np.insert(np_array7, 1, 4, axis = 1)
# print(result)


# np_value2 = np.array([[1, 2, 3], [4, 5, 6], [7, 8, 9], [0, 1, 2]])
# print(np_value2)
# print(np_value2[0][0])    # 일반적으로 접근하는 방식
# print(np_value2[0, 0])    # numpy에서 쓰는 방식   --- 둘 다 결과는 같음


# np_value3 = np.array([1, 2, 3, 4, 5], dtype=np.int32)
# print(f'평균값 : {np_value3.mean()}')
# print(f'최대값 : {np_value3.max()}')
# print(f'최소값 : {np_value3.min()}')
#
# print(np_value3.flatten())
# print(np_value2.T)


# np_value4 = np.array([5, 3, 2, 0, 1, 9])
# print(np_value4.sort())
# print(np_value4)
# print(np_value4[::-1])


# np_value5 = np.array([1, 2, 3])
# np_value6 = np.array([[3, 4, 5], [6, 7, 8]])
# np.append(np_value5, np_value6)
# result =np.append([np_value5], np_value6, axis=0)
# print(result)


# np_value7 = np.array([1, 2, 3, 4, 5, 6, 7, 8])
# np_value8 = np_value7.reshape(4, -1)
# print(np_value8)


# np_value9 = np.random.randn(10) * 10 + 175
# print(np_value9)
# print(np_value9.round(2))
# print(np_value9.astype(int))

# np.random.seed(2024)
# np_value10 = np.random.normal(165, 10, (10, 10))
# print(np_value10)


np_value11 = np.array([[1, 2], [1, -3]])
np_value12 = np.array([6, 1])
result = np.linalg.solve(np_value11, np_value12)
print(result)