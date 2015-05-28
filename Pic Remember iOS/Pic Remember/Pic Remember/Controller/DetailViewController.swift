//
//  DetailViewController.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/28/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit

class DetailViewController: UITableViewController {
    
    var kindKeyData : String = "";
    var currentData : String = "";
    var user = UserProfile()
    
    override func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        let cell = super.tableView(tableView, cellForRowAtIndexPath: indexPath)
        if cell.textLabel?.text == currentData {
            cell.accessoryType = UITableViewCellAccessoryType.Checkmark
        }
        return cell
    }
    
    override func tableView(tableView: UITableView, didSelectRowAtIndexPath indexPath: NSIndexPath) {
        let cell = tableView.cellForRowAtIndexPath(indexPath)
        cell!.accessoryType = UITableViewCellAccessoryType.Checkmark
        let value = cell!.textLabel!.text!
        self.user.setValue(value, forKey: kindKeyData)
        self.navigationController?.popViewControllerAnimated(true);
    }
}
