//
//  FoodScannerApp.swift
//  FoodScanner
//

import SwiftUI
import FirebaseCore
import GoogleSignIn

struct AppDependencies: HasAuthenticationService {
    var authenticationService: AuthenticationService
}

@main
struct FoodScannerApp: App {
    let dependencies: AppDependencies
    
    init() {
        FirebaseApp.configure()
        
        dependencies = AppDependencies(
            authenticationService: FirebaseAuthenticationService()
        )
    }
    
    var body: some Scene {
        WindowGroup {
            MainTabView(dependencies: dependencies)
                .onOpenURL { url in
                    GIDSignIn.sharedInstance.handle(url)
                }
        }
    }
}
