package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public abstract class GenerateReport {

	protected List<?extends Object> list;
	protected FileWriter stream;
	
	public GenerateReport(	List<?extends Object> list, FileWriter stream){
		this.list=list;
		this.stream=stream;
	}
	 public abstract void generate() throws IOException;
}
