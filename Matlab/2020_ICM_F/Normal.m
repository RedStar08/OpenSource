clc,clear
x=[-3:0.1:3];
y=normpdf(x,0,1);%正态分布函数。
figure;
plot(x,y);
xlabel('x')
ylabel('p')
title('N(0,1)')
ylim([0 0.43])
