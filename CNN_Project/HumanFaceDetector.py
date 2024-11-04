import numpy as np
import cv2    # 이게 openCV

face_images:list = []    # 빈 리스트 만들기
for i in range(15):
    file = "./faces/" + "img{0:02d}.jpg".format(i + 1)   # faces 디렉토리 안에 이미지를 파일에 연결하겠다란 의미
    img = cv2.imread(file)    # 이미지를 읽어오세요 란 의미
    img = cv2.resize(img, (64, 64))    # 이미지 픽셀이 다 다르기때문에 다 똑같이 맞춰야함
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)    # openCV 는 순서가 BGR로 되어있어 RGB로 바꿔줘야 함
    face_images.append(img)

# 영상을 화면에 출력해보기
import matplotlib.pyplot as plt

# 영상을 칸으로 만들어서 화면에 보여주는 함수
def plot_images(n_row:int, n_col:int, image:list[np.ndarray]) -> None:
    # fig = plt.figure()   # 화면에 영상의 갯수를 만들겠다
    (_, ax) = plt.subplots(n_row, n_col, figsize=(n_col, n_row))
    for i in range(n_row):
        for j in range(n_col):
            if n_row <= 1:
                axis = ax[j]
            else:
                axis = ax[i, j]    # ax[i][j] 랑 똑같음
                axis.imshow(image[i* n_col + j])
    plt.show()
    return None
plot_images(n_row=3, n_col=5, image=face_images)


# 동물 이미지 전처리

animal_images:list = []    # 빈 리스트 만들기
for i in range(15):
    file = "./animals/" + "img{0:02d}.jpg".format(i + 1)   # faces 디렉토리 안에 이미지를 파일에 연결하겠다란 의미
    img = cv2.imread(file)    # 이미지를 읽어오세요 란 의미
    img = cv2.resize(img, (64, 64))    # 이미지 픽셀이 다 다르기때문에 다 똑같이 맞춰야함
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)    # openCV 는 순서가 BGR로 되어있어 RGB로 바꿔줘야 함
    animal_images.append(img)

plot_images(n_row=3, n_col=5, image=animal_images)


# 학습할 데이터 만들고, 레이블링 하기
X = face_images + animal_images     # numpy 이기 때문에 + 연산이 가능함
y = [[1, 0]] * len(face_images) + [[0, 1]] * len(animal_images)  # 정답을 사람이미지는 [1, 0] , 동물은 [0, 1]로 레이블링
print(y)   # 레이블링 확인하기

X = np.array(X)  # numpy 만들어주기
y = np.array(y)  # numpy 만들어주기

# CNN 모델 만들기
import keras

model = keras.models.Sequential(name="HUMAN_FACE_DETECTOR")

# input = keras.layers.Input(shape=(64, 64, 3))
# model.add(input)
# model.add(keras.layers.Conv2D(filters=64, kernel_size=(3, 3)))
# model.add(keras.layers.MaxPool2D(pool_size=(2, 2), strides=2))
# model.add(keras.layers.Conv2D(filters=64, kernel_size=(3, 3)))
# model.add(keras.layers.MaxPool2D(pool_size=(2, 2), strides=2))
# model.add(keras.layers.Conv2D(filters=32, kernel_size=(3, 3)))
# model.add(keras.layers.MaxPool2D(pool_size=(2, 2), strides=2))
# model.add(keras.layers.Conv2D(filters=32, kernel_size=(3, 3)))
# model.add(keras.layers.MaxPool2D(pool_size=(2, 2), strides=2))  # CNN 층을 총 4개 만듬
#
# # fully connected  => DEEPLEARNING
# model.add(keras.layers.Flatten(name="FLATTEN"))
# model.add(keras.layers.Dense(units=128, activation='relu', name="LAYER1"))
# model.add(keras.layers.Dense(units=64, activation='relu', name="LAYER2"))
# model.add(keras.layers.Dense(units=32, activation='relu', name="LAYER3"))
# model.add(keras.layers.Dense(units=2, activation='softmax', name='OUTPUT'))
#
# model.summary()
# model.compile(optimizer='adam', loss="categorical_crossentropy", metrics=['accuracy'])
# history = model.fit(x=X, y=y, epochs=500)
# model.save('FACE_CNN.keras')

load_model = keras.models.load_model('FACE_CNN.keras')
load_model.summary()

test_images:list = list()
for i in range(10):
    file = "./examples/" + "img{0:02d}.jpg".format(i + 1)
    img = cv2.imread(file)
    img = cv2.resize(img, (64, 64))
    img = cv2.cvtColor(img, cv2.COLOR_BGR2RGB)
    test_images.append(img)

test_images = np.array(test_images)   # numpy로 변환
plot_images(n_col=5, n_row=2, image=test_images)

predict_images = load_model.predict(x=test_images)
print(predict_images)
(_, ax) = plt.subplots(2, 5, figsize=(10, 4))
for i in range(2):
    for j in range(5):
        axis = ax[i, j]   # 2 * 5
        if predict_images[i * 5 + j][0] > 0.5:    # 사람의 얼굴
            axis.imshow(test_images[i * 5 + j])

plt.show()