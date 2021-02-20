import React, { useContext, useEffect, useState } from 'react';
import { Text, View, FlatList, StyleSheet, TouchableOpacity, Alert, Platform } from 'react-native';
import { GlobalStyles } from '../components/styles';
import Ionicons from 'react-native-vector-icons/Ionicons';
import { scanDescription } from "../components/utils";
import AppContext from "../context/AppContext";
import { getScans, listenScans } from "../firebase/firestore";

const renderItem = (scan, itemClick) => {
  return <TouchableOpacity onPress={ () => itemClick(scan) }>
    <View style={ Styles.listItem }>
      <Text style={ Styles.listItemText }>{ scanDescription(scan) }</Text>
      <Text style={ Styles.listItemDate }>{ scan.date.toLocaleDateString() }</Text>
    </View>
  </TouchableOpacity>
};

export function ScanList({ navigation }) {
  const context = useContext(AppContext);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (!context.user) {
      context.setScans([]);
      return;
    }

    setLoading(true);

    listenScans(context.user.uid, scans => {
      context.setScans(scans);
      setLoading(false);
    });
  }, [context.user]);

  const itemClick = (scan) => {
    context.setCurrentScan(scan);

    navigation.navigate('Details');
  };

  const clickScan = () => {
    if (context.user) {
      navigation.navigate('Scan');
    } else {
      if (Platform.OS === "web") {
        navigation.navigate('Account');
      } else {
        Alert.alert("Login", "Login to start scanning!", [{
          text: 'OK',
          onPress: () => navigation.navigate('Account')
        }]);
      }
    }
  }

  let emptyMessage = null;
  if (context.user) {
    if (context.scans.length === 0) {
      if (loading)
        emptyMessage = <Text style={ Styles.emptyText }>Loading...</Text>;
      else
        emptyMessage = <Text style={ Styles.emptyText }>You have not scanned any foods yet. Press the scan button to start</Text>;
    }
  } else {
    emptyMessage = <Text style={ Styles.emptyText }>Login to start scanning grocery items</Text>;
  }


  console.info('Scans: ' + context.scans.map(s => s.id).join(","));

  return (
    <View style={ GlobalStyles.tab }>
      <TouchableOpacity style={{ alignItems: 'center' }} onPress={clickScan}>
        <Ionicons name="ios-barcode" size={ 120 } color="#000"/>
        <Text style={ GlobalStyles.title }>Scan</Text>
      </TouchableOpacity>
      {emptyMessage}
      <FlatList style={{ width: '100%', marginTop: 20 }} data={ context.scans } renderItem={({ item }) => renderItem(item, itemClick) }
        keyExtractor={ item => `${ item.id }` }/>
    </View>
  );
}

const Styles = StyleSheet.create({
  listItem: {
    paddingTop: 16,
    paddingBottom: 16,
    paddingStart: 16,
    paddingEnd: 16,
    flexDirection: 'row'
  },
  listItemTitle: {
    fontSize: 20,
  },
  listItemText: {
    fontSize: 16,
    flex: 1
  },
  listItemDate: {
    width: 80,
    textAlign: 'right'
  },
  emptyText: {
    marginTop: 40,
    marginStart: 20,
    marginEnd: 20,
    textAlign: 'center'
  }
});
