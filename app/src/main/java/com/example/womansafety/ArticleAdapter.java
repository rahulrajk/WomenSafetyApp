package com.example.womansafety;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    ArrayList<String> arrtitle = new ArrayList<>();
    ArrayList<String> arrcontent = new ArrayList<>();


    void setList(String title, String content){
     this.arrtitle.add(title);
     this.arrcontent.add(content);
    }

    @NonNull
    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyler_articles, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleAdapter.ViewHolder holder, int position) {
        holder.title.setText(arrtitle.get(position));
        holder.content.setText(arrcontent.get(position));
    }

    @Override
    public int getItemCount() {
        return arrtitle.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title, content;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.articletitle);
            content = itemView.findViewById(R.id.articlecontent);
        }
    }
}
