import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Test {

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
		m2.put("summaryMoney", new BigDecimal(61));
		m2.put("serialNo", 61);
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

		System.out.println(sourceData);
		
	}
}
