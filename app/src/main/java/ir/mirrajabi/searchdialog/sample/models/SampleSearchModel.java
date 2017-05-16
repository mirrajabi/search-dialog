package ir.mirrajabi.searchdialog.sample.models;

import ir.mirrajabi.searchdialog.core.Searchable;

/**
 * Created by MADNESS on 5/16/2017.
 */

public class SampleSearchModel implements Searchable {
    private String mTitle;

    public SampleSearchModel(String title) {
        mTitle = title;
    }

    @Override
    public String getTitle() {
        return mTitle;
    }

    public SampleSearchModel setTitle(String title) {
        mTitle = title;
        return this;
    }
}
