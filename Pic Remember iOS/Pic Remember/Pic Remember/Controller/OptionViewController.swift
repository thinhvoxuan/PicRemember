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

class OptionViewController: UITableViewController {

    let user = UserProfile()
    
    @IBOutlet weak var typeSetting: UITableViewCell!{
        didSet{
            typeSetting.detailTextLabel!.text = self.user.type
        }
    }

    @IBOutlet weak var levelSetting: UITableViewCell!{
        didSet{
            levelSetting.detailTextLabel!.text = self.user.level
        }
    }
    @IBOutlet weak var themeSetting: UITableViewCell!{
        didSet{
            themeSetting.detailTextLabel!.text = self.user.theme
        }
    }
}
