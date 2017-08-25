import java.math.BigDecimal;

class MyMath {
	public static double add(double d1, double d2) { // 进行加法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.add(b2).doubleValue();
	}

	public static double sub(double d1, double d2) { // 进行减法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.subtract(b2).doubleValue();
	}

	public static double mul(double d1, double d2) { // 进行乘法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.multiply(b2).doubleValue();
	}

	public static double div(double d1, double d2, int len) {// 进行除法运算
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	public static double round(double d, int len) {
		// 进行四舍五入操作
		BigDecimal b1 = new BigDecimal(d);
		BigDecimal b2 = new BigDecimal(1);
		// 任何一个数字除以1都是原数字
		// ROUND_HALF_UP是BigDecimal的一个常量，表示进行四舍五入的操作
		return b1.divide(b2, len, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
}

public class BigDecimalDemo01 {
	public static void main(String[] args) {
		System.out
				.println("加法运算：" + MyMath.round(MyMath.add(10.345, 3.333), 1));
		System.out
				.println("乘法运算：" + MyMath.round(MyMath.mul(10.345, 3.333), 3));
		System.out.println("除法运算：" + MyMath.div(10.345, 3.333, 3));
		System.out
				.println("减法运算：" + MyMath.round(MyMath.sub(10.994, 3), 3));
		System.out
		.println("减法运算：" + MyMath.sub(10.994, 3));
		System.out.println(10.994-3);
	}
}