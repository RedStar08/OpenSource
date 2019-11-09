// 你的名字.cpp : 此文件包含 "main" 函数。程序执行将在此处开始并结束。
//

#include "pch.h"
#include<bits/stdc++.h>
using namespace std;

int main()
{
	vector<string> catname, value;//resrve string
	vector<int> valuenum;//resrve name value
	int n, m, i, j;
	cin >> n;
	cin >> m;
	char enter = cin.get();
	//input catname and value than transform to lower
	for (i = 0; i < n; i++)//get cat name strings
	{
		string temp;
		getline(cin, temp);
		catname.push_back(temp);
		transform(catname[i].begin(), catname[i].end(), catname[i].begin(), ::tolower);
	}
	for (j = 0; j < m; j++)//get name value strings
	{
		string temp;
		getline(cin, temp);
		value.push_back(temp);
		transform(value[j].begin(), value[j].end(), value[j].begin(), ::tolower);
	}
	//get the name value
	for (i = 0; i < n; i++)
	{
		int num = 0;
		for (j = 0; j < m; j++)
		{
			int location = 0;
			auto pos = catname[i].find(value[j][location]);//find the first char			
			while (pos != string::npos)
			{
				location++; 
				if (location == value[j].size())//continue find in the rest char
					num++;
				pos = catname[i].find(value[j][location], pos + 1);//update the position
			}
		}
		valuenum.push_back(num);
	}
	//display name value
	/*vector<int>::iterator it;
	for (it = valuenum.begin(); it != valuenum.end(); it++)
		cout << (*it) << endl;*/
	for (i = 0; i < n; i++)
		cout << valuenum[i] << endl;
	return 0;
}
/*test data
5 3
Bessie
Jonathan
Montgomery
Alicia
Angola
Se
nGo
oNt
*/

