//
//  CardModelManager.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/29/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit

class CardModelManager: NSObject {
    let totalCard = 13;
    var arrayRandomCard = [String]()
    
    func checkSameCard(idx1 : Int, idx2: Int) -> Bool {
        let image1trim = arrayRandomCard[idx1].stringByReplacingOccurrencesOfString("x", withString: "", options: NSStringCompareOptions.LiteralSearch, range: nil)
        let image2trim = arrayRandomCard[idx2].stringByReplacingOccurrencesOfString("x", withString: "", options: NSStringCompareOptions.LiteralSearch, range: nil)
        return image1trim == image2trim
    }
    
    func genArrayListCard(totalcell: Int) -> [String] {
        let haftTotal = totalcell / 2
        let rangeFrom = (1..<haftTotal).generate()
        var resultArray : [AnyObject] = [AnyObject] ()
        
        for i in 1...haftTotal {
            resultArray.insert(i, atIndex: roundRollet(resultArray.count + 1))
        }
        resultArray = map(resultArray, { (element: AnyObject) -> AnyObject in
            if let intValue =  element as? Int {
                return "hinh\(intValue)"
            }
            return element
        })
        
        for i in 1...haftTotal {
            resultArray.insert(i, atIndex: roundRollet(resultArray.count + 1))
        }
        resultArray = map(resultArray, { (element: AnyObject) -> AnyObject in
            if let intValue =  element as? Int {
                return "hinh\(intValue)x"
            }
            return element
        })
        
        self.arrayRandomCard = map(resultArray) { (element: AnyObject) -> String in
            if let stringValue = element as? String {
                return stringValue
            }
            return element.description
        }
        println(self.arrayRandomCard)
        return self.arrayRandomCard
    }
    
    
    func roundRollet(max: Int) -> Int{
        var begin : Int = 0
        let randomMax = Int(arc4random()) % 100
        for _ in 0...randomMax {
            begin = (begin + Int(arc4random())) % max
        }
        return begin
    }
}
