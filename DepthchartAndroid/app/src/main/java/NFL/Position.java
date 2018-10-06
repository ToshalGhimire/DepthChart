package NFL;

import java.util.LinkedList;

public class Position {
	
	public String playerPos;
	public LinkedList<String> players = new LinkedList<String>();

	public Position(String _playerPos) {
		playerPos = FormatString(_playerPos);
	}


	@Override
	public String toString() {

		String out = playerPos + "\t ";

		if(players.size() == 1){
			out += "1st: " +players.get(0);
		}else if(players.size() > 1){
			out += "1st: " + players.get(0) + "\t\t2nd: " + players.get(1);
		}


		return out;
	}

	public String FormatString(String pos){
		String out = pos;
		if(pos == "WR 1" || pos == "WR 2" || pos == "WR 3" ||
				pos == "WR1" || pos == "WR2" || pos == "WR3"){

			out = "WR";

		}

		if(pos == "PK" || pos == "K"){
			out = "K";
		}

		if(pos == "HB" ){
			out = "RB";
		}


		return out;
	}


}
