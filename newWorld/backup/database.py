import sqlite3


class Database:

    def __init__(self, connect):
        self.connect = connect
        self.cur_cursor = None

    def get_cursor(self):
        self.cur_cursor = self.connect.cursor()

    def cursor(self):
        return self.cur_cursor

    def kind(self, param):
        if isinstance(param, str):
            return "'{}'".format(param)
        elif isinstance(param, int) or isinstance(param, float):
            return "{}".format(param)

    def make_insert_sql(self, sql, *params):
        return sql.format(",".join([self.kind(x) for x in params]))

    def execute(self, sql, *params):
        if not self.cur_cursor:
            raise ValueError("必须初始化cursor")
        try:
            if sql.startswith("insert"):
                sql = self.make_insert_sql(sql, *params)
            self.cur_cursor.execute(sql)
            self.connect.commit()
        except Exception as e:
            self.connect.rollback()
            print(sql,e.args)


if __name__ == '__main__':
    # ORM
    conn = sqlite3.connect("case")
    db = Database(conn)
    db.get_cursor()
    db.execute("insert into teacher values({})", 12, "CJ")
