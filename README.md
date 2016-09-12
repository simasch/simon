# simon - SImple MONItoring

simon is a simple monitoring tool.
All it does is sending HTTP GET request to definied URLs and logging the status code and request time.

## Configure

The configuration is done in a XML file. i.e.

``` xml
<hosts name="My Monitor">
    <group name="Production">
        <host name="simas GmbH" url="https://www.simas.ch" />
        <host name="Google" url="https://www.google.com" />
    </group>
</hosts>
```
## Download

[http://repo1.maven.org/maven2/ch/simas/simon/1.0.0/simon-1.0.0.jar](http://repo1.maven.org/maven2/ch/simas/simon/1.0.0/simon-1.0.0.jar)

## Run

simon uses Spring Boot. To configure the runtime simple create a application.properties.
The application.properties file must be in the same folder as the jar file.
```
server.port=7272
simon.config.hosts=myhosts.xml
```

Then start it with

```
java -jar simon-2.0.0.jar
```

## See

The result is either JSON or XML depending on the content type when on [http://localhost:7272/check](http://localhost:7272/check) or a simple web page on [http://localhost:4567/check](http://localhost:4567/check)

# License

simon is open source and free software under Apache License, Version 2:

[http://www.apache.org/licenses/LICENSE-2.0.html](http://www.apache.org/licenses/LICENSE-2.0.html)
