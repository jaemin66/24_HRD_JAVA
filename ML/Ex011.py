import pandas as pd

# 데이터셋 파일 경로
dataset_path = 'C:/path/to/your/braille-character-dataset/braille_data.csv'

# 데이터 불러오기
data = pd.read_csv(dataset_path)

# 데이터 확인
print(data.head())