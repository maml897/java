package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test8
{
	public static void main(String[] args)
	{
		List<Model> list = new ArrayList<>();
		List<String> features = Arrays.asList("a", "b", "a");
		Map<String,List<String>> map=features.stream().collect(Collectors.mapping(x->x+x, Collectors.groupingBy(x->x)));
		System.out.println(map);
	}
}

