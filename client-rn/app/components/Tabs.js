import React from 'react';
import { Platform } from 'react-native';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { ScanTab } from '../screens/ScanTab';
import { IngredientsTab } from '../screens/IngredientsTab';
import { AccountTab } from '../screens/AccountTab';
import { createMaterialBottomTabNavigator } from '@react-navigation/material-bottom-tabs';
import { themeColor } from "./styles";

const Tab = createMaterialBottomTabNavigator();

let icons = {
  ios: {
    focused: {
      ScanTab: 'ios-camera',
      IngredientsTab: 'ios-list',
      AccountTab: 'ios-person'
    },
    normal: {
      ScanTab: 'ios-camera',
      IngredientsTab: 'ios-list',
      AccountTab: 'ios-person'
    },
  },
  android: {
    focused: {
      ScanTab: 'md-camera',
      IngredientsTab: 'md-list',
      AccountTab: 'md-person'
    },
    normal: {
      ScanTab: 'md-camera',
      IngredientsTab: 'md-list',
      AccountTab: 'md-person'
    }
  }
};

icons.web = icons.android;

let screenOptions = ({ route }) => ({
  tabBarIcon: ({ focused, color }) => {
    let iconName = icons[Platform.OS][focused ? 'focused' : 'normal'][route.name];

    return <Ionicons name={iconName} size={26} color={color} />;
  },
});

let tabBarStyle = {
  backgroundColor: '#fff'
};

export function Tabs() {
  return (
    <Tab.Navigator shifting={true} barStyle={tabBarStyle} screenOptions={screenOptions} activeColor={themeColor} inactiveColor="gray">
      <Tab.Screen name="ScanTab" component={ScanTab} />
      <Tab.Screen name="IngredientsTab" component={IngredientsTab} />
      <Tab.Screen name="AccountTab" component={AccountTab} />
    </Tab.Navigator>
  )
}
