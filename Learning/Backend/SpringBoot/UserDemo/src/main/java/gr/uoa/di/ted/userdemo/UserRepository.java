package gr.uoa.di.ted.userdemo;


import org.springframework.data.jpa.repository.JpaRepository;

interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

}
