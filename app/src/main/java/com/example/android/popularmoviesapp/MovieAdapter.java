/*
 * Copyright (C) 2017 Udacity Android Nanodegree Popular Movies Project
 */


package com.example.android.popularmoviesapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmoviesapp.Utils.NetworkUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<Movie> mMoviesList;
    private Context mContext;
    final private ItemClickListener mItemClickListener;

    public interface ItemClickListener{
        void onItemClick(Movie movie);
    }

    public MovieAdapter(Context context, ItemClickListener mItemClickListener){
        mContext = context;
        mMoviesList = new ArrayList<Movie>();
        this.mItemClickListener = mItemClickListener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.grid_movie_item,parent,false);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = mMoviesList.get(position);
        holder.bind(movie);
        Log.d(TAG,"onBindViewHolder:#"+position);
    }

    @Override
    public int getItemCount() {
        return mMoviesList.isEmpty() ? 0 : mMoviesList.size();
    }

    public void setMovieData(List<Movie> movies){

        if(movies==null){
            return;
        }
        mMoviesList.clear();
        mMoviesList.addAll(movies);
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView mImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView)itemView.findViewById(R.id.movie_poster);
            itemView.setOnClickListener(this);
        }

        void bind(Movie movie){
            if(movie == null){
                return;
            }

            String poster = NetworkUtils.buildPosterPath(movie.getPoster(),false);
            //Title for setting content description
            String a11yMoviePoster = movie.getFormattedTitle();

            if(!TextUtils.isEmpty(poster)){
                Picasso.with(mContext).load(poster).into(mImageView);
                mImageView.setContentDescription(a11yMoviePoster);
                int x=0;
                Log.d(TAG,"bind:#"+(x++)+movie.getTitle()+" completed.");
            }
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Movie movie = mMoviesList.get(position);
            Log.d(TAG,"onClick: Clicked on Movie"+movie.getTitle());
            mItemClickListener.onItemClick(movie);
        }

    }
}
