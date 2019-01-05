import random
import math
import threading
import time
import pymysql

# 正弦信号的振幅，可以用于表征呼吸的强度
a = 0.3
# 正弦信号的频率，表征呼吸的速度
w = math.pi * 5
body_a = 0
z_angle = 0
x_angle = 0

def noise():
    return random.random() * 0.1 - 0.05

def breath_signal():
    while True:
        # 每隔一段时间改变一些呼吸的频率和深度
        time_slot = random.randint(10, 60)
        time.sleep(time_slot)
        # a range 0.1 - 0.5
        global a
        global w
        a = random.random() * 0.4 + 0.1 + noise()
        # w range 2PI - 20PI
        w = random.random() * 2 * math.pi + 2 * math.pi


def body_signal():
    while True:
        # 间隔30分钟到2小时时间趴下，起身
        # time_slot = random.randint(60 * 30, 60 * 60 * 2)
        time_slot = random.randint(60, 5 * 60)
        time.sleep(time_slot)
        global body_a
        body_a = random.random() * 2 - 1 + noise()
        time.sleep(math.sqrt(2.0 / math.fabs(body_a)))
        body_a = 0 + noise()


def head_signal():
    while True:
        # 间隔20分钟到1小时时间低头，抬头
        # time_slot = random.randint(60 * 20, 60 * 60 )
        time_slot = random.randint(60, 2 * 60)
        time.sleep(time_slot)
        global z_angle
        z_angle = random.randint(-30, 60) + noise()
        # x_angle = random.randint(-60, 60)


def sampling():
    db = pymysql.connect("123.207.19.172", "root", "qianz","oxnet")
    cursor = db.cursor()
    cnt = 0
    while True:
        time.sleep(0.02)
        t = time.clock()
        cnt += 1
        f = 9.8 + a * math.sin(w * t) + body_a
        z_ = f * math.cos(2 * math.pi * (90 + z_angle) / 360.)
        y_ = f * math.sin(2 * math.pi * (90 + z_angle) / 360.)
        x_ = 0
        # print("%d\tx:%.1f\ty:%.1f\tz:%.1f" % (cnt, x_, y_, z_))
        # print("a:%f\tw:%f\tbody_a:%f\tz_angle:%d\t" % (a, w, body_a, z_angle))
        # print("analize:%f"%(math.sqrt(x_ ** 2 + y_ ** 2 + z_ ** 2)))
        sql = "insert  into ox_data(x,y,z) values(%f, %f, %f);" %(x_, y_, z_)
        print("sql:", sql)
        cursor.execute(sql)
        db.commit()


if __name__ == '__main__':
    breath = threading.Thread(target=breath_signal, name="breath_thread")
    breath.start()
    body = threading.Thread(target=body_signal, name="body_thread")
    body.start()
    head = threading.Thread(target=head_signal, name="head_thread")
    head.start()
    sample = threading.Thread(target=sampling, name="sampling_thread")
    sample.start()
