import { StyleSheet, StatusBar } from 'react-native';

export const GlobalStyles = StyleSheet.create({
  tab: {
    flex: 1,
    marginTop: 0,
    alignItems: 'center'
  },
  tabStartPadded: {
    flex: 1,
    marginTop: 0,
    padding: 20
  },
  title: {
    fontSize: 16,
    fontWeight: 'bold'
  }
});

export const themeColor = '#7e56c1';

export const headerTitleBarStyle = {
  headerStyle: {
    backgroundColor: themeColor,
    elevation: 12,
  },

  // https://github.com/react-navigation/react-navigation/issues/5936
  //safeAreaInsets: { top: 0 },
  headerStatusBarHeight: 0,

  headerTintColor: '#FFF',
  headerTitleStyle: {
    //fontWeight: 'bold',
  }
};
