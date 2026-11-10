package br.com.projetofaculdade.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import br.com.projetofaculdade.model.ItemMidia;

/**
 * Data Access Object (DAO) da entidade {@link ItemMidia}.
 * <p>
 * Encapsula todo o acesso à tabela {@code item_midia}: cadastro, consulta,
 * atualização, exclusão e busca. Nenhuma outra classe do sistema escreve SQL.
 * <p>
 * <b>Segurança:</b> todos os métodos usam {@link PreparedStatement}.
 * Os valores informados pelo usuário entram no SQL pelos parâmetros {@code ?}
 * e são tratados pelo banco apenas como dados, o que impede ataques de
 * SQL Injection.
 * <p>
 * Conexões, comandos e resultados são abertos em blocos
 * {@code try-with-resources}, que os fecham automaticamente mesmo em caso de erro.
 *
 * @author Bruno Zebendo
 * @version 1.0
 */
public class ItemMidiaDAO {

    /**
     * Cadastra um novo item no banco.
     *
     * @param item o item a ser gravado (o {@code id} é ignorado, pois o banco o gera)
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public void inserir(ItemMidia item) throws SQLException {
        String sql = "INSERT INTO item_midia (tipo, titulo, autor_diretor, ano, genero, sinopse) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, item);
            ps.executeUpdate();
        }
    }

    /**
     * Atualiza todos os campos de um item já existente.
     *
     * @param item o item com os novos dados; o {@code id} indica qual linha alterar
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public void atualizar(ItemMidia item) throws SQLException {
        String sql = "UPDATE item_midia SET tipo=?, titulo=?, autor_diretor=?, ano=?, genero=?, sinopse=? WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            preencher(ps, item);
            ps.setInt(7, item.getId()); // 7º parâmetro: o id do WHERE
            ps.executeUpdate();
        }
    }

    /**
     * Remove um item do catálogo.
     *
     * @param id o identificador do item a excluir
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM item_midia WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Busca um único item pelo seu identificador.
     *
     * @param id o identificador do item
     * @return o item encontrado, ou {@code null} se não existir
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public ItemMidia buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM item_midia WHERE id=?";
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    /**
     * Lista todos os itens do catálogo, em ordem alfabética de título.
     *
     * @return lista com todos os itens (vazia se não houver nenhum)
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public List<ItemMidia> listarTodos() throws SQLException {
        String sql = "SELECT * FROM item_midia ORDER BY titulo";
        List<ItemMidia> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
        return lista;
    }

    /**
     * Busca simples: encontra os itens cujo título OU autor/diretor
     * contém o termo informado.
     * <p>
     * Os curingas {@code %} são adicionados em Java, e não no texto do SQL,
     * para que o termo inteiro entre como um único valor no parâmetro {@code ?}.
     *
     * @param termo o texto procurado (ex.: "machado")
     * @return lista dos itens encontrados, em ordem de título
     * @throws SQLException se ocorrer erro de acesso ao banco
     */
    public List<ItemMidia> buscar(String termo) throws SQLException {
        String sql = "SELECT * FROM item_midia WHERE titulo LIKE ? OR autor_diretor LIKE ? ORDER BY titulo";
        List<ItemMidia> lista = new ArrayList<>();
        try (Connection con = Conexao.getConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + termo + "%");
            ps.setString(2, "%" + termo + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(mapear(rs));
                }
            }
        }
        return lista;
    }

    /**
     * Preenche os seis primeiros parâmetros de um INSERT ou UPDATE
     * com os dados do item, na ordem das colunas da tabela.
     *
     * @param ps   o comando preparado
     * @param item o item de onde os valores são lidos
     * @throws SQLException se ocorrer erro ao definir um parâmetro
     */
    private void preencher(PreparedStatement ps, ItemMidia item) throws SQLException {
        ps.setString(1, item.getTipo());
        ps.setString(2, item.getTitulo());
        ps.setString(3, item.getAutorDiretor());
        // O ano é opcional: se não foi informado, grava NULL no banco.
        if (item.getAno() != null) {
            ps.setInt(4, item.getAno());
        } else {
            ps.setNull(4, Types.INTEGER);
        }
        ps.setString(5, item.getGenero());
        ps.setString(6, item.getSinopse());
    }

    /**
     * Converte a linha atual de um {@link ResultSet} em um objeto {@link ItemMidia}.
     *
     * @param rs o resultado da consulta, posicionado em uma linha
     * @return o item montado com os dados da linha
     * @throws SQLException se ocorrer erro ao ler uma coluna
     */
    private ItemMidia mapear(ResultSet rs) throws SQLException {
        ItemMidia item = new ItemMidia();
        item.setId(rs.getInt("id"));
        item.setTipo(rs.getString("tipo"));
        item.setTitulo(rs.getString("titulo"));
        item.setAutorDiretor(rs.getString("autor_diretor"));
        // getInt devolve 0 para NULL; wasNull() permite distinguir os dois casos.
        int ano = rs.getInt("ano");
        item.setAno(rs.wasNull() ? null : ano);
        item.setGenero(rs.getString("genero"));
        item.setSinopse(rs.getString("sinopse"));
        return item;
    }
}