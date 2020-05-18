package com.comweb.controllers;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.comweb.conection.AdDBManager;
import com.comweb.conection.DBManager;
import com.comweb.model.Ads;
import com.comweb.model.Users;

@WebServlet("/adsRetired")
public class AdsRetired extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		// Obtiene el usuario desde la sesion. Redirecciona a index si no se encuentra.
		HttpSession session = request.getSession();
		Users me = (Users) session.getAttribute("me");
		if (me == null) {
			response.sendRedirect("index.jsp");
		} else {

			try (DBManager db = new DBManager()) {
				AdDBManager adDb = new AdDBManager(db);
				List<Ads> adsRetired = adDb.getUserAds(me.getId(), 2);
				request.setAttribute("adsRetired", adsRetired);
			} catch (Exception e) {
				e.printStackTrace();
				response.sendRedirect("error-db.html");
			}

			RequestDispatcher rd = request.getRequestDispatcher("adsRetired.jsp");
			rd.forward(request, response);
		}
	}

}
