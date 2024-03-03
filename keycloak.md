# Keycloack

#### Download page
https://www.keycloak.org/downloads

Server Administration Guide:
```
https://www.keycloak.org/docs/latest/server_admin/index.html
```
#### OpenId connect endpoints
```
https://www.okta.com/
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

#### Key Terms
**Realm** - is a logical domain which groups together users, roles, clients, and other configurations.

**Client** is an entity that can request Keycloak to authenticate a user. A client can represent applications, services, or devices that have registered with Keycloak and are capable of interacting with the Keycloak server.

**Client Scopes** are a way to limit the claims and roles that are included in access tokens. They allow you to define a set of default client-level roles, protocol mappers, and other configurations that can be shared between multiple clients. This helps in reducing duplication and provides a centralized place to manage common settings.

In Keycloak, an access token is a JSON Web Token (JWT) provided to a client application after a successful authentication of a user. This token contains information (or claims) about the user and its roles. The client application can use this token to access the resources (like APIs) on behalf of the authenticated user.

Endpoints in Keycloak refer to the URLs that Keycloak exposes to offer various functionalities. For example, the authentication endpoint is used to authenticate users, the token endpoint is used to issue tokens, and the logout endpoint is used to log out users. These endpoints are part of Keycloak's implementation of the OAuth2 and OpenID Connect protocols.

##### Access Token types:
- Opaque Token
- Structured Token (JWT)

