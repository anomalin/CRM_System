package se.yrgo.client;


import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.calls.CallHandlingService;
import se.yrgo.services.customers.CustomerManagementMockImpl;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");
        CustomerManagementService service = container.getBean("customerManagement", CustomerManagementMockImpl.class);
        List<Customer> customerList = service.getAllCustomers();
        for (Customer c : customerList) {
            System.out.println(c);
        }

        DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

        CallHandlingService callService = container.getBean(CallHandlingService.class);

        Call newCall = new Call("Dom called from Twin Peaks Company");
        Action action1 = new Action ("Call back Dom as soon as possible for feedback",
                new GregorianCalendar(2019,12,10), "user");
        Action action2 = new Action ("Check if Dom called again",
                new GregorianCalendar(2019,12,11), "user");
        List<Action> actions = new ArrayList<>();
        actions.add(action1);
        actions.add(action2);

        try {
            callService.recordCall("NV10", newCall, actions);
        }catch(CustomerNotFoundException e) {
            System.err.println("This customer does not exist.");
        }

        System.out.println("Here are the actions:");
        Collection<Action> incompleteActions = diaryService.getAllIncompleteActions("user");
        for(Action action:incompleteActions) {
            System.out.println(action);
        }

        container.close();
    }
}