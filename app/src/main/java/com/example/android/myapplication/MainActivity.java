package com.example.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.android.myapplication.model.Apple;
import com.example.android.myapplication.model.Orange;
import com.example.android.myapplication.model.ShoppingCart;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {

    public static final double APPLE_PRICE = 0.30;
    public static final double ORANGE_PRICE = 0.25;

    private String currency;
    private ShoppingCart shoppingCart;

    TextView productPriceView;
    TextView appleView;
    TextView orangeView;
    TextView appleQuantityView;
    TextView orangeQuantityView;
    TextView totalPriceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        shoppingCart = new ShoppingCart();
        currency = getString(R.string.currency);
        productPriceView = findViewById(R.id.product_price);
        appleView = findViewById(R.id.apples);
        orangeView = findViewById(R.id.oranges);
        appleQuantityView = findViewById(R.id.apple_quantity);
        orangeQuantityView = findViewById(R.id.orange_quantity);
        totalPriceView = findViewById(R.id.total_price);

        Button btnAddProduct = findViewById(R.id.btn_add_product);
        final Spinner spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.products_array, R.layout.spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        productPriceView.setText(getString(R.string.apple_price, currency));
                        break;
                    case 1:
                        productPriceView.setText(getString(R.string.orange_price, currency));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spinner.getSelectedItem().toString()) {

                    case "Apple":

                        shoppingCart.addProduct(new Apple(APPLE_PRICE));
                        shoppingCart.addProduct(new Apple(APPLE_PRICE));
                        updateGUI(appleView, appleQuantityView,
                                shoppingCart.calculateAppleQuantity());
                        break;

                    case "Orange":
                            shoppingCart.addProduct(new Orange(ORANGE_PRICE));
                            int orangeQuantity = shoppingCart.calculateOrangeQuantity();
                            if (orangeQuantity %3 == 2 ){
                                shoppingCart.addProduct(new Orange(ORANGE_PRICE));
                            }

                        updateGUI(orangeView, orangeQuantityView,
                                shoppingCart.calculateOrangeQuantity());
                        break;
                }
            }
        });
    }

    private void updateGUI(TextView productView, TextView quantityView, int productQuantity) {
        productView.setVisibility(View.VISIBLE);
        quantityView.setVisibility(View.VISIBLE);
        quantityView.setText(String.valueOf(productQuantity));
        int orangesFromDiscount = shoppingCart.calculateOrangeQuantity() / 3;
        BigDecimal totalPrice = BigDecimal.valueOf(
                shoppingCart.calculateAppleQuantity() * APPLE_PRICE +
                        shoppingCart.calculateOrangeQuantity() * ORANGE_PRICE -
                        orangesFromDiscount * ORANGE_PRICE)
                .setScale(2, BigDecimal.ROUND_HALF_EVEN);
        totalPriceView.setText(String.valueOf(totalPrice));
    }
}
