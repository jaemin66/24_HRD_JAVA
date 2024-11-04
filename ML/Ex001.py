import seaborn as sns
import pandas as pd

titanic = sns.load_dataset("titanic")
print(titanic.isnull().sum())
titanic['age'] = round(titanic['age'].fillna(titanic['age'].median()))   # age가 비어있는 칸을 중간값으로 채우라는 의미
print(titanic)
print(titanic['embarked'].value_counts(()))
titanic['embarked']= titanic['embarked'].fillna('S')
titanic.to_csv("titanic.csv", index=False)
