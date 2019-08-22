package com.haha.baselibrary.ioc;

import android.app.Activity;
import android.view.View;
import com.haha.baselibrary.R;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: Yp_Love
 * Date 2019-04-14
 * Description:
 */
public class ViewUitls {


    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    //兼容上面三个方法  object-->反射需要执行的类
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);

    }

    /**
     * 注入事件
     */
    private static void injectEvent(ViewFinder finder, Object object) {

        //1.获取类里面的方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();

        //2.获取ViewById的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.finfViewById 找到value值
                    View view = finder.findViewById(viewId);
                    if (view != null) {
                        //4.View.setOnClickListener
                        view.setOnClickListener(new DeclareOnClickListener(method, object));
                    }
                }
            }
        }
    }


    private static class DeclareOnClickListener implements View.OnClickListener {
        //5.反射执行方法
        private Object mObject;
        private Method mMethod;

        public DeclareOnClickListener(Method method, Object object) {
            this.mMethod = method;
            this.mObject = object;
        }

        @Override
        public void onClick(View v) {
            //点击会调用该方法
            try {
                //所有方法都可以运送
                mMethod.setAccessible(true);
                mMethod.invoke(mMethod, v);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入属性
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类中所有的属性
        Class<?> clazz = object.getClass();
        //获取所有属性包括私有和公有
        Field[] fields = clazz.getDeclaredFields();

        //2.获取ViewById的value值
        for (Field field : fields) {
            ViewById viewById = field.getAnnotation(ViewById.class);
            if (viewById != null) {
                int viewId = viewById.value();
                //3.finfViewById 找到value值
                View view = finder.findViewById(viewId);
                //能够注释所有修饰符private public
                field.setAccessible(true);
                //4.动态注入找到view
                try {
                    field.set(object, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
