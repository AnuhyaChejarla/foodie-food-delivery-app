package com.tap.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tap.model.Menu;

public interface MenuRepo extends JpaRepository<Menu, Integer> {

}
