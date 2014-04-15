package com.siemens.ctbav.intership.recommandation.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.LogLikelihoodSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import com.siemens.ctbav.intership.recommandation.conf.ConfigurationService;

@Singleton
public class RecommandationEngine {
	@EJB
	private ConfigurationService confService;

	private UserSimilarity similarity;
	private UserNeighborhood neighborhood;
	private DataModel model;
	private Recommender recomender;
	private FastByIDMap<List<Long>> modelToBeAdded;
	private int nrOfUsers;

	@PostConstruct
	private void initialize() {
		nrOfUsers = confService.getNrOfUsers();

		try {
			modelToBeAdded = new FastByIDMap<List<Long>>();

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
		// InputStream stream = Thread.currentThread().getContextClassLoader()
		// .getResourceAsStream("Preferences.csv");
		//
		// File tempFile = File.createTempFile("tempPref", ".csv");
		//
		// FileOutputStream out = new FileOutputStream(tempFile);
		// IOUtils.copy(stream, out);

		String path = System.getProperty("jboss.home.dir");
		String filename = "Preferences.csv";
		path = path + "\\resources\\" + filename;

		File file = new File(path);

		this.model = new FileDataModel(file);

		// tempFile.delete();
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

	private boolean verifIfExist(Long user, Long pref) {
		List<Long> prefArray2 = null;
		PreferenceArray prefArray = null;
		try {
			//System.out.println("in try");
			prefArray2 = modelToBeAdded.get(user);
			prefArray = model.getPreferencesFromUser(user);
			//System.out.println("inainte de if");
			if (prefArray2 != null) {
				if (prefArray.hasPrefWithItemID(pref)
						|| prefArray2.contains(pref)) {
					//System.out.println("return true");
					return true;
				} else {
					if (prefArray.hasPrefWithItemID(pref)) {
						//System.out.println("return true");
						return true;
					}
				}
			}
		} catch (TasteException e) {
			if (prefArray2 != null && prefArray2.contains(pref)) {
				//System.out.println("return true");
				return true;
			}
			//System.out.println("return false");
			return false;
		}
		//System.out.println("return false");
		return false;
	}
	
	private int getSize(){
		int size = 0;
		
		Set<Entry<Long, List<Long>>> entrys = modelToBeAdded.entrySet();
		Iterator<Entry<Long, List<Long>>> it = entrys.iterator();
		while (it.hasNext()) {
			Entry<Long, List<Long>> entry = it.next();

			List<Long> array = entry.getValue();
			
			size += array.size();
		}
		
		//System.out.println("map size: => " + size);
		return size;
	}

	public void writePreferences(Long idUser, List<Long> preferences) {
		//System.out.println("pref size: => " + preferences.size());
		int n = 0;
		//System.out.println(" => write pref");

		List<Long> prefArray = modelToBeAdded.get(idUser);

		if (prefArray == null)
			prefArray = new ArrayList<Long>();

		for (int i = 0; i < preferences.size(); i++) {
			if (!verifIfExist(idUser, preferences.get(i))
					&& !prefArray.contains(preferences.get(i))) {
				prefArray.add(preferences.get(i));
				n++;
			}
		}

		//System.out.println("   n=" + n);
		modelToBeAdded.put(idUser, prefArray);

		
		if (this.getSize() >= nrOfUsers) {
			writeToFile();
			modelToBeAdded.clear();
		}
	}

	private void writeToFile() {
		//System.out.println("Sa executat");

		if (modelToBeAdded.size() == 0) {
			//System.out.println("size = 0 => return");
			return;
		}

		File file = null;
		long index = 1L;
		String path = System.getProperty("jboss.home.dir");

		String filename = "Preferences.";

		path = path + "\\resources\\" + filename;

		while (true) {
			file = new File(path + index + ".csv");
			if (!file.exists())
				break;
			else {
				index++;
				//System.out.println(path + index + ".csv");
			}
		}
		//System.out.println(index);

		try {
			file = new File(path + index + ".csv");
			BufferedWriter buffWriter = new BufferedWriter(new FileWriter(file));

			Set<Entry<Long, List<Long>>> entrys = modelToBeAdded.entrySet();
			Iterator<Entry<Long, List<Long>>> it = entrys.iterator();
			while (it.hasNext()) {
				Entry<Long, List<Long>> entry = it.next();

				List<Long> array = entry.getValue();
				//System.out.println("length: => " + array.size());

				for (int j = 0; j < array.size(); j++)
					buffWriter.write(String.valueOf(entry.getKey()) + ","
							+ String.valueOf(array.get(j)) + "\n");
				//System.out.println("sa scris");
			}

			buffWriter.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
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
}
