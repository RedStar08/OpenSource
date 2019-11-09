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
	
	Object [] name = new Object [] {"����","״̬ջ","����ջ","���봮","����˵��"};
	Object [] title = new Object [] {"״̬","i", "+", "*", "(", ")", "#", "E", "T", "F"};
	Object a[][] = new Object [30][5];
	Object b[][] = new Object [22][10];
	
	WinTable() {
		
		button = new JButton("��ʼ����");
		lab = new JLabel("���������");
		str = new JTextField("i+i*i");
		str.setFont(new Font("����",Font.BOLD,18));
		
		menubar = new JMenuBar();
		menu = new JMenu("����");
		item = new JMenuItem("��LR(1)������");
		item1 = new JMenuItem("���ķ���Ŀ����C");
		menu.add(item);
		menu.add(item1);
		menu.addSeparator();
		menubar.add(menu);
		setJMenuBar(menubar);
		
		Table = new JTable(a,name);
		table = new JTable(b,title);
		
		//ע�������
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
		setTitle("LR(1)�ķ�����");
		setSize(600,700);
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
		
		if(e.getSource().equals(item)) {
			//������
			new LR1(str.getText());
			for(int i = 0; i < b.length; i++) {  //����LR(1)���
				for(int j = 0; j < title.length; j++) {
					b[i][j] = Data.LR_table[i][j];
				}
			}
			table.repaint(); //ˢ�±��
		}
		if(e.getSource().equals(button)) {
			//�������
			for(int i = 0; i < Data.analyse_table.length; i++) {  //���Ʒ������̱��
				for(int j = 0; j < name.length; j++) {
					Data.analyse_table[i][j] = "";
				}
			}
			Table.repaint(); //ˢ�±��
			//������
			new LR1(str.getText());
			for(int i = 0; i < a.length; i++) {  //���Ʒ������̱��
				for(int j = 0; j < name.length; j++) {
					a[i][j] = Data.analyse_table[i][j];
				}
			}
			Table.repaint(); //ˢ�±��
		}
		if(e.getSource().equals(item1)) { //��ʾ��Ŀ����
			
			JFrame jf = new JFrame("��ʾ��Ŀ���崰��");
			jf.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
			
	        // ����ˮƽ������
	        Box hBox01 = Box.createHorizontalBox();
	        Box hBox02 = Box.createHorizontalBox();
	        Box hBox03 = Box.createHorizontalBox();
	        Box hBox04 = Box.createHorizontalBox();
	        Box hBox05 = Box.createHorizontalBox();
	        
			//������
			new LR1(str.getText());
			for(int i = 0; i < Data.In.size(); i++) {
				String I = new String("-----��Ŀ:"+ i + "-----" + "\r\n");
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
			hBox05.add(Box.createHorizontalGlue()); // ��Ӳ��ɼ����������ʣ��ˮƽ�ռ�
			hBox05.add(Box.createHorizontalGlue());
			hBox05.add(Box.createHorizontalGlue());
			
			// ����һ����ֱ��������������������ˮƽ�䣨Box���Ƕ�ף�
	        Box vBox = Box.createVerticalBox();
	        vBox.add(hBox01);
	        vBox.add(hBox02);
	        vBox.add(hBox03);
	        vBox.add(hBox04);
	        vBox.add(hBox05);

	        // �Ѵ�ֱ��������Ϊ����������õ�����
	        jf.setContentPane(vBox);

	        jf.pack();
	        jf.setLocationRelativeTo(null);
	        jf.setVisible(true);
	        jf.setSize(700, 800);
		}
	}
	
}
