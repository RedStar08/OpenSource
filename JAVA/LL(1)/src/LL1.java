import java.util.*;

public class LL1 extends Data{
	
	void showLL1() {
		//��ӡ�ķ�
		System.out.println("���ķ�Ϊ��");
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
            		set.addAll(FIRST.get(i));	
            	}	
	        }
	        FIRST.put(entry.getKey(), set);	        
	        //���Դ���
	        //System.out.println("FIRST("+entry.getKey()+") = {"+FIRST.get(entry.getKey())+"}");  
	    }
	    //�����ӡ
	    System.out.println("���ķ���FIRST��Ϊ��");
		for (String key : map.keySet()) {
			first[getLine(key)][0] = "FIRST("+key+") = "+FIRST.get(key);
			System.out.println("FIRST("+key+") = "+FIRST.get(key));
        }
		System.out.println("-------------------------------------------------");
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
		//��ӡ
		System.out.println("���ķ���FOLLOW��Ϊ��");
		for (String key : map.keySet()) {
			follow[getLine(key)][0] = ("FOLLOW("+key+") = "+FOLLOW.get(key));
			System.out.println("FOLLOW("+key+") = "+FOLLOW.get(key));
        }
		System.out.println("-------------------------------------------------");
    	
	}
	
	//��ǰ��
	int getLine(String s) {
		for(int i= 0 ; i < VN.length; i++) {
			if(s.equals(VN[i]))
				return i;
		}
		return -1;
	}
	//��ǰ��
	int getRow(String s) {
		for(int i= 0 ; i < VT.length; i++) {
			if(s.equals(VT[i]))
				return i;
		}
		return -1;
	}
	//�ж�FIRST���Ƿ񺬦���
	boolean haveE(Set<String> key) {
		for (String i : key) {
			if(i.equals("��")) {
				return true;
			}
		}
		return false;
	}
	//��ǰ�����������ķ�
	String getInside(String key,String s) {
		String str = "";
		for(String i : map.get(key))
        {
        	if(i.equals("")||i.equals("��"))
        		continue;
        	String temp = String.valueOf(i.charAt(0));
        	if(temp.equals(s)) { //ƥ�䵽�ķ�
        		str = i;
        		return str;
        	}
        	else { //���ս���򷵻�ԭ�ķ�
        		str = i;
        	}
        } 
		//ƥ�䲻�����ؿ�
		return str;
	}
	//��M[A,a]��ϵ������	
	void getLL1_table() {
		Set<String> first = new LinkedHashSet<>();
		Set<String> follow = new LinkedHashSet<>();
		for (String key : map.keySet()) {
			first = FIRST.get(key);
			for(String i : first) {
				//Ѱ�Ҹ��ַ�����λ��
				int index = getRow(i);
				if (index>=0) { //�ҵ����滻
					table.get(getLine(key)).set(index, getInside(key,i));
				}
			}
			follow = FOLLOW.get(key);
			for(String i : follow) {
				//Ѱ�Ҹ��ַ�����λ��
				int index = getRow(i);
				if (index>=0) { //�ҵ����滻
					if(haveE(first)) { //FIRST�Ц����� "��"
						table.get(getLine(key)).set(index, "��");
					}
					else { //�ަ��� "synch"
						table.get(getLine(key)).set(index, "synch");
					}
					
				}
			}
        }
		//��ӡ
		System.out.println("���ķ���LL(1)ͬ��������Ϊ��");
		
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
	    
//��������׼��

  	//ȡ���봮�ַ�   
    String getChar() {
		return a = String.valueOf(input.charAt(location));
    }
    
    //ȡջ���ַ�   
    String getTop() {
		return X = stack.peek();
    }
    
    //�ж��ս��
    boolean isVT(String X) {
    	for (int i = 0; i <= (VT.length - 1); i++) {
            if (VT[i].equals(X)) {
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
    
    //ȷ��M[A,a]�����겢������ֵ
    String getLocation() {
    	int VNi = 0;
    	int VTj = 0;
    	//������
    	for (int i = 0; i < VN.length; i++) {
            if (VN[i].equals(X)) {
                VNi = i;
            }
        }
    	//������
        for (int j = 0; j < VT.length; j++) {
            if (VT[j].equals(a)) {
                VTj = j;
            }
        }
    	return table.get(VNi).get(VTj);
    }
    
    //stack�ƽ�����(��ʾ����)
    void pushStack() {
    	stack.pop();
    	String temp = getLocation();
    	for(int i=temp.length()-1;i>=0;i--){ //��M[A,a]����ѹ��ջ
    		stack.push( String.valueOf(temp.charAt(i)) );
    	}
    	//��ӡM[A,a]��������Ϣ
    	System.out.printf("%-6d %-20s %6s\t %-1s->%-12s\n", (++count), stack.toString(),input.substring(location, input.length()), X, temp);
    	table_G[count][0] = (""+count);
    	table_G[count][1] = stack.toString();
    	table_G[count][2] = input.substring(location, input.length());
    	table_G[count][3] = (X+"->"+temp);
    }
    
    //�����㷨(���ģ�ʵ��ͬ���������)
    void analyse() {
    	while(control) {
    		getTop();//ȡջ��
    		if(isVT(X)) { //���ս��
    			if(X.equals(a)) { //�ս��ƥ��
    				location++;
    				a = getChar();
    				stack.pop();
    				//��ӡƥ����Ϣ
    				System.out.printf("%-6d %-20s %6s\t \n", (++count), stack.toString(),input.substring(location, input.length()));
    				table_G[count][0] = (""+count);
    		    	table_G[count][1] = stack.toString();
    		    	table_G[count][2] = input.substring(location, input.length());
    			}
    			else { //ջ���ս����ƥ�䣬������
    				stack.pop();
    				System.out.printf(X+"�ѵ���ջ\n");
    				error();
    			}
    		}
    		else if(X.equals("#")) { //������־
    			if(X.equals(a))
    				control=false;
    			else
    				error();
    		}
    		else if(getLocation() != "") { //�ж�M[A,a]���жϹ�ϵ����
    			if(getLocation().equals("synch")) { //ͬ��������ջ���ķ��ս��
    				System.out.printf("��,M["+X+","+a+"]=synch\n");
    				if(X.equals(VN[0])) {//���Ϊ��ʼ���ţ��������������
    					System.out.printf("��,����" + a +"\n");
    	    			location++;
    	    			a = getChar();
    				}
    				else {
    				stack.pop();
    				System.out.printf(X+"�ѵ���ջ\n");
    				}
    			}
    			else if(getLocation().equals("��")) { // ���ֿ�ֱ����ȥ
    				stack.pop();
    				System.out.printf("%-6d %-20s %6s\t %-1s->%-12s\n", (++count), stack.toString(),input.substring(location, input.length()), X, getLocation());
    				table_G[count][0] = (""+count);
    		    	table_G[count][1] = stack.toString();
    		    	table_G[count][2] = input.substring(location, input.length());
    		    	table_G[count][3] = (X+"->"+getLocation());
    			}
    			else {//����M[A,a]�Ž�ջ�ƽ�
    				pushStack();
    			}
    		}
    		else {//M[A,a]Ϊ�գ������������
    			System.out.printf("��,����" + a +"\n");
    			location++;
    			a = getChar();
    		}
    	}
    }
    
//��ʼ��������
    
    //��ʼ������
    void initial(){
     	input+="#";
     	stack.push("#");
     	System.out.println("����\t" + "����ջ\t" + "\t    ʣ�����봮\t" + "���ò���ʽ ");
     	stack.push(VN[0]);//��ʼ����ջ
        	getChar();
        	//��ӡ��ʼ��Ϣ
        	System.out.printf("%-6d %-20s %6s\t \n", count, stack.toString(), input.substring(location, input.length()));
        	table_G[count][0] = (""+count);
        	table_G[count][1] = stack.toString();
        	table_G[count][2] = input.substring(location, input.length());
     }
    
    //���й��캯����ʼ����
	public LL1(String input) {
		showLL1();
		getFIRST(); //��FIRST��
		getFOLLOW(); //��FOLLOW��
		getLL1_table(); //��LL(1)ͬ��������
		Data.input = input;	//�����봮
		initial(); //��ʼ������
		analyse(); //��ʼ����
		if(control)
			System.out.printf("����ʧ�ܡ�\n");
		else
			System.out.printf("�����ɹ���\n");
	}
	
	//������ʼ
	public static void main(String[] args) {
		// TODO �Զ����ɵķ������
	    @SuppressWarnings("unused")
		WinTable win = new WinTable();
	    
	}
	
	
	
}