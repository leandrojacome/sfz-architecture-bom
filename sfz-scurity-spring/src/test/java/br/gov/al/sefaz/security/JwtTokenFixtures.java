package br.gov.al.sefaz.security;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Map;

public class JwtTokenFixtures {

    public static final String JWT_CHAVE_VALIDA = "255d619e0a7830e76e34087dae504385f64d386509d8313f5fae2a95fa0f536746" +
            "50ae8043273ba5d071c20d57ddcfe692681b86901192c7f72f9906c502104d901be491899141da0dbdd92ade8425418dabe15a7e" +
            "7d2623caabe3ca1868ba5837e6adb7898939ad35ff0eda04c49f33e87e36e63ce7fc3710df64535bdfb9c863453f7ddb30918499" +
            "eb41274b1ab539a14963ea62491ba21537d32745636d651e16e370782a7b309d2ccb2589e943b46115f6c720a3584251de3e7188" +
            "4f966951868735a61b68751ae3d64e853bf8ece9a3aac1986a6891107b2b23fd3db38551c67efbc02dccd1ccf6c7e2cd905a5b99" +
            "05cb444c5ab08f05639cea33485a6d";

    public static final String JWT_CHAVE_INVALIDA = "ce9698390f7ab95c99f79574e054071ffd0a1232be66fb971d47b5622c4a6cf5" +
            "9f377f99b0d9ed9c63bd9ce29ff386ccb9e86520dc744e8dcfb8ba4c231965e040674cca5eaadc6df07fa065d47f6702d658e384" +
            "062ec05bfd12d88736b843fcb6df3d2b9983bb303f5f81263b4b150486a98498f12b67c6f79d079fc10dddf34c38f341f9d56c73" +
            "e01d272aa32824d112f16b9d417565131953bb4e376e516d0745730d99b699c588c04fd2fa4f54b1fd4c1a7ceadc17b3301819ef" +
            "e155dcc7613e46f8d1f63dae53c22a66533232d6d65c818e0aeb963ab20e3aa7e50f66ff0358b7f9996fd9ec9abc5264ed7491ba" +
            "c3dc77a5ca3ffcbb23e98ff582e94fab";


    public static final String JWT_CLAIM_SUBJECT = "josesilva";
    public static final String JWT_CLAIM_AUTH = "ROLE_A,ROLE_B,ROLE_C";
    public static final Long JWT_CLAIM_NUMERO_PESSOA = 7L;
    public static final String JWT_CLAIM_ID_CONEXAO = "7B574F47130F9BC83026BE4F0EA00124";
    public static final String JWT_CLAIM_IND_STATUS = "A";
    public static final Integer JWT_CLAIM_MATRICULA = 9;

    public static String createValidToken() {
        return getValidJwtsBuilder().compact();
    }

    public static String createInvalidToken() {
        var dataUmAnoAtras = Date.from(LocalDate.now().minusYears(1).atStartOfDay().toInstant(ZoneOffset.UTC));
        return getValidJwtsBuilder().setExpiration(dataUmAnoAtras).compact();
    }

    public static String createTokenWithClaims(Map<String, Object> claims) {
        var builder = defaultBuilder();
        claims.forEach(builder::claim);
        return builder.compact();
    }

    private static JwtBuilder getValidJwtsBuilder() {
        return defaultBuilder()
                .setSubject(JWT_CLAIM_SUBJECT)
                .claim("auth", JWT_CLAIM_AUTH)
                .claim("numPessoa", JWT_CLAIM_NUMERO_PESSOA)
                .claim("idConexao", JWT_CLAIM_ID_CONEXAO)
                .claim("indStatus", JWT_CLAIM_IND_STATUS)
                .claim("matricula", JWT_CLAIM_MATRICULA);
    }

    private static JwtBuilder defaultBuilder() {
        var expiracaoEmUmAno = LocalDate.now().plusYears(5).atStartOfDay().toInstant(ZoneOffset.UTC);
        return Jwts.builder()
                   .signWith(Keys.hmacShaKeyFor(JWT_CHAVE_VALIDA.getBytes()))
                   .setExpiration(Date.from(expiracaoEmUmAno));
    }

}
