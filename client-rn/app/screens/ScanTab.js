import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { ScanList } from './ScanList';
import { Scan } from './Scan';
import { Details } from './Details';
import { headerTitleBarStyle } from "../components/styles";

export function ScanTab() {
  let Stack = createStackNavigator();

  return <Stack.Navigator screenOptions={headerTitleBarStyle}>
    <Stack.Screen name="ScanList" component={ ScanList } options={{ title: 'Scans' }}/>
    <Stack.Screen name="Scan" component={ Scan } options={{ title: 'Scan' }}/>
    <Stack.Screen name="Details" component={ Details } options={{ title: 'Details' }}/>
  </Stack.Navigator>
}
