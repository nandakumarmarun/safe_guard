package com.security.analyzer.v1.securitytest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the SecurityTest entity.
 */
@Repository
public interface SecurityTestRepository extends JpaRepository<SecurityTest, Long> {
    default Optional<SecurityTest> findOneWithEagerRelationships(Long id) {
        return this.findOneWithToOneRelationships(id);
    }

    default List<SecurityTest> findAllWithEagerRelationships() {
        return this.findAllWithToOneRelationships();
    }

    default Page<SecurityTest> findAllWithEagerRelationships(Pageable pageable) {
        return this.findAllWithToOneRelationships(pageable);
    }

    @Query(
        value = "select securityTest from SecurityTest securityTest left join fetch securityTest.applicationUser",
        countQuery = "select count(securityTest) from SecurityTest securityTest"
    )
    Page<SecurityTest> findAllWithToOneRelationships(Pageable pageable);

    @Query("select securityTest from SecurityTest securityTest left join fetch securityTest.applicationUser")
    List<SecurityTest> findAllWithToOneRelationships();

    @Query("select securityTest from SecurityTest securityTest left join fetch securityTest.applicationUser where securityTest.id =:id")
    Optional<SecurityTest> findOneWithToOneRelationships(@Param("id") Long id);
}
