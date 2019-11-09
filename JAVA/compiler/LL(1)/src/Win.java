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
	Object [] name = new Object [] {"步骤","分析栈","剩余输入串","所用产生式"};
	Object [] title = new Object [] {"同步分析表","i", "+", "-", "*", "/", "(", ")", "#"};
	Object [] f = new String[] {"该文法的FIRST集为："};
	Object [] fo = new String[] {"该文法的FOLLOW集为："};
	String[][] First = new String [5][1];
	String[][] Follow = new String [5][1];
	WinTable() {
		//绑定数据
		a = Data.table_G; //绑定分析表数据源（给定地址）
		for(int i = 0; i < 20; i++) { //刷新清空
			for(int j = 0; j < 4; j++) {
				a[i][j]=" ";
			}
		}
		b = Data.table_T;
		for(int i = 0; i < 5; i++) { //刷新清空
			for(int j = 0; j < 9; j++) {
				b[i][j]=" ";
			}
		}
		First = Data.first;
		Follow = Data.follow;
		for(int i = 0; i < 5; i++) { //刷新清空
			First[i][0] = " ";
			Follow[i][0] = " ";
		}
		
		button = new JButton("开始分析");
		lab = new JLabel("输入分析串");
		str = new JTextField("i+i*i");
		str.setFont(new Font("宋体",Font.BOLD,18));
		
		menubar = new JMenuBar();
		menu = new JMenu("功能");
		item0 = new JMenuItem("求FIRST集");
		item1 = new JMenuItem("求FOLLOW集");
		item2 = new JMenuItem("求同步分析表");
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
		//注册监视器
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
		// TODO 自动生成的方法存根
		new LL1(new String(Data.input));
		
		if(e.getSource()==item0) { //菜单0事件
			First = Data.first; //更新FIRST数据
			first.repaint();
		
		}
		if(e.getSource()==item1) { //菜单1事件
			Follow = Data.follow; //更新FOLLOW数据
			follow.repaint();
			
		}
		
		if(e.getSource()==item2) { //菜单2事件
			b = Data.table_T; //更新同步表数据
			table.repaint();
		}
		
		if(e.getSource()==button) { //按钮事件
			str.getText();
			Data.input = str.getText();
			new LL1(new String(Data.input));
			a = Data.table_G; //更新分析表数据
			Table.repaint();
		
		}
	}
	
}
