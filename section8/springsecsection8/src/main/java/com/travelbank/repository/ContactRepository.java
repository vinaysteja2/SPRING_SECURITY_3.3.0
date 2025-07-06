package com.travelbank.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.travelbank.model.Contact;

@Repository
public interface ContactRepository extends CrudRepository<Contact, String> {
	
	
}