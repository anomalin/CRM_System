package se.yrgo.services.calls;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;

public class CallHandlingServiceImpl implements CallHandlingService {
    private CustomerManagementService customers;
    private DiaryManagementService diary;

    public CallHandlingServiceImpl(CustomerManagementService customers, DiaryManagementService diary){
        this.customers = customers;
        this.diary = diary;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
            customers.recordCall(customerId, newCall);
            for (Action a : actions) {
                diary.recordAction(a);
            }
    }
}
