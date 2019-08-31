# search-dialog
[![](https://jitpack.io/v/mirrajabi/search-dialog.svg)](https://jitpack.io/#mirrajabi/search-dialog)

An awesome and customizable search dialog with built-in search options.

![search_dialog](https://cloud.githubusercontent.com/assets/8886687/26755439/869f9e6c-48a2-11e7-9e6c-829b573e7730.jpg)

### Usage

First add jitpack to your projects build.gradle file

```gradle
allprojects {
    repositories {
        ...
        maven { url "https://jitpack.io" }
   	}
}
```

Then add the dependency in modules build.gradle file

```gradle
dependencies {
    compile 'com.github.mirrajabi:search-dialog:1.2.3'
}
```

## Simple usage

if you just want to use the simple search dialog first you need to provide searchable items.
to achieve this you should implement [Searchable](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/core/Searchable.java) in your model.

you can see the [SampleSearchModel](https://github.com/mirrajabi/search-dialog/blob/master/app/src/main/java/ir/mirrajabi/searchdialog/sample/models/SampleSearchModel.java) for example :

```java
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
```

now generate some search options in your activity :

```java
    private ArrayList<SampleSearchModel> createSampleData(){
        ArrayList<SampleSearchModel> items = new ArrayList<>();
        items.add(new SampleSearchModel("First item"));
        items.add(new SampleSearchModel("Second item"));
        items.add(new SampleSearchModel("Third item"));
        items.add(new SampleSearchModel("The ultimate item"));
        items.add(new SampleSearchModel("Last item"));
        items.add(new SampleSearchModel("Lorem ipsum"));
        items.add(new SampleSearchModel("Dolor sit"));
        items.add(new SampleSearchModel("Some random word"));
        items.add(new SampleSearchModel("guess who's back"));
        return items;
    }
```

then you just need to add the below lines where you want to show the dialog :

```java
new SimpleSearchDialogCompat(MainActivity.this, "Search...",
                        "What are you looking for...?", null, createSampleData(),
                        new SearchResultListener<SampleSearchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   SampleSearchModel item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
```

The constructor parameters are
```java
SimpleSearchDialogCompat(Context context, String title, String searchHint,
                                    @Nullable Filter filter, ArrayList<T> items,
                                    SearchResultListener<T> searchResultListener)
```

### Loading view(added to [SimpleSearchDialogCompat](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/SimpleSearchDialogCompat.java) in [v1.1](https://github.com/mirrajabi/search-dialog/releases/tag/1.1))

Just use `setLoading(true)` for showing and `setLoading(false)` for hiding it on an instance of [SimpleSearchDialogCompat](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/SimpleSearchDialogCompat.java)

### Changing default adapters text colors(added in [v1.2.1](https://github.com/mirrajabi/search-dialog/releases/tag/1.2.1))

If you want to change the default colors just override these colors in your `colors.xml` or wherever you want [like this](https://github.com/mirrajabi/search-dialog/blob/master/app/src/main/res/values/colors.xml#L6).

```xml
    <color name="searchDialogResultColor"/>
    <color name="searchDialogResultHighlightColor"/>
```

## Advanced usage

### The layout

I used [this layout](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/res/layout/search_dialog_compat.xml) for simple search dialog but you can use anything else.
Of course your layout should have thse two views :
- An EditText to use as search key input
- A RecyclerView for showing the results in it

### The search dialog

You can use your custom layouts, adapters and search options by creating a class inheriting the [BaseSearchDialogCompat](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/core/BaseSearchDialogCompat.java)
take a look at [SimpleSearchDialogCompat](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/SimpleSearchDialogCompat.java) to see an example of how it can be done
You should implement the BaseSearchDialogCompat methods : 

```java
    // handle your view with this one
    protected abstract void getView(View view);
    // Id of your custom layout
    @LayoutRes protected abstract int getLayoutResId();
    // Id of the search edittext you used in your custom layout
    @IdRes protected abstract int getSearchBoxId();
    // Id of the recyclerview you used in your custom layout
    @IdRes protected abstract int getRecyclerViewId();

```
### The search filter

You can use your custom filters for text searching. The one used in [SimpleSearchDialogCompat](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/SimpleSearchDialogCompat.java) is [SimpleSearchFilter](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/SimpleSearchFilter.java).
It checks the search key and if an item and the key had partially exact same letters it will add that item to results and also if the CheckLCS was set to true, it will check if the amount of matching letters was bigger than the given AccuracyPercentage the item will be added to results


### The [adapter](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/adapters/SearchDialogAdapter.java)

the one used in SimpleSearchDialogCompat is so simple despite its too long. the main functionality is in [initializeViews](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/adapters/SearchDialogAdapter.java#L94) method.
you can create your custom adapters and use it instead of this one

### The [StringsHelper](https://github.com/mirrajabi/search-dialog/blob/master/library/src/main/java/ir/mirrajabi/searchdialog/StringsHelper.java)

it has two methods which you can use for highlighting the results.

```java
/*
 * Returns a SpannableString with 
 * highlighted LCS(Longest Common Subsequence)
 * of two strings with the givven color
 */
SpannableStringBuilder highlightLCS(String text1, String text2, int highlightColor);

// Returns the LCS(Longest Common Subsequence) of two strings
String lcs(String text1, String text2) 
```


#### See the [sample app](https://github.com/mirrajabi/search-dialog/tree/master/app) to get a better understanding of the advanced usage

### Used in sample app

- [Okhttp](https://github.com/square/okhttp)
- [OkhttpJsonMock](https://github.com/mirrajabi/okhttp-json-mock)
- [Retrofit](http://github.com/square/retrofit)
- [ButterKnife](https://github.com/JakeWharton/butterknife)
- [Glide](https://github.com/bumptech/glide)
- [Circle-ImageView](https://github.com/hdodenhof/CircleImageView)
- [RandomUser](https://randomuser.me)'s fake api for sample images 

### Changelog

1.2.4 - `Added an option to SimpleSearchDialogCompat so that the dialog cancellation on touching outside the dialog can be customized.`

1.2.3 - `Changed minSdkVersion to 14. Added getter for the title textview of simple search dialog. Improved results sorting.`

1.2.2 - `Gradle tools version and dependencies were updated.`

1.2.1 - `Added an option for changing text color and highlight color of default adapter.`

1.2 - `Added getter for views in simple search dialog and an option to turn off the auto filtering on search edittext.`

1.1.1 - `Fixes drawable overriding issue.`

1.1 - `Added loading feature.`
