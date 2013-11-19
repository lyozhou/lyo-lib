package recomended;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.mysql.jdbc.Connection;

import Support.Connect2Database;
import au.com.bytecode.opencsv.CSVReader;

public class Rrecsys {
	private Connection con;
	private ArrayList movieList = new ArrayList();
	private ResultSet rs;

	public static void main(String[] args) {
		// Movies array contains the movie IDs of the top 5 movies.
		int movies[] = new int[5];
		CSVReader csvReader = null;
		Rrecsys importData = new Rrecsys();
		importData.importData();
//		BigDecimal   b   =   new   BigDecimal(0.9262);  

//		System.out.println(b.setScale(2,   BigDecimal.ROUND_HALF_UP));

		// Write the top 5 movies, one per line, to a text file.
		try {
			PrintWriter writer = new PrintWriter("pa1-result.txt", "UTF-8");

			for (int movieId : movies) {
				writer.println(movieId);
			}

			writer.close();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void importData() {
		Connect2Database con2Db = new Connect2Database();
		con2Db.loadDriver();
		String createRatingTable = "CREATE TABLE `ratings` (`UserID` int(11) default NULL,  `ItemID` int(11) default NULL,  `Rating` float(10,1) default NULL)";
		con = (Connection) con2Db.getCon();
//		try {
//			con.createStatement().executeUpdate(createRatingTable);
			String fileName = "D:/Downloads/";
//			readFromCSV(fileName+"recsys-data-ratings.csv");
			readMovieFromCSV(fileName+"recsys-data-movie-titles.csv");
			sortSimilarity();
//		} 
//		catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	private void sortSimilarity() {
		// TODO Auto-generated method stub
		Map<Integer,Float> map = new HashMap<Integer,Float>();
		try {
//			PreparedStatement prstInsert = con
//					.prepareStatement("select "
//							+ "(select count(*) from ratings where UserId in "
//							+ "(select UserId from ratings where ItemID=194) "
//							+ "and ItemId=?) / "
//							+ "(select count(*) from ratings where ItemID=194) as countNum");
			PreparedStatement prstInsert = con.prepareStatement("select ("
					+ "(select (select count(*) from ratings where UserId in (select UserId from ratings where ItemID=194) and ItemId=?)"
					+ "/(select count(*) from ratings where ItemID=194))"
					+ "/(select (select COUNT(*) from ratings where UserId not in (select UserId from ratings where ItemID=194) and ItemId=?)"
					+ "/(select (5564-count(*)) from ratings where ItemID=194))) as countNum");
			for(int i=0;i<movieList.size();i++){
					int movieId =(Integer) movieList.get(i);  
					prstInsert.setInt(1, movieId);
					prstInsert.setInt(2, movieId);
					rs = prstInsert.executeQuery();
					while(rs.next()){
						String tmp = rs.getString("countNum");
						if(tmp!=null){
						Float b = Float.parseFloat(tmp);
						map.put((Integer) movieList.get(i), b);}
					}
			}
			List<Map.Entry<Integer,Float >> infoIds =
				    new ArrayList<Map.Entry<Integer,Float >>(map.entrySet());

			Collections.sort(infoIds, new Comparator<Map.Entry<Integer,Float>>() {   
			    public int compare(Map.Entry<Integer,Float> o1, Map.Entry<Integer,Float> o2) {      
			        //return (o2.getValue() - o1.getValue()); 
			    	return -(o1.getValue()).compareTo(o2.getValue());
			    }
			}); 
			
			for (int i = 0; i < infoIds.size(); i++) {
			    String id = infoIds.get(i).toString();
			    System.out.println(id);
			}



		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	public void readFromCSV(String fileName) {
		// TODO Auto-generated method stub
		String divider = ",";
		try {
			PreparedStatement prstInsert = con
					.prepareStatement("INSERT INTO ratings VALUES (?,?,?)");
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			int count = 0;
			while (true) {
				String s = in.readLine();
				if (s == null) {
					System.out.println("Total imported ratings: " + count);
					break;
				}
				count++;
				StringTokenizer t = new StringTokenizer(s,
						String.valueOf(divider));
				int user = Integer.parseInt(t.nextToken());
				int item = Integer.parseInt(t.nextToken());
				float rating = Float.parseFloat(t.nextToken());
				prstInsert.setInt(1, user);
				prstInsert.setInt(2, item);
				prstInsert.setFloat(3, rating);
				if (count != 0
						&& Math.round((double) count / 100) == ((double) count / 100))
					System.out.println("Imported ratings: " + count);
				prstInsert.executeUpdate();
			}
			prstInsert.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void readMovieFromCSV(String fileName) {
		// TODO Auto-generated method stub
		String divider = ",";
		int i = 0;
		try {
			BufferedReader in = new BufferedReader(new FileReader(fileName));
			while (true) {
				String s = in.readLine();
				if (s == null) {
					break;
				}
				StringTokenizer t = new StringTokenizer(s,
						String.valueOf(divider));
				int movieId = Integer.parseInt(t.nextToken());
				movieList.add(movieId);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
