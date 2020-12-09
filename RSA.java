import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.*;
import java.util.Scanner;
public class RSA {
	public static void main(String[] args ) throws FileNotFoundException{
		rsa();
	}
	
	public static void rsa() throws FileNotFoundException {
		
		System.out.println("Creating keys...");
		long startTime = System.nanoTime();
		//public key
		SecureRandom r = new SecureRandom();
		BigInteger P = BigInteger.probablePrime(1024,r);
		BigInteger Q = BigInteger.probablePrime(1024,r);
		BigInteger n = P.multiply(Q);
		BigInteger e = new BigInteger("65537");
		BigInteger _0 = new BigInteger("0");
		
		//e cannot be a factor of n
		while((e.mod(n)) == _0) { 
			P = BigInteger.probablePrime(1024,r);
			Q = BigInteger.probablePrime(1024,r);
			n = P.multiply(Q);
		}
		
		//private key
		BigInteger _1 = new BigInteger("1");
		BigInteger phi_n = (P.subtract(_1)).multiply(Q.subtract(_1));
		BigInteger d = e.modInverse(phi_n);
		long endTime = System.nanoTime();
		
		System.out.println("Keys created. Total Time = " + (endTime-startTime)+" nanoseconds"
		+ "\n" + "Public key: " + e + "\n" + 
		"Private key:" + d);
		
		System.out.println("Starting encryption...");
		startTime = System.nanoTime();
		
		//encrypt
		try (Scanner scan= new Scanner(new File("D:\\Cyber Class\\Crypto\\alice.txt"));
				PrintStream output = new PrintStream(new File("D:\\Cyber Class\\Crypto\\RSA_Encrypted.txt"))) {
					while (scan.hasNextLine()) {
						String input = scan.nextLine();
						StringBuilder toNumber = new StringBuilder();
						for(int i = 0; i < input.length(); i++) {
							toNumber.append((int)(input.charAt(i)));
						}
						input = toNumber.toString();
						if(input.equals("")) {
							input = "32";
						}
						BigInteger numberInput = new BigInteger(input);
						BigInteger encrypted = numberInput.modPow(e,n);
						output.println(encrypted);
					}
		}
		endTime = System.nanoTime();
		System.out.println("Encrypted. Total Time = " + (endTime-startTime) +" nanoseconds");
		
		
		System.out.println("Starting decryption...");
		startTime = System.nanoTime();
		
		//decrypt
		try (Scanner scan= new Scanner(new File("D:\\Cyber Class\\Crypto\\RSA_Encrypted.txt"));
				PrintStream output = new PrintStream(new File("D:\\Cyber Class\\Crypto\\RSA_Decrypted.txt"))) {
					while (scan.hasNextLine()) {
						String encryptedText = scan.nextLine();
						BigInteger numbers = new BigInteger(encryptedText);
						BigInteger decrypted = numbers.modPow(d,n);
						String decryptedString = decrypted.toString();
						StringBuilder builder = new StringBuilder();
						for(int i = 0; i < decryptedString.length(); i++) {
							if(decryptedString.charAt(i) == '1') {
								builder.append((char)(Integer.parseInt(decryptedString.substring(i,i+3))));
								i = i + 2;
							}
							else {
								builder.append((char)(Integer.parseInt(decryptedString.substring(i,i+2))));
								i = i + 1;
							}
						}
						output.println(builder);
					}
		}
		endTime = System.nanoTime();
		System.out.println("Decrypted. Total Time = " + (endTime-startTime) +" nanoseconds");
	}
}
