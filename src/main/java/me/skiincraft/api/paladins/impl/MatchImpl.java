package me.skiincraft.api.paladins.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import me.skiincraft.api.paladins.common.EndPoint;
import me.skiincraft.api.paladins.common.Request;
import me.skiincraft.api.paladins.entity.match.Match;
import me.skiincraft.api.paladins.entity.match.MatchPlayer;
import me.skiincraft.api.paladins.entity.match.objects.Ban;

public class MatchImpl implements Match {

	private EndPoint endPoint;
	private JsonArray array;
	private JsonObject object;
	
	private List<MatchPlayer> team1 = new ArrayList<>();
	private List<MatchPlayer> team2 = new ArrayList<>();
	private List<Ban> bans = new ArrayList<>();
	
	public MatchImpl(EndPoint endPoint, JsonArray array) {
		this.endPoint = endPoint;
		this.array = array;
		this.object = array.get(0).getAsJsonObject();
	}

	public String getWinner() {
		return (object.get("Winning_TaskForce").getAsInt() == 1) ? "Blue" : "Red";
	}
	
	protected JsonElement get(String key) {
		return object.get(key);
	}

	public List<Ban> getBans() {
		if (bans.size() != 0) {
			return bans;
		}
		for (int i = 1; i <= 4; i++) {
			if (get("BanId" + i).getAsLong() == 0) {
				continue;
			}
			bans.add(new Ban(object.get("BanId"+ i).getAsLong(), object.get("Ban_"+ i).getAsString(), endPoint));
		}
		return bans;
	}

	public long getMatchId() {
		return get("Match").getAsLong();
	}

	public long getMatchDuration() {
		return TimeUnit.SECONDS.toMillis(get("Match_Duration").getAsLong());
	}

	public String getMapGame() {
		return get("Map_Game").getAsString();
	}

	public long getMatchMinutes() {
		return TimeUnit.SECONDS.toMinutes(get("Match_Duration").getAsLong());
	}

	public int getTeam1Score() {
		return get("Team1Score").getAsInt();
	}

	public int getTeam2Score() {
		return get("Team2Score").getAsInt();
	}

	public List<MatchPlayer> getTeam1() {
		if (team1.size() != 0) {
			return team1;
		}
		for (JsonElement ele : array) {
			JsonObject ob = ele.getAsJsonObject();
			if (ob.get("TaskForce").getAsInt() != 1) {
				continue;
			}
			team1.add(new MatchPlayerImpl(endPoint, ob, this));
		}
		
		return team1;
	}

	public List<MatchPlayer> getTeam2() {
		if (team2.size() != 0) {
			return team2;
		}
		for (JsonElement ele : array) {
			JsonObject ob = ele.getAsJsonObject();
			if (ob.get("TaskForce").getAsInt() != 1) {
				continue;
			}
			team2.add(new MatchPlayerImpl(endPoint, ob, this));
		}
		
		return team2;
	}

	public int getWinnerTeam() {
		return object.get("Winning_TaskForce").getAsInt();
	}

	public boolean hasReplay() {
		return get("hasReplay").getAsString() == "y";
	}

	public int getMatchQueueId() {
		return get("match_queue_id").getAsInt();
	}

	public String getGamemode() {
		return get("name").getAsString();
	}

	public boolean isRanked() {
		return getGamemode().equalsIgnoreCase("ranked");
	}

	public List<MatchPlayer> getPlayers() {
		List<MatchPlayer> m = new ArrayList<>();
		m.addAll(team1);
		m.addAll(team2);
		return m;
	}

	public boolean isDetailedMatch() {
		return true;
	}

	public Request<Match> getMatchDetails() {
		return null;
	}

}