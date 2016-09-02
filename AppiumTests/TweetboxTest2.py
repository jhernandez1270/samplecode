#!/usr/bin/env python
__author__ = 'jhernandez'


"""
Simple Android tests, showing accessing elements and getting/setting text from them.
"""
import unittest
import os
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as ec
#from selenium import webdriver
#from selenium.webdriver.common.touch_actions import TouchActions

from time import sleep


class TweetboxAndroidTests(unittest.TestCase):
    def setUp(self):
        # set up appium
        #app = os.path.join(os.path.dirname(__file__), '/Users/jhernandez/Dev/tweetboxandroid/tweetboxandroid/app/build/outputs/apk/', 'drop-0.3.6.apk')
        app = os.path.join(os.path.dirname(__file__), '/Users/jhernandez/Dev/blah/jenkins-builds/', 'drop-1.0.0-60-staging-release.apk')
        app = os.path.abspath(app)

        appium_Url = 'http://127.0.0.1:4723/wd/hub'
        desired_capabilities = {
            'app': app,
            'platformName': 'Android',
            'platformVersion': '4.4 KitKat (API Level 19)',
            #'platformVersion': '5.1 Lollipop (API Level 22)',
            #'deviceName': 'Android Simulator'
            # Real Device
            'deviceName': 'Samsung S4'

        }
        print ("WebDriver request initiated. Waiting for response, this typically takes 2-3 mins")
        self.driver = webdriver.Remote(appium_Url, desired_capabilities)
        print ("WebDriver response received")


    def tearDown(self):
        self.driver.back()
        self.driver.open_notifications()
        closemediabutton = self.driver.find_element_by_id('com.jawbone.drop:id/media_close')
        tapaction = TouchAction(self.driver)
        tapaction.tap(closemediabutton).perform()
        self.driver.back()

    def test_DropTest(self):

        self.driver.implicitly_wait(30)

        # DropTextView
        print("Testing if Youtube track is immediately present")
        contentview = self.driver.find_element_by_id('android:id/content')
        print("Tapping Content view...")
        #sleep(10)
        '''
            BLOCK to test if dropControls have Music text present.  If not, wait 2 sec, tap on Content view, check if dropControls are present.
        '''
        tapaction = TouchAction(self.driver)
        tapaction.tap(contentview).perform()
        dropcontrols = self.driver.find_element_by_id('com.jawbone.drop:id/dropControls')
        if dropcontrols.is_displayed():
            while dropcontrols.get_attribute("NAF") == "true":
                sleep(2)
                tapaction.tap(contentview).perform()

        #dropcontrols = WebDriverWait(self.driver, 10).until(ec.presence_of_element_located(By.ID, 'com.jawbone.drop:id/dropControls'))

        '''
            BLOCK End
        '''
        ''' BLOCK COMMMENTED OUT FOR TESTING
        tapaction = TouchAction(self.driver)
        tapaction.tap(contentview).perform()
        WebDriverWait(self.driver, 10).until(ec.presence_of_element_located((By.ID,'com.jawbone.drop:id/trackDesc')))
        print(self.driver.page_source)

        BLOCK COMMENT END'''


        tapaction.tap(contentview).perform()
        trackDesc = self.driver.find_element_by_id('com.jawbone.drop:id/trackDesc')
        self.assertIsNotNone(trackDesc, "Did not get Track Desc ID")
        thisTrack = trackDesc.get_attribute("text")
        #print "This track is: %s" % thisTrack
        print("TrackDesc: %s" % str(trackDesc.text))
        print(u"THIS TRACK: %s" % thisTrack)
        #assert trackDesc.text != None
        #assert trackDesc.text == thisTrack
        self.assertEqual(trackDesc.text, thisTrack, "Does not match")
        print("assert Complete, song_title TextView text present.")


        # Connect Button Present
        # STARTING HERE COMMENTED OUT FOR TESTING
        # print("Testing if Connect Twitter button is present")
        # connecttwitter = self.driver.find_element_by_id('com.jawbone.drop:id/btnLoginTwitter')
        # assert connecttwitter.text == "CONNECT TWITTER"
        # print("assert Complete, CONNECT TWITTER text button present")
        #
        # # NEVER Text present
        #
        # print("Testing if NEVER textView is present")
        # never = self.driver.find_element_by_id('com.jawbone.drop:id/twitter_never_post_text')
        # assert  never.text == "DROP WILL NEVER POST FROM YOUR ACCOUNT"
        # print("assert Complete, NEVER textView is present")
        #
        # # Press Button
        #
        # print("Pressing CONNECT TWITTER button now...")
        # twitterButton = self.driver.find_element_by_id('com.jawbone.drop:id/btnLoginTwitter')
        # tapAction = TouchAction(self.driver)
        # tapAction.tap(twitterButton).perform()
        #
        #
        # sleep(5)
        #
        # print("checking Web View...")
        # if self.driver.find_element_by_id('com.twitter.android:id/home') != None:
        #     nextview = self.driver.find_element_by_id('com.twitter.android:id/home')
        #     print("Next View is Twitter sign in WebView...")
        #
        # else:
        #     nextview = self.driver.find_element_by_id('com.jawbone.drop:id/tw_web_view')
        #     print("Next View is Twitter Home view...")
        #
        # print("Asserting...")
        # self.assertIsNotNone(nextview, msg="Could Not find the next view")
        #


        sleep(10)




if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(TweetboxAndroidTests)