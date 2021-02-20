import React, { useState, useEffect } from 'react';
import AppContext from './AppContext';
import auth from '@react-native-firebase/auth';
import { translateUser } from "../firebase/auth";

export function AppProvider({ children }) {
  let [scans, setScans] = useState([]);
  let [currentScan, setCurrentScan] = useState(null);
  let [user, setUser] = useState(null);

  const setCurrentUPC = upc => {
    setCurrentScan({ upc: upc });
  };

  function onAuthStateChanged(user) {
    setUser(translateUser(user));
  }

  useEffect(() => {
    return auth().onAuthStateChanged(onAuthStateChanged);
  }, []);

  return (
    <AppContext.Provider
      value={{
        scans: scans,
        currentScan: currentScan,
        user: user,
        setUser: setUser,
        setScans: setScans,
        setCurrentScan: setCurrentScan,
        setCurrentUPC: setCurrentUPC,
      }}
    >
      {children}
    </AppContext.Provider>
  );
}
