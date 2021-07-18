# Multi Authenticator Extension

Multi authenticator to secure  APIs for that clients without API Manager entitlement.

<img src="https://github.com/Rocket-Buddha/mule-multi-authenticator-connector/blob/master/doc/img/exchange_pic.png" alt="Exchange Icon" width="300" >

This module is intended to be used as an authentication mechanism when an account/client doesn't have the "API Manager and Analytics" add-on, ergo, doesn't have capabilities to secure APIs (e.g ability to apply policies like OAuth2.0, client-id enforcement, etc) and you do not want/can add an extra hop using a centralized authentication API.

[![CI Status](https://github.com/Rocket-Buddha/mule-multi-authenticator-connector/workflows/Java%20CI%20with%20Maven/badge.svg)]()

## Features
- HTTP Basic authentication, supported persisted credentials using:
    - SHA1
    - SHA256
    - SHA512
- API Key authentication, supported persisted credentials using:
    - SHA1
    - SHA256
    - SHA512
- JSON Web Tokens generation and verification with hash-based message authentication code:
    - SHA256
    - SHA384
    - SHA512
- (Release 1.1) JSON Web Tokens generation and verification with asymmetric sign and verification using RSA:
    - RS256
    - RS384
    - RS512

## Setup

### 1. Clone

#### HTTPS Cloning 
Take note that if you want to clone the repo you have to have a GitHub personal access token. Follow [this instructions](https://docs.github.com/en/github/authenticating-to-github/creating-a-personal-access-token) first.

```bash
git clone https://github.com/Rocket-Buddha/mule-multi-authenticator-connector.git

```

#### or SSH Cloning 

```bash
git clone git@github.com:Rocket-Buddha/mule-multi-authenticator-connector.git

```

#### or Download
Go to "Code" button and select the "Download ZIP" option in the menu, then extract the zip content.

### 2. Build and test
Find the property organization.id in the pom.xml file and replace using your [organization ID](https://docs.mulesoft.com/access-management/organization#manage-master-organization-settings).

```xml
<organization.id>{ORGANIZATION_ID}</organization.id>  
```

Then you can build  and test.

```bash
cd multi-authenticator

mvn clean install
```

### 3. Use
Then add this dependency to your application pom.xml and enjoy. Remember replace using your organization ID.

```xml
<dependency>
    <groupId>{ORGANIZATION_ID}</groupId>
    <artifactId>multi-authenticator</artifactId>
    <version>1.0.0</version>
    <classifier>mule-plugin</classifier>
</dependency>
```

### 4. Deploy the connector to your organization Exchange

Add Anypoint platform Exchange credentials in your maven settings.xml file.

```xml
<servers>
    <server>
        <id>anypoint-exchange</id>
        <username>{YOUR_ANYPOINT_USERNAME}</username>
        <password>{YOUR_ANYPOINT_PASSWORD}</password>
    </server>
</servers>
```
Remember that you should not have credentials in clear in your settings.xml file (will work anyway), so check [this](http://maven.apache.org/guides/mini/guide-encryption.html).

Now you can deploy.

```bash
mvn deploy
```

## Implementation
See [the wiki](https://github.com/Rocket-Buddha/mule-multi-authenticator-connector/wiki) for more implementation details.

## Warnings

- Take note that this is a custom connector, so you will not get Mulesoft support for related issues.

