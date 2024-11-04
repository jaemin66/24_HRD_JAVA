class Animal(object):    # object는 최상위 부모 클래스 ---- 즉 object를 상속받고 있다는 의미
    name = "고양이"    # property == instance 변수   # public

    def sound(self, name):
        self.name = name
        print(f"{self.name}이 소리를 낸다.")

animal1 = Animal()    # 클래스를 객체화 시키는 것
animal2 = Animal()
animal3 = Animal()
# animal.sound("고양이")
# print(animal.sound("고양이"))

print('#'* 100)

class Fruit:
    name = '오렌지'
    color = '노란색'

    def taste(self):
        print('새콤하다.')

    def vitamin(self):
        print('비타민 C가 풍부하다.')

orange = Fruit()

print('과일명 : %s' % orange.name)
print('색상 : %s' % orange.color)
orange.taste()
orange.vitamin()