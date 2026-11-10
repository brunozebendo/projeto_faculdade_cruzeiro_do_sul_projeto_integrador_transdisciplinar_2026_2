package br.com.projetofaculdade.controller;

import java.io.IOException;
import br.com.projetofaculdade.dao.ItemMidiaDAO;
import br.com.projetofaculdade.model.ItemMidia;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

/**
 * Controlador (Controller, no padrão MVC) do catálogo de mídias.
 * <p>
 * Atende todas as requisições do endereço {@code /itens}. A ação desejada
 * é indicada pelo parâmetro {@code acao}:
 * <ul>
 *   <li><b>GET</b> (apenas consulta, não altera dados):
 *       {@code listar} (padrão), {@code buscar}, {@code detalhes},
 *       {@code novo} e {@code editar};</li>
 *   <li><b>POST</b> (altera dados): salvar (cadastro ou edição) e
 *       {@code excluir}.</li>
 * </ul>
 * O Servlet chama o {@link ItemMidiaDAO}, guarda o resultado como atributo
 * da requisição e encaminha para uma JSP em {@code /WEB-INF/views}, que
 * monta a página.
 *
 * @author Bruno Zebendo
 * @version 1.0
 */
@WebServlet("/itens")
public class ItemMidiaServlet extends HttpServlet {

    /** Objeto de acesso a dados usado por todas as ações. */
    private final ItemMidiaDAO dao = new ItemMidiaDAO();

    /**
     * Trata as requisições GET, que apenas exibem telas.
     *
     * @param req  a requisição, com o parâmetro {@code acao} e, conforme o caso,
     *             {@code id} ou {@code termo}
     * @param resp a resposta enviada ao navegador
     * @throws ServletException se ocorrer erro no banco ou no encaminhamento
     * @throws IOException      se ocorrer erro de entrada/saída
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar"; // sem ação informada: mostra a lista completa
        }
        try {
            switch (acao) {
                // Formulário vazio para cadastrar um novo item.
                case "novo" -> abrir(req, resp, "form.jsp");

                // Formulário preenchido com os dados do item a editar.
                case "editar" -> {
                    req.setAttribute("item", dao.buscarPorId(Integer.parseInt(req.getParameter("id"))));
                    abrir(req, resp, "form.jsp");
                }

                // Página com todas as informações de um item.
                case "detalhes" -> {
                    req.setAttribute("item", dao.buscarPorId(Integer.parseInt(req.getParameter("id"))));
                    abrir(req, resp, "detalhes.jsp");
                }

                // Busca por título ou autor/diretor; reaproveita a tela de lista.
                case "buscar" -> {
                    String termo = req.getParameter("termo");
                    termo = (termo == null) ? "" : termo.trim();
                    req.setAttribute("termo", termo); // devolve o termo ao campo de busca
                    req.setAttribute("itens", dao.buscar(termo));
                    abrir(req, resp, "lista.jsp");
                }

                // Padrão: lista todos os itens.
                default -> {
                    req.setAttribute("itens", dao.listarTodos());
                    abrir(req, resp, "lista.jsp");
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    /**
     * Trata as requisições POST, que alteram dados: salvar (cadastrar ou
     * editar) e excluir.
     * <p>
     * Se o campo {@code id} vier vazio, o item é cadastrado; se vier preenchido,
     * o item existente é atualizado. Ao final, o navegador é redirecionado
     * para a lista (padrão Post/Redirect/Get), para que recarregar a página
     * não repita a operação.
     *
     * @param req  a requisição, com os campos do formulário
     * @param resp a resposta enviada ao navegador
     * @throws ServletException se ocorrer erro no banco
     * @throws IOException      se ocorrer erro de entrada/saída
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Precisa vir antes de ler qualquer parâmetro, para preservar os acentos.
        req.setCharacterEncoding("UTF-8");
        String acao = req.getParameter("acao");
        try {
            if ("excluir".equals(acao)) {
                dao.excluir(Integer.parseInt(req.getParameter("id")));
            } else {
                // Monta o objeto com os dados do formulário.
                ItemMidia item = new ItemMidia();
                item.setTipo(req.getParameter("tipo"));
                item.setTitulo(req.getParameter("titulo"));
                item.setAutorDiretor(req.getParameter("autorDiretor"));
                String ano = req.getParameter("ano");
                item.setAno(ano == null || ano.isBlank() ? null : Integer.valueOf(ano));
                item.setGenero(req.getParameter("genero"));
                item.setSinopse(req.getParameter("sinopse"));

                String id = req.getParameter("id");
                if (id == null || id.isBlank()) {
                    dao.inserir(item);              // cadastro
                } else {
                    item.setId(Integer.parseInt(id));
                    dao.atualizar(item);            // edição
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
        resp.sendRedirect(req.getContextPath() + "/itens");
    }

    /**
     * Encaminha a requisição para uma JSP da pasta {@code /WEB-INF/views}.
     * <p>
     * As JSPs ficam dentro de {@code WEB-INF} para que não possam ser
     * abertas diretamente pelo navegador, somente por este Servlet.
     *
     * @param req    a requisição atual, com os atributos que a JSP vai exibir
     * @param resp   a resposta atual
     * @param pagina o nome do arquivo JSP (ex.: "lista.jsp")
     * @throws ServletException se ocorrer erro na JSP
     * @throws IOException      se ocorrer erro de entrada/saída
     */
    private void abrir(HttpServletRequest req, HttpServletResponse resp, String pagina)
            throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/" + pagina).forward(req, resp);
    }
}