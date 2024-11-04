from random import randint

my_win_count = 0
your_win_count = 0
iteration = 0

while iteration < 500:

    my_dice = randint(1,6)
    your_dice = randint(1,6)

    print(f"My dice : {my_dice}")
    print(f"Your dice : {your_dice}")

    if my_dice > your_dice:
        my_win_count += 1
        print("I won!")

    elif my_dice == your_dice:
        print("Drew")

    else:
        your_win_count += 1
        print("You won!")

    iteration += 1

print("*" * my_win_count)
print("@" * your_win_count)
