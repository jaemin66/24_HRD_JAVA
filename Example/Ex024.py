import greet    # 파일이 들어 온 것
# import greet

from Example.greet import hello, nice_meet    # from을 쓰면 하나만 가져온단 의미
hello("홍재민")
nice_meet("홍재민")

# greet.hello("홍재민")
# greet.nice_meet("홍재민")