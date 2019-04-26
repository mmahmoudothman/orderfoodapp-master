package com.atakanakar.orderfoodapplication.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.atakanakar.orderfoodapplication.Common.Common;
import com.atakanakar.orderfoodapplication.Interfaces.ItemClickListener;
import com.atakanakar.orderfoodapplication.Model.Category;
import com.atakanakar.orderfoodapplication.R;
import com.atakanakar.orderfoodapplication.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    TextView textViewFullName;

    RecyclerView recyclerViewMenu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerOptions<Category> options;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Set Name for each user
        View headerView = navigationView.getHeaderView(0);
        textViewFullName = headerView.findViewById(R.id.textViewNavHeaderID);
        textViewFullName.setText(Common.currentUser.getName());


        //Load Menu

        recyclerViewMenu = findViewById(R.id.rvID);
        recyclerViewMenu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerViewMenu.setLayoutManager(layoutManager);

        loadMenu();

    }

    private void loadMenu() {

        firebaseDatabase = FirebaseDatabase.getInstance();
        myRef = firebaseDatabase.getReference("category");

        options = new FirebaseRecyclerOptions.Builder<Category>()
                .setQuery(myRef, Category.class)
                .build();


        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Category model) {

                holder.textViewMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(holder.imageView);
                final Category clickItem = model;

                //MenuViewHolder'da oluşturulan tıklama özellikleriyle cardView tıklanabilir hale geldi
               /* holder.setOnItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //Get categoryId and send to new activity
                        Intent foodIntent = new Intent(HomeActivity.this,FoodListActivity.class);

                        String categoryIdKey = adapter.getRef(position).getKey();

                        foodIntent.putExtra("categoryID",categoryIdKey);
                        startActivity(foodIntent);


                    }
                });*/
            }

            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_design_layout,viewGroup,false);
                MenuViewHolder holder = new MenuViewHolder(view);

                //MenuViewHolder'da oluşturulan tıklama özellikleriyle cardView tıklanabilir hale geldi
                holder.setOnItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Get categoryId and send to new activity
                        Intent foodIntent = new Intent(HomeActivity.this,FoodListActivity.class);

                        String categoryIdKey = adapter.getRef(position).getKey();

                        foodIntent.putExtra("categoryID",categoryIdKey);
                        startActivity(foodIntent);

                    }
                });
                return holder;
            }
        };

        recyclerViewMenu.setAdapter(adapter);
        adapter.startListening();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        /*int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            Toast.makeText(getApplicationContext(), "menu", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_cart) {
            Toast.makeText(getApplicationContext(), "cart", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_order) {
            Toast.makeText(getApplicationContext(), "order", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_log_out) {
            Toast.makeText(getApplicationContext(), "LOG OUT", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
