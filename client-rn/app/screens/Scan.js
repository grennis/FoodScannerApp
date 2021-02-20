import React, { useContext, useEffect, useState } from 'react';
import { View, Text, StyleSheet, Platform } from 'react-native';
import { RNCamera } from 'react-native-camera';
import AppContext from "../context/AppContext";

export function Scan({navigation}) {
  const context = useContext(AppContext);
  const [scanned, setScanned] = useState(false);

  const navigateToDetails = () => {
    navigation.goBack();
    navigation.navigate("Details");
  };

  if (Platform.OS === "web") {
    useEffect(() => {
      setTimeout(() => {
        handleBarCodeScanned({ type: '', data: '0030000050408' })
      }, 1000);
    }, []);
  }

  const handleBarCodeScanned = ({ type, data }) => {
    setScanned(true);
    context.setCurrentUPC(data);

    navigateToDetails();
  };

  return (
    <View
      style={{
        flex: 1,
        flexDirection: 'column',
        justifyContent: 'flex-end',
      }}>
      { !scanned &&
      <RNCamera
        onBarCodeRead={ handleBarCodeScanned }
        style={ StyleSheet.absoluteFillObject }
        type={RNCamera.Constants.Type.back}
        captureAudio={false}
        androidCameraPermissionOptions={{
          title: 'Camera Permission',
          message: 'Enable camera permission to scan barcodes',
          buttonPositive: 'Ok',
          buttonNegative: 'Cancel',
        }}
      />
      }
    </View>
  );
}
