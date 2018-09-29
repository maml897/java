package lambda.collect;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CollectTest
{
	public static void main(String[] args)
	{
		Stream stream = Stream.of(1, 2, 3, 4).filter(p -> p > 2);
	}
}
