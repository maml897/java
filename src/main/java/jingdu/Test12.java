package jingdu;

public class Test12
{
	public static void main(String[] args) {
        double x= 3.0000000000000003;//double比较，小数点后有效位:17-(整数位)。总有效位为17位
        double y= 30.000000000000003;//17位数字
        double z= 300.00000000000003;
        double w= 3000.0000000000003;

        System.out.println(x==3);
        System.out.println(y==30);
        System.out.println(z==300);
        System.out.println(w==3000);
        System.out.println("--------------------分界线-------------------");
        //小数后都加一个0
        double x1= 3.00000000000000003;//18位数字
        double y1= 30.0000000000000003;
        double z1= 300.000000000000003;
        double w1= 3000.00000000000003;
        
        System.out.println(x1==3);
        System.out.println(y1==30);
        System.out.println(z1==300);
        System.out.println(w1==3000);
    }
}
