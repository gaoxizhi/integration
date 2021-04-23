package net.gaox.lambda;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class list2map {

	// Main
	public static void main(String[] args) {
		
		List<Apple> appleList = new ArrayList<>();//存放apple对象集合

		Apple apple1 =  new Apple(1,"苹果1",new BigDecimal("3.25"),10);
		Apple apple12 = new Apple(1,"苹果2",new BigDecimal("1.35"),20);
		Apple apple2 =  new Apple(2,"香蕉",new BigDecimal("2.89"),30);
		Apple apple3 =  new Apple(3,"荔枝",new BigDecimal("9.99"),40);

		appleList.add(apple1);
		appleList.add(apple12);
		appleList.add(apple2);
		appleList.add(apple3);
		
		//id为key，apple对象为value，可以这么做：
		/**
		 * List -> Map
		 * 需要注意的是：
		 * toMap 如果集合对象有重复的key，会报错Duplicate key ....
		 *  apple1,apple12的id都为1。
		 *  可以用 (k1,k2)->k1 来设置，如果有重复的key,则保留key1,舍弃key2
		 */
		Map<Integer, Apple> appleMap = appleList.stream().collect(Collectors.toMap(Apple::getId, a -> a,(k1,k2)->k1));
		

		//List 以ID分组 Map<Integer,List<Apple>>
		Map<Integer, List<Apple>> groupBy = appleList.stream().collect(Collectors.groupingBy(Apple::getId));

		System.err.println("groupBy:"+groupBy);
		//{1=[Apple{id=1, name='苹果1', money=3.25, num=10}, Apple{id=1, name='苹果2', money=1.35, num=20}], 
		//2=[Apple{id=2, name='香蕉', money=2.89, num=30}], 3=[Apple{id=3, name='荔枝', money=9.99, num=40}]}
		
		//从集合中过滤出来符合条件的元素：

		//过滤出符合条件的数据
		List<Apple> filterList = appleList.stream().filter(a -> a.getName().equals("香蕉")).collect(Collectors.toList());

		System.err.println("filterList:"+filterList);
		//[Apple{id=2, name='香蕉', money=2.89, num=30}]
				

		//将集合中的数据按照某个属性求和:
		//计算 总金额
		BigDecimal totalMoney = appleList.stream().map(Apple::getMoney).reduce(BigDecimal.ZERO, BigDecimal::add);
		System.err.println("totalMoney:"+totalMoney);  //totalMoney:17.48
		
		//计算 数量
		int sum = appleList.stream().mapToInt(Apple::getNum).sum();
		System.err.println("sum:"+sum);  //sum:100
	}

}
