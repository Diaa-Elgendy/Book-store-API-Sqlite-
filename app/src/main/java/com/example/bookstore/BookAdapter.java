package com.example.bookstore;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.Holderview> {

    private final List<Book> BookHolderList;
    private Context context;
    private RecyclerViewClickListener listener;

    public BookAdapter(List<Book> bookList, Context context, RecyclerViewClickListener listener) {
        this.BookHolderList = bookList;
        this.context = context;
        this.listener = listener;
    }

    public BookAdapter(List<Book> bookList, Context context) {
        this.BookHolderList = bookList;
        this.context = context;
    }

    @Override
    public Holderview onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_book, null);
        return new Holderview(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(Holderview holder, final int position) {
        holder.txtName.setText(BookHolderList.get(position).bookTitle);
        holder.txtCategory.setText(String.valueOf(BookHolderList.get(position).author));
        Picasso.get().load( BookHolderList.get(position).imageUrl ).placeholder(R.drawable.no_image).fit().centerCrop().into( holder.imageView );
    }

    @Override
    public int getItemCount() {
        return BookHolderList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }
    public class Holderview extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView txtName, txtCategory;
        ImageView imageView;
        CardView cardView;

        public Holderview(View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            txtName = itemView.findViewById(R.id.TvBookName);
            txtCategory = itemView.findViewById(R.id.tvBookCategory);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onClick(v, getAdapterPosition());
        }
    }
}
