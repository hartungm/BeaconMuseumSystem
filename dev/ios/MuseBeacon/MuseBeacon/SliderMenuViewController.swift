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

class SliderMenuViewController: PFQueryTableViewController {
	
//	override init(style: UITableViewStyle, className: String?) {
//		super.init(style: style, className: className)
//		parseClassName = "Todo"
//		pullToRefreshEnabled = true
//		paginationEnabled = true
//		objectsPerPage = 25
//	}
//	
//	required init?(coder aDecoder: NSCoder) {
//		super.init(coder: aDecoder)
//		parseClassName = "Todo"
//		pullToRefreshEnabled = true
//		paginationEnabled = true
//		objectsPerPage = 25
//	}
	
	override func viewWillAppear(animated: Bool)
	{
		super.viewWillAppear(animated)
	}
	
	override func queryForTable() -> PFQuery
	{
		let query = PFQuery(className: "DefaultTemplate")
//		if self.objects!.count == 0
//		{
//			query.cachePolicy =
//		}
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
			print(selectedCell.titleLabel.text!)
			print(selectedCell.slideText)
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
