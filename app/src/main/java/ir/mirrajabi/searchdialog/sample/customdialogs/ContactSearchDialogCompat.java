package ir.mirrajabi.searchdialog.sample.customdialogs;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.SearchResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;
import ir.mirrajabi.searchdialog.sample.R;
import ir.mirrajabi.searchdialog.sample.customdialogs.adapters.ContactModelAdapter;


/**
 * Created by MADNESS on 5/14/2017.
 */

public class ContactSearchDialogCompat<T extends Searchable> extends BaseSearchDialogCompat<T> {
	private String mTitle;
	private String mSearchHint;
	private SearchResultListener<T> mSearchResultListener;
	
	public ContactSearchDialogCompat(
		Context context, String title, String searchHint,
		@Nullable Filter filter, ArrayList<T> items,
		SearchResultListener<T> searchResultListener
	) {
		super(context, items, filter, null, null);
		init(title, searchHint, searchResultListener);
	}
	
	private void init(
		String title, String searchHint,
		SearchResultListener<T> searchResultListener
	) {
		mTitle = title;
		mSearchHint = searchHint;
		mSearchResultListener = searchResultListener;
	}
	
	@Override
	protected void getView(View view) {
		setContentView(view);
		getWindow().setBackgroundDrawableResource(android.R.color.transparent);
		setCancelable(true);
		TextView txtTitle = (TextView) view.findViewById(ir.mirrajabi.searchdialog.R.id.txt_title);
		final EditText searchBox = (EditText) view.findViewById(getSearchBoxId());
		txtTitle.setText(mTitle);
		searchBox.setHint(mSearchHint);
		view.findViewById(ir.mirrajabi.searchdialog.R.id.dummy_background)
			.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					dismiss();
				}
			});
		final ContactModelAdapter adapter = new ContactModelAdapter<>(getContext(),
			R.layout.image_adapter_item, getItems()
		);
		adapter.setSearchResultListener(mSearchResultListener);
		adapter.setSearchDialog(this);
		setFilterResultListener(new FilterResultListener<T>() {
			@Override
			public void onFilter(ArrayList<T> items) {
				((ContactModelAdapter) getAdapter())
					.setSearchTag(searchBox.getText().toString())
					.setItems(items);
			}
		});
		setAdapter(adapter);
	}
	
	public ContactSearchDialogCompat setTitle(String title) {
		mTitle = title;
		return this;
	}
	
	public ContactSearchDialogCompat setSearchHint(String searchHint) {
		mSearchHint = searchHint;
		return this;
	}
	
	public ContactSearchDialogCompat setSearchResultListener(
		SearchResultListener<T> searchResultListener
	) {
		mSearchResultListener = searchResultListener;
		return this;
	}
	
	@LayoutRes
	@Override
	protected int getLayoutResId() {
		return ir.mirrajabi.searchdialog.R.layout.search_dialog_compat;
	}
	
	@IdRes
	@Override
	protected int getSearchBoxId() {
		return ir.mirrajabi.searchdialog.R.id.txt_search;
	}
	
	@IdRes
	@Override
	protected int getRecyclerViewId() {
		return ir.mirrajabi.searchdialog.R.id.rv_items;
	}
}
