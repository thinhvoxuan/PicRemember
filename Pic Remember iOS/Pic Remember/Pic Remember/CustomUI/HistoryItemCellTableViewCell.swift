//
//  HistoryItemCellTableViewCell.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/31/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit

class HistoryItemCellTableViewCell: UITableViewCell {
    
    @IBOutlet weak var imageScore: UIImageView!
    @IBOutlet weak var username: UILabel!
    @IBOutlet weak var totalTime: UILabel!
    @IBOutlet weak var totalClick: UILabel!
    
    func setTupeHistory(history: History){
        self.username.text = history.name
        self.totalClick.text = history.click.description
        self.totalTime.text = history.time.description
    }
    
}
