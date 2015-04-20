/**
 * @文件名称: Base64Coder.java
 * @类路径:   com.rb.lottery.util
 * @描述:     对称密码算法和散列算法
 * @作者:     robin
 * @时间:     2011-10-28 上午10:51:40
 * @版本:     1.0.0
 */
package com.rb.lottery.server.util;

import java.io.ByteArrayOutputStream;
import java.security.Key;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import com.rb.lottery.system.SystemEnvironment;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * @类功能说明: 提供了一个安全算法类,其中包括对称密码算法和散列算法
 * @类修改者:
 * @修改日期:
 * @修改说明:
 * @作者: robin
 * @创建时间: 2011-10-28 上午10:51:40
 * @版本: 1.0.0
 */

public final class Base64Coder {

	private final static BASE64Encoder base64encoder = new BASE64Encoder();
	private final static BASE64Decoder base64decoder = new BASE64Decoder();

	private final static String ENCRYPT_ENCODING = "gbk";
	// The length of Encryptionstring should be 8 bytes and not be a weak key
	// 帐户密码密钥
	private final static String ACCOUT_ENCRYPT_KEY = SystemEnvironment
			.getInstance().account_encrypt_key;
	// 支付密码密钥
	private final static String PAY_ENCRYPT_KEY = SystemEnvironment
			.getInstance().pay_encrypt_key;

	private final static String DES = "DES";
	private final static String HASH = "SHA-1";

	/**
	 * 加密帐户密码
	 */
	public static String base64EncryptAct(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = Base64Coder.symmetricEncrypt(str
						.getBytes(ENCRYPT_ENCODING), ACCOUT_ENCRYPT_KEY);
				result = base64encoder.encode(encodeByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密帐户密码
	 */
	public static String base64DecryptAct(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = base64decoder.decodeBuffer(str);
				byte[] decoder = Base64Coder.symmetricDecrypto(encodeByte,
						ACCOUT_ENCRYPT_KEY);
				result = new String(decoder, ENCRYPT_ENCODING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 加密支付密码
	 */
	public static String base64EncryptPay(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = Base64Coder.symmetricEncrypt(str
						.getBytes(ENCRYPT_ENCODING), PAY_ENCRYPT_KEY);
				result = base64encoder.encode(encodeByte);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 解密支付密码
	 */
	public static String base64DecryptPay(String str) {
		String result = str;
		if (str != null && str.length() > 0) {
			try {
				byte[] encodeByte = base64decoder.decodeBuffer(str);
				byte[] decoder = Base64Coder.symmetricDecrypto(encodeByte,
						PAY_ENCRYPT_KEY);
				result = new String(decoder, ENCRYPT_ENCODING);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 对称加密方法
	 * 
	 * @param byteSource
	 *            需要加密的数据
	 * @return 经过加密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricEncrypt(byte[] byteSource, String encryptKey)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.ENCRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			byte[] keyData = encryptKey.getBytes();
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			// int blockSize = cipher.getBlockSize();
			// int position = 0;
			// int length = byteSource.length;
			// boolean more = true;
			// while (more) {
			// if (position + blockSize <= length) {
			// baos.write(cipher.update(byteSource, position, blockSize));
			// position += blockSize;
			// } else {
			// more = false;
			// }
			// }
			// if (position < length) {
			// baos.write(cipher.doFinal(byteSource, position, length
			// - position));
			// } else {
			// baos.write(cipher.doFinal());
			// }
			// return baos.toByteArray();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 对称解密方法
	 * 
	 * @param byteSource
	 *            需要解密的数据
	 * @return 经过解密的数据
	 * @throws Exception
	 */
	public static byte[] symmetricDecrypto(byte[] byteSource, String encryptKey)
			throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			int mode = Cipher.DECRYPT_MODE;
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
			byte[] keyData = encryptKey.getBytes();
			DESKeySpec keySpec = new DESKeySpec(keyData);
			Key key = keyFactory.generateSecret(keySpec);
			Cipher cipher = Cipher.getInstance(DES);
			cipher.init(mode, key);
			byte[] result = cipher.doFinal(byteSource);
			// int blockSize = cipher.getBlockSize();
			// int position = 0;
			// int length = byteSource.length;
			// boolean more = true;
			// while (more) {
			// if (position + blockSize <= length) {
			// baos.write(cipher.update(byteSource, position, blockSize));
			// position += blockSize;
			// } else {
			// more = false;
			// }
			// }
			// if (position < length) {
			// baos.write(cipher.doFinal(byteSource, position, length
			// - position));
			// } else {
			// baos.write(cipher.doFinal());
			// }
			// return baos.toByteArray();
			return result;
		} catch (Exception e) {
			throw e;
		} finally {
			baos.close();
		}
	}

	/**
	 * 散列算法
	 * 
	 * @param byteSource
	 *            需要散列计算的数据
	 * @return 经过散列计算的数据
	 * @throws Exception
	 */
	public static byte[] hashMethod(byte[] byteSource) throws Exception {
		try {
			MessageDigest currentAlgorithm = MessageDigest.getInstance(HASH);
			currentAlgorithm.reset();
			currentAlgorithm.update(byteSource);
			return currentAlgorithm.digest();
		} catch (Exception e) {
			throw e;
		}
	}

}
