//
//  AudioPlayerViewController.swift
//  MuseBeacon
//
//  Created by Michael Hartung on 11/23/15.
//  Copyright © 2015 hartung-michael. All rights reserved.
//

import UIKit
import AVKit
import AVFoundation
import Parse

class AudioPlayerViewController: AVPlayerViewController
{
	var audioFile = PFFile()

    override func viewDidLoad()
	{
        super.viewDidLoad()
		if( audioFile.url != nil)
		{
			let audioURL = NSURL(string: audioFile.url!)
			self.player = AVPlayer(URL: audioURL!)
			self.player?.play()
		}
		// Do any additional setup after loading the view.
    }

    override func didReceiveMemoryWarning()
	{
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    

    /*
    // MARK: - Navigation

    // In a storyboard-based application, you will often want to do a little preparation before navigation
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        // Get the new view controller using segue.destinationViewController.
        // Pass the selected object to the new view controller.
    }
    */

}
