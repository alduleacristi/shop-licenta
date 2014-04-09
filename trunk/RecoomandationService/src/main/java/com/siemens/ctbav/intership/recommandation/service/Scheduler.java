package com.siemens.ctbav.intership.recommandation.service;

import javax.annotation.PreDestroy;
import javax.ejb.Schedule;
import javax.ejb.Singleton;



@Singleton(name = "scheduler")
public class Scheduler {
	@Schedule(dayOfWeek = "*" , hour = "*" , minute = "*" ,  second = "*/20")
	public void backgroundProcess(){
		System.out.println("Sa executat");
	}
	
	@PreDestroy
	public void method(){
		System.out.println("sa distrus");
	}
}
