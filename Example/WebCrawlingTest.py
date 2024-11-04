from bs4 import BeautifulSoup
import urllib.request
import pandas as pd
import urllib.parse

kbo_players = list()

Kbo_hitter_url = "https://www.koreabaseball.com/Record/Player/HitterBasic/BasicOld.aspx"
html = urllib.request.urlopen(Kbo_url)
soup_kbo = BeautifulSoup(html, 'html.parser')

# 'tData01 tt' 클래스를 가진 테이블을 찾습니다.
table = soup_kbo.find('table', {'class': 'tData01 tt'})

if table is None:
    print("테이블을 찾을 수 없습니다.")
else:
    rows = table.find_all('tr')

    # 테이블 헤더를 추출합니다.
    headers = [th.get_text() for th in rows[0].find_all('th')]

    # 테이블 데이터 추출
    players = []
    for row in rows[1:]:  # 첫 번째 행은 헤더이므로 제외
        cells = row.find_all('td')
        if len(cells) > 0:
            # rank = cells[0].get_text()
            name = cells[1].get_text()
            team = cells[2].get_text()
            avg = cells[3].get_text()
            # game = cells[4].get_text()
            # pa = cells[5].get_text()
            # ab = cells[6].get_text()
            # run = cells[7].get_text()
            hit = cells[8].get_text()
            h2 = cells[9].get_text()
            h3 = cells[10].get_text()
            hr = cells[11].get_text()
            # tb = cells[12].get_text()
            rbi = cells[13].get_text()
            # sh = cells[14].get_text()
            # sf = cells[15].get_text()

            players.append([name, team, avg, hit, h2, h3, hr, rbi])

    # DataFrame으로 변환
Kbo_tbl = pd.DataFrame(data=players,
                       columns=('이름', '팀', '타율', '안타', '2루타', '3루타', '홈런', '타점'))
Kbo_tbl.to_csv("./Kbo_table_Excel.csv",
               encoding='cp949', mode='w', index=True)
Kbo_tbl.to_csv("./Kbo_table.csv",
               encoding='utf-8', mode='w', index=True)

