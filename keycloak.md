# Keycloack

#### Download page
```
https://www.keycloak.org/downloads
```

Server Administration Guide:
```
https://www.keycloak.org/docs/latest/server_admin/index.html
```
#### OpenId connect endpoints
```
https://www.okta.com/
```
JWT Debugger:
```
https://jwt.io/
```

#### Keycloak Features
- Single Sign-On
- Identity Brokering
- Social Login (Facebook, Google, etc)
- Centralized Administration Console
- Standard Protocols - SAML 2.0, OAuth 2.0, OpenID Connect
- LDAP and Active Directory Integration
- MFA
- Fine grained Authorization Services

#### Commands:
start keycloak:
```
kc.sh start-dev
```

### Key Terms
**Realm** - is a logical domain which groups together users, roles, clients, and other configurations.

#### Client and Scopes

**Client** is an entity that can request Keycloak to authenticate a user. A client can represent applications, services, or devices that have registered with Keycloak and are capable of interacting with the Keycloak server.

**Client Scopes** are a way to limit the claims and roles that are included in access tokens. They allow you to define a set of default client-level roles, protocol mappers, and other configurations that can be shared between multiple clients. This helps in reducing duplication and provides a centralized place to manage common settings.

#### Access Token and Endpoints
##### Access Token types:
- Opaque Token
- Structured Token (JWT)

In Keycloak, an **access token** is a JSON Web Token (JWT) provided to a client application after a successful authentication of a user. This token contains information (or claims) about the user and its roles. The client application can use this token to access the resources (like APIs) on behalf of the authenticated user.

**Endpoints** in Keycloak refer to the URLs that Keycloak exposes to offer various functionalities. For example, the authentication endpoint is used to authenticate users, the token endpoint is used to issue tokens, and the logout endpoint is used to log out users. These endpoints are part of Keycloak's implementation of the OAuth2 and OpenID Connect protocols.

**Token introspection** in Keycloak is a way to get details about a token (like an access token or a refresh token). It's a feature provided by Keycloak as part of its implementation of the OAuth 2.0 Introspection specification.

When a client application has a token and it wants to validate it or get more information about it, it can send a request to the Keycloak's introspection endpoint. The endpoint will return a response that includes whether the token is active and other details about the token such as its issue time, expiration time, subject, and more.

This is particularly useful for resource servers that want to validate an incoming token, but it can also be used by other clients that want to check the state of a token.

#### Authorization Code and Refresh Token Grants

OAuth 2.0 Grant Types in Keycloak are methods through which an application can gain an access token, which represents the user's permission for the client to access their data

OAuth Grant Types:
- Authorization Code
- Authorization Code with PKCE
- Implicit (Deprecated)
- Refresh Token
- Client Credentials
- Device Code
- Password (Deprecated)

#### PKCE
PKCE (Proof Key for Code Exchange) is a security extension to OAuth 2.0 for public clients on mobile devices. It's designed to prevent interception of the authorization code by a malicious application that has managed to get installed on the same device.

In Keycloak, PKCE is supported for the Authorization Code Grant flow. Here's how it works:
- The client creates a random string called the "code verifier". It then hashes this string to create the "code challenge".
- When the client redirects the user to Keycloak for authentication, it includes the code challenge and the method used to hash the code verifier in the authorization request.
- After the user authenticates, Keycloak returns an authorization code to the client. Keycloak also associates the code challenge and the hashing method with this authorization code.
- The client then exchanges the authorization code for an access token. In this request, the client includes the original code verifier.
- Keycloak hashes the code verifier using the previously provided method and compares it with the previously received code challenge. If they match, Keycloak knows that the client making the token request is the same as the client that initiated the authorization request. Keycloak then returns the access token to the client.

This process ensures that even if a malicious party can intercept the authorization code, they cannot exchange it for an access token without the original code verifier. This significantly enhances the security of the OAuth 2.0 flow.

- Extension of the Authorization Code grant type
- Usually used by public clients
- Generate a code_verifier
  - Min = 43 chars and Max = 128 chars
  - Random and impractical to guess
- Send code_challenge with authorize request
  - code_challenge = BASE64URL-ENCODE(SHA256(ASCII(code_verifier)))
- send code_verifier with token request for grant tyype = code
  - that's the proof

#### Client Credential and Password Grants
**Client Credentials Grant**: This grant type is suitable when the client is requesting access to the protected resources under its control, or those of another resource owner that have been previously arranged with the authorization server. The client application authenticates itself with its client ID and client secret and receives an access token. This access token is not associated with a particular user, it's associated with the client application itself. In Keycloak, you can enable this grant type in the client settings by setting "Access Type" to "confidential" and "Service Accounts Enabled" to "on".
**
Resource Owner Password Credentials (ROPC) Grant**: This grant type is suitable in cases where the resource owner has a trust relationship with the client, such as the device operating system or a highly privileged application. The client application collects the user's username and password and sends them directly to the server to obtain an access token. This grant type should be used sparingly and only in scenarios where other grant types are not viable, as it requires users to share their credentials with the client application. In Keycloak, you can enable this grant type in the client settings by setting "Direct Access Grants Enabled" to "on".

Remember, the choice of grant type depends on the use case and the level of trust between the client and the server. It's always recommended to use the most secure grant type that fits your use case.

#### OpenID Connect Core
OpenId Scopes and Token
- openid profile email address phone
- /userinfo endpoint access

OpenID Connect (OIDC) is a simple identity layer built on top of the OAuth 2.0 protocol, which allows clients to verify the identity of the end-user based on the authentication performed by an authorization server. It also allows to obtain basic profile information about the end-user in an interoperable and REST-like manner.

**Keycloak** is an open-source Identity and Access Management solution that supports OIDC, among other protocols. Here's how it works with OIDC:

1. **User Authentication**: Keycloak authenticates the user through various means like username/password, social logins, or two-factor authentication.
2. **ID Tokens**: After successful authentication, Keycloak issues an ID Token to the client. The ID Token is a JWT (JSON Web Token) that contains user profile information like the user's name, email, and so on.
3. **Access Tokens**: Along with the ID Token, Keycloak also issues an Access Token that the client can use to access the resources on behalf of the user.
4. **UserInfo Endpoint**: The client can use the Access Token to retrieve more user information from the UserInfo Endpoint.
5. **Session Management**: Keycloak also provides session management and can handle user sessions for your applications.
6. **Token Introspection**: Keycloak supports token introspection which allows a client to get details about an access token.

Keycloak provides a comprehensive set of features for an OIDC provider, including support for all standard OIDC flows, a user-friendly admin console for managing realms, clients, and users, and adapters for different programming languages and platforms.

#### OpenID Connect
OpenID Documentation:
```
https://openid.net/developers/how-connect-works/
```
OpenID Connect 1.0 Core:
```
https://openid.net/developers/how-connect-works/
```
OpenID Connect 1.0 Discovery:
```
https://openid.net/specs/openid-connect-discovery-1_0.html
```
OpenID Connect Core - Client Authentication:
```
https://openid.net/specs/openid-connect-core-1_0.html#ClientAuthentication
```

#### Access Token Example
```json
{
    "exp" : 1709477365,
    "iat" : 1709477065,
    "auth_time" : 1709477065,
    "jti" : "56eb8e03-a501-4d71-b38c-099fe0a714f9",
    "iss" : "http://127.0.0.1:9090/realms/oauthrealm",
    "aud" : "account",
    "sub" : "9b319159-6761-4a1f-9907-b02d263580ad",
    "typ" : "Bearer",
    "azp" : "bugtracker",
    "nonce" : "TKuQExkchYh_B0uK3WnX56CbyogvD3rgyt_1RmH7Fjg",
    "session_state" : "d8a0aed6-b7c1-4f37-869c-da5892b542db",
    "acr" : "1",
    "allowed-origins" : [
        "http://localhost:8080"
    ],
    "realm_access" : {
        "roles" : [
            "default-roles-oauthrealm",
            "offline_access",
            "uma_authorization"
        ]
    },
    "resource_access" : {
        "account" : {
            "roles" : [
                "manage-account",
                "manage-account-links",
                "view-profile"
            ]
        }
    },
    "scope" : "openid bugtracker email profile",
    "sid" : "d8a0aed6-b7c1-4f37-869c-da5892b542db",
    "email_verified" : false,
    "name" : "John Doe",
    "preferred_username" : "johndoe",
    "given_name" : "John",
    "family_name" : "Doe",
    "email" : "johndoe@company.com"
}
```
- `exp`: Expiration time of the token in Unix time. The client should treat the token as invalid after this time.
- `iat`: Issued at time. The time the token was issued at, in Unix time.
- `auth_time`: The time the user authentication occurred.
- `jti`: JWT ID. A unique identifier for the token, can be used to prevent the JWT from being replayed.
- `iss`: Issuer. The server that issued the token.
- `aud`: Audience. The intended recipient of the token. The client should verify that it is an intended audience for the token.
- `sub`: Subject. The subject of the token, usually a machine-readable identifier of the user who authorized the token.
- `typ`: Type of the token.
- `azp`: Authorized party. The client ID of the application the token was issued for.
- `nonce`: A string value provided by the application to prevent token replay attacks.
- `session_state`: Session state. An opaque string representing the session state.
- `acr`: Authentication Context Class Reference. The class of authentication methods used.
- `allowed-origins`: A list of allowed origins for the token.
- `realm_access`: Roles that the user has in the realm.
- `resource_access`: Roles that the user has in each client application.
- `scope`: Scopes that the client has been granted access to.
- `sid`: Session ID. An opaque string representing the user's session.
- `email_verified`: A boolean indicating whether the user's email address has been verified.
- `name`: The user's full name.
- `preferred_username`: The user's preferred username.
- `given_name`: The user's given name.
- `family_name`: The user's family name.
- `email`: The user's email address.

#### Spring Security OAuth 2 Documentation
Spring Security OAuth 2 - Summary of Client Features:
```
https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client
```
Spring Security OAuth 2 - Support for Login:
```
https://docs.spring.io/spring-security/reference/servlet/oauth2/login/core.html
```
Spring Security OAuth 2 - Support for Client:
```
https://docs.spring.io/spring-security/reference/servlet/oauth2/client/index.html
```
Gitlab OpenID configuration (gitlab .well-known/openid-configuration):
```
https://docs.gitlab.com/ee/integration/openid_connect_provider.html
```
Gitlab OpenID configuration link:
```
https://gitlab.com/.well-known/openid-configuration
```
Spring Security Resource Server Documentation:
```
https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-resource-server
```

#### Problems with Multiple Identity Providers
- need to handle multiple Identity Providers
- need to translate claims in code
- users are not consolidated into one place
- architecture problem
  - SPA
  - microservices problem

#### SAML
**SAML (Security Assertion Markup Language)** is a standard protocol for web browser Single Sign-On (SSO) using secure tokens. Keycloak, as an Identity Provider (IdP), supports SAML 2.0.

With SAML, Keycloak can be used to provide authentication and authorization services to web applications (SAML Service Providers) in a secure way. It allows users to authenticate once with Keycloak, and then gain access to multiple applications without needing to authenticate with each individual application.

Keycloak handles the exchange of SAML messages (in XML format) between the Service Provider and the IdP. It can issue SAML assertions on behalf of users, manage user sessions, and provide other features like role-based access control, user federation, and social logins.





