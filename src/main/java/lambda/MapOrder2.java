package lambda;

import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class MapOrder2
{
	public static void main(String[] args)
	{
		
		Map<Double, Long> studentIDScores=new LinkedHashMap<>();
		studentIDScores.put(0.5d, 1l);
		studentIDScores.put(10d, 112l);
		studentIDScores.put(60d, 4l);
		studentIDScores.put(20d, 4l);
		studentIDScores.put(30d, 4l);
		studentIDScores.put(50d, 4l);
		
		Map<Double, Long> result=studentIDScores.entrySet().stream().sorted(Entry.<Double,Long>comparingByKey().reversed()).collect(Collectors.toMap(x->x.getKey(), x->x.getValue(), (key1, key2) -> key2, LinkedHashMap::new));
		
		for(double d:result.keySet()){
			System.out.println(d);
		}
		
		Map<Double, Long> result1=studentIDScores.entrySet().stream().sorted(Comparator.comparingDouble(x->(Double)((Entry) x).getKey()).reversed()).collect(Collectors.toMap(x->x.getKey(), x->x.getValue(), (key1, key2) -> key2, LinkedHashMap::new));
		for(double d:result1.keySet()){
			System.out.println(d);
		}

	}
}
