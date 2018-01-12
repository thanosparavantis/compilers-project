import sys
import random


def main():
    rules = {
        "ekfrasi": ["<oros>", "<ekfrasi> + <oros>"],
        "oros": ["<paragontas>", "<oros> * <paragontas>"],
        "paragontas": ["a", "b", "c"],
    }

    # First step

    print("<ekfrasi> =>")
    ekfrasi = random.choice(rules["ekfrasi"])
    print(ekfrasi + " =>")

    step_counter = 1
    step_limit = 50 # could be 10, 20 etc.

    while "<ekfrasi>" in ekfrasi:
        if step_counter >= step_limit:
            break
        step_counter += 1
        ekfrasi_rand = random.choice(rules["ekfrasi"])
        ekfrasi = ekfrasi.replace("<ekfrasi>", ekfrasi_rand, 1)
        print(ekfrasi + " =>")

    # Second step

    while "<oros>" in ekfrasi:
        if step_counter >= step_limit:
            break
        step_counter += 1
        oros_rand = random.choice(rules["oros"])
        ekfrasi = ekfrasi.replace("<oros>", oros_rand, 1)
        print(ekfrasi + " =>")

    # Third step

    while "<paragontas>" in ekfrasi:
        if step_counter >= step_limit:
            break
        step_counter += 1
        paragontas_rand = random.choice(rules["paragontas"])
        ekfrasi = ekfrasi.replace("<paragontas>", paragontas_rand, 1)
        if "<" in ekfrasi:
            print(ekfrasi + " =>")
        else:
            print(ekfrasi)

    # If we haven't finished applying the syntactical rules.
    if "<" in ekfrasi:
        print()
        print("A total of " + str(step_limit) + " syntactical rules have been applied, the limit has been reached.")
        print("Applying shortest syntactical rules to remove non-terminal symbols.")
        print()
        while "<ekfrasi>" in ekfrasi:
            ekfrasi = ekfrasi.replace("<ekfrasi>", rules["ekfrasi"][0], 1)
            print(ekfrasi + " =>")

        while "<oros>" in ekfrasi:
            ekfrasi = ekfrasi.replace("<oros>", rules["oros"][0], 1)
            print(ekfrasi + " =>")

        while "<paragontas>" in ekfrasi:
            paragontas_rand = random.choice(rules["paragontas"])
            ekfrasi = ekfrasi.replace("<paragontas>", paragontas_rand, 1)
            if "<" in ekfrasi:
                print(ekfrasi + " =>")
            else:
                print(ekfrasi)

if __name__ == "__main__":
    main()
