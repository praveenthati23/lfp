package com.lastfarewells.backend.repository;

import com.lastfarewells.backend.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
