//
//  HistoryManager.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/30/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit
import RealmSwift

class HistoryManager: NSObject {
    
    class var sharedInstance: HistoryManager {
        struct Static {
            static var onceToken: dispatch_once_t = 0
            static var instance: HistoryManager? = nil
        }
        dispatch_once(&Static.onceToken) {
            Static.instance = HistoryManager()
        }
        return Static.instance!
    }
    
//    override init() {
//        
//    }
    
    func saveHistory(username : String, totaltime: Int, totalclick: Int, level: String){
        let realmInstance = Realm()
        let newRecord = History()
        newRecord.name = username
        newRecord.time = totaltime
        newRecord.click = totalclick
        newRecord.level = level
        
        realmInstance.write { () -> Void in
            realmInstance.add(newRecord, update: false)
        }
    }
    
    func getHistoryWithLevel(level: String) -> Results<History> {
        let realmInstance = Realm()
        let result = realmInstance.objects(History).filter("level = '\(level)' ").sorted("time")
        return result
    }
    
}
