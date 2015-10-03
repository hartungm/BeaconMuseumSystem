//
//  SliderMenuViewController.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 9/28/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit
import Parse
import Bolts
import ParseUI
import CoreLocation

class SliderMenuViewController: PFQueryTableViewController, CLLocationManagerDelegate
{
	
	let locationManager = CLLocationManager()
	var beaconArray: [String] = []
	let region = CLBeaconRegion(proximityUUID: NSUUID(UUIDString: "B9407F30-F5F8-466E-AFF9-25556B57FE6D")!, identifier: "Estimotes")

	override func viewWillAppear(animated: Bool)
	{
		super.viewWillAppear(animated)
	}
	
	override func viewDidLoad()
	{
		locationManager.delegate = self
		if(CLLocationManager.authorizationStatus() != CLAuthorizationStatus.AuthorizedWhenInUse)
		{
			locationManager.requestWhenInUseAuthorization()
		}
		locationManager.startRangingBeaconsInRegion(region)
	}
	
	func locationManager(manager: CLLocationManager, didRangeBeacons beacons: [CLBeacon], inRegion region: CLBeaconRegion)
	{
		// Code for beacon ranging
		let knownBeacons = beacons.filter{ $0.proximity != CLProximity.Unknown }
		var knownBeaconArray: [String] = []
		for beacon in knownBeacons
		{
			let beaconString = "\(beacon.major)-\(beacon.minor)"
			print(beaconString)
			knownBeaconArray.append(beaconString)
		}
		if knownBeaconArray != beaconArray
		{
			beaconArray = knownBeaconArray
			self.tableView.reloadData()
		}
	}
	
	override func queryForTable() -> PFQuery
	{
//		let queryArray = ["10", "20"]
		let query = PFQuery(className: "DefaultTemplate")
		query.whereKey("BeaconID", containedIn: beaconArray)
		return query
	}
	
	override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath, object: PFObject?) -> PFTableViewCell?
	{
		let cell = tableView.dequeueReusableCellWithIdentifier("cell") as? SlideMenuTableCell
		cell!.type = "DefaultTemplate"
		if( object?.objectForKey("Title") != nil )
		{
			cell!.titleLabel?.text = object?.objectForKey("Title") as? String
		}
		if( object?.objectForKey("Text") != nil )
		{
			cell!.slideText = object?.objectForKey("Text") as! String
		}
		if( object?.objectForKey("Image") != nil )
		{
			cell!.slideImage = object?.objectForKey("Image") as! PFFile
		}
		return cell
	}
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
	{
		let navController = segue.destinationViewController as! UINavigationController
		let indexPath = self.tableView?.indexPathForSelectedRow as NSIndexPath!
		let selectedCell = self.tableView?.cellForRowAtIndexPath(indexPath) as! SlideMenuTableCell
		if ( selectedCell.type == "DefaultTemplate")
		{
			let destination : DefaultTemplate = navController.childViewControllers[0] as! DefaultTemplate
			destination.exhibitTitle = selectedCell.titleLabel.text!
			destination.exhibitText = selectedCell.slideText
			selectedCell.slideImage.getDataInBackgroundWithBlock
			{
				(imageData: NSData?, error: NSError?) -> Void in
				if error == nil
				{
					if let imageData = imageData
					{
						destination.image = UIImage(data: imageData)!
					}
				}
			}
			
		}
	}
}
