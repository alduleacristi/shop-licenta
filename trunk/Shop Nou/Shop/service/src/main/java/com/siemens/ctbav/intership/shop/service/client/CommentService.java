package com.siemens.ctbav.intership.shop.service.client;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.siemens.ctbav.intership.shop.model.Comment;

@Stateless(name = "CommentServiceClient")
public class CommentService {
	@PersistenceContext
	private EntityManager em;

	public void addComment(Comment comment) {
		em.persist(comment);
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentList(Long idProduct) {
		List<Comment> commentList = em.createNamedQuery(Comment.productComment)
				.setParameter("idProduct", idProduct).getResultList();
		
		return commentList;
	}
}
