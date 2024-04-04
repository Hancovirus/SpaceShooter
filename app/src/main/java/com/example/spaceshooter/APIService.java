package com.example.spaceshooter;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIService {
    @GET("/events/{playerID}")
    Call<PlayerEventWeb> getAllEvent(@Path("playerID") String playerID);

    @PUT("/events/{playerID}/{missionID}")
    Call<Void> completeMission(@Path("playerID") String playerID, @Path("missionID") int missionID);
}
