package com.sportsverse.util;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Utility class to handle setting request messages and forwarding to JSP pages.
 */
public class RedirectionUtil {
    private static final String BASE_PATH = "/WEB-INF/pages/";

    public static final String AUTH_PAGE = BASE_PATH + "authentication.jsp";
    public static final String HOME_PAGE = BASE_PATH + "home.jsp";
    public static final String PRODUCT_PAGE = BASE_PATH + "products.jsp";
    
    public static final String ADMIN_DASHBOARD = BASE_PATH + "dashboard.jsp";

    /**
     * Sets a message attribute in the request.
     *
     * @param req     the request object
     * @param msgType type of the message (e.g., "error", "success")
     * @param msg     the message content
     */
    public void setMsgAttribute(HttpServletRequest req, String msgType, String msg) {
        req.setAttribute(msgType, msg);
    }

    /**
     * Forwards to a specified JSP page.
     *
     * @param req  the request object
     * @param resp the response object
     * @param page the full path to the JSP page
     */
    public void redirectToPage(HttpServletRequest req, HttpServletResponse resp, String page)
            throws ServletException, IOException {
        req.getRequestDispatcher(page).forward(req, resp);
    }

    /**
     * Sets a message and forwards to a page.
     *
     * @param req     the request object
     * @param resp    the response object
     * @param msgType message type (e.g., "error")
     * @param msg     message content
     * @param page    full path to the JSP
     */
    public void setMsgAndRedirect(HttpServletRequest req, HttpServletResponse resp, String msgType, String msg,
                                   String page) throws ServletException, IOException {
        setMsgAttribute(req, msgType, msg);
        redirectToPage(req, resp, page);
    }
}
