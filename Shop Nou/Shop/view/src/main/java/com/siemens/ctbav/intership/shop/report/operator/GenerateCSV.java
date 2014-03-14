package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.ClientDTO;

public class GenerateCSV extends GenerateReport {

	public GenerateCSV(List<? extends Object> list) throws IOException {
		super(list);
	}

	@Override
	public void generate() throws IOException {

		System.out.println(list.size());
		if (list == null || list.size() == 0)
			return;
		Class cls = list.get(0).getClass();
		Field[] fields = cls.getDeclaredFields();
		// setez campurile ca fiind publice ca sa pot sa iau valorile
		for (int i = 0; i < fields.length; i++)
			fields[i].setAccessible(true);

		FileWriter flux;
		try {
			flux = new FileWriter(getFilename());
		} catch (ParseException e1) {
			throw new IOException(e1.getMessage());
		}
		for (int i = 0; i < fields.length; i++)
			flux.write(fields[i].getName() + ",");
		flux.write('\n');
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			for (int j = 0; j < fields.length; j++) {
				try {
					Object val = fields[j].get(obj);
					if (val != null) {
						flux.write(val.toString());
					} else
						flux.write("null");
					flux.write(',');
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
			flux.write('\n');
		}
		flux.flush();
		flux.close();

	}
	
	@Override
	protected String getFilename() throws ParseException{
		return super.getFilename() + ".csv";
	}
}
