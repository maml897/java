package lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Test7
{
	public static void main(String[] args)
	{
		List<Model> list = new ArrayList<>();

		Model a = new Model(1, "a");
		Model b = new Model(2, "b");

		List<Model> features = Arrays.asList(a, b);

		Map<String, Object> map = features.stream().map(x -> {

			return new Model1(x.getId(), x.getId() + "");

		}).collect(Collectors.toMap(x -> x.getName1(), x -> x.getId1()));
		System.out.println(map);
	}
}

