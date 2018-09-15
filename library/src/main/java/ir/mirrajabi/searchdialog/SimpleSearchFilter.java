package ir.mirrajabi.searchdialog;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import ir.mirrajabi.searchdialog.core.BaseFilter;
import ir.mirrajabi.searchdialog.core.FilterResultListener;
import ir.mirrajabi.searchdialog.core.Searchable;


/**
 * Created by MADNESS on 5/14/2017.
 */

public class SimpleSearchFilter<T extends Searchable> extends BaseFilter {
	private final float mAccuracyPercentage;
	private ArrayList<T> mItems;
	private FilterResultListener mFilterResultListener;
	private boolean mCheckLCS;
	private Comparator<Pair<T, Integer>> mComparator = new Comparator<Pair<T, Integer>>() {
		@Override public int compare(Pair<T, Integer> o1, Pair<T, Integer> o2) {
			if (o1 == null || o2 == null) {
				return 0;
			}
			if (o1.second == null || o2.second == null) {
				return 0;
			}
			return o2.second - o1.second;
		}
	};
	
	public SimpleSearchFilter(
		List<T> objects, @NonNull FilterResultListener filterResultListener,
		boolean checkLCS, float accuracyPercentage
	) {
		mFilterResultListener = filterResultListener;
		mCheckLCS = checkLCS;
		mAccuracyPercentage = accuracyPercentage;
		mItems = new ArrayList<>();
		synchronized (this) {
			mItems.addAll(objects);
		}
	}
	
	public SimpleSearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener) {
		this(objects, filterResultListener, false, 0);
	}
	
	@Override
	protected FilterResults performFiltering(CharSequence chars) {
		doBeforeFiltering();
		String filterSeq = chars.toString().toLowerCase();
		FilterResults result = new FilterResults();
		if (filterSeq.length() <= 0) {
			result.values = mItems;
			result.count = mItems.size();
			return result;
		}
		
		ArrayList<Pair<T, Integer>> filteredItems = new ArrayList<>();
		for (T item : mItems) {
			if (item.getTitle().toLowerCase().contains(filterSeq)) {
				filteredItems.add(new Pair<>(item, filterSeq.length()));
			} else if (mCheckLCS) {
				int lcsLength = StringsHelper.lcs(item.getTitle(), filterSeq).length();
				if (lcsLength > item.getTitle().length() * mAccuracyPercentage) {
					filteredItems.add(new Pair<>(item, lcsLength));
				}
			}
		}
		
		Collections.sort(filteredItems, mComparator);
		
		ArrayList<T> finalResult = new ArrayList<>();
		for (Pair<T, Integer> item : filteredItems) {
			finalResult.add(item.first);
		}
		
		result.values = finalResult;
		result.count = finalResult.size();
		return result;
	}
	
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		ArrayList<T> filtered = (ArrayList<T>) results.values;
		mFilterResultListener.onFilter(filtered);
		doAfterFiltering();
	}
}