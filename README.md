## Food Scanner Mobile App

This app allows a user to scan UPC barcodes for food/grocery items, and then report any ingredients which have been flagged as sensitive. 

This allows users with food allergies or sensitivies to more easily identify foods they can and can't eat.

<p float="left">
<img src="https://raw.githubusercontent.com/grennis/FoodScannerApp/master/screens/android1.png" width="30%" />
<img src="https://raw.githubusercontent.com/grennis/FoodScannerApp/master/screens/android2.png" width="30%" />
<img src="https://raw.githubusercontent.com/grennis/FoodScannerApp/master/screens/ios1.png" width="29.3%" />
</p>

## In this repo

This repo contains:

* Android native mobile app code (Kotlin)
* iOS/Android React Native mobile app code (Javascript)
* Firebase backend function (Typescript)

User scans are stored in Firebase backend. Only the UPC code and product title are stored in the database, as per TOS of Edamam API. 

Users must authenticate with Firebase authenticatin (currently only Google Sign-In supported) to
use the service.

## Building

### Prerequisites

In order to build this application you need to supply the following:

* Your own [Edamam](https://developer.edamam.com/) API keys for the ingredient service in the Firebase function environment configuration.
* Your own Firebase project with configuration files (`google-services.json` on Android and `GoogleService-Info.plist` on iOS).

### Building the Firebase backend

* In the `server` folder, execute:
  * `firebase use <your project name>`
  * `firebase functions:config:set edamam.id=<your edamam id>`
  * `firebase functions:config:set edamam.key=<your edamam key>`
  * `firebase deploy --only functions`

### Building the native Android app

* Load `client-android` in Android Studio, build and run.

### Building the React Native apps

* In `client-rn`, directory execute `npm install`
* In `client-rn/ios`, execute `pod install`
* In `client-rn` execute `npm start android` or `npm start ios`

## Disclaimer

This app and source code is for demonstration and entertainment purposes only. Do not rely on this app to determine if you can safely eat any food. Always read and verify the ingredient labels.
