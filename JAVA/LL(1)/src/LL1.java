import java.util.*;

public class LL1 extends Data{
	
	void showLL1() {
		//打印文法
		System.out.println("该文法为：");
    	for (String key : map.keySet()) {
            System.out.print(key);
            System.out.print(" -> ");
            for(String i : map.get(key))
            {
            	if(i.equals(""))
            		continue;
            	System.out.print(i+" | ");
            }
            System.out.println("");
        }
    	System.out.println("-------------------------------------------------");		
	}
			  
	//求FIRST(A)集
	void getFIRST() {
		
		ListIterator<Map.Entry<String, String[]>> li = new ArrayList<Map.Entry<String, String[]>>(map.entrySet()).listIterator(map.size());
	    
	    while(li.hasPrevious()) {  //倒叙求FIRST集
	        Map.Entry<String, String[]> entry = li.previous();
	        Set<String> set = new LinkedHashSet<>();//暂存
	        for(String i : entry.getValue()) {
	        	if(i.equals(""))
            		continue;
            	i = String.valueOf(i.charAt(0));
            	if(isVT(i)||i.equals("ε")) {	//将终结符和空字加入
            		set.add(i);	
            	}
            	else {	//将非终结符i的FIRST集加入
            		set.addAll(FIRST.get(i));	
            	}	
	        }
	        FIRST.put(entry.getKey(), set);	        
	        //测试代码
	        //System.out.println("FIRST("+entry.getKey()+") = {"+FIRST.get(entry.getKey())+"}");  
	    }
	    //正序打印
	    System.out.println("该文法的FIRST集为：");
		for (String key : map.keySet()) {
			first[getLine(key)][0] = "FIRST("+key+") = "+FIRST.get(key);
			System.out.println("FIRST("+key+") = "+FIRST.get(key));
        }
		System.out.println("-------------------------------------------------");
	}
	
	//求FOLLOW(A)集
	void getFOLLOW() {
		for (String key : map.keySet()) {
			Set<String> set = new LinkedHashSet<>();
			for(String i : map.keySet()) {
				for(String j : map.get(i)) {
					if(j.equals(""))
	            		continue;
	            	int index = j.indexOf(key);//查找key的位置	            	
	            	//为aBb型
	            	if(index >= 0 && index < j.length() - 1) { 
	            		String str = String.valueOf(j.charAt(index+1));
	            		if (isVT(str)) { //终结符直接加入
	            			set.add(str);
	            		}
	            		//否则找到str的FIRST集加入并去空字
	            		else {
	            			set.addAll(FIRST.get(str));//一定不能用FIRST集去空字
	            			set.remove("ε");
	            		}
	            	}
	            	//为aB型
	            	if(index >= 0) { //找到包含key的位置，将i的FOLLOW集加入key
	            		if(i.equals(key))
	            			continue;
//	            		System.out.println(i+index+"测试");
	            		if(FOLLOW.get(i)!=null) {//存在i的FOLLOW集加入
	            		set.addAll(FOLLOW.get(i));
	            		}
	            	}
				}
			}
        	//开始符加入#
        	if(key.equals(VN[0])) {
				set.add("#");
			}
			FOLLOW.put(key, set);
        }
		//打印
		System.out.println("该文法的FOLLOW集为：");
		for (String key : map.keySet()) {
			follow[getLine(key)][0] = ("FOLLOW("+key+") = "+FOLLOW.get(key));
			System.out.println("FOLLOW("+key+") = "+FOLLOW.get(key));
        }
		System.out.println("-------------------------------------------------");
    	
	}
	
	//求当前行
	int getLine(String s) {
		for(int i= 0 ; i < VN.length; i++) {
			if(s.equals(VN[i]))
				return i;
		}
		return -1;
	}
	//求当前列
	int getRow(String s) {
		for(int i= 0 ; i < VT.length; i++) {
			if(s.equals(VT[i]))
				return i;
		}
		return -1;
	}
	//判断FIRST集是否含ε字
	boolean haveE(Set<String> key) {
		for (String i : key) {
			if(i.equals("ε")) {
				return true;
			}
		}
		return false;
	}
	//求当前填入分析表的文法
	String getInside(String key,String s) {
		String str = "";
		for(String i : map.get(key))
        {
        	if(i.equals("")||i.equals("ε"))
        		continue;
        	String temp = String.valueOf(i.charAt(0));
        	if(temp.equals(s)) { //匹配到文法
        		str = i;
        		return str;
        	}
        	else { //非终结符则返回原文法
        		str = i;
        	}
        } 
		//匹配不到返回空
		return str;
	}
	//求M[A,a]关系分析表	
	void getLL1_table() {
		Set<String> first = new LinkedHashSet<>();
		Set<String> follow = new LinkedHashSet<>();
		for (String key : map.keySet()) {
			first = FIRST.get(key);
			for(String i : first) {
				//寻找改字符所在位置
				int index = getRow(i);
				if (index>=0) { //找到则替换
					table.get(getLine(key)).set(index, getInside(key,i));
				}
			}
			follow = FOLLOW.get(key);
			for(String i : follow) {
				//寻找改字符所在位置
				int index = getRow(i);
				if (index>=0) { //找到则替换
					if(haveE(first)) { //FIRST有ε着填 "ε"
						table.get(getLine(key)).set(index, "ε");
					}
					else { //无ε填 "synch"
						table.get(getLine(key)).set(index, "synch");
					}
					
				}
			}
        }
		//打印
		System.out.println("该文法的LL(1)同步分析表为：");
		
		System.out.println("     i  \t+  \t   -  \t\t*  \t  /  \t    (  \t\t)  \t    #");
		for (int i = 0; i < VN.length; i++) {
//			System.out.println("");
			table_T[i][0] = ""+VN[i];
			System.out.print(VN[i]);
			System.out.print("   ");
			for (int j = 0; j < VT.length; j++) {
				if(table.get(i).get(j)!="") {
					table_T[i][j+1] = ""+VN[i]+"->"+table.get(i).get(j);
					System.out.printf("%s->%-8s",VN[i],table.get(i).get(j));
				}
				else {
					table_T[i][j+1] = " ";
					System.out.printf("%s%-8s","   ",table.get(i).get(j));
				}
					
	        }
			System.out.println("");
        }
		System.out.println("-------------------------------------------------");
	}
	    
//分析过程准备

  	//取输入串字符   
    String getChar() {
		return a = String.valueOf(input.charAt(location));
    }
    
    //取栈顶字符   
    String getTop() {
		return X = stack.peek();
    }
    
    //判断终结符
    boolean isVT(String X) {
    	for (int i = 0; i <= (VT.length - 1); i++) {
            if (VT[i].equals(X)) {
                return true;
            }
        }
        return false;
    }
    
    //打印错误信息
  	void error() {
          System.out.println("错误，分析中断。");
          System.exit(0);
      }  
    
    //确定M[A,a]的坐标并返回其值
    String getLocation() {
    	int VNi = 0;
    	int VTj = 0;
    	//查找行
    	for (int i = 0; i < VN.length; i++) {
            if (VN[i].equals(X)) {
                VNi = i;
            }
        }
    	//查找列
        for (int j = 0; j < VT.length; j++) {
            if (VT[j].equals(a)) {
                VTj = j;
            }
        }
    	return table.get(VNi).get(VTj);
    }
    
    //stack推进操作(显示步数)
    void pushStack() {
    	stack.pop();
    	String temp = getLocation();
    	for(int i=temp.length()-1;i>=0;i--){ //将M[A,a]倒叙压入栈
    		stack.push( String.valueOf(temp.charAt(i)) );
    	}
    	//打印M[A,a]和其他信息
    	System.out.printf("%-6d %-20s %6s\t %-1s->%-12s\n", (++count), stack.toString(),input.substring(location, input.length()), X, temp);
    	table_G[count][0] = (""+count);
    	table_G[count][1] = stack.toString();
    	table_G[count][2] = input.substring(location, input.length());
    	table_G[count][3] = (X+"->"+temp);
    }
    
    //分析算法(核心，实现同步错误机制)
    void analyse() {
    	while(control) {
    		getTop();//取栈顶
    		if(isVT(X)) { //是终结符
    			if(X.equals(a)) { //终结符匹配
    				location++;
    				a = getChar();
    				stack.pop();
    				//打印匹配信息
    				System.out.printf("%-6d %-20s %6s\t \n", (++count), stack.toString(),input.substring(location, input.length()));
    				table_G[count][0] = (""+count);
    		    	table_G[count][1] = stack.toString();
    		    	table_G[count][2] = input.substring(location, input.length());
    			}
    			else { //栈顶终结符不匹配，弹出。
    				stack.pop();
    				System.out.printf(X+"已弹出栈\n");
    				error();
    			}
    		}
    		else if(X.equals("#")) { //结束标志
    			if(X.equals(a))
    				control=false;
    			else
    				error();
    		}
    		else if(getLocation() != "") { //判断M[A,a]并判断关系种类
    			if(getLocation().equals("synch")) { //同步，弹出栈顶的非终结符
    				System.out.printf("错,M["+X+","+a+"]=synch\n");
    				if(X.equals(VN[0])) {//如果为开始符号，则跳过输入符号
    					System.out.printf("错,跳过" + a +"\n");
    	    			location++;
    	    			a = getChar();
    				}
    				else {
    				stack.pop();
    				System.out.printf(X+"已弹出栈\n");
    				}
    			}
    			else if(getLocation().equals("ε")) { // 空字可直接消去
    				stack.pop();
    				System.out.printf("%-6d %-20s %6s\t %-1s->%-12s\n", (++count), stack.toString(),input.substring(location, input.length()), X, getLocation());
    				table_G[count][0] = (""+count);
    		    	table_G[count][1] = stack.toString();
    		    	table_G[count][2] = input.substring(location, input.length());
    		    	table_G[count][3] = (X+"->"+getLocation());
    			}
    			else {//存在M[A,a]着将栈推进
    				pushStack();
    			}
    		}
    		else {//M[A,a]为空，跳过输入符号
    			System.out.printf("错,跳过" + a +"\n");
    			location++;
    			a = getChar();
    		}
    	}
    }
    
//开始分析过程
    
    //初始化函数
    void initial(){
     	input+="#";
     	stack.push("#");
     	System.out.println("步骤\t" + "分析栈\t" + "\t    剩余输入串\t" + "所用产生式 ");
     	stack.push(VN[0]);//开始符进栈
        	getChar();
        	//打印开始信息
        	System.out.printf("%-6d %-20s %6s\t \n", count, stack.toString(), input.substring(location, input.length()));
        	table_G[count][0] = (""+count);
        	table_G[count][1] = stack.toString();
        	table_G[count][2] = input.substring(location, input.length());
     }
    
    //运行构造函数开始分析
	public LL1(String input) {
		showLL1();
		getFIRST(); //求FIRST集
		getFOLLOW(); //求FOLLOW集
		getLL1_table(); //求LL(1)同步分析表
		Data.input = input;	//绑定输入串
		initial(); //初始化数据
		analyse(); //开始分析
		if(control)
			System.out.printf("分析失败。\n");
		else
			System.out.printf("分析成功。\n");
	}
	
	//主程序开始
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
	    @SuppressWarnings("unused")
		WinTable win = new WinTable();
	    
	}
	
	
	
}