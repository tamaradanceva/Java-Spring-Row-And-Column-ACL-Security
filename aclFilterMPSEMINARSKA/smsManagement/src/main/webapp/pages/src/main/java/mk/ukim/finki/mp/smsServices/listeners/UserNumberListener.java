/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mk.ukim.finki.mp.smsServices.listeners;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;


public class UserNumberListener implements HttpSessionListener {
	public static final String NUM_USERS = "numUsers";

	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("session created...");
		ServletContext ctx = se.getSession().getServletContext();
		synchronized (ctx) {
			Integer numUsers = (Integer) ctx.getAttribute(NUM_USERS);
			if (numUsers == null) {
				numUsers = 0;
			}
			numUsers++;
			ctx.setAttribute(NUM_USERS, numUsers);
		}
	}

	public void sessionDestroyed(HttpSessionEvent se) {
		System.out.println("session destroyed...");
		ServletContext ctx = se.getSession().getServletContext();
		synchronized (ctx) {
			Integer numUsers = (Integer) ctx.getAttribute(NUM_USERS);
			numUsers--;
			ctx.setAttribute(NUM_USERS, numUsers);
		}
	}
}
