package com.ftninformatika.moviesactors.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.ftninformatika.moviesactors.Adapters.SearchListviewAdapter;
import com.ftninformatika.moviesactors.Models.Movie;
import com.ftninformatika.moviesactors.Models.Result;
import com.ftninformatika.moviesactors.Models.Search;
import com.ftninformatika.moviesactors.Net.WebService.RESTService;
import com.ftninformatika.moviesactors.R;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private onListItemClickListener listener;

    private EditText etSearch;
    private Button bSearch;
    private ListView lvSearch;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        etSearch = view.findViewById(R.id.etSearch);
        bSearch = view.findViewById(R.id.bSearch);
        lvSearch = view.findViewById(R.id.lvSearch);

        bSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!etSearch.getText().toString().equals("")){
                    searchMovies(etSearch.getText().toString());
                }
                else {
                    Toast.makeText(getActivity(), "Popunite polje sa nazivom!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    private void searchMovies(String title){
        HashMap<String, String> query = new HashMap<>();
        query.put("apikey", RESTService.API_KEY);
        query.put("s", title);

        Call<Result> call = RESTService.apiInterface().getMoviesByTitle(query);
        call.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                if (response.code() == 200) {
                    showMovies(response.body().getSearch());
                }
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showMovies(List<Search> movies) {
        if (movies != null) {
            SearchListviewAdapter adapter = new SearchListviewAdapter(getActivity(), movies);
            lvSearch.setAdapter(adapter);
            lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    searchDetails(adapter.getItem(position).getImdbID());
                }
            });
        }
    }

    private void searchDetails(String id) {
        HashMap<String, String> query = new HashMap<>();
        query.put("apikey", RESTService.API_KEY);
        query.put("i", id);

        Call<Movie> call = RESTService.apiInterface().getMoviesByIMDBID(query);
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        Movie movie = response.body();
                        listener.onListItemClicked(movie);
                    }
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof onListItemClickListener) {
            listener = (onListItemClickListener) context;
        } else {
            Toast.makeText(getActivity(), "Morate implementirati intefrace", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
    public interface onListItemClickListener {
        void onListItemClicked(Movie movie);
    }
}