package com.example.appddiction.services;

import com.example.appddiction.models.Admin;
import com.example.appddiction.models.UserWithRoles;
import com.example.appddiction.repositories.AdminRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader extends Admin implements UserDetailsService {
	private final AdminRepository admins;
	public UserDetailsLoader(AdminRepository admins) {
		this.admins = admins;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Admin admin = admins.findByUsername(username);
		if (admin == null) {
			throw new UsernameNotFoundException("No user found for " + username);
		} else {
			return new UserWithRoles(admin);
		}
	}
}
