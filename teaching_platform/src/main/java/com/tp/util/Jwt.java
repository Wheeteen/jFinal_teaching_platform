package com.tp.util;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSON;
import com.jfinal.core.Const;
import com.tp.clientModel.CheckResult;
import com.tp.clientModel.UserReqModel;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.security.SignatureException;
import java.util.Date;

public class Jwt {
	
	// JWT的组成：头部(header)  载荷(Claims) 签名 (signature)
	/**
	 * 1. Header: {"typ":"JWT", "alg":"HS256"}
	 * 2. Claims: {"sub": "用户","iss":"签发者", "exp":1496296411, "userid":1234}
	 * Claims中字段说明：这几个字段都是JWT标准定义的：
	 * iss: 该JWT的签发者
	 * sub: 该JWT所面向的用户
	 * aud: 接收该JWT的一方
	 * exp(expires): 什么时候过期，这里是一个Unix时间戳
	 * iat(issued at): 在什么时候签发的

	 * 还可以自己添加所需的字段，比如后面userid。
	 * 
	 * 3. JWT = base64(Header)+. + base64(Claim)+ 信息加密后得到第三段签名， 然后将三部分返回给客户端
	 *    在后续请求中，服务端只需要对用户请求中包含的JWT进行解码，即可验证是否可以授权用户获取相应信息。
	 *    
	 *    jwt可以省去读取session的步骤，减轻服务端的压力。服务端只需要通过密钥验证客户端携带的jwt来验证是否是已经登录的用户。
	 * @return
	 */
//	public static String createJWT() {
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//        SecretKey secretKey = generalKey();
//        JwtBuilder builder = Jwts.builder()
//                .setId(id)                                      // JWT_ID
//                .setAudience("")                                // 接受者
//                .setClaims(null)                                // 自定义属性
//                .setSubject("")                                 // 主题
//                .setIssuer("")                                  // 签发者
//                .setIssuedAt(new Date())                        // 签发时间
//                .setNotBefore(new Date())                       // 失效时间
//                .setExpiration(long)                                // 过期时间
//                .signWith(signatureAlgorithm, secretKey);           // 签名算法以及密匙
//        return builder.compact();
//	}
	/**
     * 由字符串生成加密key
     * JWT的组成：头部(header)  载荷(Clainms) 签名 (signature)
     * @return
     */
    public static SecretKey generalKey(){
        String stringKey = Constant.JWT_SECRET;
        byte[] encodedKey = Base64.decodeBase64(stringKey);
        SecretKey key = new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
        return key;
    }
    
    /**
     * 生成jwt字符串
     * @param user
     * @return
     */
    public static String create(UserReqModel user) {
        String json = JSON.toJSONString(user); //输入用户的id等信息(将object转换为json）
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        SecretKey key = generalKey(); // 加密的Key
        
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis); //从1970.1.1 00:00:00开始到现在的点的秒数
        
        return Jwts.builder()
        		.setId(Constant.JWT_ID)  // JWT_ID
        		.setIssuedAt(now)  // 签发时间
        		.setClaims(JSON.parseObject(json)) //getId就是用户指定的id号（将json转换为object）
                .setExpiration(new Date(nowMillis+Constant.JWT_REFRESH_TTL))
                .signWith(signatureAlgorithm.HS512, key)  // 使用SignatureAlgorithm用来对key加密
                .compact();
    }
    /**
     * 解析jwt字符串
     * @param jwt
     * @return
     * @throws Exception
     */
    public static Claims parseJWT(String jwt) throws Exception{
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()         
           .setSigningKey(key)
           .parseClaimsJws(jwt).getBody();
        return claims;
    }
    
    /**
     * 验证JWT
     * @param jwtStr
     * @return
     */
    public static CheckResult validateJWT(String jwtStr) {
        CheckResult checkResult = new CheckResult();
        Claims claims = null;
        try {
            claims = parseJWT(jwtStr);
            checkResult.setSuccess(true);
            checkResult.setClaims(claims);
        } 
        catch (ExpiredJwtException e) {
            checkResult.setError("The user has expired."); // 用户过期
            checkResult.setSuccess(false);
        } 
        catch (SignatureException e) {
            checkResult.setError("The signature is wrong"); // 签名有误
            checkResult.setSuccess(false);
        } 
        catch (Exception e) {
            checkResult.setError("Validation is wrong"); // 认证有误
            checkResult.setSuccess(false);
        }
        return checkResult;
    }
    

}
