package meteorologicalmonitoring.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static meteorologicalmonitoring.Utils.SystemConstants.LOGIN_USER_KEY;
import static meteorologicalmonitoring.Utils.SystemConstants.LOGIN_USER_TTL;


//生成jwt令牌toke jsonWebToken 用于鉴别身份，有效期设置为七天
public class JwtUtils {


    /**
     * 生成JWT令牌
     * @param claims JWT第二部分负载 payload 中存储的内容
     * @return
     */
    public static String generateJwt(Map<String, Object> claims,Integer userid){
        Map<String, Object> headers = new HashMap<>();
        headers.put("UserId",userid);
        String jwt = Jwts.builder()
                .addClaims(claims)
                .addClaims(headers)
                .signWith(SignatureAlgorithm.HS256, LOGIN_USER_KEY)//signKey太短的话会报错，用长一点的字符串
                .setExpiration(new Date(System.currentTimeMillis() + LOGIN_USER_TTL))
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     * @param jwt JWT令牌
     * @return JWT第二部分负载 payload 中存储的内容
     */
    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(LOGIN_USER_KEY)
                .parseClaimsJws(jwt)
                .getBody();

        return claims;
    }
}
