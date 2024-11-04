# 소비형 함수
# Consumer function

def function(param_name: int) -> None:     # -> None 는 출력(return)이 없다는 의미
    result = param_name + 10
    print(result)

function(param_name= 10)




# 생산형
# Supplier

def supply_function() -> int:
    sum = 10 + 20
    print(sum)
    return sum

result = supply_function()
print(result)




# 함수형
# functional

def functional_function(param: int) -> int:
    result = param + 1_000
    return result

result = functional_function(param=100)
print(result)




# Show , display function

def show_function():
    print("잔액 : 2000")
    print("계좌 : 2090-10-222")
    print("계좌입금 불가능")
    print("계좌출금 불가능")

show_function()


def swapping_values(value1: int, value2: int) -> tuple:
    return (value2, value1)

result2 = swapping_values(1_234, 4_321)
print(result2)


def hello():
    print("안녕하세요!")

hello()


