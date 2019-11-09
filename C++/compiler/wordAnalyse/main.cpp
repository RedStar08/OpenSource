#include <iostream>
#include <string>
#include <stdio.h>
#include <stdlib.h>

using namespace std;

#define MAX 33//����治Ҫ�ӷֺ�
char ch=' ';//Ϊɶ�ǿո�
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
        if(k[i].compare(s) == 0)//k[i].comapare(s) == 0��Ϊ���
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

//1�ؼ��֣�2�ֽ����3�������㣬4.��ϵ���㣬5.�޷�������6.��ʶ��
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
                cout << strToken << "\t\t(1," << strToken << ")  " << "\t�ؼ���" << "\t\t(" << line << "," << row << ")" << endl;
            }
            else
            {
                row++;
                InsertId(strToken);
                cout << strToken << "\t\t(6," << strToken << ")" << "\t\t��ʶ��" << "\t\t(" << line << "," << row << ")" << endl;
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
            else  //ʶ���һ������������
            {
                InsertNum(strToken);//���ֱ��в�ѯ�Ƿ����
                row++; //ʶ���һ���ַ���������һ
                cout << strToken << "\t\t(5," << strToken << ")" << "\t\t����"<<"\t\t("<<line<<","<<row<<")"<<endl;
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
                    cout << "+" << "\t\t(3,+)" << "\t\t���������"<<"\t\t("<<line<<","<<row<<")"<<endl;
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
                    cout << "-" << "\t\t(3,-)" << "\t\t���������"<<"\t\t("<<line<<","<<row<<")"<<endl;
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
                    cout << "*" << "\t\t(3,*)" << "\t\t���������"<<"\t\t("<<line<<","<<row<<")"<<endl;
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
                    cout << "==" << "\t\t(4,==)" << "\t\t��ϵ�����"<<"\t("<<line<<","<<row<<")"<<endl;
                }
                else if(ch=='('||IsLetter(ch)==1||IsNumber(ch)==1)
                {
                    fseek(fp,-1L,SEEK_CUR);
                    cout << "=" << "\t\t(4,=)" << "\t\t��ϵ�����"<<"\t("<<line<<","<<row<<")"<<endl;
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
                    cout << "/" << "\t\t(3,/)" << "\t\t���������"<<"\t\t("<<line<<","<<row<<")"<<endl;
                }
                else
                    Error(strToken+ch);
            }*/
            case'/':
                cout << ch << "\t\t(3," << ch << ")"<< "\t\t���������"<<"\t("<<line<<","<<row<<")"<<endl;
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
                cout << ch << "\t\t(2," << ch << ")" << "\t\t�ֽ��"<<"\t\t("<<line<<","<<row<<")"<<endl;
                break;
            case'>':
            {
                ch = fgetc(fp);
                if (ch == '=')
                    cout << ">=" << "\t\t(4,>=)" << "\t\t��ϵ�����"<<"\t\t("<<line<<","<<row<<")"<<endl;
                else
                {
                    cout << ">" << "\t\t(4,>)" << "\t\t��ϵ�����"<<"\t\t("<<line<<","<<row<<")"<<endl;
                    fseek(fp, -1L, SEEK_CUR);
                }
            }
            break;
            case'<':
            {
                ch = fgetc(fp);
                if (ch == '=')
                    cout << "<=" << "\t\t(4,<=)" << "\t\t��ϵ�����"<<"\t("<<line<<","<<row<<")"<<endl;
                else if (ch == '>')
                    cout << "<>" << "\t\t(4,<>)" << "\t\t��ϵ�����"<<"\t("<<line<<","<<row<<")"<<endl;
                else
                {
                    cout << "<" << "\t\t(4,<)" << "\t\t��ϵ�����"<<"\t("<<line<<","<<row<<")"<<endl;
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
    char filename[30]="E:\\��ѧ\\����ԭ��\\1.txt";
    FILE* fp;
    /*cout << "������Դ�ļ���������·���ͺ�׺����:";
    for (;;)  //�����������ѭ��
    {
        cin >> filename;
        if ((fp = fopen(filename, "r")) != NULL)//���ļ����˳�ѭ��
            break;
        else
            cout << "�ļ�·������������Դ�ļ���������·���ͺ�׺���� :";
    }*/
    if ((fp = fopen(filename, "r")) == NULL){//���ļ����˳�ѭ��
        cout << "�ļ�·������";
        return 0;
    }
    cout << "\n�������������������������������ʷ�����������������������������������"<<endl;
    cout << "����\t\t��Ԫ����\t����\t\tλ�ã��У��У� "<<endl;
    analyse(fp);
    fclose(fp);
    return 0;
}
