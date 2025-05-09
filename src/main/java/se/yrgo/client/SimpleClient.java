package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerManagementServiceProductionImpl;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class SimpleClient {

    public static void main(String[] args) throws CustomerNotFoundException {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
        CustomerManagementService service = container.getBean("customerManagement", CustomerManagementServiceProductionImpl.class);
        Customer customer1 = new Customer("C005", "anomalin jewelry", "anomlin@hjdf", "0730449610", "jewelry maker");
        Customer customer2 = new Customer("C006", "Samuel Petersson", "hello@sampet.com", "0834894", "Photographer");
        Customer customer3 = new Customer("C007", "Stina art", "art@stina", "07438297834", "Artist");
        service.newCustomer(customer1);
        service.newCustomer(customer2);
        service.newCustomer(customer3);

        List<Customer> customerList = service.getAllCustomers();
        System.out.println("Full customer list: ");
        for (Customer c : customerList) {
            System.out.println(c);
        }

        service.deleteCustomer(customer2);

        List<Customer> updatedList = service.getAllCustomers();
        System.out.println("\nList after deleting a customer: ");
        for (Customer c : updatedList) {
            System.out.println(c);
        }


        Call call1 = new Call("Call Malin back");
        Call call2 = new Call("Stina needs feedback on her email");
        Call call3 = new Call("Schedule a meeting with Malin");

        try {
            service.recordCall("C005", call1);
            service.recordCall("C007", call2);
            service.recordCall("C005", call3);
        }catch(CustomerNotFoundException e) {
            System.err.println("This customer does not exist.");
        }

        customer3.setCompanyName("Stina Art AB");
        service.updateCustomer(customer3);

        List<Customer> findName = service.findCustomersByName("anomalin jewelry");
        for (Customer c : findName) {
            System.out.println("\nFinding by name: " + c);
        }

        Customer updatedCustomer = service.findCustomerById("C007");
        System.out.println("\nUpdated customer: " + updatedCustomer);


        try {
            Customer fullInfo = service.getFullCustomerDetail("C005");
            System.out.println("\nFull info on customer: " + fullInfo.toString());
        } catch (CustomerNotFoundException e) {
            e.printStackTrace();
        }


        container.close();
    }
}