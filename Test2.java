import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Test2 {

	public static void main(String[] args) {
		
		List<Map<String,Object>> l = new LinkedList<Map<String,Object>>();
		Map<String,Object> m1 = new HashMap<String,Object>();
		m1.put("k1", "v1");
		m1.put("k2", "v2");
		Map<String,Object> m2 = new HashMap<String,Object>();
		m2.put("k1", "v1");
		m2.put("k2", "v2");
		l.add(m1);
		l.add(m2);
		System.out.println(l);
		Map<String,Object> m3 = l.get(0);
		m3.put("k1", "111111111");
		System.out.println(l);
		Map<String,Object> m4 = new HashMap<String,Object>(m1);
		m4.put("k1", "44444444444444");
		System.out.println(l);
		System.out.println(m4);
	}
}
