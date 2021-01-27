package com.zzw.learning.utils;

import com.zzw.learning.constant.JwtConstans;
import com.zzw.learning.model.UserInfo;
import io.jsonwebtoken.*;
import org.joda.time.DateTime;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

public class JwtUtils {
    /**
     * jwt的秘钥
     */
    public static String JWT_SECRET = "abcdefgh";


    /**
     * 默认jwt的过期时间分钟
     */
    public static Integer DEFAULT_EXPIRED_DATE = 120;
    /**
     * 临近过期时间 分钟
     */
    public static Integer NEAR_EXPIRED_DATE = 5;

    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return token
     */
    public static String generateToken(UserInfo userInfo, String privateKey, Integer expireMinutes) {
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstans.JWT_KEY_USER_NAME, userInfo.getUserName())
                .claim(JwtConstans.JWT_KEY_PHONE, userInfo.getPhone())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
//                .signWith(SignatureAlgorithm.RS256, privateKey)
                .signWith(SignatureAlgorithm.HS512, privateKey)
                .compact();
    }

    /**
     * 私钥加密token(载荷部分只含手机号)
     *
     * @param userInfoDTO   载荷中的数据
     * @param privateKey    私钥
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateTokenWithPhone(UserInfo userInfoDTO, PrivateKey privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ID, userInfoDTO.getId())
                .claim(JwtConstans.JWT_KEY_USER_NAME, userInfoDTO.getUserName())
                .claim(JwtConstans.JWT_KEY_PHONE, userInfoDTO.getPhone())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, privateKey)
                .compact();
    }


    /**
     * 私钥加密token
     *
     * @param userInfo      载荷中的数据
     * @param privateKey    私钥字节数组
     * @param expireMinutes 过期时间，单位秒
     * @return
     * @throws Exception
     */
    public static String generateToken(UserInfo userInfo, byte[] privateKey, int expireMinutes) throws Exception {
        return Jwts.builder()
                .claim(JwtConstans.JWT_KEY_ID, userInfo.getId())
                .claim(JwtConstans.JWT_KEY_USER_NAME, userInfo.getUserName())
                .setExpiration(DateTime.now().plusMinutes(expireMinutes).toDate())
                .signWith(SignatureAlgorithm.RS256, RsaUtils.getPrivateKey(privateKey))
                .compact();
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    /**
     * 公钥解析token
     *
     * @param token     用户请求中的token
     * @param publicKey 公钥字节数组
     * @return
     * @throws Exception
     */
    private static Jws<Claims> parserToken(String token, byte[] publicKey) throws Exception {
        return Jwts.parser().setSigningKey(RsaUtils.getPublicKey(publicKey))
                .parseClaimsJws(token);
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, PublicKey publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_USER_NAME)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_PHONE)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ICON)),
                ObjectUtils.toInt(body.get(JwtConstans.JWT_IS_ADMIN))
        );
    }

    /**
     * 获取token中的过期时间
     *
     * @param token     token
     * @param publicKey 公钥
     * @return
     * @throws Exception
     */
    public static Date getExpiration(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return body.getExpiration();
    }

    /**
     * 获取token中的用户信息
     *
     * @param token     用户请求中的令牌
     * @param publicKey 公钥
     * @return 用户信息
     * @throws Exception
     */
    public static UserInfo getInfoFromToken(String token, byte[] publicKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        return new UserInfo(
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ID)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_USER_NAME)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_PHONE)),
                ObjectUtils.toString(body.get(JwtConstans.JWT_KEY_ICON)),
                ObjectUtils.toInt(body.get(JwtConstans.JWT_IS_ADMIN))
        );
    }


    /**
     * 解析token是否正确(true-正确, false-错误)<br>
     */
    public static Boolean checkToken(String token, String jwtSecret) {
//        Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
//        return true;

        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
            return true;
        } catch (JwtException e) {
            return false;
        }
    }



    /**
     * <pre>
     *  验证token是否失效
     *  true:过期   false:没过期
     * </pre>
     */
    public static Boolean isTokenExpired(String token, String jwtSecret) {
        try {
            final Date expiration = getExpirationDateFromToken(token, jwtSecret);
            return expiration.before(new Date());
        } catch (ExpiredJwtException expiredJwtException) {
            return true;
        }
    }


    /**
     * 获取jwt失效时间
     */
    public static Date getExpirationDateFromToken(String token, String jwtSecret) {
        return getClaimFromToken(token, jwtSecret).getExpiration();
    }

    /**
     * 获取jwt的payload部分
     */
    public static Claims getClaimFromToken(String token, String jwtSecret) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }


}
