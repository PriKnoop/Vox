package br.com.ws.configs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;

import javax.ws.rs.core.MediaType;

import org.json.JSONException;
import org.json.JSONObject;

public class JSON {
	
	/*O padrão é MediaType.APPLICATION_JSON
	 *Criei esse com UTF-8 para não ter problemas com acentos e tal*/
	public static final String UTF8JSON = MediaType.APPLICATION_JSON + ";charset=utf-8";

	private static String readAll(Reader reader) throws IOException {
		StringBuilder text = new StringBuilder();
		int count;
		while ((count = reader.read()) != -1) {
			text.append((char) count);
		}
		return text.toString();
	}

	private static JSONObject readJsonFromUrl(String URL) throws IOException, JSONException {
		InputStream inputStream = new URL(URL).openStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
			String text = readAll(reader);
			JSONObject jsonObject = new JSONObject(text);
			return jsonObject;
		} finally {
			inputStream.close();
		}
	}

	/*Único método importante da classe e único que vai ser acessado de fora
	 *Tu passa a URL onde teu JSON vai estar, ele lê essa URL e retorna o JSON*/
	public static JSONObject get(String URL) {
		try {
			return readJsonFromUrl(URL);
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}