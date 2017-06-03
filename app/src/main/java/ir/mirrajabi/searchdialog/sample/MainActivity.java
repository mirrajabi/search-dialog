package ir.mirrajabi.searchdialog.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.mirrajabi.okhttpjsonmock.interceptors.OkHttpMockInterceptor;
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseFilter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import ir.mirrajabi.searchdialog.sample.customdialogs.ContactSearchDialogCompat;
import ir.mirrajabi.searchdialog.sample.customdialogs.models.ContactModel;
import ir.mirrajabi.searchdialog.sample.customdialogs.models.UserModel;
import ir.mirrajabi.searchdialog.sample.customdialogs.services.UsersService;
import ir.mirrajabi.searchdialog.sample.models.SampleModel;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit mRetrofit;
    private OkHttpClient mOkHttpClient;
    private UsersService mUsersService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mOkHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new OkHttpMockInterceptor(this, 5))
                .build();
        mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://example.com")
                .client(mOkHttpClient)
                .build();
        mUsersService = mRetrofit.create(UsersService.class);
    }

    @OnClick(R.id.button)
    void provideSimpleDialog(){
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

    @OnClick(R.id.button2)
    void provide2CustomContactDialog(){
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

    @OnClick(R.id.button3)
    void provideSimpleDialogWithApiCalls() {
        final SimpleSearchDialogCompat<UserModel> searchDialog =
                new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                        "What are you looking for...?", null, new ArrayList(),
                        new SearchResultListener<Searchable>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   Searchable item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
        BaseFilter apiFilter = new BaseFilter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                doBeforeFiltering();
                FilterResults results = new FilterResults();
                results.values = new ArrayList<UserModel>();
                results.count = 0;
                try {
                    ArrayList<UserModel> users = mUsersService
                            .getFakeUsersBasedOnASearchTag(charSequence.toString())
                            .execute()
                            .body();
                    if(users != null) {
                        results.values = users;
                        results.count = users.size();
                    }
                    return results;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                if(filterResults != null) {
                    ArrayList<UserModel> filtered = (ArrayList<UserModel>) filterResults.values;
                    if(filtered != null)
                        searchDialog.getFilterResultListener().onFilter(filtered);
                    doAfterFiltering();
                }
            }
        };
        searchDialog.setFilter(apiFilter);
        searchDialog.show();
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