# Last Farewells backend-service API


## Steps to Setup

**1. Clone the application**

```
$  git clone  https://github.com
```

**2. Build project using Maven**

```
$ mvn clean install -DskipTests
```

**3. Required Atrifacts**

```
All the dependent systems are present in docker-compose.yml
docker compose-up
```
**4. Check the running systems**

```
All the dependent systems are present in docker-compose.yml
docker compose-up

Postgres Admin running on : http://localhost:8888
Postgres DB running on : localhost:5432
```
**5. Keycloak setup**

```
• Create a new Realm : hawks  in application.yml {keycloak.realm}
• Create a new Client in this realm : hawks-admin in application.yml {keycloak.resource}
• For hawks-admin client -> Enable 'Client authentication' and enable 'Direct access grants' and 
  'Service account roles'
• Copy client secret from Credentials tab : in application.yml {keycloak.credentials.secret}
• Assign role 'real-management' in 'Service accounts roles' tab
• We send custom mails from our service using SMTP. But Keycloak can also be configured to send 
email verification link, configure SMTP for Email from 'Realm Settings', 
• Update token expiry time from Realm settings/ Tokens   
```

**5. Run DB Scripts**

```
Project is configured to ru with Flyway
OR
Run below scripts in postgres admin console
/db.migrations/V1__init.sql

```

The app will start running at <http://localhost:9061/core>

## Api Documentation (Swagger)

<http://localhost:9061/core/swagger-ui.html>