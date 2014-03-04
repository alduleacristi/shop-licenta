package com.siemens.ctbav.intership.shop.util.operator;

public enum Interval {
	ONE_DAY(86400000,1),
	ONE_WEEK(ONE_DAY.val * 7,2),
	ONE_MONTH(ONE_DAY.val * 30,3),
	ONE_YEAR(ONE_DAY.val * 365,4),
	DEFAULT(ONE_DAY);
	

	private  long val;
	private  int rank;
	
	private Interval(long val, int rank) {
		this.val = val;
		this.rank = rank;
	}
	
	private Interval(Interval interval){
		this.val= interval.val;
		this.rank=interval.rank;
	}
	
	public void set(Interval interval){
		DEFAULT.rank=interval.rank;
		DEFAULT.val = interval.val;
	}

	public long getVal() {
		return val;
	}

  public int getRank(){
	  return rank;
  }
	
	
}
