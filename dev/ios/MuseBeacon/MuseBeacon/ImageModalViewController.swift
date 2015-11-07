//
//  ImageModalViewController.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 11/7/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit

class ImageModalViewController: UIViewController
{
	
	@IBOutlet weak var imageView: UIImageView!
	var imageModal : UIImage?
	
    override func viewDidLoad()
	{
        super.viewDidLoad()
		self.imageView.image = imageModal
		let dismissViewTap = UITapGestureRecognizer(target: self, action: Selector("dismissView"))
		dismissViewTap.numberOfTapsRequired = 1
		self.imageView.userInteractionEnabled = true
		self.imageView.addGestureRecognizer(dismissViewTap)
    }

    override func didReceiveMemoryWarning()
	{
        super.didReceiveMemoryWarning()
    }
}
