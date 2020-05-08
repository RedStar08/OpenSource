'''
遗传算法在TSP问题中的实现
'''
# -*- encoding: utf-8 -*-
import numpy as np
import pandas as pd

class TSP(object):
    citys = np.array([])  # 城市数组
    citys_name = np.array([])
    city_size = -1  # 标记城市数目

    pop_size = 50  # 种群大小
    c_rate = 0.7  # 交叉率
    m_rate = 0.05  # 突变率

    pop = np.array([])  # 种群数组
    fitness = np.array([])  # 适应度数组
    best_dist = -1  # 记录目前最优距离
    best_gen = []  # 记录目前最优旅行方案

    ga_num = 200  # 最大迭代次数

    def __init__(self, c_rate, m_rate, pop_size, ga_num):
        self.fitness = np.zeros(self.pop_size)
        self.c_rate = c_rate
        self.m_rate = m_rate
        self.pop_size = pop_size
        self.ga_num = ga_num

    def init(self):
        tsp = self
        tsp.load_Citys()  # 加载城市数据
        tsp.pop = tsp.creat_pop(tsp.pop_size)  # 创建种群
        tsp.fitness = tsp.get_fitness(tsp.pop)  # 计算初始种群适应度


    def load_Citys(self, file='/root/PycharmProjects/WechatServer/excuteFile/china.csv', delm=';'):
        # 中国34城市经纬度
        data = pd.read_csv(file, delimiter=delm, header=None).values
        self.citys = data[:, 1:] #第2、3列
        self.citys_name = data[:, 0] #第1列
        self.city_size = data.shape[0]

    def creat_pop(self, size):
        pop = []
        for i in range(size):
            gene = np.arange(self.citys.shape[0])  # 问题的解，即求解的路径 基因，种群中的个体：[0，...，city_size] [0,1,...33]
            #print('gene', gene)
            np.random.shuffle(gene)  # 打乱数组[0，...，city_size]
            pop.append(gene)  # 加入种群
        return np.array(pop)

    def get_fitness(self, pop):
        d = np.array([])  # 适应度记录数组
        for i in range(pop.shape[0]):
            gen = pop[i]  # 取其中一条基因（编码解，个体）
            dis = self.gen_distance(gen)  # 计算此基因优劣（距离长短）
            dis = self.best_dist / dis  # 当前最优距离除以当前pop[i]（个体）距离；越近适应度越高，最优适应度为1
            d = np.append(d, dis)  # 保存适应度pop[i]
        return d

    def gen_distance(self, gen):
        # 计算基因所代表的总旅行距离
        distance = 0.0
        for i in range(-1, len(self.citys) - 1):
            index1, index2 = gen[i], gen[i + 1]
            city1, city2 = self.citys[index1], self.citys[index2]
            distance += np.sqrt((city1[0] - city2[0]) ** 2 + (city1[1] - city2[1]) ** 2)
        return distance

    def evolution(self):
        # 主程序：迭代进化种群
        self.init()
        tsp = self
        for i in range(self.ga_num):
            best_f_index = np.argmax(tsp.fitness)#返回最适应度大值的索引。
            worst_f_index = np.argmin(tsp.fitness)
            local_best_gen = tsp.pop[best_f_index]
            local_best_dist = tsp.gen_distance(local_best_gen)
            if i == 0:
                tsp.best_gen = local_best_gen #初始化最优值
                tsp.best_dist = tsp.gen_distance(local_best_gen)#初始化最优个体基因

            if local_best_dist < tsp.best_dist:
                tsp.best_dist = local_best_dist  # 记录最优值
                tsp.best_gen = local_best_gen  # 记录最优个体基因
            ###########开始1#############
            # 计算种群适应度
            # 选择-复制
            tsp.pop = tsp.select_pop(tsp.pop)   #选择淘汰种群
            tsp.fitness = tsp.get_fitness(tsp.pop)  #计算种群适应度
            ###########结束1#############
            for j in range(self.pop_size):#交叉 、变异
                r = np.random.randint(0, self.pop_size - 1)
                ###########开始2#############
                # 交叉、变异种群中第j,r个体的基因
                if j != r:
                    tsp.pop[j] = tsp.cross(tsp.pop[j], tsp.pop[r])  #交叉种群中第j,r个体的基因
                    tsp.pop[j] = tsp.mutate(tsp.pop[j]) #突变种群中第j个体的基因
                ###########结束2#############

            print('gen:%d,best dist :%s' % (i, self.best_dist)) #可以查看求解过程
            if i==499:
                if self.best_dist<=200:
                    print('success')


    # 选择-复制种群，优胜劣汰，策略1：低于平均的要替换改变，进行交叉、变异，返回pop
    def select_pop(self, pop):
        best_f_index = np.argmax(self.fitness)
        av = np.median(self.fitness, axis=0)
        for i in range(self.pop_size):
            if i != best_f_index and self.fitness[i] < av:
                pi = self.cross(pop[best_f_index], pop[i]) #交叉
                pi = self.mutate(pi) #变异
                # d1 = self.distance(pi)
                # d2 = self.distance(pop[i])
                # if d1 < d2:
                pop[i, :] = pi[:]
        return pop

    '''
        def select_pop2(self, pop):
        # 选择种群，优胜劣汰，策略2：轮盘赌，适应度低的替换的概率大
        probility = self.fitness / self.fitness.sum()
        idx = np.random.choice(np.arange(self.pop_size), size=self.pop_size, replace=True, p=probility)
        n_pop = pop[idx, :]
        return n_pop
    '''


    # 交叉 对parent1插入parent2的部分片段，返回插入后的gene
    def cross(self, parent1, parent2):
        """交叉p1,p2的部分基因片段"""
        if np.random.rand() > self.c_rate:#通过本函数可以返回一个或一组服从“0~1”均匀分布的随机样本值。随机样本取值范围是[0,1)，不包括1。
            return parent1
        index1 = np.random.randint(0, self.city_size - 1)
        index2 = np.random.randint(index1, self.city_size - 1)
        tempGene = parent2[index1:index2]  # 交叉的基因片段
        newGene = []
        p1len = 0
        for g in parent1:
            if p1len == index1:
                newGene.extend(tempGene)  # 插入基因片段
            if g not in tempGene:
                newGene.append(g)
            p1len += 1
        newGene = np.array(newGene)

        if newGene.shape[0] != self.city_size:
            print('c error')
            return self.creat_pop(1)
            # return parent1
        return newGene

    #变异
    def mutate(self, gene):
        """突变"""
        if np.random.rand() > self.m_rate:
            return gene
        index1 = np.random.randint(0, self.city_size - 1)
        index2 = np.random.randint(index1, self.city_size - 1)
        newGene = self.reverse_gen(gene, index1, index2)
        if newGene.shape[0] != self.city_size:
            print('m error')
            return self.creat_pop(1)
        return newGene

    def reverse_gen(self, gen, i, j):
        # 函数：翻转基因中i到j之间的基因片段
        if i >= j:
            return gen
        if j > self.city_size - 1:
            return gen
        parent1 = np.copy(gen)
        tempGene = parent1[i:j]
        newGene = []
        p1len = 0
        for g in parent1:
            if p1len == i:
                newGene.extend(tempGene[::-1])  # 插入基因片段
            if g not in tempGene:
                newGene.append(g)
            p1len += 1
        return np.array(newGene)


def main():
    tsp = TSP(0.5, 0.1, 100, 500)
    tsp.evolution()

if __name__ == '__main__':
    main()