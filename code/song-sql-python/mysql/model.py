#!/usr/bin/env python3
# -*- coding: utf-8 -*-
import time

from sqlalchemy import Column, String, Float, Integer, Boolean
from sqlalchemy.ext.declarative import declarative_base

# 创建对象的基类:
Base = declarative_base()



# 定义原图OrigPic对象：
class OrigPic(Base):
    # 表的名字
    __tablename__ = 'origpic'

    # 表的结构：
    id = Column(Integer, primary_key=True)
    name = Column(String(50))  # 图片名
    storage_location = Column(String(50))   # 存储位置
    add_time = Column(Float(50),default=time.strftime("%Y%m%d%H%M%S", time.localtime())) # 添加时间
    file_suffixes = Column(String(50))  # 文件后缀
    file_size = Column(String(50))  # 文件大小
    file_pixel = Column(String(50))  # 文件像素


# 定义处理图ProcessedPic对象：
class ProcessedPic(Base):
    # 表的名字
    __tablename__ = 'processedpic'

    # 表的结构：
    id = Column(Integer, primary_key=True)
    name = Column(String(50))  # 处理图名
    # og_id = Column(Integer,ForeignKey('origpic.id'))  # 原图id
    og_id = Column(Integer)  # 原图id
    # sub_id = Column(Integer,ForeignKey('subpic.id'))  # 正确子图id
    sub_id = Column(Integer)  # 正确子图id
    storage_location = Column(String(50))  # 存储位置
    add_time = Column(Float(50),default=time.strftime("%Y%m%d%H%M%S", time.localtime())) # 添加时间
    file_suffixes = Column(String(50))  # 文件后缀
    file_size = Column(String(50))  # 文件大小
    file_pixel = Column(String(50))  # 文件像素


# 定义子图SubPic对象：
class SubPic(Base):
    # 表的名称
    __tablename__ = 'subpic'

    # 表的结构
    id = Column(Integer, primary_key=True)
    name = Column(String(50))  # 子图名
    # processed_id = Column(Integer,ForeignKey('processedpic.id'))  # 处理图id
    processed_id = Column(Integer)  # 对应处理图id
    border_struct = Column(String(50))  # 边界结构
    file_suffixes = Column(String(50))  # 文件后缀
    file_size = Column(String(50))  # 文件大小


# 定义拼图组合CombPic对象：
class CombPic(Base):
    # 表的名称
    __tablename__ = 'combpic'

    # 表的结构
    id = Column(Integer, primary_key=True)
    processed_id = Column(Integer)  # 处理图id
    sub_id = Column(Integer)  # 子图id
    add_time = Column(Float(50),default=time.strftime("%Y%m%d%H%M%S", time.localtime())) # 添加时间


# 定义拼图解锁信息UnlockPic对象：
class UnlockPic(Base):
    # 表的名称
    __tablename__ = 'unlockpic'

    # 表的结构
    id = Column(Integer, primary_key=True)
    processed_id = Column(Integer)  # 处理图id
    try_times = Column(Integer)  # 拼图解锁尝试次数
    success_times = Column(Integer)  # 拼图解锁成功次数
    avg_time = Column(Float(50))  # 平均尝试时间


# 定义用户——拼图解锁信息UserUnlock对象：
class UserUnlock(Base):
    # 表的名称
    __tablename__ = 'userunlock'

    # 表的结构
    id = Column(Integer, primary_key=True)
    user_id = Column(String(50))  # 用户唯一标识符（临时实用IP地址）
    combpic_id = Column(Integer)  # 拼图组合唯一标识符
    start_time = Column(Float(50))  # 解锁开始时间
    end_time = Column(Float(50))  # 解锁结束时间
    result = Column(Boolean)  # 解锁结果


# 定义用户拼图解锁鼠标状态MouseStates对象：
class MouseStates(Base):
    # 表的名称
    __tablename__ = 'mousestates'

    # 表的结构
    id = Column(Integer, primary_key=True)
    userunlock = Column(Integer)  # 用户-拼图解锁唯一标识符
    clock_times = Column(Integer)  # 鼠标点击次数
    mouse_press = Column(Float(50))  # 鼠标按下时间点
    mouse_free = Column(Float(50))  # 鼠标释放时间
    mouse_continue = Column(Float(50))  # 鼠标持续按下时间
    mouse_path = Column(String(50))  # 鼠标运动轨迹存储文件