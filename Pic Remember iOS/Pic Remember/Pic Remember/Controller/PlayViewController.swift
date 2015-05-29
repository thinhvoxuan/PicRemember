//
//  PlayViewController.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/26/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit
import KxMenu

class PlayViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    
    
    var totalTime = 0
    var totalClick = 0
    var timmer : NSTimer? = nil
    
    @IBOutlet weak var timmerLabel: UILabel!
    
    let padding = 1
    var numberInRow = 3
    var numberInCol = 4
    
    var arrayItem = [Int]()
    var arrayCorrect = [Int]()
    
    var cardModel = CardModelManager()

    
    @IBOutlet weak var cardGrid: UICollectionView! {
        didSet{
            self.cardGrid.delegate = self
            self.cardGrid.dataSource = self
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        self.navigationItem.setHidesBackButton(true, animated: false);
        cardModel.genArrayListCard(12)
    }
    
    @IBAction func openOption(sender: UIBarButtonItem) {
        let item = KxMenuItem("Pause", image: nil, target: self, action: Selector("pauseGame"))
        let item1 = KxMenuItem("Quit", image: nil, target: self, action: Selector("quitGame"))
        let buttonItemView = sender.valueForKey("view") as! UIView
        var startFrame = buttonItemView.frame;
        startFrame.origin.y += 10
        KxMenu.showMenuInView(self.navigationController!.view, fromRect: startFrame, menuItems: [item, item1]);
    }
    
    func sameCard(firstElement: Int, secondElement: Int) -> Bool {
        return self.cardModel.checkSameCard(firstElement, idx2: secondElement)
    }
    
    func addIndex(idx: Int){
        arrayItem.append(idx)
        if (arrayItem.count > 0 && arrayItem.count % 2 == 0){
            dispatch_after(dispatch_time(DISPATCH_TIME_NOW, Int64(0.5 *  Double(NSEC_PER_SEC))), dispatch_get_main_queue(), { () -> Void in
                if (self.arrayItem.count >= 2) {
                    let first = self.arrayItem.removeAtIndex(0)
                    let second = self.arrayItem.removeAtIndex(0)
                    if self.sameCard(first, secondElement: second) {
                        self.arrayCorrect.append(first)
                        self.arrayCorrect.append(second)
                    }
                    self.cardGrid.reloadData()
                }
            })
        }
        self.cardGrid.reloadData()
    }
    
    
    func startTimmer(){
        if self.timmer == nil {
            self.timmer = NSTimer.scheduledTimerWithTimeInterval(1, target: self, selector: "updateTimmer", userInfo: nil, repeats: true)
        }
    }
    func stopTimmer(){
        if self.timmer != nil {
            self.timmer!.invalidate()
            self.timmer = nil
        }
    }
    
    func updateTimmer(){
        totalTime += 1;
        updateLabel()
    }
    func updateLabel(){
        self.timmerLabel.text = "Time: \(totalTime)  Click: \(totalClick)"
    }
    
    func pauseGame(){
        stopTimmer()
    }
    
    func quitGame(){
        self.navigationController!.popViewControllerAnimated(true);
    }
    
    // MARK: - Collection Datasource
    func collectionView(collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int{
        return 12
    }
    
    func collectionView(collectionView: UICollectionView, cellForItemAtIndexPath indexPath: NSIndexPath) -> UICollectionViewCell{
        
        let cell = collectionView.dequeueReusableCellWithReuseIdentifier(
            "cardCell", forIndexPath: indexPath) as! CardCollectionCell
        let row = indexPath.row
        
        cell.hidden = false
        let imageName = self.cardModel.arrayRandomCard[row]
        cell.OriginImage.image = UIImage(named: imageName)
        cell.OriginImage.hidden = true;
        cell.HideImage.hidden = false;

        let idx = indexPath.row
        if contains(arrayItem, idx) {
            cell.OriginImage.hidden = false;
            cell.HideImage.hidden = true;
        }
        if contains(arrayCorrect, idx){
            cell.hidden = true;
        }
        return cell
    }
    
    func collectionView(collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAtIndexPath indexPath: NSIndexPath) -> CGSize{
        let sizeWidth = Int(collectionView.bounds.width) / numberInCol - numberInCol * padding;
        let sizeHeight = Int(collectionView.bounds.height) / numberInRow - numberInRow * padding;
        return CGSize(width: sizeWidth, height: sizeHeight);
    }
    
    // MARK: - Collection Delegate
    func collectionView(collectionView: UICollectionView, didSelectItemAtIndexPath indexPath: NSIndexPath){
        startTimmer()
        totalClick += 1
        updateLabel();
        addIndex(indexPath.row)
    }
    
    
}
