//
//  ViewController.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 9/24/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit

class ViewController: UIViewController
{

	@IBOutlet weak var someLabel: UILabel!
	@IBOutlet weak var openMenuButton: UIBarButtonItem!
	
	var varView = Int()
	
	override func viewDidLoad()
	{
		super.viewDidLoad()
		openMenuButton.target = self.revealViewController()
		openMenuButton.action = Selector("revealToggle:")
		self.view.addGestureRecognizer(self.revealViewController().panGestureRecognizer())
		
		if(varView == 0)
		{
			someLabel.text = "Strings"
		}
		else
		{
			someLabel.text = "Others"
		}
	}

	override func didReceiveMemoryWarning()
	{
		super.didReceiveMemoryWarning()
		// Dispose of any resources that can be recreated.
	}


}

