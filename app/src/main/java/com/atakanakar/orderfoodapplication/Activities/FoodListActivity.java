package com.atakanakar.orderfoodapplication.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.atakanakar.orderfoodapplication.Interfaces.ItemClickListener;
import com.atakanakar.orderfoodapplication.Model.Food;
import com.atakanakar.orderfoodapplication.R;
import com.atakanakar.orderfoodapplication.ViewHolder.FoodViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

public class FoodListActivity extends AppCompatActivity {

    private RecyclerView rv;
    private RecyclerView.LayoutManager manager;

    private FirebaseDatabase database;
    private DatabaseReference myRefFoodList;

    private String categoryId="";

    private FirebaseRecyclerOptions<Food> options;
    private FirebaseRecyclerAdapter<Food, FoodViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        rv = findViewById(R.id.rvFoodListID);
        rv.setHasFixedSize(true);

        manager = new LinearLayoutManager(this);
        rv.setLayoutManager(manager);



        //getIntent:
        if (getIntent()!=null)
        categoryId = getIntent().getStringExtra("categoryID");
            loadListFood(categoryId);



    }

    private void loadListFood(String categoryId) {

        database = FirebaseDatabase.getInstance();
        myRefFoodList = database.getReference("foods");

        Query query = myRefFoodList.orderByChild("menuId").equalTo(categoryId);

        options = new FirebaseRecyclerOptions.Builder<Food>()
                .setQuery(query, Food.class)
                .build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {

                holder.foodName.setText(model.getName());
                Picasso.with(getBaseContext())
                        .load(model.getImage())
                        .into(holder.foodImage);

//                final Food localFoodObject = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        Intent detailIntent = new Intent(FoodListActivity.this,FoodDetailActivity.class);
                        detailIntent.putExtra("food_id",adapter.getRef(position).getKey());
                        startActivity(detailIntent);


                    }
                });

            }

            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext())
                        .inflate(R.layout.card_design_food_list,viewGroup,false);
                FoodViewHolder holder = new FoodViewHolder(view);

                return holder;
            }
        };

        rv.setAdapter(adapter);
        adapter.startListening();

    }

}