%*************数量-时间模型***********************
%解EDPs-time的logistic阻滞模型微分方程
clc,clear
%*************马尔代夫***********************
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	
ri = 0.11            %EDPs的变化率
Nimax = 496493-11000*(ri*10)^2
t = 2020:0.1:2200	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;

%*************马尔代夫***********************
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	

ri = 0.12            %EDPs的变化率

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************马尔代夫***********************
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.13            %EDPs的变化率

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************马尔代夫***********************
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.14            %EDPs的变化率

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************马尔代夫***********************
syms ri Ni(t) Nimax	s%符号定义
dNi = diff(Ni)	%EDPs的变化率
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%简化结果
%指导撤离曲线
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.15            %EDPs的变化率

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%时间-年份
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%画出重量随时间变化的模型曲线
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;



hold on;
h = legend('ri=0.11','ri=0.12','ri=0.13','ri=0.14','ri=0.15')
set(h,'FontName','Times New Roman','FontSize',13,'FontWeight','normal','Location', 'northwest')
title('EDPs-time','FontSize',13)
xlabel('time/year','FontSize',13)
ylabel('NEDPs(t)','FontSize',13)

set(gca,'XLim',[2020 2120]);%X轴的数据显示范围
set(gca,'YLim',[0 500000]);%Y轴的数据显示范围