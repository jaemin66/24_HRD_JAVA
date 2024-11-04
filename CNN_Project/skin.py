from keras.src.legacy.preprocessing.image import ImageDataGenerator
import keras

# 모델 로드
model = keras.models.load_model('SKIN_DISEASE_DETAIL_CATEGORICAL.keras')

# 테스트 데이터셋 준비
test_dir = 'D:\skin_disease_detail\skin_disease_detail2\TEST_CLASSES'  # 테스트 데이터셋의 경로
generator = ImageDataGenerator(rescale=1./255)

# 테스트 데이터셋 생성
test_generator = generator.flow_from_directory(
    directory=test_dir,
    target_size=(150, 150),
    class_mode='categorical',
    shuffle=False  # 순서를 유지하기 위해 False로 설정
)

print(test_generator.class_indices)

# 모델 평가
loss, accuracy = model.evaluate(test_generator)

print(f'Loss: {loss}')
print(f'Accuracy: {accuracy}')

from sklearn.metrics import confusion_matrix, classification_report
import numpy as np

# 예측값 얻기
predictions = model.predict(test_generator)
predicted_classes = np.argmax(predictions, axis=1)

# 실제 레이블 얻기
true_classes = test_generator.classes

# 혼동 행렬 및 보고서 출력
cm = confusion_matrix(true_classes, predicted_classes)
report = classification_report(true_classes, predicted_classes, target_names=test_generator.class_indices.keys())

print("Confusion Matrix:")
print(cm)
print("\nClassification Report:")
print(report)
