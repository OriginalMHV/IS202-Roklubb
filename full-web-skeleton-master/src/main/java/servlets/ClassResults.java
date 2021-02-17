package servlets;

import models.ClassResultModel;
import tools.repository.ClassRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "SeniorMen", urlPatterns = {"/SeniorMen"})
public class ClassResults extends AbstractAppServlet{
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void writeBody(HttpServletRequest req, HttpServletResponse res, PrintWriter out) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<ClassResultModel> useResult = ClassRepository.getClassResultat();
        req.setAttribute("List", useResult);
        req.getRequestDispatcher("seniorMen.jsp").forward(req, resp);
    }
    }
