package recommandservice;

import java.util.ArrayList;

public class Main {
	public static void main(String[] args) {
		RecommandService_Service rs = new RecommandService_Service();
		RecommandService obj = rs.getRecommand();
		
		//obj.writePreferences(5L, new ArrayList<Long>());
		
		System.out.println(obj.getRecommandation(1L));
	}
}
