/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hpt150030.controllers;

import com.hpt150030.utilities.Constants;
import com.hpt150030.utilities.DatabaseConnection;
import com.hpt150030.utilities.STATUS_TYPE;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
public class NewBookController extends HttpServlet {

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
            if (request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR) == null && request.getParameter(Constants.NEW_BOOK_REQ_ID) == null && request.getParameter(Constants.NEW_BOOK_REQ_TITLE) == null ) 
            {
            //first time
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, false);
            rd.forward(request, response);
        }else if (request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR).equalsIgnoreCase("") && request.getParameter(Constants.NEW_BOOK_REQ_ID).equalsIgnoreCase("") && request.getParameter(Constants.NEW_BOOK_REQ_TITLE).equalsIgnoreCase("")) 
        { //nothing entered
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, true);
            request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.WARNING);
            request.setAttribute(Constants.STATUS_HEADER, "Empty fields");
            request.setAttribute(Constants.STATUS_BODY, "Please enter something");
            rd.forward(request, response);
        }
        else if (request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR).equalsIgnoreCase("") || request.getParameter(Constants.NEW_BOOK_REQ_ID).equalsIgnoreCase("") || request.getParameter(Constants.NEW_BOOK_REQ_TITLE).equalsIgnoreCase("") ) {
            //nothing entered
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, true);
            request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.WARNING);
            request.setAttribute(Constants.STATUS_HEADER, "Some fields Empty");
            request.setAttribute(Constants.STATUS_BODY, "All fields required");
            rd.forward(request, response);
        }
            String bid = request.getParameter(Constants.NEW_BOOK_REQ_ID);
            String btitle = request.getParameter(Constants.NEW_BOOK_REQ_TITLE);
            String bauthor = request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR);
            
            StringBuffer sqlString = new StringBuffer();
            sqlString.append("Select COUNT(*) from book where book_id = ? and title = ?");
            DatabaseConnection dbConnection = new DatabaseConnection();
            try {
                dbConnection.openConnection();
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.preparedStatement.setString(2, btitle);
                
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                dbConnection.resultSet.next();
                if (dbConnection.resultSet.getInt(1) > 0) {
                    //borrower record already exists
                    RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                    request.setAttribute(Constants.HAS_STATUS, true);
                    request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.ERROR);
                    request.setAttribute(Constants.STATUS_HEADER, "Error");
                    request.setAttribute(Constants.STATUS_BODY, "Book already exists. Please check the details again.");
                    rd.forward(request, response);
                    return;
                }
                
                sqlString = new StringBuffer();
                sqlString.append("insert into book (book_id,title) values (?,?);");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.preparedStatement.setString(2, btitle);
                
                dbConnection.preparedStatement.executeUpdate();
                
                sqlString = new StringBuffer();
                sqlString.append("insert into book_authors (book_id,author_name) values (?,?);");
                
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.preparedStatement.setString(2, bauthor);
                dbConnection.preparedStatement.executeUpdate();
                dbConnection.closeConnection();
                
                RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                request.setAttribute(Constants.HAS_STATUS, true);
                request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.SUCCESS);
                request.setAttribute(Constants.STATUS_HEADER, "Success");
                request.setAttribute(Constants.STATUS_BODY, "New book has been successfully added to the database.");
                rd.forward(request, response);
            } catch (SQLException ex) {
                Logger.getLogger(NewBookController.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(NewBookController.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
              dbConnection.closeConnection();
            }
            
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
        processRequest(request, response);
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
        processRequest(request, response);
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
