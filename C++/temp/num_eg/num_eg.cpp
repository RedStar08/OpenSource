// num_eg.cpp
//

#include "pch.h"
#include <iostream>
#include <math.h>
using namespace std;
int ave(int a[]);//平均数
int G(int x, int A, int mu);//函数G（x）
void cou(int data[30], int count[20]);
int main()
{
	int data[30] = {1,1,1,1,1,5,10,15,20,25,30,35,40,45,50,55,60,65,70,75,80,85,90,95,100,50,50,50,50,50};
	int count[20];
	int mu = 0;
	int s = 0;
	mu = ave(data);
	cou(data, count);
	static int A = 0;
	for (int i = 0; i < 20; i++) {
		if (count[i] > A)
			A = count[i];
	}
	for (int i = 0; i < 20; i++) {
		s += pow((count[i] - G(5 * (i + 1), A, mu)), 2);
	}
	cout << "The average is: " << mu << endl;
	for (int i = 0; i < 20; i++) {
		cout << "Value: ("<< 5*i<<", "<<5*(i+1)<<" ] number is: " << count[i] << endl;
	}
	cout << "The s is: " << s << endl;
	return 0;
}

int ave(int a[])
{
	int i = 0;
	int sum = 0;
	while (a[i] > 0&& a[i] <= 100) {
		sum += a[i];
		i++;
	}
	return sum / i;
}

int G(int x, int A, int mu)
{
	return A * exp(-pow((x - mu), 2));
}

void cou(int data[30], int count[20]) //统计区间频数
{
	for (int i = 0; i < 20; i++) { //清零
		count[i] = 0;
	}
	int i = 0;
	while (data[i] > 0 && data[i] <= 100) { //统计
		if (data[i] == 1) //属于区间（0,5]计数
			count[0]++;
		else
			count[(data[i] / 5) - 1]++;//属于区间( 5i, 5(i+1) ]计数
		i++;
	}
}
