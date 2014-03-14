package com.siemens.ctbav.intership.shop.report.operator;

import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import com.google.gson.Gson;

public class GenerateJson extends GenerateReport {

	public GenerateJson(List<? extends Object> list) throws IOException {
		super(list);
	}

	@Override
	public void generate() throws IOException {
		try{
		FileWriter flux = new FileWriter(getFilename());
		Gson gson = new Gson();
		for (int i = 0; i < list.size(); i++) {
			String json = gson.toJson(list.get(i));
			flux.write(json);
		}
		flux.close();
		}
		catch(Exception exc){
			throw new IOException(exc.getMessage());
		}
	}

	@Override
	protected String getFilename() throws ParseException{
		return super.getFilename() + ".json";
	}

}
