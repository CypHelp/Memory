import re
from configparser import ConfigParser

from selenium.webdriver.common.by import By


class PageObject:
    PoToBy = {
        "id": By.ID,
        "xpath": By.XPATH,
        "p_link_text": By.PARTIAL_LINK_TEXT,
        "link_text": By.LINK_TEXT,
        "tag_name": By.TAG_NAME,
        "css": By.CSS_SELECTOR,
        "class_name": By.CLASS_NAME,
        "name": By.NAME,
        "js": "js",
        "jsc": "java_script"
    }

    def __init__(self, filename):
        self.config = ConfigParser()
        self.config.read(filename)
        self.current_section = ""

    def set_current_section(self, section: str):
        self.current_section = section

    def get_element(self, name: str):
        if self.current_section == "":
            raise ValueError("请设置当前的section")
        result = self.config.get(self.current_section, name)
        parser_result = re.findall("\((\w{2,10}),(.+)\)", result)
        if len(parser_result) == 0:
            raise SyntaxError("{}:语法错误".format(result))
        try:
            method, value = parser_result[0]
        except IndexError:
            raise SyntaxError("{}:语法错误".format(parser_result[0]))
        return PageObject.PoToBy[method], value

    def get_value(self, value: str):
        return str(self.config.get(self.current_section, value)).replace("\"", "")


if __name__ == '__main__':
    p = PageObject("/home/amdins/no.625/seleniums/po.ini")
    p.set_current_section("login")
    print(p.get_element("xxxc"))
