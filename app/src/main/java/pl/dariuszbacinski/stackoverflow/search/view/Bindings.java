package pl.dariuszbacinski.stackoverflow.search.view;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pl.dariuszbacinski.stackoverflow.R;

public class Bindings {

    @BindingAdapter("app:src")
    public static void loadImage(ImageView imageView, String url) {
        if(isNotEmpty(url)) {
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

    private static boolean isNotEmpty(String url) {
        return url != null && !url.isEmpty();
    }
}
