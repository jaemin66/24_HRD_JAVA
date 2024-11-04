from bs4 import BeautifulSoup
import urllib.request
import pandas as pd
import urllib.parse
# import numpy as np
import plotly.express as px
# import chardet
# import matplotlib.pyplot as plt
from matplotlib import font_manager, rc

font_path = "C:/Windows/Fonts/malgun.ttf"  # Windows의 경우 Malgun Gothic 글꼴 경로
font = font_manager.FontProperties(fname=font_path).get_name()
rc('font', family=font)

Pay_event = list()

pay_url = "https://finance.naver.com/sise/sise_quant.naver?sosok=0"
html = urllib.request.urlopen(pay_url)
soup_pay = BeautifulSoup(html, 'html.parser')

table = soup_pay.find('table', {'class': 'type_2'})
tag = table.find_all('tr')

for event in tag:
    event_td = event.find_all('td')
    if len(event_td) > 1:
        event_name = event_td[1].string
        current_price = event_td[2].string
        diff_price_span = event_td[3].find('span')
        diff_price = diff_price_span.string if diff_price_span else 'N/A'
        percent_change_span = event_td[4].find('span')
        percent_change = percent_change_span.string if percent_change_span else 'N/A'
        volume = event_td[5].string
        trading_value = event_td[6].string

        Pay_event.append([event_name] +
                         [current_price] +
                         [diff_price] +
                         [percent_change] +
                         [volume] +
                         [trading_value])

print(f'RESULT of Crawling : \r\n{Pay_event}')

Pay_tbl = pd.DataFrame(data=Pay_event,
                       columns=('종목', '현재가', '전일비', '등락률', '거래량', '거래대금'))

def clean_price(price_str):
    try:
        # 쉼표와 % 제거 후 실수형으로 변환
        return float(price_str.replace(',', '').replace('%', ''))
    except ValueError:
        return float('nan')  # 변환할 수 없는 경우 NaN으로 반환

Pay_tbl['현재가'] = Pay_tbl['현재가'].apply(clean_price)
Pay_tbl['등락률'] = Pay_tbl['등락률'].apply(clean_price)
Pay_tbl['거래량'] = Pay_tbl['거래량'].apply(clean_price)
Pay_tbl['거래대금'] = Pay_tbl['거래대금'].apply(clean_price)


Pay_tbl.to_csv("./Pay_table_Excel.csv",
               encoding='cp949', mode='w', index=True)
Pay_tbl.to_csv("./Pay_table.csv",
               encoding='utf-8', mode='w', index=True)


# 시가 및 종가 변화 그래프 예시
fig_current_price = px.line(
    Pay_tbl,
    x='종목',
    y='현재가',
    title='종목별 현재가 변화',
    labels={'종목': '종목', '현재가': '현재가'},
    markers=True
)

# 그래프 레이아웃 설정
fig_current_price.update_layout(
    xaxis_title='종목',
    yaxis_title='현재가',
    xaxis_tickangle=-45
)

# 그래프 보여주기
fig_current_price.show()

# 등락률 vs 거래량 그래프
fig_volume_change = px.scatter(
    Pay_tbl,
    x='거래량',
    y='등락률',
    text='종목',  # 종목 이름을 점에 레이블로 추가
    title='등락률 vs 거래량',
    labels={'거래량': '거래량', '등락률': '등락률 (%)'},
    color='등락률',
    color_continuous_scale='Viridis'
)

# 그래프 레이아웃 설정
fig_volume_change.update_layout(
    xaxis_title='거래량',
    yaxis_title='등락률 (%)',
    xaxis_type='log',  # 거래량이 큰 범위를 가질 수 있으므로 로그 스케일 적용
    yaxis_tickformat='%'
)

# 레이블 표시 설정
fig_volume_change.update_traces(textposition='top center')

# 그래프 보여주기
fig_volume_change.show()

# 거래량 vs 현재가 그래프 추가
fig_price_volume = px.scatter(
    Pay_tbl,
    x='거래량',
    y='현재가',
    text='종목',  # 종목 이름을 점에 레이블로 추가
    title='거래량 vs 현재가',
    labels={'거래량': '거래량', '현재가': '현재가'},
    color='현재가',
    color_continuous_scale='Bluered'
)

# 그래프 레이아웃 설정
fig_price_volume.update_layout(
    xaxis_title='거래량',
    yaxis_title='현재가',
    xaxis_type='log',  # 거래량이 큰 범위를 가질 수 있으므로 로그 스케일 적용
)

# 레이블 표시 설정
fig_price_volume.update_traces(textposition='top center')

# 그래프 보여주기
fig_price_volume.show()

# 전일비 및 등락률 변화 그래프 추가
fig_price_diff = px.scatter(
    Pay_tbl,
    x='전일비',
    y='등락률',
    text='종목',  # 종목 이름을 점에 레이블로 추가
    title='전일비 vs 등락률',
    labels={'전일비': '전일비 (등락폭)', '등락률': '등락률 (%)'},
    # size='거래량',
    color='등락률',
    color_continuous_scale='Inferno'
)

# 그래프 레이아웃 설정
fig_price_diff.update_layout(
    xaxis_title='전일비 (등락폭)',
    yaxis_title='등락률 (%)',
    yaxis_tickformat='%'
)

# 레이블 표시 설정
fig_price_diff.update_traces(textposition='top center')

# 그래프 보여주기
fig_price_diff.show()

# 등락률 vs 거래대금 그래프 추가
fig_price_value_change = px.scatter(
    Pay_tbl,
    x='거래대금',
    y='등락률',
    text='종목',  # 종목 이름을 점에 레이블로 추가
    title='등락률 vs 거래대금',
    labels={'거래대금': '거래대금 (원)', '등락률': '등락률 (%)'},
    color='등락률',
    color_continuous_scale='Plasma',
    size='거래대금',  # 거래대금에 따라 점 크기 조정
    hover_data=['현재가', '거래량']  # 추가 정보를 마우스 오버 시 제공
)

# 그래프 레이아웃 설정
fig_price_value_change.update_layout(
    xaxis_title='거래대금 (원)',
    yaxis_title='등락률 (%)',
    xaxis_type='log',  # 거래대금이 큰 범위를 가질 수 있으므로 로그 스케일 적용
    yaxis_tickformat='%'
)

# 레이블 표시 설정
fig_price_value_change.update_traces(textposition='top center')

# 그래프 보여주기
fig_price_value_change.show()