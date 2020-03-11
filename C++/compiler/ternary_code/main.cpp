#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>
using namespace std;
//a=b+(c-d)*e+f/g*(h-i+j/(k+l*m-n))
char w;
int j = 1;
struct TOKEN
{
	char t;
	int i;
} ;
struct TOKEN word, sem[10];
int i_sem;
struct QT
{
	char w;
	struct TOKEN word1;
	struct TOKEN word2;
	struct TOKEN temp;
};
char exp[50];
int i = 0;
struct QT qt[30];
int q = 0;
int D();
int E();
int T();
int F();
void next();
void newt();
void quat(char);
int main()
{
	printf("please input your expression: ");
	scanf(" %s", exp);
	next();
	D();
	if (w == '\0')
	{
		printf("\n");
		for (i = 0; i < q; i++)
		{
			printf(" (%d) ", i + 1);
			if (qt[i].w != '=') {
				printf(" ( %c", qt[i].w);
				if (qt[i].word1.t != 't')
					printf(" , %c", qt[i].word1.t);
				else
					printf(" , %c%d", qt[i].word1.t, qt[i].word1.i);
				if (qt[i].word2.t != 't')
					printf(" , %c", qt[i].word2.t);
				else
					printf(" , %c%d", qt[i].word2.t, qt[i].word2.i);
				printf(" , %c%d )\n", qt[i].temp.t, qt[i].temp.i);
			}
			else
			{
				printf(" ( %c", qt[i].w);
				printf(" , %c%d ", qt[i - 1].temp.t, qt[i - 1].temp.i);
				printf(" , _");
				printf(" , %c)", qt[i].word1.t);
			}
		}
		//getch();
	}
	else printf("err");
	return 0;
}
int D()
{
	char w0;
	E();
	while ( w == '=')
	{
		w0 = w;
		next();
		E();
		quat(w0);
	}
	return 1;
}
int E()
{
	char w1;
	T();
	while ( w == '+' || w == '-')
	{
		w1 = w;
		next();
		T();
		quat(w1);
	}
	return 1;
}
int T()
{
	char w2;
	F();
	while ( w == '*' || w == '/')
	{
		w2 = w;
		next();
		F();
		quat(w2);
	}
	return 1;
}
int F()
{
	if ( w == '(')
	{
		next();
		E();
		if ( w != ')')
		{
			printf("err");
			return 0;
		}
	}
	else if ((w >= 'a' && w <= 'z') || (w >= '0' && w <= '9'))
	{
		word.t = w; word.i = 0 ;
		sem[++i_sem] = word;
	}
	else
	{
		printf("err");
		return 0;
	}
	next();
	return 1;
}
void next()
{
	w = exp[i];
	i++;
}
void newt()
{
	word.t = 't';
	word.i = j++;
}
void quat(char ww)
{
	newt();
	qt[q].w = ww;
	qt[q].word1 = sem[i_sem - 1];
	qt[q].word2 = sem[i_sem];
	qt[q].temp = word;
	i_sem--;
	i_sem--;
	sem[++i_sem] = word;
	q++;
}
