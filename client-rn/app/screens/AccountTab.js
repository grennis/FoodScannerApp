import React from 'react';
import { createStackNavigator } from '@react-navigation/stack';
import { Profile } from './Profile';
import { headerTitleBarStyle } from "../components/styles";

export function AccountTab() {
  let Stack = createStackNavigator();

  return <Stack.Navigator screenOptions={headerTitleBarStyle}>
    <Stack.Screen name="Login" component={Profile} options={{title: 'Login'}}/>
  </Stack.Navigator>
}
