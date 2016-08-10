#  Odoo JSON-RPC

This library allows you to interact with Odoo Modules ORMs by retrieving all json strings as Java Object

## Getting Started

* Download jar file

1. [odoo-jsonrpc](https://bitbucket.org/qfast/odoo-jsonrpc/downloads/odoo-jsonrpc-1.0.jar)
2. [odoo-jsonrpc-javadoc](https://bitbucket.org/qfast/odoo-jsonrpc/downloads/odoo-jsonrpc-1.0-javadoc.jar)
3. [odoo-jsonrpc-sources](https://bitbucket.org/qfast/odoo-jsonrpc/downloads/odoo-jsonrpc-1.0-sources.jar)


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

2. Configure test to connect to your Odoo server in [/src/test/java/com/odoo/rpc/AbstractBaseTest.java](/src/test/java/com/odoo/rpc/AbstractBaseTest.java)

3. 
            $ mvn clean install

## Deployment

* Include this library jar to you android, desktop or web application

* Add to your local repository for use in your builds

        $ mvn install:install-file -Dfile=<path-to-odoo-jsonrpc-1.0.jar>
        
        $ mvn install:install-file -Dfile=<path-to-odoo-jsonrpc-1.0-javadoc.jar> -DgroupId=org.qfast.odoo-rpc \
        -DartifactId=odoo-jsonrpc -Dversion=1.0 -Dpackaging=jar
        
    
        $ mvn install:install-file -Dfile=<path-to-odoo-jsonrpc-1.0-sources.jar> -DgroupId=org.qfast.odoo-rpc \
        -DartifactId=odoo-jsonrpc -Dversion=1.0 -Dpackaging=jar

## Built With

* Maven

## Authors

* [Ahmed El-mawaziny](https://bitbucket.org/amawaziny/) in [QFast](https://bitbucket.org/qfast/) team

## License

This project is licensed under Apache License - see the [LICENSE](LICENSE.md) file for details
