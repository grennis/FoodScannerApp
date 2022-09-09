//
//  AccountView.swift
//  FoodScanner
//

import Foundation
import SwiftUI
import Combine
import GoogleSignIn
import GoogleSignInSwift

struct AccountView: View {
    class ViewModel: ObservableObject {
        var dependencies: Dependencies
        private var cancellables = Set<AnyCancellable>()

        typealias Dependencies = HasAuthenticationService
        
        init(dependencies: Dependencies) {
            self.dependencies = dependencies
        }
        
        func signInClicked() {
            guard let viewController = (UIApplication.shared.connectedScenes.first as? UIWindowScene)?.keyWindow?.rootViewController else {
                return
            }
            
            dependencies.authenticationService.login(from: viewController)
        }
        
        func signOutClicked() {
            dependencies.authenticationService.logout()
        }
    }

    @ObservedObject
    private var viewModel: ViewModel
    
    @ObservedObject
    private var authenticationState: AuthenticationState

    init(_ viewModel: ViewModel) {
        self.viewModel = viewModel
        self.authenticationState = viewModel.dependencies.authenticationService.state
    }

    var body: some View {
        VStack {
            if let user = authenticationState.user {
                Spacer()
                Text(user.name)
                AsyncImage(url: user.profileImage)
                Spacer()
                Button("Logout") {
                    viewModel.signOutClicked()
                }.buttonStyle(.borderedProminent)
                Spacer()
            } else {
                GoogleSignInButton(action: viewModel.signInClicked)
                    .frame(maxWidth: 300)
            }
        }
    }
}
