package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.Common;
import au.edu.unsw.comp4920.common.CommonDAO;
import au.edu.unsw.comp4920.common.Constants;
import au.edu.unsw.comp4920.objects.User;
import au.edu.unsw.comp4920.web.Command;

public class ResetPasswordCommand implements Command {

	public ResetPasswordCommand() {

	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao)
			throws ServletException, IOException {
		System.out.println("Inside: ResetPasswordCommand");
		String action = request.getParameter("action");
		if (action != null && action.equalsIgnoreCase("reset")) {
			String input_token = request.getParameter("token");
			System.out.println("ResetPasswordCommand: token from request is " + input_token);
			User user = dao.getUser(request.getParameter("username"), null);
			System.out.println("ResetPasswordCommand: user is " + request.getParameter("username"));
			if (user == null) {
				System.err.println("ResetPasswordCommand: User not found in database");

				request.setAttribute(Constants.ERROR, 1);
				request.setAttribute(Constants.ERRORMSG, "User does not exist.");
			} else {

				String user_token = dao.getToken(user);
				System.out.println("ResetPasswordCommand: user token = " + user_token);
				if (user_token.equals(input_token)) {
					String newPassword = request.getParameter("password");
					String hashedPassword = Common.hashPassword(newPassword, user.getSaltHash());
					System.out.println("hashed password is " + hashedPassword);
					dao.setPassword(user, hashedPassword);
					dao.setToken(user, "");
					System.out.println("ResetPasswordCommand: Password reset successful");
					response.sendRedirect(Constants.ROUTER + Constants.RESETPASSWORD_COMMAND + "&success=yes");
					return;
				} else {
					System.err.println("ResetPasswordCommand: Invalid token");

					request.setAttribute(Constants.ERROR, 1);
					request.setAttribute(Constants.ERRORMSG, "Invalid token.");
				}

			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/resetpassword.jsp");
		rd.forward(request, response);
	}
}
