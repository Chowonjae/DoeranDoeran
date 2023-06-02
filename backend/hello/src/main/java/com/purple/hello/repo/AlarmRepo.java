package com.purple.hello.repo;

import com.purple.hello.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlarmRepo extends JpaRepository<Alarm, Long> {
}
