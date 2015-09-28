//
//  SlideMenuTableViewController.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 9/27/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit

class SlideMenuTableViewController: UITableViewController
{
	
	var tableArray = [String]()

    override func viewDidLoad()
	{
        super.viewDidLoad()
		tableArray = ["First", "Second", "Third"]
    }

    override func didReceiveMemoryWarning()
	{
        super.didReceiveMemoryWarning()
    }

//    override func numberOfSectionsInTableView(tableView: UITableView) -> Int
//	  {
//        return 0
//    }

    override func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int
	{
        return tableArray.count
    }

    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell 
	{
        let cell = tableView.dequeueReusableCellWithIdentifier("cell", forIndexPath: indexPath)
		cell.textLabel?.text = tableArray[indexPath.row]
        return cell
    }
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
	{
		let navController = segue.destinationViewController as! UINavigationController
		let destination = navController.childViewControllers[0] as! ViewController
		let indexPath = self.tableView?.indexPathForSelectedRow as NSIndexPath!
		destination.varView = indexPath.row
	}

    /*
    // Override to support conditional editing of the table view.
    override func tableView(tableView: UITableView, canEditRowAtIndexPath indexPath: NSIndexPath) -> Bool 
	{
        // Return false if you do not want the specified item to be editable.
        return true
    }
    */

    /*
    // Override to support editing the table view.
    override func tableView(tableView: UITableView, commitEditingStyle editingStyle: UITableViewCellEditingStyle, forRowAtIndexPath indexPath: NSIndexPath) 
	{
        if editingStyle == .Delete {
            // Delete the row from the data source
            tableView.deleteRowsAtIndexPaths([indexPath], withRowAnimation: .Fade)
        } else if editingStyle == .Insert {
            // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view
        }    
    }
    */

    /*
    // Override to support rearranging the table view.
    override func tableView(tableView: UITableView, moveRowAtIndexPath fromIndexPath: NSIndexPath, toIndexPath: NSIndexPath) 
	{

    }
    */

    /*
    // Override to support conditional rearranging of the table view.
    override func tableView(tableView: UITableView, canMoveRowAtIndexPath indexPath: NSIndexPath) -> Bool 
	{
        // Return false if you do not want the item to be re-orderable.
        return true
    }
    */

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) 
	{
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
