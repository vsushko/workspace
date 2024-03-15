# Keycloak

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
./kc.sh start-dev --http-port <PORT>
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

Copy here X509 Certificate for formatting:
```
https://www.samltool.com/format_x509cert.php
```

#### Spring Security - oauth2Login versus oauth2Client
**HttpSecurity.oauth2Client(..)** should be used when the application needs exclusive OAuth capability to access a Resource Server. By calling the method, the relevant filters are registered in the Spring Security Filter chain and it generates the redirect "**authorize**" URL. This redirect URL needs to be registered with the Identity Provider.

The format of the redirect authorize URL is:
```
<APPLICATION_ROOT>/authorize/oauth2/code/REG-ID
 
# Example for registration id 'gitlab-oauth'
http://localhost:8080/authorize/oauth2/code/gitlab-oauth
```
**HttpSecurity.oauth2Login(..)** should be used when the application needs to login to the application using OpenID Connect and optionally want to call microservice using an access token. By calling the method, the relevant filters are registered in the Spring Security Filter chain and it generates the redirect "**login**" URL. This login URL needs to be registered with the Identity Provider.

The format of the redirect login URL is:
```
<APPLICATION_ROOT>/login/oauth2/code/REG-ID
 
# Example for registration id 'keycloak-oidc'
http://localhost:8080/login/oauth2/code/keycloak-oidc
```
Spring Security OAuth Client and Login:
```
https://docs.spring.io/spring-security/reference/servlet/oauth2/index.html#oauth2-client
```

#### ServiceLoader
```
https://docs.oracle.com/javase/8/docs/api/java/util/ServiceLoader.html
```

#### Spi
Keycloak's `Spi` class is part of Keycloak's Service Provider Interface (SPI). SPIs are used in Keycloak to provide extensibility. They allow you to plug in your own providers for various aspects of the system.

For example, you might want to plug in your own provider for user storage, so you would implement the UserStorageProvider SPI. Or you might want to plug in your own provider for events, so you would implement the EventListenerProvider SPI.

The `Spi` class itself is a base class for all SPIs. It defines a few methods that all SPIs must implement, such as getName() (which returns the name of the SPI) and getProviderClass() (which returns the class of the provider for this SPI).

Here is a simple example of how you might define a new SPI:
```java
public class MySpi implements Spi {

    @Override
    public boolean isInternal() {
        return false;
    }

    @Override
    public String getName() {
        return "mySpi";
    }

    @Override
    public Class<? extends Provider> getProviderClass() {
        return MyProvider.class;
    }

    @Override
    public Class<? extends ProviderFactory> getProviderFactoryClass() {
        return MyProviderFactory.class;
    }
}
```
In this example, `MyProvider` would be your custom provider class, and `MyProviderFactory` would be a factory class that creates instances of `MyProvider`.

Keycloak's SPIs:
```
http://127.0.0.1:9090/admin/master/console/#/master/providers
```

#### ProvierFactory
The `ProviderFactory` class in Keycloak is part of Keycloak's Service Provider Interface (SPI). It's used to create instances of a specific `Provider`.

A `Provider` in Keycloak is a specific implementation of a service. For example, you might have a `UserStorageProvider` that interfaces with a MySQL database, and another `UserStorageProvider` that interfaces with a PostgreSQL database. Each of these would be a `Provider`.

The `ProviderFactory` is responsible for creating instances of these `Providers`. It has a single method, `create(KeycloakSession session)`, which creates and returns a new instance of the `Provider`.

Here's a simple example of a `ProviderFactory`:

```java
public class MyProviderFactory implements ProviderFactory<MyProvider> {

    @Override
    public MyProvider create(KeycloakSession session) {
        return new MyProvider(session);
    }

    // Other methods like init, postInit, close, getId...
}
```
In this example, MyProvider is a custom Provider class, and MyProviderFactory is a factory that creates instances of MyProvider. The create method takes a KeycloakSession as a parameter, which can be used to access various aspects of the current Keycloak session.

In Keycloak, the `Provider` class is a part of the Service Provider Interface (SPI). It represents a specific implementation of a service. For instance, you might have different `Provider` implementations for user storage, each interfacing with a different type of database.

A `Provider` is created by a `ProviderFactory`, which is also part of the SPI. The `ProviderFactory` is responsible for creating instances of a `Provider`.

Here's a simple example of a `Provider`:

```java
public class MyProvider implements UserStorageProvider {

    private KeycloakSession session;

    public MyProvider(KeycloakSession session) {
        this.session = session;
    }

    // Implement the methods defined in the UserStorageProvider interface...
}
```
In this example, MyProvider is a custom Provider class that implements the UserStorageProvider interface. It uses a KeycloakSession to access various aspects of the current Keycloak session.
The `KeycloakSession` class in Keycloak represents a session with the Keycloak server. It provides methods to access various aspects of the session, such as the user's authentication state, the user's identity, and the user's permissions.

Here's a simple example of how you might use a `KeycloakSession`:

```java
public class MyProvider implements UserStorageProvider {

    private KeycloakSession session;

    public MyProvider(KeycloakSession session) {
        this.session = session;
    }

    public void doSomething() {
        UserSessionModel userSession = session.getUserSession();
        // Do something with the user session...
    }

    // Implement the other methods defined in the UserStorageProvider interface...
}
```
#### Scopes
- ProviderFactory: Application
- KeycloackSession: Request scoped (in addition to other async tasks running in the background)
- Provider: created on demand, lives for the whole Session

How keycloak defines priorities:
- it looks at the order() method()
- otherewise loads them based on Classpath order (normally internal ones will always come first)
In this example, MyProvider is a custom Provider class that implements the UserStorageProvider interface. It uses a KeycloakSession to access the user's session.

#### Authentication
Configuring authentication:
```
https://www.keycloak.org/docs/latest/server_admin/index.html#configuring-authentication_server_administration_guide
```
#### Configuring providers
```
https://www.keycloak.org/server/configuration-provider
```

```
    command: ["start-dev", "--spi-email-sender-default-my-config=myValue",
              "--spi-email-sender-provider=void-email-sender",
              "--spi-password-policy-dollar-password-enabled=true"]
```
#### Add custom REST endpoints
Add custom REST endpoints:
```
https://www.keycloak.org/docs/latest/server_development/#_extensions
```
test custom provider:
```
curl -v "localhost:8080/realms/master/email-exists?email=admin@admin.com"
```
get token:
```
http://localhost:8080/realms/master/protocol/openid-connect/auth?client_id=account-console&response_type=token&redirect_uri=https://httpbin.org/
```
take token from the output:
```
https://httpbin.org/#/session_state=8aed1318-2e37-4561-bd52-176f2ff3d42a&access_token=<my_token>&token_type=Bearer&expires_in=900
export TOKEN=<my_token>
```
call the resource:
```
curl -H "Authorization: Bearer $TOKEN" "localhost:8080/realms/master/email-exists?email=admin@admin.com"
```
#### Required Actions:
Required actions for all users:
```
https://www.keycloak.org/docs/latest/server_admin/#proc-setting-default-required-actions_server_administration_guide
```
#### Theme resources
```
https://www.keycloak.org/docs/latest/server_development/#_theme_resource
```
### Configuring Authentication
Authentication flows:
```
https://www.keycloak.org/docs/latest/server_admin/#_authentication-flows
```
Themes:
```
https://www.keycloak.org/docs/latest/server_development/#deploying-themes
```
#### Add custom JPA entities to the Keycloak data model
```
https://www.keycloak.org/docs/latest/server_development/#_extensions_jpa
```
#### User Storage SPI
```
https://www.keycloak.org/docs/latest/server_development/#_user-storage-spi
```


















