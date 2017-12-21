import os

from PIL import Image
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker

from model import ProcessedPic,SubPic

# 初始化数据库连接：
engine = create_engine('mysql+pymysql://slidecaptcha:123456@localhost:3306/slide_captcha')

# 创建DBSession类型：
DBsession = sessionmaker(bind=engine)

# 文件位置和文件名
storage_location="../imgs/pieces/pic-00001000-01.png"
img = Image.open(storage_location)
processedpic = 'pic-00001000.jpeg'
#  创建session对象：
session = DBsession()
# # 获取对应处理图id
# query1 = session.query(ProcessedPic)
# processed_id = query1.filter(ProcessedPic.name==processedpic).one().id

# 原图SubPic增删改查

# # 增
# # 创建新SubPic对象(ID自增长)
# new_subpic = SubPic(name=os.path.basename(storage_location),processed_id=processed_id,file_suffixes=img.format,
#                                 file_size=os.path.getsize(storage_location))
# # 添加到session：
# session.add(new_subpic)
# # 提交即保存到数据库：
# session.commit()
#
# # 同时修改processedpic表对应sub_id
# query1 = session.query(ProcessedPic)
# query2 = session.query(SubPic)
# u = query2.filter(SubPic.name==os.path.basename(storage_location)).one().id  # 得到subpic的id
# if(os.path.basename(storage_location)=='pic-00001000-00.png'):  #是否是正确子图
#     query1.filter(ProcessedPic.id==processed_id).update({ProcessedPic.sub_id:u})  # 修改对应值
#     session.commit()
#     print('new name:',query1.filter(ProcessedPic.id==1).one().sub_id)


# # 查
# # 创建一个query
# query1 = session.query(SubPic)
# for u in query1.all():  # 获取所有信息
#     print('Id:\t',u.id,'\tname:\t',u.name,'\tprocessedid:\t',u.processed_id)
# # 条件过滤filter 等价于 where
# for u in query1.filter(SubPic.id>=5).all():
#     print('Id:\t',u.id,'\tname:\t',u.name,'\tprocessed_id:\t',u.processed_id)
# u = query1.filter(SubPic.id==5).one()
# print('Id:\t',u.id,'\tname:\t',u.name,'\tprocessed_id:\t',u.processed_id)

# # 改
# query1 = session.query(SubPic)
# print('old ogid:',query1.filter(SubPic.id==1).one().name)
# query1.filter(SubPic.id==1).update({SubPic.name:'pic'})
# session.commit()
# print('new name:',query1.filter(SubPic.id==1).one().name)

# # 删
# query1 = session.query(SubPic)
# query1.filter(SubPic.id<=5).delete()
# 关闭session：
session.close()


