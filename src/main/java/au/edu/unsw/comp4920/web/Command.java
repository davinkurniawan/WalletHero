package au.edu.unsw.comp4920.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import au.edu.unsw.comp4920.common.CommonDAO;

public interface Command {
	void execute(HttpServletRequest request, HttpServletResponse response, CommonDAO dao) throws ServletException, IOException;
}