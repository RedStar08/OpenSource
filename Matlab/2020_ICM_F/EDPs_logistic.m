%*************数量-时间模型***********************
%解EDPs-time的logistic阻滞模型微分方程
clc,clear
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.11            %EDPs的变化率
t = 2020:0.1:2120	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')

plot(t,M)
title('EDPs-rate')
xlabel('time/year')
ylabel('NEDPs(t)')
%ylim([0 510000])



