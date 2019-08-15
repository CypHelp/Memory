"""
--------------------获取数据------------------------------
Excel   ->  read    ->  数据
                         |
                         V
                        Step  ->    结构化存储（存储一行数据） 步骤（Step）
                        |
                        V
                    Request  -》 准备好执行所需的原材料
--------------------执行步骤------------------------------
                根据原材料进行生产
   Client
    |
    V
  请求结果
    |
    V
-------------------断言结果------------------------------
            查看生产结果
  断言结果

"""
import logging
import re

import xlrd
from requests import PreparedRequest

from backup.database import Database
from interface.client import Analysis, ResponseTable, Client

logging.basicConfig(level=logging.INFO,
                    format='%(asctime)s %(filename)s[line:%(lineno)d] %(levelname)s %(message)s',
                    datefmt='%Y %b %d %a, %H:%M:%S')

table = ResponseTable()


class Step:

    def __init__(self):
        # 编号
        self.case_id = 0
        # 描述
        self.name = ""
        # 请求url
        self.url = ""
        # 请求方式
        self.method = ""
        # 请求参数
        self.param = ""
        # 请求header
        self.header = ""
        # 依赖关系
        self.depends = ""
        # 预期结果
        self.excepts = ""
        # 实际结果
        self.real = ""
        # 通过
        self.is_pass = False

    def __str__(self):
        return "{}".format(self.__dict__)

    def prepare_uri(self):
        return "{0}?{1}".format(self.url, self.param)

    def prepare_header(self):
        header = self.header.replace(" ", "")
        # User-Agent:Android
        return {k: v for k, v in re.findall("(.+?):(.+)", header)}

    def request(self):
        req = PreparedRequest()
        case_id, where, param, insert_place = Analysis.analysis(self.depends)
        if where == "response":
            # 根据id获取响应结果
            result = Analysis.get_response(table.get(case_id), param)
            send_param = self.kind(result, param, insert_place)
        else:
            raise SyntaxError("语法错误: 不支持【{}】操作".format(where))

        req.prepare(
            method=self.method,
            url=self.url,
            params=send_param,
            headers=self.prepare_header()
        )
        logging.info("当前执行步骤为：【{0}】".format(self.name))
        return req

    def reverse_uri(self):
        all_param = re.findall("(.+?)=(.+?)", self.param)
        # (number,1)
        result = {}
        for param in all_param:
            key, value = param
            result[key] = value
        return result

    def kind(self, result, param, place):
        reverse_param = self.reverse_uri()
        # number=1&act=order_add&amount=13.0&siteid={}&itemid={}&userid={}&buyer_receive=普通快递
        if place == "param":
            for p in param:
                reverse_param[p] = result[p]
        return reverse_param


class ExcelReader:

    def __init__(self, filename):
        self.book = xlrd.open_workbook(filename)

    def select_page(self, page_number):
        sheet = self.book.sheet_by_index(page_number)
        all_step = [self.make_one_step(row, sheet) for row in range(1, sheet.nrows)]
        all_step.sort(key=lambda x: x.case_id)
        return all_step

    @staticmethod
    def make_one_step(rows, sheet):
        step = Step()
        index = 0
        for key in step.__dict__:
            step.__dict__[key] = sheet.cell_value(rows, index)
            index += 1
        return step


class DatabaseReader:

    def __init__(self, connect):
        self.db = Database(connect)
        self.db.get_cursor()

    def make_one_step(self, index):
        self.db.execute("""
        select case_id,name, url,method,param,header,excepts,real,is_pass
        from step
        where step_id = {}
        """.format(index))
        all_step = []
        for i in self.db.cursor():
            step = Step()
            index = 0
            for j in step.__dict__:
                step.__dict__[j] = i[index]
                index += 1
            all_step.append(step)
        return all_step

    def select_page(self, step_id):
        self.db.execute("""
        select step_id from execute_step where id = {}; 
        """.format(step_id))
        ids = next(self.db.cursor())
        return self.make_one_step(ids[0])


def f(steps):
    return [steps.__dict__[x] for x in steps.__dict__]


if __name__ == '__main__':

    #
    # conn = sqlite3.connect("step")
    # db = Database(conn)
    # db.get_cursor()
    #
    # for steps in all_steps:
    #     db.execute("insert into step values({})",
    #                *f(steps))
    excel = ExcelReader("/home/amdins/文档/case.xlsx")
    all_steps = excel.select_page(0)
    client = Client()
    for s in all_steps:
        table.set(s.case_id, client.do(s.request()).jsonify())
