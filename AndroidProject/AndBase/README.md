#AndBase
**AndBase框架功能介绍**
  这个框架是之前在AndBase的官网上下载的，后来在做项目时不停的把一些常用的工具类、控件等往里面粘，所以就越来越大，功能也越来越全。慢慢的在项目开发中就离不开了，添加依赖后，很多功能都是现成的，需要额外做的就很少了，所以用起来就很方便。总之，就是让人越来越懒的工具集合。
  看着他越来越臃肿，有必要介绍一下他的功能了。
  

###1.libs下添加了几个常用的jar包。

    butterknife大家都懂，一键生成布局里和id映射的view定义，还有点击事件，需要配合butterknife插件使用。
    fastjson是解析json的，配合GsonFormat插件，加上代码里util包下的JsonUtil类，能让json的处理无比迅速。
    glide是加载图片的框架，性能优异，杀人越货之不二利器。搭配com.ab.view.bannerimages包下的ImageDisplay类，一句话加载网络图片（包括gif），实在无比方便。
    xutils也是很不错的框架，注解、网络请求、db等都很不错，平时用一下他的注解功能。
###2.代码activity包

    原来的andbase项目只有个AbActivity，后来我加了几个。AbActivity用法可以去AndBase的官网找，我在里面加了个progressDialog，继承这个AbActivity后可以通过showProgressDialog和closeProgressDialog来显示个菊花圈。
    BaseActivity是用来做分页的，适合于一个activity里是个listview或者gridview，继承他就能完成上拉、下拉的功能，主要做的是分页的处理。用法呢大概是这样：activity布局是这样的
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
              android:orientation="vertical"
              android:layout_width="match_parent"
              android:layout_height="match_parent"
              android:background="@color/main_bg">

    <include
            android:id="@+id/top"
            layout="@layout/title_layout"/>

    <include layout="@layout/pull_fresh_view"/>
</LinearLayout>
title_layout就是标题通用的布局，返回键啊title啊之类的。
pull_fresh_view是这样的
``````
<?xml version="1.0" encoding="utf-8"?>
<com.ab.view.pullview.AbPullToRefreshView
        android:layout_below="@+id/top"
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:id="@+id/mPullRefreshView"
        android:scrollbars="none"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <smoothprogressbar.SmoothProgressBar
            android:layout_below="@+id/top"
            android:id="@+id/sm_progressbar"
            style="@style/GPlusProgressBar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:indeterminate="true"
            android:paddingLeft="1dip"
            android:paddingRight="1dip"
            android:visibility="gone"/>

    <ListView
            android:id="@+id/mListView"
            android:fadingEdge="none"
            android:clipToPadding="false"
            android:layout_width="fill_parent"
            android:layout_height="fill_parent"
            android:scrollbarStyle="outsideOverlay"
            android:cacheColorHint="@android:color/transparent"
            android:dividerHeight="1px"
            android:divider="@color/divider_color"/>

</com.ab.view.pullview.AbPullToRefreshView>
```
就是在这个AbPullToRefreshView包一个ListView或者GridView，这样就可以用BaseActivity来做分页加载了。用法是在Activity里使用initRefreshView方法，需要一些参数requestCode，这个参数是网络请求时标识是哪个请求的，可以参考一下csdn里的HttpUtil[输入链接说明](http://blog.csdn.net/tianyaleixiaowu/article/details/50735991)。然后是map是参数，callback回调，然后传入布局里的AbPullToRefreshView，listview和适配器。里面还有个getHttpUtilListener抽象方法，是用来让使用者指明是用的哪个网络请求类，就返回个HttpUtil就是了。用法我截个图。![这是适配器adapter](http://git.oschina.net/uploads/images/2016/0225/165003_bd4596f5_303698.png "在这里输入图片标题")![输入图片说明](http://git.oschina.net/uploads/images/2016/0225/165144_cafef3b8_303698.png "在这里输入图片标题")
    写完适配器后，调用initRefreshView方法就可以了。在success时注意调用doSuccess传入对象集合，失败时用doFailure就OK了，中间那坨不用管它，如果不需要做多余操作的话，就调用doSuccess和doFailure通知父类做分页就行了。
    然后是ImageBaseActivity，这个是做图片选择、拍照功能的。继承这个类之后，调用showAlbumDialog方法，传入最多选择的图片数量，和回调，回调里面就是拍照或者选择本地相册后的图片地址集合。然后就可以用回调里得到的地址集合做上传啊等各种操作了。
 

###  3.adapter包
    AbCommonAdapter是用来做自定义Adapter的，用法就看看上面的创建adapter的图，比较简单。或者看看这个帖子[输入链接说明](http://blog.csdn.net/tianyaleixiaowu/article/details/50736488)
###  4.bmob包
    这个是给bmob用的基于restapi的java实现，如果你的服务器用的是bmob，那么在java项目里就可以使用Bmob类来做增删改查、短信验证码等等一些功能。这个是给java项目用的，你可以在javaee里面使用，android里bmob有自己的api就不要用这个类了。
### 5.fragment包
    这里面新加的是LazyFragment，就是做延迟加载用的，继承他之后在LazyLoad方法里面做你的操作就OK了，当fragment可见时才去调用lazyload方法。这里插一段话吧，免得待会忘了，就是做双击返回键退出的代码。```
@Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        AbToastUtil.showToast(this, "再按一次退出程序");

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
doubleBackToExitPressedOnce是定义的boolean型变量。
###   6.hongyang包
    这里面主要放的是csdn的hongyang博客里的一些代码。有图片加载的ImageLoader，百分比布局（http://blog.csdn.net/lmj623565791/article/details/46767825），手势放大缩小的ImageView等。
###   7.http包
    这里面新加的就是一个HttpUtil，可以参考http://blog.csdn.net/tianyaleixiaowu/article/details/50735991里面的解释。其他的都是原框架自带的网络请求类。
    由于Android6以后废弃了httpclient，所以后来我用okhttp又做了一个工具类，HttpTool。用法和HttpUtil一样，就是框架改成okhttp了。
###   8.task包
    这个里面的类比较好用，能够替代AsyncTask，里面有线程池、队列等，比较好的几个类。具体用法就搜AbTask网上有相应的教程。适合处理一些线程相关的业务。
###   9.util包
    这里面就是大杂烩了，JudgeTime类是做那种显示几天前、几分钟前用的。TimeCountUtil是做倒计时的，就是获取手机验证码时用的类。其他常用的AbLogUtil，AbSharedUtil是做sharedpreference用的，AbToastUtil。里面类很多，看名字就大概知道是干什么的了。
###   10.view包
    bannerimage是做轮播图的，会自动轮播的那种。用法是在布局里include这个布局，
```
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
        android:layout_width = "match_parent"
        android:layout_alignParentTop = "true"
        android:id = "@+id/frame_layout"
        android:layout_height = "160dip"
        xmlns:android = "http://schemas.android.com/apk/res/android">

    <android.support.v4.view.ViewPager
            android:id = "@+id/banner_viewpager"
            android:layout_width = "fill_parent"
            android:layout_height = "fill_parent"/>

    <LinearLayout
            android:layout_width = "fill_parent"
            android:layout_height = "35dip"
            android:layout_gravity = "bottom"
            android:gravity = "center"
            android:orientation = "vertical">

        <LinearLayout
                android:id = "@+id/dot_linear"
                android:layout_width = "wrap_content"
                android:layout_height = "wrap_content"
                android:orientation = "horizontal"
                android:layout_marginTop = "3dip"
                android:gravity = "center">

        </LinearLayout>
    </LinearLayout>
</FrameLayout>
```
这个就是轮播图和导航小圆点的布局，然后在代码里获取图片信息后展示、设置点击响应
![输入图片说明](http://git.oschina.net/uploads/images/2016/0225/173028_a8b785c8_303698.png "在这里输入图片标题")
new ImageViewPagerDisplay（）后图片就能开始轮播了。

### 11.bouncescrollview包
    里面主要是解决ScrollView和ListView等的冲突用的类
### 12.dialog包
    这里面主要是继承CommonDialog或者BaseDialog，然后设置对话框弹出的位置和动画。其他的类都是动画。用用就知道了。
### 13.drawablecenter包
    这个是textview设置图片属性时居中对齐显示的。
### 14.dropdownmenu包
    ![dropdown图片说明](http://git.oschina.net/uploads/images/2016/0315/104825_9bc1fe64_303698.png "dropdown")
### 15.edittext包
    这个是edittext，当输入内容后右边会出来一个X号图片，点击就清空了edittext，当输入为空时，可以调用shakeAnimation方法来抖几下。
![输入图片说明](http://git.oschina.net/uploads/images/2016/0225/173834_86611ef4_303698.png "在这里输入图片标题")
### 16.gridpasswordview包
    手机支付宝输入密码那个view。6个小格子。
### 17.pullscrollview
    下拉有阻尼效果，顶部图片慢慢显露出来的那个效果，早期的微信有这个效果。
### 18.rippleview
    点击有水波纹效果的view。
### 19.roundimageview
    圆形图片。
### 20.viewpageranim
    viewpager切换动画，3D效果、渐隐等。全是动画效果。
### 21.wheel
    滚轮效果布局，有好几个。

### 其他
    还是自己看源码吧……















