/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import EnterpriseJavaBeans.BidFacade;
import EnterpriseJavaBeans.LoginBean;
import EnterpriseJavaBeans.ProductFacade;
import EnterpriseJavaBeans.UserFacade;
import Entities.AuctionUser;
import Entities.Bid;
import Entities.Product;
import ManagedBeans.ProductView;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.SessionBean;
import javax.faces.bean.ManagedProperty;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Åsmund
 */
@WebServlet(name = "Controller",
        urlPatterns = {"/Controller",
            "/index",
            "/register",
            "/registerProduct",
            "/search",
            "/makeBid",
            "/login",
            "/logout",
            "/updateD",
            "/updatePic"})
public class Controller extends HttpServlet {

    @EJB
    private UserFacade userFacade;

    @EJB
    private ProductFacade productFacade;

    @EJB
    private LoginBean loginBean;

    @EJB
    private BidFacade bidFacade;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controller</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controller at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();

        if (userPath.equals("/search")) {
            System.out.println("------------------------------------------------");
            String productID = request.getParameter("productID");

            session.setAttribute("selectedProduct", productFacade.find(Long.parseLong(productID)));

            //productView.setProduct(productFacade.find(Long.parseLong(productID)));
            response.sendRedirect("/AuctionWeb/faces/product.xhtml");

            /* List<Product> searchResult;
            
            String itemName = request.getParameter("searchVal");
        
            if( productFacade.searchForProduct(itemName).isEmpty()){
                response.sendRedirect("/AuctionWeb");
            } else {
                searchResult = productFacade.searchForProduct(itemName);
                session.setAttribute("searchList", searchResult);
                response.sendRedirect("listProducts");
            }*/
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //processRequest(request, response);
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();

        //Here we register users
        if (userPath.equals("/register")) {
            String name = request.getParameter("username");
            String password = request.getParameter("userpass");
   
            boolean registerSuccess = userFacade.register(name, password);
            if(!registerSuccess){
                String error = "Username is already taken";
                session.setAttribute("nameTakenError", error);
                response.sendRedirect("/AuctionWeb/faces/register.xhtml");
            }
            else{
                            response.sendRedirect("/AuctionWeb/faces/userPage.xhtml");
            }
            //AuctionUser u = userFacade.createUser(name, password);

            //session.setAttribute("user", u);


        }//end register

        if (userPath.equals("/registerProduct")) {

            //only logged on users can create products
            if (session.getAttribute("user") == null) {
                String error = "You need to be logged in to register items";
                session.setAttribute("isNotLoggedInError", error);
                response.sendRedirect("/AuctionWeb/faces/registerproduct.xhtml");
            }

            String name = request.getParameter("productName");
            String startingPrice = request.getParameter("startingPrice");
            String shipsTo = request.getParameter("shipsTo");
            String description = request.getParameter("description");
            String date = request.getParameter("expirationDate");
            String isPublished = request.getParameter("isPublished");

            Product p
                    = productFacade.createProduct(name, startingPrice, shipsTo,
                            description, date, isPublished,
                            (AuctionUser) session.getAttribute("user"));

            response.sendRedirect("/AuctionWeb");
        }//end registerProduct

        //System.out.println(userPath);
        if (userPath.equals("/makeBid")) {

            //only logged on users can make Bids
            if (session.getAttribute("user") == null) {
                response.sendError(401);
                return;
            }

            double amount = Double.parseDouble(request.getParameter("amount"));

            Product product = (Product)session.getAttribute("selectedProduct");
            
            
            if(product.getStartingPrice() < amount){
            
                
                Bid b =
                bidFacade.createBid(amount, product, (AuctionUser) session.getAttribute("user"));
                
                if(b != null){
                    productFacade.merge(product);
                    userFacade.merge((AuctionUser) session.getAttribute("user"));
                }

            }

            response.sendRedirect("/AuctionWeb/faces/product.xhtml");
        }//end makeBid

        if (userPath.equals("/login")) {
            String name = request.getParameter("username");
            String password = request.getParameter("userpass");

            AuctionUser u = loginBean.login(name, password);
            if (u == null) {
                //TODO send "invalid login" to somewhere on client?
                //boolean loginSuccess = false;
                String loginFailedMessage = "Invalid credentials";
                session.setAttribute("loginStatusMessage", loginFailedMessage);
                try {
                    response.sendRedirect("/AuctionWeb");
                } catch (Exception e) {

                }

            }
            else { //login success
                String welcome = "Hello " + u.getName();
                session.setAttribute("user", u);
                session.setAttribute("welcomeMessage", welcome);
                session.removeAttribute("isNotLoggedInError");
                session.removeAttribute("loginStatusMessage");


                try {
                    response.sendRedirect("/AuctionWeb");
                } catch (Exception e) {

                }
            }
        }

        if (userPath.equals("/logout")) {
            System.out.println("----------------------------in logout--------------------");
            if (session.getAttribute("user") != null) {
                //session.invalidate();
                session.removeAttribute("user");
                response.sendRedirect("/AuctionWeb");
                //response.sendRedirect("/AuctionWeb/faces/registerproduct.xhtml");
            }
        }

        if (userPath.equals("/updateD")) {

            //only logged on users can make Bids
            if (session.getAttribute("user") == null) {
                response.sendError(401);
                return;
            }

            String desc = request.getParameter("desc");
            AuctionUser a = (AuctionUser) session.getAttribute("user");
            a.setDescription(desc);
            userFacade.merge(a);
        }
        response.sendRedirect("/AuctionWeb/faces/userPage.xhtml");

        if (userPath.equals("/updatePic")) {

            //only logged on users can make Bids
            if (session.getAttribute("user") == null) {
                response.sendError(401);
                return;
            }

            String pic = request.getParameter("picture");
            AuctionUser a = (AuctionUser) session.getAttribute("user");
            a.setPic(pic);
            userFacade.merge(a);
        }
        response.sendRedirect("/AuctionWeb/faces/userPage.xhtml");
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
