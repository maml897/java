package lambda;

import java.util.ArrayList;
import java.util.List;

public class List2array
{
	public static void main(String[] args)
	{
		List<Long> list = new ArrayList<Long>();  

		list.add(1L);  
		list.add(2L);  
		list.add(3L);  
		System.out.println(list);  

		long [] array = list.stream().mapToLong(t->t.longValue()).toArray(); 
		
		System.out.println(array[0]);
		System.out.println(array[1]);
		System.out.println(array[2]);
	}
}
