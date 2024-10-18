package org.jsp.Flight_Ticket_Bookinggg.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES  //69--->Advanced Encryption Standard
{

	 

	 
 
	
	
	 
	
	
	
	private static SecretKeySpec secretKey;//79
	private static byte[] key;  //73---->wide range of characters are going to be stored in this arra with the help of  UTF-8

	public static void setKey(final String myKey) //70
	{
		MessageDigest sha = null; //it is used for data integrity or data security--71
		try {
			key = myKey.getBytes("UTF-8");//72 unicode transformation format--->its primary work is to represent a wide range of characters(including symbols emojis,special character)
			sha = MessageDigest.getInstance("SHA-1");//75---secure hash algorithm1--->it will provide some space 20byte --160 bits to store the characters from the   key =myKey.getBytes("UTF-8");
			key = sha.digest(key);//76
			key = Arrays.copyOf(key, 16);//77 here it is helping us to create 16 cells to store 16 characters of password
			secretKey = new SecretKeySpec(key, "AES");//78 it means inside key all the characters are presented in byte array formate to make it as AES-Advanced encryption standard
		} catch (Exception e) {
			e.printStackTrace();
		}
		//note:here try-catch block is used to handle exceptions that may arise during the key generation process--->UnsupportedEncodingException:
		//                                                                                                           2)No Such Algorithm Exception etc
		                                                                                                            //3)Array index out of Bounds Exception
	}
	
	public static String encrypt(final String strToEncrypt, final String secret) //69
	{
		//very important note about String secret
		
	//	During encryption, the secret is used to scramble the input text into a non-readable form (ciphertext).
	//	During decryption, the same secret is used to unscramble the ciphertext back into the original text.
		
		
		try {
			setKey(secret);//71
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//80  ecb stands for electronic Codebook mode -->finally entire this line is used for encryption and decryption of data
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);//81 in summary this line sets up cipher to encrypt data using the specified secret key
			return Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes("UTF-8")));//82---this line represents the common way of binary data
		} catch (Exception e) {
			System.out.println("Error while encrypting: " + e.toString());//83 here we will mention error with detail of Exception.
		}
		return null;
	}
	
 
	
	public static String decrypt(final String strToDecrypt, final String secret) //160
	{
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
			cipher.init(Cipher.DECRYPT_MODE, secretKey);
		return new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
	 
		} catch (Exception e) {
			System.out.println("Error while decrypting: " + e.toString());
		}
		return null;
	}
	

	 
	
	
	
 
	
	

}
