package LR;
import java.util.*;

public class LR1 extends Data{
	
	//打印
	void show_LR1() {
		//打印文法
		System.out.println("该拓展文法为：");
		for(String i : map_num)
        {
        	System.out.println(i);
        }
    	System.out.println("------------------------------------");	
    	
    	//打印FIRST
	    System.out.println("该文法的FIRST集为：");
		for (String key : map.keySet()) {
			System.out.println("FIRST("+key+") = "+FIRST.get(key));
        }
		System.out.println("------------------------------------");	
		
		//打印FOLLOW
		System.out.println("该文法的FOLLOW集为：");
		for (String key : map.keySet()) {
			System.out.println("FOLLOW("+key+") = "+FOLLOW.get(key));
        }
		System.out.println("------------------------------------");			
		
		//打印项目集族
		System.out.println("该拓展文法的LR(1)项目集族为：");
		for(int i = 0; i < In.size(); i++) {
			System.out.println("项目:"+ i + "----------");	
			for(String str : In.get(i)) {
				System.out.println(str + " \t|");
			}
			System.out.println("-----------------");
			System.out.println();
		}
		System.out.println("------------------------------------");	
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
            		if(i.equals(entry.getKey()))
            			continue;
            		set.addAll(FIRST.get(i));
//            		System.out.println(entry.getKey());
            	}	
	        }
	        FIRST.put(entry.getKey(), set);	        
	    }
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
	}
	
	//将·B展开
	Set<String> expand(String s) {
		Set<String> temp = new LinkedHashSet<>();
		for(String i : map_num) {
			if(String.valueOf(i.charAt(0)).equals(String.valueOf(s.charAt(0)))) {//找到对应文法
				StringBuffer str = new StringBuffer();
				str.append(i);
				str.append(",");
				//插入·
				int index = i.indexOf(">");
				str.insert(index+1, "·");
				String fir = String.valueOf(s.charAt(1));
				if(fir.equals(",")) {//S->a·B,a形式
					str.append(String.valueOf(s.charAt(s.length()-1)));//将展望符加入
					temp.add(str.toString());//加入项目
				}//S->a·Bb,a形式，需要求FIRST(ba)
				else if(isVT(fir)) {//b是终结符,直接作为展望符
					str.append(fir);
					temp.add(str.toString());//加入项目
				}
				else {//b是非终结符，VN
					for (String key : FIRST.get(fir)) { //fir为非终结符，利用FIRST集
						if(key.equals("ε")) { //空字则直接展望符加入
							str.append(String.valueOf(s.charAt(s.length()-1)));
						}
						//循环加入FIRST的终结符
						else{
							str.append(key);
						}
						temp.add(str.toString());//加入项目
						str.deleteCharAt(str.length()-1);//重置，并重新加入
			        }
				}
//				System.out.println();
			}
		}
//		//测试代码
//		for (String key : temp) {
//			System.out.println(key+temp.size());
//        }
		return temp;
	}
	//求CLOSURE(I)
	Set<String> CLOSURE(Set<String> I) {
		if(I.isEmpty()) { //空集直接返回空闭包
			return I;
		}
		Set<String> temp = new LinkedHashSet<>();
		int index= 0;
		String str ="";
		boolean flag = true;
		while(flag) {
			int size = temp.size();
			for(String i:I) {
				//添加文法
				temp.add(i);
				index = i.indexOf("·");
				str = i.substring(index+1, i.length());//·后面的符号
				String fir = String.valueOf(str.charAt(0));
				if(isVN(fir)) { //第一个字符为非终结符
					temp.addAll(expand(str));//将展开项目加入
				}
			}
			I.addAll(temp);
			if(size == temp.size()) { //CLOSURE(I)不再增大则退出
				flag = false;
			}
		}
	    return I;
	}	
	
	//求GO(I, X)=CLOSURE(J)
	Set<String> GO(Set<String> I, String X) {
		Set<String> temp = new LinkedHashSet<>();
		int index= 0;
		String str ="";
		for(String i : I) {
			index = i.indexOf("·");
			str = String.valueOf(i.charAt(index+1));//·后面的符号
			if(str.equals(X)) { //移进·
				StringBuffer s = new StringBuffer(i);
				s.deleteCharAt(index);//删除·
				s.insert(index+1, "·");//后移·
				temp.add(s.toString());//加入移进项目
			}
		}
	    return CLOSURE(temp);
//	    return temp;
	}
	
	//构造LR(1)项目集族
	List<Set<String>> get_C(Set<Set<String>> C) {
		Set<String> set = new LinkedHashSet<String> ();
		Set<Set<String>> c = new LinkedHashSet<>();
		set.add("S->·E,#");//初始项目
		c.add(CLOSURE(set));//首项I0
		boolean flag = true;
		while(flag) {
			int size = C.size();
			for(Set<String> I : C) { //C的每个项目
				for(String X : table_row) { //S的每个符号
					if(!GO(I,X).isEmpty()) { //不为空则加入
						c.add(GO(I,X));
//						for(String i : GO(I,X)) {
//							System.out.println(i);
//						}
//						System.out.println(c.size()+"---------");
					}
				}
			}
			C.addAll(c);
			if(size == C.size()) { //不再增大，退出
				flag = false;
			}
		}
		List<Set<String>> list = new ArrayList<>(C);
		return list;
	}
	
	//文法的第j个产生式
	int get_mapNum(String str) {
		for(int i = 0; i < map_num.length; i++) {
			if( map_num[i].equals(str) ) {
				return i;
			}
		}
		return -1;
	}
	
	//第I0-In状态下标
	int get_INum(Set<String> Ij) {
		for(int i = 0; i < In.size(); i++){
			if(Ij.equals(In.get(i))) {
				return i;
			}
		}
		return -1;
		
	}
	
	//构造LR(1)分析表
	String[][] getLR1_table(List<Set<String>> In) {
		
		//初始化LR_ACTION和GOTO表
		LR_ACTION = new String[In.size()][VT.length];
		LR_GOTO = new String[In.size()][VN.length];
		for(int i = 0; i < LR_ACTION.length; i++) {
			for(int j = 0; j < VT.length; j++) {
				LR_ACTION[i][j] = "";
			}
		}
		for(int i = 0; i < LR_GOTO.length; i++) {
			for(int j = 0; j < VN.length; j++) {
				LR_GOTO[i][j] = "";
			}
		}
		
		//开始构造------核心算法
		for(int i = 0; i <In.size(); i++ ) { //遍历C中I的每个项目
			for(String j : In.get(i)) {
				int index = j.indexOf("·");
				String str = new String(String.valueOf(j.charAt(index+1)));//·后面的符号
				if(str.equals(",")) { //归约项目
					if(j.equals(map_num[0]+"·,#")) { //(3)初始项目S'->s·,#
					LR_ACTION[i][getActionRow("#")] = "acc";
					}
					else { //(2)A->a·,a
						int a = getActionRow(String.valueOf(j.charAt(index+2)));
						int b = get_mapNum(j.substring(0, index));
						LR_ACTION[i][a] = "r"+ b;
//						System.out.println(b +"    \t" +a);
					}
				}
				if(isVT(str)) { //(1)A->a·bB,a
					int sj = get_INum(GO(In.get(i),str));//找移进后的状态
					if(sj > 0) { //找到则填sj
						LR_ACTION[i][getActionRow(str)] = "s" + sj;
					}
				}
				if(isVN(str)) { //(4)A->a·B,a
					int go = get_INum(GO(In.get(i),str));//找移进后的状态
					if(go > 0) { //GOTO表
						LR_GOTO[i][getGotoRow(str)] = "" + go;
					}
				}
			}
		}
		
		//返回构造表
//		System.out.println("------ACTION--------GOTO------");
		LR_table = new String[In.size()][table_row.length+1];
		for(int i = 0; i < In.size(); i++) { 
			LR_table[i][0] = ""+i;
			for(int j = 0; j < VT.length; j++) {//填入ACTION
				LR_table[i][j+1] = LR_ACTION[i][j];
			}
			for(int j = VT.length; j < table_row.length; j++) { //填入GOTO
				LR_table[i][j+1] = LR_GOTO[i][j - VT.length];
			}
		}
		
		return LR_table;
		
	}
	//求当前action列
	int getActionRow(String s) {
		for(int i= 0 ; i < VT.length; i++) {
			if(s.equals(VT[i]))
				return i;
		}
		return -1;
	}
	
	//求当前goto列
	int getGotoRow(String s) {
			for(int i= 0 ; i < VN.length; i++) {
				if(s.equals(VN[i]))
					return i;
			}
			return -1;
		}
	    
//分析过程准备

  	//取输入串字符   
    String getChar() {
		return ( a = String.valueOf(input.charAt(location)) );
    }
    
    //取状态栈顶   
    String getTop() {
		return ( X = status_stack.peek() );
    }
    
    //判断终结符
    boolean isVT(String X) {
    	for (int i = 0; i < VT.length; i++) {
            if (VT[i].equals(X)) {
                return true;
            }
        }
        return false;
    }
    
  //判断终结符
    boolean isVN(String X) {
    	for (int i = 0; i < VN.length; i++) {
            if (VN[i].equals(X)) {
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
    
  	//判断移进、归约、接受、报错
  	int judgeAction(String s) {
  		if(s.equals("")) {
  			return 4;
  		}
  		String temp = String.valueOf(s.charAt(0));
  		if(temp.equals("s")) {
  			return 1;
  		}
  		else if(temp.equals("r")) {
  			return 2;
  		}
  		else if(temp.equals("a")) {
  			return 3;
  		}
  		else {
  	  		return 4;
  		}
  	}
  	String cutFirst(String s) {
  		return s.substring(1, s.length());
  	}
  	//查询Action[X,a]表
  	String Action(String X, String a) {
		return LR_ACTION[Integer.parseInt(X)][getActionRow(a)];
  	}
  	//查询Goto[X, VN]表
  	String Goto(String X, String vn) {
  		return LR_GOTO[Integer.parseInt(X)][getGotoRow(vn)];
  	}
    
  	//初始化函数
    void initial(){
//    	System.out.println("步骤\t" + "状态栈\t"+ "符号栈\t" + "输入串\t" + "动作说明");
    	getFIRST();
		getFOLLOW();
     	input+="#";
     	sign_stack.push("#");
     	status_stack.push("0");
        getChar();
        getTop();
        //打印开始信息
        analyse_table[count][0]=""+(count+1);
        analyse_table[count][1]=status_stack.toString();
        analyse_table[count][2]=sign_stack.toString();
        analyse_table[count][3]=input.substring(location, input.length());
        analyse_table[count][4]="";
        count++;
//        System.out.printf("%-8d %-8s %-8s %-8s\t \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()));
        
     }
    //分析算法
    void analyse() {
    	int judge = 0;
    	String action = "";
    	while(control) {
    		getTop();//取栈顶
    		getChar();
    		//查表
    		action = Action(X,a);
    		//判断动作
    		judge = judgeAction(action);
    		if(judge == 1) { //移进
    			status_stack.push(cutFirst(action));
    			sign_stack.push(a);
    			location++;
    			String note = "Action["+X+","+a+"]="+action+",状态"+cutFirst(action)+"入栈";
    			analyse_table[count][0]=""+(count+1);
    	        analyse_table[count][1]=status_stack.toString();
    	        analyse_table[count][2]=sign_stack.toString();
    	        analyse_table[count][3]=input.substring(location, input.length());
    	        analyse_table[count-1][4]=note;
    	        count++;
//    			System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), note);
    		}
    		else if(judge == 2) { //归约
    			int num = Integer.parseInt(cutFirst(action));
    			String vn = String.valueOf(map_num[num].charAt(0));
    			for(int i = 0; i < map_num[num].length() - 3; i++) {//出栈次数
    				status_stack.pop(); //弹出
        			sign_stack.pop();
    			}
    			sign_stack.push(vn); //归约串入栈
    			getTop();
    			status_stack.push(Goto(X,vn)); //Goto入栈
    			String note = action+":"+map_num[num]+"归约,"+"Goto("+X+","+vn+")="+Goto(X,vn)+"入栈";
    			analyse_table[count][0]=""+(count+1);
    	        analyse_table[count][1]=status_stack.toString();
    	        analyse_table[count][2]=sign_stack.toString();
    	        analyse_table[count][3]=input.substring(location, input.length());
    	        analyse_table[count-1][4]=note;
    	        count++;
//    			System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), note);
    		}
    		else if(judge == 3) { //接受
        		control = false;
        		analyse_table[count-1][4]="Acc：分析成功。";
//        		System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), "Acc,分析成功");
    		}
    		else if(judge == 4) { //报错
        		error();
    		}
    	}
    }
    //运行构造函数开始分析
	public LR1(String input) {
		
		Data.input = input;	//绑定输入串
		initial(); //初始化数据
		In=get_C(C);//求项目集族
		getLR1_table(In);//求LR（1）分析表
		analyse(); //开始分析
		
		//打印相关过程信息
//		show_LR1(); 
		if(control)
			System.out.printf("分析失败。\n");
		else
			System.out.printf("分析成功。\n");
		System.out.println("-----------end-----------");
		
	}
	//主程序开始
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new WinTable();
	    
	}
	
}
