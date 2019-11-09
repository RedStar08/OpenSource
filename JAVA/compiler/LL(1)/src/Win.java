import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class WinTable extends JFrame implements ActionListener {

	JLabel lab;
	JTextField str;
	JTable first;
	JTable follow;
	JButton button;
	JTable table;
	JTable Table;
	JMenuBar menubar;
	JMenu menu;
	JMenuItem item0,item1,item2;
	
	Object a[][] = new Object [20][4];;
	Object b[][] = new Object [5][9];;
	Object [] name = new Object [] {"����","����ջ","ʣ�����봮","���ò���ʽ"};
	Object [] title = new Object [] {"ͬ��������","i", "+", "-", "*", "/", "(", ")", "#"};
	Object [] f = new String[] {"���ķ���FIRST��Ϊ��"};
	Object [] fo = new String[] {"���ķ���FOLLOW��Ϊ��"};
	String[][] First = new String [5][1];
	String[][] Follow = new String [5][1];
	WinTable() {
		//������
		a = Data.table_G; //�󶨷���������Դ��������ַ��
		for(int i = 0; i < 20; i++) { //ˢ�����
			for(int j = 0; j < 4; j++) {
				a[i][j]=" ";
			}
		}
		b = Data.table_T;
		for(int i = 0; i < 5; i++) { //ˢ�����
			for(int j = 0; j < 9; j++) {
				b[i][j]=" ";
			}
		}
		First = Data.first;
		Follow = Data.follow;
		for(int i = 0; i < 5; i++) { //ˢ�����
			First[i][0] = " ";
			Follow[i][0] = " ";
		}
		
		button = new JButton("��ʼ����");
		lab = new JLabel("���������");
		str = new JTextField("i+i*i");
		str.setFont(new Font("����",Font.BOLD,18));
		
		menubar = new JMenuBar();
		menu = new JMenu("����");
		item0 = new JMenuItem("��FIRST��");
		item1 = new JMenuItem("��FOLLOW��");
		item2 = new JMenuItem("��ͬ��������");
		menu.add(item0);
		menu.add(item1);
		menu.add(item2);
		menu.addSeparator();
		menubar.add(menu);
		setJMenuBar(menubar);
		
		first = new JTable(First,f);
		first.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		follow = new JTable(Follow,fo);
		
		table = new JTable(b,title);
		Table = new JTable(a,name);
		//ע�������
		button.addActionListener(this);
		item0.addActionListener(this);
		item1.addActionListener(this);
		item2.addActionListener(this);
		
		Container con = getContentPane();
		con.add(lab);
		con.add(str);
		con.add(button);
		getContentPane().add(new JScrollPane(first));
		getContentPane().add(new JScrollPane(follow));
		getContentPane().add(new JScrollPane(table));
		getContentPane().add(new JScrollPane(Table));
		
		setLayout(new BoxLayout(con, 1));
		setSize(600,700);
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		new LL1(new String(Data.input));
		
		if(e.getSource()==item0) { //�˵�0�¼�
			First = Data.first; //����FIRST����
			first.repaint();
		
		}
		if(e.getSource()==item1) { //�˵�1�¼�
			Follow = Data.follow; //����FOLLOW����
			follow.repaint();
			
		}
		
		if(e.getSource()==item2) { //�˵�2�¼�
			b = Data.table_T; //����ͬ��������
			table.repaint();
		}
		
		if(e.getSource()==button) { //��ť�¼�
			str.getText();
			Data.input = str.getText();
			new LL1(new String(Data.input));
			a = Data.table_G; //���·���������
			Table.repaint();
		
		}
	}
	
}
