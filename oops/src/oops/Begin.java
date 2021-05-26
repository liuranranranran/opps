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
  //���ô���Ļ�������  ��С
  /**
   *  1.1�����ô���������Դ�С ���� �߿����� Ĭ�Ϲرհ�ť logoͼ��
    1.2�������������MainPanel��ʵ�ֱ���ͼƬ����

    2.ͼƬ��ť����
   */
	
  
  JLabel start,help,exit;

  JPanel MainPanel;

  public Begin() {//�޲ι��죬�������󡣲���main�����е���
    //2.2
    start = new JLabel(new ImageIcon("5.jpg"));//ImageIcon:ͼ��
    start.setBounds(350,320,150,40);
    start.setEnabled(false);//false��ťΪ��ɫ    
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


    /**1.ʵ�ֱ���ͼƬ����������*/
    MainPanel panel = new MainPanel();
    this.add(panel);

    //���ô���������Դ�С ���� �߿����� Ĭ�Ϲرհ�ť logoͼ��
    this.setSize(1200,730);//��С
    this.setLocationRelativeTo(null);//����
    this.setUndecorated(true);//�߿�����
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//Ĭ�Ϲر�
    this.setIconImage(new ImageIcon("Image/115.png").getImage());//logo
    this.setVisible(true);      
  }


  //2�������������MainPanel��ʵ�ֱ���ͼƬ����
  class MainPanel extends JPanel{//������MainPanel�࣬��MainFrame�е���
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



//�������������Ϊ��� implements MouseListener �󣬿�ݳ�����

  @Override
  public void mouseClicked(MouseEvent e) {
    //�����
    if(e.getSource().equals(start)){
      //ѡ����Ϸ�Ѷ�
      Object[] possibleValues = {BubbleShooter.LEVEL_EASY,BubbleShooter.LEVEL_MEDIUM, BubbleShooter.LEVEL_HARD}; //�û���ѡ����Ŀ
      //Object selectedValue = JOptionPane.showInputDialog(null, "Please choose the level of the game", "choose", JOptionPane.QUESTION_MESSAGE,null, possibleValues, possibleValues[0]);
      Object selectedValue = JEnhancedOptionPane.showInputDialog("Please choose the level of the game", possibleValues, "choose");
      if (selectedValue == null) {
        return;
      }
      String level =(String)selectedValue;
      //��ת����һ����
      new BubbleShooter().Start(level);
    	 this.dispose();
      //new BubbleShooter();
      //�رյ�ǰ����
        //dispose();
    }else if(e.getSource().equals(exit)){
      dispose();
    }else if(e.getSource().equals(help)){
    	BubbleShooter.readFile(BubbleShooter.LEVEL_EASY);
       BubbleShooter.readFile(BubbleShooter.LEVEL_MEDIUM);
       BubbleShooter.readFile(BubbleShooter.LEVEL_HARD);
       String msg = "��߷֣���("+ BubbleShooter.easyMaxScore +")���е�("+BubbleShooter.mediumMaxScore +")������("+ BubbleShooter.hardMaxScore+")";
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
    // �������
    if(e.getSource().equals(start)){//eָһ���¼���e.getSource()��ȡ�¼�
      //���������뵽��start�������ͼƬ��ť��
      start.setEnabled(true);
    }else if(e.getSource().equals(help)){
      help.setEnabled(true);
    }else if(e.getSource().equals(exit)){
      exit.setEnabled(true);
    }
  }




  @Override
  public void mouseExited(MouseEvent e) {
    //����Ƴ�
      if(e.getSource().equals(start)){
        start.setEnabled(false);
    }else if(e.getSource().equals(help)){
      help.setEnabled(false);
    }else if(e.getSource().equals(exit)){
      exit.setEnabled(false);
    }
  }
}