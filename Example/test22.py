from bs4 import BeautifulSoup
import urllib.request
import pandas as pd
import urllib.parse

base_url = "https://www.koreabaseball.com/Player/Search.aspx"
teams = ["SSG", "롯데", "KIA"]  # 예시 팀명, 실제 팀명으로 대체 필요
positions = ["투수", "타자"]  # 예시 포지션, 실제 포지션으로 대체 필요

players = []

for team in teams:
    for position in positions:
        # URL을 생성하기 위한 쿼리 문자열 구성
        query_params = {
            'team': team,  # 팀 파라미터, 실제로 필요한 파라미터로 대체 필요
            'position': position  # 포지션 파라미터, 실제로 필요한 파라미터로 대체 필요
        }
        query_string = urllib.parse.urlencode(query_params)
        search_url = f"{base_url}?{query_string}"

        html = urllib.request.urlopen(search_url)
        soup_kbo = BeautifulSoup(html, 'html.parser')

        # 결과 테이블 추출
        table = soup_kbo.find('table', {'class': 'tData01 tt'})  # 실제 클래스 이름으로 대체 필요

        if table:
            rows = table.find_all('tr')

            for row in rows[1:]:
                cells = row.find_all('td')
                if len(cells) > 0:
                    name = cells[1].get_text()
                    team = cells[2].get_text()
                    avg = cells[3].get_text()
                    hit = cells[8].get_text()
                    h2 = cells[9].get_text()
                    h3 = cells[10].get_text()
                    hr = cells[11].get_text()
                    rbi = cells[13].get_text()

                    players.append([name, team, avg, hit, h2, h3, hr, rbi])

# DataFrame으로 변환
Kbo_tbl = pd.DataFrame(data=players,
                       columns=['이름', '팀', '타율', '안타', '2루타', '3루타', '홈런', '타점'])

# CSV 파일로 저장
Kbo_tbl.to_csv("./Kbo_filtered_players_Excel.csv",
               encoding='cp949', mode='w', index=True)
Kbo_tbl.to_csv("./Kbo_filtered_players.csv",
               encoding='utf-8', mode='w', index=True)

