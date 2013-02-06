package com.example.fsexplorer.template;

import com.example.fsexplorer.api.NodeList;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Andrey Tarashevsky
 *         Date: 06.02.13
 */
public class TemplateFactory {

    private static final Logger log = LoggerFactory.getLogger(TemplateFactory.class);

    private static final String defaultTemplate = "templates/nodes.mustache";

    public static String getDefaultTemplate() {
        try {
            URL templatePath = TemplateFactory.class.getClassLoader().getResource(defaultTemplate);
            if (templatePath != null) {
                return new String(Files.readAllBytes(Paths.get(templatePath.toURI())), Charset.defaultCharset());
            } else {
                throw new FileNotFoundException("Can't find default template file " + defaultTemplate);
            }
        } catch (Exception e) {
            log.error("Error on loading default template", e);
            return "";
        }
    }

    public static String prepareNodeListTemplate(NodeList nodes) throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(defaultTemplate);
        StringWriter writer = new StringWriter();
        mustache.execute(writer, nodes);
        return writer.toString();
    }

}
