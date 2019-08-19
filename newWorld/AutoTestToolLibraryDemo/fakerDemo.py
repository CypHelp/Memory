# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: fakerDemo.py
@time: 2019/8/16 0016 09:33
"""
from faker import Faker

"""
   Faker是一个Python包，开源的GITHUB项目，主要用来创建伪数据，使用Faker包，无需再手动生成或者手
写随机数来生成数据，只需要调用Faker提供的方法，即可完成数据的生成
    zh_CN  en_AU
    
    https://www.jianshu.com/p/6bd6869631d9 ->>faker的方法一览
"""

fakerdata = Faker(locale='zh_CN')

#姓名
faker_name=fakerdata.name()
# print(faker_name)

#地址
faker_add=fakerdata.address()
# print(faker_add)

"""
district()：区geo_coordinate()：地理坐标latitude()：地理坐标(纬度)
longitude()：地理坐标(经度)lexify()：替换所有问号（“？”）带有随机字母的事件。
numerify()：三位随机数字postcode()：邮编province()：省份
street_address()：街道地址street_name()：街道名street_suffix()：街、路
random_digit()：0~9随机数random_digit_not_null()：1~9的随机数
random_element()：随机字母random_int()：随机数字，默认0~9999，可以通过设置min,max来设置
random_letter()：随机字母random_number()：随机数字，参数digits设置生成的数字位数
color_name()：随机颜色名hex_color()：随机HEX颜色rgb_color()：随机RGB颜色
safe_color_name()：随机安全色名safe_hex_color()：随机安全HEX颜色
bs()：随机公司服务名company()：随机公司名（长）company_prefix()：随机公司名（短）
company_suffix()：公司性质credit_card_expire()：随机信用卡到期日
credit_card_full()：生成完整信用卡信息credit_card_number()：信用卡号
credit_card_provider()：信用卡类型credit_card_security_code()：信用卡安全码
currency_code()：货币编码am_pm()：AM/PMcentury()：随机世纪date()：随机日期
date_between()：随机生成指定范围内日期，参数：start_date，end_date取值：具体日期或者today,-30d,-30y类似
date_between_dates()：随机生成指定范围内日期，用法同上date_object()：随机生产从1970-1-1到指定日期的随机日期。
date_this_month()：date_this_year()：date_time()：随机生成指定时间（1970年1月1日至今）
date_time_ad()：生成公元1年到现在的随机时间date_time_between()：用法同
datesfuture_date()：未来日期future_datetime()：未来时间month()：随机月份
month_name()：随机月份（英文）past_date()：随机生成已经过去的日期
past_datetime()：随机生成已经过去的时间time()：随机24小时时间
timedelta()：随机获取时间差time_object()：随机24小时时间，time对象
time_series()：随机TimeSeries对象timezone()：随机时区
unix_time()：随机Unix时间year()：随机年份file_extension()：随机文件扩展名
file_name()：随机文件名（包含扩展名，不包含路径）file_path()：随机文件路径（包含文件名，扩展名）
mime_type()：随机mime Typeascii_company_email()：随机ASCII公司邮箱名
ascii_email()：随机ASCII邮箱ascii_free_email()：ascii_safe_email()：company_email()：domain_name()：生成域名
domain_word()：域词(即，不包含后缀)email()：free_email()：free_email_domain()：f.safe_email()：安全邮箱
f.image_url()：随机URL地址ipv4()：随机IP4地址ipv6()：随机IP6地址
mac_address()：随机MAC地址tld()：网址域名后缀(.com,.net.cn,等等，不包括.)
uri()：随机URI地址uri_extension()：网址文件后缀
uri_page()：网址文件（不包含后缀）uri_path()：网址文件路径（不包含文件名）
url()：随机URL地址user_name()：随机用户名isbn10()：随机ISBN（10位）
isbn13()：随机ISBN（13位）job()：随机职位paragraph()：随机生成一个段落
paragraphs()：随机生成多个段落，通过参数nb来控制段落数，返回数组
sentence()：随机生成一句话sentences()：随机生成多句话，与段落类似
text()：随机生成一篇文章（不要幻想着人工智能了，至今没完全看懂一句话是什么意思）
word()：随机生成词语words()：随机生成多个词语，用法与段落，句子，类似
binary()：随机生成二进制编码
boolean()：True/Falselanguage_code()：随机生成两位语言编码
locale()：随机生成语言/国际 信息md5()：随机生成MD5
null_boolean()：NULL/True/Falsepassword()：随机生成密码,可选参数：length：密码长度；special_chars：是否能使用特殊字符；digits：是否包含数字；upper_case：是否包含大写字母；lower_case：是否包含小写字母
sha1()：随机SHA1sha256()：随机SHA256uuid4()：随机UUIDfirst_name()：
first_name_female()：女性名first_name_male()：男性名
first_romanized_name()：罗马名last_name()：last_name_female()：女姓
last_name_male()：男姓last_romanized_name()：name()：随机生成全名
name_female()：男性全名name_male()：女性全名
romanized_name()：罗马名
msisdn()：移动台国际用户识别码，即移动用户的ISDN号码
phone_number()：随机生成手机号phonenumber_prefix()：随机生成手机号段
profile()：随机生成档案信息simple_profile()：随机生成简单档案信息

"""
#市，县
fakerdata.city_suffix()

#国家
fakerdata.country()

#：国家编码
fakerdata.country_code()