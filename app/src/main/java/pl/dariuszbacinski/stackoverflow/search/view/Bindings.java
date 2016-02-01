package pl.dariuszbacinski.stackoverflow.search.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import fr.ganfra.materialspinner.MaterialSpinner;
import pl.dariuszbacinski.stackoverflow.R;
import pl.dariuszbacinski.stackoverflow.search.viewmodel.FilterDisplay;

public class Bindings {

    @BindingAdapter("app:src")
    public static void loadImage(ImageView imageView, String url) {
        if (isNotEmpty(url)) {
            Picasso.with(imageView.getContext()).load(url).noFade().placeholder(R.drawable.ic_image_placeholder).into(imageView);
        } else {
            imageView.setImageResource(R.drawable.ic_image_placeholder);
        }
    }

    @BindingAdapter("app:answered")
    public static void setBackgroundColor(View view, boolean isAnswered) {
        Context context = view.getContext();
        int answeredColor = ContextCompat.getColor(context, R.color.answeredQuestion);
        int notAnsweredQuestion = ContextCompat.getColor(context, R.color.notAnsweredQuestion);
        view.setBackgroundColor(isAnswered ? answeredColor : notAnsweredQuestion);
    }

    @BindingAdapter("app:loading")
    public static void setBackgroundColor(SwipeRefreshLayout swipeRefreshLayout, boolean isAnswered) {
        swipeRefreshLayout.setRefreshing(isAnswered);
    }

    @BindingAdapter({"app:filterValues", "app:onFilterSelected"})
    public static void setSpinnerAdapter(final MaterialSpinner spinner, FilterDisplay filterDisplay, final AdapterView.OnItemSelectedListener listener) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_spinner_item, filterDisplay.filterValues());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setFloatingLabelText(filterDisplay.filterNameRes());
        spinner.setHint(filterDisplay.filterNameRes());
        //XXX array adapter do not count hint element
        final int position = getSelectedItemPosition(spinner, filterDisplay.filterSelected());
        //XXX http://stackoverflow.com/questions/1484528/android-setselection-having-no-effect-on-spinner
        spinner.post(new Runnable() {
            @Override
            public void run() {
                spinner.setSelection(position, false);
                spinner.setOnItemSelectedListener(listener);
            }
        });

    }

    private static int getSelectedItemPosition(MaterialSpinner spinner, String selected) {
        int count = spinner.getAdapter().getCount();

        for (int i = 0; i < count; i++) {
            if (spinner.getAdapter().getItem(i).equals(selected)) {
                return i;
            }
        }

        return 0;
    }

    private static boolean isNotEmpty(String url) {
        return url != null && !url.isEmpty();
    }
}
