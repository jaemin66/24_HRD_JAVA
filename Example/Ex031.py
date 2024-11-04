def function1():
    print("function1")

def function2(func):
    return func()

func = function1
function2(func)