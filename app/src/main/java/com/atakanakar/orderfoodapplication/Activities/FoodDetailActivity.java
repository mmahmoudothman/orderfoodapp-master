package com.atakanakar.orderfoodapplication.Activities;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.atakanakar.orderfoodapplication.Model.Food;
import com.atakanakar.orderfoodapplication.R;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetailActivity extends AppCompatActivity {

    private TextView foodName;
    private TextView foodPrice;
    private TextView foodDescription;

    private ImageView imageViewFood;

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private FloatingActionButton fabButtonCart;
    private ElegantNumberButton numberButton;


    private FirebaseDatabase database;
    private DatabaseReference myRefFoodDetail;

    private String foodId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        foodName = findViewById(R.id.textViewFoodName);
        foodPrice = findViewById(R.id.textView_food_price);
        foodDescription = findViewById(R.id.textViewFoodDescription);

        imageViewFood = findViewById(R.id.imageViewFoodDetail);

        collapsingToolbarLayout = findViewById(R.id.collapsingID);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        fabButtonCart = findViewById(R.id.fabButton);
        numberButton = findViewById(R.id.number_button);

        database = FirebaseDatabase.getInstance();
        myRefFoodDetail = database.getReference("foods");

        if (getIntent() != null)
            foodId = getIntent().getStringExtra("food_id");
        if (!foodId.isEmpty()){
            getDatailFood(foodId);
        }

    }
    public void getDatailFood(String foodId){

        myRefFoodDetail.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food food = dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(food.getImage()).into(imageViewFood);

                collapsingToolbarLayout.setTitle(food.getName());

                foodPrice.setText(food.getPrice());
                foodDescription.setText(food.getDescription());
                foodName.setText(food.getName());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
