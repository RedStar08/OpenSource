// test.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//

#include "pch.h"
#include<bits/stdc++.h>
using namespace std;
#define INF 10000000
struct Point
{
	double x, y;
};
typedef Point Vector;
int Multi(Point pi, Point pj, Point p0)//叉积
{
	return ((pi.x - p0.x)*(pj.y - p0.y) - (pj.x - p0.x)*(pi.y - p0.y));
}
int distance(Point pi, Point pj)//距离
{
	return(sqrt((pi.x - pj.x)*(pi.x - pj.x) + (pi.y - pj.y)*(pi.y - pj.y)));
}
double round_off(double x, int n)//按位四舍五入
{
	double temp = x * pow(10, n);
	temp = floor(temp + 0.5);
	return (temp * pow(10, -n));
}
double get_K(Point A, Point B)//斜率
{
	if (A.x == B.x) {
		return INF;
	}
	else if (A.y == B.y) {
		return 0;
	}
	else {
		return  round_off(((B.y - A.y) / (B.x - A.x)), 8);
	}
}
void Graham_scan(vector<Point>  &allPoints, vector<Point>  &polygon, int n)//Graham扫描法求凸包
{
	if (allPoints.size() <= 3)
	{
		polygon = allPoints;
		return;
	}
	//找到最下角的点
	int a = 0, top = 1;
	int i, j;
	Point temp;
	for (i = 1; i < n; i++)
	{
		if (allPoints[a].y > allPoints[i].y)
			a = i;
		else if ((allPoints[a].y == allPoints[i].y) && (allPoints[a].x > allPoints[i].x))
			a = i;
	}
	//交换
	temp = allPoints[0];
	allPoints[0] = allPoints[a];
	allPoints[a] = temp;
	//其余点根据极角递增关系排列
	for (i = 1; i < n - 1; i++)
	{
		for (j = i + 1; j < n; j++)
		{
			if ((Multi(allPoints[j], allPoints[i], allPoints[0]) > 0) || ((Multi(allPoints[j], allPoints[i], allPoints[0]) == 0)
				&& (distance(allPoints[i], allPoints[0]) > distance(allPoints[j], allPoints[0]))))
			{
				temp = allPoints[i];
				allPoints[i] = allPoints[j];
				allPoints[j] = temp;
			}
		}
	}
	//连接凸包
	polygon.push_back(allPoints[0]);
	polygon.push_back(allPoints[1]);
	for (i = 2; i < n; i++)
	{
		while (Multi(allPoints[i], polygon[top], polygon[top - 1]) >= 0)
		{
			top--;
			polygon.pop_back();
			if (top == 0)
				break;
		}
		top++;
		polygon.push_back(allPoints[i]);
	}
}

int main()
{
	vector<Point>  allPoints, polygon;
	int i, j, n, num = 0, result = 0;
	fstream cin;
	cin.open("D:\\SourceCode\\4.in", ios::in);
	cin >> n;
	Point temp;
	vector<double> K;
	long sum_x = 0, sum_y = 0;
	double ave_x, ave_y;
	for (i = 0; i < n; i++)//输入点并求重心
	{
		cin >> temp.x;
		cin >> temp.y;
		allPoints.push_back(temp);
		sum_x += temp.x;
		sum_y += temp.y;
	}
	cin.close();
	clock_t start, end;
	start = clock();
	Graham_scan(allPoints, polygon, n);
	Point A, B, C, G;
	ave_x = (double)sum_x / n;
	ave_y = (double)sum_y / n;
	G.x = ave_x;
	G.y = ave_y;
	for (i = 0; i < polygon.size(); i++)
	{
		A = polygon.at(i);
		for (j = i + 1; j < polygon.size(); j++)
		{
			B = polygon.at(j);
			C.x = (A.x + B.x) / 2.0f;
			C.y = (A.y + B.y) / 2.0f;
			if (((float)(C.x - ave_x)*(B.x - A.x) + (float)(C.y - ave_y)*(B.y - A.y)) == 0.0)
			{
				if (C.x == ave_x && C.y == ave_y)
				{
					K.push_back(get_K(A, B));
				}
				else
				{
					K.push_back(get_K(C, G));
				}
				num++;
			}
		}
	}
	sort(K.begin(), K.end());
	K.erase(unique(K.begin(), K.end()), K.end());
	if (num == 1)//直线的时候自己就是一条
	{
		result++;
	}
	result += K.size();
	end = clock();
	cout << result << endl;
	cout << "Time used is : " << end - start << endl;
	return 0;
}