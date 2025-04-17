package se.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;

public class CustomerManagementMockImpl implements CustomerManagementService {
	private HashMap<String,Customer> customerMap;

	public CustomerManagementMockImpl() {
		customerMap = new HashMap<String,Customer>();
		customerMap.put("OB74", new Customer("OB74" ,"Fargo Ltd", "some notes"));
		customerMap.put("NV10", new Customer("NV10" ,"North Ltd", "some other notes"));
		customerMap.put("RM210", new Customer("RM210" ,"River Ltd", "some more notes"));
	}


	@Override
	public void newCustomer(Customer newCustomer) {
			customerMap.put(newCustomer.getCustomerId(), newCustomer);
	}

	@Override
	public void updateCustomer(Customer changedCustomer) {
		Customer existingCustomer = customerMap.get(changedCustomer.getCustomerId());

		if (existingCustomer != null) {
			existingCustomer.setCompanyName(changedCustomer.getCompanyName());
			existingCustomer.setEmail(changedCustomer.getEmail());
			existingCustomer.setTelephone(changedCustomer.getTelephone());
			existingCustomer.setNotes(changedCustomer.getNotes());
			existingCustomer.setCalls(changedCustomer.getCalls());

		} else {
			throw new IllegalArgumentException("Customer not found: " + changedCustomer.getCustomerId());
		}

	}

	@Override
	public void deleteCustomer(Customer oldCustomer) {
		customerMap.remove(oldCustomer.getCustomerId());

	}

	@Override
	public Customer findCustomerById(String customerId) throws CustomerNotFoundException {
		return customerMap.get(customerId);
	}

	@Override
	public List<Customer> findCustomersByName(String name) {
		List<Customer> customerList = new ArrayList<>(customerMap.values());
		List<Customer> listByName = customerList.stream().filter(c -> c.getCompanyName().equals("name")).collect(Collectors.toList());
		return listByName;
	}

	@Override
	public List<Customer> getAllCustomers() {
		List<Customer> customerList = new ArrayList<>(customerMap.values());
		return customerList;
	}

	@Override
	public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
		Customer customer = customerMap.get(customerId);
		return customer;
	}

	@Override
	public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
		//First find the customer
		Customer customer = customerMap.get(customerId);

		//Call the addCall on the customer
		customer.addCall(callDetails);
	}

}
