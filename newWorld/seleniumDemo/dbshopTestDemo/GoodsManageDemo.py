# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: GoodsManageDemo.py
@time: 2019/8/1 0001 16:43
"""

from AutoTestPlatform.web.WebDriver import Driver

driver = Driver()

#登录Dbshop
driver.get("http://192.168.1.16/DBshop/admin")
driver.find_element_by_id_data("user_name", 'admin')
driver.find_element_by_id_data("user_passwd", "123456")
driver.find_element_by_xpath('//*[@id="admin_login_form"]/button').click()


#--------------------------------------------------------------------------------------------
#进入商品管理，添加商品
driver.find_element_by_xpath('/html/body/div[1]/div/ul[1]/li[3]/a').click()
driver.find_element_by_xpath('/html/body/div[1]/div/ul[1]/li[3]/ul/li[1]/a').click()
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/p[2]/a[1]').click()

#商品基本信息
driver.find_element_by_id_data('goods_name',"ipad ")
driver.find_element_by_id_data('goods_extend_name',"mini5")
driver.find_element_by_id_data('goods_item',"0551")
driver.find_element_by_id_data('goods_price',"6000")
driver.find_element_by_id_data('goods_shop_price',"5999")
driver.find_element_by_xpath('//*[@id="goods_a"]/div[2]/div[7]/div/table/tbody/tr/td[2]/input').send_keys("5899")
driver.find_element_by_id_data("virtual_sales","1000")
driver.find_element_by_id_data("goods_weight","15")
driver.switch_to_iframe(driver.find_element_by_id("ueditor_0"))
driver.find_element_by_xpath('/html/body').send_keys("最实用的ipad，你值得拥有")
driver.switch_to_parent_handle()
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button').click()


#对商品进行分类
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/table/tbody/tr[2]/td[9]/a[1]').click()
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[3]/a').click()
driver.find_element_by_id('class_id_14').click()
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()



#goods库存
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[5]/a').click()
driver.find_element_by_id_data('goods_stock','1000000')
driver.find_element_by_id_data('goods_out_of_stock_set','250')
driver.find_element_by_id_data('goods_cart_buy_min_num','1')
driver.find_element_by_id_data('goods_cart_buy_max_num','99')
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()

#优惠价格
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[6]/a').click()
driver.find_element_by_id_data('goods_preferential_price',"4999")
driver.find_element_by_id_data('goods_preferential_start_time',"2019-08-05 14:25")
driver.find_element_by_id_data('goods_preferential_end_time',"2019-08-09 14:25")
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()


#销售规格
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[7]/a').click()
driver.find_element_by_id('ff0000').click()
driver.find_element_by_id('other1').click()
driver.find_element_by_id_data('price_ff0000other1',"6000")
driver.find_element_by_id_data('stock_ff0000other1','100')
driver.find_element_by_id_data('item_ff0000other1','0551-001')
driver.find_element_by_id_data('weight_ff0000other1',"15")
driver.find_element_by_xpath('//*[@id="select_goods_color_size_in"]/tbody/tr/td[8]/table/tbody/tr/td[2]/input').send_keys('2999')
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()


#商品属性
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[8]/a').click()
driver.find_element_by_id('attribute_group_id').click()
driver.find_element_by_xpath('//*[@id="attribute_group_id"]/option[2]').click()
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()

#商品标签
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[9]/a').click()
driver.find_element_by_xpath('//*[@id="goods_l"]/div[2]/div[2]/div/label[1]/input').click()
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()

#商品自定义
driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[10]/a').click()
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[2]/label/input').send_keys('蔡徐坤！！！必备')
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[2]/div/input').send_keys("你其实不止是会'唱跳rap打篮球'，ipad给你带来新世界的one piece~")
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[2]/div/label/input').click()
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[3]/label/input').send_keys("22世纪的大佬们！！！")
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[3]/div/input').send_keys("大佬无处不在，因为这是22世纪，拥有ipad，你离大佬只是一步之遥")
driver.find_element_by_xpath('//*[@id="goods_f"]/div[2]/div[3]/div/label/input').click()
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()


# #关联商品
#
# driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[11]/a').click()
# driver.find_element_by_xpath('//*[@id="relationgoods_id"]').execute_script('type="visable"')
# sleep(2)
# driver.find_element_by_id_data('relationgoods_id','2')
# # d = dr.find_element_by_xpath('//*[@id="mainImgclass"]/div[2]/input')
# # dr.execute_script('arguments[0].removeAttribute(\"style\")', d)
# # driver.find_element_by_id('relationgoods_id').set_element_visable('visable')
# # driver.find_element_by_id_data('relationgoods_id',"2")
# # driver.find_element_by_xpath('//*[@id="relation_goods_keyword"]').send_keys('索尼').key_down(Keys.ENTER)
# # driver.find_element_by_id_data('relationgoods_id','2').set_element_visable("type='visable'")
# driver.find_element_by_xpath('//*[@id="goods_n"]/div[2]/div[2]/button').click()
# driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()


# #相关商品
# driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[12]/a').click()
# driver.find_element_by_id_data('related_goods_keyword','苹果(Apple) iPhone X 64GB 深空灰色 移动联通电信全网通4G手机')
# driver.find_element_by_xpath('//*[@id="goods_e"]/div[2]/div[2]/button').click()
# driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()
#
# #组合商品
# driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[13]/a').click()
# driver.find_element_by_id_data('combination_goods_keyword','苹果(Apple) iPhone X 64GB 深空灰色 移动联通电信全网通4G手机')
# driver.find_element_by_xpath('//*[@id="goods_m"]/div[2]/div[2]/button').click()
# driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()
#
#
#
# #商品评价
# driver.find_element_by_xpath('/html/body/div[2]/div/div[2]/div/ul/li[14]/a').click()
#
# driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[1]').click()
#
#

#保存商品
driver.find_element_by_xpath('//*[@id="sticky_navigation_right"]/button[2]').click()


#--------------------------------------------------------------------------------------

#商品分类
driver.find_element_by_xpath('/html/body/div[1]/div/ul[1]/li[3]/a').click()
driver.find_element_by_xpath('/html/body/div[1]/div/ul[1]/li[3]/ul/li[2]/a')
driver.find_element_by_xpath('/html/body/div[1]/div/ul[1]/li[3]/ul/li[2]/ul/li[2]/a').click()

#添加侧边信息
driver.find_element_by_xpath('//*[@id="sticky_navigation"]/p[2]/a[1]').click()
driver.find_element_by_id_data('frontside_name','我是你得不到的baba')
driver.find_element_by_id_data('frontside_url','https://ask.csdn.net/questions/664268')
driver.find_element_by_xpath('//*[@id="frontside_class_id"]/option[10]').click()
driver.find_element_by_xpath('//*[@id="sticky_navigation"]/div[2]/button').click()



#-----------------------------------------------------------------------------------------
#退出登录

driver.find_element_by_xpath('/html/body/div[2]/div/div[1]/p[2]/a[2]').click()
driver.close()

