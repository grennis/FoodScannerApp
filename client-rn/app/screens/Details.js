import React, { useEffect, useState, useContext } from 'react';
import { Text, View, Image, FlatList, StyleSheet, ActivityIndicator } from 'react-native';
import { GlobalStyles } from '../components/styles';
import { scanDescription } from "../components/utils";
import Ionicons from 'react-native-vector-icons/Ionicons';
import { fetchIngredients } from "../firebase/functions";
import AppContext from "../context/AppContext";
import { storeScan } from "../firebase/firestore";
import { getLevelColor, getSummary } from "../data/ingredientsUI";

export function Details() {
  const context = useContext(AppContext);
  const scan = context.currentScan;
  const [data, setData] = useState({ loadingState: 'loading' });

  useEffect(() => {
    fetchIngredients(scan.upc, !scan.id).then(result => {
      setData(result);
    });
  }, []);

  const renderIngredient = ({ item: { text, level } }) => {
    return <View style={ Styles.listItem }>
      <Text style={{ ...Styles.listItemText, color: getLevelColor(level) }}>{ text }</Text>
    </View>
  };

  let image, message;

  if (data.loadingState === 'loading') {
    message = <Text style={{ marginTop: 20 }}>Loading...</Text>;
    image = <ActivityIndicator size="large" style={{ width: '60%', height: 200 }}  />;
  } else if (data.loadingState === 'loaded') {
    const summary = getSummary(data);

    message = <View style={{ marginTop: 20, alignItems: 'center', flexDirection: 'row' }}>
      <Ionicons name={summary.icon} size={24} color={summary.iconColor} />
      <Text style={{ marginStart: 12, marginTop: 4 }}>{summary.text}</Text>
    </View>;

    if (data.imageURL) {
      image = <Image style={{ width: '60%', height: 200 }} source={{ uri: data.imageURL }} />;
    } else {
      image = <View style={{ width: '60%', height: 200, alignItems: 'center', justifyContent: 'center' }}><Ionicons name='ios-apps' size={80} color='#444444'/></View>;
    }
  } else {
    message = <Text style={{ marginTop: 20 }}>Unable to load details for this UPC.</Text>;
    image = <View style={{ width: '60%', height: 200, alignItems: 'center', justifyContent: 'center' }}><Ionicons name='ios-close-circle' size={80} color='#444444'/></View>;
  }

  return (
    <View style={ GlobalStyles.tabStartPadded }>
      <View style={{ flexDirection: 'row', width: '100%' }}>
        {image}
        <Text style={{ marginStart: 20, marginEnd: 20, flex: 1, fontSize: 16, fontWeight: 'bold' }}>{ scanDescription(data) }</Text>
      </View>
      {message}
      {data.loadingState === 'loaded' && <Text style={{ marginTop: 20, fontWeight: 'bold' }}>Ingredients</Text>}
      <FlatList style={{ width: '100%', marginTop: 12, flex: 1 }}
                data={ data.ingredients }
                renderItem={ renderIngredient }
                keyExtractor={(item, index) => String(index)} />
    </View>
  );
}

const Styles = StyleSheet.create({
  listItem: {
    paddingTop: 4,
    paddingBottom: 4,
  },
  listItemTitle: {
    fontSize: 20,
  },
  listItemText: {
    fontSize: 14,
  },
});
