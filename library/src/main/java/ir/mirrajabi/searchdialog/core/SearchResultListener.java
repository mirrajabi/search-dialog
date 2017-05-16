package ir.mirrajabi.searchdialog.core;

/**
 * Created by MADNESS on 5/14/2017.
 */

public interface SearchResultListener<T> {
    void onSelected(BaseSearchDialogCompat dialog, T item, int position);
}
