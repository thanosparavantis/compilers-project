import sys


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

    contents = formatInput(contents)

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
    state = "k1"

    while True:
        char = contents[0] if len(contents) > 0 else ""
        print("Now: " + char)
        if char == "(":
            contents = contents[1:]
            print("Contents: " + contents)
            stack.append(char)
            state = "k1"
            print("PUSH: " + str(stack))
        elif stack[-1] == "(" and char == ")":
            contents = contents[1:]
            print("Contents: " + contents)
            stack.pop()
            state = "k1"
            print("POP: " + str(stack))
        elif state == "k1" and stack[-1] == "$" and char == "":
            contents = contents[1:]
            print("Contents: " + contents)
            state = "k2"
            print("END: " + str(stack))
            break
        else:
            print("Contents: " + contents)
            print("BREAK: " + str(stack))
            break

    if stack[-1] == "$" and state == "k2":
        print("YES")
    else:
        print("NO")


def formatInput(string):
    return string.replace('\n', ' ').replace('\r', '').replace(' ', '')


if __name__ == "__main__":
    main(sys.argv)
