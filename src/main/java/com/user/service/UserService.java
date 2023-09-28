package com.user.service;

import com.user.exceptionH.IllegalEmailException;
import com.user.exceptionH.NotAnAdultException;
import com.user.exceptionH.UserNotFoundException;
import com.user.model.User;
import com.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    // min age from properties file
    @Value("${min.age}")
    private int minAge;
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAll(){
        return repository.findAll();
    }

    public User getById(Long id){
        return repository.findById(id).orElseThrow(
                () -> new RuntimeException("No user with current id")
        );
    }

    public User add(User user){
        // email
        Pattern pEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
        if (user.getEmail() == null || !pEmail.matcher(user.getEmail()).matches()) {
            throw new IllegalEmailException("Illegal email address");
        }
        // date
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = user.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period age = Period.between(birthDate, currentDate);

        if (age.getYears() < minAge) {
            throw new NotAnAdultException("User must be more than 18 years old");
        }
        return repository.save(user);
    }

    public User edit(Long id, User user){
        // email
        Pattern pEmail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
        if (user.getEmail() == null || !pEmail.matcher(user.getEmail()).matches()) {
            throw new IllegalEmailException("Illegal email address");
        }
        // date
        LocalDate currentDate = LocalDate.now();
        LocalDate birthDate = user.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Period age = Period.between(birthDate, currentDate);

        if (age.getYears() < minAge) {
            throw new NotAnAdultException("User must be more than 18 years old");
        }

        Optional<User> userOptional = repository.findById(id);
        if(userOptional.isPresent()){
            User user1 = userOptional.get();
            user1.setName(user.getName());
            user1.setSurname(user.getSurname());
            user1.setEmail(user.getEmail());
            user1.setAddress(user.getAddress());
            user1.setDate(user.getDate());
            user1.setPhoneNumber(user.getPhoneNumber());
            return repository.save(user1);
        }else{
            throw new UserNotFoundException("Wrong id: "+id);
        }
    }

    public List<User> findUsersByBirthDateRange(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null || fromDate.after(toDate)) {
            throw new IllegalArgumentException("Invalid date range");
        }

        return repository.findByDate(fromDate, toDate);
    }

    public void delete(Long id){
        User user = repository.findById(id).orElseThrow(
                () -> new UserNotFoundException("id: "+id)
        );
        repository.delete(user);
    }



}
