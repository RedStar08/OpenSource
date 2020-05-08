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
%*************身长-体重模型***********************
s = 10			%初始体重10kg
r = 1.25		%增长因子(由初始10kg，一年后30-40kg求得)
K = 12000		%理论最大重量
t = 0:0.1:15	%时间-年份
w_u = K*s.*exp(r*t)		%模型分子
w_d = K-s+s.*exp(r*t)	%模型分母
W = w_u ./ w_d			%体重
b = 15
L= power((W/b),1/4)		%身长与体重的关系模型
%画出身长随时间变化的模型曲线
plot(t,L)
xlabel('time/year')
ylabel('L(t)/m')
title('Length-time')
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
%*************O奖模型***********************
p0 = 10; % Initial weight value
k = 10694; % Theoretical upper limit
r = 20; % Growth rate
a = 0.064; % Environmental impact factor
t = 0:0.1:15 % timeline
p_u = a*k*p0* exp(a*r*t); % weight fraction molecule
p_l = a*k+p0*(exp(a*r*t) -1); % Weight fraction denominator
p = p_u./p_l/a; % Weight
% Draw a curve of body weight over time
plot(t,p)
xlabel('time/year')
ylabel('w(t)/kg')
title('weight -time ')

%Draw the image of body length changes over time
l0=0.8;%Initial value of body length
l=l0*(p/10).^(1/4);%Relationship between body length and body weight
%Draw an image of body length over time
plot(t,l)
hold on
xlabel('t/year')
ylabel('length/m')
title('length-t')

function [t,x] = function3(a,r,k, tspan)
% Dragon Sheep Habitat Model Code
x0 = k;
s = @(t,x) r*x.*(1-x/k) - a;
% Numerical Solution[t x] = ode45(s, tspan , x0);
% Draw horizontal line y=0
plot(t,x)
y=zeros(1,length(x));
hold on
plot(t,y)
hold off