package oops;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;


public class BubbleShooter extends JPanel {
	public static void main(String[] args) {

		new BubbleShooter();

	}

	int mx;
	int my;
	int mbX = 280;
	int mbY = 740;
	public static String LEVEL_EASY="easy";
	public static String LEVEL_MEDIUM="medium";
	public static String LEVEL_HARD="hard";
	String level = LEVEL_EASY;//��Ϸ�Ѷ�
	boolean isAdd = false;//�Ƿ��Զ�����һ��С��
	Image ball1 = new ImageIcon("1.gif").getImage();
	Image ball2 = new ImageIcon("2.gif").getImage();
	Image ball3 = new ImageIcon("3.gif").getImage();
	Image ball4 = new ImageIcon("4.gif").getImage();
	Image ball5 = new ImageIcon("5.gif").getImage();
	Image ball6 = new ImageIcon("6.gif").getImage();
	Image ball7 = new ImageIcon("7.gif").getImage();
	Image ball8 = new ImageIcon("8.gif").getImage();
	Image[] images = { ball1, ball2, ball3, ball4, ball5, ball6, ball7, ball8 };

	ArrayList<Ball> balls = new ArrayList<Ball>(); // �������п���С��

	int index = 5;
	int lastIndex;
	Ball shootBall = new Ball(images[index], mbX, mbY);
	int taughX;
	int taughY;

	static int score = 0; // ����

	long time = 0; // ʱ��
	
	public void Start(String level){
	 BubbleShooter p = new BubbleShooter();
	 p.level = level;
	 p.time = System.currentTimeMillis();
	 JFrame frame = new JFrame();
	 frame.add(p);
	 frame.setSize(1200,820);
	 frame.setLocationRelativeTo(null);
	 frame.setVisible(true);
	 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	 if(!level.equals(LEVEL_EASY)){
		 final long sleepTime ;
		 if(LEVEL_MEDIUM.equals(level)){
			 sleepTime = 30000;//�еȵȼ�ʱ�� 30��
		 }else {
			 sleepTime = 10000;//���ѵȼ�ʱ�� 10��
		 }
		 Thread thread = new Thread(new Runnable(){
			 public void run(){
				 try {
					 Thread.sleep(sleepTime);
					 while (true){
						 p.isAdd = true;
						 Thread.sleep(sleepTime);
					 }
				 } catch (InterruptedException e) {
					 e.printStackTrace();
				 }
			 }});
		 thread.start();
	 }
	}
	public BubbleShooter() {
		balls(); // ����С;��ֵ������������С��ֵ

		addMouseMotionListener(new MouseAdapter() { // ������̨�ı仯
			public void mouseMoved(MouseEvent e) {
				mx = e.getX();
				my = e.getY();
				repaint();
			}

		});

		addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				double a = mx - 305;
				double b = my - 765;
				double degree = -Math.atan(a / b);

				if (degree > 1.4) // ���䱣��
					degree = 1.4;
				if (degree < -1.4)
					degree = -1.4;

				shootBall.panel = BubbleShooter.this;

				shootBall.degree = degree;

				if (!isOver()) {
					try {
						shootBall.start();
					} catch (IllegalThreadStateException e1) {
						JOptionPane.showMessageDialog(null, "Don't click before last ball is stoped");
					}

					repaint();
				}

				else {
				
					JOptionPane.showMessageDialog(null, "Game Over!");
					System.exit(0);
				}

			}
		});

	}

	@SuppressWarnings("deprecation")
	public void paint(Graphics g) { // �����
		super.paint(g);

		Image image = new ImageIcon("9.jpg").getImage(); // ����ͼ
		g.drawImage(image, 0, 0,1200,820, null);

	

		Image gunG = new ImageIcon("gunG.png").getImage(); // ������̨
		// *************************************

		if (balls.size() < 35) {
			addBalls(2);
		}
		//�Զ�����С��
		if (isAdd) {
			addBalls(1);
			isAdd = false;
		}

		for (int i = 0; i < balls.size(); i++) {
			g.drawImage(balls.get(i).image, balls.get(i).getX(), balls.get(i).getY(), 50, 50, null);
		}

		g.drawImage(gunG, 155, 700, 300, 100, null); // ��̨

		g.drawImage(shootBall.getImage(), mbX, mbY, 50, 50, null); // �˶���С��

		boolean taugh = isTaugh();

		if (taugh) {
			shootBall.stop();
			taughX = mbX;
			taughY = mbY;
			//System.out.println("taughX="+taughX+",taughY="+taughY);
			Ball theBall = new Ball(images[index], taughX, taughY, index);

			balls.add(theBall); // �Ӵ���С����ӵ�С������

			// ��ȥС��
			calculateScore(clearBalls(theBall) + removeBalls(theBall));

			mbX = 280;
			mbY = 740;
			index = (int) (Math.random() * 8); // ������һ������С��
			shootBall = new Ball(images[index]);

			taugh = false;

		}

		// �÷�
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString("" + score, 860, 550);

		// ʱ��

		long nowTime = Math.round((System.currentTimeMillis() - time) / 1000); // ��ǰ��������
		long minute = nowTime / 60;
		long second = nowTime - minute * 60;
		g.setColor(Color.BLACK);
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
		g.drawString(minute + ":" + second, 850, 650);

		// ***************************************
		// ����̨
		double a = mx - 305;
		double b = my - 765;
		double degreeR = -Math.atan(a / b);

		Graphics2D g2 = (Graphics2D) g;
		g2.rotate(degreeR, 305, 765);
		Image gunS = new ImageIcon("gunS.png").getImage();
		g.drawImage(gunS, 255, 595, this);

	}

	public void balls() {
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 11; j++) {
				int dex = (int) (Math.random() * 8);
				if (i % 2 == 0) {

					balls.add(new Ball(images[dex], 50 * j + 10, 50 * i, dex));
				} else {
					balls.add(new Ball(images[dex], 50 * j + 10, 50 * i, dex));
				}
			}
		}
	}

	public void addBalls(int num) {

		for (int i = 0; i < balls.size(); i++) { // ����С���½�һ��
			int theY = balls.get(i).getY();
			balls.get(i).setY(theY + 50*num);
		}

		// ���ϲ�����µ�С�򣬿�ʼ���С��û�д�dex�����³���
		for (int i = 0; i < num; i++) {
			for (int j = 0; j < 11; j++) {

				int dex = (int) (Math.random() * 8);
				if (i % num == 0) {
					balls.add(new Ball(images[dex], 50 * j + 10, 50 * i, dex));
				} else {
					balls.add(new Ball(images[dex], 50 * j + 10, 50 * i, dex));
				}
			}
		}

	}

	@SuppressWarnings("deprecation")
	public boolean isTaugh() { // �ض��Ƕȵ�С��ͣ��λ�ò�̫���룬��ǽ��б��С�����bug
		boolean flag =false;
		//�����������
		if(mbX<0){
			mbX =0;
		}
		if(mbX>525){
			mbX =525;
		}
		for (int j = 0; j < balls.size(); j++) {
			int distance = (int) (Math.sqrt(Math.pow(mbX - balls.get(j).x, 2) + Math.pow(mbY - balls.get(j).y, 2)));

				//CalculateUtil.setMinLen(distance,false);
				if(distance==50&&mbX>=0&&mbX<=525){
					flag = true;
				}
				if (distance < 50 ){//����С��50�����ཻ�������λ��
					//System.out.println("x1="+balls.get(j).x+",y1="+balls.get(j).y);
					//System.out.println("mbX="+mbX+",mbY="+mbY+",vx="+shootBall.vx);
					if(mbX==balls.get(j).x){
						mbY=balls.get(j).y+50;
					}else {
						//��ȡ�������λ��
						CalculateUtil.calc(balls.get(j).x,balls.get(j).y,mbX,mbY);
						CalculateUtil.getPoint(balls.get(j).x,balls.get(j).y,50,shootBall.vx,mbX,mbY);
						mbX = CalculateUtil.mbX;
						mbY = CalculateUtil.mbY ;
					}
					//�����������
					if(mbX<0){
						mbX =0;
						mbY = balls.get(j).y+(int) (Math.sqrt(2500-Math.pow( balls.get(j).x, 2) ));
					}
					if(mbX>525){
						mbX =525;
						mbY = balls.get(j).y+(int) (Math.sqrt(2500-Math.pow( 525-balls.get(j).x, 2) ));
					}
					flag = true;
					//System.out.println("---mbX="+mbX+",mbY="+mbY);
				}
			/*	double theDe = Math.atan(Math.abs(mbX - balls.get(j).x) / Math.abs(mbY - balls.get(j).y));

				if (distance < 50 && theDe < 0.74) { // ��ײ�Ӵ��Ż��������ض��Ƕȣ�
					System.out.println("x1="+balls.get(j).x+",y1="+balls.get(j).y);

					CalculateUtil.calc(balls.get(j).x,balls.get(j).y,mbX,mbY);
					CalculateUtil.getPoint(balls.get(j).x,balls.get(j).y,50);
					if (mbX < balls.get(j).x) // ��ײ����С����Ҫ΢��ͣ��λ��
						mbX = mbX - 5;
					if (mbX > balls.get(j).x)
						mbX = mbX + 5;
					if (mbY < balls.get(j).y)
						mbY = mbY - 5;
					if (mbY > balls.get(j).y)
						mbY = mbY + 5;
					System.out.println("mbX="+mbX+",mbY="+mbY);
					return true;
				}*/
			/*}

			if (Math.abs(mbX - balls.get(j).x) < 50 && distance < 50) {
				flag = true;
			}*/

		}

		return flag;
	}

	public boolean checkTaugh() { // �ж��Ƿ�ᷢ����ײ
		boolean flag =false;
		for (int j = 0; j < balls.size(); j++) {
			int distance = (int) (Math.sqrt(Math.pow(mbX - balls.get(j).x, 2) + Math.pow(mbY - balls.get(j).y, 2)));
			if (distance < 50 ) {
					flag = true;
				}
		}
		return flag;
	}

	// ����С��

	public int clearBalls(Ball theBall) {
		ArrayList<Ball> sameBalls = new ArrayList<Ball>();

		for (int m = 0; m < balls.size(); m++) {
			if (balls.get(m) == theBall)
				balls.get(m).setIsSame(true);
			else
				balls.get(m).setIsSame(false);
		}

		for (int times = 0; times < 3; times++) // ���Ӽ�������
			for (int i = 0; i < balls.size(); i++) {
				if (balls.get(i).getIsSame()) // ��������Χ�Ĳ�������ͬ��ɫ��С����Χ
					checkSame(balls.get(i));
			}

		int n = 0;
		for (int i = 0; i < balls.size(); i++) { // ��ͬ��ɫ��һ���С���һ��
			if (balls.get(i).getIsSame()) {
				sameBalls.add(balls.get(i));
				n++;
			}
		}

		if (sameBalls.size() >= 3) // ������������������
		{
			balls.removeAll(sameBalls);
			System.out.println("clearBalls :"+n);
			return n;
		} else
			return 0;

	}

	public void checkSame(Ball theBall) { // �����Χ������

		for (int i = 0; i < balls.size(); i++) { // ��������С�� �����ɫ��ͬ���һ�û�б�ǵģ�������룬����С��70 �ľͱ��
			if (!balls.get(i).getIsSame() && theBall.getImageIndex() == balls.get(i).getImageIndex()) {
				int distance = (int) (Math
						.sqrt(Math.pow(theBall.x - balls.get(i).x, 2) + Math.pow(theBall.y - balls.get(i).y, 2)));
				if (distance < 70) {
					balls.get(i).setIsSame(true);
				}
			}
		}

	}

	public int removeBalls(Ball theBall) { // ����10���ӳ���һ��bug���������С����δ����������
		ArrayList<Ball> notLinkedBalls = new ArrayList<Ball>();

		for (int k = 0; k < balls.size(); k++) {
			if (balls.get(k).y > 25)
				balls.get(k).setIsLinked(false);
			else
				balls.get(k).setIsLinked(true); // ������С����Ϊ����
		}

		for (int i = 0; i < balls.size(); i++) { // �Զ���С��Ϊ������������С�򣬽������Ӽ��
			if (!balls.get(i).getIsLinked()){
				checkLinked(balls.get(i));
			}
		}
		int num = 0;
		for (int j = 0; j < balls.size(); j++) {
			if (!balls.get(j).getIsLinked()) {
				notLinkedBalls.add(balls.get(j)); // �����Ϊû���ӵ�С�򣬷ŵ�һ��
				num++;
			}
		}

		if (notLinkedBalls.size() == 1 && notLinkedBalls.get(0) == theBall) // ���δ���ӵ�С��ֻ��һ�������ҵ��ڷ����С�򣬷���0�����Ҳ�����
			return 0;
		else {
			balls.removeAll(notLinkedBalls); // û���ӵ�С��һ���������������û�м�ʱ������bug
			System.out.println("removeBalls :"+num);
			return num;
		}


	}

	public void checkLinked(Ball theBall) {
		for (int i = 0; i < balls.size(); i++) {
			int distance = (int) (Math
					.sqrt(Math.pow(theBall.x - balls.get(i).x, 2) + Math.pow(theBall.y - balls.get(i).y, 2)));

			if (distance < 60) {
				balls.get(i).setIsLinked(true);
			}

		}

	}

	public void calculateScore(int number) { // 3-10; 4-20; 5-30; 6-40; 7-50
		score += number * (number - 2) * 10;
		writeMaxScore();
	}

	// �жϽ�������
	public boolean isOver() { // ����� ��280,740��

		int count = 0;
		for (int k = 0; k < balls.size(); k++) {
			double d = Math.sqrt(Math.pow(balls.get(k).x - 280, 2) + Math.pow(balls.get(k).y - 740, 2));
			if (d < 80.0 && Math.abs(balls.get(k).x - 280) < 60.0)
				return true;
			if (d < 80.0)
				count++;

		}

		if (count >= 3)
			return true;
		else
			return false;
	}
	//�����ʷ��ߵ÷֣��������ͨ����ȡ�ļ�����ֵ
	public static int easyMaxScore;
	public static int mediumMaxScore;
	public static int hardMaxScore;
	//���ļ�����ȡ��ʷ��߷�
	public static void readFile(String level){
		File file = new File(level+"MaxScore.txt");
		//����ļ������ڣ��ļ���������Զ������ļ�
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//��ȡ�ļ�
		BufferedReader br;
		try {
			br = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(file), "UTF-8"));
			if(LEVEL_EASY.equals(level)){
				easyMaxScore = br.read();
			}else if(LEVEL_MEDIUM.equals(level)){
				mediumMaxScore = br.read();
			}else if(LEVEL_HARD.equals(level)){
				hardMaxScore = br.read();
			}
			br.close();

		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeMaxScore() {
		if(level.equals(LEVEL_EASY)){
			if (BubbleShooter.score > easyMaxScore) {
				easyMaxScore = BubbleShooter.score;
				writeFile();
			}
		}else if(level.equals(LEVEL_MEDIUM)){
			if (BubbleShooter.score > mediumMaxScore) {
				mediumMaxScore = BubbleShooter.score;
				writeFile();
			}
		}else if(level.equals(LEVEL_HARD)){
			if (BubbleShooter.score > hardMaxScore) {
				hardMaxScore = BubbleShooter.score;
				writeFile();
			}
		}

	}
	public void writeFile() {

		File file = new File(level+"MaxScore.txt");
		//����ļ������ڣ��ļ���������Զ������ļ�
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		//д�ļ�
		try {

			BufferedWriter bw = new BufferedWriter(
					new OutputStreamWriter(
							new FileOutputStream(file), "UTF-8"));
			bw.write(score);//���ļ�д����߷�
			bw.close();//�ر���

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	  



}

// С���࣬�̳��߳�
class Ball extends Thread {
	double degree;
	final int v = 10;
	BubbleShooter panel;
	Image image;
	int x;
	int y;
	int imageIndex;
	boolean isSame = false;
	boolean isLinked = false;
    public  int vx;
	public Ball() {

	}

	public Ball(Image image) {
		this.image = image;
	}

	public Ball(Image image, int x, int y) {
		this.image = image;
		this.x = x;
		this.y = y;
	}

	public Ball(Image image, int x, int y, int imageIndex) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.imageIndex = imageIndex;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setIsLinked(boolean isLinked) {
		this.isLinked = isLinked;
	}

	public boolean getIsLinked() {
		return isLinked;
	}

	public void setIsSame(boolean isSame) {
		this.isSame = isSame;
	}

	public boolean getIsSame() {
		return isSame;
	}

	public String toString() {
		return "imageIndex:" + imageIndex + " x:" + x + " y:" + y;
	}

	public void run() {
		super.run();
		int vx = (int) (v * Math.cos(degree - Math.PI / 2));
		int vy = (int) (v * Math.sin(degree - Math.PI / 2));
		if(vy==0){
			vy=-1;
		}
		this.vx =vx;
		//System.out.println("vx="+vx+",vy="+vy);
		//CalculateUtil.setMinLen(10000,true);//����
		boolean flagX =false;
		boolean flagY =false;
		while (true) {
			//int sum =0;
			//����������������ٶ�
			/*if(CalculateUtil.minLen<100&&flag){
				System.out.println("minLen="+CalculateUtil.minLen);
				vx = (int) (5 * Math.cos(degree - Math.PI / 2));
				vy = (int) (5 * Math.sin(degree - Math.PI / 2));
			}*/
			//System.out.println("slow --vx="+vx );
			//System.out.println("slow --vy="+vy );
			panel.mbX += vx;
			panel.mbY += vy;
			//����Ƿ񼴽���ײ������С������ǣ�����
			if(panel.checkTaugh()){
				panel.mbX -= vx;
				panel.mbY -= vy;
				vx = (int) (5 * Math.cos(degree - Math.PI / 2));
				vy = (int) (5 * Math.sin(degree - Math.PI / 2));
				if(vy==0){
					vy=-1;
				}
				//System.out.println("slow vx="+vx );
				//System.out.println("slow vy="+vy );
				if(flagX){
					vx = -vx;
					this.vx =vx;
					//System.out.println("change2 VX="+vx);
				}
				if(flagY){
					vy = -vy;
					//System.out.println("change2 VY="+vy);
				}
				panel.mbX += vx;
				panel.mbY += vy;
			}
			if (panel.mbX <= 0 || panel.mbX > 525) { // ��ײ�ٶȵĸı�
				vx = -vx;
				this.vx =vx;
				flagX = true;
				if(panel.mbX < 0){
					panel.mbX = 0;
				}else if(panel.mbX > 525){
					panel.mbX = 525;
				}
				//System.out.println("change vx="+vx );
			}else {
				flagX = false;
			}

			if (panel.mbY <= 0 || panel.mbY > 800) {
				vy = -vy;
				flagY =true;
				if(panel.mbY < 0){
					panel.mbY = 0;
				}else if(panel.mbY > 800){
					panel.mbY = 800;
				}
				//System.out.println("change vy="+vy );
			}else {
				flagY = false;
			}
			try {
				sleep(10);
			} catch (InterruptedException e) {
				// e.printStackTrace();
			}
			panel.repaint();
		}

	}

}