# simon - SImple MONItoring

simon is a simple monitoring tool.
All that it does is sending HTTP GET request to definied URLs and sending logging the status code and request time.

The configuration is done in a XML file. i.e.

``` xml
<hosts name="My Monitor">
    <group name="Production">
        <host name="simas GmbH" url="https://www.simas.ch" />
        <host name="Google" url="https://www.google.com" />
    </group>
</hosts>
```

The result is either JSON or XML when accessing http://localhost:4567/check or a simple web page on http://localhost:4567/check

To start it use

```
java -jar simon-0.0.1-SNAPSHOT.jar myhosts.xml
```
