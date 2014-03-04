package com.siemens.ctbav.intership.shop.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.NGramFilterFactory;
import org.apache.solr.analysis.WhitespaceTokenizerFactory;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.ClassBridge;
import org.hibernate.search.annotations.ClassBridges;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;

import com.siemens.ctbav.intership.shop.util.client.CategoryIndex;
import com.siemens.ctbav.intership.shop.util.client.ProductCategoryIndex;


/**
 * The persistent class for the Client database table.
 * 
 */
@Entity
@NamedQueries({@NamedQuery(name = Client.GET_ALL_CLIENTS, query = "SELECT c FROM Client c order by c.firstname"),
			@NamedQuery(name = Client.GET_CLIENT_BY_USERNAME , query = "select c from Client c where c.user.username = :username")})
@ClassBridges({
	@ClassBridge(name = "category", impl = CategoryIndex.class, params = @Parameter(name = "sepChar", value = " "), analyzer = @Analyzer(definition = "categorySearch")),
	@ClassBridge(name = "productCategory", impl = ProductCategoryIndex.class, params = @Parameter(name = "sepChar", value = " "), analyzer = @Analyzer(definition = "productSearch")) })

@AnalyzerDefs({
	@AnalyzerDef(name = "productSearch", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = NGramFilterFactory.class, params = {
					@Parameter(name = "maxGramSize", value = "10"),
					@Parameter(name = "minGramSize", value = "1") }), }),
	@AnalyzerDef(name = "categorySearch", tokenizer = @TokenizerDef(factory = WhitespaceTokenizerFactory.class), filters = {
			@TokenFilterDef(factory = LowerCaseFilterFactory.class),
			@TokenFilterDef(factory = NGramFilterFactory.class, params = {
					@Parameter(name = "maxGramSize", value = "100"),
					@Parameter(name = "minGramSize", value = "100") }), })

})
public class Client implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String GET_ALL_CLIENTS = "findAllClients";
	public static final String GET_CLIENT_BY_USERNAME = "getClientByUsername";

	@Id
	private Long id;

	

	@Column(name = "first_name")
	private String firstname;

	@Column(name = "last_name")
	private String lastname;

	@Column(name = "phone_number")
	private String phoneNumber;

	// bi-directional many-to-one association to Command

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "client")
	private List<Command> commands;

	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "id_client")
	private List<Adress> adresses;

	@OneToOne
	@JoinColumn(name = "id")
	private User user;

	public Client() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public List<Command> getCommands() {
		return this.commands;
	}

	public void setCommands(List<Command> commands) {
		this.commands = commands;
	}

	public Command addCommand(Command command) {
		getCommands().add(command);
		command.setClient(this);

		return command;
	}

	public Command removeCommand(Command command) {
		getCommands().remove(command);
		command.setClient(null);

		return command;
	}

	public List<Adress> getAdresses() {
		return adresses;
	}

	public void setAdresses(List<Adress> adresses) {
		this.adresses = adresses;
	}

	@Override
	public String toString() {
		return "Client [id=" + id +  ", firstname="
				+ firstname + ", lastname=" + lastname + ", phoneNumber="
				+ phoneNumber + ", adresses=" + adresses + ", user=" + user
				+ "]";
	}
}