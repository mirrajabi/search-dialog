package ir.mirrajabi.searchdialog;

import android.support.annotation.NonNull;
import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MADNESS on 5/14/2017.
 */

public class SearchFilter<T extends Searchable> extends Filter {
    private ArrayList<T> mItems;
    private FilterResultListener mFilterResultListener;

    public SearchFilter(List<T> objects, @NonNull FilterResultListener filterResultListener) {
        mFilterResultListener = filterResultListener;
        mItems = new ArrayList<>();
        synchronized (this) {
            mItems.addAll(objects);
        }
    }

    @Override
    protected FilterResults performFiltering(CharSequence chars) {
        String filterSeq = chars.toString().toLowerCase();
        FilterResults result = new FilterResults();
        if (filterSeq != null && filterSeq.length() > 0) {
            ArrayList<T> filter = new ArrayList<>();
            for (T object : mItems)
                if (object.getTitle().toLowerCase().contains(filterSeq))
                    filter.add(object);

            result.values = filter;
            result.count = filter.size();
        } else {
            synchronized (this) {
                result.values = mItems;
                result.count = mItems.size();
            }
        }
        return result;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        ArrayList<T> filtered = (ArrayList<T>) results.values;
        mFilterResultListener.onFilter(filtered);
    }
}
