package pl.dariuszbacinski.stackoverflow.search.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
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

    @BindingAdapter("app:filterValues")
    public static void setSpinnerAdapter(MaterialSpinner spinner, FilterDisplay filterDisplay) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(spinner.getContext(), android.R.layout.simple_spinner_item, filterDisplay.filterValues());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setFloatingLabelText(filterDisplay.filterNameRes());
        spinner.setHint(filterDisplay.filterNameRes());
        spinner.setSelection(adapter.getPosition(filterDisplay.filterSelected()) + 1);
    }

    private static boolean isNotEmpty(String url) {
        return url != null && !url.isEmpty();
    }
}
