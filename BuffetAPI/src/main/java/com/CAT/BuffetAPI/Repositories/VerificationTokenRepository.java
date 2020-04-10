package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CAT.BuffetAPI.Entities.Verificationtoken;


public interface VerificationTokenRepository extends JpaRepository<Verificationtoken,String> {

	Verificationtoken findByToken(String token);

}
