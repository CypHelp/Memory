package com.ab.hongyang;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import com.ab.R;
import com.bumptech.glide.Glide;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * 单张图片显示Fragment
 */
public class NewImageDetailFragment extends Fragment {
    private static boolean local;
    private String mImageUrl;
    private ImageView mImageView;
    private ProgressBar progressBar;

    public static NewImageDetailFragment newInstance(String imageUrl, boolean isLocal) {
        final NewImageDetailFragment f = new NewImageDetailFragment();
        local = isLocal;
        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        f.setArguments(args);


        return f;
    }

    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);

        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (local) {
            mImageUrl = "file://" + mImageUrl;
        }
        Glide.with(getActivity()).load(mImageUrl).centerCrop().crossFade().into(mImageView).onLoadStarted(getActivity().getResources().getDrawable(R.drawable.loading));
//        ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
//            @Override
//            public void onLoadingStarted(String imageUri, View view) {
//                progressBar.setVisibility(View.VISIBLE);
//            }
//
//            @Override
//            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
//                String message = null;
//                switch (failReason.getType()) {
//                    case IO_ERROR:
//                        message = "下载错误";
//                        break;
//                    case DECODING_ERROR:
//                        message = "图片无法显示";
//                        break;
//                    case NETWORK_DENIED:
//                        message = "网络有问题，无法下载";
//                        break;
//                    case OUT_OF_MEMORY:
//                        message = "图片太大无法显示";
//                        break;
//                    case UNKNOWN:
//                        message = "未知的错误";
//                        break;
//                }
//                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
//            }
//
//            @Override
//            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//        BitmapUtils utils = new BitmapUtils(MyApplication.getInstance());
//        utils.display(mImageView, mImageUrl);
//        mAttacher.update();
    }
}
