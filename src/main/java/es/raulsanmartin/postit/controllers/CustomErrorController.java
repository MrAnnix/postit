package es.raulsanmartin.postit.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        String errorCode = status.toString();
        String errorDescription = "We are having troubles :(";

        if (status != null) {
            int statusCode = Integer.parseInt(errorCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                errorDescription = "We couldn't find anything here :(";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                errorDescription = "We are having troubles :(";
            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
                errorDescription = "Unauthorized, you shall not pass";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                errorDescription = "Forbidden, you shall not pass";
            }
        }

        model.addAttribute("errorCode", errorCode);
        model.addAttribute("errorDescription", errorDescription);

        return "error";
    }
}