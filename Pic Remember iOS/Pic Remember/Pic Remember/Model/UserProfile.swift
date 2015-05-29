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
    
    
    func getBoolFromNSUserDefault(key: String, defaultValue: Bool =  true) -> Bool {
        let value = userdefault.valueForKey(key) as? Bool ?? defaultValue
        return value;
    }
    
    func setBoolToNSUserDefault(key: String, value: Bool) {
        userdefault.setValue(value, forKey: key);
    }
    
    struct Setting {
        static let TypeKey : String = "SETTING.TYPE"
        static let LevelKey : String = "SETTING.LEVEL"
        static let ThemeKey : String = "SETTING.THEME"
        static let ClickKey : String = "SETTING.CLICKKEY"
        static let CorrectKey : String = "SETTING.CORRECTKEY"
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
    
    var numberColAndRow : (Int, Int) {
        get{
            switch level {
                case "24": return (4,6)
                case "48": return (3,16)
                case "12": fallthrough
                default: return (4,3)
            }
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
    
    var correctsound :Bool {
        set {
            setBoolToNSUserDefault(Setting.CorrectKey, value: newValue)
        }
        get{
            return getBoolFromNSUserDefault(Setting.CorrectKey, defaultValue: true)
        }
    }
    
    var clicksound :Bool {
        set {
            setBoolToNSUserDefault(Setting.ClickKey, value: newValue)
        }
        get{
            return getBoolFromNSUserDefault(Setting.ClickKey, defaultValue: true)
        }
    }
    
}
