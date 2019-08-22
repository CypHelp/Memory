package com.haha.baselibrary.ioc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Yp_Love
 * Date 2019-04-14
 * Description: View时间注解的Annoctation
 */

//@Target(ElementType.FIELD)代表Annotation的位置  FIELD代表的属性 TYPE类 构造函数上
@Target(ElementType.METHOD)
//@Retention(RetentionPolicy.CLASS) 什么时候生效 CLASS编译时  RUNTIME运行时 SOURCE源码资源
@Retention(RetentionPolicy.RUNTIME)
public @interface OnClick {
    //-->@ViewById(R.id.XXX)
    int[] value();
}
