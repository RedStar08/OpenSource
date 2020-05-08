%Solve the differential equation of the 1ogistic  retardation model of EDPs-time
clc,clear
%*************The Maldives***********************
syms ri Ni(t) Nimax	s
dNi = diff(Ni)	
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2020)== s)
Ni = simplify(Ni)	

M = diff(Ni)
M = simplify(M)

s = 6919	
Nimax = 496493
ri = 0.11            
t = 2020:0.1:2200	
Ni = (Nimax.*s.*exp(ri.*(t - 2020)))./(Nimax - s + s.*exp(ri.*(t - 2020)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2020)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2020))).^2

plot(t,M)
hold on;

all=M;

%*************Kiribati***********************
syms ri Ni(t) Nimax	s
dNi = diff(Ni)	
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2039)== s)
Ni = simplify(Ni)	

M = diff(Ni)
M = simplify(M)

s = 2178  
Nimax = 243118  
ri = 0.13            
t = 2020:0.1:2200	
Ni = (Nimax.*s.*exp(ri.*(t - 2039)))./(Nimax - s + s.*exp(ri.*(t - 2039)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2039)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2039))).^2

plot(t,M)
hold on;
all=all+M;

%*************The Marshall Islands***********************
syms ri Ni(t) Nimax	s
dNi = diff(Ni)	
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2080)== s)
Ni = simplify(Ni)	

M = diff(Ni)
M = simplify(M)

s = 1118  
Nimax = 161933  
ri = 0.10          
t = 2020:0.1:2200	
Ni = (Nimax.*s.*exp(ri.*(t - 2080)))./(Nimax - s + s.*exp(ri.*(t - 2080)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2080)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2080))).^2


plot(t,M)

hold on;
all=all+M;
%*************Tuvalu***********************
syms ri Ni(t) Nimax	s
dNi = diff(Ni)	
Ni = dsolve(dNi == ri*Ni(t)*(1 - Ni(t)/Nimax), Ni(2054)== s)
Ni = simplify(Ni)	

M = diff(Ni)
M = simplify(M)

s = 72  
Nimax = 47579  
ri = 0.11           
t = 2020:0.1:2200	
Ni = (Nimax.*s.*exp(ri.*(t - 2054)))./(Nimax - s + s.*exp(ri.*(t - 2054)))
M = (Nimax.*ri.*s.*exp(ri.*(t - 2054)).*(Nimax - s))./(Nimax - s + s.*exp(ri.*(t - 2054))).^2

plot(t,M)

hold on;
all=all+M;
plot(t,M)
hold on;

%****************************************
h = legend('Total of four countries','Maldives','Kiribati','Marshall Islands','Tuvalu');
set(h,'FontName','Times New Roman','FontSize',13,'FontWeight','normal')
title('EDPs-rate')
xlabel('time/year')
ylabel('Nm(t)')
ylim([0 510000])
set(gca,'XLim',[2020 2200]);
set(gca,'YLim',[0 20000]);

