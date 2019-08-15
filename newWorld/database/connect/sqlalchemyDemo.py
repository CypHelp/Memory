# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: sqlalchemyDemo.py
@time: 2019/8/14 0014 14:54
"""

# pymysql
# sqlalchemy
# from sqlalchemy import create_engine, Column, Integer, String, select
# from sqlalchemy.ext.declarative import declarative_base
# from sqlalchemy.orm import sessionmaker, scoped_session
#
# """
#  ORM：Object Relation Mapping，最初主要描述的是程序中的Object对象和关系型数据库中Rlation关系(表)之间的映射关系，
# 目前来说也是描述程序中对象和数据库中数据记录之间的映射关系的统称，是一种进行程序和数据库之间数据持久化的一种编程思想。
# 增、删、改、查 核心操作一般会区分为：增删改、查询
#
# """
#
#
#
# """
# 1. 连接引擎
# """
# # 创建一个和mysql数据库之间的连接引擎对象
# #engine = create_engine("mysql://scott:tiger@hostname/dbname",encoding='latin1', echo=True)
# engine=create_engine("mysql+pymysql://root:123456@192.168.195.131:3306/yp",
#                                     encoding='latin1', echo=True)
#
# """
# 2.连接会话
# """
# Session=sessionmaker(bind=engine)
# session=Session()
#
# """
# 3.ORM之Object操作
# """
# # 创建基础类
# BaseModel=declarative_base()
# # 创建用户类型
# class User(BaseModel):
#     __tabelname__ = 'test'
#     id = Column(Integer, primary_key=True)
#     name = Column(String(50))
#     age = Column(Integer)
#     def __repr__(self):
#         return "id={0},name={1},age={2}".format(self.id, self.name, self.age)
# pymsql
# sqlalchemy
from sqlalchemy import create_engine, Column, Integer, String, select
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, scoped_session

# ORM

Base = declarative_base()


class User(Base):
    __tablename__ = "user"

    id = Column(Integer(), primary_key=True)
    name = Column(String(50))
    age = Column(Integer())

    def __repr__(self):
        return "id={0},name={1},age={2}".format(self.id, self.name, self.age)


engine = create_engine("mysql+pymysql://root:123456@localhost:3306/yp")

# engine.connect()
# Base.metadata.create_all(bind=engine) 创建所有的表


session = sessionmaker(bind=engine, expire_on_commit=False)

session_manager = scoped_session(session)


def get_session():
    return session_manager()


sess = get_session()

# sess.add(User(id=1, name="Snake", age=18))

# Query

# sess.commit()
# select user.id,user.name,user.age from user where user.id == 1
user = sess.query(User).filter(User.id == 1).all()[0]
print(user.id, user.name, user.age)

user = User(id=1)
sess.delete(user)
sess.execute(select(["now()"]))
sess.close()
