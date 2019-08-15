import logging

import time
from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.by import By
from selenium.webdriver.remote.webelement import WebElement
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.support.select import Select
from selenium.webdriver.support.wait import WebDriverWait

from AutoTestPlatform.tools.date import DateTime
from AutoTestPlatform.tools.file import FileUtil
from AutoTestPlatform.web.FireFoxProfile import FireFoxProfile


class Driver:

    def __init__(self, kind="Chrome", executable_path="D:\Program Files (x86)\Google\Chrome\Application\chromedriver"):
        self.executable_path = FileUtil.check_execute_able_path(executable_path)
        logging.info("正在初始化{}浏览器".format(kind))
        self.__driver = self.init(kind)
        logging.info("{}浏览器初始化成功".format(kind))
        self.use_color = False

    def init(self, kind):
        kind = kind.lower()
        if kind == "firefox":
            return webdriver.Firefox(executable_path=self.executable_path)
        elif kind == "chrome":
            return webdriver.Chrome(executable_path=self.executable_path)
        elif kind == "edge":
            return webdriver.Edge(executable_path=self.executable_path)
        elif kind == "ie":
            return webdriver.Ie(executable_path=self.executable_path)
        elif kind == "opera":
            return webdriver.Opera(executable_path=self.executable_path)
        elif kind == "phantomJS":
            return webdriver.PhantomJS(executable_path=self.executable_path)
        elif kind == "safari":
            return webdriver.Safari(executable_path=self.executable_path)
        else:
            raise TypeError("不支持该浏览器:【{}】".format(kind))

    def find_element(self, method, value, timeout=30):
        """
        带有智能等待的定位方法
        :param method: 定位方式
        :param value: 定位值
        :param timeout: 最大超时时间，如果这个时间内没有找到该元素，将会抛出TimeOutException
        :return:
        """
        ele = WebDriverWait(self.__driver, timeout). \
            until(EC.visibility_of_element_located((method, value)),
                  message="没有找到该元素 定位方式：【{}】,值为【{}】,请检查是否有新窗口跳出或者没有切换iframe".format(method, value))
        if isinstance(ele, WebElement):
            self.execute_element_with_color(ele)
            return ele
        raise NoSuchElementException(ele)

    def find_element_by_id(self, value):
        return self.find_element(By.ID, value)

    def find_element_by_id_data(self, value, data):
        return self.find_element(By.ID, value).send_keys(data)

    def find_element_by_name(self, value):
        return self.find_element(By.NAME, value)

    def find_element_by_xpath(self, value):
        return self.find_element(By.XPATH, value)

    def find_element_by_tag_name(self, value):
        return self.find_element(By.TAG_NAME, value)

    def find_element_by_link_text(self, value):
        return self.find_element(By.LINK_TEXT, value)

    def find_element_by_class_name(self, value):
        return self.find_element(By.CLASS_NAME, value)

    def find_element_by_css_selector(self, value):
        return self.find_element(By.CSS_SELECTOR, value)

    def find_element_by_partial_link_text(self, value):
        return self.find_element(By.PARTIAL_LINK_TEXT, value)

    def find_element_by_js_query_selector(self, js):
        e = self.__driver.execute_script('return document.querySelector("{}")'.format(js))
        if isinstance(e, WebElement):
            return e
        raise NoSuchElementException("根据表达式【{}】没有找到该元素".format(js))

    def find_elements(self, method, value, timeout=30):
        """
        带有智能等待的定位方法，多元素
        :param method: 定位方式
        :param value: 定位值
        :param timeout: 最大超时时间，如果这个时间内没有找到该元素，将会抛出TimeOutException
        :return:
        """
        ele = WebDriverWait(self.__driver, timeout). \
            until(EC.visibility_of_all_elements_located((method, value)),
                  message="没有找到该元素 定位方式：【{}】,值为【{}】,请检查是否有新窗口跳出或者没有切换iframe".format(method, value))
        if isinstance(ele, list):
            return ele
        raise NoSuchElementException(ele)

    def find_elements(self, method, value, timeout=30):
        """
        带有智能等待的定位方法，多元素
        :param method: 定位方式
        :param value: 定位值
        :param timeout: 最大超时时间，如果这个时间内没有找到该元素，将会抛出TimeOutException
        :return:
        """
        ele = WebDriverWait(self.__driver, timeout). \
            until(EC.visibility_of_all_elements_located((method, value)),
                  message="没有找到该元素 定位方式：【{}】,值为【{}】,请检查是否有新窗口跳出或者没有切换iframe".format(method, value))
        if isinstance(ele, list):
            return ele
        raise NoSuchElementException(ele)

    def find_elements_by_id(self, value):
        return self.find_elements(By.ID, value)

    def find_elements_by_name(self, value):
        return self.find_elements(By.NAME, value)

    def find_elements_by_xpath(self, value):
        return self.find_elements(By.XPATH, value)

    def find_elements_by_tag_name(self, value):
        return self.find_elements(By.TAG_NAME, value)

    def find_elements_by_link_text(self, value):
        return self.find_elements(By.LINK_TEXT, value)

    def find_elements_by_class_name(self, value):
        return self.find_elements(By.CLASS_NAME, value)

    def find_elements_by_css_selector(self, value):
        return self.find_elements(By.CSS_SELECTOR, value)

    def find_elements_by_partial_link_text(self, value):
        return self.find_elements(By.PARTIAL_LINK_TEXT, value)

    def find_elements_by_js_query_selector(self, js):
        e = self.__driver.execute_script('return document.querySelectorAll("{}")'.format(js))
        if isinstance(e, list):
            return e
        raise NoSuchElementException("根据表达式【{}】没有找到该元素".format(js))

    def title(self):
        """
        返回当前的网页标题
        :return:  None
        """
        return self.__driver.title

    def current_url(self):
        """
        获取当前URL
        :return: string
        """
        return self.__driver.current_url

    def switch_to_first_window(self):
        """
        切换到第一个窗口
        :return: None
        """
        self.__driver.switch_to.window(self.__driver.window_handles[0])

    def switch_to_last_window(self):
        """
        切换到最后一个窗口
        :return: None
        """
        self.__driver.switch_to.window(self.__driver.window_handles[len(self.__driver.window_handles) - 1])

    def switch_to_window_index(self, index):
        """
        根据索引切换窗口
        :param index: 索引位置
        :return: None
        """
        assert 0 < index < len(self.__driver.window_handles), "index out of bounds"
        self.__driver.switch_to.window(self.__driver.window_handles[index])

    def current_windows_handle(self):
        """
        获取当前窗口句柄
        :return: string
        """
        return self.__driver.current_window_handle

    def switch_to_parent_handle(self):
        """
        返回主frame
        :return: None
        """
        self.__driver.switch_to.default_content()

    def switch_to_iframe_element(self, method, value):
        """
        带有智能等待的切换iframe方法
        :param method: 定位方式
        :param value:  定位表达式
        :return: None
        """
        self.__driver.switch_to.frame(self.find_element(method, value))

    def switch_to_iframe(self, ele):
        """
        切换iframe
        :param ele: iframe的name,索引,WebElement对象
        :return: None
        """
        self.__driver.switch_to.frame(ele)

    @property
    def driver(self):
        """
        获取原生WebDriver对象
        :return: WebDriver
        """
        return self.__driver

    def save_screenshot(self):
        """
        根据日期时间创建文件夹并截图
        :return: None
        """
        filename = FileUtil.current_date_dir()
        current_time = DateTime.current_time() + ".png"
        filename = FileUtil.path_join(filename, current_time)
        self.__driver.save_screenshot(filename)

    def get(self, url):
        self.__driver.get(url)

    def __enter__(self):
        return self

    def __exit__(self, exc_type, exc_val, exc_tb):
        self.__driver.close()

    @staticmethod
    def stop(sec):
        """
        休眠
        :param sec: 休眠秒数
        :return: None
        """
        if sec < 0:
            sec = 0
        time.sleep(sec)

    def implicitly_wait(self, sec):
        """
        隐式等待
        :param sec: 秒数
        :return: None
        """
        self.__driver.implicitly_wait(sec)

    def scroll_to_up(self):
        """
        移动到最上边
        :return: None
        """
        self.__driver.execute_script("document.documentElement.scrollTop=scrollMaxX")

    def scroll_to_down(self):
        """
        移动到最下边
        :return: None
        """
        self.__driver.execute_script("document.documentElement.scrollTop=scrollMaxY")

    def scroll_to_right(self):
        """
        移动到最右边
        :return: None
        """
        self.__driver.execute_script("document.documentElement.scrollLeft=document.documentElement.scrollLeftMax")

    def scroll_to_left(self):
        """
        移动到最左边
        :return: None
        """
        self.__driver.execute_script("document.documentElement.scrollLeft=0")

    def scroll_to_position(self, x, y):
        """
        将滚动条滚动到指定坐标
        :param x: 要移动的x坐标
        :param y: 要移动的y坐标
        :return:None
        """
        self.__driver.execute_script("document.documentElement.scrollTo({0},{1}})".format(x, y))

    def scroll(self, start, end):
        """
        将滚动条滚动到指定坐标
        :param x: 要移动的x坐标
        :param y: 要移动的y坐标
        :return:None
        """
        self.__driver.execute_script("window.scrollTo({0},{1})".format(start, end))

    def scroll_to_element(self, Id):
        """
        将元素移动到可见区域
        :param Id: 元素id
        :return:None
        """
        self.__driver.execute_script('document.getElementById("{}").scrollIntoView(true)'.format(Id))

    def get_element_functional(self, element, functional):
        """
        获取元素属性
        :param element:  定位到的元素
        :param functional:  元素值
        :return: string,list,dict
        """
        return self.__driver.execute_script('return arguments[0].{}'.format(functional), element)

    def get_element_inner_html(self, element):
        """
        获取元素内部的所有子标签
        :param element: 定位到的元素
        :return: string
        """
        return self.get_element_functional(element, "innerHTML")

    def get_element_inner_text(self, element):
        """
        获取元素内部的文本值
        :param element: 定位到的元素
        :return: string
        """
        return self.get_element_functional(element, "innerText")

    def get_element_attributes(self, element):
        """
        获取元素所有属性
        :param element: 定位的元素
        :return: dict
        """
        return self.get_element_functional(element, "attributes")

    def element_contains(self, element, name=None, attrs=None, text=None):
        """
        元素是否包含符合条件的子标签
        :param element: 定位到的元素
        :param name: 子标签名
        :param attrs: 子标签属性值
        :param text: 子标签值
        :return: Bool
        """
        html = self.get_element_inner_html(element)
        bs = BeautifulSoup(html, features="lxml")
        res = bs.find_all(name=name, attrs=attrs, text=text)
        if res is None or len(res) == 0:
            return False
        return True

    @staticmethod
    def element_test_equal(element, text):
        """
        判断元素值是否相等
        :param element: 定位到的元素
        :param text:  判断的值
        :return: Bool
        """
        return element.text == text

    def set_element_attribute(self, element, attr, value):
        """
        设置元素属性
        :param element:  定位到的元素
        :param attr:  属性名
        :param value:  属性值
        :return: None
        """
        self.__driver.execute_script("arguments[0].{0}={1}".format(attr, value), element)

    def set_element_value(self, element, value):
        """
        设置元素值
        :param element:
        :param value:
        :return: None
        """
        self.set_element_attribute(element, "value", value)

    def set_element_visable(self, locator):
        """
        设置元素可见
        使用css定位方式
        :param locator: css表达式
        :return:
        """
        js = 'document.querySelector("{}")'.format(locator)
        style_visibility = self.__driver.execute_script('return {}.style.visibility'.format(js))
        style_display = self.__driver.execute_script('return {}.style.display'.format(js))
        type_hidden = self.__driver.execute_script('return {}.type'.format(js))
        if style_visibility != "visable":
            self.__driver.execute_script('{}.style.visibility="visable"'.format(js))
        if style_display != "block":
            self.__driver.execute_script('{}.style.display="block"'.format(js))
        if type_hidden == "hidden":
            self.__driver.execute_script('{}.type="visable"'.format(js))

    def execute_element_with_color(self, element):
        """
        执行元素时高亮显示
        :param element: 要执行的元素
        :return: None
        """
        self.__driver.execute_script("arguments[0].setAttribute('style',arguments[1])", element,
                                     'border:2px solid red;')

    def close(self):
        self.__driver.close()

    def floating_box_with_element(self, attr, value, index):
        """
        选择浮动框中的值
        :param attr:  浮动框ul的属性
        :param value:  属性值
        :param index:  索引位置
        :return: WebElement
        """
        return self.find_element_by_xpath("//ul[@{}='{}']//li[{}]".format(attr, value, index))
        # action = ActionChains(driver=self.__driver)
        # for i in range(index):
        #     action.key_down(Keys.DOWN).key_up(Keys.DOWN).perform()
        # action.key_down(Keys.ENTER).key_up(Keys.ENTER).perform()

    def set_current_time(self, method, value):
        """
        设置当前时间到时间输入框
        :param method:  定位方式
        :param value: 定位值
        :return:WebElement
        """
        return self.set_current_time_use_element(self.find_element(method, value))

    @staticmethod
    def set_current_time_use_element(element):
        """
        设置当前时间到时间输入框
        :param element: 时间输入框对象
        :return: WebElement
        """
        return element.send_keys(DateTime.current_date_time())

    def select(self, method, value):
        """
        处理选择框
        :param method: 定位方式
        :param value:  定位值
        :return:Select
        """

        ele = self.find_element(method, value)
        return self.select_use_element(ele)

    @staticmethod
    def select_use_element(element):
        """
        处理选择框
        :param element: 定位的元素
        :return: Select
        """
        return Select(element)

    def execute_java_script(self, js, *args):
        return self.__driver.execute_script(js, *args)

    def element_action_with_locator(self, method, value):
        if method == "js":
            ele = self.find_element_by_js_query_selector(value)
        else:
            ele = self.find_element(method, value)
        return self.element_action(ele)

    @staticmethod
    def element_action(element):
        return ActionChains(element)


"""
7大定位方式 + 1js 定位

输入框，按钮，日期框，上传文件，下载文件,选择框，鼠标键盘模拟

3种等待方式

如何确保能够定位到元素

回归测试，大量重复操作

自动化测试不能代替手工测试
"""

"""
附赠querySelector定位方式
|.class                 |.intro                 |选择 class="intro" 的所有元素。
|#id                    |#firstname             |选择 id="firstname" 的所有元素。
|*                      |*                      |选择所有元素。
|element                |p                      |选择所有 `<p> `元素。
|element,element        |div,p                  |选择所有 `<div> `元素和所有 `<p> `元素。 
|element element        |div p                  |选择 `<div> `元素内部的所有 `<p> `元素。 
|element>element        |div>p                  |选择父元素为` <div>` 元素的所有 `<p> `元素。 
|element+element        |div+p                  |选择紧接在` <div>` 元素之后的所有 `<p> `元素。 
|[attribute]            |[target]               |选择带有 target 属性所有元素。
|[attribute=value]      |[target=_blank]        |选择 target="_blank" 的所有元素。
|[attribute~=value]     |[title~=flower]        |选择 title 属性包含单词 "flower" 的所有元素。
|[attribute\|=value]    |[lang\|=en]            |选择 lang 属性值以 "en" 开头的所有元素。
|:link                  |a:link                 |选择所有未被访问的链接。
|:visited               |a:visited              |选择所有已被访问的链接。
|:active                |a:active               |选择活动链接。
|:hover                 |a:hover                |选择鼠标指针位于其上的链接。
|:focus                 |input:focus            |选择获得焦点的 input 元素。
|:first-letter          |p:first-letter         |选择每个 `<p> `元素的首字母。 
|:first-line            |p:first-line           |选择每个 `<p> `元素的首行。
|:first-child 	        |p:first-child 	        |选择属于父元素的第一个子元素的每个 `<p>` 元素。
|:before 	            |p:before 	            |在每个 `<p> `元素的内容之前插入内容。
|:after 	            |p:after                |在每个 `<p> `元素的内容之后插入内容。 
|:lang(language) 	    |p:lang(it) 	        |选择带有以 "it" 开头的 lang 属性值的每个 `<p>` 元素。
|element1~element2 	    |p~ul 	                |选择前面有 `<p>`元素的每个 `<ul> `元素。
|[attribute^=value] 	|a[src^="https"] 	    |选择其 src 属性值以 "https" 开头的每个` <a> `元素。
|[attribute$=value] 	|a[src$=".pdf"] 	    |选择其 src 属性以 ".pdf" 结尾的所有 `<a>` 元素。
|[attribute\*=value] 	|a[src\*="abc"]         |选择其 src 属性中包含 "abc" 子串的每个 `<a> `元素。  
|:first-of-type         |p:first-of-type        |选择属于其父元素的首个 `<p>` 元素的每个` <p>` 元素。 
|:last-of-type          |p:last-of-type         |选择属于其父元素的最后` <p>` 元素的每个` <p>` 元素。  
|:only-of-type          |p:only-of-type         |选择属于其父元素唯一的 `<p> `元素的每个` <p> `元素。  
|:only-child 	        |p:only-child           |选择属于其父元素的唯一子元素的每个 `<p> `元素        
|:nth-child(n) 	        |p:nth-child(2) 	    |选择属于其父元素的第二个子元素的每个` <p> `元素        
|:nth-last-child(n) 	|p:nth-last-child(2) 	|同上，从最后一个子元素开始计数。                     
|:nth-of-type(n) 	    |p:nth-of-type(2) 	    |选择属于其父元素第二个 `<p> `元素的每个 `<p> `元素。
|:nth-last-of-type(n) 	|p:nth-last-of-type(2) 	|同上，但是从最后一个子元素开始计数。
|:last-child 	        |p:last-child 	        |选择属于其父元素最后一个子元素每个 `<p> `元素。
|:root 	                |:root 	                |选择文档的根元素。
|:empty 	            |p:empty                |选择没有子元素的每个` <p> `元素（包括文本节点）。 
|:target                |#news:target           |选择当前活动的 #news 元素。
|:enabled 	            |input:enabled          |选择每个启用的 `<input> `元素。 
|:disabled              |input:disabled         |选择每个禁用的 `<input> `元素 
|:checked               |input:checked          |选择每个被选中的` <input>` 元素。 
|:not(selector)         |:not(p)                |选择非` <p> `元素的每个元素。 
|::selection            |::selection            |选择被用户选取的元素部分。
"""
