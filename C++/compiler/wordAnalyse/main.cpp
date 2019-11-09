#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

#define MAX 33//这后面不要加分号
char ch=' ';//为啥是空格
static int line = 1,row = 0;
int numCount = 0,idCount = 0;

string k[] = { "auto","break","case","catch","char","class","const","continue","default","delete","do","double","else","enum","float","for","if","int","long","new","private","protected","public","register","return","short","static","struct","switch","this","void","while","then" };
string s[] = { ";","(",")","[","]",",",".","{","}","+","-","*","/","<","<=","==",">",">=" };
string id[100];
string ci[100];

int IsKey(string s)
{
    int i;
    for(i=0; i<MAX; i++)
    {
        if(k[i].compare(s) == 0)//k[i].comapare(s) == 0即为相等
            return 1;
    }
    return 0;
}

int IsLetter(char ch)
{
    if((ch >= 'a' && ch <= 'z')||(ch >='A' && ch <= 'Z'))
    {
        return 1;
    }
    else
        return 0;
}

int IsNumber(char ch)
{
    if(ch >= '0' && ch <= '9')
        return 1;
    else
        return 0;
}

int InsertId(string s)
{
    int i;
    for(i = 0; i < idCount; i++)
    {
        if(id[i].compare(s) == 0);
        return i;
    }
    id[i]=s;
    idCount++;
    return idCount;
}

int InsertNum(string s)
{
    int i;
    for(i = 0; i < numCount; i++)
    {
        if(ci[i].compare(s) == 0);
        return i;
    }
    ci[i]=s;
    numCount++;
    return numCount;
}

void Error(string s)
{
    cout << s << "\t\tError" << "\t\tError" << "\t\t(" << line << "," << row << ")" << endl;
}

//1关键字，2分解符，3算术运算，4.关系运算，5.无符号数，6.标识符
void analyse(FILE* fp)
{
    string strToken = "";
    while ((ch = fgetc(fp)) != EOF)
    {
        strToken = "";
        if(ch==' '||ch=='\t'||ch=='\n')
        {
            if(ch=='\n')
            {
                line++;
                row=0;
            }
        }
        else if(IsLetter(ch)==1)
        {
            while(IsLetter(ch)==1||IsNumber(ch)==1)
            {
                strToken=strToken+ch;
                ch=fgetc(fp);
            }
            fseek(fp,-1L,SEEK_CUR);
            if(IsKey(strToken)==1)
            {
                row++;
                cout << strToken << "\t\t(1," << strToken << ")  " << "\t关键字" << "\t\t(" << line << "," << row << ")" << endl;
            }
            else
            {
                row++;
                InsertId(strToken);
                cout << strToken << "\t\t(6," << strToken << ")" << "\t\t标识符" << "\t\t(" << line << "," << row << ")" << endl;
            }
        }
        else if(IsNumber(ch)==1)
        {
            while(IsNumber(ch)==1)
            {
                strToken=strToken+ch;
                ch=fgetc(fp);
            }
            if(IsLetter(ch)==1)
            {
                while(IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    strToken=strToken+ch;
                    ch=fgetc(fp);
                }
                fseek(fp,-1L,SEEK_CUR);
                row++;
                Error(strToken);
            }
            else  //识别出一串完整的数字
            {
                InsertNum(strToken);//数字表中查询是否存在
                row++; //识别出一个字符，列增加一
                cout << strToken << "\t\t(5," << strToken << ")" << "\t\t常数"<<"\t\t("<<line<<","<<row<<")"<<endl;
                fseek(fp,-1L,SEEK_CUR);
            }
        }
        else
        {
            row++;
            strToken=ch;
            switch(ch)
            {
            case'+':
            {
                ch=fgetc(fp);
                if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "+" << "\t\t(3,+)" << "\t\t算术运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }
            break;
            case'-':
            {
                ch=fgetc(fp);
                if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "-" << "\t\t(3,-)" << "\t\t算术运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }
            break;
            case'*':
            {
                ch=fgetc(fp);
                if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "*" << "\t\t(3,*)" << "\t\t算术运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }
            break;
            case'=':
            {
                ch=fgetc(fp);
                if(ch=='=')
                {
                    cout << "==" << "\t\t(4,==)" << "\t\t关系运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                }
                else if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "=" << "\t\t(4,=)" << "\t\t关系运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }
            break;
            /*case'/':{
                ch=fgetc(fp);
                if(ch=='*'){
                        ch=fgetc(fp);
                        while(ch!='*'){
                            if(ch=='\n'){
                                line++;
                                row=0;
                            }
                            ch=fgetc(fp);
                            if(ch=='*'){
                                ch=fgetc(fp);
                                if(ch=='/'){
                                    fseek(fp,-1L,SEEK_CUR);
                                    break;
                                }
                            }
                        }
                }
                if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1){
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "/" << "\t\t(3,/)" << "\t\t算术运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }*/
            case'/':
                cout << ch << "\t\t(3," << ch << ")"<< "\t\t算术运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                break;
            case'(':
            case')':
            case'[':
            case']':
            case';':
            case'.':
            case',':
            case'{':
            case'}':
                cout << ch << "\t\t(2," << ch << ")" << "\t\t分界符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                break;
            case'>':
            {
                ch = fgetc(fp);
                if (ch == '=')
                    cout << ">=" << "\t\t(4,>=)" << "\t\t关系运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                else
                {
                    cout << ">" << "\t\t(4,>)" << "\t\t关系运算符"<<"\t\t("<<line<<","<<row<<")"<<endl;
                    fseek(fp, -1L, SEEK_CUR);
                }
            }
            break;
            case'<':
            {
                ch = fgetc(fp);
                if (ch == '=')
                    cout << "<=" << "\t\t(4,<=)" << "\t\t关系运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                else if (ch == '>')
                    cout << "<>" << "\t\t(4,<>)" << "\t\t关系运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                else
                {
                    cout << "<" << "\t\t(4,<)" << "\t\t关系运算符"<<"\t("<<line<<","<<row<<")"<<endl;
                    fseek(fp, -1L, SEEK_CUR);
                }
            }
            break;
            default:
                cout << ch << "\t\tError" << "\t\tError"<<"\t\t("<<line<<","<<row<<")"<<endl;
                break;
            }
        }
    }
}

int main()
{
    char filename[30]="E:\\大学\\编译原理\\1.txt";
    FILE* fp;
    /*cout << "请输入源文件名（包括路径和后缀名）:";
    for (;;)  //若输入错误则循环
    {
        cin >> filename;
        if ((fp = fopen(filename, "r")) != NULL)//打开文件则退出循环
            break;
        else
            cout << "文件路径错误！请输入源文件名（包括路径和后缀名） :";
    }*/
    if ((fp = fopen(filename, "r")) == NULL){//打开文件则退出循环
        cout << "文件路径错误！";
        return 0;
    }
    cout << "\n———————————————词法分析———————————————"<<endl;
    cout << "单词\t\t二元序列\t类型\t\t位置（行，列） "<<endl;
    analyse(fp);
    fclose(fp);
    return 0;
}
