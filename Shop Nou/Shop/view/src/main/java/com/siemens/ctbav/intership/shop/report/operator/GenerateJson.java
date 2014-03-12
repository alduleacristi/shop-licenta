package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import com.google.gson.Gson;

public class GenerateJson extends GenerateReport {

	public GenerateJson(List<? extends Object> list, FileWriter stream) {
		super(list, stream);
	}

	@Override
	public void generate() throws IOException{
		System.out.println("generez json");

		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			String json = gson.toJson(list.get(i));
			System.out.println(json);
			stream.write(json);
		}
		stream.close();
	}

}
