# Backlog

 session cookies—cookies that do not explicitly set an expiration date with Max-Age or Expires—as these are instead cleared when the browsing session ends.

 __Secure- prefix: Cookies with names starting with __Secure- (dash is part of the prefix) must be set with the secure flag from a secure page (HTTPS).

__Host- prefix: Cookies with names starting with __Host- must be set with the secure flag, must be from a secure page (HTTPS), must not have a domain specified (and therefore, are not sent to subdomains), and the path must be /


```scala
 expires = None,
maxAge = None,
```
the csrf cookie is a session cookie as both the expires and maxAge are None. firefox indicates it as Session

`git fetch origin` fetches all remote branches