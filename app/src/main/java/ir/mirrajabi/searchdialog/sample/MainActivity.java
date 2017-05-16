package ir.mirrajabi.searchdialog.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SearchDialogCompat;
import ir.mirrajabi.searchdialog.SearchResultListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<SampleSearchModel> items = new ArrayList<>();
        items.add(new SampleSearchModel("First item"));
        items.add(new SampleSearchModel("Second item"));
        items.add(new SampleSearchModel("Third item"));
        items.add(new SampleSearchModel("The ultimate item"));
        items.add(new SampleSearchModel("Last item"));
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SearchDialogCompat(MainActivity.this, "Search...", "Type here your interest...",
                        null, items, new SearchResultListener() {
                    @Override
                    public void onSelected(Object item, int position) {
                        Toast.makeText(MainActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }
}
