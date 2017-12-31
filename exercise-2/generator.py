import sys
import random


def main(args):
    rules = {
        "ekfrasi": ["<oros>", "<ekfrasi> + <oros>"],
        "oros": ["<paragontas>", "<oros> * <paragontas>"],
        "paragontas": ["a", "b", "c"],
    }

    # First step

    print("<ekfrasi> =>")
    ekfrasi = random.choice(rules["ekfrasi"])
    print(ekfrasi + " =>")

    while "<ekfrasi>" in ekfrasi:
        ekfrasi_rand = random.choice(rules["ekfrasi"])
        ekfrasi = ekfrasi.replace("<ekfrasi>", ekfrasi_rand, 1)
        print(ekfrasi + " =>")

    # Second step

    while "<oros>" in ekfrasi:
        oros_rand = random.choice(rules["oros"])
        ekfrasi = ekfrasi.replace("<oros>", oros_rand, 1)
        print(ekfrasi + " =>")

    # Third step

    while "<paragontas>" in ekfrasi:
        paragontas_rand = random.choice(rules["paragontas"])
        ekfrasi = ekfrasi.replace("<paragontas>", paragontas_rand, 1)
        if "<" in ekfrasi:
            print(ekfrasi + " =>")
        else:
            print(ekfrasi)


if __name__ == "__main__":
    main(sys.argv)
