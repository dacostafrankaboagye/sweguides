//package org.example.springsecurity.lesson2.repositories;
//
//import org.example.springsecurity.lesson2.entities.MyUser;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import java.util.Optional;
//
//public interface MyUsersRepository extends JpaRepository<MyUser, Integer> {
//    // you can specify the query too
//    @Query(
//           """
//           SELECT u FROM MyUser u WHERE u.username = :username
//           """
//    )
//    Optional<MyUser> findMyUserByUsername(String username);
//}
