package hcs.hellospring.api;

import hcs.hellospring.api.ApiExecutor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpClientApiExecutor implements ApiExecutor {
	@Override
	public String execute(URI uri) throws IOException {
		HttpRequest build = HttpRequest.newBuilder()
				.uri(uri)
				.GET().build();
		try {
			return HttpClient.newBuilder()
					.build()
					.send(build, HttpResponse.BodyHandlers.ofString()).body();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
