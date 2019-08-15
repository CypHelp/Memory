import xlrd


class ExcelReader:

    def __init__(self, filename):
        self.book = xlrd.open_workbook(filename)
        self.clazz = None

    def set_class(self, clazz):
        self.clazz = clazz

    def select_page(self, page_number):
        sheet = self.book.sheet_by_index(page_number)
        all_step = [self.make_one_step(row, sheet) for row in range(1, sheet.nrows)]
        all_step.sort(key=lambda x: x.case_id)
        return all_step

    def make_one_step(self, rows, sheet):
        step = self.clazz()
        index = 0
        for key in step.__dict__:
            step.__dict__[key] = sheet.cell_value(rows, index)
            index += 1
        return step
