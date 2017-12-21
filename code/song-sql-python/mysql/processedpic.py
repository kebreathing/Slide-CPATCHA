import os

from PIL import Image
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from model import ProcessedPic,OrigPic

# 初始化数据库连接：
engine = create_engine('mysql+pymysql://slidecaptcha:123456@localhost:3306/slide_captcha')

# 创建DBSession类型：
DBsession = sessionmaker(bind=engine)

# 文件位置和文件名
storage_location="../imgs/pieces/pic-00001000.jpeg"
img = Image.open(storage_location)
fpixel ='{}{}'.format(img.size[0],img.size[1])  # 获取文件像素
ogname = 'pic-00001000.jpg'
#  创建session对象：
session = DBsession()
# 获取原图id
query1 = session.query(OrigPic)
u = query1.filter(OrigPic.name==ogname).one()
ogid = u.id

# 原图ProcessedPic增删改查

# # 增
# # 创建新ProcessedPic对象(ID自增长，add_time按输入时间默认,正确子图id还没生成。。)
# new_processedpic = ProcessedPic(name=os.path.basename(storage_location),og_id=ogid,storage_location=storage_location,file_suffixes=img.format,
#                                 file_size=os.path.getsize(storage_location),file_pixel=fpixel)
# # 添加到session：
# session.add(new_processedpic)
#
# # 提交即保存到数据库：
# session.commit()

# # 查
# # 创建一个query
# query1 = session.query(ProcessedPic)
# for u in query1.all():  # 获取所有信息
#     print('Id:\t',u.id,'\togid:\t',u.og_id,'\tstorage_location:\t',u.storage_location)
# # 条件过滤filter 等价于 where
# for u in query1.filter(ProcessedPic.id>=5).all():
#     print('Id:\t',u.id,'\togid:\t',u.og_id,'\tstorage_location:\t',u.storage_location)
# u = query1.filter(ProcessedPic.id==5).one()
# print('Id:\t',u.id,'\togid:\t',u.og_id,'\tstorage_location:\t',u.storage_location)

# # 改
# query1 = session.query(ProcessedPic)
# print('old ogid:',query1.filter(ProcessedPic.id==1).one().og_id)
# query1.filter(ProcessedPic.id==1).update({ProcessedPic.og_id:'1'})
# session.commit()
# print('new name:',query1.filter(ProcessedPic.id==1).one().og_id)

# # 删
# query1 = session.query(ProcessedPic)
# query1.filter(ProcessedPic.id<=5).delete()
# 关闭session：
session.close()


