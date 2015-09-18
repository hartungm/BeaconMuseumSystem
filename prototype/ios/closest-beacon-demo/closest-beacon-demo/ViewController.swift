//
//  ViewController.swift
//  closest-beacon-demo
//
//  Created by Michael Hartung on 8/5/15.
//  Copyright (c) 2015 hartung-michael. All rights reserved.
//

import UIKit
import CoreLocation

class ViewController: UIViewController, CLLocationManagerDelegate {
	
	let locationManager = CLLocationManager()
	let region = CLBeaconRegion(proximityUUID: NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D"), identifier: "Estimotes")
	
	let colors = [
		14429: UIColor.greenColor(),
		9306: UIColor.purpleColor(),
		22318: UIColor.blueColor()
	]

	override func viewDidLoad() {
		super.viewDidLoad()
		// Do any additional setup after loading the view, typically from a nib.
		locationManager.delegate = self
		if(CLLocationManager.authorizationStatus() != CLAuthorizationStatus.AuthorizedWhenInUse)
		{
			locationManager.requestWhenInUseAuthorization()
		}
		locationManager.startRangingBeaconsInRegion(region)
//		registerLocalNotification()
	}

	override func didReceiveMemoryWarning() {
		super.didReceiveMemoryWarning()
		// Dispose of any resources that can be recreated.
	}

	func locationManager(manager: CLLocationManager!, didRangeBeacons beacons: [AnyObject]!, inRegion region: CLBeaconRegion!) {
//		println(beacons)
		let knownBeacons = beacons.filter{ $0.proximity != CLProximity.Unknown }
		for beacon in beacons
		{
			let beaconNow = beacon as! CLBeacon
			println("\(beaconNow.minor.integerValue) - Proximity: \(beaconNow.proximity.rawValue) - RSSI: \(beaconNow.rssi) - Accuracy: \(beaconNow.accuracy)")
		}
		println("")
		if( knownBeacons.count > 0)
		{
			let closestBeacon = knownBeacons[0] as! CLBeacon
			self.view.backgroundColor = self.colors[closestBeacon.minor.integerValue]
		}
	}
	
	// Notifications Section
//	func registerLocalNotification()
//	{
//		let localNotification = UILocalNotification()
//		localNotification.regionTriggersOnce = false
//		localNotification.alertAction = "Welcome Back!"
//		localNotification.alertBody = "Found your Estimote Beacons!"
//		localNotification.alertTitle = "Estimote Beacon Test"
//		localNotification.region = region
//		localNotification.timeZone = NSTimeZone.defaultTimeZone()
//		localNotification.soundName = UILocalNotificationDefaultSoundName
//		let application = UIApplication.sharedApplication()
//		application.cancelAllLocalNotifications()
//		application.scheduleLocalNotification(localNotification)
//	}

}

