package com.siemens.ctbav.intership.shop.view.visitor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.siemens.ctbav.intership.shop.model.Client;
import com.siemens.ctbav.intership.shop.model.Comment;
import com.siemens.ctbav.intership.shop.model.Product;
import com.siemens.ctbav.intership.shop.service.client.CommentService;

@ManagedBean(name = "CommentBeanVisitor")
@ViewScoped
public class CommentBeanVisitor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private CommentService commentService;

	private String newComment;
	private List<Comment> commentList;
	
	@PostConstruct
	private void initialize(){
		commentList = new ArrayList<Comment>();
	}

	public String getNewComment() {
		return newComment;
	}

	public void setNewComment(String newComment) {
		this.newComment = newComment;
	}

	public List<Comment> getCommentList() {
		return commentList;
	}

	public void setCommentList(List<Comment> commentList) {
		this.commentList = commentList;
	}

	public String doAction(Product product) {
		Comment comment = new Comment();

		comment.setComment(newComment);
		comment.setProduct(product);

		Client client = (Client) FacesContext.getCurrentInstance().getExternalContext()
				.getSessionMap().get("client");

		if (client == null) {

			FacesContext.getCurrentInstance().addMessage(
					null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
							"We have a problem. Please try again later."));
			return "";
		}else{
			comment.setUser(client.getUser());
		}
		
		Date date = new Date();
		comment.setDate(date);
		
		System.out.println(product);
		//commentService.addComment(comment);
		
		//commentList.add(comment);
		product.getComments().add(comment);
		
		newComment = "";

		return "";
	}
	
	@SuppressWarnings("deprecation")
	public String getDate(Date date){
		StringBuilder dateS = new StringBuilder(30);
		
		if(date == null)
			return "";
		
		dateS.append(String.valueOf(date.getYear()+1900));
		dateS.append("-");
		dateS.append(String.valueOf(date.getMonth() + 1));
		dateS.append("-");
		dateS.append(String.valueOf(date.getDate()));
		
		return dateS.toString();
	}
}
