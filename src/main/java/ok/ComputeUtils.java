package ok;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.convert.Convert;

public class ComputeUtils {

	/**
	 * 平均分
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <T> double average(Collection<T> list, ToDoubleFunction<T> mapper) {
		return list.stream().mapToDouble(mapper).average().orElse(0);
	}

	/**
	 * 最高分
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <T> double top(Collection<T> list, ToDoubleFunction<T> mapper) {
		return list.stream().mapToDouble(mapper).max().orElse(0);
	}

	/**
	 * 最低分
	 * @param list
	 * @param mapper
	 * @return
	 */
	public static <T> double min(Collection<T> list, ToDoubleFunction<T> mapper) {
		return list.stream().mapToDouble(mapper).min().orElse(0);
	}

	/**
	 * 中位数
	 * @param list
	 * @param orderd
	 *            列表是否已经进行了排序，如果没有需要false
	 * @return
	 */
	public static double median(List<Double> list, boolean... orderd) {
		if (list.size() == 0) {
			return 0;
		}
		if (orderd == null || orderd.length == 0 || orderd[0]) {
			int number = list.size();
			double median = 0f;
			int x = number / 2;
			if (number % 2 == 0) {
				return (list.get(x - 1) + list.get(x)) / 2;
			} else {
				median = list.get(x);
			}
			return median;
		}

		// 无序列表快速得到中位数https://www.cnblogs.com/shizhh/p/5746151.html
		int heapSize = list.size() / 2 + 1;
		PriorityQueue<Double> heap = new PriorityQueue<>(heapSize);
		for (int i = 0; i < heapSize; i++) {
			heap.add(list.get(i));
		}

		for (int i = heapSize; i < list.size(); i++) {
			if (heap.peek() < list.get(i)) {
				heap.poll();
				heap.add(list.get(i));
			}
		}

		if (list.size() % 2 == 1) {
			return (double) heap.peek();
		} else {
			return (double) (heap.poll() + heap.peek()) / 2.0;
		}
	}

	/**
	 * 方差
	 * @param list
	 * @param mapper
	 * @param averages
	 * @return
	 */
	public static <T> double varience(Collection<T> list, ToDoubleFunction<T> mapper, double... averages) {
		double average = (averages == null || averages.length == 0) ? average(list, mapper) : averages[0];
		return list.stream().mapToDouble(item -> Math.pow(mapper.applyAsDouble(item) - average, 2)).average().orElse(0);
	}

	/***
	 * 标准差
	 * @param list
	 * @param mapper
	 * @param variences
	 * @return
	 */
	public static <T> double standard(Collection<T> list, ToDoubleFunction<T> mapper, double... variences) {
		if (variences != null && variences.length == 1) {
			return Math.sqrt(variences[0]);
		}
		double varience = varience(list, mapper);
		return Math.sqrt(varience);
	}

	/**
	 * 满分人数
	 * @param list
	 * @param predicate
	 * @return
	 */
	public static <T> int full(Collection<T> list, Predicate<T> predicate) {
		long count = list.stream().filter(predicate).count();
		return Convert.toInt(count);
	}

	/**
	 * 最简单的计算排序
	 * @param map 分数-数量,需要排序
	 * @return 分数-排名
	 */
	private static <T> Map<Double, Integer> computeOrderFromScoreCount(Map<Double, Long> map,boolean...ordereds) {
		boolean ordered = ordereds != null && ordereds.length>0&& ordereds[0];
		if(!ordered) {
			map = map.entrySet().stream().sorted(Collections.reverseOrder(comparingByKey()))
					.collect(toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));
		}
		
		Map<Double, Integer> studentIDOrder = new HashMap<>();
		long order = 1;
		for (Entry<Double, Long> entry : map.entrySet()) {
			studentIDOrder.put(entry.getKey(), Convert.toInt(order));
			order = order + entry.getValue();
		}
		return studentIDOrder;
	}

	/**
	 * 最简单的计算排序
	 * @param list 分数集合
	 * @return 分数-排名
	 */
	public static <T> Map<Double, Integer> computeOrder(List<Double> list) {
		Map<Double, Long> map = LambdaUtils.groupby(list, x -> x, Collectors.counting());// 每个分对应多少个学生
		return computeOrderFromScoreCount(map);
	}
	
	/**
	 * 个面具具体业务计算排名
	 * @param list 带分数的业务列表数据
	 * @param scoreFun 获取分数的逻辑
	 * @param orderCon 设置排名的逻辑
	 */
	public static <T> void computeOrder(List<T> list, Function<T, Double> scoreFun, BiFunction<T, Integer, T> action) {
		List<Double> scores = LambdaUtils.list2list(list, scoreFun);
		Map<Double, Integer> studentIDOrder = computeOrder(scores);
		list.forEach(item -> {
			int order=studentIDOrder.get(scoreFun.apply(item));
			action.apply(item, order);
		});
	}

	/**
	 *  差异系数，变异系数，最终保留默认2位
	 * @param standard 保留下属位数之后的,4位
	 * @param average 保留小数位数之后的，2位
	 * @return
	 */
	public static double cv(double standard, double average, int... scales) {
		int scale = (scales == null || scales.length == 0) ? 2 : scales[0];
		standard = MathUtils.round(standard, 4);
		average = MathUtils.round(average);
		return MathUtils.round(MathUtils.div(standard, average), scale);
	}

	/**
	 *  全距，最终保留2位
	 * @param max 保留下属位数之后的2位
	 * @param min 保留小数位数之后的，2位
	 * @return
	 */
	public static double range(double max, double min) {
		max = MathUtils.round(max);
		min = MathUtils.round(min);
		return MathUtils.round(MathUtils.sub(max, min));
	}

	/**
	 *  难度,默认保留4位
	 * @param average 保留下属位数之后的2位
	 * @param fullScore 保留小数位数之后的，2位
	 * @return
	 */
	public static double difficulty(double average, double fullScore, int... scales) {
		int scale = (scales == null || scales.length == 0) ? 4 : scales[0];
		average = MathUtils.round(average);
		fullScore = MathUtils.round(fullScore);
		return MathUtils.round(MathUtils.div(average, fullScore), scale);
	}

	/**
	 * 离均差,默认保留4位
	 * @param average1 保留下属位数之后的2位
	 * @param average2 保留小数位数之后的，2位
	 * @return
	 */
	public static double dfa(double average1, double average2, int... scales) {
		int scale = (scales == null || scales.length == 0) ? 4 : scales[0];
		average1 = MathUtils.round(average1);
		average2 = MathUtils.round(average2);
		return MathUtils.round(MathUtils.sub(average1, average2), scale);
	}

	/**
	 * 离均差率,默认保留4位
	 * @param average1 保留下属位数之后的2位
	 * @param average2 保留小数位数之后的，2位
	 * @return
	 */
	public static double dfaL(double dfa, double average2, int... scales) {
		return MathUtils.round(MathUtils.div(dfa, average2), scales);
	}

	
	
	/**
	 * 计算一个科目，或者一个小题的难度指数
	 * @param segments，科目10分一段，小题5分一段
	 * @param list 带计算学生成绩列表，包含两个属性，一个是总分t2totalscore，一个单科成绩t2score（或者一个是单科成绩，一个是小题成绩）
	 * @param t2totalscore 总成绩或者单科成绩
	 * @param t2score 单科成绩或者小题成绩
	 * @param full 总分或者单科满分
	 * @return
	 */
	public static <T> Map<Double,Double> difficultyIndex(List<Double> segments,List<T> list, Function<T, Double> t2totalscore, Function<T, Double> t2score, double full) {
		if (full == 0 || segments.isEmpty()) {
			return new LinkedHashMap<>();
		}
		
		Map<Double, List<T>> map=LambdaUtils.groupby(list,x->ToolUtils.key(segments, t2totalscore.apply(x), true,true));
		map=LambdaUtils.whole4group(map, segments, x->x, new ArrayList<>());
		
		Map<Double,Double> result=new LinkedHashMap<>();
		for(Map.Entry<Double,List<T>> entry:map.entrySet()) {
			double average = entry.getValue().stream().mapToDouble(x -> t2score.apply(x)).average().orElse(0);
			double difficulty = MathUtils.div(average, full);
			result.put(entry.getKey(), difficulty);
		}
		return result;
	}
	
	
	
	/**
	 * 计算score表，分数的排名，数量，sum（例如 130对应的是 (130,满分]的人数）
	 * @param objects //带计算得列表，要有分数数据
	 * @param scorefun  分数数据
	 * @return
	 */
	public static <T> List<Map<String, Object>> computeScore(List<T> objects, Function<T, Double> scorefun)
	{
		Map<Double, Long> group = LambdaUtils.groupby(objects, scorefun, Collectors.counting());
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

		int lastOrder = 1;
		int lastCount = 0;
		int lastSum = 0;

		for (double score : group.keySet())
		{
			Map<String, Object> map = new HashMap<>();

			int order = lastOrder + lastCount;//
			int sum = lastCount + lastSum;//
			int count = Integer.parseInt(String.valueOf(group.get(score)));

			map.put("Score", score);
			map.put("COrder", order);
			map.put("CCount", count);
			map.put("CSum", sum);
			result.add(map);

			lastOrder = order;
			lastCount = count;
			lastSum = sum;
		}
		return result;
	}
	
	
	
	
	/**
	 * 计算分数段，可以自定义收集方式,一般其他的用score表计算，班级的可以用这个方法计算因为班级没有score表
	 * @param top
	 * @param bottom
	 * @param step
	 * @param objects
	 * @param scoreExtractor
	 * @param c
	 * @param withSums
	 * @return
	 */
	@SafeVarargs
	public static <T, U> List<Map<String, Object>> computeSegments(double top, double bottom, double step, List<T> objects, Function<T, Double> scoreExtractor, Collector<T, ?, ?>... collectors)
	{
		Collector<T, ?, ?> collector = Collectors.counting();
		if (collectors != null && collectors.length > 0)
		{
			collector = collectors[0];
		}
		
		List<Double> temp = getSegments(top, bottom, step);
		List<Double> segments = new ArrayList<>(temp);
		if (!segments.contains(0d))
		{
			segments.add(0d);
		}
		Map<Double, ?> map = LambdaUtils.groupby(objects, x -> ToolUtils.key(segments, scoreExtractor.apply(x), false, false), collector);
		
		List<Map<String, Object>> list = new ArrayList<>();
		int size=segments.size();
		for (int i = 0; i <size;  i++)
		{
			double key = segments.get(i);
			Integer count = 0;
			Object o = map.get(key);
			if (o != null)
			{
				count = Integer.parseInt(o.toString());
			}

			Map<String, Object> onemap = new HashMap<>();
			onemap.put("id", key);
			onemap.put("count", count);
			if (i == 0)
			{
				onemap.put("desc", MathUtils.format(key) + "以上");
				onemap.put("min", key);
				onemap.put("max", Integer.MAX_VALUE);
			}
			else if (i == size-1)
			{
				onemap.put("desc", MathUtils.format(segments.get(i - 1)) + "以下");
				onemap.put("min", Integer.MIN_VALUE);
				onemap.put("max", segments.get(i - 1));
			}
			else
			{
				onemap.put("desc", MathUtils.format(key) + "-" + MathUtils.format(segments.get(i - 1)));
				onemap.put("min", key);
				onemap.put("max", segments.get(i - 1));
			}
			list.add(onemap);
		}

		// 累计
		int sum = 0;
		for (Map<String, Object> onemap : list)
		{
			int number = (int) onemap.get("count");
			sum += number;
			onemap.put("sum", sum);
		}
		return list;
	}

	/**
	 * 计算分数段，需要基础表 score
	 * @param top
	 * @param bottom
	 * @param step
	 * @param objects
	 * @param scoreExtractor
	 * @param countExtractor
	 * @param withSums
	 * @return
	 */
	public static <T, U> List<Map<String, Object>> computeSegments(double top, double bottom, double step, List<T> objects, Function<T, Double> scoreExtractor, ToIntFunction<T> countExtractor)
	{
		List<Map<String, Object>> list = computeSegments(top, bottom, step, objects, scoreExtractor, Collectors.summingInt(countExtractor));
		return list;
	}
	
	
	/**
	 * 获取分段
	 * @param from
	 *            开始值
	 * @param to
	 *            结束值
	 * @param step
	 *            步长
	 * @param addZero
	 *            是否加0，默认不加
	 * @return
	 */
	private static List<Double> getSegments(double from, double to, double step, boolean... addZeros)
	{

		boolean addzreo = false;
		if (addZeros != null && addZeros.length > 0)
		{
			addzreo = addZeros[0];
		}

		if (from == to)
		{
			if (addzreo && from != 0)
			{
				return Arrays.asList(from, 0d);
			}
			return Arrays.asList(from);
		}

		List<Double> list = new ArrayList<>();
		if (from > to)
		{
			while (from > to)
			{
				list.add(from);
				from = from - step;
			}
			if (!list.contains(to))
			{
				list.add(to);
			}

			if (addzreo && !list.contains(0d))
			{
				list.add(0d);
			}

		}
		else
		{
			while (from < to)
			{
				list.add(from);
				from = from + step;
			}
			if (!list.contains(to))
			{
				list.add(to);
			}

			if (addzreo && !list.contains(0d))
			{
				list.add(0, 0d);
			}
		}

		return list;
	}
	
	
	
	//整个科目小题分组
	public static <T> List<Map<String, Object>> subjectQuestionGroup(long subjectID,String subjectName,List<T> questions,ToDoubleFunction<T> scoreFun,Function<T,Integer> typeFun,Function<T,String> titleFun,List<Map<String, Object>> list) {
		//questions=LambdaUtils.filter(questions,  x->x.getLevel().length()==8);
		
		List<Map<String, Object>> groupby=ComputeUtils.questionGroup(questions,scoreFun,typeFun,titleFun,list);
//		for(Map<String, Object> item:groupby) {
//			item.put("subjectName", subjectName);
//			item.put("subjectID", subjectID);
//		}
		Map<Integer, Map<Integer, Map<String, Object>>> map=LambdaUtils.groupbymap(groupby, x->Convert.toInt(x.get("id")), x->Convert.toInt(x.get("questionType")));
		
		List<Map<String, Object>> result =new ArrayList<>();
		
		for(int id:map.keySet()) {
			
		}
		
//		for(int type:types) {
//			map.get(type);
//		}
		return groupby;
	}
	
	
	/**
	 * 用于饼状图得小题难度区分度统计
	 * @param questions
	 * @param scoreFun
	 * @param typeFun
	 * @param titleFun
	 * @param list
	 * @return
	 */
	public static <T> Map<String, Map<Integer, Map<String, Object>>> questionGroupInfo(List<T> questions,ToDoubleFunction<T> scoreFun,Function<T,Integer> typeFun,Function<T,String> titleFun,List<Map<String, Object>> list) {
		List<Map<String, Object>> groupby=ComputeUtils.questionGroup(questions,scoreFun,typeFun,titleFun,list);
		Map<String, Map<Integer, Map<String, Object>>> map=LambdaUtils.groupbymap(groupby, x->Convert.toStr(x.get("value")), x->Convert.toInt(x.get("questionType")));
		return map;
	}

	/**
	 * // 小题分组，主要用于难度和区分度分组+主观题，客观题，总体分组
	 * @param seQuestions 小题
	 * @param scoreFun 分数
	 * @param typeFun 主客观
	 * @param titleFun 题目
	 * @param predicates 划分标准
	 * @return [难题-主观题；难题-客观题；难题-合计；简单题-主观题....]
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<Map<String, Object>> questionGroup(List<T> seQuestions,ToDoubleFunction<T> scoreFun,Function<T,Integer> typeFun,Function<T,String> titleFun,List<Map<String, Object>> predicates)
	{
		//seQuestions=LambdaUtils.filter(seQuestions,  x->x.getLevel().length()==8);
		
		List<Integer> types=Arrays.asList(1,0,2);//客观题，主观题，合计
		
		Map<Integer, Double> scoremap=LambdaUtils.groupby(seQuestions, typeFun,Collectors.summingDouble(scoreFun));//主客观题得分数，小题累加，可能超过满分（有选做题）
		scoremap=LambdaUtils.whole4group(scoremap, types, x->x, 0d);
		scoremap.put(2, seQuestions.stream().mapToDouble(scoreFun).sum());
		
		Map<Integer, Long> numbermap=LambdaUtils.groupby(seQuestions, typeFun,Collectors.counting());//主客观题小题数量
		numbermap=LambdaUtils.whole4group(numbermap, types, x->x, 0l);
		numbermap.put(2, Convert.toLong(seQuestions.size()));
		
		List<Map<String, Object>> result= new ArrayList<>();
		for (Map<String, Object> map : predicates)
		{
			@SuppressWarnings("rawtypes")
			List<T> questions = LambdaUtils.filter(seQuestions, (Predicate) map.get("predicate"));
			if (questions == null)
			{
				questions = new ArrayList<>();
			}
			
			Map<Integer, List<T>> group=LambdaUtils.groupby(questions, typeFun);//主观题和客观题
			group=LambdaUtils.whole4group(group,types, x->x, new ArrayList<>());
			group.put(2, questions);
			
			for(int type:group.keySet()) {
				List<T> typequestions=group.get(type);
				
				Map<String, Object> item =new HashMap<>(map);
				item.put("questionType", type);
				item.put("questionTypeName", "合计");
				if(type!=2) {
					item.put("questionTypeName", type==0?"非客观题":"客观题");
				}
				
				double score= typequestions.stream().mapToDouble(scoreFun).sum();
				double totalScoreType= scoremap.containsKey(type)?scoremap.get(type):0;
				item.put("score",score);
				item.put("totalScore",totalScoreType);
				item.put("scoreRate", MathUtils.divPercent(score, totalScoreType));
				
				long totalNumberType=numbermap.containsKey(type)?numbermap.get(type):0;
				item.put("number", typequestions.size());
				item.put("totalNumber", totalNumberType);
				item.put("numberRate", MathUtils.divPercent(typequestions.size(), totalNumberType));
				
				item.put("questionNames", LambdaUtils.list2list(typequestions, titleFun));
				
				item.remove("predicate");
				result.add(item);
			}
		}
		return result;
	}
	
	
	
	//////////////////////////////////////////////////////////////////////下面的待完善////////////////////////////////////////////////////////////////////////////////////////
	
	
	
	//客观题选项统计，主观题得分统计
	//学生小题得分也可以用score表，这样统计每个小题的得分会简单，客观题存储另外一个表格？
	//均衡发展
	//缺考
	
	/**
	 * 获取一个小题的区分度
	 * @param orderedStudentIds 排序好的的待计算的学生列表，一般是按照总分或者单科总分排序
	 * @param ts 一般是指 questionStudent
	 * @param t2studentID  
	 * @param t2questionID
	 * @param t2score
	 * @param score 小题满分
	 * @return  奇葩，statistics是去除0分的人数，orderedStudentIds是包含0分的
	 */
	public static <T> double[] discrimination(List<Long> orderedStudentIds, List<T> ts, Function<T, Long> t2studentID, Function<T, Float> t2score, float score) {
		int number = orderedStudentIds.size();
		if (number == 0) {
			return new double[] { 0, 0, 0, 0, 0 };
		}

		// 这里实现的有问题，hnum和lnum应该是一样的，比如100个人，那么前27% 和后27%是一样的都是 27个人。
		int hnum = number * 27 / 100;
		int lnum = number - number * 73 / 100;
		if (hnum + lnum > 3 && lnum > 0 && hnum > 0) {
			// 前27%
			List<Long> hstudentList = CollUtil.sub(orderedStudentIds, 0, hnum);
			Set<Long> sets = new HashSet<>(hstudentList);
			List<T> hs = LambdaUtils.filter(ts, x -> sets.contains(t2studentID.apply(x)));
			double havg = hs.stream().mapToDouble(x -> t2score.apply(x)).average().orElse(0);

			// 后27%
			List<Long> lstudentList = CollUtil.sub(orderedStudentIds, number - lnum, number);
			Set<Long> sets1 = new HashSet<>(lstudentList);
			List<T> ls = LambdaUtils.filter(ts, x -> sets1.contains(t2studentID.apply(x)));
			double lavg = ls.stream().mapToDouble(x -> t2score.apply(x)).average().orElse(0);

			double result = (havg - lavg) / score;
			return new double[] { hnum, havg, lnum, lavg, result };
		}

		return new double[] { 0, 0, 0, 0, 0 };
	}

	
	//信度
	void reliability(){
		
	}
	
	
	
	
	public static void main(String[] args) {
		System.out.println(getSegments(50, 10, 10, false));
	}
}