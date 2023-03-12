package dat.bibliotek.web;

import dat.bibliotek.config.ApplicationStart;
import dat.bibliotek.dtos.UdlaanDTO;
import dat.bibliotek.exceptions.DatabaseException;
import dat.bibliotek.persistence.BiblioteksMapper;
import dat.bibliotek.persistence.ConnectionPool;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet(name = "udlaan", urlPatterns = {"/udlaan"} )
public class udlaan extends HttpServlet
{
    private ConnectionPool connectionPool;

    @Override
    public void init() throws ServletException
    {
        this.connectionPool = ApplicationStart.getConnectionPool();
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
    {
        response.setContentType("text/html");
        BiblioteksMapper biblioteksMapper = new BiblioteksMapper(connectionPool);
        List<UdlaanDTO> udlaanDTOList = null;
        try
        {
            udlaanDTOList = biblioteksMapper.hentAlleUdlaan();
        }
        catch (DatabaseException e)
        {
            Logger.getLogger("web").log(Level.SEVERE, e.getMessage());
            request.setAttribute("fejlbesked", e.getMessage());
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
        request.setAttribute("udlaanliste", udlaanDTOList);
        request.getRequestDispatcher("WEB-INF/udlaan.jsp").forward(request, response);
    }

    public void destroy()
    {

    }
}