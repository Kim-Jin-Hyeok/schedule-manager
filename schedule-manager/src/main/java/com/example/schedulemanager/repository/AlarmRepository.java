package com.example.schedulemanager.repository;

import com.example.schedulemanager.domain.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long>{
	List<Alarm> findByUserIdAndCheckedFalse(Long userId);
}
