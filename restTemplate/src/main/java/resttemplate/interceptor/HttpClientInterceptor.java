package resttemplate.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

@Slf4j
public class HttpClientInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        // Request 수정
        request.getHeaders().add("Custom-Header", "Custom-Value");

        // Request 정보 로깅
        log.info("Request URI : {}", request.getURI());
        log.info("Request Method : {}", request.getMethod());
        log.info("Request Headers : {}", request.getHeaders());
        log.info("Request Body : {}", new String(body, "UTF-8"));

        // 다음 인터셉터 또는 요청 실행
        ClientHttpResponse response = execution.execute(request, body);

        // Response 정보 로깅
        log.info("Response Status : {}", response.getStatusCode());
        log.info("Response Headers : {}", response.getHeaders());

        return response;
    }
}