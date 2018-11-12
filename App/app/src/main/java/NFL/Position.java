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

		String out = playerPos + ":\t ";

		if(players.size() == 1){
			out += players.get(0);
		}else if(players.size() > 1){
			out +=  players.get(0) + "\t - \t" + players.get(1);
		}


		return out;
	}

	public String FormatString(String pos){
		String out = pos;
		if(pos.equals("WR 1") || pos.equals("WR 2") || pos.equals("WR 3") ||
				pos.equals("WR1") || pos.equals("WR2") || pos.equals("WR3")
				|| pos.equals("RWR") || pos.equals("LWR")){

			out = "WR";

		}

		if(pos.equals("PK") || pos.equals("K")){
			out = "K";
		}

		if(pos.equals("HB")){
			out = "RB";
		}


		return out;
	}


}
