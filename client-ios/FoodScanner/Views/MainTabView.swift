//
//  MainTabView.swift
//  FoodScanner
//

import Foundation
import SwiftUI

struct MainTabView: View {
    var dependencies: HasAuthenticationService
    
    var body: some View {
        TabView {
            ScanView()
                .tabItem {
                    Image(systemName: "camera")
                    Text("Scan")
                }
            IngredientsView()
                .tabItem {
                    Image(systemName: "books.vertical")
                    Text("Ingredients")
                }
            AccountView(.init(dependencies: dependencies))
                .tabItem {
                    Image(systemName: "person")
                    Text("Account")
                }
        }
    }
}

struct MainTabView_Previews: PreviewProvider {
    static var previews: some View {
        let dependencies = AppDependencies(
            authenticationService: StubAuthenticationService()
        )
        
        MainTabView(dependencies: dependencies)
    }
}

