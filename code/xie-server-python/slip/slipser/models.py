# -*- coding: UTF-8 -*-
from django.db import models

# Create your models here.
#与数据库操作相关，存入或读取数据时用到这个

class Person(models.Model):
    name = models.CharField(max_length=30)
    age = models.IntegerField()