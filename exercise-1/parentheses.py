import sys
import re


def main(args):

    # Check if the user has provided a file path argument

    if len(args) < 2:
        print("Input file argument missing!")
        sys.exit()

    # Store the file path

    filepath = args[1]
    print("Found: " + filepath)

    # Read the contents of the file

    contents = ""
    try:
        contents = open(filepath, 'r').read()
    except IOError:
        print("Could not read file")

    # Format input

    contents = format_input(contents)

    # Setup push-down automaton
    # K = { k0, k1, k2 }
    # T = { (, ) }
    # V = { (, ), $ }
    # k1 = k0
    # A1 = $
    # F = { t2 }

    # f(k0, e, e) = { (k1, $) }
    # f(k1, e, '(') = { (k1, '(') }
    # f(k1, '(', ')') = { (k1, e) }
    # f(k1, $, e) = { (k2, e) }

    stack = ["$"]
    messages = []
    state = "k1"

    while True:
        char = contents[0] if len(contents) > 0 else ""
        if char == "(":
            contents = contents[1:]
            messages.append("Character: " + char)
            messages.append("Input: " + contents)
            stack.append(char)
            state = "k1"
            messages.append("PUSH: " + str(stack) + " -> State: " + state)
        elif stack[-1] == "(" and char == ")":
            contents = contents[1:]
            messages.append("Character: " + char)
            messages.append("Input: " + contents)
            stack.pop()
            state = "k1"
            messages.append("POP: " + str(stack) + " -> State: " + state)
        elif state == "k1" and stack[-1] == "$" and char == "":
            contents = contents[1:]
            messages.append("Character: " + char)
            messages.append("Input: " + contents)
            state = "k2"
            messages.append("END: " + str(stack) + " -> State: " + state)
            break
        else:
            messages.append("Character: " + char)
            messages.append("Input: " + contents)
            messages.append("BREAK: " + str(stack) + " -> State: " + state)
            break

    if stack[-1] == "$" and state == "k2":
        print("YES")
    else:
        print("NO")

    choice = input("Show steps (y/n)? ").lower()

    if choice == "y" or choice == "yes":
        for m in messages:
            print(m)


def format_input(string):
    string = string.replace('\n', ' ').replace('\r', '').replace(' ', '')
    string = re.sub("[^()]", "", string)
    return string


if __name__ == "__main__":
    main(sys.argv)
