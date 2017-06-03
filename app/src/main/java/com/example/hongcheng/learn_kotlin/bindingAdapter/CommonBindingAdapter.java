package com.example.hongcheng.learn_kotlin.bindingAdapter;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.example.hongcheng.learn_kotlin.R;
import com.example.hongcheng.learn_kotlin.base.BaseApplication;
import com.squareup.picasso.Picasso;

/**
 * Created by hongcheng on 16/9/11.
 */
public class CommonBindingAdapter
{
    @BindingAdapter({"imageUrl"})
    public static void setImageUrl(final ImageView view, final String url) {
        view.post(new Runnable() {
            @Override
            public void run() {
                Picasso.with(view.getContext()).load(url)
                        .resize(view.getMeasuredWidth(), view.getMeasuredHeight())
                        .centerCrop()
                        .placeholder(BaseApplication.getInstance().getResources().getDrawable(R.mipmap.example))
                        .error(BaseApplication.getInstance().getResources().getDrawable(R.mipmap.example))
                        .into(view);
            }
        });
    }
}
