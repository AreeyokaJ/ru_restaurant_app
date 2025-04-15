package com.example.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.manager.OrderManager;
import com.example.model.Beverage;
import com.example.model.SideOrBeverageItem;
import com.example.model.Size;
import com.example.ru_restaurant_app.R;

import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView adapter for displaying a list of beverage items with flavor and image.
 *
 * Author: Elvin Xu
 */
public class BeverageAdapter extends RecyclerView.Adapter<BeverageAdapter.BeverageViewHolder> {

    private final List<SideOrBeverageItem> beverageList;

    /**
     * Constructs a BeverageAdapter using a list of BeverageItem objects.
     *
     * @param beverageList The list of items to display
     */
    public BeverageAdapter(List<SideOrBeverageItem> beverageList) {
        this.beverageList = beverageList;
    }

    /**
     * ViewHolder that holds the beverage item views.
     */
    public static class BeverageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        LinearLayout optionsLayout;
        Spinner sizeSpinner;
        Spinner quantitySpinner;
        Button addToOrderButton;
        LinearLayout clickableRow;

        /**
         * Initializes the view holder with beverage image and name.
         *
         * @param itemView The item view
         */
        public BeverageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.beverageImage);
            textView = itemView.findViewById(R.id.beverageName);
            optionsLayout = itemView.findViewById(R.id.optionsLayout);
            sizeSpinner = itemView.findViewById(R.id.sizeSpinner);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);
            addToOrderButton = itemView.findViewById(R.id.addToOrderButton);
            clickableRow = itemView.findViewById(R.id.clickableRow);
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
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
        SideOrBeverageItem item = beverageList.get(position);
        holder.textView.setText(item.getDisplayName());
        holder.imageView.setImageResource(item.getImageResId());

        // Allows user to click on Beverage and add to order
        holder.clickableRow.setOnClickListener(v -> {
            if (holder.optionsLayout.getVisibility() == View.GONE) {
                holder.optionsLayout.setVisibility(View.VISIBLE);
            } else {
                holder.optionsLayout.setVisibility(View.GONE);
            }
        });

        //Add logic to populate handle Spinners and button
        ArrayAdapter<Size> sizeAdapter = new ArrayAdapter<>(
          holder.itemView.getContext(),
          android.R.layout.simple_spinner_item,
          Size.values()
        );
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.sizeSpinner.setAdapter(sizeAdapter);

        // Populate Quantity Spinner
        List<Integer> quantities = Arrays.asList(1, 2, 3, 4, 5);
        ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(
                holder.itemView.getContext(),
                android.R.layout.simple_spinner_item,
                quantities
        );
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(qtyAdapter);

        // Handle add to order
        holder.addToOrderButton.setOnClickListener(v -> {
            Size selectedSize = (Size) holder.sizeSpinner.getSelectedItem();
            int quantity = (int) holder.quantitySpinner.getSelectedItem();

            Beverage beverage = new Beverage(selectedSize, item.getFlavor(), quantity);
            OrderManager.getInstance().getCurrentOrder().addItem(beverage);

            Toast.makeText(holder.itemView.getContext(),
                    "Added " + quantity + " " + selectedSize + " " + item.getFlavor().toString() + " to order",
                    Toast.LENGTH_SHORT).show();
        });
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
