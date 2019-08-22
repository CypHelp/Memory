package com.ab.util.umengshare;

/**
 * Created by wolf on 2015/11/25.
 */
public class ShareUtil {

    //配置友盟 组件-社会化分享-设置appkey
//    public static void init(Context context) {
//        initWeiXin(context);
//        initQQ(context);
//    }
//
//    private static void initWeiXin(Context context) {
//        String appID = "wx967daebe835fbeac";
//        String appSecret = "5fa9e68ca3970e87a1f83e563c8dcbce";
//        // 添加微信平台
//        UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
//        wxHandler.addToSocialSDK();
//        // 添加微信朋友圈
//        UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
//        wxCircleHandler.setToCircle(true);
//        wxCircleHandler.addToSocialSDK();
//    }
//
//    private static void initQQ(Context context) {
//        //分享给QQ好友,参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler((Activity) context, "1104787827",
//                "Wc7Dryl3hviYqGe8");
//        qqSsoHandler.addToSocialSDK();
//        //添加QQ空间,参数1为当前Activity，参数2为开发者在QQ互联申请的APP ID，参数3为开发者在QQ互联申请的APP kEY.
//        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler((Activity) context, "1104787827",
//                "Wc7Dryl3hviYqGe8");
//        qZoneSsoHandler.addToSocialSDK();
//    }
//
//    public static void shareWeiXin(UMSocialService mController, Context context, String title, String url, String image) {
//        //设置微信好友分享内容
//        WeiXinShareContent weixinContent = new WeiXinShareContent();
//        //设置title
//        weixinContent.setTitle(title);
//        //设置分享内容跳转URL
//        weixinContent.setTargetUrl(url);
//        //设置分享图片
//        weixinContent.setShareImage(new UMImage(context, image));
//        mController.setShareMedia(weixinContent);
//    }
//
//    public static void shareCicle(UMSocialService mController, Context context, String title, String url, String image) {
//        //设置微信朋友圈内容
//        CircleShareContent circleMedia = new CircleShareContent();
//        //设置title
//        circleMedia.setTitle(title);
//        circleMedia.setShareContent(title);
//        //设置分享内容跳转URL
//        circleMedia.setTargetUrl(url);
//        //设置分享图片
//        circleMedia.setShareImage(new UMImage(context, image));
//        mController.setShareMedia(circleMedia);
//    }
//
//    public static void shareQQ(UMSocialService mController, Context context, String title, String url, String image) {
//        //设置qq
//        QQShareContent qqShareContent = new QQShareContent();
//        //设置title
//        qqShareContent.setTitle(title);
//        qqShareContent.setShareContent(title);
//        //设置分享内容跳转URL
//        qqShareContent.setTargetUrl(url);
//        //设置分享图片
//        qqShareContent.setShareImage(new UMImage(context, image));
//        mController.setShareMedia(qqShareContent);
//    }
//
//    public static void shareQzone(UMSocialService mController, Context context, String title, String url, String image) {
//        //设置qq空间
//        QZoneShareContent qqShareContent = new QZoneShareContent();
//        //设置title
//        qqShareContent.setTitle(title);
//        //设置分享内容跳转URL
//        qqShareContent.setTargetUrl(url);
//        //设置分享图片
//        qqShareContent.setShareImage(new UMImage(context, image));
//        mController.setShareMedia(qqShareContent);
//    }

//    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    // 设置分享内容
//    mController.setShareContent(title+newUrl);
    // 设置分享图片, 参数2为图片的url地址
//    mController.setShareMedia(new UMImage(mContext, icon) );

    //设置新浪SSO handler
//        mController.getConfig().setSsoHandler(new SinaSsoHandler());
//        //设置腾讯微博SSO handler
//        mController.getConfig().setSsoHandler(new TencentWBSsoHandler());
//        mController.getConfig().removePlatform(SHARE_MEDIA.RENREN, SHARE_MEDIA.DOUBAN);
//
//        ShareUtil.init(mContext);
//        ShareUtil.shareCicle(mController, mContext, title, newUrl, icon);
//        ShareUtil.shareQQ(mController, mContext, title, newUrl, icon);
//        ShareUtil.shareQzone(mController, mContext, title, newUrl, icon);
//        ShareUtil.shareWeiXin(mController, mContext, title, newUrl, icon);
//        mController.openShare(this, false);

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        /**使用SSO授权必须添加如下代码 */
//        UMSsoHandler ssoHandler = mController.getConfig().getSsoHandler(requestCode);
//        if (ssoHandler != null) {
//            ssoHandler.authorizeCallBack(requestCode, resultCode, data);
//        }
//    }
}
