import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author kenan.zhang
 * @date 2017-08-29
 */
public class Calculator3 {
	
	public static List<Map<Integer,List<Integer>>> patch(List<Integer> source,List<Integer> target){
		
		List<Map<Integer,List<Integer>>> result = new LinkedList<Map<Integer,List<Integer>>>();
		//找出最大的那笔交易进行拆分
		Integer max = Collections.max(source);
		source.remove(max);
		for(int i=0;i<target.size();i++){
			Integer t = target.get(i);
			int start = 0;
			//偏移量
			int shift= 0;
			//获取最接近汇总数据的明细数据
			int sum = 0;
			for(int j=0;j<source.size();j++){
				Integer s = source.get(j);
				if(sum==0 && t-s < 0){
					start++;
					continue;
				}else{
					sum += s;
					if(sum > t){
						break;
					}
					shift++;
				}
			}
			List<Integer> sub = source.subList(start, start+shift);
			Map<Integer,List<Integer>> item = new HashMap<Integer,List<Integer>>();
			item.put(t, new LinkedList<Integer>(sub));
			result.add(item);
			source.removeAll(sub);
		}
		//对明细数据总和不等于汇总数据的item进行拼凑
		for(Map<Integer,List<Integer>> item:result){
			Iterator<Entry<Integer, List<Integer>>> iter = item.entrySet().iterator();
			Entry<Integer, List<Integer>> entry = iter.next();
			int sum = 0;
			List<Integer> v = entry.getValue();
			Integer k = entry.getKey();
			for(int i=0;i<v.size();i++){
				sum += v.get(i);
			}
			if(sum < k){
				v.add(k-sum);
			}
		}
		return result;
		
	}
	
	public static void main(String[] args) {
		Integer[] source = new Integer[]{1032, 69, 35, 29, 26, 18, 12, 46, 92, 96, 75, 58, 34, 120,9000};
		Integer[] target = new Integer[]{104,44,58,93,73,260,110,10000};
		List<Map<Integer,List<Integer>>> result = patch(new ArrayList<Integer>(Arrays.asList(source)),new ArrayList<Integer>(Arrays.asList(target)));
		System.out.println(result);
	}
	
}