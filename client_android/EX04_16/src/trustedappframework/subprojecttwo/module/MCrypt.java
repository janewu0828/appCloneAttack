package trustedappframework.subprojecttwo.module;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MCrypt {

	private String iv = "fedcba9876543210";// 虛擬的 iv (需更改)
	private IvParameterSpec ivspec;
	private SecretKeySpec keyspec;
	private Cipher cipher;

	private String key = "";// 虛擬的 personal_key (需更改)

	public MCrypt(String key) {
		super();

		this.key = key;

		ivspec = new IvParameterSpec(iv.getBytes());
		keyspec = new SecretKeySpec(key.getBytes(), "AES");

		try {
			cipher = Cipher.getInstance("AES/CBC/NoPadding");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public byte[] encryptEB(String text) throws Exception {
		if (text == null || text.length() == 0)
			throw new Exception("Empty string");

		byte[] encrypted = null;

		try {
			cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);

			encrypted = cipher.doFinal(padString(text).getBytes());
		} catch (Exception e) {
			throw new Exception("[encrypt] " + e.getMessage());
		}

		return encrypted;
	}

	public byte[] decryptEB(String code) throws Exception {
		if (code == null || code.length() == 0)
			throw new Exception("Empty string");

		byte[] decrypted = null;

		try {
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

			decrypted = cipher.doFinal(hexToBytes(code));
		} catch (Exception e) {
			throw new Exception("[decrypt] " + e.getMessage());
		}
		return decrypted;
	}
	
	// 解密文件
		public void DecryptFile2(String session_key, File fileIn, File fileOut)
				throws Exception {
			try {
				// 讀文件
				FileInputStream fis = new FileInputStream(fileIn);
				byte[] bytIn = readbyte(fis);
				// AES解密
//				KeyGenerator kgen = KeyGenerator.getInstance("AES");
//				kgen.init(256, new SecureRandom(session_key.getBytes()));
//				SecretKey skey = kgen.generateKey();				
//				byte[] raw = skey.getEncoded();
//				SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
				keyspec = new SecretKeySpec(session_key.getBytes(), "AES");
//				Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
				Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
//				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
				// 寫文件
				// byte[] bytIn_tmp = interceptByte(bytIn,8,8);
				byte[] bytOut = cipher.doFinal(bytIn);
				FileOutputStream fos = new FileOutputStream(fileOut);
				InputStream sbs = new ByteArrayInputStream(bytOut);
//				fos.write(readbyte(sbs));
				
				CipherInputStream cis = new CipherInputStream(fis, cipher);
				// Here you read from the file in fis and write to cos.
				int b;
			    byte[] d = new byte[8];
			    while((b = cis.read(d)) != -1) {
//			        fos.write(d, 0, b);
			    	fos.write(readbyte(sbs));
			    }
			    fos.flush();
			    
				fos.close();
				fis.close();
				cis.close();
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		}

	// 解密文件
	public void DecryptFile(String session_key, File fileIn, File fileOut)
			throws Exception {
		try {
			// 讀文件
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = readbyte(fis);
			// AES解密
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(256, new SecureRandom(session_key.getBytes()));
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
//			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skeySpec);
			// 寫文件
			// byte[] bytIn_tmp = interceptByte(bytIn,8,8);
			byte[] bytOut = cipher.doFinal(bytIn);
			FileOutputStream fos = new FileOutputStream(fileOut);
			InputStream sbs = new ByteArrayInputStream(bytOut);
			fos.write(readbyte(sbs));
			fos.close();
			fis.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
	
	//讀取InputStream轉換為byte函数 ----
    protected static byte[] readbyte(InputStream stream)
    {
        try 
        {
            ByteArrayOutputStream baos = new ByteArrayOutputStream( 8196 );
            byte[] buffer = new byte[1024];
            int length = 0;
            while ( ( length = stream.read( buffer ) ) > 0 ) 
            {
                baos.write( buffer, 0, length );
            }
            return baos.toByteArray();
             
        }
        catch ( Exception exception ) 
        {
            return exception.getMessage().getBytes();
        }
    }

//	public byte[] decryptCB3(String code) throws Exception {
//		if (code == null || code.length() == 0)
//			throw new Exception("Empty string");
//
//		byte[] decrypted = null;
//
//		try {
//			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);
//
//			decrypted = cipher.doFinal(hexToBytes(code));
//		} catch (Exception e) {
//			throw new Exception("[decrypt] " + e.getMessage());
//		}
//		return decrypted;
//	}

	public byte[] decryptCB2(byte[] raw, byte[] iv, byte[] encrypted)
			throws Exception {
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		// Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		IvParameterSpec ivspec = new IvParameterSpec(iv);
		cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivspec);
		byte[] decrypted = cipher.doFinal(encrypted);

		return decrypted;
	}

	public byte[] decryptCB(byte[] code) throws Exception {
		if (code == null || code.length == 0)
			throw new Exception("Empty code");

//		System.out.println("after if");

		byte[] decrypted = null;

		try {
//			System.out.println("in try");
			cipher.init(Cipher.DECRYPT_MODE, keyspec, ivspec);

//			System.out.println("2");
			decrypted = cipher.doFinal(code);

//			System.out.println("3");
			// Remove trailing zeroes
			if (decrypted.length > 0) {
//				System.out.println("in if");
				int trim = 0;
				for (int i = decrypted.length - 1; i >= 0; i--)
					if (decrypted[i] == 0)
						trim++;

				if (trim > 0) {
					byte[] newArray = new byte[decrypted.length - trim];
					System.arraycopy(decrypted, 0, newArray, 0,
							decrypted.length - trim);
					decrypted = newArray;
				}
			}
//			System.out.println("after if");
		} catch (Exception e) {
			throw new Exception("[decrypt] " + e.getMessage());
		}
		return decrypted;
	}

	public static String bytesToHex(byte[] data) {
		if (data == null) {
			return null;
		}

		int len = data.length;
		String str = "";
		for (int i = 0; i < len; i++) {
			if ((data[i] & 0xFF) < 16)
				str = str + "0" + java.lang.Integer.toHexString(data[i] & 0xFF);
			else
				str = str + java.lang.Integer.toHexString(data[i] & 0xFF);
		}
		return str;
	}

	public static byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(
						str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	private static String padString(String source) {
		char paddingChar = ' ';
		int size = 16;
		int x = source.length() % size;
		int padLength = size - x;

		for (int i = 0; i < padLength; i++) {
			source += paddingChar;
		}

		return source;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
		keyspec = new SecretKeySpec(key.getBytes(), "AES");
	}

	public String getIv() {
		return iv;
	}

	public void setIv(String iv) {
		this.iv = iv;
		ivspec = new IvParameterSpec(iv.getBytes());
	}

}
