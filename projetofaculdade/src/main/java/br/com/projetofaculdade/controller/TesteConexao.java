package br.com.projetofaculdade.controller;

import java.io.IOException;
import java.sql.*;
import br.com.projetofaculdade.dao.Conexao;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/teste-conexao")
public class TesteConexao extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html; charset=UTF-8");
        try (Connection con = Conexao.getConexao();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery("SELECT COUNT(*) FROM item_midia")) {
            rs.next();
            resp.getWriter().println("<h1>Conectado! Itens no catálogo: " + rs.getInt(1) + "</h1>");
        } catch (Exception e) {
            resp.getWriter().println("<h1>Erro: " + e.getMessage() + "</h1>");
        }
    }
}