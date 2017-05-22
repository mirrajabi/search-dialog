package ir.mirrajabi.searchdialog.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.sample.customdialogs.ContactSearchDialogCompat;
import ir.mirrajabi.searchdialog.sample.customdialogs.models.ContactModel;
import ir.mirrajabi.searchdialog.sample.models.SampleModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SampleModel item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new ContactSearchDialogCompat<>(MainActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleContacts(),
                        new SearchResultListener<ContactModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   ContactModel item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
    }

    private ArrayList<SampleModel> createSampleData(){
        ArrayList<SampleModel> items = new ArrayList<>();
        items.add(new SampleModel("First item"));
        items.add(new SampleModel("Second item"));
        items.add(new SampleModel("Third item"));
        items.add(new SampleModel("The ultimate item"));
        items.add(new SampleModel("Last item"));
        items.add(new SampleModel("Lorem ipsum"));
        items.add(new SampleModel("Dolor sit"));
        items.add(new SampleModel("Some random word"));
        items.add(new SampleModel("guess who's back"));
        return items;
    }

    private ArrayList<ContactModel> createSampleContacts(){
        ArrayList<ContactModel> items = new ArrayList<>();
        // Thanks to https://randomuser.me for the images
        items.add(new ContactModel("First item", "https://randomuser.me/api/portraits/women/93.jpg"));
        items.add(new ContactModel("Second item", "https://randomuser.me/api/portraits/women/79.jpg"));
        items.add(new ContactModel("Third item", "https://randomuser.me/api/portraits/women/56.jpg"));
        items.add(new ContactModel("The ultimate item", "https://randomuser.me/api/portraits/women/44.jpg"));
        items.add(new ContactModel("Last item", "https://randomuser.me/api/portraits/women/82.jpg"));
        items.add(new ContactModel("Lorem ipsum", "https://randomuser.me/api/portraits/lego/3.jpg"));
        items.add(new ContactModel("Dolor sit", "https://randomuser.me/api/portraits/women/60.jpg"));
        items.add(new ContactModel("Some random word", "https://randomuser.me/api/portraits/women/32.jpg"));
        items.add(new ContactModel("guess who's back", "https://randomuser.me/api/portraits/women/67.jpg"));
        return items;
    }
}
