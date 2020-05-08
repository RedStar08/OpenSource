clc,clear;
syms p11 p12 p13 p14 p21 p22 p23 p24 p31 p32 p33 p34 p41 p42 p43 p44
p11 = 0.25; p12 = 0.35; p13 = 0.35; p14 = 0.05;
p21 = 0.15; p22 = 0.35; p23 = 0.40; p24 = 0.10;
p31 = 0.10; p32 = 0.35; p33 = 0.40; p34 = 0.15;
p41 = 0.05; p42 = 0.10; p43 = 0.15; p44 = 0.70;

p = [ p11, p12, p13, p14;
      p21, p22, p23, p24;
      p31, p32, p33, p34;
      p41, p42, p43, p44 ]

p0 = [0.18 0.43 0.25 0.14]

Y=zeros(5,4);
Y(1,:) = p0;
Y(2,:) = p0*p^1;
Y(3,:) = p0*p^2;
Y(4,:) = p0*p^3;
Y(5,:) = p0*p^4;

figure
X=1:5;
h=bar(X,Y);
% 设置条形图颜色
set(h(1),'FaceColor',[1,1,0])
set(h(2),'FaceColor',[0,0.9,0.3])
set(h(3),'FaceColor',[1,0,0.3])
set(h(4),'FaceColor',[0.5,0.5,0.5])

ylim([0,0.6]);
title('Maldives Cultural Development')
ylabel('States Probability');
xlabel('Times/5year');
legend('S1','S2','S3','S4', 'FontSize',12,'FontName','Times New Roman', 'Location', 'northeast'); %修改图例
set(gca,'xtick',1:5);
set(gca,'XTickLabel',{'1','2','3','4','5'},'FontSize',12,'FontName','Times New Roman'); %修改横坐标名称、字体
Y_1=roundn(Y,-2);

for i = 1:length(X)
    text(X(i)-0.25,Y_1(i,1),num2str(Y_1(i,1)),'HorizontalAlignment','right','VerticalAlignment','bottom','FontSize',12,'FontName','Times New Roman');
    text(X(i),Y_1(i,2),num2str(Y_1(i,2)),'HorizontalAlignment','right','VerticalAlignment','bottom','FontSize',12,'FontName','Times New Roman');
    text(X(i)+0.25,Y_1(i,3),num2str(Y_1(i,3)),'HorizontalAlignment','right','VerticalAlignment','bottom','FontSize',12,'FontName','Times New Roman');
    text(X(i)+0.5,Y_1(i,4),num2str(Y_1(i,4)),'HorizontalAlignment','right','VerticalAlignment','bottom','FontSize',12,'FontName','Times New Roman');
end

       