# -*- coding: utf-8 -*-
"""
粒子群优化算法
粒子群算法求解函数最大值（最小值）
f(x)= x + 10*sin5x + 7*cos4x  根据具体情况 改函数
"""
import matplotlib
import numpy as np
import matplotlib.pyplot as plt
# 粒子（鸟）
class Particle:
    def __init__(self):
        self.p = 0  # 粒子当前位置
        self.v = 0  # 粒子当前速度
        self.pbest = 0  # 粒子历史最好位置


class PSO:
    def __init__(self, N=20, iter_N=200):
        self.w = 0.2  # 惯性因子
        self.c1 = 1  # 自我认知学习因子
        self.c2 = 2  # 社会认知学习因子
        self.gbest = 0  # 种群当前最好位置
        self.N = N  # 种群中粒子数量
        self.POP = []  # 种群
        self.iter_N = iter_N  # 迭代次数

    # 适应度值计算函数
    def fitness(self, x):
        return x + 10 * np.sin(5 * x) + 7 * np.cos(4 * x)

    # 找到全局最优解
    def g_best(self, pop):
        for bird in pop:
            if bird.fitness > self.fitness(self.gbest):
                self.gbest = bird.p

    # 初始化种群
    def initPopulation(self, pop, N):
        for i in range(N):
            bird = Particle()
            bird.p = np.random.uniform(-10, 10)#从一个均匀分布[low,high)中随机采样，注意定义域是左闭右开，即包含low，不包含high.
            bird.fitness = self.fitness(bird.p)
            bird.pbest = bird.fitness
            pop.append(bird)
        # 找到种群中的最优位置
        self.g_best(pop)

    # 更新速度和位置，和p_best
    def update(self, pop):
        for bird in pop:
            v = self.w * bird.v + self.c1 * np.random.random() * (
                        bird.pbest - bird.p) + self.c2 * np.random.random() * (self.gbest - bird.p)

            p = bird.p + v

            if -10 < p < 10:
                bird.p = p
                bird.v = v
                # 更新适应度
                bird.fitness = self.fitness(bird.p)

                # 是否需要更新本粒子历史最好位置
                if bird.fitness > self.fitness(bird.pbest):
                    bird.pbest = bird.p

    def implement(self):
        # 初始化种群
        self.initPopulation(self.POP, self.N)
        ###########开始1#############
        # 迭代
        # 更新速度和位置,p_best,更新种群中最好位置
        def func(x):
            return x + 10 * np.sin(5 * x) + 7 * np.cos(4 * x)
        
        x = np.linspace(-10, 10, 1000)
        y = func(x)
        
        # 迭代
        for i in range(self.iter_N):
            # 更新速度和位置
            self.update(self.POP)
            
            # 更新种群中最好位置
            self.g_best(self.POP)
            
            # 绘制动画
            plt.clf()
            scatter_x = np.array([ind.p for ind in pso.POP])
            scatter_y = np.array([ind.fitness for ind in pso.POP])
            
            scatter_x1 = pso.gbest
            scatter_y1 = pso.fitness(pso.gbest)
            
            plt.plot(x, y)
            plt.scatter(scatter_x, scatter_y, c='b')
            plt.scatter(scatter_x1, scatter_y1, c='r')
            plt.pause(0.01)



        ###########结束1#############


pso = PSO(N=20, iter_N=200)
pso.implement()

'''
#可以查看求解过程
for ind in pso.POP:
    print("x = ", ind.p, "f(x) = ", ind.fitness)
print("最优解 x = ", pso.gbest, "相应最大值 f(x) = ", pso.fitness(pso.gbest))
'''

if (pso.gbest>=7.850) and (pso.gbest<=7.860) :
    if (pso.fitness(pso.gbest)>=24.850) and (pso.fitness(pso.gbest)<=24.860) :
        print('success')