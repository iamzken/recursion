import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 将手续费作为一笔交易，如果拼凑不出来，则拆分剩余明细中金额最大的那笔交易
 * 该方案理论上可能存在需要拆分多笔交易的情况
 * @author kenan.zhang
 * @date 2017-08-23
 */
public class Calculator2 {
	
	public static void recursion(List<Map<String,Object>> detailList,LinkedList<Map<String,Object>> tmpList,LinkedList<Object> resultList,Map<String,Object> targetSummary,int startIndex){
		
		for (int i = startIndex; i < detailList.size(); i++) {
			Map<String,Object> currentDetail = detailList.get(i);
			BigDecimal summaryMoney = (BigDecimal) targetSummary.get("summaryMoney");
			BigDecimal detailMoney = (BigDecimal) currentDetail.get("detailMoney");
			if(detailMoney.compareTo(summaryMoney) != 1){
				if(detailMoney.compareTo(summaryMoney) == 0){
					tmpList.add(currentDetail);
					resultList.add(new LinkedList<Map<String,Object>>(tmpList));
					tmpList.remove(currentDetail);
					resultList.add(targetSummary);
					//从源数据列表中去掉已经使用过的数据
					detailList.removeAll((Collection<?>) resultList.get(0));
					break;
				}else if(detailMoney.compareTo(summaryMoney) == -1){
					tmpList.add(currentDetail);
					targetSummary.put("summaryMoney", summaryMoney.subtract(detailMoney));
					recursion(detailList, tmpList, resultList, targetSummary, i+1);
					//值还原
					targetSummary.put("summaryMoney", ((BigDecimal)(targetSummary.get("summaryMoney"))).add(detailMoney));
					tmpList.remove(currentDetail);
					//只取第一对符合条件的数据,第一个元素代表明细数据列表,第二个元素代表对应的汇总数据
					if(resultList.size() == 2){
						//从源数据列表中去掉已经使用过的数据
						detailList.removeAll((Collection<?>) resultList.get(0));
						break;
					}
				}
			}else{
				break;
			}
		}
		
	}
	
	
	public static LinkedList<Object> getResult(Map<String,List<Map<String,Object>>> sourceData){
		
		List<Map<String,Object>> detailList = sourceData.get("detail");
		List<Map<String,Object>> summaryList = sourceData.get("summary");
		DetailValueComparator detailComparator = new DetailValueComparator();
		SummaryValueComparator summaryComparator = new SummaryValueComparator();
		Collections.sort(detailList,detailComparator);
		Collections.sort(summaryList,summaryComparator);
		int startIndex = 0;
		List<Map<String,Object>> remainSummaryList = new LinkedList<Map<String,Object>>();
		LinkedList<Object> returnList = new LinkedList<Object>();
		for(int i=0;i<summaryList.size();i++){
			Map<String,Object> currentSummary = summaryList.get(i);
			LinkedList<Object> resultList = new LinkedList<Object>();
			LinkedList<Map<String,Object>> tmpList = new LinkedList<Map<String,Object>>();
			recursion(detailList, tmpList, resultList,currentSummary, startIndex);
			if(resultList.size() == 0){
				remainSummaryList.add(currentSummary);
			}
			returnList.addAll(resultList);
		}
	
		Collections.sort(detailList,detailComparator);
		for(int i=0;i<remainSummaryList.size();i++){
			//对最大的一笔交易进行拆分
			int lastIndex = detailList.size()-1;
			Map<String,Object> lastDetail = detailList.get(lastIndex);
			Map<String,Object> targetSummary = remainSummaryList.get(i);
			LinkedList<Map<String,Object>> myTmpList = new LinkedList<Map<String,Object>>();
			BigDecimal lastDetailMoney = (BigDecimal) lastDetail.get("detailMoney");
			BigDecimal summaryMoney = (BigDecimal) targetSummary.get("summaryMoney");
			BigDecimal sum = new BigDecimal(0);
			//待删除列表
			List<Map<String,Object>> listToRemove = new LinkedList<Map<String,Object>>();
			for(int j=0;j<detailList.size();j++){
				myTmpList.add(detailList.get(j));
				Object t = detailList.get(j).get("detailMoney");
				BigDecimal detailMoney = new BigDecimal(t.toString());
				listToRemove.add(detailList.get(j));
				sum = sum.add(detailMoney);
				if(!less(sum, summaryMoney)){
					listToRemove.remove(detailList.get(j));
					myTmpList.remove(detailList.get(j));
					Map<String,Object> m = new HashMap<String,Object>();
					//gap代表拼凑的金额
					BigDecimal gap = summaryMoney.subtract(sum.subtract(detailMoney));
					m.put("detailMoney", gap);
					m.put("ifBorrow", "yes");
					//被拆交易的序列号
					m.put("borrowSerial", lastDetail.get("serialNo"));
					myTmpList.add(m);
					returnList.add(myTmpList);
					returnList.add(targetSummary);
					//更新被拆分交易的金额
					lastDetail.put("detailMoney", lastDetailMoney.subtract(gap));
					detailList.removeAll(listToRemove);
					if(equal(sum, summaryMoney)){
						detailList.remove(lastDetail);
					}
					//重新排序,保证每次取到最大的交易明细进行拆分
					Collections.sort(detailList, detailComparator);
					break;
				}
			}
		}
		return returnList;
	}
	
	public static boolean greater(BigDecimal d1,BigDecimal d2){
		return d1.compareTo(d2)==1;
	}
	
	public static boolean equal(BigDecimal d1,BigDecimal d2){
		return d1.compareTo(d2)==0;
	}
	
	public static boolean less(BigDecimal d1,BigDecimal d2){
		return d1.compareTo(d2)==-1;
	}
	
	public static void main(String[] args) {
		
		Map<String,List<Map<String,Object>>> sourceData = new HashMap<String,List<Map<String,Object>>>();
		List<Map<String,Object>> detailList = new LinkedList<Map<String,Object>>();
		Map<String, Object> m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(69));
		m1.put("serialNo", 69);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(35));
		m1.put("serialNo", 35);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(29));
		m1.put("serialNo", 29);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(26));
		m1.put("serialNo", 26);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(18));
		m1.put("serialNo", 18);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(12));
		m1.put("serialNo", 12);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(46));
		m1.put("serialNo", 46);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(92));
		m1.put("serialNo", 92);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(96));
		m1.put("serialNo", 96);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(75));
		m1.put("serialNo", 75);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(58));
		m1.put("serialNo", 58);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(34));
		m1.put("serialNo", 34);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(120));
		m1.put("serialNo", 120);
		detailList.add(m1);
		m1 = new HashMap<String, Object>();
		m1.put("detailMoney", new BigDecimal(32));
		m1.put("serialNo", 32);
		detailList.add(m1);
		sourceData.put("detail", detailList);
		
		List<Map<String,Object>> summaryList = new LinkedList<Map<String,Object>>();
		Map<String, Object> m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(104));
		m2.put("serialNo", 104);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(44));
		m2.put("serialNo", 44);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(58));
		m2.put("serialNo", 58);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(93));
		m2.put("serialNo", 93);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(73));
		m2.put("serialNo", 73);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(260));
		m2.put("serialNo", 260);
		summaryList.add(m2);
		m2 = new HashMap<String, Object>();
		m2.put("summaryMoney", new BigDecimal(110));
		m2.put("serialNo", 110);
		summaryList.add(m2);
		sourceData.put("summary", summaryList);

		DetailValueComparator detailComparator = new DetailValueComparator();
		SummaryValueComparator summaryComparator = new SummaryValueComparator();
		Collections.sort(detailList,detailComparator);
		Collections.sort(summaryList,summaryComparator);
		LinkedList<Object> result = getResult(sourceData);
		System.out.println(result);
	}
	
}