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

* In Firebase console set the Android SHA1 fingerprint to your keystore's value.
* Load `client-android` in Android Studio, build and run.

### Building the iOS app

* [Add the URL scheme](https://developers.google.com/identity/sign-in/ios/start-integrating#add_a_url_scheme_for_google_sign-in_to_your_project) of your Firebase project to the iOS build settings. This will create the Info.plist file.
* Download the GoogleService-Info.plist file of your Firebase project to the client-ios directory.
* Open the xcodeproj, build and run.

### Building the React Native apps

* In Xcode, [Add the URL scheme](https://developers.google.com/identity/sign-in/ios/start-integrating#add_a_url_scheme_for_google_sign-in_to_your_project) of your Firebase project to the iOS build settings.
* Create the file `app/firebase/keys.js` with content `googleWebClientID = '(Your client ID)';`
* Run `npm install && cd ios && pod install && cd ..`
* Run `npx react-native run-ios` or `npx react-native run-android`

## Disclaimer

This app and source code is for demonstration and entertainment purposes only. Do not rely on this app to determine if you can safely eat any food. Always read and verify the ingredient labels.
