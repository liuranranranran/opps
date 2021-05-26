package oops;

import java.awt.color.*;
import java.awt.font.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;


public class Begin extends JFrame implements MouseListener {
  //设置窗体的基本属性  大小
  /**
   *  1.1、设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
    1.2、创建背景面板MainPanel，实现背景图片功能

    2.图片按钮功能
   */
	
  
  JLabel start,help,exit;

  JPanel MainPanel;

  public Begin() {//无参构造，创建对象。并在main函数中调用
    //2.2
    start = new JLabel(new ImageIcon("5.jpg"));//ImageIcon:图标
    start.setBounds(350,320,150,40);
    start.setEnabled(false);//false按钮为灰色    
    start.addMouseListener(this);
    this.add(start);

    help = new JLabel(new ImageIcon("6.jpg"));
    help.setBounds(350,420,150,40);
    help.setEnabled(false);
    help.addMouseListener(this);
    this.add(help);

    exit = new JLabel(new ImageIcon("7.jpg"));
    exit.setBounds(350, 520, 150, 40);
    exit.setEnabled(false);
    exit.addMouseListener(this);
    this.add(exit);


    /**1.实现背景图片及窗体属性*/
    MainPanel panel = new MainPanel();
    this.add(panel);

    //设置窗体基本属性大小 居中 边框隐藏 默认关闭按钮 logo图标
    this.setSize(1200,730);//大小
    this.setLocationRelativeTo(null);//居中
    this.setUndecorated(true);//边框隐藏
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//默认关闭
    this.setIconImage(new ImageIcon("Image/115.png").getImage());//logo
    this.setVisible(true);      
  }


  //2、创建背景面板MainPanel，实现背景图片功能
  class MainPanel extends JPanel{//创建的MainPanel类，在MainFrame中调用
  Image background;    
  public MainPanel() {
    try {
      background = ImageIO.read(new File("9.jpg"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  @Override
  public void paint(Graphics g) {
    super.paint(g);
    g.drawImage(background, 0, 0,1200,730, null);
    }
  }



//以下五个方法均为添加 implements MouseListener 后，快捷出来的

  @Override
  public void mouseClicked(MouseEvent e) {
    //鼠标点击
    if(e.getSource().equals(start)){
      //选择游戏难度
      Object[] possibleValues = {BubbleShooter.LEVEL_EASY,BubbleShooter.LEVEL_MEDIUM, BubbleShooter.LEVEL_HARD}; //用户的选择项目
      //Object selectedValue = JOptionPane.showInputDialog(null, "Please choose the level of the game", "choose", JOptionPane.QUESTION_MESSAGE,null, possibleValues, possibleValues[0]);
      Object selectedValue = JEnhancedOptionPane.showInputDialog("Please choose the level of the game", possibleValues, "choose");
      if (selectedValue == null) {
        return;
      }
      String level =(String)selectedValue;
      //跳转到下一界面
      new BubbleShooter().Start(level);
    	 this.dispose();
      //new BubbleShooter();
      //关闭当前界面
        //dispose();
    }else if(e.getSource().equals(exit)){
      dispose();
    }else if(e.getSource().equals(help)){
    	BubbleShooter.readFile(BubbleShooter.LEVEL_EASY);
       BubbleShooter.readFile(BubbleShooter.LEVEL_MEDIUM);
       BubbleShooter.readFile(BubbleShooter.LEVEL_HARD);
       String msg = "最高分：简单("+ BubbleShooter.easyMaxScore +")，中等("+BubbleShooter.mediumMaxScore +")，困难("+ BubbleShooter.hardMaxScore+")";
      JOptionPane.showMessageDialog( null, msg);
    }

  }


	public static void main(String[] args) {

		new Begin();

	}

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub

  }




  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub

  }




  @Override
  public void mouseEntered(MouseEvent e) {
    // 鼠标移入
    if(e.getSource().equals(start)){//e指一个事件。e.getSource()获取事件
      //如果鼠标移入到（start）组件（图片按钮）
      start.setEnabled(true);
    }else if(e.getSource().equals(help)){
      help.setEnabled(true);
    }else if(e.getSource().equals(exit)){
      exit.setEnabled(true);
    }
  }




  @Override
  public void mouseExited(MouseEvent e) {
    //鼠标移出
      if(e.getSource().equals(start)){
        start.setEnabled(false);
    }else if(e.getSource().equals(help)){
      help.setEnabled(false);
    }else if(e.getSource().equals(exit)){
      exit.setEnabled(false);
    }
  }
}