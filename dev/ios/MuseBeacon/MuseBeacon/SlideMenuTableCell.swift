//
//  SlideMenuTableCell.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 10/2/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit
import Parse
import ParseUI

class SlideMenuTableCell: PFTableViewCell
{
	@IBOutlet weak var titleLabel: UILabel!
	var slideText: String = ""
	var slideImage: PFFile = PFFile()
	var slideAudio: PFFile = PFFile()
	var type: String = ""
}