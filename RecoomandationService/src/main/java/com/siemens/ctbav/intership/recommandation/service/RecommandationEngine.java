package com.siemens.ctbav.intership.recommandation.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.faces.context.FacesContext;

import org.apache.commons.io.IOUtils;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

@Singleton
public class RecommandationEngine {
	private UserSimilarity similarity;
	private UserNeighborhood neighborhood;
	private DataModel model;
	private Recommender recomender;
	private Integer fileIndex;

	@PostConstruct
	private void initialize() {
		fileIndex = 1;
		
		try {
			createDataModel();
			computeSimilarity();
			computeNeighborhood();
			buildRecommender();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TasteException e) {
			e.printStackTrace();
		}
		System.out.println("sistemul a fost construit");
	}

	private void createDataModel() throws IOException {
		InputStream stream = Thread.currentThread().getContextClassLoader()
				.getResourceAsStream("Preferences.csv");

		File tempFile = File.createTempFile("tempPref", ".csv");

		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(stream, out);

		this.model = new FileDataModel(tempFile);

		tempFile.delete();
	}

	private void computeSimilarity() {
		this.similarity = new LogLikelihoodSimilarity(model);
	}

	private void computeNeighborhood() throws TasteException {
		this.neighborhood = new NearestNUserNeighborhood(2, similarity, model);
	}

	private void buildRecommender() {
		this.recomender = new GenericUserBasedRecommender(model, neighborhood,
				similarity);
	}

	public List<Long> getRecomandation(Long idUser, Long nrOfItems)
			throws TasteException {
		List<RecommendedItem> recomendations = recomender.recommend(idUser,
				nrOfItems.intValue());

		List<Long> recomadationsId = new ArrayList<Long>();

		for (RecommendedItem recomandetion : recomendations)
			recomadationsId.add(recomandetion.getItemID());

		return recomadationsId;
	}

	public void writePreferences(Long idUser, List<Long> preferences) {		
		BufferedWriter out = null;
//		String path = Thread.currentThread().getContextClassLoader().getResource("Preferences.csv").getPath();
//		path = path + "." + fileIndex;
		
//		String path = FacesContext.getCurrentInstance().getExternalContext().getRealPath("");
//		System.out.println("path: " + path);

//		File file = new File(path);
//		try {
//			out = new BufferedWriter(new FileWriter(file));
//			
//			for(Long itemId:preferences)
//				System.out.println(idUser+" "+itemId);
//			
//			if(out != null)
//				System.out.println("nu e null");
//			else
//				System.out.println("este null");
//		} catch (IOException e) {
//			e.printStackTrace();
//		}finally{
//
//		}
	}
}
