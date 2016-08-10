#  Odoo JSON-RPC

This library allows you to interact with Odoo Modules ORMs by retrieving all json strings as Java Object

## Getting Started

```
#!java

OeExecutor executor = OeExecutor.getInstance(SCHEME, HOST, PORT, DATABASE, USERNAME, PASSWORD);
OeUserService service = new OeUserService(executor);
OeUser oeUser = service.findById(id);
```

### Prerequisities

JDK6

## Running the tests

1. Change skipTests value to false

            <skipTests>false</skipTests>

2. Configure test to connect to your Odoo server in /src/test/java/com/odoo/rpc/AbstractBaseTest.java

3. 
            $ mvn clean install

## Deployment

include this library jar to you android, desktop, web application

## Built With

* Maven

## Authors

* [Ahmed El-mawaziny](https://bitbucket.org/amawaziny/) in [QFast](https://bitbucket.org/qfast/) team

## License

This project is licensed under Apache License - see the [LICENSE](LICENSE.md) file for details
