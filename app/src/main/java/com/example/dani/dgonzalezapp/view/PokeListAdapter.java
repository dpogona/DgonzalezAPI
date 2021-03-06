package com.example.dani.dgonzalezapp.view;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import com.example.dani.dgonzalezapp.R;
import com.example.dani.dgonzalezapp.model.Poke;


import java.util.ArrayList;
import java.util.List;

public class PokeListAdapter extends RecyclerView.Adapter<PokeListAdapter.PokeListViewHolder>{
    public List<Poke> pokeList = new ArrayList<>();
    FragmentActivity activity;


    public PokeListAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @NonNull
    @Override
    public PokeListViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        final PokeListViewHolder pokeListViewHolder = new PokeListViewHolder(view);

        return new PokeListViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final @NonNull PokeListViewHolder holder, int position) {
        final Poke poke = pokeList.get(position);

        holder.tv_pokename.setText(poke.name);

        int size = String.valueOf(poke.getId()).length();
        if(size == 1 )holder.id.setText("#00" + String.valueOf(poke.getId()));
        else if (size == 2)holder.id.setText("#0" + String.valueOf(poke.getId()));
        else holder.id.setText("#" + String.valueOf(poke.getId()));

        final boolean equipo = activity.getIntent().getBooleanExtra("equipo", false);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(holder.itemView.getContext(), PokeActivity.class);
                intent.putExtra("pokemon_url", poke.url);
                intent.putExtra("equipo", equipo);
                holder.itemView.getContext().startActivity(intent);
            }
        });

        Glide.with(holder.itemView.getContext())
                .asBitmap()
                .load("http://pokestadium.com/sprites/xy/" + poke.name.toLowerCase() + ".gif")
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull final Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            @Override
                            public void onGenerated(@Nullable Palette palette) {
                                holder.contenedor.setBackgroundColor(palette.getLightVibrantColor(Color.CYAN));
                                holder.ig_pokemon.setImageBitmap(resource);
                            }
                        });
                    }
                });
    }

    @Override
    public int getItemCount() {
        return pokeList.size();
    }

    public Poke getPosition(int i) {
        return pokeList.get(i);
    }


    class PokeListViewHolder extends RecyclerView.ViewHolder {
        TextView id;
        TextView tv_pokename;
        ImageView ig_pokemon;
        ConstraintLayout contenedor;

        public PokeListViewHolder(View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.listPokeID);
            tv_pokename = itemView.findViewById(R.id.name);
            ig_pokemon = itemView.findViewById(R.id.imagen);
            contenedor = itemView.findViewById(R.id.contenedor);
        }
    }
}