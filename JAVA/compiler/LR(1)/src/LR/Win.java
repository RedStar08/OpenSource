package LR;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

@SuppressWarnings("serial")
class WinTable extends JFrame implements ActionListener {

	JLabel lab;
	JTextField str;
	JButton button;
	JTable table;
	JTable Table;
	JMenuBar menubar;
	JMenu menu;
	JMenuItem item , item1;
	
	Object [] name = new Object [] {"步骤","状态栈","符号栈","输入串","动作说明"};
	Object [] title = new Object [] {"状态","i", "+", "*", "(", ")", "#", "E", "T", "F"};
	Object a[][] = new Object [30][5];
	Object b[][] = new Object [22][10];
	
	WinTable() {
		
		button = new JButton("开始分析");
		lab = new JLabel("输入分析串");
		str = new JTextField("i+i*i");
		str.setFont(new Font("宋体",Font.BOLD,18));
		
		menubar = new JMenuBar();
		menu = new JMenu("功能");
		item = new JMenuItem("求LR(1)分析表");
		item1 = new JMenuItem("求文法项目集族C");
		menu.add(item);
		menu.add(item1);
		menu.addSeparator();
		menubar.add(menu);
		setJMenuBar(menubar);
		
		Table = new JTable(a,name);
		table = new JTable(b,title);
		
		//注册监视器
		button.addActionListener(this);
		item.addActionListener(this);
		item1.addActionListener(this);
		
		Container con = getContentPane();
		con.add(lab);
		con.add(str);
		con.add(button);
		getContentPane().add(new JScrollPane(table));
		getContentPane().add(new JScrollPane(Table));
		Table.getColumnModel().getColumn(0).setPreferredWidth(20);
		Table.getColumnModel().getColumn(1).setPreferredWidth(120);
		Table.getColumnModel().getColumn(2).setPreferredWidth(100);
		Table.getColumnModel().getColumn(3).setPreferredWidth(80);
		Table.getColumnModel().getColumn(4).setPreferredWidth(200);
		
		setLayout(new BoxLayout(con, 1));
		setTitle("LR(1)文法分析");
		setSize(600,700);
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		
		if(e.getSource().equals(item)) {
			//绑定数据
			new LR1(str.getText());
			for(int i = 0; i < b.length; i++) {  //复制LR(1)表格
				for(int j = 0; j < title.length; j++) {
					b[i][j] = Data.LR_table[i][j];
				}
			}
			table.repaint(); //刷新表格
		}
		if(e.getSource().equals(button)) {
			//清空数据
			for(int i = 0; i < Data.analyse_table.length; i++) {  //复制分析过程表格
				for(int j = 0; j < name.length; j++) {
					Data.analyse_table[i][j] = "";
				}
			}
			Table.repaint(); //刷新表格
			//绑定数据
			new LR1(str.getText());
			for(int i = 0; i < a.length; i++) {  //复制分析过程表格
				for(int j = 0; j < name.length; j++) {
					a[i][j] = Data.analyse_table[i][j];
				}
			}
			Table.repaint(); //刷新表格
		}
		if(e.getSource().equals(item1)) { //显示项目集族
			
			JFrame jf = new JFrame("显示项目集族窗口");
			jf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			
	        // 创建水平箱容器
	        Box hBox01 = Box.createHorizontalBox();
	        Box hBox02 = Box.createHorizontalBox();
	        Box hBox03 = Box.createHorizontalBox();
	        Box hBox04 = Box.createHorizontalBox();
	        Box hBox05 = Box.createHorizontalBox();
	        
			//绑定数据
			new LR1(str.getText());
			for(int i = 0; i < Data.In.size(); i++) {
				String I = new String("-----项目:"+ i + "-----" + "\r\n");
				for(String str : Data.In.get(i)) {
					I += "  "+str + " \t|" + "\r\n";
				}
				I += "-----------------" + "\r\n";
				if( i < 5 )
					hBox01.add(new JScrollPane(new JTextArea(I)));
				if( i < 10 && i >= 5 )
					hBox02.add(new JScrollPane(new JTextArea(I)));
				if( i < 15 && i >= 10 )
					hBox03.add(new JScrollPane(new JTextArea(I)));
				if( i < 20 && i >= 15)
					hBox04.add(new JScrollPane(new JTextArea(I)));
				if( i < 22 && i >= 20)
					hBox05.add(new JScrollPane(new JTextArea(I)));
			}
			hBox05.add(Box.createHorizontalGlue()); // 添加不可见组件，撑满剩余水平空间
			hBox05.add(Box.createHorizontalGlue());
			hBox05.add(Box.createHorizontalGlue());
			
			// 创建一个垂直箱容器，放置上面两个水平箱（Box组合嵌套）
	        Box vBox = Box.createVerticalBox();
	        vBox.add(hBox01);
	        vBox.add(hBox02);
	        vBox.add(hBox03);
	        vBox.add(hBox04);
	        vBox.add(hBox05);

	        // 把垂直箱容器作为内容面板设置到窗口
	        jf.setContentPane(vBox);

	        jf.pack();
	        jf.setLocationRelativeTo(null);
	        jf.setVisible(true);
	        jf.setSize(700, 800);
		}
	}
	
}
