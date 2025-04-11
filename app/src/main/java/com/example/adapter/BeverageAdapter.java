package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.model.BeverageItem;
import com.example.ru_restaurant_app.R;

import java.util.List;

/**
 * RecyclerView adapter for displaying a list of beverage items with flavor and image.
 *
 * Author: Elvin Xu
 */
public class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.BeverageViewHolder> {

    private final List<BeverageItem> beverageList;

    /**
     * Constructs a BeverageAdapter using a list of BeverageItem objects.
     *
     * @param beverageList The list of items to display
     */
    public BeverageAdapter(List<BeverageItem> beverageList) {
        this.beverageList = beverageList;
    }

    /**
     * ViewHolder that holds the beverage item views.
     */
    public static class BeverageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        /**
         * Initializes the view holder with beverage image and name.
         *
         * @param itemView The item view
         */
        public BeverageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.beverageImage);
            textView = itemView.findViewById(R.id.beverageName);
        }
    }

    /**
     * Creates a new ViewHolder for a beverage item.
     *
     * @param parent   The parent view group
     * @param viewType The type of the view
     * @return A new BeverageViewHolder
     */
    @Override
    public BeverageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.beverage_item, parent, false);
        return new BeverageViewHolder(view);
    }

    /**
     * Binds the data from a BeverageItem to the ViewHolder.
     *
     * @param holder   The ViewHolder
     * @param position The position in the list
     */
    @Override
    public void onBindViewHolder(BeverageViewHolder holder, int position) {
        BeverageItem item = beverageList.get(position);
        holder.textView.setText(item.getDisplayName());
        holder.imageView.setImageResource(item.getImageResId());
    }

    /**
     * Gets the number of items in the RecyclerView.
     *
     * @return Item count
     */
    @Override
    public int getItemCount() {
        return beverageList.size();
    }
}
