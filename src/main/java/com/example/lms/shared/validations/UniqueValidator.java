package com.example.lms.shared.validations;

import com.example.lms.shared.RequestContext;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.jdbc.core.JdbcOperations;

public class UniqueValidator implements ConstraintValidator<Unique, String> {


    private final JdbcOperations jdbcOperations;
    private String table;
    private String column;


    public UniqueValidator(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.table=constraintAnnotation.table();
        this.column=constraintAnnotation.column();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {

        context.disableDefaultConstraintViolation();

        String  sql = String.format(
                "SELECT COUNT(*) AS aggregate FROM %s e WHERE (e.%s = ?)",
                table,
                column
        );
        Integer result = jdbcOperations.queryForObject(
                sql,
                Integer.class,
                email
        );


        context.buildConstraintViolationWithTemplate("is already exists")
                .addPropertyNode(column)
                .addConstraintViolation();

        return isValid(email,result);
    }


    public Boolean isValid(String value,Integer result){
            if(RequestContext.getAuthEmail().equals(value))
                        return result == 1;
            else
                return result == 0;

    }
}