package com.siemens.ctbav.intership.shop.util.operator;

public class Interval {

	public static  Interval ONE_DAY=new Interval (new Long(86400000),1);

	public static  Interval ONE_WEEK= new Interval(new Long(ONE_DAY.value * 7),2);

	public static  Interval ONE_MONTH = new Interval(new Long(ONE_DAY.value * 30),3); // nu e chiar exact, in fine

	public static  Interval ONE_YEAR= new Interval(new Long(ONE_MONTH.value * 12),4);

	private final Long value;
	private final int rank;

	private Interval(Long value, int rank) {
		this.value = value;
		this.rank=rank;
	}

	private static Interval defaultInt=ONE_WEEK;
	
	public static void setDefault(Interval obj) {
			defaultInt = obj;
	}
	public static Interval getDefault(){
		return defaultInt;
	}
	
	public Long getValue()
	{
		return value;
	}
	public int getRank() {
		return rank;
	}
}
