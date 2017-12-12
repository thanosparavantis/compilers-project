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
    print("Contents: " + contents)

    # ...


def formatInput(string):
    return string.replace('\n', ' ').replace('\r', '').replace(' ', '')


if __name__ == "__main__":
    main(sys.argv)
