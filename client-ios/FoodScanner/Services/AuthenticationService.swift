//
//  AuthenticationService.swift
//  FoodScanner
//

import Foundation
import Combine
import FirebaseAuth
import UIKit
import GoogleSignIn
import Firebase

struct User {
    var id: String
    var name: String
    var profileImage: URL?
}

class AuthenticationState: ObservableObject {
    @Published var user: User? = nil

    var isAuthenticated: Bool {
        user != nil
    }
}

protocol AuthenticationService {
    var state: AuthenticationState { get }
    
    func login(from viewController: UIViewController)
    func logout()
}

protocol HasAuthenticationService {
    var authenticationService: AuthenticationService { get }
}

class StubAuthenticationService: AuthenticationService {
    var state = AuthenticationState()
    
    func login(from viewController: UIViewController) {}
    func logout() {}
}

class FirebaseAuthenticationService: AuthenticationService {
    var state = AuthenticationState()
    var googleConfig: GIDConfiguration

    init() {
        let clientID = FirebaseApp.app()!.options.clientID!
        
        googleConfig = GIDConfiguration(clientID: clientID)

        _ = Auth.auth().addStateDidChangeListener { auth, user in
            if let user = user {
                self.state.user = User(
                    id: user.uid,
                    name: user.displayName ?? "",
                    profileImage: user.photoURL
                )
            } else {
                self.state.user = nil
            }
        }
    }

    func login(from viewController: UIViewController) {
        GIDSignIn.sharedInstance.signIn(with: googleConfig,
          presenting: viewController) { [unowned self] user, error in
            self.continueLogin(user: user)
        }
    }
    
    private func continueLogin(user: GIDGoogleUser?) {
        guard let authentication = user?.authentication,
              let idToken = authentication.idToken else {
            return
        }

        let credential = GoogleAuthProvider.credential(
            withIDToken: idToken,
            accessToken: authentication.accessToken
        )

        Auth.auth().signIn(with: credential) { authResult, error in
            if let error = error {
                // TODO report error
                print(error)
            }
        }
    }
    
    func logout() {
        try? Auth.auth().signOut()
        GIDSignIn.sharedInstance.signOut()
    }
}
