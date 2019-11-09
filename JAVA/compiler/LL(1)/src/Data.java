import java.util.*;

public class Data {
		
    // a为当前符号
    String a = null;
    // X为stack栈顶符号
    String X = null;

    //输入串
     static String input = "i+i*i";
    // 分析栈
    Stack<String> stack = new Stack<String>();
    
    // 记录步数
    int count = 0;
    // 记录输入串当前位置
    int location = 0; 
    // Control控制预测分析是否成功
    boolean control = true;
    
	// 非终结符
    String[] VN = new String[] { "E", "G", "T", "S", "F" };
	// 终结符
    String[] VT = new String[] { "i", "+", "-", "*", "/", "(", ")", "#" };  
    
    //LL(1)文法
    String[][] LL1_G = new String [][] {
		{"TG" , ""   , ""    },
		{"+TG", "-TG", "ε"  },
		{"FS" , ""   , ""    },
		{"*FS", "/FS", "ε"  },
		{"(E)", "i"  , ""    }
	};
	//文法Map
    Map<String, String[]> map = new LinkedHashMap<>();
    //FIRST集
    Map<String,Set<String>> FIRST = new LinkedHashMap<>();
    //FOLLOW集
    Map<String,Set<String>> FOLLOW = new LinkedHashMap<>();
    //LL(1)分析表
    List<ArrayList<String>> table = new ArrayList<>();
    
    //绑定数据
    static String[][] first = new String [5][1];
    static String[][] follow = new String [5][1];
    static String[][] table_T = new String [5][9];
    static String[][] table_G = new String [20][4];
	  
    public Data() { //构造函数绑定文法数据
    	
    	map.put("E", LL1_G[0]);
    	map.put("G", LL1_G[1]);
    	map.put("T", LL1_G[2]);
    	map.put("S", LL1_G[3]);
    	map.put("F", LL1_G[4]);
    	//初始化table
    	for(int i= 0 ; i < VN.length; i++) {
    		ArrayList<String> list = new ArrayList<>();
    		for(int j= 0 ; j < VT.length; j++) {
    			list.add("");
    		}
    		table.add(list);
    	}
//    	System.out.println(table);
    }
    
    
}
