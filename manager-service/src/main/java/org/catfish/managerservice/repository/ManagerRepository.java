package org.catfish.managerservice.repository;

import org.apache.catalina.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepository extends JpaRepository<Manager, Long> {
}
