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
 * @author Bhushan
 */
public class AddCopiesController extends HttpServlet {

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
            if (request.getParameter(Constants.NEW_CBRANCH_REQ_ID) == null && request.getParameter(Constants.NEW_CBOOK_REQ_ID) == null && request.getParameter(Constants.NEW_BRANCH_REQ_COPIES) == null ) 
            {
            //first time
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, false);
            rd.forward(request, response);
        }else if (request.getParameter(Constants.NEW_CBRANCH_REQ_ID).equalsIgnoreCase("") && request.getParameter(Constants.NEW_CBOOK_REQ_ID).equalsIgnoreCase("") && request.getParameter(Constants.NEW_BRANCH_REQ_COPIES).equalsIgnoreCase("")) 
        { //nothing entered
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, true);
            request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.WARNING);
            request.setAttribute(Constants.STATUS_HEADER, "Empty fields");
            request.setAttribute(Constants.STATUS_BODY, "Please enter something");
            rd.forward(request, response);
        }
        else if (request.getParameter(Constants.NEW_CBRANCH_REQ_ID).equalsIgnoreCase("") || request.getParameter(Constants.NEW_CBOOK_REQ_ID).equalsIgnoreCase("") || request.getParameter(Constants.NEW_BRANCH_REQ_COPIES).equalsIgnoreCase("")) 
        { //nothing entered
            RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
            request.setAttribute(Constants.HAS_STATUS, true);
            request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.WARNING);
            request.setAttribute(Constants.STATUS_HEADER, "Empty fields");
            request.setAttribute(Constants.STATUS_BODY, "Please enter something");
            rd.forward(request, response);
        }
            String bid = request.getParameter(Constants.NEW_CBOOK_REQ_ID);
            String brid = request.getParameter(Constants.NEW_CBRANCH_REQ_ID);
            String bcopies = request.getParameter(Constants.NEW_BRANCH_REQ_COPIES);
            
            StringBuffer sqlString = new StringBuffer();
            sqlString.append("Select COUNT(*) from book_copies where book_id = ? and branch_id = ?");
            DatabaseConnection dbConnection = new DatabaseConnection();
            try {
                dbConnection.openConnection();
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.preparedStatement.setString(2, brid);
                
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                dbConnection.resultSet.next();
                if (dbConnection.resultSet.getInt(1) > 0) {
                    //borrower record already exists
                    RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                    request.setAttribute(Constants.HAS_STATUS, true);
                    request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.ERROR);
                    request.setAttribute(Constants.STATUS_HEADER, "Error");
                    request.setAttribute(Constants.STATUS_BODY, "Copies already exists. Please check the details again.");
                    rd.forward(request, response);
                    return;
                }
                
                sqlString = new StringBuffer();
                sqlString.append("select count(*) from book where book_id= ?");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                
                if (dbConnection.resultSet.getInt(1) > 0) {
                dbConnection.closeConnection();
                dbConnection.openConnection();
                sqlString = new StringBuffer();
                sqlString.append("select count(*) from library_branch where branch_id= ?");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, brid);
                dbConnection.resultSet = dbConnection.preparedStatement.executeQuery();
                
             if (dbConnection.resultSet.getInt(1) > 0) {
                 dbConnection.closeConnection();
                 dbConnection.openConnection();
                 sqlString = new StringBuffer();
                 sqlString.append("insert into book_copies (book_id,branch_id,no_of_copies) values (?,?,?);");
                dbConnection.preparedStatement = dbConnection.connect.prepareStatement(sqlString.toString());
                dbConnection.preparedStatement.setString(1, bid);
                dbConnection.preparedStatement.setString(2, brid);
                dbConnection.preparedStatement.setString(3, bcopies);
                
                dbConnection.preparedStatement.executeUpdate();
                dbConnection.closeConnection();
                    }
             else {
                 RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                    request.setAttribute(Constants.HAS_STATUS, true);
                    request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.ERROR);
                    request.setAttribute(Constants.STATUS_HEADER, "Error");
                    request.setAttribute(Constants.STATUS_BODY, "Branch doesn't exists.");
                    rd.forward(request, response);
                    return;
             }
                    
                }
                else {
                 RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                    request.setAttribute(Constants.HAS_STATUS, true);
                    request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.ERROR);
                    request.setAttribute(Constants.STATUS_HEADER, "Error");
                    request.setAttribute(Constants.STATUS_BODY, "Book doesn't exists.");
                    rd.forward(request, response);
                    return;
             }
               
                
                RequestDispatcher rd = request.getRequestDispatcher("BookManagement.jsp");
                request.setAttribute(Constants.HAS_STATUS, true);
                request.setAttribute(Constants.STATUS_TYPE, STATUS_TYPE.SUCCESS);
                request.setAttribute(Constants.STATUS_HEADER, "Success");
                request.setAttribute(Constants.STATUS_BODY, "Copies has been successfully added to the database.");
                rd.forward(request, response);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
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
