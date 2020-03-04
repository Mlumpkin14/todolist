package com.todo.web;

import java.io.IOException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.lang.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.todo.dao.TodoDao;
import com.todo.dao.TodoImpl;
import com.todo.model.Todo;
/**
 * Servlet implementation class todoController
 */
@WebServlet("/")
public class todoController extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private TodoDao todoDAO;
    /**
     * @see HttpServlet#HttpServlet()
     */
   

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		todoDAO = new TodoImpl();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getServletPath();
		
		try {
			switch(action) {
			case "/new":
				showNewForm(request,response);
				break;
			case "/insert":
                insertTodo(request, response);
                break;
            case "/delete":
                deleteTodo(request, response);
                break;
            case "/edit":
                showEditForm(request, response);
                break;
            case "/update":
                updateTodo(request, response);
                break;
            case "/list":
                listTodo(request, response);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
                dispatcher.forward(request, response);
                break;
			}
		} catch (SQLException ex) {
			throw new ServletException(ex);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
	
	private void listTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException,ServletException {
		List < Todo > listTodo = todoDAO.selectAllTodos();
		request.setAttribute("listTodo", listTodo);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todolist.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("todoform.jsp");
		dispatcher.forward(request, response);
	}
	
	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		Todo existingTodo = todoDAO.selectTodo(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("todoform.jsp");
		request.setAttribute("todo", existingTodo);
		dispatcher.forward(request, response);		
	}
	
	private void insertTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
        LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"),df);
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
	    Todo newTodo = new Todo(title, username, description, LocalDate.now(), isDone);
	    todoDAO.insertTodo(newTodo);
	    response.sendRedirect("list");
	}
	
	private void updateTodo(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		
		
		String title = request.getParameter("title");
		String username = request.getParameter("username");
		String description = request.getParameter("description");
		
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-mm-dd");
		LocalDate targetDate = LocalDate.parse(request.getParameter("targetDate"));
		
		boolean isDone = Boolean.valueOf(request.getParameter("isDone"));
	    Todo updateTodo = new Todo(id, title, username, description, targetDate, isDone);

	    todoDAO.updateTodo(updateTodo);

	    response.sendRedirect("list");
	}
	/**
	 * @throws SQLException 
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void deleteTodo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		int id = Integer.parseInt(request.getParameter("id"));
		
		todoDAO.deleteTodo(id);
		
		response.sendRedirect("list");
	}

}
