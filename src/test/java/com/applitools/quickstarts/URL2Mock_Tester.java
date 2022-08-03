package com.applitools.quickstarts;

import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import org.imgscalr.Scalr;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResults;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.config.Configuration;
import com.applitools.eyes.selenium.ClassicRunner;

public class URL2Mock_Tester {

	private Configuration config;
	private BatchInfo batchInfo;
	private String apiKey;
	private String testName;
	private String appName;
	private int viewPortWidth, viewPortHeight;

	public void run(String[] image) {
		TestResultsSummary allTestResults;
		batchInfo = new BatchInfo("Test");

		apiKey = "mLthewPXtXy5Uln25n2jiNaRrxY5p9BTKrmiB872qu0110";

		config = new Configuration();

		config.setApiKey(apiKey);
		config.setBatch(batchInfo);
		testName = "test";
		appName = "app";
		viewPortWidth = Integer.parseInt(image[1]);
		viewPortHeight = Integer.parseInt(image[2]);

		System.out.println("Starting URL Test - ");

		allTestResults = testURL("https://cloud.e-news.chanel.com/template-rsvp", config);
		System.out.println("Finished Testing URL");

		config = updateConfig(config, allTestResults);

		System.out.println("Starting Image Test");

		TestResults res = testImage(image[0], config);

		System.out.println(res);
		System.out.println("Done");

	}

	private Configuration updateConfig(Configuration config, TestResultsSummary testResults) {

		TestResults res = testResults.getAllResults()[0].getTestResults();

		config.setHostApp(res.getHostApp());
		config.setHostOS(res.getHostOS());

		return config;
	}

	public TestResultsSummary testURL(String url, Configuration configuration) {
		return testURL(url, configuration, false);
	}

	public TestResultsSummary testURL(String url, Configuration configuration, Boolean saveDiffs) {
		ClassicRunner runner = new ClassicRunner();

		runner.setDontCloseBatches(true);
		System.setProperty("webdriver.chrome.driver","C:\\Users\\vchinnap\\Downloads\\chromedriver_win32\\chromedriver.exe");

		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
//		options.setBinary("C:\\Users\\vchinnap\\Downloads\\chromedriver_win32\\chromedriver.exe");
		ChromeDriver driver = new ChromeDriver(options);

		com.applitools.eyes.selenium.Eyes eyes = new com.applitools.eyes.selenium.Eyes(runner);
		eyes.setConfiguration(configuration);
		eyes.open(driver, appName, testName, new RectangleSize(viewPortWidth, viewPortHeight));
		driver.get(url);
		eyes.check(com.applitools.eyes.selenium.fluent.Target.window().fully());
		driver.quit();
		eyes.closeAsync();
		TestResultsSummary allTestResults = runner.getAllTestResults(false);

		return allTestResults;

	}

	public boolean isValidURL(String url) {

	    try {
	        new URL(url).toURI();
	    } catch (Exception e) {
	        return false;
	    }

	    return true;
	}
	
	
	public TestResults testImage(String imgURL, Configuration configuration)
	{
		com.applitools.eyes.images.Eyes eyes = new com.applitools.eyes.images.Eyes();
		TestResults res = null;

		eyes.setConfiguration(configuration);

		try {
			eyes.setApiKey(apiKey);
			eyes.open(appName, testName, new RectangleSize(viewPortWidth, viewPortHeight));

			BufferedImage img;

			// Load page image from URL
			if (isValidURL(imgURL))
				img = ImageIO.read(new URL(imgURL));
			else// Load page image from File
			{
				img = ImageIO.read(new File(imgURL));
			}

			// Zeplin exports images in x2 widthxheight, this is to resize
			BufferedImage scaledImg = Scalr.resize(img, Scalr.Method.ULTRA_QUALITY, viewPortWidth, viewPortHeight);

			// // Visual validation.
			eyes.check(testName, com.applitools.eyes.images.Target.image(scaledImg));

			// End visual UI testing.
			res = eyes.close(false);
		} catch (Exception ex) {
			System.out.println(ex);
		} finally {
			// If the test was aborted before eyes.close was called, ends the test as
			// aborted.
			eyes.abortIfNotClosed();
		}
		return res;

	}

}
