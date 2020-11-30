package com.ftninformatika.moviesactors.Adapters;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ftninformatika.moviesactors.Models.Movie;
import com.ftninformatika.moviesactors.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class WatchListAdapter extends BaseAdapter {
    private List<Movie> movies = null;
    private Activity activity;

    public WatchListAdapter(Activity activity, List<Movie> movies) {
        this.movies = movies;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Movie getItem(int position) {
        return movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = activity.getLayoutInflater().inflate(R.layout.listview_single_search_item, null);
        }

        ImageView imageMovie = convertView.findViewById(R.id.image_Movie);
        TextView tvTitle = convertView.findViewById(R.id.textView_Title);

        tvTitle.setText(movies.get(position).getTitle() + " (" + movies.get(position).getYear() + ") ");

        Picasso.get().load(movies.get(position).getPoster()).into(imageMovie);

        return convertView;
    }
}

