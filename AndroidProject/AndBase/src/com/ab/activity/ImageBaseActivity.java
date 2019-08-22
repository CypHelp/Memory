package com.ab.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.ab.R;
import com.ab.activity.chooseimage.ImageBucketChooseActivity;
import com.ab.activity.listener.ImageListener;
import com.ab.constant.Constant;
import com.ab.constant.IntentConstants;
import com.ab.util.AbImageUtil;
import com.ab.view.dialog.sheet.ActionSheetDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by wolf on 2015/11/3.
 * 仿朋友圈添加上传图片功能，能拍照或者选择本地相册，继承该类后调用showAlbumDialog方法，传入回调接口，接收返回的本地图片地址集合
 */
@SuppressWarnings("ALL")
public abstract class ImageBaseActivity extends BaseActivity {
    private static final int TAKE_PICTURE = 0x000100;
    private static final int TAKE_HEAD_PICTURE = 0x000101;
    private static final int ZOOM_PICTURE = 0x000200;
    private static final int PHOTO_REQUEST_CUT = 0x000300;
    private static final int PHOTO_REQUEST_GALLERY = 0x000301;
    /**
     * 选择相册还是相机的对话框
     */
    private AlertDialog albumDialog;
    /**
     * 相机拍照返回的图片地址
     */
    private String mPath;
    private Context mContext;


    private int mRequestCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext =this;
    }

    public void showAlbumDialog(Context context, int mAvailSize, ImageListener addImageListener) {
        albumDialog = new AlertDialog.Builder(context).create();
        albumDialog.setCanceledOnTouchOutside(true);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_usericon, null);
        albumDialog.show();
        albumDialog.setContentView(v);
        albumDialog.getWindow().setGravity(Gravity.CENTER);


        TextView albumPic = (TextView) v.findViewById(R.id.album_pic);
        TextView cameraPic = (TextView) v.findViewById(R.id.camera_pic);
        albumPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                albumDialog.dismiss();
                openPhoto(mAvailSize,addImageListener);
            }
        });
        cameraPic.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                albumDialog.dismiss();

                takePhoto(false,addImageListener);
            }
        });
    }
    public void openPhoto(int mAvailSize, ImageListener addImageListener){
        mRequestCode = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
        Constant.imageListenerMap.put(mRequestCode, addImageListener);
        Intent intent = new Intent(mContext,
                ImageBucketChooseActivity.class);
        //带着还可以添加的图片数量
        intent.putExtra(IntentConstants.EXTRA_CAN_ADD_IMAGE_SIZE,
                mAvailSize);
        intent.putExtra(IntentConstants.REQUEST_CODE, mRequestCode);
        mContext.startActivity(intent);
    }

    public void takeHeadIcon(Context context, ImageListener addImageListener) {
        mContext = context;
        new ActionSheetDialog(context).builder().setCancelable(false).setCanceledOnTouchOutside(false)
                .addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        takePhoto(true,addImageListener);
                    }

                }).addSheetItem("从相册中选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
            }
        }).show();
    }

    /**
     * 用相机拍照
     */
    public void takePhoto(boolean isHeadIcon,ImageListener addImageListener) {
        mRequestCode = new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE);
        Constant.imageListenerMap.put(mRequestCode, addImageListener);
        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File vFile = new File(Environment.getExternalStorageDirectory()
                + "/myimage/", String.valueOf(System.currentTimeMillis())
                + ".jpg");
        if (!vFile.exists()) {
            File vDirPath = vFile.getParentFile();
            vDirPath.mkdirs();
        } else {
            if (vFile.exists()) {
                vFile.delete();
            }
        }
        mPath = vFile.getPath();
        Uri cameraUri = Uri.fromFile(vFile);
        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
        if (isHeadIcon) {
            ((Activity) mContext).startActivityForResult(openCameraIntent, TAKE_HEAD_PICTURE);
        } else {
            ((Activity) mContext).startActivityForResult(openCameraIntent, TAKE_PICTURE);
        }
    }

    /**
     * 拍照完成返回调用
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_CANCELED) {
            Constant.imageListenerMap.get(mRequestCode).allImages(new ArrayList<>());
        } else if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    List<String> paths = new ArrayList<>();
                    paths.add(mPath);
                    Constant.imageListenerMap.get(mRequestCode).allImages(paths);
                    break;
                case PHOTO_REQUEST_CUT:
                    try {
                        Bitmap bitmap = data.getParcelableExtra("data");
                        Bitmap roundBitmap = AbImageUtil.toRoundBitmap(bitmap);
                        String imagePath = AbImageUtil.savePic(roundBitmap, 100, "myimage/");
                        List<String> list = new ArrayList<>();
                        list.add(imagePath);
                        Constant.imageListenerMap.get(mRequestCode).allImages(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case TAKE_HEAD_PICTURE:
                    startPhotoZoom(Uri.fromFile(new File(mPath)));
                    break;
                case PHOTO_REQUEST_GALLERY:
                    if (data != null) {
                        // 得到图片的全路径
                        Uri uri = data.getData();
                        startPhotoZoom(uri);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startPhotoZoom(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

}