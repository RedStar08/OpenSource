package LR;
import java.util.*;

public class Data {
		
    // a为当前符号
    String a = null;
    // X为stack栈顶符号
    String X = null;

    // 输入串
    static String input = "i+i*i";
    // 符号栈
    Stack<String> sign_stack = new Stack<String>();
    // 状态栈
    Stack<String> status_stack = new Stack<String>();
    
    // 记录步数
    int count = 0;
    // 记录输入串当前位置
    int location = 0; 
    // Control控制预测分析是否成功
    boolean control = true;
    
	// 非终结符
    String[] VN = new String[] { "E", "T", "F" };
	// 终结符
    String[] VT = new String[] { "i", "+", "*", "(", ")", "#" };  
    
    // LR(1)文法
    String[][] LR1_G = new String [][] {
    	{"E+T", "T" },
		{"T*F", "F" },
		{"(E)", "i" }
	};
	// 文法Map
    Map<String, String[]> map = new LinkedHashMap<>();
   
    // FIRST集
    Map<String,Set<String>> FIRST = new LinkedHashMap<>();
    // FOLLOW集
    Map<String,Set<String>> FOLLOW = new LinkedHashMap<>();
    
    //LR(1)项目集族
    static Set<Set<String>> C = new LinkedHashSet<>();
    //LR(1)分析表
    static List<Set<String>> In = new ArrayList<>(C);
    static String[][] LR_ACTION;
    static String[][] LR_GOTO;
    
    //文法编号
    String [] map_num = new String [] { "S->E", "E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->i" };
    //ACTION表
    
    // LR分析表
    String[] table_row = new String [] {"i", "+", "*", "(", ")", "#", "E", "T", "F"};
    static String[][] LR_table;
    //显示分析过程
    static String[][] analyse_table = new String [30][5];
    
    public Data() { //构造函数绑定文法数据
    	// 绑定文法
    	map.put("E" , LR1_G[0]);
    	map.put("T" , LR1_G[1]);
    	map.put("F" , LR1_G[2]);
    	
    }
    
}

