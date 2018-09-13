package lambda.method;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class SupplierTest
{
	// 构造器
	private static SupplierTest construntorRef(Supplier<SupplierTest> sup)
	{
		SupplierTest p = sup.get();
		return p;
	}

	// 静态方法
	private static void print(String s)
	{
		System.out.println(s);
	}

	public static void main(String[] args)
	{
		SupplierTest p = construntorRef(SupplierTest::new);
		System.out.println(p);

		Arrays.asList("aa", "bb", "cc").forEach(SupplierTest::print);
		
		//对象
		List<String> list =new ArrayList<>();
		Arrays.asList("aa","bb","cc").forEach(list::add);
		System.out.println(list);
		
		
		//类的任意对象的实例方法引用(很怪异)
		String[] strs={"zzaa","xxbb","yycc"};
	    Arrays.sort(strs,String::compareToIgnoreCase);//OK
	    System.out.println(Arrays.asList(strs));
	    File[] files = new File("C:").listFiles(File::isHidden); // OK
        
	}

}
