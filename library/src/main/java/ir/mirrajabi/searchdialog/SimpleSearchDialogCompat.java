package ir.mirrajabi.searchdialog;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.adapters.SearchDialogAdapter;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by MADNESS on 5/14/2017.
 */

public class SimpleSearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {
    private String mTitle;
    private String mSearchHint;
    private SearchResultListener<T> mSearchResultListener;

    public SimpleSearchDialogCompat(Context context, String title, String searchHint,
                                    @Nullable Filter filter, ArrayList<T> items,
                                    SearchResultListener<T> searchResultListener) {
        super(context, items, filter, null,null);
        init(title, searchHint, searchResultListener);
    }

    private void init(String title, String searchHint,
                      SearchResultListener<T> searchResultListener){
        mTitle = title;
        mSearchHint = searchHint;
        mSearchResultListener = searchResultListener;
    }

    @Override
    protected void getView(View view) {
        setContentView(view);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setCancelable(true);
        TextView txtTitle = (TextView) view.findViewById(R.id.txt_title);
        final EditText searchBox = (EditText) view.findViewById(getSearchBoxId());
        txtTitle.setText(mTitle);
        searchBox.setHint(mSearchHint);
        final SearchDialogAdapter adapter = new SearchDialogAdapter<>(getContext(),
                android.R.layout.simple_list_item_1,getItems());
        adapter.setSearchResultListener(mSearchResultListener);
        adapter.setSearchDialog(this);
        setFilterResultListener(new FilterResultListener<T>() {
            @Override
            public void onFilter(ArrayList<T> items) {
                ((SearchDialogAdapter)getAdapter())
                        .setSearchTag(searchBox.getText().toString())
                        .setItems(items);
            }
        });
        setAdapter(adapter);
    }

    public SimpleSearchDialogCompat setTitle(String title) {
        mTitle = title;
        return this;
    }

    public SimpleSearchDialogCompat setSearchHint(String searchHint) {
        mSearchHint = searchHint;
        return this;
    }

    public SimpleSearchDialogCompat setSearchResultListener(
            SearchResultListener<T> searchResultListener) {
        mSearchResultListener = searchResultListener;
        return this;
    }

    @LayoutRes
    @Override
    protected int getLayoutResId() {
        return R.layout.search_dialog_compat;
    }

    @IdRes
    @Override
    protected int getSearchBoxId() {
        return R.id.txt_search;
    }

    @IdRes
    @Override
    protected int getRecyclerViewId() {
        return R.id.rv_items;
    }
}
