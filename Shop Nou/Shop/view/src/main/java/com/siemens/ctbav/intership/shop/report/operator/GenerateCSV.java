package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import com.siemens.ctbav.intership.shop.dto.ClientDTO;

public class GenerateCSV extends GenerateReport {

	public GenerateCSV(List<? extends Object> list, FileWriter stream) {
		super(list, stream);
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
		for (int i = 0; i < fields.length; i++)
			System.out.println(fields[i].getName());
		for (int i = 0; i < fields.length; i++)
			stream.write(fields[i].getName() + ",");
		stream.write('\n');
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			for (int j = 0; j < fields.length; j++) {
				try {
					Object val = fields[j].get(obj);
					if (val != null) {
						System.out.println("val  " + val.toString());
						stream.write(val.toString());
					} else
						stream.write("null");
					stream.write(',');
				} catch (Exception e) {
					throw new IOException(e);
				}
			}
			stream.write('\n');
		}
		stream.flush();
		stream.close();

	}

}
