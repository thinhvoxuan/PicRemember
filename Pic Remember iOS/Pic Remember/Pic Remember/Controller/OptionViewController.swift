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

    let SOUNDSECTION = 1
    let CLICKSOUND = 0
    let CORRECTSOUND = 1
    
    
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
    
    override func viewDidAppear(animated: Bool) {
        self.tableView.reloadData()
        self.updateUI();
    }
    
    func updateUI(){
        typeSetting.detailTextLabel!.text = self.user.type
        levelSetting.detailTextLabel!.text = self.user.level
        themeSetting.detailTextLabel!.text = self.user.theme
    }
    
    override func prepareForSegue(segue: UIStoryboardSegue, sender: AnyObject?) {
        if let detailView = segue.destinationViewController as? DetailViewController {
            if let usertable = sender! as? UITableViewCell {
                let key = usertable.textLabel!.text!
                let type : String = self.user.valueForKey(key.lowercaseString) as? String ?? ""
                detailView.kindKeyData = key;
                detailView.currentData = type;
            }
        }
    }
 
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let tableCell = super.tableView(tableView, cellForRowAtIndexPath: indexPath)
        
        let index = indexPath.row
        let section = indexPath.section

        if (section == 1){
            var valueCell:Bool = false
            switch index {
            case CLICKSOUND:
                valueCell = user.clicksound
            case CORRECTSOUND:
                valueCell = user.correctsound
            default:
                break
            }
            tableCell.accessoryType = valueCell ?  UITableViewCellAccessoryType.Checkmark :  UITableViewCellAccessoryType.None
        }
        return tableCell;
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let index = indexPath.row
        let section = indexPath.section
        let cell = tableView.cellForRowAtIndexPath(indexPath);
        
        if (section == 1){
            var newValue:Bool = false
            switch index {
            case CLICKSOUND:
                user.clicksound = !user.clicksound
                newValue = user.clicksound
            case CORRECTSOUND:
                user.correctsound = !user.correctsound
                newValue = user.correctsound
            default:
                break
            }    
            cell!.accessoryType = newValue ?  UITableViewCellAccessoryType.Checkmark :  UITableViewCellAccessoryType.None
        }
    }
    
}
