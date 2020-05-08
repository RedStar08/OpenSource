%*************羊群模型***********************
%解Number-time的logistic阻滞模型微分方程
clc,clear
syms p K s N(t) a
dN = diff(N)
N = dsolve(dN == p*N(t)*(1-N(t)/K)-a, N(0)== K)
N = simplify(N)
%画出羊群数量随时间变化的模型曲线
p = 0.01		%羊群繁殖因子(由1000只羊在3年内繁殖为5000只羊估计得出)
K = 12000		%初始的羊群数量
a = 27			%3只龙日进食羊的数量
t = 0:0.1:1096	%时间线
%龙进入羊群后羊群的数量变化曲线
N = (K.*p - tan((t.*(K.*p.*(4.*a - K.*p))^(1./2) - 2.*K.*atan((K.*p)./(K.*p.*(4.*a - K.*p))^(1./2)))./(2.*K)).*(K.*p.*(4.*a - K.*p))^(1./2))./(2.*p)
plot(t,N)
xlabel('time/day')
ylabel('sheep-number')
title('Number-time')
% Draw horizontal line y=7800
line([0,1200],[7800,7800])

%羊群logistics模型曲线
s = 1000		%初始体重10kg
r = 0.01		%增长因子(由初始10kg，一年后30-40kg求得)
K = 4900 + 5*((1000*r)^2)	%理论最大重量
t = 0:0.1:1200	%时间-年份
w_u = K*s.*exp(r*t)		%模型分子
w_d = K-s+s.*exp(r*t)	%模型分母
W = w_u ./ w_d			%体重
%画出重量随时间变化的模型曲线
plot(t,W)
hold on
legend('p = 0.006','p = 0.008','p = 0.01','p = 0.012','p = 0.014');
xlabel('time/day')
ylabel('N(t)/sheep number')
title('Number-time')