class Member:
    def __init__(self, name, age):
        self.name = name
        self.age = age

    def show_member(self):
        print(self.name, "\t", self.age)

member1 = Member(age=40, name="Karl")
member1.show_member()