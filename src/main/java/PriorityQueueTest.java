import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PriorityQueueTest {
	public static void main(String[] args) {
		List<Float> list =new ArrayList<>();
		
		for(float f=0;f<100000;f++){
			list.add(f);
		}
		
		
		long s=System.currentTimeMillis();
		System.out.println(key(list, 89762.5f,false));
		System.out.println("用时："+(System.currentTimeMillis()-s));
		
		s=System.currentTimeMillis();
		System.out.println(key1(list, 89762.5f,false));
		System.out.println("用时："+(System.currentTimeMillis()-s));
		
	}
	
	
	public static float key1(Collection<Float> values, float value, boolean... flag) {
		
		
		
		return -1;
	}
	
	public static float key(Collection<Float> values, float value, boolean... flag) {
		
		if (flag != null && flag.length > 0 && flag[0]) {
			float result = -1;
			for (Float f : values) {
				if (f <= value) {
					result = f;
				}
			}
			return result;

		} else {
			for (Float f : values) {// 取高
				if (f >= value) {
					return f;
				}
			}
			return -1;
		}
	}
}
