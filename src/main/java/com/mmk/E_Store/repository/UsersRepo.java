

package com.mmk.E_Store.repository;

import com.mmk.E_Store.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {

    Optional<Users> findByEmailAddress(String emailAddress);

    Optional<Users> findByEmailAddressAndPassword(String emailAddress, String password);

    @Query("SELECT u FROM Users u LEFT JOIN FETCH u.orders WHERE u.id = :userId")
    Optional<Users> findByIdWithOrders(@Param("userId") Long userId);

    boolean existsByEmailAddress(String emailAddress);

    @Query("SELECT u FROM Users u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Users> findByNameContainingIgnoreCase(@Param("name") String name);
}
