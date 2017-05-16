package ir.mirrajabi.searchdialog;

/**
 * Created by MADNESS on 5/14/2017.
 */

public interface SearchResultListener<T> {
    void onSelected(T item, int position);
}
