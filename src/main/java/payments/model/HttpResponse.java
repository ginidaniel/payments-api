package payments.model;

import org.springframework.http.HttpStatus;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "HttpResponse")
public class HttpResponse {

    private int httpStatus;
    private Object data;

    public HttpResponse(HttpStatus httpStatus, Object data) {
        this.httpStatus = httpStatus.value();
        this.data = data;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public Object getData() {
        return data;
    }
}
