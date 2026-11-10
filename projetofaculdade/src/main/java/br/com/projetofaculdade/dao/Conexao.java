package br.com.projetofaculdade.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Fornece conexões JDBC com o banco de dados MySQL {@code catalogo}.
 * <p>
 * Centraliza em um único lugar o endereço do banco, o usuário e a senha,
 * para que as classes DAO não precisem repetir essas informações.
 * <p>
 * <b>Observação de segurança:</b> neste projeto acadêmico as credenciais
 * estão fixas no código. Em produção, deveriam vir de variáveis de ambiente
 * ou de um pool de conexões configurado no servidor.
 *
 * @author Bruno Zebendo
 * @version 1.0
 */
public class Conexao {

    /** Endereço JDBC do banco: servidor local, porta 3306, banco "catalogo". */
    private static final String URL = "jdbc:mysql://localhost:3306/catalogo";

    /** Usuário do MySQL. */
    private static final String USUARIO = "root";

    /** Senha do usuário do MySQL. */
    private static final String SENHA = "sua_senha_aqui";

    /**
     * Abre e devolve uma nova conexão com o banco.
     * <p>
     * Quem chama este método é responsável por fechar a conexão.
     * No projeto isso é feito com {@code try-with-resources}.
     *
     * @return uma conexão aberta com o banco {@code catalogo}
     * @throws SQLException se o driver não for encontrado ou se a conexão falhar
     *                      (por exemplo, senha errada ou MySQL desligado)
     */
    public static Connection getConexao() throws SQLException {
        try {
            // Carrega o driver do MySQL (mysql-connector-j, em WEB-INF/lib).
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do MySQL não encontrado", e);
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}