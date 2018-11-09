package lambda;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class Main
{
	public static void main(String[] args)
	{
		
		Map<Long,Float> studentIDScores=new LinkedHashMap<>();
		studentIDScores.put(1l, 0.5f);
		studentIDScores.put(2l, 0.2f);
		studentIDScores.put(4l, 0.4f);
		studentIDScores.put(3l, 0.3f);
		
		List<Entry<Long,Float>> result = studentIDScores.entrySet().stream().sorted(Entry.<Long,Float>comparingByValue().reversed()).collect(Collectors.toList());

		
		Entry.comparingByKey();
		System.out.println(result);
	}
}
