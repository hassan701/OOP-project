import re

code = """<?php
class MyClass {
    function abc(){ $i=5;
    $z=$i*2;
    echo "One '$=".$z;}
}
?>""".splitlines()

token = []
colnum =0
lineNum = 0


def valueSet(code: str,colnum):
    value = code.replace("=", " = ")
    value = value.replace("*", " * ")
    valueSplit = value.split(" ")

    for val in valueSplit:

        if val == "=":
            colnum+=1
            token.append([lineNum,colnum, "assign"])

        elif val == "*":
            colnum += 1
            token.append([lineNum,colnum, "multiplication"])

        else:

            if val.isdigit():
                colnum += 1
                token.append([lineNum,colnum, "Number", val])

            else:
                colnum += 1
                token.append([lineNum,colnum, "String", val])


def functionName(code):
    emptystr = ""
    regText = re.search(emptystr, code)

    placement = regText.span()

    return code[placement[0]: placement[1] - 1]


def brackets(code,colnum):
    if "(" in code:
        functionsName = functionName(code)
        colnum+=1
        token.append([lineNum,colnum, "type-identifier", functionsName])
        colnum+=1
        token.append([lineNum,colnum, "Opening Brackets"])

    if ")" in code:
        colnum+=1
        token.append([lineNum,colnum, "Closing Brackets"])

    if "{" in code:
        colnum+=1
        token.append([lineNum,colnum, "Opening Curly Brackets"])

    if "}" in code:
        colnum+=1
        token.append([lineNum,colnum, "Closing Curly Brackets"])


for i in code:

    newcode = i.split(" ")
    print(newcode)
    container = ""
    for t in newcode:
        if t == "<?php":
            colnum+= 1
            token.append([lineNum,colnum, "php-opening-tag"])

        if t == "?>":
            colnum+= 1
            token.append([lineNum,colnum, "php-closing-tag"])

        if "." in t:
            colnum += 1
            token.append([lineNum,colnum, "concatenate"])

            concatenate = t.index(".")
            concatenatecode = t[concatenate + 1:]
            valueSet(concatenatecode,colnum)
        if t != '' and t[0] == '"':
            container = container + t

        if ";" in t:
            colnum += 1
            token.append([lineNum,colnum, "semicolon"])
        elif t == "class":
            colnum += 1
            token.append([lineNum,colnum, "class"])

        elif t == "function":
            colnum += 1
            token.append([lineNum,colnum, "function"])


        elif t == "echo":
            colnum += 1
            token.append([lineNum,colnum, "print-output"])

        elif "=" in t:
            valueSet(t)

            if ";" in t:
                colnum += 1
                token.append([lineNum,colnum,"semicolon"])

        elif ";" in t:
            colnum+= 1
            token.append([lineNum,colnum, "semicolon"])

        elif re.match("[a-zA-Z]+", t) and "(" not in t:
            colnum+= 1
            token.append([lineNum,colnum, "type-identifier", t])

        else:
            brackets(t,colnum)
    colnum = 0;
    lineNum += 1


for i in range(len(token)):
    print(token[i])

