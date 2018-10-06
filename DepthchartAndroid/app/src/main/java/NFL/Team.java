package NFL;

import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import io.github.toshalghimire.depthchartandroid.HomeScreen;
import io.github.toshalghimire.depthchartandroid.R;

public abstract class Team {
	protected String teamName;
	protected String city;
	protected String scraperLink;

    public String outText;
	protected ArrayList<Position> DepthChart;
	//protected ArrayList<String> DefenseStats;

	/**
	 * Brief A constructor.
	 * 
	 * @param _city Teams location.
	 * @param _teamName Teams name.
	 * @param _link Link to teams site's depth chart page.
	 */
	public Team(String _city, String _teamName, String _link) {
		this.teamName = _teamName;
		this.city = _city;
		this.scraperLink = _link;
		
	}

	public String getTeamName() {
		return teamName;
	}
	public String getCity() {
		return city;
	}

	public void PopulatedData(){
	    new getTEAM().execute(scraperLink);
	    Log.d("GET DATA","Done running getData");
    }

	private class getTEAM extends AsyncTask<String, Integer, Void>{
        @Override
        protected Void doInBackground(String... Str) {
            try {
                Document teamSite = Jsoup.connect(Str[0]).get();
                Elements e = teamSite.select("table").select("tr").select("td");

                Position temp = null;
                for(Element row : e){
                    if (row.text().length() <= 4 && !row.text().equals("")) {
                        // parsing Positions from element e
                        if (temp != null)
                            DepthChart.add(temp);

                        temp = new Position(row.text());

                    } else {
                        // Adding players to Arraylist
                        if (!row.text().equals(""))
                            temp.players.add(row.text());
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            String temp = "\n";
            for(Position p : DepthChart){
                temp += p.toString() + "\n";
            }
            Log.d("DEPTH CHART CONTENT",temp);
        }
    }


	
//	public void getDefenceStats() {
//
//		Document _DefenceStats = null;
//		Elements tempElement = null;
//		Scanner S = null;
//		String Ranked = null;
//		String val = null;
//		ArrayList<String> ds = new ArrayList<String>();
//
//
//		// SCRAPING TOTAL DEFENSE STATS =================
//				try {
//					_DefenceStats = Jsoup.connect("http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=GAME_STATS&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=TOTAL_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1").get();
//				} catch (IOException e) {
//					System.out.println("Unable to connect to NFL stats webpage");
//					e.printStackTrace();
//				}
//
//				tempElement = _DefenceStats.select("tbody").select("tr");
//
//				Ranked = "";
//				for (int i = 0; i < 33; i++) {
//					String ElementString = tempElement.get(i).text();
//
//					if(ElementString.contains(this.teamName)) {
//						S = new Scanner(ElementString);
//						Ranked = S.next();
//
//						while(!S.hasNext(this.teamName))
//							S.next();
//
//						break;
//					}
//				}
//
//				for(int i = 0; i < 5; i ++)
//					S.next();
//
//				val = "The " + this.city + " " + this.teamName + " Defense are Ranked " + ordinal(Ranked) + " overall";// " + S.next() + " Yds/G";
//
//
//		// SCRAPING PASSING DEFENSE STATS ===============
//		try {
//			_DefenceStats = Jsoup.connect("http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=TEAM_PASSING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=PASSING_NET_YARDS_GAME_AVG&d-447263-o=1&d-447263-n=1").get();
//		} catch (IOException e) {
//			System.out.println("Unable to connect to NFL stats webpage");
//			e.printStackTrace();
//		}
//
//		tempElement = _DefenceStats.select("tbody").select("tr");
//
//
//		Ranked = "";
//		for (int i = 0; i < 33; i++) {
//			String ElementString = tempElement.get(i).text();
//
//			if(ElementString.contains(this.teamName)) {
//				S = new Scanner(ElementString);
//				Ranked = S.next();
//
//				while(!S.hasNext(this.teamName))
//					S.next();
//
//				break;
//			}
//		}
//
//		for(int i = 0; i < 10; i ++)
//			S.next();
//
//		val += " With " + S.next() + " PASSING Yds/G ("  + ordinal(Ranked) + ") and ";
//
//		// SCRAPING RUSHING DEFENSE STATS ===============
//		try {
//			_DefenceStats = Jsoup.connect("http://www.nfl.com/stats/categorystats?tabSeq=2&defensiveStatisticCategory=RUSHING&conference=ALL&role=OPP&season=2018&seasonType=REG&d-447263-s=RUSHING_YARDS_PER_GAME_AVG&d-447263-o=1&d-447263-n=1").get();
//		} catch (IOException e) {
//			System.out.println("Unable to connect to NFL stats webpage");
//			e.printStackTrace();
//		}
//
//		tempElement = _DefenceStats.select("tbody").select("tr");
//
//
//		Ranked = "";
//		for (int i = 0; i < 33; i++) {
//			String ElementString = tempElement.get(i).text();
//
//			if(ElementString.contains(this.teamName)) {
//				S = new Scanner(ElementString);
//				Ranked = S.next();
//
//				while(!S.hasNext(this.teamName))
//					S.next();
//
//				break;
//			}
//		}
//
//		for(int i = 0; i < 8; i ++)
//			S.next();
//
//		val += S.next() +" RUSHING Yds/G ("+ ordinal(Ranked) + ").";
//
//		System.out.println(val);
//		ds.add(val);
//
//
//		DefenseStats = ds;
//		S.close();
//	}
//
//	public static String ordinal(String input) {
//		int i = Integer.parseInt(input);
//	    String[] sufixes = new String[] { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th" };
//	    switch (i % 100) {
//	    case 11:
//	    case 12:
//	    case 13:
//	        return i + "th";
//	    default:
//	        return i + sufixes[i % 10];
//
//	    }
//	}

}
