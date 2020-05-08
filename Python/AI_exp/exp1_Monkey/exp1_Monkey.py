'''
猴子摘香蕉问题的Python编程实现
'''
#全局变量i
i=0
def Monkey_go_box(x,y):
    global i
    i=i+1
    print('step:',i,'monkey从',x,'走到'+y)

def Monkey_move_box(x,y):
    global i
    i = i + 1
    print('step:', i, 'monkey把箱子从', x, '运到' + y)

def Monkey_on_box():
    global i
    i = i + 1
    print('step:', i, 'monkey爬上箱子')

def Monkey_get_banana():
    global i
    i = i + 1
    print('step:', i, 'monkey摘到香蕉')

import sys
print('请用‘a’、‘b’、‘c’表示猴子香蕉箱子的位置')
#读取输入的运行参数
codeIn=sys.stdin.read()
codeInList=codeIn.split()
#将运行参数赋值给monkey、banana、box
monkey=codeInList[0]
banana=codeInList[1]
box=codeInList[2]
print('操作步骤如下：')
#请用最少步骤完成猴子摘香蕉任务
###########开始#############
Monkey_go_box(monkey,box)
Monkey_move_box(box,banana)
Monkey_on_box()
Monkey_get_banana()
###########结束#############