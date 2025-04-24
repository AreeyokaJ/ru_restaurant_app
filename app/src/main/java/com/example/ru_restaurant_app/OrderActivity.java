package com.example.ru_restaurant_app;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.manager.OrderManager;
import com.example.model.Order;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Activity responsible for order activity
 * @author AreeyokaJohn
 */
public class OrderActivity extends AppCompatActivity {

    private ListView ordersListView;

    /**
     * Initializes activity
     * @param savedInstanceState If the activity is being re-initialized after
     *     previously being shut down then this Bundle contains the data it most
     *     recently supplied in {@link #onSaveInstanceState}.  <b><i>Note: Otherwise it is null.</i></b>
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_orders);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Bind the ListView
        ordersListView = findViewById(R.id.ordersListView);

        // Get the list of placed orders
        List<Order> orders = OrderManager.getInstance().getOrderHistory();

        // Set up a basic adapter
        ArrayAdapter<Order> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                orders
        );

        ordersListView.setAdapter(adapter);

        onLongPress(ordersListView, orders, adapter);

        Button exportButton = findViewById(R.id.exportOrdersButton);
        onExportOrder(exportButton);

    }

    /**
     * Cancels orders on long press
     * @param ordersListView
     * @param orders
     * @param adapter
     */
    private void onLongPress(ListView ordersListView, List<Order> orders, ArrayAdapter<Order> adapter){
        ordersListView.setOnItemLongClickListener((parent, view, position, id) -> {
            Order selectedOrder = orders.get(position);

            new android.app.AlertDialog.Builder(this)
                    .setTitle("Cancel Order")
                    .setMessage("Are you sure you want to cancel Order #" + selectedOrder.getNumber() + "?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        orders.remove(position); // remove from list
                        adapter.notifyDataSetChanged(); // update ListView
                        OrderManager.getInstance().getOrderHistory().remove(selectedOrder);
                    })
                    .setNegativeButton("No", null)
                    .show();

            return true;
        });
    }

    /**
     * Exports orders to download folder after export button press
     * @param exportButton
     */
    private void onExportOrder(Button exportButton){
        exportButton.setOnClickListener(v -> {
            StringBuilder content = new StringBuilder();
            for (Order order : OrderManager.getInstance().getOrderHistory()) {
                content.append(order.toString()).append("\n\n");
            }

            String fileName = "orders.txt";
            String mimeType = "text/plain";
            OutputStream outputStream = null;

            try {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                values.put(MediaStore.Downloads.MIME_TYPE, mimeType);
                values.put(MediaStore.Downloads.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);

                Uri uri = getContentResolver().insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, values);
                if (uri != null) {
                    outputStream = getContentResolver().openOutputStream(uri);
                }

                if (outputStream != null) {
                    outputStream.write(content.toString().getBytes());
                    outputStream.close();
                    Toast.makeText(this, "Exported to Downloads", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Failed to export orders", Toast.LENGTH_SHORT).show();
                }
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Export failed", Toast.LENGTH_SHORT).show();
            }
        });
    }


}