/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import oracle.jrockit.jfr.tools.ConCatRepository;

/**
 *
 * @author ffmas
 */
@WebServlet(urlPatterns = {"/Serv"})
public class Serv extends HttpServlet {
    
    private List<Contato> lista = new ArrayList();
    
    private Contato maisVelhoDaLista (List<Contato> listaAux) {
        // recuperar o contato com a menor birthDate em listaAux
        
        Date dataComp = new Date();
        Contato c = null;
        
        for (Contato contato : listaAux) {
            if (contato.getBirthDate().before(dataComp)) {
                c = contato;
                dataComp = contato.getBirthDate();
            }
        }
        
        return c;
    }
    
    private List<Contato> ordenarLista (List<Contato> listaAux) {
        List<Contato> listaOrdenada = new ArrayList();
    
        while (!listaAux.isEmpty()) {
            
            Contato maisVelho = maisVelhoDaLista(listaAux);
            
            listaOrdenada.add(maisVelho);
            
            listaAux.remove(maisVelho);
            
        }       
        
        return listaOrdenada;
    }

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
            throws ServletException, IOException, ParseException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */

            out.print("        <form name=\"form\" action=\"Serv\" method=\"post\">\n" +
"            Nome: <input type=\"text\" name=\"name\" value=\"\" /> <br/>\n" +
"            Endereço: <input type=\"text\" name=\"address\" value=\"\" /> <br/>  \n" +
"            Telefone: <input type=\"text\" name=\"phone\" value=\"\" /> <br/>\n" +
"            Data: <input type=\"text\" name=\"birthDate\" value=\"\" /> <br/>\n" +
"            <input type=\"submit\" value=\"Submeter\" />\n" +
"        </form>");
            
            out.print("<form action=\"Serv\" method=\"post\">\n" +
"            <input type=\"text\" name=\"DEL\" value=\"\" />\n" +
"            <input type=\"submit\" value=\"Deletar\" />\n" +
"        </form>");
            
            if (request.getParameter("name") != null
                    && !request.getParameter("name").equals("")) {
                String name = request.getParameter("name").toString();
                String address = request.getParameter("address").toString();
                String phone = request.getParameter("phone").toString();
                String auxDate = request.getParameter("birthDate").toString();
                Date birthDate = new SimpleDateFormat("dd/MM/yyyy").parse(auxDate);

                Contato c = new Contato();
                
                c.setAddress(address);
                c.setBirthDate(birthDate);
                c.setName(name);
                c.setPhone(phone);

                lista.add(c);

                out.println("Contato criado com sucesso: " + c.getName());

            }

            if (request.getParameter("DEL") != null && 
                    !request.getParameter("DEL").equals("")) {
                String nameDel = request.
                        getParameter("DEL").toString();
                
                System.out.println("nameDEL: " + nameDel);
                
                Contato contactToDelete = null;
                
                for (Contato contato : lista) {
                    
                    if (contato.getName().equals(nameDel)) {
                        contactToDelete = contato;
                    }
                }
                
                if (contactToDelete != null) {
                    lista.remove(contactToDelete);
                }              
                
            }
            
//            this.lista = ordenarLista(this.lista);
//            Collections.sort(lista, new Comparator<Contato>() {
//                @Override
//                public int compare(Contato c1, Contato c2) {
//
//                    return c1.getBirthDate().
//                            compareTo(c2.getBirthDate());
//                }
//            });
            Collections.sort(lista, (Contato c1, Contato c2)
                    -> c1.getBirthDate().compareTo(c2.getBirthDate()));

            for (Contato contato : lista) {
                out.println("Nome: " + contato.getName() + " <br/>");
                out.println("Data de nascimento: " + contato.getBirthDate() + " <br/>");
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Serv.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(Serv.class.getName()).log(Level.SEVERE, null, ex);
        }
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
