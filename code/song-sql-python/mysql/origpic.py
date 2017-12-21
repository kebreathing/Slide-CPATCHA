import os

from PIL import Image
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from model import OrigPic

# 初始化数据库连接：
engine = create_engine('mysql+pymysql://slidecaptcha:123456@localhost:3306/slide_captcha')

# 创建DBSession类型：
DBsession = sessionmaker(bind=engine)

# 文件位置和文件名
storage_location="../imgs/pics/pic-00001000.jpg"
img = Image.open(storage_location)
fpixel ='{}{}'.format(img.size[0],img.size[1])  # 获取文件像素

#  创建session对象：
session = DBsession()

# 原图OrigPic增删改查

# # 增
# # 创建新OrigPic对象(ID自增长，add_time按输入时间默认)
# new_origpic = OrigPic(name=os.path.basename(storage_location),storage_location=storage_location,
#                       file_suffixes=img.format,file_size=os.path.getsize(storage_location),
#                       file_pixel=fpixel)
# # 添加到session：
# session.add(new_origpic)
#
# # 提交即保存到数据库：
# session.commit()

# 查
# # 创建一个query
# query1 = session.query(OrigPic)
# for u in query1.all():  # 获取所有信息
#     print('Id:\t',u.id,'\tname:\t',u.name,'\tstorage_location:\t',u.storage_location)
# # 条件过滤filter 等价于 where
# for u in query1.filter(OrigPic.id>=5).all():
#     print('Id:\t',u.id,'\tname:\t',u.name,'\tstorage_location:\t',u.storage_location)
# u = query1.filter(OrigPic.id==5).one()
# print('Id:\t',u.id,'\tname:\t',u.name,'\tstorage_location:\t',u.storage_location)

# # 改
# query1 = session.query(OrigPic)
# print('old name:',query1.filter(OrigPic.id==1).one().name)
# query1.filter(OrigPic.id==1).update({OrigPic.name:'pic-00000200.jpg'})
# session.commit()
# print('new name:',query1.filter(OrigPic.id==1).one().name)

# # 删
# query1 = session.query(OrigPic)
# query1.filter(OrigPic.id<=5).delete()
# 关闭session：
session.close()


