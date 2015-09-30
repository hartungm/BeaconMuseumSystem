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
		let cell = tableView.dequeueReusableCellWithIdentifier("cell") as? PFTableViewCell
		cell!.textLabel?.text = object?.objectForKey("Title") as? String
		return cell
	}
	
}
