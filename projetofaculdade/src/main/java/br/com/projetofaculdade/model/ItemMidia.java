package br.com.projetofaculdade.model;

/**
 * Representa um item do catálogo: um livro, um filme ou uma série.
 * <p>
 * É a classe de modelo (Model, no padrão MVC). Cada objeto corresponde
 * a uma linha da tabela {@code item_midia} do banco de dados. Os objetos
 * são criados pelo {@link br.com.projetofaculdade.dao.ItemMidiaDAO} ao ler
 * o banco e pelo Servlet ao receber os dados do formulário.
 * @brunozebendo
 */
public class ItemMidia {

    /** Identificador único, gerado automaticamente pelo banco (AUTO_INCREMENT). */
    private int id;

    /** Tipo da mídia: "LIVRO", "FILME" ou "SERIE". */
    private String tipo;

    /** Título da obra. Campo obrigatório. */
    private String titulo;

    /** Autor (no caso de livro) ou diretor (no caso de filme ou série). Campo obrigatório. */
    private String autorDiretor;

    /**
     * Ano de publicação ou lançamento.
     * Usa {@code Integer} (e não {@code int}) para aceitar {@code null}
     * quando o ano não for informado.
     */
    private Integer ano;

    /** Gênero da obra, por exemplo "Romance" ou "Ficção científica". */
    private String genero;

    /** Breve resumo da obra. */
    private String sinopse;

    /** @return o identificador do item */
    public int getId() { return id; }

    /** @param id o identificador do item */
    public void setId(int id) { this.id = id; }

    /** @return o tipo da mídia (LIVRO, FILME ou SERIE) */
    public String getTipo() { return tipo; }

    /** @param tipo o tipo da mídia (LIVRO, FILME ou SERIE) */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return o título da obra */
    public String getTitulo() { return titulo; }

    /** @param titulo o título da obra */
    public void setTitulo(String titulo) { this.titulo = titulo; }

    /** @return o autor ou diretor */
    public String getAutorDiretor() { return autorDiretor; }

    /** @param autorDiretor o autor ou diretor */
    public void setAutorDiretor(String autorDiretor) { this.autorDiretor = autorDiretor; }

    /** @return o ano de publicação/lançamento, ou {@code null} se não informado */
    public Integer getAno() { return ano; }

    /** @param ano o ano de publicação/lançamento, ou {@code null} */
    public void setAno(Integer ano) { this.ano = ano; }

    /** @return o gênero da obra */
    public String getGenero() { return genero; }

    /** @param genero o gênero da obra */
    public void setGenero(String genero) { this.genero = genero; }

    /** @return a sinopse da obra */
    public String getSinopse() { return sinopse; }

    /** @param sinopse a sinopse da obra */
    public void setSinopse(String sinopse) { this.sinopse = sinopse; }
}