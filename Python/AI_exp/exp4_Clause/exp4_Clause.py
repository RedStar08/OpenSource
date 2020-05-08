''' 字句集消解实验 '''
''' 
->:> 
析取：∨ 
合取：∧ 
全称：∀ 
存在：# 
'''
# (∀x)(P(x)>((∀y)(P(y)>P(f(x,y)))∧﹁(∀y)(Q(x,y)>P(y))))
# 1.消去>蕴涵项 a>b变为﹁a∨b
def del_inlclue(orign):
    ind = 0
    flag = 0
    orignStack = []
    right_bracket = 0
    while (ind < len(orign)):
        orignStack.append(orign[ind])
        if ((ind + 1 < len(orign)) and (orign[ind + 1] == '>')):
            flag = 1
            if orign[ind].isalpha():  # 是字母
                orignStack.pop()
                orignStack.append('﹁')
                orignStack.append(orign[ind])
                orignStack.append('∨')
                ind = ind + 1
            if orign[ind] == ')':
                right_bracket = right_bracket + 1
                tempStack = []
                while (right_bracket != -1):
                    tempStack.append(orignStack[-1])
                    if orignStack[-1] == '(':
                        right_bracket = right_bracket - 1
                    orignStack.pop()
                right_bracket = right_bracket + 1
                tempStack.pop()  # 去掉'('
                orignStack.append('(﹁')
                tempStack.reverse()
                for i in tempStack:
                    orignStack.append(i)
                orignStack.append('∨')
                ind = ind + 1
        ind = ind + 1
    if flag == 1:
        a = ''
        for i in orignStack:
            a = a + i
        return a
    else:
        return orign

    # 2.处理否定连接词


def dec_neg_rand(orign):
    # 处理﹁(∀x)p(x) 变为(#x)﹁p(x)#####################################
    ind = 0
    flag = 0
    orignStack = []
    left_bracket = 0
    while (ind < len(orign)):
        orignStack.append(orign[ind])
        if orign[ind] == '﹁':
            if orign[ind + 1] == '(':
                if orign[ind + 2] == '∀' or orign[ind + 2] == '#':
                    flag = 1
                    ind = ind + 1
                    orignStack.pop()  # 去掉前面的﹁
                    orignStack.append(orign[ind])
                    if orign[ind + 1] == '∀':
                        orignStack.append('#')
                    else:
                        orignStack.append('∀')
                    orignStack.append(orign[ind + 2])  # 'x'
                    orignStack.append(orign[ind + 3])  # ')'
                    orignStack.append('﹁')
                    ind = ind + 3
        ind = ind + 1
    if flag == 1:
        a = ''
        for i in orignStack:
            a = a + i
        orign2 = a
    else:
        orign2 = orign
        # print('orign2',orign2)

    # 处理﹁(p∨q) 变为(﹁p∧﹁q)#####################################
    ind = 0
    flag = 0
    flag2 = 0  # 判断是否进入while left_bracket>=1:循环 ，若进入，出来后ind再减1
    orignStack = []
    left_bracket = 0
    while (ind < len(orign2)):
        orignStack.append(orign2[ind])
        if orign2[ind] == '﹁':
            if orign2[ind + 1] == '(':
                orignStack.pop()

                ind = ind + 2  # 此时为p
                left_bracket = left_bracket + 1
                orignStack.append('(﹁')
                while left_bracket >= 1:
                    flag2 = 1
                    orignStack.append(orign2[ind])
                    if orign2[ind] == '(':
                        left_bracket = left_bracket + 1
                    if orign2[ind] == ')':
                        left_bracket = left_bracket - 1
                    if left_bracket == 1 and orign2[ind + 1] == '∨' and orign2[ind + 2] != '∀' and orign2[
                        ind + 2] != '#':
                        flag = 1
                        orignStack.append('∧﹁')
                        ind = ind + 1
                    if left_bracket == 1 and orign2[ind + 1] == '∧' and orign2[ind + 2] != '∀' and orign2[
                        ind + 2] != '#':
                        flag = 1
                        orignStack.append('∨﹁')
                        ind = ind + 1
                    ind = ind + 1
        if flag2 == 1:
            ind = ind - 1
            flag2 = 0
        ind = ind + 1
    if flag == 1:
        a = ''
        for i in orignStack:
            a = a + i
        orign3 = a
    else:
        orign3 = orign2
        # print('orign3',orign3)

    # 处理﹁﹁p 变为p#####################################
    ind = 0
    flag = 0
    bflag = 0
    orignStack = []
    while (ind < len(orign3)):
        orignStack.append(orign3[ind])
        if orign3[ind] == '﹁':
            if orign3[ind + 1] == '﹁':
                flag = 1
                orignStack.pop()
                ind = ind + 1
        ind = ind + 1

    if flag == 1:
        a = ''
        for i in orignStack:
            a = a + i
        orign4 = a
    else:
        orign4 = orign3
        # print('orign4', orign4)
    # 处理﹁(﹁p) 变为p#####################################
    ind = 0
    flag = 0
    bflag = 0
    orignStack = []
    while (ind < len(orign4)):
        orignStack.append(orign4[ind])
        if orign4[ind] == '﹁':
            if orign4[ind + 1] == '(':
                left_bracket = 1
                if orign4[ind + 2] == '﹁':
                    flag = 1
                    orignStack.pop()
                    ind = ind + 2
                    while left_bracket >= 1:
                        orignStack.append(orign4[ind + 1])
                        if orign4[ind + 1] == '(':
                            left_bracket = left_bracket + 1
                        if orign4[ind + 1] == ')':
                            left_bracket = left_bracket - 1
                        if orign4[ind + 1] == '∨' or orign4[ind + 1] == '∧':
                            bflag = 1
                        ind = ind + 1

                    orignStack.pop()

        ind = ind + 1

    if flag == 1 and bflag == 0:
        a = ''
        for i in orignStack:
            a = a + i
        orign5 = a
    else:
        orign5 = orign4
        # print('orign5',orign5)
    return orign5


# 3.命题变量标准化，使后面y=w
def standard_var(orign):  # 对变量标准化,简化,不考虑多层嵌套

    flag = 1
    desOri = []
    des = ['w', 'k', 'j']
    j = 0
    orignStack = []
    left_bracket = 0
    ind = 0
    while flag != 0:
        flag = 0
        while (ind < len(orign)):
            orignStack.append(orign[ind])
            if orign[ind] == '∀' or orign[ind] == '#':
                x = orign[ind + 1]  # 保存x
                if orign[ind + 1] in desOri:
                    orignStack.append(des[j])
                    desOri.append(des[j])
                    j = j + 1
                    orignStack.append(')')
                    ind = ind + 3
                    if ind < len(orign):
                        if orign[ind].isalpha():  # (∀x)p(x,y)这种情况
                            orignStack.append(orign[ind])  # p
                            ind = ind + 1
                            if orign[ind] == '(':
                                left_bracket = left_bracket + 1
                                orignStack.append(orign[ind])
                                ind = ind + 1
                                while left_bracket > 0:
                                    if orign[ind] == ')':
                                        left_bracket = left_bracket - 1
                                    if orign[ind] == '(':
                                        left_bracket = left_bracket + 1
                                    if orign[ind] == x:
                                        flag = 1
                                        orignStack.append(des[j - 1])
                                    else:
                                        orignStack.append(orign[ind])
                                    ind = ind + 1
                                ind = ind - 1

                    if ind < len(orign):
                        if orign[ind] == '(':
                            left_bracket = left_bracket + 1
                            orignStack.append(orign[ind])
                            ind = ind + 1
                            while left_bracket > 0:
                                if orign[ind] == ')':
                                    left_bracket = left_bracket - 1
                                if orign[ind] == '(':
                                    left_bracket = left_bracket + 1
                                if orign[ind] == x:
                                    flag = 1
                                    orignStack.append(des[j - 1])
                                else:
                                    orignStack.append(orign[ind])
                                ind = ind + 1
                            ind = ind - 1

                else:
                    desOri.append(orign[ind + 1])
            ind = ind + 1

    a = ''
    for i in orignStack:
        a = a + i
    orign2 = a
    return orign2


# 4.消去存在量词（skolem化）
def del_exists(orign):
    ind = 0
    flag = 1
    orignStack = []
    x = ''
    y = ''
    # 第1种情况：前面有全称量词 (∀x)((#y)p(x,y))
    while flag != 0:  # 为了嵌套的情况出现
        flag = 0
        while (ind < len(orign)):
            orignStack.append(orign[ind])

            if orign[ind] == '(' and orign[ind + 1] == '∀' and orign[ind + 4] == '(':
                x = orign[ind + 2]
                orignStack.append(orign[ind + 1:ind + 5])
                ind = ind + 5  # 指向
                while orign[ind] != '#':
                    orignStack.append(orign[ind])
                    ind = ind + 1
                orignStack.pop()
                y = orign[ind + 1]  # 为y
                ind = ind + 2  # 指向p
                flag = 1

            ind = ind + 1

        if flag == 1:
            orignStack2 = []
            for i in orignStack:
                if i == y:
                    orignStack2.append('g(')
                    orignStack2.append(x)
                    orignStack2.append(')')
                else:
                    orignStack2.append(i)
    a = ''
    for i in orignStack2:
        a = a + i
    orign2 = a
    ind = 0
    flag = 1
    orignStack = []
    # 第2种情况：前面有全称量词 (#y)p(x,y)
    while flag != 0:  # 为了嵌套的情况出现
        flag = 0
        while (ind < len(orign2)):
            orignStack.append(orign2[ind])
            if orign2[ind] == '#':
                y = orign2[ind + 1]
                orignStack.pop()
                orignStack.pop()
                ind = ind + 2  # 指向')'
                flag = 1
            ind = ind + 1
        if flag == 1:
            orignStack2 = []
            for i in orignStack:
                if i == y:
                    orignStack2.append('g(')
                    orignStack2.append(x)
                    orignStack2.append(')')
                else:
                    orignStack2.append(i)

    a = ''
    for i in orignStack2:
        a = a + i
    orign2 = a
    return orign2

# 5.前束化
def convert_to_front(orign):  # 化为前束形
    ind = 0
    orignStack = []
    tempStack = []  # 存放全称量词
    while (ind < len(orign)):
        orignStack.append(orign[ind])
        if orign[ind] == '(' and orign[ind + 1] == '∀':
            orignStack.pop()
            tempStack.append(orign[ind:ind + 4])
            ind = ind + 3

        ind = ind + 1

    orignStack = tempStack + orignStack
    a = ''
    for i in orignStack:
        a = a + i
    orign2 = a
    return orign2

# (∀x)(∀y)(﹁P(x)∨((﹁P(y)∨P(f(x,y)))∧(Q(x,g(x))∧﹁P(g(x)))))
# 6.消去全称量词
def del_all(orign):
    ind = 0
    orignStack = []
    # (∀x)(P(x)>((∀y)(P(y)>P(f(x,y)))∧﹁(∀y)(Q(x,y)>P(y))))
    ###########开始1#############
    while(ind < len(orign)):
        orignStack.append(orign[ind])
        if orign[ind] == '(' and orign[ind+1] == '∀':
            orignStack.pop()
            ind = ind + 3
        ind = ind +1
    str = ''
    for i in orignStack:
        str = str + i
    orign_new = str
    return orign_new
    ###########结束1#############


import sys

codeIn = sys.stdin.read()
orign = codeIn
# orign = '(∀x)(p(x)>((∀y)(p(y)>p(f(x,y)))∧﹁(∀y)(Q(x,y)>p(y))))'
print('orign:', orign)
a = del_inlclue(orign)
print('1.去除蕴含后:', a)
a = dec_neg_rand(a)
print('2.处理否定连接词后:')
print(a)
a = standard_var(a)
print('3.变量命名标准化后:')
print(a)
a = del_exists(a)
print('4.消去存在量词后:')
print(a)
a = convert_to_front(a)
print('5.前束化后:')
print(a)
a = del_all(a)
print('6.消去全称量词后:')
print(a)

# 选做
# #定义化为合取范式、将公式转化为子句集合表示、更换变量名称的函数#
# 调用函数完成子句集的分解
