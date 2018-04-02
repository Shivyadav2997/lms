<%-- 
    Document   : BookManagement
    Created on : Oct 13, 2015, 7:53:21 PM
    Author     : Hardik
--%>

<%@page import="com.hpt150030.utilities.STATUS_TYPE"%>
<%@page import="com.hpt150030.utilities.Constants"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Book Management</title>
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    </head>
    <body>
        <%!
            boolean hasStatus = false;
        %>
        <%
            if (request.getAttribute(Constants.HAS_STATUS) != null) {
                if ((Boolean) request.getAttribute(Constants.HAS_STATUS) == true) {
                    hasStatus = true;
                }
            } else {
                hasStatus = false;
            }
        %>
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container">
                <div class="navbar-header">
                    <a class="navbar-brand" href="index.html">Library Management System</a>
                </div>
                <div id="navbar" class="navbar-collapse collapse">
                    <ul class="nav navbar-nav navbar-right">
                        <li><a href="index.html">Home</a></li>
                        <li><a href="SearchBookController">Search</a></li>
                        <li><a href="BookLoanController">Loan</a></li>
                        <li><a href="FineTrackingController">Fines</a></li>
                        <li><a href="NewBorrowerController">Borrowers</a></li>
                        <li class="active"><a href="#">Books</a></li>
                    </ul>
                </div>
            </div>
        </nav>

        <br>
        <br>
        <br>
        <% if (hasStatus) {%>
                <h5>
                    <% if (request.getAttribute(Constants.STATUS_TYPE) == STATUS_TYPE.ERROR) {
                    %>
                    <div class="alert alert-danger" role="alert">
                        <strong>
                            <%= request.getAttribute(Constants.STATUS_BODY)%>
                        </strong>
                    </div>
                    <% } else if (request.getAttribute(Constants.STATUS_TYPE) == STATUS_TYPE.WARNING) {
                    %>
                    <div class="alert alert-info" role="alert">
                        <strong>
                            <%= request.getAttribute(Constants.STATUS_BODY)%>
                        </strong>
                    </div>
                    <% } else if (request.getAttribute(Constants.STATUS_TYPE) == STATUS_TYPE.SUCCESS) {
                    %>
                    <div class="alert alert-success" role="alert">
                        <strong>
                            <%= request.getAttribute(Constants.STATUS_BODY)%>
                        </strong>
                    </div>
                    <% }%>
                </h5>
                <%}%>
        <div class="panel panel-default">
            <div class="panel-heading">Add new book</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/NewBookController" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BOOK_REQ_ID%>">Book Id:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BOOK_REQ_ID%>" placeholder="Enter book id"
                                   value="<% if (request.getParameter(Constants.NEW_BOOK_REQ_ID) != null) {
                                           out.println(request.getParameter(Constants.NEW_BOOK_REQ_ID));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BOOK_REQ_TITLE%>">Title:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BOOK_REQ_TITLE%>" placeholder="Enter title" 
                                   value="<% if (request.getParameter(Constants.NEW_BOOK_REQ_TITLE) != null) {
                                           out.println(request.getParameter(Constants.NEW_BOOK_REQ_TITLE));
                                       }%>">
                        </div>
                    </div>
                    

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BOOK_REQ_AUTHOR%>">Author:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BOOK_REQ_AUTHOR%>" placeholder="Enter author"
                                   value="<% if (request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR) != null) {
                                           out.println(request.getParameter(Constants.NEW_BOOK_REQ_AUTHOR));
                                       }%>">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>

                
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Add new branch</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/NewBranchController" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BRANCH_REQ_ID%>">Branch Id:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BRANCH_REQ_ID%>" placeholder="Enter branch id"
                                   value="<% if (request.getParameter(Constants.NEW_BRANCH_REQ_ID) != null) {
                                           out.println(request.getParameter(Constants.NEW_BRANCH_REQ_ID));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BRANCH_REQ_NAME%>">Name:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BRANCH_REQ_NAME%>" placeholder="Enter name" 
                                   value="<% if (request.getParameter(Constants.NEW_BRANCH_REQ_NAME) != null) {
                                           out.println(request.getParameter(Constants.NEW_BRANCH_REQ_NAME));
                                       }%>">
                        </div>
                    </div>
                    

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BRANCH_REQ_ADDRESS%>">Address:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BRANCH_REQ_ADDRESS%>" placeholder="Enter address"
                                   value="<% if (request.getParameter(Constants.NEW_BRANCH_REQ_ADDRESS) != null) {
                                           out.println(request.getParameter(Constants.NEW_BRANCH_REQ_ADDRESS));
                                       }%>">
                        </div>
                    </div>

   
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>

                
            </div>
        </div>
        <div class="panel panel-default">
            <div class="panel-heading">Add Copies</div>
            <div class="panel-body">
                <form class="form-horizontal" role="form" action="<%=request.getContextPath()%>/AddCopiesController" method="POST">
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_CBRANCH_REQ_ID%>">Branch Id:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_CBRANCH_REQ_ID%>" placeholder="Enter branch id"
                                   value="<% if (request.getParameter(Constants.NEW_CBRANCH_REQ_ID) != null) {
                                           out.println(request.getParameter(Constants.NEW_CBRANCH_REQ_ID));
                                       }%>">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_CBOOK_REQ_ID%>">Book Id:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_CBOOK_REQ_ID%>" placeholder="Enter book id" 
                                   value="<% if (request.getParameter(Constants.NEW_CBOOK_REQ_ID) != null) {
                                           out.println(request.getParameter(Constants.NEW_CBOOK_REQ_ID));
                                       }%>">
                        </div>
                    </div>
                    

                    <div class="form-group">
                        <label class="control-label col-sm-2" for="<%=Constants.NEW_BRANCH_REQ_COPIES%>">Copies:</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="<%= Constants.NEW_BRANCH_REQ_COPIES%>" placeholder="Enter copies"
                                   value="<% if (request.getParameter(Constants.NEW_BRANCH_REQ_COPIES) != null) {
                                           out.println(request.getParameter(Constants.NEW_BRANCH_REQ_COPIES));
                                       }%>">
                        </div>
                    </div>

   
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-sm-10">
                            <button type="submit" class="btn btn-primary">Submit</button>
                        </div>
                    </div>
                </form>

                
            </div>
        </div>
    </body>
</html>
