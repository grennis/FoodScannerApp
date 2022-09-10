import React, { useContext, useEffect, useState } from 'react';
import { View, Button, Text, Image } from 'react-native';
import { GlobalStyles } from '../components/styles';
import { loginWithGoogle, logout } from "../firebase/auth";
import AppContext from "../context/AppContext";
import { GoogleSigninButton } from "@react-native-google-signin/google-signin";

export function Profile({ navigation }) {
  const [loading, setLoading] = useState(false);
  const context = useContext(AppContext);
  const user = context.user;

  const loginClicked = () => {
    setLoading(true);

    loginWithGoogle()
      .catch(err => {
        console.info(err)
      })
      .done(() => {
        setLoading(false);
      });
  };

  const logoutClicked = () => {
    setLoading(true);

    logout().done(() => {
      setLoading(false);
    });
  };

  return (
    <View style={GlobalStyles.tab}>
      <View style={{ marginTop: 80 }}>
        {!user && loading && <View style={{ alignItems: 'center' }}>
          <Text style={{ marginTop: 40 }}>Please Wait...</Text>
        </View>}
        {user && <View style={{ alignItems: 'center' }}>
          <Image style={{ width: 200, height: 200 }} source={{ uri: user.photoURL }} />
          <Text style={{ marginTop: 40 }}>Welcome, {user.name}</Text>
          <View style={{ marginTop: 40 }}>
            <Button style={{ marginTop: 40 }} onPress={logoutClicked} title="Logout" />
          </View>
        </View>}
        {!loading && !user && <GoogleSigninButton
          style={{ width: 192, height: 48 }}
          size={GoogleSigninButton.Size.Wide}
          color={GoogleSigninButton.Color.Dark}
          onPress={loginClicked} />}
      </View>
    </View>
  );
}
