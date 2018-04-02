package com.robindrew.common.http;

import static com.robindrew.common.text.Strings.bytes;
import static com.robindrew.common.text.Strings.number;

import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.util.Java;

public class Http {

	private static final Logger log = LoggerFactory.getLogger(Http.class);

	public static String getText(String url, Charset defaultCharset) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			// Create the request
			HttpGet request = new HttpGet(url);

			// Execute the request
			Stopwatch timer = Stopwatch.createStarted();
			CloseableHttpResponse response = client.execute(request);

			// Check the response
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				timer.stop();
				throw new HttpResponseException(statusCode, "GET '" + url + "' -> " + response.getStatusLine());
			}

			// Get text from the response
			HttpEntity entity = response.getEntity();
			String text = EntityUtils.toString(entity, defaultCharset);
			timer.stop();
			log.info("[GET] {} chars in {} ({})", number(text.length()), timer, url);

			return text;
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	public static byte[] getBytes(String url) {
		try (CloseableHttpClient client = HttpClients.createDefault()) {

			// Create the request
			HttpGet request = new HttpGet(url);

			// Execute the request
			Stopwatch timer = Stopwatch.createStarted();
			CloseableHttpResponse response = client.execute(request);

			// Check the response
			StatusLine statusLine = response.getStatusLine();
			int statusCode = statusLine.getStatusCode();
			if (statusCode != 200) {
				timer.stop();
				throw new HttpResponseException(statusCode, "GET '" + url + "' -> " + response.getStatusLine());
			}

			// Get bytes from the response
			HttpEntity entity = response.getEntity();
			byte[] bytes = EntityUtils.toByteArray(entity);
			timer.stop();
			log.info("[GET] {} in {} ({})", bytes(bytes), timer, url);

			return bytes;
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}
