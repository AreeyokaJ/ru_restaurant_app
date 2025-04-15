package com.example.adapter;

import android.content.Context;
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
import com.example.model.Side;
import com.example.model.SideItem;
import com.example.model.SideOrBeverageItem;
import com.example.model.Size;
import com.example.ru_restaurant_app.R;

import java.util.Arrays;
import java.util.List;

/**
 * RecyclerView adapter for displaying side items with an expandable menu
 * to select size, quantity, and add to the order.
 *
 * This uses a shared hidden menu layout with spinners and a button to add items.
 * The image and name can be tapped to show/hide the options.
 *
 * @author Elvin Xu
 */
public class SideAdapter extends RecyclerView.Adapter<SideAdapter.SideViewHolder> {
    private final List<SideOrBeverageItem> sideList;
    private final Context context;

    /**
     * Constructs a new SideAdapter with a list of Side enums and application context.
     *
     * @param sideList List of Side enum values
     * @param context  Android context used for layout inflation and resource access
     */
    public SideAdapter(List<SideOrBeverageItem> sideList, Context context) {
        this.sideList = sideList;
        this.context = context;
    }

    /**
     * ViewHolder class that holds all views for a single side item in the RecyclerView.
     */
    public static class SideViewHolder extends RecyclerView.ViewHolder {
        LinearLayout clickableRow, optionsLayout;
        TextView sideName;
        ImageView sideImage;
        Spinner sizeSpinner, quantitySpinner;
        Button addToOrder;

        /**
         * Initializes the ViewHolder with references to all necessary UI elements.
         *
         * @param itemView The inflated layout for a single side item
         */
        public SideViewHolder(View itemView) {
            super(itemView);
            clickableRow = itemView.findViewById(R.id.clickableRow);
            optionsLayout = itemView.findViewById(R.id.optionsLayout);
            sideName = itemView.findViewById(R.id.beverageName); // Shared ID
            sideImage = itemView.findViewById(R.id.beverageImage); // Shared ID
            sizeSpinner = itemView.findViewById(R.id.sizeSpinner);
            quantitySpinner = itemView.findViewById(R.id.quantitySpinner);
            addToOrder = itemView.findViewById(R.id.addToOrderButton);
        }
    }

    /**
     * Inflates the layout for each side item and returns a new ViewHolder.
     *
     * @param parent   Parent view group
     * @param viewType View type (not used here)
     * @return A new instance of SideViewHolder
     */
    @Override
    public SideViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false);
        return new SideViewHolder(view);
    }

    /**
     * Binds data to each ViewHolder including name, image, spinners, and button click listeners.
     *
     * @param holder   The ViewHolder to bind data to
     * @param position Position of the current item
     */
    @Override
    public void onBindViewHolder(SideViewHolder holder, int position) {
        Side side = sideList.get(position).getSide();
        holder.sideName.setText(side.toString());

        // Set image resource based on enum name
        String imageName = side.name().toLowerCase();
        int imageRes = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
        holder.sideImage.setImageResource(imageRes);

        // Toggle hidden menu on click
        holder.clickableRow.setOnClickListener(v -> {
            holder.optionsLayout.setVisibility(
                    holder.optionsLayout.getVisibility() == View.GONE ? View.VISIBLE : View.GONE
            );
        });

        // Populate size spinner
        ArrayAdapter<Size> sizeAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                Size.values()
        );
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.sizeSpinner.setAdapter(sizeAdapter);

        // Populate quantity spinner
        ArrayAdapter<Integer> qtyAdapter = new ArrayAdapter<>(
                context,
                android.R.layout.simple_spinner_item,
                Arrays.asList(1, 2, 3, 4, 5)
        );
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.quantitySpinner.setAdapter(qtyAdapter);
        
        // Handle "Add to Order" click
        holder.addToOrder.setOnClickListener(v -> {
            Size size = (Size) holder.sizeSpinner.getSelectedItem();
            int qty = (int) holder.quantitySpinner.getSelectedItem();
            
            SideItem item = new SideItem(side, size, qty);
            OrderManager.getInstance().getCurrentOrder().addItem(item);

            Toast.makeText(context, "Added " + qty + " " + size + " " + side + " to order", Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Returns the total number of side items in the list.
     *
     * @return Item count
     */
    @Override
    public int getItemCount() {
        return sideList.size();
    }
}
