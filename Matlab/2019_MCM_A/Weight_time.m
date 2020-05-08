%*************体重-时间模型***********************
%解weight-time的logistic阻滞模型微分方程
clc,clear
syms r K s W(t)	%符号定义
dW = diff(W)	%体重的变化率
W = dsolve(dW == r*W(t)*(1-W(t)/K), W(0)== s)
W = simplify(W)	%简化结果

s = 10			%初始体重10kg
r = 1.25		%增长因子(由初始10kg，一年后30-40kg求得)
K = 12000		%理论最大重量
t = 0:0.1:15	%时间-年份
w_u = K*s.*exp(r*t)		%模型分子
w_d = K-s+s.*exp(r*t)	%模型分母
W = w_u ./ w_d			%体重
%画出重量随时间变化的模型曲线
plot(t,W)
xlabel('time/year')
ylabel('w(t)/kg')
title('Weight-time')