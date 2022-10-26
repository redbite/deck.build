package com.example.loginApp;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPsw = "banane12";
		String encodedPwd = encoder.encode(rawPsw);
		System.out.println(encodedPwd);
	}

}
