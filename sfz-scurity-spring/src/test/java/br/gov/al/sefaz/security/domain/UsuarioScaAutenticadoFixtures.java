package br.gov.al.sefaz.security.domain;

import java.util.List;

import static br.gov.al.sefaz.security.domain.UsuarioScaAutenticado.*;

public class UsuarioScaAutenticadoFixtures {

    public static final String USUARIO_VALIDO_ROLE_01 = "ROLE_01";
    public static final String USUARIO_VALIDO_ROLE_02 = "ROLE_02";
    public static final String USUARIO_VALIDO_ROLE_03 = "ROLE_03";
    public static final List<String> USUARIO_VALIDO_PERMISSOES = List.of(USUARIO_VALIDO_ROLE_01,
                                                                         USUARIO_VALIDO_ROLE_02,
                                                                         USUARIO_VALIDO_ROLE_03);
    public static final long USUARIO_VALIDO_NUMERO_PESSOA = 1L;
    public static final String USUARIO_VALIDO_IDENTIFICADOR_CONEXAO = "1f4b7c99";
    public static final String USUARIO_VALIDO_LOGIN = "josemaria";
    public static final StatusUsuario USUARIO_VALIDO_STATUS = StatusUsuario.ATIVO;
    public static final int USUARIO_VALIDO_MATRICULA = 3;

    public static Builder umUsuarioValidoBemPreenchido() {
        return builder()
                .numeroPessoa(USUARIO_VALIDO_NUMERO_PESSOA)
                .idenficadorConexao(USUARIO_VALIDO_IDENTIFICADOR_CONEXAO)
                .login(USUARIO_VALIDO_LOGIN)
                .status(USUARIO_VALIDO_STATUS)
                .permissoes(USUARIO_VALIDO_PERMISSOES)
                .matricula(USUARIO_VALIDO_MATRICULA);
    }

}
