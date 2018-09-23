package com.andrewhoover.sctool.data.database;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DataPointRepository extends JpaRepository<DataPoint, Integer> {
}
