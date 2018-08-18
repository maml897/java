package jingdu;

/**
 * 该例子说明需要double参数的不能随意传float类型
 * @author maml
 *
 */
public class Test {
	public static void main(String[] args) {
		float f=172.525f;
		test(f);
	}
	
	private static void test(double d){
		System.out.println(String.valueOf(d));
	}
}
