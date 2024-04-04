package com.example.spaceshooter;

import java.util.List;

public class PlayerEventWeb {
    private String playerID;
    private List<Mission> missions;

    public PlayerEventWeb(String playerID, List<Mission> missions) {
        this.playerID = playerID;
        this.missions = missions;
    }

    public String getPlayerID() {
        return playerID;
    }

    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }

    public List<Mission> getMissions() {
        return missions;
    }

    public void setMissions(List<Mission> missions) {
        this.missions = missions;
    }
}
