package LR;
import java.util.*;

public class Data {
		
    // aΪ��ǰ����
    String a = null;
    // XΪstackջ������
    String X = null;

    // ���봮
    static String input = "i+i*i";
    // ����ջ
    Stack<String> sign_stack = new Stack<String>();
    // ״̬ջ
    Stack<String> status_stack = new Stack<String>();
    
    // ��¼����
    int count = 0;
    // ��¼���봮��ǰλ��
    int location = 0; 
    // Control����Ԥ������Ƿ�ɹ�
    boolean control = true;
    
	// ���ս��
    String[] VN = new String[] { "E", "T", "F" };
	// �ս��
    String[] VT = new String[] { "i", "+", "*", "(", ")", "#" };  
    
    // LR(1)�ķ�
    String[][] LR1_G = new String [][] {
    	{"E+T", "T" },
		{"T*F", "F" },
		{"(E)", "i" }
	};
	// �ķ�Map
    Map<String, String[]> map = new LinkedHashMap<>();
   
    // FIRST��
    Map<String,Set<String>> FIRST = new LinkedHashMap<>();
    // FOLLOW��
    Map<String,Set<String>> FOLLOW = new LinkedHashMap<>();
    
    //LR(1)��Ŀ����
    static Set<Set<String>> C = new LinkedHashSet<>();
    //LR(1)������
    static List<Set<String>> In = new ArrayList<>(C);
    static String[][] LR_ACTION;
    static String[][] LR_GOTO;
    
    //�ķ����
    String [] map_num = new String [] { "S->E", "E->E+T", "E->T", "T->T*F", "T->F", "F->(E)", "F->i" };
    //ACTION��
    
    // LR������
    String[] table_row = new String [] {"i", "+", "*", "(", ")", "#", "E", "T", "F"};
    static String[][] LR_table;
    //��ʾ��������
    static String[][] analyse_table = new String [30][5];
    
    public Data() { //���캯�����ķ�����
    	// ���ķ�
    	map.put("E" , LR1_G[0]);
    	map.put("T" , LR1_G[1]);
    	map.put("F" , LR1_G[2]);
    	
    }
    
}

