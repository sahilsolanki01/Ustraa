package com.solanki.sahil.assignment;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.TextView;

import com.solanki.sahil.assignment.adapters.RecyclerAdapter_Product;
import com.solanki.sahil.assignment.adapters.RecyclerAdapter_Tab;
import com.solanki.sahil.assignment.models.Model_Product;
import com.solanki.sahil.assignment.models.Model_Tab;
import com.solanki.sahil.assignment.repositories.Repository_Product;
import com.solanki.sahil.assignment.repositories.Repository_Tab;
import com.solanki.sahil.assignment.utils.OnSwipeTouchListener;
import com.solanki.sahil.assignment.viewmodels.MainActivityViewModel;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class MainActivity extends AppCompatActivity implements Repository_Tab.RepositoryCallback, Repository_Product.RepositoryCallback, RecyclerAdapter_Tab.OnTabListener {
    private RecyclerView recyclerView_tab, recyclerView_product;
    private RecyclerAdapter_Tab recyclerAdapter_tab;
    private RecyclerAdapter_Product recyclerAdapter_product;
    private MainActivityViewModel viewModel;
    private Repository_Tab.RepositoryCallback callback_tab;
    private Repository_Product.RepositoryCallback callback_product;
    private int[] check = new int[2];
    private ArrayList<Model_Tab> arrayList_tab;
    private ArrayList<Model_Product> arrayList_product;
    private Button changeButton, viewMoreButton;
    private boolean toggle = true;
    private TextView textView;
    private int count = 0;
    private View view;


    private View.OnClickListener buttonOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            buttonClicked(v);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();

        textView = findViewById(R.id.textView_order_name);
        changeButton = findViewById(R.id.button_change);
        viewMoreButton = findViewById(R.id.button_view_more);

        changeButton.setOnClickListener(buttonOnClickListener);
        viewMoreButton.setOnClickListener(buttonOnClickListener);

        check[0] = 0;

        recyclerView_product = findViewById(R.id.productRecyclerView);
        recyclerAdapter_product = new RecyclerAdapter_Product(null, this);
        recyclerView_product.setAdapter(recyclerAdapter_product);
        recyclerView_product.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();
                if(count < arrayList_tab.size()-1){
                    count++;
                    tabClicked(count);
                }
            }
            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                if(count > 0){
                    count--;
                    tabClicked(count);
                }
            }
        });

        viewModel = new ViewModelProvider(this).get(MainActivityViewModel.class);
        callback_tab = this;
        callback_product = this;
        viewModel.init_product(callback_product);
        viewModel.init_tab(callback_tab);

        recyclerView_tab = findViewById(R.id.tabRecyclerView);
        recyclerAdapter_tab = new RecyclerAdapter_Tab(null, this, this);
        recyclerView_tab.setAdapter(recyclerAdapter_tab);
    }


    private void initToolBar() {

        Toolbar mToolbar = findViewById(R.id.toolBar);
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);

        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.custom_tool_bar, null);
        actionBar.setCustomView(view);
    }

    private void buttonClicked(View view) {
        switch (view.getId()) {

            case R.id.button_change:
                Log.e(TAG, "buttonClicked: 0");
                PopupMenu menu = new PopupMenu(MainActivity.this, view);

                for (int i = 0; i < arrayList_tab.size(); i++) {
                    menu.getMenu().add(Menu.NONE, i, i + 1, arrayList_tab.get(i).getName());
                }

                menu.show();
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        int i = item.getItemId();

                        String s1 = "Showing for ";
                        String s2 = arrayList_tab.get(i).getName();

                        SpannableString ss1=  new SpannableString(s2);
                        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
                        textView.setText("");
                        textView.append(s1);
                        textView.append(ss1);

                        tabClicked(i);
                        return true;
                    }
                });
                break;

            case R.id.button_view_more:
                Log.e(TAG, "buttonClicked: ");
                if(toggle) {
                    toggle = false;
                    viewMoreButton.setText("[-]View Less");
                    recyclerAdapter_product.setExpanded(true);
                }
                else {
                    toggle = true;
                    viewMoreButton.setText("[+]View More");
                    recyclerAdapter_product.setExpanded(false);
                }
                break;
        }
    }


    @Override
    public void onSuccess_product(LiveData<List<Model_Product>> list) {
        recyclerAdapter_product = new RecyclerAdapter_Product(viewModel.getData_product().getValue(), this);
        recyclerView_product.setAdapter(recyclerAdapter_product);

        viewModel.getData_product().observe(this, new Observer<List<Model_Product>>() {
            @Override
            public void onChanged(List<Model_Product> list) {
                arrayList_product = (ArrayList<Model_Product>) list;
                recyclerAdapter_product.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onSuccess_tab(LiveData<List<Model_Tab>> list) {
        recyclerAdapter_tab = new RecyclerAdapter_Tab(viewModel.getData_tab().getValue(), this, this);
        recyclerView_tab.setAdapter(recyclerAdapter_tab);
        String s1 = "Showing for ";
        String s2 = list.getValue().get(0).getName();

        SpannableString ss1=  new SpannableString(s2);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        textView.setText("");
        textView.append(s1);
        textView.append(ss1);

        viewModel.getData_tab().observe(this, new Observer<List<Model_Tab>>() {
            @Override
            public void onChanged(List<Model_Tab> list) {
                arrayList_tab = (ArrayList<Model_Tab>) list;
                recyclerAdapter_tab.notifyDataSetChanged();
            }
        });
    }


    @Override
    public void onTabClick(int position) {
        tabClicked(position);
    }

    public void tabClicked(int position) {
        Repository_Product.getInstance().repoData_product(callback_product, Integer.parseInt(arrayList_tab.get(position).getId()));
        viewModel.init_product(callback_product);

        ((LinearLayoutManager) recyclerView_tab.getLayoutManager()).scrollToPositionWithOffset(position, 110);

        recyclerAdapter_tab.setSelectedPosition(position);

        if (check.length == 1) {
            check[1] = position;
        } else if(check.length == 2) {
            check[0] = check[1];
            check[1] = position;
        }


        if (check[1] > check[0]) {
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_in_left);
            recyclerView_product.setLayoutAnimation(controller);
            recyclerView_product.scheduleLayoutAnimation();
        } else if (check[1] < check[0]) {
            final LayoutAnimationController controller =
                    AnimationUtils.loadLayoutAnimation(MainActivity.this, R.anim.layout_animation_in_right);
            recyclerView_product.setLayoutAnimation(controller);
            recyclerView_product.scheduleLayoutAnimation();
        }

        String s1 = "Showing for ";
        String s2 = arrayList_tab.get(position).getName();

        SpannableString ss1=  new SpannableString(s2);
        ss1.setSpan(new StyleSpan(Typeface.BOLD), 0, ss1.length(), 0);
        textView.setText("");
        textView.append(s1);
        textView.append(ss1);
    }
}
