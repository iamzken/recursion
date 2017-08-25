import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 将手续费作为一笔交易，如果拼凑不出来，则拆分剩余明细中金额最大的那笔交易
 * @author kenan.zhang
 * @date 2017-08-23
 */
public class Calculator {
	
	public static void recursion(List<Integer> sourceList,LinkedList<Integer> tmpList,List<LinkedList<Integer>> resultList,Integer targetValue,int startIndex){
		
		for (int i = startIndex; i < sourceList.size(); i++) {
			Integer currentValue = sourceList.get(i);
			if(currentValue <= targetValue){
				if(currentValue.equals(targetValue)){
					tmpList.add(currentValue);
					resultList.add(new LinkedList<Integer>(tmpList));
					tmpList.remove(currentValue);
					//从源数据列表中去掉已经使用过的数据
					sourceList.removeAll(resultList.get(0));
					break;
				}else if(currentValue < targetValue){
					tmpList.add(currentValue);
					recursion(sourceList, tmpList, resultList, targetValue-currentValue, i+1);
					tmpList.remove(currentValue);
					//只取第一个符合条件的数据组
					if(resultList.size() == 1){
						//从源数据列表中去掉已经使用过的数据
						sourceList.removeAll(resultList.get(0));
						break;
					}
				}
			}else{
				break;
			}
		}
		
	}
	
	public static Map<Integer,LinkedHashMap<Integer,List<Integer>>> getResult(List<Integer> sourceList,List<Integer> targetValues){
		Collections.sort(sourceList);
		Collections.sort(targetValues);
		int startIndex = 0;
		List<Integer> remainTargetValues = new LinkedList<Integer>();
		Map<Integer,LinkedHashMap<Integer,List<Integer>>> resultMap = new LinkedHashMap<Integer,LinkedHashMap<Integer,List<Integer>>>();
		for(int i=0;i<targetValues.size();i++){
			Integer currentValue = targetValues.get(i);
			List<LinkedList<Integer>> resultList = new LinkedList<LinkedList<Integer>>();
			LinkedList<Integer> tmpList = new LinkedList<Integer>();
			recursion(sourceList, tmpList, resultList,currentValue, startIndex);
			if(resultList.size() == 0){
				remainTargetValues.add(currentValue);
				resultMap.put(currentValue, null);
			}else{
				LinkedHashMap<Integer,List<Integer>> res = new LinkedHashMap<Integer,List<Integer>>();
				//为了区分该汇总交易是否通过某笔交易明细拆分拼凑而来：如果子map的key为Integer.MAX_VALUE，否则对应的是被拆分的交易明细
				res.put(Integer.MAX_VALUE, resultList.get(0));
				resultMap.put(currentValue, res);
			}
		}
		//对最大的一笔交易进行拆分
		int lastIndex = sourceList.size()-1;
		Integer lastValue = sourceList.get(lastIndex);
		for(int i=0;i<remainTargetValues.size();i++){
			Integer targetValue = remainTargetValues.get(i);
			List<Integer> myTmpList = new LinkedList<Integer>();
			Integer gap = lastValue - sourceList.get(i);
			if(gap >= 0){
				myTmpList.add(sourceList.get(i));
				myTmpList.add(targetValue-sourceList.get(i));
				Collections.sort(myTmpList);
				LinkedHashMap<Integer,List<Integer>> res = new LinkedHashMap<Integer,List<Integer>>();
				//为了区分该汇总交易是否通过某笔交易明细拆分拼凑而来：如果子map的key为Integer.MAX_VALUE，否则对应的是被拆分的交易明细
				res.put(lastValue, myTmpList);
				resultMap.put(targetValue, res);
				sourceList.set(lastIndex, gap);
			}else{
				System.out.println(sourceList);
				for(int k=remainTargetValues.size()-1;k<sourceList.size();k++){
					myTmpList.add(sourceList.get(k));
				}
				myTmpList.add(lastValue);
				LinkedHashMap<Integer,List<Integer>> res = new LinkedHashMap<Integer,List<Integer>>();
				//为了区分该汇总交易是否通过某笔交易明细拆分拼凑而来：如果子map的key为Integer.MAX_VALUE，否则对应的是被拆分的交易明细
				res.put(lastValue, myTmpList);
				resultMap.put(targetValue, res);
			}
		}
		return resultMap;
	}
	
	//重载
	public static Map<Integer,LinkedHashMap<Integer,List<Integer>>> getResult(Integer[] source,Integer[] target){
		List<Integer> sourceList = new LinkedList<Integer>();
		sourceList.addAll(Arrays.asList(source));
		LinkedList<Integer> targetValues = new LinkedList<Integer>(Arrays.asList(target));
		return getResult(sourceList, targetValues);
	}
	public static void main(String[] args) {
		Integer[] source = new Integer[]{69, 35, 29, 26, 18, 12, 46, 92, 96, 75, 58, 34, 120, 1032,9000};
		Integer[] target = new Integer[]{104,44,58,93,73,260,110,10000};
		Map<Integer,LinkedHashMap<Integer,List<Integer>>> resultMap = getResult(source, target);
		System.out.println("结果resultMap是:"+resultMap);
		List<Integer> sourceList = new LinkedList<Integer>();
		sourceList.addAll(Arrays.asList(source));
		LinkedList<Integer> targetValues = new LinkedList<Integer>(Arrays.asList(target));
		Map<Integer,LinkedHashMap<Integer,List<Integer>>> resultMap2 = getResult(sourceList, targetValues);
		System.out.println("结果resultMap2是:"+resultMap2);
		
		
		
		
		/*Integer currentValue = 44;
		List<LinkedList<Integer>> resultList = new LinkedList<LinkedList<Integer>>();
		LinkedList<Integer> tmpList = new LinkedList<Integer>();
		Collections.sort(sourceList);
		recursion(sourceList, tmpList, resultList,currentValue, 0);*/
	}
	
}