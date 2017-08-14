package wordrecite;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class NewWord {
	/**
	 * 
	 */
	ReadDailyNum rdn = new ReadDailyNum();
	private  int dailyNum = rdn.readDailyNum();	
	WordHandler readl = new WordHandler();
	
	JFrame jf = new JFrame("背单词");	
	JLabel word = new JLabel();
	JLabel explain = new JLabel();
	JLabel hide = new JLabel();//用于记录产生的索引随机数，并不显示
	JLabel total = new JLabel("共"+ readl.getTotalSize()+"个");
	JLabel learned = new JLabel("本次学习"+ dailyNum+ "个，已学习"+ "0个，");
	

	int count = 1;
	
	public NewWord() throws HeadlessException{
		
		int i = readl.makeRandom();
		
		JButton b_next = new JButton("下一个");
		JButton b_recited = new JButton("已记住");
		JButton b_explain = new JButton("释意");		
		word.setText(readl.showWord(i));
		explain.setText(null);
		hide.setText(String.valueOf(i));
		
		jf.setLayout(null);
		// set button
		b_next.setLocation(800, 350);
		b_recited.setLocation(800, 450);
		b_explain.setLocation(800, 400);
		b_next.setSize(79, 30);
		b_recited.setSize(79, 30);
		b_explain.setSize(79, 30);

		b_next.addActionListener(new NextButtonHandler());
		b_recited.addActionListener(new AccomplishButtonHandler());	
		b_explain.addActionListener(new ParaphraseButtonHandler());

		//set label		
		word.setLocation(100, 100);
		word.setSize(800, 50);
		explain.setLocation(100, 200);
		explain.setSize(800, 50);
		learned.setLocation(300, 20);
		learned.setSize(300, 50);
		total.setLocation(580, 20);
		total.setSize(100, 50);
//		hide.setLocation(100,500);
//		hide.setSize(500, 40);
		
		//set font
		word.setFont(new java.awt.Font("Monospaced",Font.BOLD,22));
		explain.setFont(new java.awt.Font("Monospaced",Font.BOLD,22));
		learned.setFont(new java.awt.Font("Dialog", 0, 22));
		total.setFont(new java.awt.Font("Dialog", 0, 22));
		// add button & label to jframe.
		jf.add(b_next); jf.add(b_recited); jf.add(word); jf.add(explain); jf.add(learned); jf.add(total); jf.add(b_explain);
		jf.add(hide);
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				try {
					readl.writefile();
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}
		 });

	
		//添加主菜单
		JLabel jlMenu = new JLabel();
		
		JMenuBar jmb = new JMenuBar();
		//文件菜单
		JMenu jmFile = new JMenu("文件");
		JMenuItem jmiOpen = new JMenuItem("导入词库");
		jmFile.add(jmiOpen);
		jmb.add(jmFile);
		jmiOpen.addActionListener(new OpenFile());
		//选项菜单
		JMenu jmOptions = new JMenu("选项");
		JMenuItem jmiDaily = new JMenuItem("每天学习数量");
		jmb.add(jmOptions);
		jmOptions.add(jmiDaily);
		jmiDaily.addActionListener(new SetDaily());

		//帮助菜单
		JMenu jmHelp = new JMenu("帮助");
		jmb.add(jmHelp);
		
		jf.add(jlMenu);
		jf.setJMenuBar(jmb);
				
		//jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);关闭时应先执行WritFile
		jf.setSize(1200, 800);
		jf.setResizable(false);
		jf.setLocation(300, 100);	
		jf.setVisible(true);	
	}
	//获取hide标签的值
	public int getHideNum() throws NumberFormatException{			
		    int index = Integer.parseInt(hide.getText());
			return index;				
	}
		
	public void showNextWord(int i){
			learned.setText("本次学习"+ dailyNum+ "个，已学习"+ count+ "个，");
			word.setText(readl.showWord(i));
			hide.setText(String.valueOf(i));//将随机索引值记录在hide标签里
			explain.setText(null);
			
			if (readl.getWritelistSize() == 0){
				JOptionPane.showMessageDialog(jf, "本词库学习已结束，请导入新词库");//循环结束，弹窗显示已学完	
			}	
	}
		
/*********************************************ActionListener setted by inner class***************************************/
	private class NextButtonHandler implements ActionListener{//“下一个”键监听
		public void actionPerformed(ActionEvent e){				
			int i = readl.makeRandom();
//				System.out.println("random = "+ i);				
			if(count> dailyNum ){
				JOptionPane.showMessageDialog(jf, "本次学习已结束");//循环结束，弹窗显示已学完	
				try {
					readl.writefile();
				} catch (UnsupportedEncodingException | FileNotFoundException e1) {
					e1.printStackTrace();
				}
			}				
			if(readl.getWritelistSize()<dailyNum){
				i = readl.makeLowRandom();
			}				
			else{
				showNextWord(i);
					count += 1;
			}
		}
	}
	//“已记住”键的监听
	private class AccomplishButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
			if(count> dailyNum )
				JOptionPane.showMessageDialog(jf, "本次学习已结束");//循环结束，弹窗显示已学完					
			else{					
				readl.delword(getHideNum());
//					System.out.println("del index"+ hide.getText());
				total.setText("共"+ readl.getWritelistSize()+"个");					
				
				int i = readl.makeRandom();
				showNextWord(i);

				count += 1;
			}
		}
	}
	//“释意”键
	private class ParaphraseButtonHandler implements ActionListener{
		public void actionPerformed(ActionEvent e){
//				System.out.println("explain index"+ hide.getText());
		    explain.setText(readl.showExplain(getHideNum()));
		}
	}
//		打开文件
	private class OpenFile implements ActionListener{
		public void actionPerformed(ActionEvent e){
			FileDialog open = new FileDialog(jf, "打开文件",FileDialog.LOAD);
			open.show();
			String fileName = open.getDirectory() + open.getFile();
			if (fileName != null) {
				try {
					new Copy(fileName);
					readl = new WordHandler();
					total.setText("共"+ readl.getTotalSize()+"个"); 
					word.setText(null);
//						new NewWord();
					JOptionPane.showMessageDialog(jf, "导入成功！请按\"下一个\"继续");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
//		设置学习量
	private class SetDaily implements ActionListener{
		public void actionPerformed(ActionEvent e){
			JFrame jfDaily = new JFrame("设置日学习量");
			JTextField jtfDaily = new JTextField(3);
			JLabel jlDaily = new JLabel("请输入日学习数量,按Enter确认：");
			FlowLayout  flow=new FlowLayout( );
			jfDaily.setLayout(flow);
			jfDaily.add(jlDaily);
			jfDaily.add(jtfDaily);
			jfDaily.pack();
			jfDaily.setBounds(800, 500, 400, 100);
			jfDaily.setVisible(true);
			jtfDaily.addActionListener(new ActionListener(){//输入新的学习量后的动作
				public void actionPerformed(ActionEvent e) {
					rdn.writeDailyNum(jtfDaily.getText());
					JOptionPane.showMessageDialog(jfDaily, "设置成功，下次启动程序时生效！");
					jfDaily.dispose();//关闭jframe jfDaily.
				}
			});				
		}
	}	

	public static void main(String[] args) {
		new NewWord();
	}
}
