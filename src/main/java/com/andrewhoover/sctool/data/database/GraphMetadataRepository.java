package com.andrewhoover.sctool.data.database;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.QueryHint;

public interface GraphMetadataRepository extends JpaRepository<GraphMetadata, Integer> {
    @Query("SELECT t FROM #{#entityName} t")
    @QueryHints(
            {
                    @QueryHint(name = "TOP", value = "1"),
                    @QueryHint(name = "org.hibernate.cachable", value = "true")
            })
    GraphMetadata getTopOne();
}
