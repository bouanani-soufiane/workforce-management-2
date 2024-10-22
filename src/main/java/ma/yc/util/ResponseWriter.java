package ma.yc.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.inject.Inject;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ResponseWriter {

    @Inject
    private ObjectMapper objectMapper;

    public void writeResponse(HttpServletResponse resp, int statusCode, String message) {
        try (PrintWriter out = resp.getWriter()) {
            resp.setStatus(statusCode);
            out.write(objectMapper.writeValueAsString(new ResponseMessage(message)));
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ResponseMessage {
        public String message;

        public ResponseMessage(String message) {
            this.message = message;
        }
    }
}