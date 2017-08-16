package main.examples;

import com.marklogic.client.DatabaseClientFactory.Authentication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

	private static Properties props = loadProperties();

	protected static String host = props.getProperty("mlHost");

	protected static int port = Integer.parseInt(props.getProperty("mlRestPort"));

	protected static String user = props.getProperty("mlAdminUsername");

	protected static String password = props.getProperty("mlAdminPassword");

	protected static String admin_user = props.getProperty("mlAdminUsername");

	protected static String admin_password = props.getProperty("mlAdminPassword");

	protected static Authentication authType = Authentication.valueOf(
			props.getProperty("mlAuth").toUpperCase()
	);

	// get the configuration for the example
	private static Properties loadProperties() {
	    try {
			String propsName = "gradle.properties";
				FileInputStream propsStream = new FileInputStream(propsName);
			if (propsStream == null)
				throw new IOException("Could not read config properties");

			Properties props = new Properties();
			props.load(propsStream);

			return props;

	    } catch (final IOException exc) {
	        throw new Error(exc);
	    }
	}
}
