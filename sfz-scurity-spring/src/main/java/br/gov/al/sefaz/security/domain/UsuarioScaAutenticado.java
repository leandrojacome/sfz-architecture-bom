package br.gov.al.sefaz.security.domain;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public record UsuarioScaAutenticado(Long numeroPessoa,
                                    String idenficadorConexao,
                                    String login,
                                    StatusUsuario status,
                                    List<String> permissoes,
                                    Integer matricula) {

    public UsuarioScaAutenticado {
        Objects.requireNonNull(numeroPessoa);
        Objects.requireNonNull(idenficadorConexao);
        Objects.requireNonNull(login);
        if (login.isBlank()) {
            throw new IllegalArgumentException("Login não pode ser vazio");
        }
        Objects.requireNonNull(status);
        if (Objects.isNull(permissoes)) {
            permissoes = Collections.emptyList();
        } else {
            permissoes = List.copyOf(permissoes);
        }
    }

    public enum StatusUsuario {

        ATIVO('A', "Ativo"),
        PRIMEIRO_ACESSO('P', "Primeiro Acesso"),
        INATIVO('I', "Inativo"),
        BLOQUEADO('B', "Bloqueado"),
        EXPIRADO('E', "Expirado"),
        TROCAR_SENHA('T', "Trocar senha");

        private final Character codigo;
        private final String descricao;

        StatusUsuario(Character codigo, String descricao) {
            this.codigo = codigo;
            this.descricao = descricao;
        }

        public static StatusUsuario fromStatusChar(char statusChar) {
            return Arrays.stream(values())
                         .filter(s -> s.getCodigo().equals(statusChar))
                         .findFirst()
                         .orElseThrow(IllegalArgumentException::new);
        }

        public Character getCodigo() {
            return codigo;
        }

        public String getDescricao() {
            return descricao;
        }

    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private Long numeroPessoa;
        private String idenficadorConexao;
        private String login;
        private StatusUsuario status;
        private List<String> permissoes;
        private Integer matricula;

        public Builder numeroPessoa(Long val) {
            numeroPessoa = val;
            return this;
        }

        public Builder idenficadorConexao(String val) {
            idenficadorConexao = val;
            return this;
        }

        public Builder login(String val) {
            login = val;
            return this;
        }

        public Builder status(StatusUsuario val) {
            status = val;
            return this;
        }

        public Builder permissoes(List<String> val) {
            permissoes = val;
            return this;
        }

        public Builder matricula(Integer val) {
            matricula = val;
            return this;
        }

        public UsuarioScaAutenticado build() {
            return new UsuarioScaAutenticado(numeroPessoa, idenficadorConexao, login, status, permissoes, matricula);
        }

    }

    @Override
    public String toString() {
        return "UsuarioScaAutenticado{" +
                "numeroPessoa=" + numeroPessoa +
                ", idenficadorConexao='" + idenficadorConexao + '\'' +
                ", login='" + login + '\'' +
                ", status=" + status +
                ", permissoes=" + permissoes +
                ", matricula=" + matricula +
                '}';
    }

}
