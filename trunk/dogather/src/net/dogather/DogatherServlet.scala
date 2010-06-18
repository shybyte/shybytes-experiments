package net.dogather;

import java.io.IOException;
import javax.servlet.http._;

class DogatherServlet extends HttpServlet {
	
	@throws(classOf[IOException])
	override def doGet(req:HttpServletRequest ,resp:HttpServletResponse )  =  {
		resp.setContentType("text/plain");
		val car = new Car();
		resp.getWriter().println("Hola Mundo "+car.drive());
	}
}
