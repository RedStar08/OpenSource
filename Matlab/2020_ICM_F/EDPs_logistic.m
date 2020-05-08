%*************����-ʱ��ģ��***********************
%��EDPs-time��logistic����ģ��΢�ַ���
clc,clear
syms ri Ni(t) Nimax	s%���Ŷ���
dNi = diff(Ni)	%EDPs�ı仯��
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	%�򻯽��
%ָ����������
M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.11            %EDPs�ı仯��
t = 2020:0.1:2120	%ʱ��-���
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2
%����������ʱ��仯��ģ������
plot(t,Ni)
title('EDPs-time')
xlabel('time/year')
ylabel('Nm(t)')

plot(t,M)
title('EDPs-rate')
xlabel('time/year')
ylabel('NEDPs(t)')
%ylim([0 510000])



