#!/usr/bin/env python3
# -*- coding: utf-8 -*-

'''
Models for user
'''


import time
import uuid
from orm import Model, StringField, BooleanField, IntegerField,FloatField, TextField


def next_id():
    # return '%015d%s000' % (int(time.time() * 1000), uuid.uuid4().hex)
    return '%s000' % (uuid.uuid4().hex)


class User(Model):
    __table__ = 'users'

    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    # email = StringField(ddl='varchar(50)')
    # passwd = StringField(ddl='varchar(50)')
    # admin = BooleanField()
    name = StringField(ddl='varchar(50)')
    # image = StringField(ddl='varchar(500)')
    created_at = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))

class Blog(Model):
    __table__ = 'blogs'

    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    user_id = StringField(ddl='varchar(50)')
    user_name = StringField(ddl='varchar(50)')
    user_image = StringField(ddl='varchar(500)')
    name = StringField(ddl='varchar(50)')
    summary = StringField(ddl='varchar(200)')
    content = TextField()
    created_at = FloatField(default=time.time)

class Comment(Model):
    __table__ = 'comments'

    id = StringField(primary_key=True, default=next_id, ddl='varchar(50)')
    blog_id = StringField(ddl='varchar(50)')
    user_id = StringField(ddl='varchar(50)')
    user_name = StringField(ddl='varchar(50)')
    user_image = StringField(ddl='varchar(500)')
    content = TextField()
    created_at = FloatField(default=time.time)


class OrigPic(Model):
    __table__ = 'origpic'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')
    storage_location = StringField(ddl='varchar(50)')  # 存储位置
    add_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 添加时间
    file_suffixes = StringField(ddl='varchar(50)')  # 文件后缀
    file_size = StringField(ddl='varchar(50)')  # 文件大小
    file_long = StringField(ddl='varchar(50)')  # 文件长
    file_wide = StringField(ddl='varchar(50)')  # 文件宽


class ProcessedPic(Model):
    __table__ = 'processedpic'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')
    og_id = StringField(ddl='varchar(50)')  # 原图id
    sub_id = StringField(ddl='varchar(50)')  # 子图id
    storage_location = StringField(ddl='varchar(50)')  # 存储位置
    add_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 添加时间
    file_suffixes = StringField(ddl='varchar(50)')  # 文件后缀
    file_size = StringField(ddl='varchar(50)')  # 文件大小
    file_long = StringField(ddl='varchar(50)')  # 文件长
    file_wide = StringField(ddl='varchar(50)')  # 文件宽


class SubPic(Model):
    __table__ = 'subpic'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')
    processed_id = StringField(ddl='varchar(50)')  # 处理图id
    border_struct = StringField(ddl='varchar(50)')  #边界结构
    file_suffixes = StringField(ddl='varchar(50)')  # 文件后缀
    file_size = StringField(ddl='varchar(50)')  # 文件大小


class CombPic(Model):
    __table__ = 'combpic'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')
    processed_id = StringField(ddl='varchar(50)')  # 处理图id
    sub_id = StringField(ddl='varchar(50)')  # 子图id
    add_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 添加时间

class UnlockPic(Model):
    __table__ = 'unlockpic'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')
    processed_id = StringField(ddl='varchar(50)')  # 处理图id
    try_times = IntegerField()  # 拼图解锁尝试次数
    success_times = IntegerField()  # 拼图解锁成功次数
    avg_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 平均尝试时间


class UserUnlock(Model):
    __table__ = 'userunlock'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')  # 用户-拼图解锁信息唯一标识符
    user_id = StringField(ddl='varchar(50)')  # 用户唯一标识符（临时实用IP地址）
    combpic_id = StringField(ddl='varchar(50)')  # 拼图组合唯一标识符
    start_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 解锁开始时间
    end_time = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 解锁结束时间
    result = BooleanField()  # 解锁结果

class MouseStates(Model):
    __table__ = 'mousestates'

    id = StringField(primary_key=True, default=next_id(), ddl='varchar(50)')  # 鼠标状态唯一标识符
    userunlock = StringField(ddl='varchar(50)')  # 用户-拼图解锁唯一标识符
    clock_times = IntegerField()  # 鼠标点击次数
    mouse_press = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 鼠标按下时间点
    mouse_free = FloatField(default=time.strftime("%Y%m%d%H%M%S", time.localtime()))  # 鼠标释放时间
    mouse_continue = FloatField()  # 鼠标持续按下时间
    mouse_path = StringField(ddl='varchar(50)')  # 鼠标运动轨迹存储文件

