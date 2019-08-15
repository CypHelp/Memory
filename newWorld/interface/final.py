import xlrd

from interface.client import Client


class Case:

    def __init__(self):
        # 封装xlsx表格的第一行的描述
        # 编号 描述 请求url 请求方式 请求参数 请求header 响应码 预期结果（响应内容） 实际结果 通过
        self.case_id = 0
        self.name = ""
        self.url = ""
        self.method = ""
        self.param = ""
        self.header = ""
        self.excepts = ""
        self.real = ""
        self.is_pass = False

    # 每个类的类变量、函数名都放在自己的__dict__中
    def __str__(self):
        return "{}".format(self.__dict__)


class CaseFactory:
    def __init__(self):
        self.book = xlrd.open_workbook("..\zixun\example\case.xlsx")

    def read_one_sheet(self, index):
        sheet = self.book.sheet_by_name(self.book.sheet_names()[index])
        all_case = [self.build(sheet, x) for x in range(1, sheet.nrows)]
        return all_case

    def build(self, sheet, row):
        case = Case()
        index = 0
        for x in case.__dict__:
            case.__dict__[x] = sheet.cell_value(row, index)
            index += 1
        return case
        # CaseFactory类


# book = xlrd.open_workbook("..\zixun\example\case.xlsx")
# # 查看表格的张数
# # print(book.sheet_names())
# sheet = book.sheet_by_name(book.sheet_names()[0])
# index = 0
# for x in case.__dict__:
#     case.__dict__[x] = sheet.cell_value(1, index)
#     index += 1
# print(case)

caseFactory = CaseFactory()

for x in caseFactory.read_one_sheet(0):
    print(x)
client = Client()
