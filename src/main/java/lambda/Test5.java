package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test5
{
	public static void main(String[] args)
	{

		List<Model> list = new ArrayList<>();

		Model a = new Model(1, "a");
		Model b = new Model(2, "b");

		List<Model> features = Arrays.asList(a, b);
		
		List l = features.stream().map((n) -> {
			Map<String, Object> map = new HashMap<>();
			map.put("n", n);

			return map;
		}).collect(Collectors.toList());

		System.out.println(l);
		
		
	}

}
