package com.example.lms.shared;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @After("execution(* com.example.lms.owner.services.BookManagementService.store(..))")
    public void logAfterBookCreated() {
        System.out.println("book created successfully");
    }

    @After("execution(* com.example.lms.owner.services.BookManagementService.update(..))")
    public void logAfterBookUpdated() {
        System.out.println("book updated successfully");
    }

    @After("execution(* com.example.lms.owner.services.BookManagementService.destroy(..))")
    public void logAfterBookDeleted() {
        System.out.println("book deleted successfully");
    }


    @After("execution(* com.example.lms.loan.BookLoanService.borrow(..))")
    public void logAfterBorrowed() {
        System.out.println("book borrowed successfully");
    }

    @After("execution(* com.example.lms.loan.BookLoanService.returnBack(..))")
    public void logAfterBookReturnBack() {
        System.out.println("book return back successfully");
    }

    @After("execution(* com.example.lms.owner.controllers.AuthController.register(..))")
    public void logAfterLibraryOwnerCreated() {
        System.out.println("library owner Registered successfully");
    }

    @After("execution(* com.example.lms.patron.controllers.AuthPatronController.register(..))")
    public void logAfterPatronCreated() {
        System.out.println("patron Registered successfully");
    }


    @After("execution(* com.example.lms.patron.controllers.PatronController.update(..))")
    public void logAfterPatronUpdated() {
        System.out.println("patron updated successfully");
    }

    @After("execution(* com.example.lms.patron.controllers.PatronController.delete(..))")
    public void logAfterPatronDeleted() {
        System.out.println("patron delete successfully");
    }
}
