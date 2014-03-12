package com.siemens.ctbav.intership.shop.service.superadmin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jcr.ItemExistsException;
import javax.jcr.LoginException;
import javax.jcr.NoSuchWorkspaceException;
import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Repository;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.SimpleCredentials;
import javax.jcr.Value;
import javax.jcr.ValueFormatException;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.value.BinaryValue;
import org.primefaces.event.FileUploadEvent;

import com.siemens.ctbav.intership.shop.conf.ConfigurationService;

@Stateless(name = "photoService")
public class PhotoService {

	@EJB
	private ConfigurationService confService;

	private Repository repository;
	private SimpleCredentials creds;
	private String workspace;

	@PostConstruct
	void getStreamsForMenu() throws RepositoryException {
		String url = "http://localhost:8082/server";
		workspace = "default";
		creds = new SimpleCredentials("admin", "admin".toCharArray());
		repository = JcrUtils.getRepository(url);
	}

	private Session loginUpdate() {
		Session session = null;
		try {
			session = repository.login(creds, workspace);
		} catch (LoginException e) {
			System.out.println(e);
		} catch (NoSuchWorkspaceException e) {
			System.out.println(e);
		} catch (RepositoryException e) {
			System.out.println(e);
		}
		return session;
	}

	private void logout(Session jcrSession) {
		if (jcrSession != null && jcrSession.isLive())
			jcrSession.logout();
	}

	public InputStream getPhotoStreamFromRepository(String id, String name) {
		InputStream is = null;

		Session session = loginUpdate();

		if (session != null) {
			try {
				is = getStream(session, id, name);
			} catch (RepositoryException e) {
				System.out.println(e);
			} finally {
				logout(session);
			}
		} else {
			System.out.println("Session is null");
		}
		return is;
	}

	private InputStream getStream(Session session, String id, String name)
			throws RepositoryException {
		InputStream is = null;
		Node root = session.getRootNode();
		Node content = root.getNode("products/" + id + "/" + name);
		is = content.getNode("jcr:content").getProperty("jcr:data").getBinary()
				.getStream();
		return is;
	}

	public InputStream getPhotoStreamFromRepository(String id) {
		InputStream is = null;
		Session session = loginUpdate();
		if (session != null) {
			try {
				is = getStream(session, id);
			} catch (RepositoryException e) {
				System.out.println(e);
			} finally {
				logout(session);
			}
		} else {
			System.out.println("Session is null");
		}
		return is;
	}

	private InputStream getStream(Session session, String id)
			throws RepositoryException {
		InputStream is = null;
		Node root = session.getRootNode();
		Node content = root.getNode("products/" + id);
		NodeIterator n = content.getNodes();
		while (n.hasNext()) {
			Node aux = n.nextNode();
			is = aux.getNode("jcr:content").getProperty("jcr:data").getBinary()
					.getStream();
			break;
		}
		return is;
	}

	private Node getNode(Node products, String path)
			throws RepositoryException, ItemExistsException,
			PathNotFoundException, VersionException,
			ConstraintViolationException, LockException {
		Node node;
		try {
			node = products.getNode(path);
		} catch (PathNotFoundException e) {
			node = products.addNode(path);
		}
		return node;
	}

	public void addPhoto(FileUploadEvent event, String path)
			throws RepositoryException, IOException {
		Session session = loginUpdate();
		try {
			if (session != null) {
				Node root = session.getRootNode();
				Node products = getNode(root, "products");
				Node node = getNode(products, path);
				uniqueCheck(event, node);
				addNodeContent(event, node);
				session.save();
			}
		} catch (RepositoryException e) {
			throw e;
		} finally {
			logout(session);
		}
	}

	private void uniqueCheck(FileUploadEvent event, Node node)
			throws RepositoryException {
		NodeIterator n = node.getNodes();
		String name = event.getFile().getFileName();
		while (n.hasNext()) {
			Node aux = n.nextNode();
			if (name.equals(aux.getName()))
				throw new RepositoryException("Photo already exists");
		}
	}

	private void addNodeContent(FileUploadEvent event, Node node)
			throws ItemExistsException, PathNotFoundException,
			NoSuchNodeTypeException, LockException, VersionException,
			ConstraintViolationException, RepositoryException {
		Node imageNode = node.addNode(event.getFile().getFileName(), "nt:file");
		Node contentNode = imageNode.addNode("jcr:content", "nt:resource");

		Value value = new BinaryValue(event.getFile().getContents());

		contentNode.setProperty("jcr:data", value);
		contentNode.setProperty("jcr:mimeType", event.getFile()
				.getContentType());
	}

	public void removePhotos(String path) throws RepositoryException {
		Session session = loginUpdate();
		try {
			if (session != null) {
				Node root = session.getRootNode();
				Node products = getNode(root, "products");
				Node node = getNode(products, path);
				node.remove();
				session.save();
			}
		} finally {
			logout(session);
		}
	}

	public List<String> displayOfPhotos(String path) throws LoginException,
			RepositoryException, IOException {
		List<String> photos = new ArrayList<String>();
		Session session = loginUpdate();
		try {
			if (session != null) {
				photos = getPhotosFromRepository(session, path);
			}
		} finally {
			logout(session);
		}
		return photos;
	}

	private List<String> getPhotosFromRepository(Session session, String path)
			throws LoginException, RepositoryException, IOException {
		List<String> photos = new ArrayList<String>();
		Node root = session.getRootNode();
		Node products = getNode(root, "products");
		Node node = getNode(products, path);

		NodeIterator n = node.getNodes();
		while (n.hasNext()) {
			photos.add(photosIteration(n, path));
		}
		return photos;
	}

	private String photosIteration(NodeIterator n, String path)
			throws ValueFormatException, RepositoryException,
			PathNotFoundException, IOException {
		Node aux = n.nextNode();
		StringBuilder sb = new StringBuilder(50);
		sb.append(confService.getHost() + "/Shop4j/rest/products/" + path);
		sb.append("/" + aux.getName());

		return sb.toString();
	}
}
