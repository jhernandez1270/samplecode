__author__ = 'jhernandez'

""" These are automated functions to test the Android Tweetbox Chromecast app.  To run these tests you must have Appium server running locally.
You must have the Android client settings section in Appium configured for your android phone. Finally, you must have Appium environment settings set up properly.

Appium instantiation and app config is done in the setUp() function of this class.  To change the DUT, set it in the desired_capabilities assignment
and in the Appium Android client settings.
i.e to test a LG G3 with current Lollipop, enter 5.1 Lollipop (API Level 22) for platformVersion in both the desired_capabilities
and in Appium Android client settings. Also, set the deviceName to LG G3.

"""

import unittest
import os
from appium import webdriver
from appium.webdriver.common.touch_action import TouchAction


from time import sleep

class TweetboxChromecastTests(unittest.TestCase):
    def setUp(self):
        app = os.path.join(os.path.dirname(__file__), '/Users/jhernandez/Dev/blah/jenkins-builds/', 'drop-1.0.0-60-staging-release.apk')
        app = os.path.abspath(app)

        appium_Url = 'http://127.0.0.1:4723/wd/hub'
        desired_capabilities = {
            'app': app,
            'platformName': 'Android',
            'platformVersion': '4.4 KitKat (API Level 19)',
            'deviceName': 'Samsung S4'
        }

        print("Webdriver request initiated. Waiting for response, this typically takes 2-3 min.")
        self.driver = webdriver.Remote(appium_Url, desired_capabilities)
        print("Webdriver response received")

        tapAction = TouchAction(self.driver)

    def tearDown(self):
        print "Beginning TearDown...\n\n"
        self.driver.back()
        self.driver.quit()
        #self.driver.implicitly_wait(30)
        #self.driver.open_notifications()
        #closemediabutton = self.driver.find_element_by_id('com.jawbone.drop:id/media_close')
        #tapaction = TouchAction(self.driver)
        #tapaction.tap(closemediabutton).perform()
        #self.driver.back()
        #self.driver.back()
        #TRY close_app() self.driver.close_app()
        #self.driver.close_app()



    def test_NUXTitle(self):
        print("====== TESTING: NUX Title Present... ======")
        self.driver.implicitly_wait(35)
        nuxtitle = self.driver.find_element_by_id('com.jawbone.drop.staging:id/nux_message')
        nuxtext = "DROP IS YOUR SOCIAL VIDEO DJ.\n\nGET READY FOR\nAN ENDLESS MIX\nOF MUSIC VIDEOS.\n\nIT'S GONNA ROCK."#nuxtitle.get_attribute("text")
        colorstatic = self.driver.find_element_by_id('com.jawbone.drop.staging:id/static_gif')

        self.assertEqual(nuxtitle.text, nuxtext, "ERROR: NUX Title did not display")
        self.assertIsNotNone(colorstatic, "ERROR: Background Color Static is not present.")
        print("====== NUX Title TESTING Complete ======")



    def test_VideoStarts(self):
        print("====== TESTING: Video Overlay Present... ======")
        self.driver.implicitly_wait(45)

        # DropTextView
        videooverlay = self.driver.find_element_by_id('com.jawbone.drop.staging:id/video_overlay')
        self.assertIsNotNone(videooverlay, "ERROR: Video does not seem to be present.")
        print("====== Video Overlay TESTING Complete ======")


    def test_TapToShowControls(self):
        print("====== TESTING: Tap to Show Controls Present... ======")
        sleep(50)
        actualtext = "TAP TO SHOW CONTROLS"
        extras = self.driver.find_element_by_id('com.jawbone.drop.staging:id/extras')
        tap2show = extras.find_element_by_class_name('android.widget.TextView')
        #tap2show = extras.find_element_by_android_uiautomator('android.widget.TextView')

        taptext = tap2show.get_attribute("text")

        print("Tap to show controls NUX should read: %s" % taptext)
        self.assertEqual(actualtext, taptext, "ERROR: Either the text does not match or is not present")
        print("====== Tap to Show Controls TESTING Complete ======")
        print("\n\n")
        print("====== TESTING: Tapping Tap to Show Controls banner... ======")
        self.driver.tap(tap2show).perform()
        self.driver.implicitly_wait(10)
        self.assertIsNone(tap2show, "ERROR: Tap to Show banner should have dismissed after tapping the screen")








if __name__ == '__main__':
    suite = unittest.TestSuite()
    suite.addTest(TweetboxChromecastTests("test_NUXTitle"))
    runner = unittest.TextTestRunner()
    runner.run(suite)
    #suite = unittest.TestLoader().loadTestsFromTestCase(TweetboxChromecastTests)