'''
编程实现简单恐龙识别系统的知识表示
'''
# python 使用类创建结构体
class MyRules(object):
    class Struct(object):
        def __init__(self, count, pre, back,mark):
            self.count = count
            self.pre = pre
            self.back = back
            self.mark=mark

    def make_rule(self, count, pre, back,mark):
        return self.Struct(count, pre, back,mark)
myrule = MyRules()

#规则库
rules=[
    myrule.make_rule(3,'晚三叠纪到早侏罗纪&杂食-素食&中等体型','原蜥脚类',0),
    myrule.make_rule(3,'原蜥脚类&巨型&第一种','板龙',0),
    myrule.make_rule(2,'原蜥脚类&侏罗纪早期','安琪龙',0),
    myrule.make_rule(3,'晚三叠世至白垩纪&大型&素食','蜥脚形类',0),
    myrule.make_rule(4,'头小&脖子长&尾巴长&牙齿成小匙状','蜥脚形类',0),
    myrule.make_rule(1,'蜥脚形类','蜥脚类',0),
    myrule.make_rule(1,'原蜥脚类','蜥脚类',0),
    myrule.make_rule(4,'蜥脚类&产于我国四川、甘肃&晚侏罗纪&由19节颈椎组成的脖子长度约等于体长的一半','马门溪龙',0),
    myrule.make_rule(2,'蜥脚类&体形最大的陆地动物','易碎双腔龙',0),
    myrule.make_rule(6,'晚三叠世至白垩纪&肉食性&两足行走&趾端长有锐利的爪子&头部很发达&最聪明','兽脚类',0),
    myrule.make_rule(1,'嘴里长着匕首或小刀一样的利齿','兽脚类',0),
    myrule.make_rule(2,'兽脚类&著名代表','暴龙',0),
    myrule.make_rule(2,'鸟臀类&化石最多的一个类群','鸟脚类',0),
    myrule.make_rule(1,'化石最多的一个类群','鸟脚类',0),
    myrule.make_rule(5,'两足行走&下颌骨有单独的前齿骨&牙齿仅生长在颊部&上颌牙齿齿冠向内弯曲&下颌牙齿齿冠向外弯曲','鸟脚类',0),
    myrule.make_rule(5,'四足行走&下颌骨有单独的前齿骨&牙齿仅生长在颊部&上颌牙齿齿冠向内弯曲&下颌牙齿齿冠向外弯曲','鸟脚类',0),
    myrule.make_rule(2,'晚三叠纪至白垩纪&全部素食','鸟脚类',0),
    myrule.make_rule(3,'四足行走&背部具有直立的骨板&尾部有骨质刺棒两对或多对','剑龙类',0),
    myrule.make_rule(3,'侏罗纪到早白垩纪&全部素食&最先灭亡的一个大类','剑龙类',0),
    myrule.make_rule(2,'剑龙类&居住在平原上','剑龙',0),
    myrule.make_rule(2,'剑龙类&被发现于坦桑尼亚','肯氏龙',0),
    myrule.make_rule(4,'体形低矮粗壮&全身披有骨质甲板&素食&白垩纪早期','甲龙类',0),
    myrule.make_rule(2,'四足行走&素食','角龙类',0),
    myrule.make_rule(3,'头骨后部扩大成颈盾&白垩纪晚期&祖先是鹦鹉嘴龙','角龙类',0),
    myrule.make_rule(6,'头骨肿厚&颥孔封闭&骨盘中耻骨被坐骨排挤&不参与组成腰带&生活在白垩纪&全部素食','肿头龙类',0),
]
cat=24#规则库长度
length=0#事实库长度
f=[]#事实库

def check():#查看规则库
    print('规则库如下：')
    j=1
    for i in rules:
        print(j,'.由',i.pre,'可得',i.back)
        j=j+1

def inputRule():#添加规则   #ask1
    global cat,rules
###########开始1#############
    cat+=1
    rules.append(myrule.make_rule(codeInList[0], codeInList[1], codeInList[2], codeInList[3]))

###########结束1#############
    check()

def deleteRule():#删除规则  #ask2
###########开始2#############
    global cat,rules
    rules.pop(int(codeInList[4]) - 1)
    cat-=1

###########结束2#############
    check()

import sys
'''
添加规则输入(参见规则库形式)：事实条数 事实（事实之间用&隔开） 结果 是否使用   例如输入：3 晚三叠纪到早侏罗纪&杂食-素食&中等体型 原蜥脚类 0
删除规则输入：删除的规则索引号（第一条为1） 例如删除第一条输入：1
'''
#输入的运行参数前4位为添加规则输入、第5位为删除规则输入
codeIn=sys.stdin.read()
codeInList=codeIn.split()

check()
print('添加规则：',codeInList[1],'可得',codeInList[2])
print('添加后：')
inputRule()
print('删除规则：',codeInList[4])
print('删除后：')
deleteRule()