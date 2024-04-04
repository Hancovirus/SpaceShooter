package com.example.spaceshooter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.spaceshooter.databinding.FragmentEventWebBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class EventWebServiceFragment extends Fragment {
    private static final String BASE_URL = "https://eventandroidgameapi.onrender.com/";
    private static final String PLAYER_ID = "14";

    private FragmentEventWebBinding binding;
    private View root;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState

    ) {

        binding = FragmentEventWebBinding.inflate(inflater, container, false);
        root = binding.getRoot();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build();
        APIService apiService = retrofit.create(APIService.class);
        Call<PlayerEventWeb> call = apiService.getAllEvent(PLAYER_ID);
        call.enqueue(new Callback<PlayerEventWeb>() {
            @Override
            public void onResponse(@NonNull Call<PlayerEventWeb> call, @NonNull Response<PlayerEventWeb> response) {
                if (response.isSuccessful()) {
                    PlayerEventWeb player = response.body();
                    ListView listView = binding.listView;
                    assert player != null;
                    EventDataAdapter adapter = new EventDataAdapter(EventWebServiceFragment.this, player.getMissions());
                    listView.setAdapter(adapter);
                    System.out.println(adapter.getCount());
                } else {
                    Log.e("MainActivity", "Failed to fetch events: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<PlayerEventWeb> call, @NonNull Throwable t) {
                Log.e("MainActivity", "Failed to fetch events: " + t.getMessage());
            }
        });
        return root;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(EventWebServiceFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public static void completeMission(int missionId) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(MoshiConverterFactory.create()).build();
        APIService apiService = retrofit.create(APIService.class);
        Call<Void> completeCall = apiService.completeMission(PLAYER_ID, missionId);
        completeCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NonNull Call<Void> call, @NonNull Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.d("MainActivity", "Mission completed successfully");
                    // Handle successful completion
                    // Update the UI or any other necessary action
                } else {
                    Log.e("MainActivity", "Failed to complete mission: " + response.message());
                    // Handle failure
                }
            }

            @Override
            public void onFailure(@NonNull Call<Void> call, @NonNull Throwable t) {
                Log.e("MainActivity", "Failed to complete mission: " + t.getMessage());
            }
        });
    }

}