package LR;
import java.util.*;

public class LR1 extends Data{
	
	//��ӡ
	void show_LR1() {
		//��ӡ�ķ�
		System.out.println("����չ�ķ�Ϊ��");
		for(String i : map_num)
        {
        	System.out.println(i);
        }
    	System.out.println("------------------------------------");	
    	
    	//��ӡFIRST
	    System.out.println("���ķ���FIRST��Ϊ��");
		for (String key : map.keySet()) {
			System.out.println("FIRST("+key+") = "+FIRST.get(key));
        }
		System.out.println("------------------------------------");	
		
		//��ӡFOLLOW
		System.out.println("���ķ���FOLLOW��Ϊ��");
		for (String key : map.keySet()) {
			System.out.println("FOLLOW("+key+") = "+FOLLOW.get(key));
        }
		System.out.println("------------------------------------");			
		
		//��ӡ��Ŀ����
		System.out.println("����չ�ķ���LR(1)��Ŀ����Ϊ��");
		for(int i = 0; i < In.size(); i++) {
			System.out.println("��Ŀ:"+ i + "----------");	
			for(String str : In.get(i)) {
				System.out.println(str + " \t|");
			}
			System.out.println("-----------------");
			System.out.println();
		}
		System.out.println("------------------------------------");	
	}
	
	//��FIRST(A)��
	void getFIRST() {
		
		ListIterator<Map.Entry<String, String[]>> li = new ArrayList<Map.Entry<String, String[]>>(map.entrySet()).listIterator(map.size());
	    
	    while(li.hasPrevious()) {  //������FIRST��
	        Map.Entry<String, String[]> entry = li.previous();
	        Set<String> set = new LinkedHashSet<>();//�ݴ�
	        for(String i : entry.getValue()) {
	        	if(i.equals(""))
            		continue;
            	i = String.valueOf(i.charAt(0));
            	if(isVT(i)||i.equals("��")) {	//���ս���Ϳ��ּ���
            		set.add(i);	
            	}
            	else {	//�����ս��i��FIRST������
            		if(i.equals(entry.getKey()))
            			continue;
            		set.addAll(FIRST.get(i));
//            		System.out.println(entry.getKey());
            	}	
	        }
	        FIRST.put(entry.getKey(), set);	        
	    }
	}
	
	//��FOLLOW(A)��
	void getFOLLOW() {
		for (String key : map.keySet()) {
			Set<String> set = new LinkedHashSet<>();
			for(String i : map.keySet()) {
				for(String j : map.get(i)) {
					if(j.equals(""))
	            		continue;
	            	int index = j.indexOf(key);//����key��λ��	            	
	            	//ΪaBb��
	            	if(index >= 0 && index < j.length() - 1) { 
	            		String str = String.valueOf(j.charAt(index+1));
	            		if (isVT(str)) { //�ս��ֱ�Ӽ���
	            			set.add(str);
	            		}
	            		//�����ҵ�str��FIRST�����벢ȥ����
	            		else {
	            			set.addAll(FIRST.get(str));//һ��������FIRST��ȥ����
	            			set.remove("��");
	            		}
	            	}
	            	//ΪaB��
	            	if(index >= 0) { //�ҵ�����key��λ�ã���i��FOLLOW������key
	            		if(i.equals(key))
	            			continue;
//	            		System.out.println(i+index+"����");
	            		if(FOLLOW.get(i)!=null) {//����i��FOLLOW������
	            		set.addAll(FOLLOW.get(i));
	            		}
	            	}
				}
			}
        	//��ʼ������#
        	if(key.equals(VN[0])) {
				set.add("#");
			}
			FOLLOW.put(key, set);
        }
	}
	
	//����Bչ��
	Set<String> expand(String s) {
		Set<String> temp = new LinkedHashSet<>();
		for(String i : map_num) {
			if(String.valueOf(i.charAt(0)).equals(String.valueOf(s.charAt(0)))) {//�ҵ���Ӧ�ķ�
				StringBuffer str = new StringBuffer();
				str.append(i);
				str.append(",");
				//���롤
				int index = i.indexOf(">");
				str.insert(index+1, "��");
				String fir = String.valueOf(s.charAt(1));
				if(fir.equals(",")) {//S->a��B,a��ʽ
					str.append(String.valueOf(s.charAt(s.length()-1)));//��չ��������
					temp.add(str.toString());//������Ŀ
				}//S->a��Bb,a��ʽ����Ҫ��FIRST(ba)
				else if(isVT(fir)) {//b���ս��,ֱ����Ϊչ����
					str.append(fir);
					temp.add(str.toString());//������Ŀ
				}
				else {//b�Ƿ��ս����VN
					for (String key : FIRST.get(fir)) { //firΪ���ս��������FIRST��
						if(key.equals("��")) { //������ֱ��չ��������
							str.append(String.valueOf(s.charAt(s.length()-1)));
						}
						//ѭ������FIRST���ս��
						else{
							str.append(key);
						}
						temp.add(str.toString());//������Ŀ
						str.deleteCharAt(str.length()-1);//���ã������¼���
			        }
				}
//				System.out.println();
			}
		}
//		//���Դ���
//		for (String key : temp) {
//			System.out.println(key+temp.size());
//        }
		return temp;
	}
	//��CLOSURE(I)
	Set<String> CLOSURE(Set<String> I) {
		if(I.isEmpty()) { //�ռ�ֱ�ӷ��ؿձհ�
			return I;
		}
		Set<String> temp = new LinkedHashSet<>();
		int index= 0;
		String str ="";
		boolean flag = true;
		while(flag) {
			int size = temp.size();
			for(String i:I) {
				//����ķ�
				temp.add(i);
				index = i.indexOf("��");
				str = i.substring(index+1, i.length());//������ķ���
				String fir = String.valueOf(str.charAt(0));
				if(isVN(fir)) { //��һ���ַ�Ϊ���ս��
					temp.addAll(expand(str));//��չ����Ŀ����
				}
			}
			I.addAll(temp);
			if(size == temp.size()) { //CLOSURE(I)�����������˳�
				flag = false;
			}
		}
	    return I;
	}	
	
	//��GO(I, X)=CLOSURE(J)
	Set<String> GO(Set<String> I, String X) {
		Set<String> temp = new LinkedHashSet<>();
		int index= 0;
		String str ="";
		for(String i : I) {
			index = i.indexOf("��");
			str = String.valueOf(i.charAt(index+1));//������ķ���
			if(str.equals(X)) { //�ƽ���
				StringBuffer s = new StringBuffer(i);
				s.deleteCharAt(index);//ɾ����
				s.insert(index+1, "��");//���ơ�
				temp.add(s.toString());//�����ƽ���Ŀ
			}
		}
	    return CLOSURE(temp);
//	    return temp;
	}
	
	//����LR(1)��Ŀ����
	List<Set<String>> get_C(Set<Set<String>> C) {
		Set<String> set = new LinkedHashSet<String> ();
		Set<Set<String>> c = new LinkedHashSet<>();
		set.add("S->��E,#");//��ʼ��Ŀ
		c.add(CLOSURE(set));//����I0
		boolean flag = true;
		while(flag) {
			int size = C.size();
			for(Set<String> I : C) { //C��ÿ����Ŀ
				for(String X : table_row) { //S��ÿ������
					if(!GO(I,X).isEmpty()) { //��Ϊ�������
						c.add(GO(I,X));
//						for(String i : GO(I,X)) {
//							System.out.println(i);
//						}
//						System.out.println(c.size()+"---------");
					}
				}
			}
			C.addAll(c);
			if(size == C.size()) { //���������˳�
				flag = false;
			}
		}
		List<Set<String>> list = new ArrayList<>(C);
		return list;
	}
	
	//�ķ��ĵ�j������ʽ
	int get_mapNum(String str) {
		for(int i = 0; i < map_num.length; i++) {
			if( map_num[i].equals(str) ) {
				return i;
			}
		}
		return -1;
	}
	
	//��I0-In״̬�±�
	int get_INum(Set<String> Ij) {
		for(int i = 0; i < In.size(); i++){
			if(Ij.equals(In.get(i))) {
				return i;
			}
		}
		return -1;
		
	}
	
	//����LR(1)������
	String[][] getLR1_table(List<Set<String>> In) {
		
		//��ʼ��LR_ACTION��GOTO��
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
		
		//��ʼ����------�����㷨
		for(int i = 0; i <In.size(); i++ ) { //����C��I��ÿ����Ŀ
			for(String j : In.get(i)) {
				int index = j.indexOf("��");
				String str = new String(String.valueOf(j.charAt(index+1)));//������ķ���
				if(str.equals(",")) { //��Լ��Ŀ
					if(j.equals(map_num[0]+"��,#")) { //(3)��ʼ��ĿS'->s��,#
					LR_ACTION[i][getActionRow("#")] = "acc";
					}
					else { //(2)A->a��,a
						int a = getActionRow(String.valueOf(j.charAt(index+2)));
						int b = get_mapNum(j.substring(0, index));
						LR_ACTION[i][a] = "r"+ b;
//						System.out.println(b +"    \t" +a);
					}
				}
				if(isVT(str)) { //(1)A->a��bB,a
					int sj = get_INum(GO(In.get(i),str));//���ƽ����״̬
					if(sj > 0) { //�ҵ�����sj
						LR_ACTION[i][getActionRow(str)] = "s" + sj;
					}
				}
				if(isVN(str)) { //(4)A->a��B,a
					int go = get_INum(GO(In.get(i),str));//���ƽ����״̬
					if(go > 0) { //GOTO��
						LR_GOTO[i][getGotoRow(str)] = "" + go;
					}
				}
			}
		}
		
		//���ع����
//		System.out.println("------ACTION--------GOTO------");
		LR_table = new String[In.size()][table_row.length+1];
		for(int i = 0; i < In.size(); i++) { 
			LR_table[i][0] = ""+i;
			for(int j = 0; j < VT.length; j++) {//����ACTION
				LR_table[i][j+1] = LR_ACTION[i][j];
			}
			for(int j = VT.length; j < table_row.length; j++) { //����GOTO
				LR_table[i][j+1] = LR_GOTO[i][j - VT.length];
			}
		}
		
		return LR_table;
		
	}
	//��ǰaction��
	int getActionRow(String s) {
		for(int i= 0 ; i < VT.length; i++) {
			if(s.equals(VT[i]))
				return i;
		}
		return -1;
	}
	
	//��ǰgoto��
	int getGotoRow(String s) {
			for(int i= 0 ; i < VN.length; i++) {
				if(s.equals(VN[i]))
					return i;
			}
			return -1;
		}
	    
//��������׼��

  	//ȡ���봮�ַ�   
    String getChar() {
		return ( a = String.valueOf(input.charAt(location)) );
    }
    
    //ȡ״̬ջ��   
    String getTop() {
		return ( X = status_stack.peek() );
    }
    
    //�ж��ս��
    boolean isVT(String X) {
    	for (int i = 0; i < VT.length; i++) {
            if (VT[i].equals(X)) {
                return true;
            }
        }
        return false;
    }
    
  //�ж��ս��
    boolean isVN(String X) {
    	for (int i = 0; i < VN.length; i++) {
            if (VN[i].equals(X)) {
                return true;
            }
        }
        return false;
    }
    
    //��ӡ������Ϣ
  	void error() {
          System.out.println("���󣬷����жϡ�");
          System.exit(0);
    }  
    
  	//�ж��ƽ�����Լ�����ܡ�����
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
  	//��ѯAction[X,a]��
  	String Action(String X, String a) {
		return LR_ACTION[Integer.parseInt(X)][getActionRow(a)];
  	}
  	//��ѯGoto[X, VN]��
  	String Goto(String X, String vn) {
  		return LR_GOTO[Integer.parseInt(X)][getGotoRow(vn)];
  	}
    
  	//��ʼ������
    void initial(){
//    	System.out.println("����\t" + "״̬ջ\t"+ "����ջ\t" + "���봮\t" + "����˵��");
    	getFIRST();
		getFOLLOW();
     	input+="#";
     	sign_stack.push("#");
     	status_stack.push("0");
        getChar();
        getTop();
        //��ӡ��ʼ��Ϣ
        analyse_table[count][0]=""+(count+1);
        analyse_table[count][1]=status_stack.toString();
        analyse_table[count][2]=sign_stack.toString();
        analyse_table[count][3]=input.substring(location, input.length());
        analyse_table[count][4]="";
        count++;
//        System.out.printf("%-8d %-8s %-8s %-8s\t \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()));
        
     }
    //�����㷨
    void analyse() {
    	int judge = 0;
    	String action = "";
    	while(control) {
    		getTop();//ȡջ��
    		getChar();
    		//���
    		action = Action(X,a);
    		//�ж϶���
    		judge = judgeAction(action);
    		if(judge == 1) { //�ƽ�
    			status_stack.push(cutFirst(action));
    			sign_stack.push(a);
    			location++;
    			String note = "Action["+X+","+a+"]="+action+",״̬"+cutFirst(action)+"��ջ";
    			analyse_table[count][0]=""+(count+1);
    	        analyse_table[count][1]=status_stack.toString();
    	        analyse_table[count][2]=sign_stack.toString();
    	        analyse_table[count][3]=input.substring(location, input.length());
    	        analyse_table[count-1][4]=note;
    	        count++;
//    			System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), note);
    		}
    		else if(judge == 2) { //��Լ
    			int num = Integer.parseInt(cutFirst(action));
    			String vn = String.valueOf(map_num[num].charAt(0));
    			for(int i = 0; i < map_num[num].length() - 3; i++) {//��ջ����
    				status_stack.pop(); //����
        			sign_stack.pop();
    			}
    			sign_stack.push(vn); //��Լ����ջ
    			getTop();
    			status_stack.push(Goto(X,vn)); //Goto��ջ
    			String note = action+":"+map_num[num]+"��Լ,"+"Goto("+X+","+vn+")="+Goto(X,vn)+"��ջ";
    			analyse_table[count][0]=""+(count+1);
    	        analyse_table[count][1]=status_stack.toString();
    	        analyse_table[count][2]=sign_stack.toString();
    	        analyse_table[count][3]=input.substring(location, input.length());
    	        analyse_table[count-1][4]=note;
    	        count++;
//    			System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), note);
    		}
    		else if(judge == 3) { //����
        		control = false;
        		analyse_table[count-1][4]="Acc�������ɹ���";
//        		System.out.printf("%-8d %-8s %-8s %-8s %s \n", ++count,status_stack.toString(),sign_stack.toString(), input.substring(location, input.length()), "Acc,�����ɹ�");
    		}
    		else if(judge == 4) { //����
        		error();
    		}
    	}
    }
    //���й��캯����ʼ����
	public LR1(String input) {
		
		Data.input = input;	//�����봮
		initial(); //��ʼ������
		In=get_C(C);//����Ŀ����
		getLR1_table(In);//��LR��1��������
		analyse(); //��ʼ����
		
		//��ӡ��ع�����Ϣ
//		show_LR1(); 
		if(control)
			System.out.printf("����ʧ�ܡ�\n");
		else
			System.out.printf("�����ɹ���\n");
		System.out.println("-----------end-----------");
		
	}
	//������ʼ
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
		new WinTable();
	    
	}
	
}
