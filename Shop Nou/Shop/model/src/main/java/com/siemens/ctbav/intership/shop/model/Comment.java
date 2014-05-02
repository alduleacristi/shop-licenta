package com.siemens.ctbav.intership.shop.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity(name = "comments")
@NamedQuery(name = Comment.productComment, query = "SELECT c FROM comments c where c.id = :idProduct")
public class Comment {
	public static final String productComment = "getProductComment";
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_comments")
	private Long id;

	@OneToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@OneToOne
	@JoinColumn(name = "id_user")
	private User user;

	@Column(name = "date")
	private Date date;

	@Column(name = "comment")
	private String comment;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public String toString() {
		return comment;
	}
}
