package com.siemens.ctbav.intership.shop.service.operator;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Messages;
import com.siemens.ctbav.intership.shop.model.User;



@Stateless
public class MessageService {

	@PersistenceContext
	private EntityManager em;
	
	public List<Messages> getAllMesasges() throws Exception{
		List<Messages> list = new ArrayList<Messages>();
	    list = em.createNamedQuery(Messages.GET_UNREPLIED_MESSAGES).setParameter("isReplied", false).getResultList();
	    if(list == null || list.size() ==0) throw new Exception();
	    return list;
	}
	
	public void deleteMessage(Messages message) throws Exception{
		Messages mes = em.find(Messages.class, message.getId());
		if(mes == null) throw new Exception();
		em.remove(mes);
	}
	
	public void setReplied(Messages mesasge,User user) throws Exception{
		Messages mes = em.find(Messages.class, mesasge.getId());
		if(mes == null) throw new Exception();
		mes.setReplied(true);
		mes.setUser(user);
		em.merge(mes);
	}
}
