'''
BP神经网络实验
'''
import numpy as np


# sigmoid函数
def logistic(x):
    return 1 / (1 + np.exp(-x))


# sigmoid函数的导数
def logistic_derivative(x):
    return logistic(x) * (1 - logistic(x))


class NeuralNetwork:
    def __init__(self, layers):
        """
        神经网络算法构造函数
        :param layers: 神经元层数
        :param activation: 使用的函数（默认tanh函数）
        :return:none
        """

        self.activation = logistic
        self.activation_deriv = logistic_derivative

        # 权重列表
        self.weights = []
        # 初始化权重（随机）
        for i in range(1, len(layers) - 1):
            self.weights.append((2 * np.random.random((layers[i - 1] + 1, layers[i] + 1)) - 1) * 0.25)
            self.weights.append((2 * np.random.random((layers[i] + 1, layers[i + 1])) - 1) * 0.25)

    def fit(self, X, y, learning_rate=0.2, epochs=10000):
        """
        训练神经网络
        :param X: 数据集（通常是二维）
        :param y: 分类标记
        :param learning_rate: 学习率（默认0.2）
        :param epochs: 训练次数（最大循环次数，默认10000）
        :return: none
        """
        # 确保数据集是二维的
        X = np.atleast_2d(X)#atleast_xd 支持将输入数据直接视为 x维

        temp = np.ones([X.shape[0], X.shape[1] + 1])
        temp[:, 0: -1] = X
        X = temp
        y = np.array(y)

        for k in range(epochs):
            # 随机抽取X的一行
            i = np.random.randint(X.shape[0])
            # 用随机抽取的这一组数据对神经网络更新
            a = [X[i]]
            # 正向更新
            for l in range(len(self.weights)):
                a.append(self.activation(np.dot(a[l], self.weights[l])))#np.dot()做矩阵乘法,
            error = y[i] - a[-1]
            deltas = [error * self.activation_deriv(a[-1])]

            # 反向更新
            ###########开始1#############
            for l in range(len(a) - 2, 0, -1):
                deltas.append(deltas[-1].dot(self.weights[l].T) * self.activation_deriv(a[l]))  # 隐含层误差
            deltas.reverse()  # 对误差进行顺序颠倒
            for i in range(len(self.weights)):
                layer = np.atleast_2d(a[i])
                delta = np.atleast_2d(deltas[i])
                self.weights[i] += learning_rate * layer.T.dot(delta)

            ###########结束1#############

    def predict(self, x):
        x = np.array(x)
        temp = np.ones(x.shape[0] + 1)
        temp[0:-1] = x
        a = temp
        for l in range(0, len(self.weights)):
            a = self.activation(np.dot(a, self.weights[l]))
        return a

if __name__ == '__main__':
    # X:                  Y
    # 0 0                 0
    # 0 1                 1
    # 1 0                 1
    # 1 1                 0
    nn = NeuralNetwork([2, 2, 1])
    temp = [[0, 0], [0, 1], [1, 0], [1, 1]]
    X = np.array(temp)
    y = np.array([0, 1, 1, 0])
    nn.fit(X, y)

    flag=0
    for i in temp:
        print(i, nn.predict(i))  # 可以测试输出预测结果
        if (nn.predict(i) >= 0.40) and (nn.predict(i) <= 0.60):
            flag = flag + 1
    if flag == 4:
        print('success')