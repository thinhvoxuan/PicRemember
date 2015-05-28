//
//  UserProfile.swift
//  Pic Remember
//
//  Created by thinhvoxuan on 5/27/15.
//  Copyright (c) 2015 thinhvoxuan. All rights reserved.
//

import UIKit

class UserProfile: NSObject {
    let userdefault = NSUserDefaults.standardUserDefaults();
    
    func getStringFromNSUserDefault(key: String, defaultValue: String = " ") -> String {
        let value = userdefault.valueForKey(key) as? String ?? defaultValue
        return value;
    }
    
    func setStringToNSUserDefault(key: String, value: String) {
        userdefault.setValue(value, forKey: key);
    }
    
    struct Setting {
        static let TypeKey : String = "SETTING.TYPE"
        static let LevelKey : String = "SETTING.LEVEL"
        static let ThemeKey : String = "SETTING.THEME"
    }
    
    var type: String{
        set{
            setStringToNSUserDefault(Setting.TypeKey, value: newValue)
        }
        get{
            return getStringFromNSUserDefault(Setting.TypeKey, defaultValue: "Time")
        }
    }
    var level: String {
        set{
            setStringToNSUserDefault(Setting.LevelKey, value: newValue)
        }
        get{
            return getStringFromNSUserDefault(Setting.LevelKey, defaultValue: "12")
        }
    }
    
    var theme: String {
        set{
            setStringToNSUserDefault(Setting.ThemeKey, value: newValue)
        }
        get{
            return getStringFromNSUserDefault(Setting.ThemeKey, defaultValue: "Default")
        }
    }
    
}
