//
//  DefaultTemplate.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 10/2/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit
import Parse

class DefaultTemplate: UIViewController {
	
	@IBOutlet weak var openMenuButton: UIBarButtonItem!
	@IBOutlet weak var imageView: UIImageView!
	@IBOutlet weak var titleLabel: UILabel!
	@IBOutlet weak var scrollTextView: UITextView!
	var exhibitTitle = "Welcome to MuseBeacon!"
	var exhibitText = "MuseBeacon is a system that allows museums to configure 'Beacons', or small Bluetooth connected devices, with information regarding the exhibits within their museum. This allows individuals to view additional information on the exhibits as they come in contact with them. To see this in action, open the menu using the icon in the top left corner. This list shows all exhibits in your vicinity."
	var audioFile = PFFile()
	var image = UIImage()
	
	
	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		self.scrollTextView.text = self.exhibitText
		self.titleLabel.text = self.exhibitTitle
		self.imageView.image = self.image
	}
	
	
	override func viewDidLoad()
	{
		super.viewDidLoad()
		openMenuButton.target = self.revealViewController()
		openMenuButton.action = Selector("revealToggle:")
		self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
		let tapGesture = UITapGestureRecognizer(target: self, action: Selector("imageTapDetected"))
		tapGesture.numberOfTapsRequired = 1
		self.imageView.userInteractionEnabled = true
		self.imageView.addGestureRecognizer(tapGesture)
	}
	
	override func didReceiveMemoryWarning()
	{
		super.didReceiveMemoryWarning()
	}
	
	func imageTapDetected()
	{
//		self.performSegueWithIdentifier("modalImageView", sender: self)
		
	}
	
	override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?)
	{
		let destViewController = segue.destinationViewController as! AudioPlayerViewController
		destViewController.audioFile = self.audioFile
	}

}
