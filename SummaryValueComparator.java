import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Map;

public class SummaryValueComparator implements Comparator<Map<String, Object>> {
	public int compare(Map<String, Object> m, Map<String, Object> n) {
		return ((BigDecimal) m.get("summaryMoney")).compareTo((BigDecimal) n.get("summaryMoney"));
	}
}