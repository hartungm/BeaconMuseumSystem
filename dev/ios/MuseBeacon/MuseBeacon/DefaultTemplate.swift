//
//  DefaultTemplate.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 10/2/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit

class DefaultTemplate: UIViewController {
	
	@IBOutlet weak var openMenuButton: UIBarButtonItem!
	@IBOutlet weak var imageView: UIImageView!
	@IBOutlet weak var textLabel: UILabel!
	@IBOutlet weak var titleLabel: UILabel!
	var exhibitTitle = ""
	var exhibitText = ""
	var image = UIImage()
	
	override func viewWillAppear(animated: Bool) {
		super.viewWillAppear(animated)
		self.textLabel.text = self.exhibitText
		self.titleLabel.text = self.exhibitTitle
		self.imageView.image = self.image
	}
	
	
	override func viewDidLoad()
	{
		super.viewDidLoad()
		openMenuButton.target = self.revealViewController()
		openMenuButton.action = Selector("revealToggle:")
		self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
//		self.imageView = UIImageView()
	}
	
	override func didReceiveMemoryWarning()
	{
		super.didReceiveMemoryWarning()
		// Dispose of any resources that can be recreated.
	}

}
