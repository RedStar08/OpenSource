import java.util.*;

public class Data {
		
    // aΪ��ǰ����
    String a = null;
    // XΪstackջ������
    String X = null;

    //���봮
     static String input = "i+i*i";
    // ����ջ
    Stack<String> stack = new Stack<String>();
    
    // ��¼����
    int count = 0;
    // ��¼���봮��ǰλ��
    int location = 0; 
    // Control����Ԥ������Ƿ�ɹ�
    boolean control = true;
    
	// ���ս��
    String[] VN = new String[] { "E", "G", "T", "S", "F" };
	// �ս��
    String[] VT = new String[] { "i", "+", "-", "*", "/", "(", ")", "#" };  
    
    //LL(1)�ķ�
    String[][] LL1_G = new String [][] {
		{"TG" , ""   , ""    },
		{"+TG", "-TG", "��"  },
		{"FS" , ""   , ""    },
		{"*FS", "/FS", "��"  },
		{"(E)", "i"  , ""    }
	};
	//�ķ�Map
    Map<String, String[]> map = new LinkedHashMap<>();
    //FIRST��
    Map<String,Set<String>> FIRST = new LinkedHashMap<>();
    //FOLLOW��
    Map<String,Set<String>> FOLLOW = new LinkedHashMap<>();
    //LL(1)������
    List<ArrayList<String>> table = new ArrayList<>();
    
    //������
    static String[][] first = new String [5][1];
    static String[][] follow = new String [5][1];
    static String[][] table_T = new String [5][9];
    static String[][] table_G = new String [20][4];
	  
    public Data() { //���캯�����ķ�����
    	
    	map.put("E", LL1_G[0]);
    	map.put("G", LL1_G[1]);
    	map.put("T", LL1_G[2]);
    	map.put("S", LL1_G[3]);
    	map.put("F", LL1_G[4]);
    	//��ʼ��table
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
