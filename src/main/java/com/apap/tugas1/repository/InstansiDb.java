package com.apap.tugas1.repository;

import com.apap.tugas1.model.*;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstansiDb extends JpaRepository<InstansiModel, Long>{

}
