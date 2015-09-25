//
//  ViewController.swift
//  closest-beacon-demo
//
//  Created by Michael Hartung on 8/5/15.
//  Copyright (c) 2015 hartung-michael. All rights reserved.
//

import UIKit
import CoreLocation
import Parse

class ViewController: UIViewController, CLLocationManagerDelegate
{
	@IBOutlet weak var majorLabel: UILabel!
	@IBOutlet weak var minorLabel: UILabel!
	@IBOutlet weak var parseLabel: UILabel!
	
	let locationManager = CLLocationManager()
//	NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")
	let region = CLBeaconRegion(proximityUUID: NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")!, identifier: "Estimotes")
	
	let colors = [
		14429: UIColor.greenColor(),
		9306: UIColor.purpleColor(),
		22318: UIColor.blueColor()
	]

	override func viewDidLoad()
	{
		super.viewDidLoad()
		// Do any additional setup after loading the view, typically from a nib.
		locationManager.delegate = self
		if(CLLocationManager.authorizationStatus() != CLAuthorizationStatus.AuthorizedWhenInUse)
		{
			locationManager.requestWhenInUseAuthorization()
		}
		locationManager.startRangingBeaconsInRegion(region)
		
		let query = PFQuery(className: "TestObject")
		query.whereKey("foo", equalTo: "bar")
		do
		{
			let parseArray = try query.findObjects()
			for object : PFObject in parseArray
			{
				print(object.objectForKey("foo"))
				self.parseLabel.text = object.objectForKey("foo") as? String
			}
		}
		catch
		{
			print("Issue grabbing parse data")
		}
		
	//	registerLocalNotification()
	}

	override func didReceiveMemoryWarning() {
		super.didReceiveMemoryWarning()
		// Dispose of any resources that can be recreated.
	}

	func locationManager(manager: CLLocationManager, didRangeBeacons beacons: [CLBeacon], inRegion region: CLBeaconRegion) {
//		println(beacons)
		let knownBeacons = beacons.filter{ $0.proximity != CLProximity.Unknown }
		for beacon in beacons
		{
			let beaconNow = beacon 
			print("\(beaconNow.minor.integerValue) - Proximity: \(beaconNow.proximity.rawValue) - RSSI: \(beaconNow.rssi) - Accuracy: \(beaconNow.accuracy)", terminator: "")
		}
		print("", terminator: "")
		if( knownBeacons.count > 0)
		{
			let closestBeacon = knownBeacons[0] 
			self.view.backgroundColor = self.colors[closestBeacon.minor.integerValue]
			self.minorLabel.text = "Minor: \(closestBeacon.minor.integerValue)"
			self.majorLabel.text =  "Major: \(closestBeacon.major.integerValue)"
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

