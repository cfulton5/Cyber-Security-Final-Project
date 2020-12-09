import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.*;
public class Caesar {
	public static void main(String[] args ) throws FileNotFoundException {
		@SuppressWarnings("resource")
		Scanner numberReader = new Scanner(System.in);
	    System.out.print("Enter a number: ");
		int shift = numberReader.nextInt();
		
		System.out.println("Encrypting...");
		long startTime = System.nanoTime();
		String fileName = "D:\\Cyber Class\\Crypto\\alice.txt";
		try (Scanner scan= new Scanner(new File(fileName));
			PrintStream output = new PrintStream(new File("D:\\Cyber Class\\Crypto\\Caesar_Encrypted.txt"))) {
				while (scan.hasNextLine()) {
					output.println(caesarEncrypt(scan.nextLine(), shift));
				}
		}
		long endTime = System.nanoTime();
		System.out.println("Encryption complete. Time elapsed: " + (endTime - startTime));
		
		System.out.println("Decrypting...");
		startTime = System.nanoTime();
		try (Scanner scan= new Scanner(new File("D:\\Cyber Class\\Crypto\\Caesar_Encrypted.txt"));
				PrintStream output = new PrintStream(new File("D:\\Cyber Class\\Crypto\\Caesar_Decrypted.txt"))) {
					while (scan.hasNextLine()) {
						output.println(caesarDecrypt(scan.nextLine(), shift));
					}
		}
		endTime = System.nanoTime();
		System.out.println("Encryption complete. Time elapsed: " + (endTime - startTime));
		
		
	}
	
	static String caesarEncrypt(String text, int shift){
		StringBuilder encryptedText = new StringBuilder();
	    for(int i = 0; i < text.length(); i++){
	    	if((char)(text.charAt(i) + shift) > 126) {
	    		int overflow = shift -126 + 31;
	    		encryptedText.append((char)(text.charAt(i) + (overflow)));
	    	}
	    	else {
	    		encryptedText.append((char)(text.charAt(i) + shift));
	    	}
	    }
	    return encryptedText.toString();
	}
	
	static String caesarDecrypt(String text, int shift) {
		StringBuilder decryptedText = new StringBuilder();
	    for(int i = 0; i < text.length(); i++){
	    	if((char)(text.charAt(i) - shift) < 32) {
	    		decryptedText.append(((char)(text.charAt(i) - shift + 126 - 31)));
	    	}
	    	else {
	    		decryptedText.append((char)(text.charAt(i) - shift));
	    	}
	    }
	    return decryptedText.toString();		
	}
}

