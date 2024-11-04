# class Student(object):
#     __name = "황재호"    ## property, 속성, 맴버변수, 인스턴스변수 라고 함 ( 다 같은 말 )    __ private
#     __kor = 80
#     __eng = 90
#     __math = 100
#
#     def get_summation(self):
#         sum = self.__kor + self.__eng + self.__math
#         return sum
#
# student = Student()
# print(f'총합은 : {student.get_summation()}')


print('#'*100)

class Circle:
    radius = 10

    def getArea(self):
        area = 3.141592 * self.radius ** 2
        return area

    def getCircum(self):
        circum = 2 * 3.141592 * self.radius
        return circum

cir = Circle()

print('반지름 : %d' % cir.radius)
print('원의 면적 : %.2f' % cir.getArea())
print('원주의 길이 : %.2f' % cir.getCircum())

print('#'* 100)

class Student:
    name = ""
    student_id = 0
    department = ""

    def __init__(self, name, student_id, department):
        self.name = name
        self.student_id = student_id
        self.department = department

gammie = Student("홍재민", "15", "HRD")
print(gammie.name)
print(gammie.student_id)
print(gammie.department)
print(gammie)