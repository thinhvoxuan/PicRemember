//
//  PlayViewController.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/26/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit
import KxMenu

class PlayViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setHidesBackButton(true, animated: false);
        // Do any additional setup after loading the view.
        
    }

    override func didReceiveMemoryWarning() {
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

    @IBAction func openOption(sender: UIBarButtonItem) {
        
        
        
        let item = KxMenuItem("string",image: UIImage(named: "actionIcon")!, target: self, action: Selector("clickItem"))
        
        let item1 = KxMenuItem("string",image: UIImage(named: "actionIcon")!, target: self, action: Selector("clickItem"))
        
        let item2 = KxMenuItem("string",image: UIImage(named: "actionIcon")!, target: self, action: Selector("clickItem"))
        
        let item3 = KxMenuItem("string",image: UIImage(named: "actionIcon")!, target: self, action: Selector("clickItem"))
        
        
        let buttonItemView = sender.valueForKey("view") as! UIView
        KxMenu.showMenuInView(self.navigationController!.view, fromRect: buttonItemView.frame, menuItems: [item, item1, item2, item3]);
        
    }
    
    func clickItem(){
        println("option click")
    }
}
