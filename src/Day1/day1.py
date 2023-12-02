def number(string):
    numlist = []
    for i in range(0, string):
        if string[i] in "0123456789":
            numlist.append(int(letter))
        else:
            try:
                if string[i] == "o" and string[i + 1] == "n" and string[i + 2] == "e":
                    numlist.append(1)
                elif string[i] == "t" and string[i + 1] == "w" and string[i + 2] == "o":
                    numlist.append(2)
                elif string[i] == "t" and string[i + 1] == "h" and string[i + 2] == "r" and string[i + 3] == "e" and \
                        string[i + 4] == "e":
                    numlist.append(3)
                elif string[i] == "f" and string[i + 1] == "o" and string[i + 2] == "u" and string[i + 3] == "r":
                    numlist.append(4)
                elif string[i] == "f" and string[i + 1] == "i" and string[i + 2] == "v" and string[i + 3] == "e":
                    numlist.append(5)
                elif string[i] == "s" and string[i + 1] == "i" and string[i + 2] == "x":
                    numlist.append(6)
                elif string[i] == "s" and string[i + 1] == "e" and string[i + 2] == "v" and string[i + 3] == "e" and \
                        string[i + 4] == "n":
                    numlist.append(7)
                elif string[i] == "e" and string[i + 1] == "i" and string[i + 2] == "g" and string[i + 3] == "h" and \
                        string[i + 4] == "t":
                    numlist.append(8)
                elif string[i] == "n" and string[i + 1] == "i" and string[i + 2] == "n" and string[i + 3] == "e":
                    numlist.append(9)
            except:
                continue
    firstNum = str(numlist[0])
    lastNum = str(numlist[-1])
    return int(firstNum + lastNum)
