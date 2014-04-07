package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean(name = "CommentBeanVisitor")
@ViewScoped
public class CommentBeanVisitor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<String> list;
	
	private String newComment;

	@PostConstruct
	private void initialize() {
		list = new ArrayList<String>();
		list.add("Ana are mwere");
		list.add("Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,Gigi are pere,");
		list.add("Gigi are pere");
		list.add("Gigi are pere");
		list.add("Gigi are pere");
		list.add("Gigi are pere");
		list.add("Gigi are pere");
	}

	public List<String> getList() {
		return list;
	}

	public void setList(List<String> list) {
		this.list = list;
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public String doAction() {
		list.add(newComment);
		
		newComment="";
		
		return "";
	}
}
