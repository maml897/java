import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Erfen {

	public static int binSearch(List<Integer> srcArray, int key) {
		int mid;
		int start = 0;
		int end = srcArray.size() - 1;
		while (start <= end) {
			mid = (end - start) / 2 + start;
			if (key < srcArray.get(mid)) {
				end = mid - 1;
			} else if (key > srcArray.get(mid)) {
				start = mid + 1;
			} else {
				return mid;
			}
		}
		return -1;

	}

	public static float binSearch(List<Float> srcArray, float key, int... range) {

		int start = 0;
		int end = 0;
		if (range == null || range.length == 0) {
			start = 0;
			end = srcArray.size() - 1;
		} else {
			start = range[0];
			end = range[1];
		}

		int mid = (end - start) / 2 + start;
		if (srcArray.get(mid) == key) {
			return mid;
		}
		if (start >= end) {
			return -1;
		} 
		else if (key > srcArray.get(mid)) 
		{
			if(mid==srcArray.size()-1){//取高，超出最高的返回-1
				return -1;
			}
			
			if(key<srcArray.get(mid+1)){
				return srcArray.get(mid+1);
			}
			return binSearch(srcArray,key, mid + 1, end);
		} 
		else if (key < srcArray.get(mid)) 
		{
			if(mid==0){//取高，小于最小的都算作最小的。。。
				return srcArray.get(mid);
			}
			if(key>srcArray.get(mid-1)){
				return srcArray.get(mid);
			}
			return binSearch(srcArray, key,start, mid - 1);
		}
		return -1;
	}

	public static void main(String[] args) {
		List<Float> list = new ArrayList<>();
		for (float f = 0; f < 750; f++) {
			list.add(f);
		}
		
		List<Float> list1 = new ArrayList<>();
		for (float f = 0; f < 750; f++) {
			list1.add(f+0.5f);
		}

		long s = System.currentTimeMillis();
		for(float f:list1){
			binSearch(list, f);
		}
		System.out.println("用时：" + (System.currentTimeMillis() - s));
		
		s = System.currentTimeMillis();
		for(float f:list1){
			key(list, f);
		}
		System.out.println("用时：" + (System.currentTimeMillis() - s));
	}
	
	
	public static float key(Collection<Float> values, float value, boolean... flag) {

		if (flag != null && flag.length > 0 && flag[0]) {// 取低
			float result = -1;
			for (Float f : values) {
				if (f <= value) {
					result = f;
				}
			}
			return result;

		} else {
			for (Float f : values) {
				if (f >= value) {
					return f;
				}
			}
			return -1;
		}
	}
}
