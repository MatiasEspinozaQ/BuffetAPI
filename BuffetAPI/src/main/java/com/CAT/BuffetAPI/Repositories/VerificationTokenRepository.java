package com.CAT.BuffetAPI.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.CAT.BuffetAPI.Entities.VerificationToken;

public interface VerificationTokenRepository extends JpaRepository<VerificationToken,String> {

}
