import keras
import tensorflow as tf

(train_images, train_labels), (test_images, test_labels) = keras.datasets.mnist.load_data()
train_images = train_images.reshape((60000, 28, 28, 1))
test_images = test_images.reshape((10000, 28, 28, 1))
(train_images, test_images) = (train_images / 255.0, test_images / 255.0)   # 0~1 사이로 만들기 위해--- 즉 정규화를 위해서 이 코드 필요

model = keras.Sequential(name='CNN')
# input = keras.Input(shape=(28, 28, 1))    #  1은 흑백을 의미  28, 28 은 가로 세로
# model.add(input)
# model.add(keras.layers.Conv2D(32, (3, 3), activation='relu'))    #  이미지를 받기 때문에 2D를 사용
# model.add(keras.layers.MaxPool2D((2,2)))   #   이건 무조건 해줘야함  MAXPool
# model.add(keras.layers.Conv2D(64, (3, 3), activation='relu'))    #  이미지를 받기 때문에 2D를 사용
# model.add(keras.layers.MaxPool2D((2,2)))   #   이건 무조건 해줘야함  MAXPool
# model.add(keras.layers.Conv2D(64, (3, 3), activation='relu'))    #  이미지를 받기 때문에 2D를 사용
# model.add(keras.layers.MaxPool2D((2,2)))   #   이건 무조건 해줘야함  MAXPool

# deep learning
# model.add(keras.layers.Flatten())   # 분류된 이미지를 눌러서 1차원으로 만들기
# model.add(keras.layers.Dense(64, activation='relu', name='DNN_LAYER1'))
# model.add(keras.layers.Dense(64, activation='relu', name='DNN_LAYER2'))
# model.add(keras.layers.Dense(10, activation='softmax', name='OUTPUT'))

# model.summary()
# model.compile(optimizer='adam', loss='sparse_categorical_crossentropy', metrics=['accuracy'])  # accuracy는 정확도를 나타내줌
# model.fit(train_images, train_labels, epochs=10)
# model.save('CNN_MNIST.keras')
cnn_model = keras.models.load_model('CNN_MNIST.keras')  # 저장된 것 불러오기