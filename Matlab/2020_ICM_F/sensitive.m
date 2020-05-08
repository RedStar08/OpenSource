%*************����-ʱ��ģ��***********************
%��EDPs-time��logistic����ģ��΢�ַ���
clc,clear
%*************�������***********************
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	
ri = 0.11            %EDPs�ı仯��
Nimax = 496493-11000*(ri*10)^2
t = 2020:0.1:2200	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;

%*************�������***********************
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	

ri = 0.12            %EDPs�ı仯��

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************�������***********************
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.13            %EDPs�ı仯��

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************�������***********************
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.14            %EDPs�ı仯��

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')
hold on;


%*************�������***********************
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.15            %EDPs�ı仯��

Nimax = 496493-20000*(ri*10)^3
t = 2020:0.1:2200	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
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

set(gca,'XLim',[2020 2120]);%X���������ʾ��Χ
set(gca,'YLim',[0 500000]);%Y���������ʾ��Χ