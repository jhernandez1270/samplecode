#!/usr/bin/env python
__author__ = 'jhernandez'


"""
Simple Android tests, showing accessing elements and getting/setting text from them.
"""
import unittest
import os
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction
#from selenium import webdriver
#from selenium.webdriver.common.touch_actions import TouchActions

from time import sleep


class TweetboxAndroidTests(unittest.TestCase):
    def setUp(self):
        # set up appium
        app = os.path.join(os.path.dirname(__file__), '/Users/jhernandez/Dev/blah/jenkins-builds/', 'drop-1.0.0-60-staging-release.apk')
        #app = os.path.join(os.path.dirname(__file__), '/Users/jhernandez/Dev/tweetboxandroid/tweetboxandroid/app/build/outputs/apk/', 'app-release.apk')
        app = os.path.abspath(app)
        #self.driver = webdriver.Remote(
        #    command_executor='http://127.0.0.1:4723/wd/hub',
        #        desired_capabilities={
        #            'app': app,
        #            'platformName': 'Android',
        #            'platformVersion': '4.4 KitKat (API Level 19)',
        #            'deviceName': 'Android Simulator'
        #        }
        #)
        appium_Url = 'http://127.0.0.1:4723/wd/hub'
        desired_capabilities = {
            'app': app,
            'platformName': 'Android',
            'platformVersion': '4.4 KitKat (API Level 19)',
            #'deviceName': 'Android Simulator'
            # Real Device
            'deviceName': 'Samsung S4'

        }
        print ("WebDriver request initiated. Waiting for response, this typically takes 2-3 mins")
        self.driver = webdriver.Remote(appium_Url, desired_capabilities)
        print ("WebDriver response received")


    def tearDown(self):
        self.driver.close_app()
        self.driver.quit()

    def test_DropText(self):

        self.driver.implicitly_wait(30)

        # DropTextView
        print("Testing if DropTextView is present")
        textview2 = self.driver.find_element_by_id('com.jawbone.drop:id/textView2')
        assert textview2.text == "DROP WATCHES YOUR TWITTER ACCOUNT FOR SONG REQUESTS"
        print("assert Complete, DropTextView text present.")

        # Connect Button Present

        print("Testing if Connect Twitter button is present")
        connecttwitter = self.driver.find_element_by_id('com.jawbone.drop:id/btnLoginTwitter')
        assert connecttwitter.text == "CONNECT TWITTER"
        print("assert Complete, CONNECT TWITTER text button present")

        # NEVER Text present

        print("Testing if NEVER textView is present")
        never = self.driver.find_element_by_id('com.jawbone.drop:id/twitter_never_post_text')
        assert  never.text == "DROP WILL NEVER POST FROM YOUR ACCOUNT"
        print("assert Complete, NEVER textView is present")

        # Press Button

        print("Pressing CONNECT TWITTER button now...")
        twitterButton = self.driver.find_element_by_id('com.jawbone.drop:id/btnLoginTwitter')
        tapAction = TouchAction(self.driver)
        tapAction.tap(twitterButton).perform()


        sleep(5)

        print("checking Web View...")
        if self.driver.find_element_by_id('com.twitter.android:id/home') != None:
            nextview = self.driver.find_element_by_id('com.twitter.android:id/home')
            print("Next View is Twitter sign in WebView...")

        else:
            nextview = self.driver.find_element_by_id('com.jawbone.drop:id/tw_web_view')
            print("Next View is Twitter Home view...")

        print("Asserting...")
        self.assertIsNotNone(nextview, msg="Could Not find the next view")


        sleep(20)




if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TweetboxAndroidTests)
