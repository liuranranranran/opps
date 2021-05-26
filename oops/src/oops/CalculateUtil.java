package oops;

public class CalculateUtil {
    private static double a;
    private static double b;
    public static int mbX;
    public static int mbY;
    public static int minLen=10000;

    public static void calc(double x1,double y1,double x2,double y2){
        a=(y1-y2)/(x1-x2);
        b=y1-a*x1;
       // System.out.println(a);
       // System.out.println(b);
    }

    public static void getPoint(double x1,double y1,double len ,int vx,double mbx,double mby){
        double a1=a*a+1;
        double b1=2*a*(b-y1)-2*x1;
        double c1= (b-y1)*(b-y1)-(len*len-x1*x1);
        double x2=(-b1+Math.sqrt(b1*b1-4*a1*c1))*1/(2*a1);
        double y2= a*x2+b;
        double x3=(-b1-Math.sqrt(b1*b1-4*a1*c1))*1/(2*a1);
        double y3= a*x3+b;
        if(vx>=0&&mbx<x1&&x1<x2){
            mbX=(int)x3;
            mbY=(int)y3;
        }else if (vx<0&&mbx>x1&&x1>x3){
            mbX=(int)x2;
            mbY=(int)y2;
        }else if(y3>y2){
            mbX=(int)x3;
            mbY=(int)y3;
        }else {
            mbX=(int)x2;
            mbY=(int)y2;
        }
    }
    public static void setMinLen(int len,boolean force){
        if(len<minLen||force){
            minLen =len;
        }
    }
    public static void main(String[] args) {//240,154
      /*  CalculateUtil.calc(260,200,280,246);
        CalculateUtil.getPoint(260,200,50,0,280,246);
        System.out.println(mbX+","+mbY);*/

    }
}