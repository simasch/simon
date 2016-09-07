package ch.simas.monitoring;

import ch.simas.monitoring.data.Hosts;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import spark.Request;
import spark.Response;
import static spark.Spark.*;

public class Monitor {

    public static void main(String[] args) throws JAXBException {
        if (args.length != 1) {
            throw new IllegalArgumentException("XML configuration missing");
        }
        Hosts hosts = loadConfiguration(args[0]);

        staticFiles.location("/public");

        get("/check", (Request req, Response res) -> {
            for (Hosts.Group group : hosts.getGroup()) {
                for (Hosts.Group.Host host : group.getHost()) {
                    long start = System.currentTimeMillis();
                    URL obj = new URL(host.getUrl());
                    HttpURLConnection con = (HttpURLConnection) obj.openConnection();
                    int responseCode = con.getResponseCode();
                    host.setStatus("" + responseCode);
                    host.setTime("" + (System.currentTimeMillis() - start));
                }
            }
            if (req.contentType() != null && req.contentType().equals("application/xml")) {
                res.header("Content-Type", "application/xml");
                return marshalToXml(hosts);
            } else {
                res.header("Content-Type", "application/json");
                return marshalToJson(hosts);
            }
        });
    }

    private static Hosts loadConfiguration(String configFile) {
        try {
            File file = new File(configFile);
            JAXBContext jaxbContext = JAXBContext.newInstance(Hosts.class);

            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            Hosts hosts = (Hosts) unmarshaller.unmarshal(file);
            return hosts;
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static String marshalToXml(Hosts hosts) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Hosts.class);
            Marshaller marshaller = jaxbContext.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");

            StringWriter stringWriter = new StringWriter();
            marshaller.marshal(hosts, stringWriter);

            return stringWriter.toString();
        } catch (JAXBException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String marshalToJson(Hosts hosts) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            StringWriter sw = new StringWriter();
            mapper.writeValue(sw, hosts);
            return sw.toString();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
