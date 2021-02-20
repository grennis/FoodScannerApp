import 'react-native-gesture-handler';
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { Tabs } from './app/components/Tabs';
import { AppProvider } from './app/context/AppProvider';

export default function App() {
  return (
    <AppProvider>
      <NavigationContainer>
        <Tabs />
      </NavigationContainer>
    </AppProvider>
  );
}
