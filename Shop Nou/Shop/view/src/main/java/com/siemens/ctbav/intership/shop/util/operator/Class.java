package com.siemens.ctbav.intership.shop.util.operator;

import java.util.ArrayList;
import java.util.List;

public class Class implements Comparable<Class> {

	
	private int x;
	private String y;
	@Override
	public int compareTo(Class other) {
		if(y.equals(other.y)) return 0;
		if(y.compareTo(other.y) >0) return 1;
		return -1;
	}
	
	public Class(int x, String y) {
		this.x = x;
		this.y = y;
	}

	public static void main(){
		
		List<Class> list = new ArrayList<Class>();
		Class c1 = new Class(1,"pisica");
		Class c2 = new Class(1,"caine");
		Class c3 = new Class(1,"bufnita");
		Class c4 = new Class(1,"maimuta");
		
	}
}
