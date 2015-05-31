//
//  ScoreViewController.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/26/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit
import RealmSwift


class ScoreViewController: UIViewController, UITableViewDataSource, UITableViewDelegate {

    @IBOutlet weak var menuSegment: UISegmentedControl!{
        didSet{
            menuSegment.selectedSegmentIndex = 0
        }
    }
    
    
    var historyManager = HistoryManager.sharedInstance
    var historyArray: Results<History>? = nil
    
    @IBOutlet weak var historyTable: UITableView! {
        didSet{
            historyTable.dataSource = self
            historyTable.delegate = self
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        settingHistory()
        // Do any additional setup after loading the view.
    }
    
    func settingHistory(){
        var level = "12"
        switch menuSegment.selectedSegmentIndex {
            case 1:
                let level = "24"
            case 2:
                let level = "48"
            case 0: fallthrough
            default:
                let level = "12"
                break;
        }
        historyArray = historyManager.getHistoryWithLevel(level)
        self.historyTable.reloadData()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func tableView(tableView: UITableView, numberOfRowsInSection section: Int) -> Int{
        return self.historyArray!.count
    }
    
    func tableView(tableView: UITableView, cellForRowAtIndexPath indexPath: NSIndexPath) -> UITableViewCell {
        if let cellHistory = tableView.dequeueReusableCellWithIdentifier("historyCell") as? HistoryItemCellTableViewCell {
            let index = indexPath.row
            let itemAtIndex = self.historyArray![index]
            cellHistory.setTupeHistory(itemAtIndex)
            return cellHistory
        }
        return HistoryItemCellTableViewCell()
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
