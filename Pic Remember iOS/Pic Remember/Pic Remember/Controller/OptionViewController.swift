//
//  OptionViewController.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/26/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit
import DLRadioButton
import M13Checkbox

class OptionViewController: UIViewController {

    @IBOutlet weak var typeButton: DLRadioButton!
    
    @IBAction func selectType(sender: DLRadioButton) {
        let type = sender.currentTitle!
        println("type change: \(type)")
    }
    
    @IBAction func changeLevel(sender: DLRadioButton) {
        let level = sender.currentTitle!
        println("level change: \(level)")
    }

    
    @IBAction func changeTheme(sender: DLRadioButton) {
        let theme = sender.currentTitle!
        println("theme change: \(theme)")
    }
    
    @IBOutlet weak var clicksound: M13Checkbox! {
        didSet{
            clicksound!.titleLabel.text = "Click"
            clicksound.checkAlignment = M13CheckboxAlignmentLeft
        }
    }
    
    @IBOutlet weak var clickCorrect: M13Checkbox! {
        didSet{
            clickCorrect!.titleLabel.text = "Correct"
            clickCorrect.checkAlignment = M13CheckboxAlignmentLeft
        }
    }
    
}
