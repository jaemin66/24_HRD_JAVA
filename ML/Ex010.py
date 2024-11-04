import keras
import numpy as np
import tensorflow as tf

X = tf.constant([[0, 0], [0, 1], [1, 0], [1, 1]])
y = tf.constant([0, 1, 1, 0])

model = keras.models.Sequential(name="XOR")
# # keras.layers.Input()
# input = keras.layers.Input(shape=(2,))
# model.add(input)
# layer1 = keras.layers.Dense(units=4, activation='sigmoid', name='Layer1')
# model.add(layer1)
# layer2 = keras.layers.Dense(units=2, activation='sigmoid', name='Layer2')
# model.add(layer2)
# output = keras.layers.Dense(units=1, activation='sigmoid', name='OUTPUT')
# model.add(output)
# print(model.summary())

# model.compile(loss='mse', optimizer=keras.optimizers.SGD(learning_rate=0.7))  # optimizer는 미분을 어떻게 해줄건지를 의미
# model.fit(X, y, epochs=5000, verbose=2)
# print(model.predict(X))
# model.save('XORGATE.keras')

load_model = keras.models.load_model(filepath='XORGATE.keras')
load_model.summary()
x = np.array([[0, 0], [0, 1], [1, 0], [1, 1]])
print(load_model.predict(x))