# encoding: utf-8


"""
@author: yp
@software: PyCharm
@file: music.py
@time: 2019/7/31 0031 13:38
"""
from time import sleep

from selenium import webdriver

import unittest

path = "D:\Program Files (x86)\Google\Chrome\Application\chromedriver"


class MusicTest(unittest.TestCase):
    driver = None

    @classmethod
    def setUpClass(cls, ) -> None:
        cls.driver = webdriver.Chrome(executable_path=path)

    def test_music_play(self):
        MusicTest.driver.get('https://www.baidu.com/')

        MusicTest.driver.find_element_by_id("kw").send_keys("网易云音乐")
        MusicTest.driver.find_element_by_id('su').click()
        sleep(1)
        MusicTest.driver.find_element_by_xpath("//*[@id=1]/h3/a[1]").click()

        # 切换窗口
        MusicTest.driver.switch_to.window(MusicTest.driver.window_handles[1])

        sleep(3)

        frame = MusicTest.driver.find_element_by_id('g_iframe')
        MusicTest.driver.switch_to.frame(frame)
        MusicTest.driver.find_element_by_link_text("流行").click()

        sleep(3)
        MusicTest.driver.find_element_by_link_text('视频首选BGM〖CG混剪〗【戴上耳机】').click()

        sleep(5)
        MusicTest.driver.find_element_by_xpath('//b[@title="果物の盛り合わせ"]').click()
        sleep(2)
        MusicTest.driver.find_element_by_link_text('播放').click()




if __name__ == '__main__':
    unittest.main()