package com.siemens.ctbav.intership.shop.report.operator;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class GenerateReport {

	static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.mm.yyyy");
	protected List<? extends Object> list;

	public GenerateReport(List<? extends Object> list) throws IOException {
		File dir = new  File("d:\\Reports");
		if(!dir.exists())
		{
			boolean result = dir.mkdir();
			if(!result) throw new IOException("The directory cannot be created!");
		}
		this.list = list;

	}

	protected String getFilename() throws ParseException {
		java.util.Date currDate = new Date();
		String newdate = DATE_FORMAT.format(currDate);
		String path = "D:\\Reports\\" + list.get(0).getClass().getSimpleName() + ""
				+ newdate;

		System.out.println("calea este : " + path);
		return path;
	}

	public abstract void generate() throws IOException;
}
