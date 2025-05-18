# Spring Security Fundamentals 
- by: Laur Spilca

- Spring Security is about Application Level security
  - Application level security is about
    - Authentication
    - Authorisation

- Authentication
  - who's the user?
- Authorisation
  - is the user allowed to do something?

- Authority
  - something you have
  - I have the authority to perform certain actions
- Role
  - something you are
  - sort of a badge
- Authority and Roles can be somewhat similar..
  - they have the same contract behind the scenes - GrantedAuthority

- Ways – Authentication
    - HTTP Basic Authentication – Simple apps, internal tools
    - X.509 Certificate Authentication – High-security enterprise systems
    - JWT (with OAuth2 Resource Server or Custom) – REST APIs, SPA backends
        - Using Spring Security OAuth2 Resource Server
        - Custom JWT Authentication Filter

- Ways – Authorization
    - In Web Apps
        - Filter-Based Authorization – Role-based page access
        - Annotation-Based Authorization – e.g., @PreAuthorize, @Secured, @RolesAllowed
  - In Non-Web Apps
      - Aspect-Based Authorization (AOP) – Used in service layers, background jobs (can also be used in web apps)
- Note
```text
- Just by adding Spring Security to the pom, we get

=================================================================================
Using generated security password: 3072d7ba-6c2b-4c14-9da5-6c0aa0ceab49

This generated password is for development use only. Your security configuration must be updated before running your application in production.
==================================================================================
```
