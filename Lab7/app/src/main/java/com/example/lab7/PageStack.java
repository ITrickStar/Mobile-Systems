package com.example.lab7;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Objects;

@SuppressLint("StaticFieldLeak")
public class PageStack extends AppCompatActivity {
    public static TextView pageStackExpand;
    public static TextView pageStackReduced;
    public static TextView Number;
    private static TabsPagerAdapter adapter;
    private static int added = 0;
    private static int deleted = 0;
    private static int counter = 0;
    private TabLayout tabs;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_stack);

        ViewPager2 viewpager;
        tabs = findViewById(R.id.tabLayout);
        viewpager = findViewById(R.id.tabs_viewpager);
        adapter = new TabsPagerAdapter(getSupportFragmentManager(), getLifecycle(), 1);
        viewpager.setAdapter(adapter);
        viewpager.setUserInputEnabled(true);

        new TabLayoutMediator(tabs, viewpager, (tab, position) -> {
            if (position == 0) {
                tab.setText("Main");
            } else {
                tab.setText("Tab");
            }
        }
        ).attach();

        Button bPreviousPage = findViewById(R.id.PreviousPage);
        bPreviousPage.setOnClickListener(view -> {
            Intent intent = new Intent(PageStack.this, Stopwatch.class);
            startActivity(intent);
        });
    }

    public void addPage(View view) {
        TabLayout.Tab tab = tabs.newTab();
        tab.setText("Tab");
        tabs.addTab(tab, false);
        adapter.setNumberOfTabs(adapter.getItemCount() + 1);
        pageStackExpand.setText("Page Stack Added: " + ++added);
        adapter.notifyDataSetChanged();
    }

    public void deletePage(View view) {
        if (tabs.getTabCount() > 1) {
            tabs.removeTab(Objects.requireNonNull(tabs.getTabAt(1)));
            adapter.setNumberOfTabs(adapter.getItemCount() - 1);
            pageStackReduced.setText("Page Stack Deleted: " + ++deleted);
            adapter.notifyDataSetChanged();
        }
    }

    public static class MainFragment extends Fragment {
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_main, container, false);
            pageStackExpand = v.findViewById(R.id.PageStackExpand);
            pageStackReduced = v.findViewById(R.id.PageStackReduced);
            return v;
        }
    }

    public static class PageFragment extends Fragment {
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.page_stack_fragment, container, false);
            Number = v.findViewById(R.id.Number);
            Number.setText("Number " + ++counter);
            return v;
        }
    }

    static class TabsPagerAdapter extends FragmentStateAdapter {
        public int numberOfTabs;
        FragmentManager fm;
        Lifecycle lifecycle;

        public TabsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle _lifecycle, int n) {
            super(fragmentManager, _lifecycle);
            fm = fragmentManager;
            lifecycle = _lifecycle;
            numberOfTabs = n;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0: {
                    Bundle bundle = new Bundle();
                    bundle.putString("fragmentName", "Main Fragment");
                    MainFragment newFragment = new MainFragment();
                    newFragment.setArguments(bundle);
                    return newFragment;
                }
                case 1: {
                    Bundle bundle = new Bundle();
                    bundle.putString("fragmentName", "Page Fragment");
                    PageFragment newFragment = new PageFragment();
                    newFragment.setArguments(bundle);
                    return newFragment;
                }
                default:
                    return new PageFragment();
            }
        }

        @Override
        public int getItemCount() {
            return numberOfTabs;
        }

        public void setNumberOfTabs(int numberOfTabs) {
            this.numberOfTabs = numberOfTabs;
        }
    }
}
